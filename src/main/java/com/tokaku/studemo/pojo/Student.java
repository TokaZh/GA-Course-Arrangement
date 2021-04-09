package com.tokaku.studemo.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Student extends User {
    private String sno;
    private String sname;
    private String password;
    private int grade;
    private int clazz;

    @Override
    public String getName() {
        return sname;
    }
}
