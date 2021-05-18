package com.tokaku.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Schedule {
    //id自动生成
    private String scheduleId;
    //课程   由自动排课得出
    private String courseId;
    //班级id 由自动排课得出班级 结合年级得出班级id
    private String classId;
    //时间片  由自动排课得出
    private int timePart;

    //教师id 分配（排课后由可以负责该课程的教师分配）
    private String teacherId;
    //教室id 分配
    private String classRoomId;

}
