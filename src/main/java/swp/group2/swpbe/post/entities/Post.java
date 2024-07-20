package swp.group2.swpbe.post.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "file_url")
	private String fileUrl;
	@Column(name = "created_at")
	private Date createdAt;
	@Column(name = "updated_at")
	private Date updatedAt;
	@Column(name = "user_id")
	private String userId;
	private String content;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	@JsonManagedReference
	private List<PostLike> likes;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	@JsonManagedReference
	private List<Comments> comments;

	public Post(String fileUrl, String userId, String content) {
		this.fileUrl = fileUrl;
		this.createdAt = new Date();
		this.updatedAt = new Date();
		this.userId = userId;
		this.content = content;
	}

}
