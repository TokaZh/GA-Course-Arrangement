package com.tokaku.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Student {
    private String studentId;//10位长度
    private String studentName;
    private String studentPassword;
    private int grade;
    private int clazz;
}
