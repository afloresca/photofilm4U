package edu.uoc.epcsd.course.infrastructure.kafka;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CourseMessage {

    private Long courseId;
    private String type;
    private LocalDateTime occurredOn;
    private String payload;
}
