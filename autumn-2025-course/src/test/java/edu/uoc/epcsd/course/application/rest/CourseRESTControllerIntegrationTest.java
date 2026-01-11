package edu.uoc.epcsd.course.application.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import edu.uoc.epcsd.course.application.rest.request.CourseRequest;
import edu.uoc.epcsd.course.domain.Course;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                properties = {
                    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
                    }
)
@ActiveProfiles("test")
class CourseRESTControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenCreatingCourse_thenItCanBeRetrievedViaHttp() {
        // given
        CourseRequest request = new CourseRequest(
                "teacher@uoc.edu",
                "Software Architecture",
                "Photo Jazz hiphop",
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(10)),
                "ONLINE",
                100L,
                "Learn architecture",
                "Theory and practice",
                40L,
                "EN",
                "Virtual"
        );

        // when: create course
        ResponseEntity<Void> postResponse =
                restTemplate.postForEntity("/courses/", request, Void.class);

        // then: creation OK
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = postResponse.getHeaders().getLocation();
        assertThat(location).isNotNull();

        // when: retrieve courses
        ResponseEntity<Course[]> getResponse =
                restTemplate.getForEntity("/courses/", Course[].class);

        // then
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody())
                .isNotNull()
                .anyMatch(c -> "Software Architecture".equals(c.getTitle()));
    }
}
