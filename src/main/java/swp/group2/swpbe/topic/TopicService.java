package swp.group2.swpbe.topic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import swp.group2.swpbe.topic.enties.Topic;
@Service
public class TopicService {
    @Autowired
    TopicRepository topicRepository;
    public List<Topic> getAllTopic(){
        return topicRepository.findAll();
    }
}
