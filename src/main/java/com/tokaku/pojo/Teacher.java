package com.tokaku.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Teacher extends User {
    private String teacherId;//8位长度
    private String teacherName;
    private String telephone;
    private String teacherPassword;
    private String courseId;

    @Override
    public String getName() {
        return teacherName;
    }
}
