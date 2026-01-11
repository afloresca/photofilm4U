package edu.uoc.epcsd.course.domain.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import edu.uoc.epcsd.course.domain.Course;
import edu.uoc.epcsd.course.domain.repository.CourseRepository;
import edu.uoc.epcsd.course.domain.repository.RestMicrocredentialsRepositoryUOC;
import edu.uoc.epcsd.course.domain.repository.UserRepository;
import edu.uoc.epcsd.course.infrastructure.kafka.CourseMessage;

@ExtendWith(MockitoExtension.class)
class CourseServiceUnitTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestMicrocredentialsRepositoryUOC restMicrocredentialsRepositoryUOC;

    @Mock
    private KafkaTemplate<String, CourseMessage> courseKafkaTemplate;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void whenFindCourses_thenReturnCourseList() {
        // given
        Course course1 = privateCourse(1L, "SA", "Software Architecture");
        Course course2 = privateCourse(2L, "DS", "Distributed Systems");

        when(courseRepository.findCourses())
                .thenReturn(List.of(course1, course2));

        // when
        List<Course> result = courseService.findCourses();

        // then
        assertThat(result)
                .hasSize(2)
                .containsExactly(course1, course2);
    }

    @Test
    void whenGetCourseByIdWithValidId_thenReturnCourse() {
        // given
        Course course = privateCourse(1L, "SA", "Software Architecture");

        when(courseRepository.getCourseById(1L))
                .thenReturn(Optional.of(course));

        // when
        Optional<Course> result = courseService.getCourseById(1L);

        assertThat(result)
                     .isPresent()
                     .contains(course);
    }

    Course privateCourse(Long id, String title, String description) {
        Course course = new Course();
        course.setId(id);
        course.setDescription(description);
        course.setTitle(title);
        return course;
    }
}
