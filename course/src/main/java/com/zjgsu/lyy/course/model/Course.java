package com.zjgsu.lyy.course.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private String id;
    private String code;
    private String title;
    private Instructor instructor;
    private ScheduleSlot schedule;
    private Integer capacity;
    private Integer enrolled = 0; // 当前选课人数

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Instructor getInstructor() {
        return instructor;
    }
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
    public ScheduleSlot getSchedule() {
        return schedule;
    }
    public void setSchedule(ScheduleSlot schedule) {
        this.schedule = schedule;
    }
    public Integer getCapacity() {
        return capacity;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    public Integer getEnrolled() {
        return enrolled;
    }
    public void setEnrolled(Integer enrolled) {
        this.enrolled = enrolled;
    }

    // 检查是否还有空位
    public boolean hasAvailableSeats() {
        return enrolled < capacity;
    }
    //获取剩余空位数
    public int getAvailableSeats() {
        return capacity - enrolled;
    }


}