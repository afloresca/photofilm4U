package edu.uoc.epcsd.notification.domain.service;

import edu.uoc.epcsd.notification.application.kafka.ProductMessage;
import edu.uoc.epcsd.notification.application.kafka.CourseMessage;
public interface NotificationService {
    void notifyProductAvailable(ProductMessage productMessage);
    void notifyCourseEvent(CourseMessage courseMessage);
}
