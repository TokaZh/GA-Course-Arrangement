package com.tokaku.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Grade {
    private int gradeId;
    private int majorId;
    private int classNum;
}
