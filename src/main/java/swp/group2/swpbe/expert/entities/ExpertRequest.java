package swp.group2.swpbe.expert.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp.group2.swpbe.constant.ExpertRequestStatus;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "expert_request")
public class ExpertRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "cv_url")
    private String cvUrl;
    @Column(name = "created_at")
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private ExpertRequestStatus state;

    public ExpertRequest(int id, String userId, String cvUrl, Date createdAt, ExpertRequestStatus state) {
        this.id = id;
        this.userId = userId;
        this.cvUrl = cvUrl;
        this.createdAt = createdAt;
        this.state = state;
    }

}
