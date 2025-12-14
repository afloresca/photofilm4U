package edu.uoc.epcsd.notification.domain.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.uoc.epcsd.notification.application.kafka.CourseMessage;
import edu.uoc.epcsd.notification.application.kafka.KafkaConstants;
import edu.uoc.epcsd.notification.application.kafka.ProductMessage;
import edu.uoc.epcsd.notification.application.rest.dtos.GetProductResponse;
import edu.uoc.epcsd.notification.application.rest.dtos.GetUserResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${userService.getUsersToAlert.url}")
    private String userServiceUrl;

    @Value("${productService.getProductDetails.url}")
    private String productServiceUrl;

    @Override
    public void notifyProductAvailable(ProductMessage productMessage) {

        GetProductResponse product = new RestTemplate().getForEntity(productServiceUrl, GetProductResponse.class, productMessage.getProductId()).getBody();

        GetUserResponse[] usersToAlert = new RestTemplate().getForEntity(userServiceUrl, GetUserResponse[].class, productMessage.getProductId(), LocalDate.now()).getBody();

        for (GetUserResponse user : usersToAlert) {
            log.info("Sending an email to user " + user.getFullName() + " at \"" + user.getEmail() + "\" to notify new units available on product \"" + product.getName() + "\".");
        }
    }

        // Implementation for course event notification
    @Override
    public void notifyCourseEvent(CourseMessage courseMessage) {   
        
        switch (courseMessage.getType()) {
         case KafkaConstants.COURSE_GRADE_REPORTS_CLOSED:
            log.info(
                "MOCK EMAIL: Grade reports have been closed for course {}",
                courseMessage.getCourseId()
            );
            break;

        case KafkaConstants.COURSE_ENROLLMENT_OPENED:
            log.info(
                "MOCK EMAIL: Enrollment has been opened for course {}",
                courseMessage.getCourseId()
            );
            break;
            
        case KafkaConstants.COURSE_ENROLLMENT_CLOSED:
            log.info(
                "MOCK EMAIL: Enrollment has been closed for course {}",
                courseMessage.getCourseId()
            ); 
            break;

        case KafkaConstants.COURSE_STUDENT_ENROLLED:
            log.info(
                "MOCK EMAIL: {} has been enrolled in course {}",
                courseMessage.getPayload(),
                courseMessage.getCourseId()
            );
            break;

        default:
            log.warn(
                "Something happened on the way to heaven (unknown event): {} with payload {}",
                courseMessage.getType(),
                courseMessage.getPayload()
            );           
        }

    }
}
