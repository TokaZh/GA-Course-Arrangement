package com.tokaku.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    // 专业号 年级号 班级号
    //id自动生成
//    private String scheduleId;
    private String majorId;
    private int term;
    private int classNum;

    //时间片  由自动排课得出
    private int timePart;
    //课程   由自动排课得出
    private String courseId;

    //教师id 分配（排课后由可以负责该课程的教师分配）
    private String teacherId;
    //教室id 分配
    private String roomId;

}
