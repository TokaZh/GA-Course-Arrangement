package com.tokaku.service;

import com.tokaku.pojo.Schedule;

import java.util.List;
import java.util.Set;

public interface ScheduleService {
    Schedule selectSchedule(String majorId, int term, int classNum, int timePart);

    int addScheduleList(List<Schedule> schedules);

    int deleteScheduleByGrade(String majorId, int term);

    int addScheduleList(String[][] schedule, String majorId, int term);

    Set<Schedule> selectScheduleByClass(String majorId, int term, int classNum);

    Schedule[][] selectSchedule(String majorId, int term, int classNum);

    Set<Schedule> selectScheduleByTeacher(String teacherId);

    Set<Schedule> selectScheduleByRoom(String roomId);

    int updateSchedule(Schedule schedule, int newPart);
}
