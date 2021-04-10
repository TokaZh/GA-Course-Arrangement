package com.tokaku.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Student extends User {
    private String studentId;
    private String studentName;
    private String studentPassword;
    private int grade;
    private int clazz;

    @Override
    public String getName() {
        return studentName;
    }
}
