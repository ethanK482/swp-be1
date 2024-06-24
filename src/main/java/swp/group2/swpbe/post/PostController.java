package swp.group2.swpbe.post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import swp.group2.swpbe.AuthService;
import swp.group2.swpbe.CloudinaryService;
import swp.group2.swpbe.post.entities.Post;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class PostController {
	@Autowired
	PostService postService;
    @Autowired
    CloudinaryService cloudinaryService;
     @Autowired
    AuthService authService;


	
    @PostMapping("/create-post")
    public ResponseEntity<?> upload(@RequestParam( name = "file", required = false) MultipartFile file, @RequestParam("content") String content, 
	@RequestHeader("Authorization") String token) 
	{
        String userId = authService.loginUser(token);
        String url = null;
        if(file != null){
            Map data = this.cloudinaryService.upload(file);
            url=  (String) data.get("url");
        }
       
        postService.createPost(url,content,userId);
        return new ResponseEntity<>(" Post successfully", HttpStatus.OK);
    }

    @PutMapping("/update-post/{id}")
	public ResponseEntity<?> updatePost(@PathVariable String id , @RequestParam(value= "file", required=false) MultipartFile file,
				 @RequestParam("content") String content, @RequestHeader("Authorization") String token)
	{
        String userId = authService.loginUser(token);
		String url =null;
		if(file !=null){
			Map data = this.cloudinaryService.upload(file);
			url= (String) data.get("url");
		}		
        postService.updatePost(id, url,content,userId);
        return ResponseEntity.ok("Update post successfully");
    }

	@DeleteMapping("/deletePost/{id}")
    public boolean deletePost(@PathVariable("id") Integer id,  @RequestHeader("Authorization") String token){
        String userId = authService.loginUser(token);
        return postService.deletePost(id);
    }


    @GetMapping("posts")
    public List<Post> getAllPost() {
        return postService.getAllPosts();
    }

}
