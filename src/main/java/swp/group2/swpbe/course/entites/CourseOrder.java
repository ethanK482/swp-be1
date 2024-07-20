package swp.group2.swpbe.course.entites;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp.group2.swpbe.constant.PaymentStatus;

@Entity(name = "course_order")
@Getter
@Setter
@NoArgsConstructor
public class CourseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "transaction_id")
    private String transactionId;
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    public CourseOrder(Course course, int userId, PaymentStatus paymentStatus, String transactionId) {
        this.course = course;
        this.userId = userId;
        this.paymentStatus = paymentStatus;
        this.createdAt = new Date();
        this.transactionId = transactionId;
    }

}
