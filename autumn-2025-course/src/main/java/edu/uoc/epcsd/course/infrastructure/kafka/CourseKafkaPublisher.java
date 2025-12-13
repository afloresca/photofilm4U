package edu.uoc.epcsd.course.infrastructure.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CourseKafkaPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CourseKafkaPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(CourseMessage message) {
        kafkaTemplate.send(
            KafkaConstants.COURSE_TOPIC,
            message.getCourseId().toString(),
            message
        );
    }
}
