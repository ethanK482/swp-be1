package swp.group2.swpbe.course.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp.group2.swpbe.constant.ResourceStatus;
import swp.group2.swpbe.course.entites.Course;

@Getter
@Setter
@NoArgsConstructor
public class ExpertCourseResponse extends Course {
    private int totalUserBuy;
    private double income;

    public ExpertCourseResponse(int totalUserBuy, double income, String expertId, Double price, String description,
            String bannerUrl, String name, String topicId, ResourceStatus state) {
        super(expertId, price, description, bannerUrl, name, topicId, state);
        this.totalUserBuy = totalUserBuy;
        this.income = income;
    }

}
