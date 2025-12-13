package edu.uoc.epcsd.course.infrastructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic courseTopic() {
        return new NewTopic(KafkaConstants.COURSE_TOPIC, 1, (short) 1);
    }
}
