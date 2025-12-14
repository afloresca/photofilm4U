package edu.uoc.epcsd.course.infrastructure.kafka;

public final class KafkaConstants {

    private KafkaConstants() {}

        // topic courses
    public static final String COURSE_TOPIC = "course-topic";

       //commands
    public static final String COURSE_CREATED = "course_created";

    public static final String COURSE_UPDATED = "course_updated";  
    
    public static final String COURSE_DELETED = "course_deleted";

    public static final String COURSE_CLOSED = "course_closed";

    public static final String COURSE_ENROLLMENT_OPENED = "course_enrollment_opened";
    
    public static final String COURSE_ENROLLMENT_CLOSED = "course_enrollment_closed";

    public static final String COURSE_STUDENT_ENROLLED = "course_user_enrolled";

    public static final String COURSE_GRADE_REPORTS_CLOSED = "course_grade_reports_closed";


}