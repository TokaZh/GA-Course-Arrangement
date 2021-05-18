package com.tokaku.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Grade {
    private String gradeId;
    private String majorId;
    private int grade;
    private int classNum;
}
