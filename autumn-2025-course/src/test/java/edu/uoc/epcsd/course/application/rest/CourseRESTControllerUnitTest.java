package edu.uoc.epcsd.course.application.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        mockMvc.perform(get("/courses/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("SA"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("DS"));
    }

    Course privateCourse(Long id,  String title, String description) {
        return Course.builder()
                .id(id)
                .title(title)
                .description(description) 
                .build();
    }
}
