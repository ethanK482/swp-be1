package swp.group2.swpbe.flashcard.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp.group2.swpbe.constant.ReviewState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity(name = "flashcards_review")
@Getter
@Setter
@NoArgsConstructor
public class FlashcardReview {
    @Id
    private int id;
    @Enumerated(EnumType.STRING)
    private ReviewState state;
    @Column(name = "user_id")
    private String userId;
    @ManyToOne
    @JoinColumn(name = "flashcards_id")
    @JsonBackReference
    private Flashcard flashcards;

    public FlashcardReview(Flashcard flashcards, String userId, ReviewState state) {
        this.state = state;
        this.userId = userId;
        this.flashcards = flashcards;
    }

}
