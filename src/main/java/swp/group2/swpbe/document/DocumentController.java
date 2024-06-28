package swp.group2.swpbe.document;

import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import swp.group2.swpbe.AuthService;
import swp.group2.swpbe.CloudinaryService;
import swp.group2.swpbe.JwtTokenProvider;
import swp.group2.swpbe.constant.ReviewState;
import swp.group2.swpbe.document.entities.Document;

@RestController
public class DocumentController {
    @Autowired
    DocumentService documentService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    AuthService authService;

    @PostMapping("/upload-document")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
            @RequestParam("description") String description, @RequestParam("title") String title,
            @RequestParam("topicId") String topicId,
            @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        Map data = this.cloudinaryService.upload(file);
        String url = (String) data.get("url");
        documentService.uploadDocument(url, description, userId, topicId, title);
        return new ResponseEntity<>("upload document successfully", HttpStatus.OK);
    }

    @PostMapping("/upload-review")
    public ResponseEntity<?> review(@RequestParam("documentId") String documentId,
            @RequestParam("review") String review,
            @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        Document document = documentService.getDocumentById(documentId);
        documentService.uploadReview(document, ReviewState.valueOf(review), userId);
        return new ResponseEntity<>("upload review successfully", HttpStatus.OK);
    }

    @GetMapping("/document")
    public Document getDocument(@RequestBody Map body) {
        String documentId = (String) body.get("documentId");
        Document document = documentService.getDocumentById(documentId);
        return document;

    }

    @GetMapping("/document/all")
    public List<Document> getAllDocument() {
        List<Document> allDocuments = documentService.getAllDocuments();
        return allDocuments;
    }
}
