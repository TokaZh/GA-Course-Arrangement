package com.tokaku.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private int courseId;
    private String courseName;
    private int type;//决定教室类型
    private int score;//用作权重
    private int time;//学期学时
    private int majorId;//专业
    private int grade;
//    private int step;


    public String getCourseId() {
        return String.valueOf(courseId);
    }
}
