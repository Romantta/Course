package com.zjgsu.lyy.course.repository;

import com.zjgsu.lyy.course.model.Student;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StudentRepository {
    private final Map<String, Student> students = new ConcurrentHashMap<>();
    private final Map<String, Student> studentsByStudentId = new ConcurrentHashMap<>();

    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }

    public Optional<Student> findById(String id) {
        return Optional.ofNullable(students.get(id));
    }

    public Optional<Student> findByStudentId(String studentId) {
        return Optional.ofNullable(studentsByStudentId.get(studentId));
    }

    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(UUID.randomUUID().toString());
        }
        if (student.getCreatedAt() == null) {
            student.setCreatedAt(LocalDateTime.now());
        }
        students.put(student.getId(), student);
        studentsByStudentId.put(student.getStudentId(), student);
        return student;
    }

    public void deleteById(String id) {
        Student student = students.get(id);
        if (student != null) {
            studentsByStudentId.remove(student.getStudentId());
            students.remove(id);
        }
    }

    public boolean existsByStudentId(String studentId) {
        return studentsByStudentId.containsKey(studentId);
    }
}