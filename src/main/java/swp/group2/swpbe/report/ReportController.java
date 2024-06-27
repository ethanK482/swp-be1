package swp.group2.swpbe.report;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import swp.group2.swpbe.AuthService;
import swp.group2.swpbe.JwtTokenProvider;
import swp.group2.swpbe.constant.ResourceType;
import swp.group2.swpbe.exception.ApiRequestException;
import swp.group2.swpbe.report.entities.Report;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ReportController {
    @Autowired
    ReportService reportService;
    @Autowired
    AuthService authService;

    @PostMapping("/upload-report")
    public ResponseEntity<?> upload(
            @RequestParam("resourceType") ResourceType resourceType, @RequestParam("resourceId") String resourceId,
            @RequestParam("reason") String reason,
            @RequestHeader("Authorization") String token) {

        String userId = authService.loginUser(token);
        reportService.uploadReport(userId, resourceType, resourceId, reason);

        return new ResponseEntity<>("upload report successfully", HttpStatus.OK);
    }

    @GetMapping("/reports")
    public List<Report> getAllReports(@RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        if (authService.isAdmin(userId) == false) {
            throw new ApiRequestException("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
        List<Report> reports = reportService.getAllReportsSortedByDate();
        return reports;
    }
}
