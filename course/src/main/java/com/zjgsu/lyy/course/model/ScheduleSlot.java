package com.zjgsu.lyy.course.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程时间安排实体类
 * 包含课程的上课时间、日期等信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSlot {
    private String dayOfWeek;           // 星期几，如 "MONDAY"
    private String startTime;           // 开始时间，格式 "HH:mm"
    private String endTime;             // 结束时间，格式 "HH:mm"
    private Integer expectedAttendance; // 预期出勤人数

    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public Integer getExpectedAttendance() {
        return expectedAttendance;
    }
    public void setExpectedAttendance(Integer expectedAttendance) {
        this.expectedAttendance = expectedAttendance;
    }

}