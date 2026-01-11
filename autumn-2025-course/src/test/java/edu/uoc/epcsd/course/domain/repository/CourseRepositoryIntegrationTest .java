package edu.uoc.epcsd.course.domain.repository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import edu.uoc.epcsd.course.domain.Course;
import edu.uoc.epcsd.course.infrastructure.kafka.CourseMessage;

@SpringBootTest(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
  })
@ActiveProfiles("test")
@Transactional
class CourseRepositoryIntegrationTest {


    @MockBean
    private KafkaTemplate<String, CourseMessage> kafkaTemplate;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

   @BeforeEach
    void setup() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(true);
    }

    @Test
    void whenSavingCourse_thenFindCoursesReturnsIt() {
        // given
        Course course   = new Course();
        course.setTitle("Software Architecture");
        course.setDescription("SA course");
        course.setInstructor("instructor1@uoc.edu");
        course.setEnrollmentStartDate(Date.valueOf(LocalDate.now()));
        course.setEnrollmentEndDate(Date.valueOf(LocalDate.now().plusDays(10)));
        course.setMode("ONLINE");
        course.setPrice(100L);
        course.setObjectives("Learn architecture");
        course.setMethology("Theory and practice");
        course.setDuration(40L);
        course.setLanguage("EN");
        course.setLocation("Virtual");

        courseRepository.createCourse(course);

        // when
        List<Course> courses = courseRepository.findCourses();

        // then
        assertThat(courses)
                .isNotEmpty()
                .anyMatch(c -> c.getTitle().equals("Software Architecture"));
    }
}
