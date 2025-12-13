package edu.uoc.epcsd.course.infrastructure.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CourseKafkaPublisher {

    private final KafkaTemplate<String, CourseMessage> kafkaTemplate;

    public CourseKafkaPublisher(KafkaTemplate<String, CourseMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @SuppressWarnings("null")
    public void publish(CourseMessage message) {
        kafkaTemplate.send(
            KafkaConstants.COURSE_TOPIC,
            message.getCourseId().toString(),
            message
        );
    }
}
