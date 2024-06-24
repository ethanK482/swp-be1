package swp.group2.swpbe.course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import swp.group2.swpbe.course.entites.CourseOrder;

public interface CourseOrderRepository extends JpaRepository<CourseOrder, Integer>{
    List<CourseOrder> findByCourseIdAndPaymentStatus(int courseId, String paymentStatus);
    List<CourseOrder> findByUserIdAndPaymentStatus(int userId, String paymentStatus);
    CourseOrder findByCourseIdAndUserId(int courseId, int userId);
}
