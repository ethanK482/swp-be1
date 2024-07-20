package swp.group2.swpbe.course.entites;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "course_review")
@Getter
@Setter
@NoArgsConstructor
public class CourseReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    private String content;
    @Column(name = "created_at")
    private Date createdAt;
    private int rate;
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    public CourseReview(int userId, Course course, String content, int rate) {
        this.userId = userId;
        this.course = course;
        this.content = content;
        this.createdAt = new Date();
        this.rate = rate;
    }

}
