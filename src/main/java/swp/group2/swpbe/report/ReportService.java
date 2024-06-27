package swp.group2.swpbe.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import swp.group2.swpbe.constant.ResourceType;

import swp.group2.swpbe.report.entities.Report;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    public List<Report> getAllReportsSortedByDate() {
        //List<Report> reports =  reportRepository.findAll();
        return reportRepository.findAllByOrderByReportDateDesc();
    }

    public void uploadReport(String userId, ResourceType resourceType, String resourceId, String reason) {

        Report report = new Report(userId, resourceType, resourceId, reason);
        reportRepository.save(report);
    }
}
