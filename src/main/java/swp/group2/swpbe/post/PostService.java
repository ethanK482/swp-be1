package swp.group2.swpbe.post;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import swp.group2.swpbe.exception.ApiRequestException;
import swp.group2.swpbe.post.entities.Post;

@Service
public class PostService {
	@Autowired
    PostRepository postRepository;

	public void createPost(String url, String content, String userId){
		Post post = new Post( url, userId, content);
		postRepository.save(post);
	}
	
	public void updatePost(String id, String url, String content, String userId) {
        Post post = postRepository.findById(Integer.parseInt(id) );       
		if (post == null) {
			throw new ApiRequestException("Post not found", HttpStatus.BAD_REQUEST);
		}		
		if (!post.getUser_id().equals(userId)  ) {
			throw new ApiRequestException("Unauthorized user", HttpStatus.UNAUTHORIZED);
		}
		post.setUpdated_at(new Date());
		post.setContent(content);
	
		if(url != null )    post.setFile_url(url);		
        postRepository.save(post);

    }

	public boolean deletePost(int id) {
        if(id >=1  ){
            Post post= postRepository.findById(id);
            if(post!=null){
                postRepository.delete(post);
                return true;
            }
        }
        return false;
    }

	public List<Post> getAllPosts(){
		return postRepository.findAllByOrderByCreatedAtDesc();
	}
		
    }

