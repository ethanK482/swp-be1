package swp.group2.swpbe.flashcard.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardDTO {
    private String name;
    private String description;
    private String topicId;
    private List<FlashcardQuestionDTO> questions;

}
