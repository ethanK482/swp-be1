package swp.group2.swpbe.user.entities;

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
import swp.group2.swpbe.constant.UserRole;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sid;
    @Column(name = "full_name")
    private String fullName;
    private String email;
    private String password;
    private int state;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    private String about;
    private Date dob;
    private String gender;

    public User(String fullName, String email, String password, String avatarUrl, int state, String sid) {
        this.fullName = fullName;
        this.email = email;
        this.state = state;
        this.role = UserRole.USER;
        this.avatarUrl = avatarUrl;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.password = password;
        this.sid = sid;
        this.about = null;
        this.dob = null;
        this.gender = null;
    }

}