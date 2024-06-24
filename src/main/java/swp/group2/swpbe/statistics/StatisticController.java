package swp.group2.swpbe.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
    public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/admin/statistics")
    public StatisticResponse getAdminStatistic() {
        return statisticService.getAdminStatistic();
    }
}
