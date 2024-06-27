package swp.group2.swpbe.post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.group2.swpbe.post.entities.Comments;
import swp.group2.swpbe.post.entities.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Integer> {
	Comments findById(int id);
	Comments findByPostAndUserId(Post post, String userId);
	Comments findByPost(Post post);
}
