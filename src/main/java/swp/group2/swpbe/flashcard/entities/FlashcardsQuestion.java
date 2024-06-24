package swp.group2.swpbe.flashcard.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "flashcards_question")
public class FlashcardsQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String question;
    private String answer;
    @ManyToOne
    @JoinColumn(name="flashcards_id")
    @JsonBackReference
    private Flashcard flashcards;

 
    public FlashcardsQuestion(Flashcard flashcards, String question, String answer) {
        this.flashcards = flashcards;
        this.question = question;
        this.answer = answer;
    }
    public FlashcardsQuestion() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Flashcard getFlashcards() {
        return this.flashcards;
    }

    public void setFlashcards(Flashcard flashcards) {
        this.flashcards = flashcards;
    }
   
   
}
