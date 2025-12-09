package edu.uoc.epcsd.course.infrastructure.repository.jpa;

import edu.uoc.epcsd.course.domain.CourseStatus;
import edu.uoc.epcsd.course.domain.Course;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import javax.persistence.*;

@Entity(name = "course")
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity implements DomainTranslatable<Course> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "instructor", nullable = false)
    private String instructor;
    
    @Column(name = "enrollment", nullable = false)
    @ElementCollection(targetClass=String.class)
    private List<String> enrollment = new ArrayList<>();
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "enrollmentstartdate", nullable = false)
    private Date enrollmentStartDate;
    
    @Column(name = "enrollmentenddate", nullable = false)
    private Date enrollmentEndDate;

    @Column(name = "mode", nullable = false)
    private String mode;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "objectives", nullable = false)
    private String objectives;

    @Column(name = "methology", nullable = false)
    private String methology;

    @Column(name = "duration", nullable = false)
    private Long duration;

    @Column(name = "language", nullable = false)
    private String language;
    
    @Column(name = "location", nullable = false)
    private String location;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
   
    @Builder.Default
    private CourseStatus status = CourseStatus.DRAFT;

    
    public static CourseEntity fromDomain(Course course) {
        if (course == null) {
            return null;
        }

        return CourseEntity.builder()
                .id(course.getId())
                .instructor(course.getInstructor())                
                .enrollment(course.getEnrollment())                
                .title(course.getTitle())
                .description(course.getDescription())
                .enrollmentStartDate(course.getEnrollmentStartDate())
                .enrollmentEndDate(course.getEnrollmentEndDate())
                .mode(course.getMode())
                .price(course.getPrice())
                .objectives(course.getObjectives())
                .methology(course.getMethology())
                .duration(course.getDuration())
                .language(course.getLanguage())
                .location(course.getLocation())
                .status(course.getStatus())
                .build();
    }

    @Override
    public Course toDomain() {
        return Course.builder()
                .id(this.getId())
                .instructor(this.getInstructor())
                .enrollment(this.getEnrollment())
                .title(this.getTitle())
                .description(this.getDescription())
                .enrollmentStartDate(this.getEnrollmentStartDate())
                .enrollmentEndDate(this.getEnrollmentEndDate())
                .mode(this.getMode())
                .price(this.getPrice())
                .objectives(this.getObjectives())
                .methology(this.getMethology())
                .duration(this.getDuration())
                .language(this.getLanguage())
                .location(this.getLocation())                
                .status(this.getStatus())
                .build();
    }
}
