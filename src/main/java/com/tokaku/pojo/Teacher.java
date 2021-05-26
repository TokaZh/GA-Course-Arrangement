package com.tokaku.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private String teacherId;//8位长度
    private String teacherName;
    private String telephone;
    private String teacherPassword;
    private String courseId;
}
