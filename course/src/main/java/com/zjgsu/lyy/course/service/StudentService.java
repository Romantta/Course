package com.zjgsu.lyy.course.service;

import com.zjgsu.lyy.course.model.Student;
import com.zjgsu.lyy.course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class StudentService {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Autowired
    private StudentRepository studentRepository;
    private EnrollmentService enrollmentService;

    // 使用构造器注入 + @Lazy 解决循环依赖
    @Autowired
    public StudentService(StudentRepository studentRepository, @Lazy EnrollmentService enrollmentService) {
        this.studentRepository = studentRepository;
        this.enrollmentService = enrollmentService;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Optional<Student> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        // 验证必填字段
        validateStudentFields(student);

        // 验证学号唯一性
        if (studentRepository.existsByStudentId(student.getStudentId())) {
            throw new RuntimeException("学号已存在: " + student.getStudentId());
        }

        // 验证邮箱格式
        if (!isValidEmail(student.getEmail())) {
            throw new RuntimeException("邮箱格式不正确: " + student.getEmail());
        }

        return studentRepository.save(student);
    }

    public Student updateStudent(String id, Student student) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("学生不存在: " + id));

        // 验证必填字段
        validateStudentFields(student);

        // 验证学号唯一性（排除自己）
        if (!existingStudent.getStudentId().equals(student.getStudentId())
                && studentRepository.existsByStudentId(student.getStudentId())) {
            throw new RuntimeException("学号已存在: " + student.getStudentId());
        }

        // 验证邮箱格式
        if (!isValidEmail(student.getEmail())) {
            throw new RuntimeException("邮箱格式不正确: " + student.getEmail());
        }

        student.setId(id);
        student.setCreatedAt(existingStudent.getCreatedAt()); // 保持创建时间不变
        return studentRepository.save(student);
    }

    public void deleteStudent(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("学生不存在: " + id));

        // 检查是否有选课记录
        if (enrollmentService.hasEnrollmentsByStudentId(id)) {
            throw new RuntimeException("无法删除：该学生存在选课记录");
        }

        studentRepository.deleteById(id);
    }

    private void validateStudentFields(Student student) {
        if (student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            throw new RuntimeException("学号不能为空");
        }
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new RuntimeException("姓名不能为空");
        }
        if (student.getMajor() == null || student.getMajor().trim().isEmpty()) {
            throw new RuntimeException("专业不能为空");
        }
        if (student.getGrade() == null) {
            throw new RuntimeException("入学年份不能为空");
        }
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new RuntimeException("邮箱不能为空");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}