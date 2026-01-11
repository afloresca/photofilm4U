package edu.uoc.epcsd.course.application.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.uoc.epcsd.course.domain.Course;
import edu.uoc.epcsd.course.domain.service.CourseService;

@WebMvcTest(CourseRESTController.class)
class CourseRESTControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void whenFindCourses_thenReturnCourses() throws Exception {
        // given
        Course course1 = privateCourse(1L, "SA", "Software Architecture");
        Course course2 = privateCourse(2L, "DS", "Distributed Systems");

        when(courseService.findCourses())
                .thenReturn(List.of(course1, course2));

        // when & then
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("SA"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("DS"));
    }

    Course privateCourse(Long id, String instructor, String title) {
        Course course = new Course();
        course.setId(id);
        course.setInstructor(instructor);
        course.setTitle(title);
        return course;
    }
}
