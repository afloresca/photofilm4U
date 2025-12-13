package edu.uoc.epcsd.course.domain.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import edu.uoc.epcsd.course.domain.Course;
import edu.uoc.epcsd.course.domain.CourseStatus;
import edu.uoc.epcsd.course.domain.repository.CourseRepository;
import edu.uoc.epcsd.course.domain.repository.RestMicrocredentialsRepositoryUOC;
import edu.uoc.epcsd.course.domain.repository.UserRepository;
import edu.uoc.epcsd.course.infrastructure.kafka.CourseMessage;
import edu.uoc.epcsd.course.infrastructure.kafka.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final RestMicrocredentialsRepositoryUOC restMicrocredentialsRepositoryUOC;
    private final KafkaTemplate<String, CourseMessage> courseKafkaTemplate;
	
    @Override
    public List<Course> findCourses() {
        return courseRepository.findCourses();
    }

    @Override
    public List<String> getEnrolledStudents(Long courseId) {

    	// return course.getEnrollment() if courseId exists   
        Course course = courseRepository
                .getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("The "+courseId+" course does not exist!"));  
        return course.getEnrollment(); 
    }

    @Override
    public Optional<Course> getCourseById(Long courseId) {    	

    	// return getCourseById(courseId) if courseId exists          
    	return courseRepository.getCourseById(courseId);
    
    }

    @Override
    public Long createCourse(Course course) {
   	    	// set status how DRAFT
        course.setStatus(CourseStatus.DRAFT);          
        // and return courseRepository.createCourse(course)         
    	
    	return courseRepository.createCourse(course);
    }

    @Override
    public Long modifyCourseDetails(Long courseId, String instructor, String title, String description, Date enrollmentStartDate, Date enrollmentEndDate, String mode, Long price,
    		String objectives, String methology, Long duration, String language, String location) {

    	// verify that the courseId and the instructor exists and then set all variables
        Course course = courseRepository
                .getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("The "+courseId+"th course does not exist!"));
        if (!userRepository.findUserByEmail(instructor)){
             new IllegalArgumentException("The instructor with email "+instructor+" does not exist!"); 
            }
        course.setInstructor(instructor);
        course.setTitle(title);
        course.setDescription(description);
        course.setEnrollmentStartDate(enrollmentStartDate);
        course.setEnrollmentEndDate(enrollmentEndDate);
        course.setMode(mode);
        course.setPrice(price);
        course.setObjectives(objectives);
        course.setMethology(methology);
        course.setDuration(duration);
        course.setLanguage(language);
        course.setLocation(location);
                 
        // and return courseRepository.modifyCourseDetails(course)  
        Long modifyId = courseRepository.modifyCourseDetails(course);       

        return modifyId;
    }

    @Override
    public Long openEnrollmentCourse(Long courseId) {

        // verify that the courseId exists and that the status is DRAFT, then change status
        Course course = courseRepository
                .getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("The "+courseId+" course does not exist!"));
        if (!course.getStatus().equals(CourseStatus.DRAFT)) {
            throw new IllegalArgumentException("The course " +courseId+" is not in Draft status!");
        }
        course.setStatus(CourseStatus.ENROLLMENT_OPEN); 

        // and return courseRepository.openEnrollmentCourse(course)         
        Long openId = courseRepository.openEnrollmentCourse(course);

        //emit event CourseEnrollmentOpened
        courseKafkaTemplate.send(KafkaConstants.COURSE_TOPIC, CourseMessage.builder()
                .courseId(courseId)
                .type("ENROLLMENT_OPENED")
                .occurredOn(LocalDateTime.now())
                .payload(Long.toString(courseId))
                .build());
        
        return openId;
    }

    @Override
    public Long closeEnrollmentCourse(Long courseId) {

        // verify that the courseId exists and that the status is ENROLLMENT_OPEN, then change status
        Course course = courseRepository
                .getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("The "+courseId+"th course does not exist!"));

        if (!course.getStatus().equals(CourseStatus.ENROLLMENT_OPEN)) {
            throw new IllegalArgumentException("The course " +courseId+"th is not in Enrollment Open status!");
        }

        course.setStatus(CourseStatus.PENDING_CLOSUE);

        //emit event CourseEnrollmentClosed
        courseKafkaTemplate.send(KafkaConstants.COURSE_TOPIC, CourseMessage.builder()
                .courseId(courseId)
                .type("ENROLLMENT_CLOSED")
                .occurredOn(LocalDateTime.now())
                .payload(Long.toString(courseId))
                .build());

        // and return courseRepository.closeEnrollmentCourse(course)  
        Long closeId = courseRepository.closeEnrollmentCourse(course);

        
        return closeId;
    }

    @Override
    public Long enrollInCourse(Long courseId, String userEmail) {
    	
    	// verify that the courseId exists, that the status is ENROLLMENT_OPEN and that userEmail exists
        Course course = courseRepository
                .getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("The "+courseId+"th course does not exist!"));

        if (!course.getStatus().equals(CourseStatus.ENROLLMENT_OPEN)) {
            throw new IllegalArgumentException("The course " +courseId+"th is not in Enrollment Open status!");
        }
        if (!userRepository.findUserByEmail(userEmail)){
             new IllegalArgumentException("The user with email "+userEmail+" does not exist!");
         }
 
    	// then add user to course if he isn't already included 
        if (course.getEnrollment().contains(userEmail)) {
            throw new IllegalArgumentException("The user with email "+userEmail+" is already enrolled in the course "+courseId+"th!");
        }     

        course.getEnrollment().add(userEmail);
   
        Long enrollId = courseRepository.enrollInCourse(course);

        courseKafkaTemplate.send(KafkaConstants.COURSE_TOPIC, CourseMessage.builder()
                .courseId(courseId)
                .type("STUDENT_ENROLLED")
                .occurredOn(LocalDateTime.now())
                .payload(userEmail)
                .build());
        //return the enrollId        
        return enrollId;

    }

    @Override
    public Long closeGradeReports(Long courseId) {
    	
    	// verify that the courseId exists and that the status is ACTIVE, then change status
        Course course = courseRepository
                .getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("The "+courseId+" course does not exist!"));
        if (!course.getStatus().equals(CourseStatus.ACTIVE)) {
            throw new IllegalArgumentException("The course " +courseId+" is not in Active status!");
        }
        // and return courseRepository.closeGradeReports(course)  
        Long closeId = courseRepository.closeGradeReports(course);

        //send kafka event CourseGradeReportsClosed
        courseKafkaTemplate.send(KafkaConstants.COURSE_TOPIC, CourseMessage.builder()
                .courseId(courseId)
                .type("GRADE_REPORTS_CLOSED")
                .occurredOn(LocalDateTime.now())
                .payload(Long.toString(courseId))
                .build());  

        return closeId;
    }

    @Override
    public Long closeCourse(Long courseId) {
        Course course = courseRepository
                .getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("The "+courseId+" course does not exist!"));
        
        if (!course.getStatus().equals(CourseStatus.PENDING_CLOSUE)) {
            throw new IllegalArgumentException("The course " +courseId+" is not in Pending Closue status!");
        }
        
        course.setStatus(CourseStatus.CLOSED);        
        
        restMicrocredentialsRepositoryUOC.createMicrocredentials(course);      
        
        return courseRepository.closeEnrollmentCourse(course);
    }
    
}
