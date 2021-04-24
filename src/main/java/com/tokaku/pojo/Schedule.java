package com.tokaku.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Schedule {
    private int scheduleId;
    private int courseId;
    private int teacherId;
    private int classId;
    private int timePart;
}
