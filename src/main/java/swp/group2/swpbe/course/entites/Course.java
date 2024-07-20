package swp.group2.swpbe.course.entites;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp.group2.swpbe.constant.ResourceStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "expert_id")
    private String expertId;
    private Double price;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    private String description;
    @Column(name = "banner_url")
    private String bannerUrl;
    private String name;
    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<Lesson> lessons;
    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<CourseOrder> orders;
    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<CourseReview> reviews;
    @Column(name = "topic_id")
    private String topicId;
    @Enumerated(EnumType.STRING)
    private ResourceStatus state;

    public Course(String expertId, Double price, String description, String bannerUrl, String name, String topicId,
            ResourceStatus state) {
        this.expertId = expertId;
        this.price = price;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.description = description;
        this.bannerUrl = bannerUrl;
        this.name = name;
        this.topicId = topicId;
        this.state = state;
    }

}
