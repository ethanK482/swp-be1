package swp.group2.swpbe.course;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import swp.group2.swpbe.AuthService;
import swp.group2.swpbe.CloudinaryService;
import swp.group2.swpbe.Common;
import swp.group2.swpbe.course.dto.ReviewCourseDTO;
import swp.group2.swpbe.course.entites.Course;
import swp.group2.swpbe.course.entites.CourseOrder;
import swp.group2.swpbe.course.entites.Lesson;
import swp.group2.swpbe.course.response.ExpertCourseResponse;
import swp.group2.swpbe.exception.ApiRequestException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CourseController {
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    CourseService courseService;
    @Autowired
    AuthService authService;
    @Autowired
    CourseOrderRepository courseOrderRepository;

    @PostMapping("expert/course")
    public Course create(@RequestParam("files") MultipartFile[] files,
            @RequestParam("description") String description, @RequestParam("banner") MultipartFile banner,
            @RequestParam("name") String name, @RequestParam("price") Double price,
            @RequestParam("topic_id") String topic_id, @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        if (authService.isExpert(userId) == false) {
            throw new ApiRequestException("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
        Map data = this.cloudinaryService.upload(banner);
        String url = (String) data.get("url");
        Course course = courseService.createCourse(userId, price, description, name, url, topic_id);
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getOriginalFilename();
            String lesson = fileName.substring(0, fileName.length() - 4);
            String[] lessonHandleArr = lesson.split("-");
            Map lessonData = this.cloudinaryService.upload(files[i]);
            String lessonUrl = (String) lessonData.get("url");
            courseService.createLesson(course, lessonUrl, Integer.parseInt(lessonHandleArr[0].trim()),
                    lessonHandleArr[1]);
        }
        return course;
    }

    @PutMapping("expert/course/{id}")
    public Course updateCourse(@PathVariable String id,
            @RequestParam("description") String description,
            @RequestParam("name") String name, @RequestParam("price") Double price,
            @RequestParam("topicId") String topicId,
            @RequestParam(value = "banner", required = false) MultipartFile banner,
            @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        if (authService.isExpert(userId) == false) {
            throw new ApiRequestException("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
        String bannerUrl = null;
        if (banner != null) {
            Map bannerData = this.cloudinaryService.upload(banner);
            bannerUrl = (String) bannerData.get("url");
        }

        Course course = courseService.updateCourse(Integer.parseInt(id), userId, price, description, name,
                topicId, bannerUrl);
        return course;
    }

    @PutMapping("expert/lesson/{id}")
    public Lesson updateLesson(@PathVariable int id,
            @RequestParam("video") MultipartFile video,
            @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        if (authService.isExpert(userId) == false) {
            throw new ApiRequestException("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
        String fileName = video.getOriginalFilename();
        String lesson = fileName.substring(0, fileName.length() - 4);
        String[] lessonHandleArr = lesson.split("-");
        Map data = this.cloudinaryService.upload(video);
        String url = (String) data.get("url");
        return courseService.updateLesson(id, url, Integer.parseInt(lessonHandleArr[0]), lessonHandleArr[1]);
    }

    @GetMapping("expert/courses")
    public List<ExpertCourseResponse> getExpertCourses(
            @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        if (authService.isExpert(userId) == false) {
            throw new ApiRequestException("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
        List<Course> courses = courseService.getExpertCourse(userId);
        List<ExpertCourseResponse> coursesResponses = new ArrayList<ExpertCourseResponse>();
        for (int i = 0; i < courses.size(); i++) {
            Course courseI = courses.get(i);
            List<Lesson> sortedLessons = courseI.getLessons();
            sortedLessons.sort(Comparator.comparingInt(Lesson::getLesson_order));
            List<CourseOrder> orders = courseOrderRepository.findByCourseIdAndPaymentStatus(courseI.getId(), "paid");
            int totalOrder = orders.size();
            Double income = Common.calculateExpertAmount(courseI.getPrice()) * totalOrder;
            ExpertCourseResponse courseResponse = new ExpertCourseResponse(totalOrder, income, courseI.getExpertId(),
                    courseI.getPrice(), courseI.getDescription(), courseI.getBanner_url(), courseI.getName(),
                    courseI.getTopicId(), courseI.getState());
            courseResponse.setId(courseI.getId());
            courseResponse.setCreated_at(courseI.getCreated_at());
            courseResponse.setCreated_at(courseI.getUpdated_at());
            courseResponse.setLessons(sortedLessons);
            coursesResponses.add(courseResponse);

        }
        return coursesResponses;
    }

    @GetMapping("admin/courses")
    public List<Course> getAllCourses(
            @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        if (authService.isAdmin(userId) == false) {
            throw new ApiRequestException("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
        return courseService.getAllCourse();
    }

    @GetMapping("public/courses")
    public List<Course> getAllUserCourses() {
        List<Course> courses = courseService.getAllActiveCourse();

        for (Course course : courses) {
            List<Lesson> sortedLessons = course.getLessons();
            sortedLessons.sort(Comparator.comparingInt(Lesson::getLesson_order));
            for (int i = 0; i < sortedLessons.size(); i++) {
                if (i != 0) {
                    sortedLessons.get(i).setVideo_url("");
                }
            }
        }
        return courses;
    }

    @PutMapping("admin/course/{id}")
    public Course updateCourse(
            @PathVariable int id,
            @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        if (authService.isAdmin(userId) == false) {
            throw new ApiRequestException("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
        return courseService.acceptCourse(id);
    }

    @GetMapping("course/bought")
    public List<Course> getBoughtCourse(
            @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        List<CourseOrder> orders = courseService.getUserOrder(userId);
        List<Course> courses = new ArrayList<>();
        orders.forEach(order -> courses.add(order.getCourse()));
        return courses;
    }

    @PostMapping("course/review")
    public ResponseEntity<?> reviewCourses(
            @RequestBody ReviewCourseDTO body, @RequestHeader("Authorization") String token) {
        String userId = authService.loginUser(token);
        courseService.createReview(body, Integer.parseInt(userId));
        return new ResponseEntity<>("Review course successfully", HttpStatus.OK);
    }

    @PostMapping("user/buy-course")
    public String buyCourse(@RequestBody String entity) {

        return entity;
    }

}
