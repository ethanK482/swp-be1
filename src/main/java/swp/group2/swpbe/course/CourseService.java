package swp.group2.swpbe.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stripe.model.Review;

import swp.group2.swpbe.CloudinaryService;
import swp.group2.swpbe.constant.PaymentStatus;
import swp.group2.swpbe.course.dto.ReviewCourseDTO;
import swp.group2.swpbe.course.entites.Course;
import swp.group2.swpbe.course.entites.CourseOrder;
import swp.group2.swpbe.course.entites.CourseReview;
import swp.group2.swpbe.course.entites.Lesson;
import swp.group2.swpbe.course.response.StudentResponse;
import swp.group2.swpbe.exception.ApiRequestException;
import swp.group2.swpbe.user.UserRepository;
import swp.group2.swpbe.user.entities.User;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    CourseOrderRepository courseOrderRepository;
    @Autowired
    CourseReviewRepository courseReviewRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CloudinaryService cloudinaryService;

    public Course createCourse(String expert_id, Double price, String description, String name, String banner_url,
            String topic_id) {
        Course course = new Course(expert_id, price, description, banner_url, name, topic_id, "pending");
        return courseRepository.save(course);

    }

    public Course updateCourse(int course_id, String expert_id, Double price, String description, String name,
            String topic_id, String bannerUrl) {
        Course course = courseRepository.findById(course_id);
        if (course == null) {
            throw new ApiRequestException("Course not found", HttpStatus.BAD_REQUEST);
        }
        if (bannerUrl != null) {
            course.setBanner_url(bannerUrl);
        }
        course.setExpertId(expert_id);
        course.setPrice(price);
        course.setDescription(description);
        course.setName(name);
        course.setTopicId(topic_id);
        course.setUpdated_at(new Date());

        return courseRepository.save(course);

    }

    public Course updateCourseBanner(int course_id, String banner_url) {
        Course course = courseRepository.findById(course_id);
        if (course == null) {
            throw new ApiRequestException("Course not found", HttpStatus.BAD_REQUEST);
        }
        course.setBanner_url(banner_url);
        course.setUpdated_at(new Date());
        return courseRepository.save(course);

    }

    public void createLesson(Course course, String video_url, int order, String name) {
        Lesson lesson = new Lesson(video_url, order, name);
        lesson.setCourse(course);
        lessonRepository.save(lesson);
    }

    public Lesson updateLesson(int lessonId, String videoUrl, int order, String name) {
        Lesson lesson = lessonRepository.findById(lessonId);
        lesson.setName(name);
        lesson.setVideo_url(videoUrl);
        lesson.setLesson_order(order);
        return lessonRepository.save(lesson);
    }

    public List<Course> getExpertCourse(String expertId) {
        List<Course> courses = courseRepository.findByExpertId(expertId);
        return courses;
    }

    public List<Course> getAllCourse() {
        List<Course> courses = courseRepository.findAll();
        return courses;
    }

    public List<Course> getAllActiveCourse() {
        List<Course> courses = courseRepository.findByState("active");
        Collections.sort(courses, new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                double totalRating1 = o1.getReviews().stream()
                        .mapToDouble(CourseReview::getRate)
                        .sum();
                double totalRating2 = o2.getReviews().stream()
                        .mapToDouble(CourseReview::getRate)
                        .sum();
                return Double.compare(totalRating2, totalRating1);
            }
        });
        return courses;
    }

    public Course acceptCourse(int id) {
        Course course = courseRepository.findById(id);
        course.setState("active");
        return courseRepository.save(course);
    }

    public List<CourseOrder> getUserOrder(String userId) {
        return courseOrderRepository.findByUserIdAndPaymentStatus(Integer.parseInt(userId), PaymentStatus.paid);
    }

    public void createReview(ReviewCourseDTO body, int userId) {
        int courseId = body.getCourseId();
        Course course = courseRepository.findById(courseId);
        CourseReview reviewExist = courseReviewRepository.findByUserIdAndCourse(userId, course);
        if (reviewExist != null) {
            throw new ApiRequestException("You can't review 2 times", HttpStatus.BAD_REQUEST);
        }
        String content = body.getContent();
        int rate = body.getRate();
        CourseReview review = new CourseReview(userId, course, content, rate);
        courseReviewRepository.save(review);
    }

    public List<StudentResponse> getCourseStudent(int courseId, String expertId) {
        Course course = courseRepository.findByIdAndExpertId(courseId,
                expertId);
        if (course == null) {
            throw new ApiRequestException("Course not found", HttpStatus.BAD_REQUEST);
        }
        List<CourseOrder> orderList = courseOrderRepository.findByCourseIdAndPaymentStatus(courseId,
                PaymentStatus.paid);
        List<StudentResponse> studentList = new ArrayList<>();
        for (CourseOrder order : orderList) {
            int userId = order.getUserId();
            User user = userRepository.findById(userId);
            StudentResponse student = new StudentResponse(user.getFullName(), user.getAvatarUrl(), user.getId(),
                    order.getCreatedAt());
            studentList.add(student);
        }
        return studentList;
    }

    public Course getCourseById(int id) {
        return courseRepository.findById(id);
    }

    public void addCourseLesson(int courseId, MultipartFile[] files, String expertId) {
        Course course = courseRepository.findByIdAndExpertId(courseId, expertId);
        if (course == null) {
            throw new ApiRequestException("Course not found", HttpStatus.BAD_REQUEST);
        }
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getOriginalFilename();
            String lesson = fileName.substring(0, fileName.length() - 4);
            String[] lessonHandleArr = lesson.split("-");
            Map lessonData = this.cloudinaryService.upload(files[i]);
            String lessonUrl = (String) lessonData.get("url");
            this.createLesson(course, lessonUrl, Integer.parseInt(lessonHandleArr[0].trim()),
                    lessonHandleArr[1]);
        }
    }
}
