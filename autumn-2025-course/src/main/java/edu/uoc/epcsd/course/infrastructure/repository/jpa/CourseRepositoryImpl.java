package edu.uoc.epcsd.course.infrastructure.repository.jpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.uoc.epcsd.course.domain.Course;
import edu.uoc.epcsd.course.domain.CourseStatus;
import edu.uoc.epcsd.course.domain.exception.UserNotFoundException;
import edu.uoc.epcsd.course.domain.repository.CourseRepository;
import edu.uoc.epcsd.course.domain.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseRepositoryImpl implements CourseRepository {

    private final SpringDataCourseRepository jpaCourseRepository;
    
    private final UserRepository userRepository;
    
    @Override
    public List<Course> findCourses() {
        return jpaCourseRepository.findAll().stream().map(CourseEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Course> getCourseById(Long courseId) {
        return jpaCourseRepository.getCourseById(courseId).map(CourseEntity::toDomain);
    }   

    @Override
    public Long createCourse(Course course) {
		
    	if (!userRepository.findUserByEmail(course.getInstructor())) {
            throw new UserNotFoundException(course.getInstructor());
        }
		// create CourseEntity, set instructor and status and return your id
		CourseEntity courseEntity = CourseEntity.fromDomain(course);

		if (courseEntity != null){
			return jpaCourseRepository.save(courseEntity).getId();
		}
        return null;
    }

	@Override
	public Long modifyCourseDetails(Course course) {
        
		// If instructor exists, set all course variables, save CourseEntity and return your id
		if (!userRepository.findUserByEmail(course.getInstructor())) {
            throw new UserNotFoundException(course.getInstructor());
        }

		CourseEntity courseEntity =  jpaCourseRepository.findById(course.getId()).orElseThrow(IllegalArgumentException::new);
	
		
		if (courseEntity != null){
			courseEntity.setInstructor(course.getInstructor());
			courseEntity.setEnrollment(course.getEnrollment());
			courseEntity.setTitle(course.getTitle());
			courseEntity.setDescription(course.getDescription());
			courseEntity.setEnrollmentStartDate(course.getEnrollmentStartDate());
			courseEntity.setEnrollmentEndDate(course.getEnrollmentEndDate());
			courseEntity.setMode(course.getMode());
			courseEntity.setPrice(course.getPrice());
			courseEntity.setObjectives(course.getObjectives());
			courseEntity.setMethology(course.getMethology());
			courseEntity.setDuration(course.getDuration());
			courseEntity.setLanguage(course.getLanguage());
			courseEntity.setLocation(course.getLocation());	
			return jpaCourseRepository.save(courseEntity).getId();
		}

        return null;  
	}

	@Override
	public Long openEnrollmentCourse(Course course) {
        
		// save CourseEntity and return your id
		CourseEntity courseEntity = jpaCourseRepository.findById(course.getId()).orElseThrow(IllegalArgumentException::new);

		if (courseEntity != null){
			courseEntity.setStatus(CourseStatus.ENROLLMENT_OPEN);
			return jpaCourseRepository.save(courseEntity).getId();
		}
        return null; 
	} 

	@Override
	public Long closeEnrollmentCourse(Course course) {
        

		// save CourseEntity and return your id
		CourseEntity courseEntity = jpaCourseRepository.findById(course.getId()).orElseThrow(IllegalArgumentException::new);

		if (courseEntity != null){
			courseEntity.setStatus(CourseStatus.PENDING_CLOSUE);
			return jpaCourseRepository.save(courseEntity).getId();
		}

        return null; 
	} 
	
	@Override
	public Long closeGradeReports(Course course) {
        
        CourseEntity courseEntity = jpaCourseRepository.findById(course.getId()).orElseThrow(IllegalArgumentException::new);  
		
		if (courseEntity != null){
        	courseEntity.setStatus(CourseStatus.CLOSED);            
       		return jpaCourseRepository.save(courseEntity).getId(); 
		}
		return null;
	}

	@Override
	public Long enrollInCourse(Course course) {
        
		// save CourseEntity and return your id
		CourseEntity courseEntity = jpaCourseRepository.findById(course.getId()).orElseThrow(IllegalArgumentException::new);
		if (courseEntity != null){
			courseEntity.setEnrollment(course.getEnrollment());
			return jpaCourseRepository.save(courseEntity).getId();
		}	

        return null; 
	} 
	    
}
