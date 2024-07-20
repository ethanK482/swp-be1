package swp.group2.swpbe.flashcard.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "flashcards_question")
@Getter
@Setter
@NoArgsConstructor
public class FlashcardsQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String question;
    private String answer;
    @ManyToOne
    @JoinColumn(name = "flashcards_id")
    @JsonBackReference
    private Flashcard flashcards;

    public FlashcardsQuestion(Flashcard flashcards, String question, String answer) {
        this.flashcards = flashcards;
        this.question = question;
        this.answer = answer;
    }

}
