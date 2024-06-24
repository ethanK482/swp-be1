package swp.group2.swpbe.user.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private int id;
    private String sid;
    @Column(name = "full_name")
    private String fullName;
    private String email;
    private String password;
    @Column(name = "is_verify_email")
    private int isVerifyEmail;
    private String role;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    private String about;
    private Date dob;
    private String gender;

    public User(String fullName, String email, String password, String avatarUrl, int isVerifyEmail, String sid) {
        this.fullName = fullName;
        this.email = email;
        this.isVerifyEmail = isVerifyEmail;
        this.role = "user";
        this.avatarUrl = avatarUrl;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.password = password;
        this.sid = sid;
        this.about = null;
        this.dob=null;
        this.gender=null;
    }

    public User() {
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSid() {
        return this.sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsVerifyEmail() {
        return this.isVerifyEmail;
    }

    public void setIsVerifyEmail(int isVerifyEmail) {
        this.isVerifyEmail = isVerifyEmail;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAbout() {
        return this.about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Date getDob() {
        return this.dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
   
}