package edu.uoc.epcsd.notification.application.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableKafka
@Configuration
class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // Configuración común
    private Map<String, Object> getCommonConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // Recomendado
        return props;
    }

    @Bean
    public ConsumerFactory<String, ProductMessage> productMessageConsumerFactory() {
        Map<String, Object> props = getCommonConfig();

        // Configurar ErrorHandlingDeserializer
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
                 ErrorHandlingDeserializer.class);
        
        // Crear JsonDeserializer configurado
        JsonDeserializer<ProductMessage> productDeserializer = 
            new JsonDeserializer<>(ProductMessage.class);
        
        // Configuración del JsonDeserializer
        productDeserializer.setRemoveTypeHeaders(false);
        productDeserializer.addTrustedPackages("*");
        productDeserializer.setUseTypeMapperForKey(true);
        productDeserializer.ignoreTypeHeaders(); 

        return new DefaultKafkaConsumerFactory<>(
            props,
            new StringDeserializer(),
            new ErrorHandlingDeserializer<>(productDeserializer)
        );
    }
    
    @Bean
    public ConsumerFactory<String, CourseMessage> courseMessageConsumerFactory() {
        Map<String, Object> props = getCommonConfig();
        
        // Configurar ErrorHandlingDeserializer
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
                 ErrorHandlingDeserializer.class);
        
        // Crear JsonDeserializer para CourseMessage
        JsonDeserializer<CourseMessage> courseDeserializer = 
            new JsonDeserializer<>(CourseMessage.class);
        
        // Configuración del JsonDeserializer
        courseDeserializer.setRemoveTypeHeaders(false);
        courseDeserializer.addTrustedPackages("*");
        courseDeserializer.setUseTypeMapperForKey(true);
        courseDeserializer.ignoreTypeHeaders(); 

        return new DefaultKafkaConsumerFactory<>(
            props,
            new StringDeserializer(),
            new ErrorHandlingDeserializer<>(courseDeserializer)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProductMessage> 
        productKafkaListenerContainerFactory() {
        
        ConcurrentKafkaListenerContainerFactory<String, ProductMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(productMessageConsumerFactory());
        
        // Añadir error handler si es necesario
        factory.setCommonErrorHandler(new DefaultErrorHandler(
            (record, exception) -> {
                log.warn("Corrupted message skipped in product: {}", record.topic());
            },
            new FixedBackOff(0L, 0)
        ));
        
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CourseMessage> 
        courseKafkaListenerContainerFactory() {
        
        ConcurrentKafkaListenerContainerFactory<String, CourseMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(courseMessageConsumerFactory());
        
        // Añadir error handler para CourseMessage
        factory.setCommonErrorHandler(new DefaultErrorHandler(
            (record, exception) -> {
                log.warn("Corrupted Message skipped in course: {}", record.topic());
            },
            new FixedBackOff(0L, 0)
        ));
        
        return factory;
    }
}