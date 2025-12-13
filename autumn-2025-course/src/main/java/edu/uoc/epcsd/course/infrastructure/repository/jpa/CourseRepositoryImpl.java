package edu.uoc.epcsd.course.infrastructure.repository.jpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.uoc.epcsd.course.domain.Course;
import edu.uoc.epcsd.course.domain.exception.UserNotFoundException;
import edu.uoc.epcsd.course.domain.repository.CourseRepository;
import edu.uoc.epcsd.course.domain.repository.UserRepository;
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

        return null;  
	}

	@Override
	public Long openEnrollmentCourse(Course course) {
        
	    // TODO: add the code for the missing system operations here 
		// save CourseEntity and return your id

        return null; 
	} 

	@Override
	public Long closeEnrollmentCourse(Course course) {
        
	    // TODO: add the code for the missing system operations here 
		// save CourseEntity and return your id

        return null; 
	} 
	
	@Override
	public Long closeGradeReports(Course course) {
        
        CourseEntity courseEntity = jpaCourseRepository.findById(course.getId()).orElseThrow(IllegalArgumentException::new);       
        courseEntity.setStatus(course.getStatus());    
        
        return jpaCourseRepository.save(courseEntity).getId(); 
	}
	@Override
	public Long enrollInCourse(Course course) {
        
	    // TODO: add the code for the missing system operations here 
		// save CourseEntity and return your id

        return null; 
	} 
	    
}
