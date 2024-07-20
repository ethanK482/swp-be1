package swp.group2.swpbe.course.response;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentResponse {
    private String fullName;
    private String avatarUrl;
    private Date joinAt;
    private int userId;

    public StudentResponse(String fullName, String avatarUrl, int userId, Date joinAt) {
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.userId = userId;
        this.joinAt = joinAt;
    }

}
