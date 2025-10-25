package com.zjgsu.lyy.course.repository;

import com.zjgsu.lyy.course.model.Course;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CourseRepository {
    private final Map<String, Course> courses = new ConcurrentHashMap<>();

    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }

    public Optional<Course> findById(String id) {
        return Optional.ofNullable(courses.get(id));
    }

    public Course save(Course course) {
        if (course.getId() == null) {
            course.setId(UUID.randomUUID().toString());
        }
        courses.put(course.getId(), course);
        return course;
    }

    public void deleteById(String id) {
        courses.remove(id);
    }

    public Optional<Course> findByCode(String code) {
        return courses.values().stream()
                .filter(course -> course.getCode().equals(code))
                .findFirst();
    }
}