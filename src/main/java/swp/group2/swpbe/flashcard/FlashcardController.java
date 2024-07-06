package swp.group2.swpbe.flashcard;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp.group2.swpbe.AuthService;
import swp.group2.swpbe.constant.ResourceStatus;
import swp.group2.swpbe.constant.ReviewState;
import swp.group2.swpbe.flashcard.dto.FlashcardDTO;
import swp.group2.swpbe.flashcard.entities.Flashcard;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class FlashcardController {
    @Autowired
    FlashcardService flashcardService;
    @Autowired
    AuthService authService;

    @PostMapping("/flashcard/create")
    public Flashcard createFlashcard(@RequestBody FlashcardDTO body, @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        return flashcardService.create(body, userId);
    }

    @PutMapping("flashcard/update/{id}")
    public Flashcard updateFlashcard(@RequestBody FlashcardDTO body, @PathVariable String id,
            @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        return flashcardService.update(body, id, userId);
    }

    // get flashcard
    @GetMapping("/flashcards")
    public List<Flashcard> getAllFlashcards() {
        return flashcardService.getAllFlashCard();
    }

    @PostMapping("/flashcard/upload-review")
    public ResponseEntity<?> review(@RequestParam("flashcardId") String flashcardId,
            @RequestParam("review") String review,
            @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);

        flashcardService.uploadReviewFlashcard(flashcardId, userId, ReviewState.valueOf(review));
        return new ResponseEntity<>("upload review successfully", HttpStatus.OK);
    }

    @PutMapping("/flashcard/acitve")
    public ResponseEntity<?> setFlashcardToActive(@RequestParam("flashcardId") String flashcardId,
            @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        Flashcard flashcard = flashcardService.getFlashcardById(Integer.parseInt(flashcardId));

        if (flashcard == null) {
            return new ResponseEntity<>("Flashcard not found", HttpStatus.NOT_FOUND);
        }

        flashcardService.updateFlashcardState(flashcard, ResourceStatus.active);
        return new ResponseEntity<>("Flashcard state updated to pending successfully", HttpStatus.OK);
    }
}
