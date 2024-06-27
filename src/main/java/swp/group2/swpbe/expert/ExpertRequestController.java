package swp.group2.swpbe.expert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import swp.group2.swpbe.AuthService;

import swp.group2.swpbe.exception.ApiRequestException;
import swp.group2.swpbe.expert.entities.ExpertRequest;
import swp.group2.swpbe.user.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ExpertRequestController {
    @Autowired
    private ExpertRequestService expertRequestService;
    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;

    @PostMapping("expert-requests")
    public ResponseEntity<?> createExpertRequest(
            @RequestHeader("Authorization") String token,
            @RequestParam("cv") MultipartFile file) throws IOException {
        String userId = authService.loginUser(token);
        boolean isAllowRequest = userService.getUserLegit(userId) > 200;
        if (isAllowRequest) {
            expertRequestService.saveRequest(userId, file);
        } else {
            throw new ApiRequestException("your legit is not enough to be expert", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("request sent successfully", HttpStatus.OK);
    }

    @GetMapping("/getexpert")
    public List<ExpertRequest> getExpertRequest(@RequestBody Map body) {
        List<ExpertRequest> expertRequest = expertRequestService.getAllExpert();
        return expertRequest;

    }
}
