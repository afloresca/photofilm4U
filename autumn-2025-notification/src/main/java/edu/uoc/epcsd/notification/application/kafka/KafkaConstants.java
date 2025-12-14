package edu.uoc.epcsd.notification.application.kafka;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class KafkaConstants {

    // misc
    public static final String SEPARATOR = ".";

    // topic items
    public static final String PRODUCT_TOPIC = "product";

    // commands
    public static final String UNIT_AVAILABLE = "unit_available";

    // topic courses
    public static final String COURSE_TOPIC = "course-topic";

    public static final String COURSE_CREATED = "course_created";

    public static final String COURSE_UPDATED = "course_updated";  
    
    public static final String COURSE_DELETED = "course_deleted";

    public static final String COURSE_CLOSED = "course_closed";

    public static final String COURSE_ENROLLMENT_OPENED = "course_enrollment_opened";
    
    public static final String COURSE_ENROLLMENT_CLOSED = "course_enrollment_closed";

    public static final String COURSE_STUDENT_ENROLLED = "course_user_enrolled";

    public static final String COURSE_GRADE_REPORTS_CLOSED = "course_grade_reports_closed";
}
