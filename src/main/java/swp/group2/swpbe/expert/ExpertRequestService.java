package swp.group2.swpbe.expert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import swp.group2.swpbe.CloudinaryService;
import swp.group2.swpbe.expert.entities.ExpertRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExpertRequestService {
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ExpertRequestRepository expertRequestRepository;

    public void saveRequest(String userId, MultipartFile file) throws IOException {
        ExpertRequest expertRequest = new ExpertRequest();
        expertRequest.setUserId(userId);
        expertRequest.setState("pending");
        expertRequest.setCreatedAt(new Date());
        Map data = this.cloudinaryService.upload(file);
        String url = (String) data.get("url");
        expertRequest.setCvUrl(url);
        expertRequestRepository.save(expertRequest);
    }

    public List<ExpertRequest> getAllExpert() {
        return expertRequestRepository.findAll();
    }
}
