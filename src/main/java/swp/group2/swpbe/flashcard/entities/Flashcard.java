package swp.group2.swpbe.flashcard.entities;

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

@Entity(name = "flashcards")
@Getter
@Setter
@NoArgsConstructor
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "topic_id")
    private String topicId;
    private String description;
    @OneToMany(mappedBy = "flashcards")
    @JsonManagedReference
    private List<FlashcardsQuestion> questions;
    @Enumerated(EnumType.STRING)
    private ResourceStatus state;

    @OneToMany(mappedBy = "flashcards")
    @JsonManagedReference
    private List<FlashcardReview> reviews;

    public Flashcard(String name, String userId, String topicId, String description) {
        this.name = name;
        this.userId = userId;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.topicId = topicId;
        this.description = description;
        this.state = ResourceStatus.pending;
    }

}
