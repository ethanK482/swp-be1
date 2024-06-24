package swp.group2.swpbe.topic;

import org.springframework.web.bind.annotation.RestController;

import swp.group2.swpbe.topic.enties.Topic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TopicController {
    @Autowired
    TopicService topicService;
    @GetMapping("topics")
    public List<Topic> getAllTopics() {
        return topicService.getAllTopic();
    }

}
