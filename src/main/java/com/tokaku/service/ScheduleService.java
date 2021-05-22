package com.tokaku.service;

import com.tokaku.pojo.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule selectSchedule(String majorId, int term, int classNum, int timePart);

    int addScheduleList(List<Schedule> schedules);

    int deleteScheduleByGrade(String majorId, int term);
}
