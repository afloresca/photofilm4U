package edu.uoc.epcsd.course.domain.service;

import edu.uoc.epcsd.course.domain.Course;
import edu.uoc.epcsd.course.domain.CourseStatus;
import edu.uoc.epcsd.course.domain.repository.CourseRepository;
import edu.uoc.epcsd.course.domain.repository.RestMicrocredentialsRepositoryUOC;
import edu.uoc.epcsd.course.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final RestMicrocredentialsRepositoryUOC restMicrocredentialsRepositoryUOC;
	
    @Override
    public List<Course> findCourses() {
        return courseRepository.findCourses();
    }

    @Override
    public List<String> getEnrolledStudents(Long courseId) {

    	// TODO: add the code for the missing system operations here:
    	// return course.getEnrollment() if courseId exists         

        return null;
    }

    @Override
    public Optional<Course> getCourseById(Long courseId) {
    	
    	// TODO: add the code for the missing system operations here:
    	// return getCourseById(courseId) if courseId exists         
 
    	return null;
    
    }

    @Override
    public Long createCourse(Course course) {
    	
    	// TODO: add the code for the missing system operations here:
    	// set status how DRAFT
        // and return courseRepository.createCourse(course)         
    	
    	return null;
    }

    @Override
    public Long modifyCourseDetails(Long courseId, String instructor, String title, String description, Date enrollmentStartDate, Date enrollmentEndDate, String mode, Long price,
    		String objectives, String methology, Long duration, String language, String location) {
    	 	
    	// TODO: add the code for the missing system operations here:
    	// verify that the courseId and the instructor exists and then set all variables
        // and return courseRepository.modifyCourseDetails(course)         

        return null;
    }

    @Override
    public Long openEnrollmentCourse(Long courseId) {
    	
    	// TODO: add the code for the missing system operations here:
    	// verify that the courseId exists and that the status is DRAFT, then change status
        // and return courseRepository.openEnrollmentCourse(course)         
        
        return null;
    }

    @Override
    public Long closeEnrollmentCourse(Long courseId) {

    	// TODO: add the code for the missing system operations here:
    	// verify that the courseId exists and that the status is ENROLLMENT_OPEN, then change status
        // and return courseRepository.closeEnrollmentCourse(course)  
       
        return null;
    }

    @Override
    public Long enrollInCourse(Long courseId, String userEmail) {
    	
    	// TODO: add the code for the missing system operations here:
    	// verify that the courseId exists, that the status is ENROLLMENT_OPEN and that userEmail exists,
    	// then add user to course if he isn't already included 
        // and return courseRepository.enrollInCourse(course)  
        
        return null;
    }

    @Override
    public Long closeGradeReports(Long courseId) {
    	
    	// TODO: add the code for the missing system operations here:
    	// verify that the courseId exists and that the status is ACTIVE, then change status
        // and return courseRepository.closeGradeReports(course)  
        
        return null;
    }

    @Override
    public Long closeCourse(Long courseId) {
        Course course = courseRepository
                .getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("The "+courseId+"th course does not exist!"));
        
        if (!course.getStatus().equals(CourseStatus.PENDING_CLOSUE)) {
            throw new IllegalArgumentException("The course " +courseId+"th is not in Pending Closue status!");
        }
        
        course.setStatus(CourseStatus.CLOSED);        
        
        restMicrocredentialsRepositoryUOC.createMicrocredentials(course);      
        
        return courseRepository.closeEnrollmentCourse(course);
    }
    
}
