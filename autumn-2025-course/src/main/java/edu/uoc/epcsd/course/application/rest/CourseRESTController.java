package edu.uoc.epcsd.course.application.rest;

import edu.uoc.epcsd.course.application.rest.request.CourseRequest;
import edu.uoc.epcsd.course.domain.Course;
import edu.uoc.epcsd.course.domain.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/courses")
public class CourseRESTController {

    private final CourseService courseService;

    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/"
    // and create the method List<Course> findCourses()
    // which call the corresponding findCourses() method  
  
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}"
    // and create the method ResponseEntity<Course> getCourseById(@PathVariable @NotNull Long courseId)
    // which call the corresponding getCourseById(courseId) method  

    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}/students"
    // and create the method getEnrolledStudents(@RequestParam @NotNull Long courseId)
    // which call the corresponding courseService.getEnrolledStudents(courseId) method  
    
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/"
    // and create the method ResponseEntity<Long> createCourse(@RequestBody @Valid CourseRequest createCourseRequest)
    // which call the corresponding courseService.createCourse(Course.builder().instructor(createCourseRequest.getInstructor()),...) method  

    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}"
    // and create the method ResponseEntity<Boolean> modifyCourseDetails(@PathVariable @NotNull Long courseId, @RequestBody @Valid CourseRequest updateCourseRequest)
    // which call the corresponding modifyCourseDetails(courseId, updateCourseRequest.getInstructor(),...) method  

    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}/openEnrollment"
    // and create the method ResponseEntity<Boolean> openEnrollment(@PathVariable @NotNull Long courseId)
    // which call the corresponding openEnrollmentCourse(courseId) method  

    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}/closeEnrollment"
    // and create the method ResponseEntity<Boolean> closeEnrollment(@PathVariable @NotNull Long courseId)
    // which call the corresponding closeEnrollmentCourse(courseId) method  
   
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}/{userEmail}"
    // and create the method ResponseEntity<Boolean> enrollInCourse(@PathVariable @NotNull Long courseId, @PathVariable @NotNull String userEmail)
    // which call the corresponding enrollInCourse method  

    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}/closeGradeReports"
    // and create the method ResponseEntity<Boolean> closeGradeReports(@PathVariable @NotNull Long courseId)
    // which call the corresponding closeGradeReports method  
    
    @PatchMapping("/{courseId}/closeCourse")
    public ResponseEntity<Boolean> closeCourse(@PathVariable @NotNull Long courseId) {
        log.trace("closeCourse");
        try {
			log.trace("close Course number " +courseId);
            courseService.closeCourse(courseId);
                                
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e+"", e);
        }
    }         
}
