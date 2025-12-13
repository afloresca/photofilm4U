package edu.uoc.epcsd.course.application.rest;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.uoc.epcsd.course.application.rest.request.CourseRequest;
import edu.uoc.epcsd.course.domain.Course;
import edu.uoc.epcsd.course.domain.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/courses")
public class CourseRESTController {

    private final CourseService courseService;

    // use the corresponding mapping HTTP request annotation with the parameter: "/"
    // and create the method List<Course> findCourses()
    // which call the corresponding findCourses() method  
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Course> findCourses() {
        log.trace("findCourses");
        return courseService.findCourses();
    }

    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}"
    // and create the method ResponseEntity<Course> getCourseById(@PathVariable @NotNull Long courseId)
    // which call the corresponding getCourseById(courseId) method  
    @GetMapping("/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Course> getCourseById(@PathVariable @NotNull Long courseId) {
        log.trace("getCourseById");
        try {
          return courseService.getCourseById(courseId)
                .map(course -> ResponseEntity.ok().body(course))
                .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e + "", e);
        }
    }

    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}/students"
    // and create the method getEnrolledStudents(@RequestParam @NotNull Long courseId)
    // which call the corresponding courseService.getEnrolledStudents(courseId) method  
    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<String>> getEnrolledStudents(@PathVariable @NotNull Long courseId) 
    {
        log.trace("getEnrolledStudents");
        try {
            return courseService.getCourseById(courseId)
                .map(course -> ResponseEntity.ok().body(courseService.getEnrolledStudents(courseId)))
                .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e + "", e);
        }
    } 
    
    // use the corresponding mapping HTTP request annotation with the parameter: "/"
    // and create the method ResponseEntity<Long> createCourse(@RequestBody @Valid CourseRequest createCourseRequest)
    // which call the corresponding courseService.createCourse(Course.builder().instructor(createCourseRequest.getInstructor()),...) method  
    @PostMapping("/")
    public ResponseEntity<Long> createCourse(@RequestBody @Valid CourseRequest createCourseRequest) {
        log.trace("createCourse");
        try {
            Long createdCourseId = courseService.createCourse(
                    Course.builder()
                            .title(createCourseRequest.getTitle())
                            .description(createCourseRequest.getDescription())
                            .instructor(createCourseRequest.getInstructor())
                            .enrollmentStartDate(createCourseRequest.getEnrollmentStartDate())
                            .enrollmentEndDate(createCourseRequest.getEnrollmentEndDate())
                            .mode(createCourseRequest.getMode())
                            .price(createCourseRequest.getPrice())
                            .objectives(createCourseRequest.getObjectives())
                            .methology(createCourseRequest.getMethology())
                            .duration(createCourseRequest.getDuration())
                            .language(createCourseRequest.getLanguage())
                            .location(createCourseRequest.getLocation())
                            .build()
            );

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdCourseId)
                    .toUri();

            return ResponseEntity.created(uri).body(createdCourseId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e + "", e);
        }
    }

    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}"
    // and create the method ResponseEntity<Boolean> modifyCourseDetails(@PathVariable @NotNull Long courseId, @RequestBody @Valid CourseRequest updateCourseRequest)
    // which call the corresponding modifyCourseDetails(courseId, updateCourseRequest.getInstructor(),...) method  
    @PutMapping("/{courseId}")
    public ResponseEntity<Long> modifyCourseDetails(@PathVariable @NotNull Long courseId, @RequestBody @Valid CourseRequest updateCourseRequest) {
        log.trace("modifyCourseDetails");
        try {
            Long modifiedCourseId = courseService.modifyCourseDetails(
                    courseId,
                    updateCourseRequest.getInstructor(),
                    updateCourseRequest.getTitle(),
                    updateCourseRequest.getDescription(),
                    updateCourseRequest.getEnrollmentStartDate(),
                    updateCourseRequest.getEnrollmentEndDate(),
                    updateCourseRequest.getMode(),
                    updateCourseRequest.getPrice(),
                    updateCourseRequest.getObjectives(),
                    updateCourseRequest.getMethology(),
                    updateCourseRequest.getDuration(),
                    updateCourseRequest.getLanguage(),
                    updateCourseRequest.getLocation()
            );

            return new ResponseEntity<>(modifiedCourseId, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e + "", e);
        }
    }   

    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}/openEnrollment"
    // and create the method ResponseEntity<Boolean> openEnrollment(@PathVariable @NotNull Long courseId)
    // which call the corresponding openEnrollmentCourse(courseId) method  
    @PatchMapping("/{courseId}/openEnrollment")
    public ResponseEntity<Boolean> openEnrollment(@PathVariable @NotNull Long courseId) {
        log.trace("openEnrollment");
        try {
            log.trace("open Enrollment for Course number " +courseId);

            return courseService.openEnrollmentCourse(courseId) > 0 ?
                    new ResponseEntity<>(true, HttpStatus.OK) :
                    new ResponseEntity<>(false, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e + "", e);
        }
    }

    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}/closeEnrollment"
    // and create the method ResponseEntity<Boolean> closeEnrollment(@PathVariable @NotNull Long courseId)
    // which call the corresponding closeEnrollmentCourse(courseId) method
    @PatchMapping("/{courseId}/closeEnrollment")
    public ResponseEntity<Boolean> closeEnrollment(@PathVariable @NotNull Long courseId) {
        log.trace("closeEnrollment");
        try {
            log.trace("close Enrollment for Course number " +courseId);
            return courseService.closeEnrollmentCourse(courseId) > 0 ?
                    new ResponseEntity<>(true, HttpStatus.OK) :
                    new ResponseEntity<>(false, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e + "", e);
        }  
    }
   
    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}/{userEmail}"
    // and create the method ResponseEntity<Boolean> enrollInCourse(@PathVariable @NotNull Long courseId, @PathVariable @NotNull String userEmail)
    // which call the corresponding enrollInCourse method 
    @PostMapping("/{courseId}/{userEmail}")
    public ResponseEntity<Boolean> enrollInCourse(@PathVariable @NotNull Long courseId, @PathVariable @NotNull String userEmail) {
        log.trace("enrollInCourse");
        try {
             log.trace("enroll user " +userEmail+" in Course number " +courseId);
             return courseService.enrollInCourse(courseId, userEmail) > 0 ?
                     new ResponseEntity<>(true, HttpStatus.OK) :
                     new ResponseEntity<>(false, HttpStatus.OK);
         } catch (IllegalArgumentException e) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e+"", e);
         }     
    }       

    // use the corresponding mapping HTTP request annotation with the parameter: "/{courseId}/closeGradeReports"
    // and create the method ResponseEntity<Boolean> closeGradeReports(@PathVariable @NotNull Long courseId)
    // which call the corresponding closeGradeReports method  
    
    @PatchMapping("/{courseId}/closeCourse")
    public ResponseEntity<Boolean> closeCourse(@PathVariable @NotNull Long courseId) {
        log.trace("closeCourse");
        try {
			log.trace("close Course number " +courseId);
            return courseService.closeCourse(courseId) > 0 ?
                    new ResponseEntity<>(true, HttpStatus.OK) :
                    new ResponseEntity<>(false, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e+"", e);
        }
    }         
}
