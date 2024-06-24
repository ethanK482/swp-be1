package swp.group2.swpbe.course.entites;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "expert_id")
    private String expertId;
    private Double price;
    private Date created_at;
    private Date updated_at;
    private String description;
    private String banner_url;
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
    private String state;

    public Course() {
    }

    public Course(String expertId, Double price, String description, String banner_url, String name, String topicId, String state) {
        this.expertId = expertId;
        this.price = price;
        this.created_at = new Date();
        this.updated_at = new Date();
        this.description = description;
        this.banner_url = banner_url;
        this.name = name;
        this.topicId = topicId;
        this.state = state;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpertId() {
        return this.expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBanner_url() {
        return this.banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lesson> getLessons() {
        return this.lessons;
    }

    public void setLessons(List<Lesson> object) {
        this.lessons = object;
    }

    public String getTopicId() {
        return this.topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<CourseOrder> getOrders() {
        return this.orders;
    }

    public void setOrders(List<CourseOrder> orders) {
        this.orders = orders;
    }

    public List<CourseReview> getReviews() {
        return this.reviews;
    }

    public void setReviews(List<CourseReview> reviews) {
        this.reviews = reviews;
    }


  
}
