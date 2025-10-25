package com.zjgsu.lyy.course.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教师实体类
 * 包含教师的基本信息：ID、姓名、邮箱
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    private String id;      // 教师ID，如 "T001"
    private String name;    // 教师姓名
    private String email;   // 教师邮箱

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String Name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}