package swp.group2.swpbe.report.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp.group2.swpbe.constant.ResourceType;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType resourceType;

    @Column(name = "resource_id")
    private String resourceId;

    private String reason;

    @Column(name = "report_date")
    private Date reportDate;

    public Report(String userId, ResourceType resourceType, String resourceId, String reason) {
        this.userId = userId;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.reason = reason;
        this.reportDate = new Date();
    }

}
