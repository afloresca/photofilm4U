package edu.uoc.epcsd.course.domain;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;

class CourseUnitTest {

    @Test
    void whenCreatingNewCourse_thenStatusIsEnrollmentOpen() {
        // given
        Course course = new Course();
        course.setDescription("PhotoJazz Extreme");
        course.setTitle("Software Architecture");
        course.setInstructor("instructor1@uoc.edu");

        course.setEnrollmentStartDate(new Date(0));
        course.setEnrollmentEndDate(new Date(0));
        course.setMode("Face-to-face course");
        course.setPrice(1000L);
        course.setObjectives("DE Qualification");
        course.setMethology("methology 1");
        course.setDuration(120L);
        course.setLanguage("English");
        course.setLocation("London");
        course.setStatus(CourseStatus.ENROLLMENT_OPEN);

        assertThat(course.getStatus())
                .isEqualTo(CourseStatus.ENROLLMENT_OPEN);
    }
}