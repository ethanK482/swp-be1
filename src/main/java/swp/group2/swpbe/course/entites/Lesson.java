package swp.group2.swpbe.course.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String video_url;
    private int lesson_order;
    private String name;
    @ManyToOne
    @JoinColumn(name = "course_id")
     @JsonBackReference
    private Course course;

    public Lesson() {
    }

    public Lesson(String video_url, int lesson_order, String name) {
        this.video_url = video_url;
        this.lesson_order = lesson_order;
        this.name = name;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo_url() {
        return this.video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getLesson_order() {
        return this.lesson_order;
    }

    public void setLesson_order(int lesson_order) {
        this.lesson_order = lesson_order;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
   

}
