package com.zjgsu.lyy.course.service;

import com.zjgsu.lyy.course.model.Course;
import com.zjgsu.lyy.course.model.Enrollment;
import com.zjgsu.lyy.course.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private EnrollmentRepository enrollmentRepository;
    private CourseService courseService;
    private StudentService studentService;

    // 使用构造器注入
    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             CourseService courseService,
                             StudentService studentService) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment enrollStudent(String courseId, String studentId) {
        // 验证课程存在
        courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("课程不存在: " + courseId));

        // 验证学生存在
        studentService.getStudentById(studentId)
                .orElseThrow(() -> new RuntimeException("学生不存在: " + studentId));

        // 检查是否已选课
        if (enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)) {
            throw new RuntimeException("学生已选过该课程");
        }

        // 检查课程容量
        Course course = courseService.getCourseById(courseId).get();
        if (!course.hasAvailableSeats()) {
            throw new RuntimeException("课程容量已满");
        }

        // 创建选课记录
        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(courseId);
        enrollment.setStudentId(studentId);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // 更新课程选课人数
        courseService.incrementEnrolledCount(courseId);

        return savedEnrollment;
    }

    public void cancelEnrollment(String enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("选课记录不存在: " + enrollmentId));

        String courseId = enrollment.getCourseId();
        enrollmentRepository.deleteById(enrollmentId);

        // 更新课程选课人数
        courseService.decrementEnrolledCount(courseId);
    }

    public List<Enrollment> getEnrollmentsByCourseId(String courseId) {
        // 验证课程存在
        courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("课程不存在: " + courseId));

        return enrollmentRepository.findByCourseId(courseId);
    }

    public List<Enrollment> getEnrollmentsByStudentId(String studentId) {
        // 验证学生存在
        studentService.getStudentById(studentId)
                .orElseThrow(() -> new RuntimeException("学生不存在: " + studentId));

        return enrollmentRepository.findByStudentId(studentId);
    }

    public boolean hasEnrollmentsByStudentId(String studentId) {
        return enrollmentRepository.countByStudentId(studentId) > 0;
    }
}