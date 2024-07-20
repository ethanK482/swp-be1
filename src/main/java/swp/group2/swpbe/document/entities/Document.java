package swp.group2.swpbe.document.entities;

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
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "file_url")
    private String fileUrl;
    @Column(name = "created_at")
    private Date createdAt;
    private String description;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "topic_id")
    private String topicId;
    private String title;
    @Enumerated(EnumType.STRING)
    private ResourceStatus state;

    @OneToMany(mappedBy = "document")
    @JsonManagedReference
    private List<DocumentReview> reviews;

    public Document(String fileUrl, String title, String description, String userId, String topicId) {
        this.fileUrl = fileUrl;
        this.createdAt = new Date();
        this.description = description;
        this.userId = userId;
        this.topicId = topicId;
        this.title = title;
        this.state = ResourceStatus.pending;
    }

}
