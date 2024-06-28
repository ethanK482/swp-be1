package swp.group2.swpbe.post.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import swp.group2.swpbe.course.entites.Lesson;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int id;
	private String file_url;
	@Column(name = "created_at")
	private Date createdAt;
	private Date updated_at;
	private String user_id;
	private String content;

	@OneToMany(mappedBy = "post")
	@JsonManagedReference
	private List<PostLike> likes;

	@OneToMany(mappedBy = "post")
	@JsonManagedReference
	private List<Comments> comments;

	public Post(String file_url, String user_id, String content) {
		this.file_url = file_url;
		this.createdAt = new Date();
		this.updated_at = new Date();
		this.user_id = user_id;
		this.content = content;
	}

	public Post() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFile_url() {
		return this.file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdated_at() {
		return this.updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public String getUser_id() {
		return this.user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
