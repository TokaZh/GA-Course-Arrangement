package com.tokaku.service.impl;

import com.tokaku.mapper.ScheduleMapper;
import com.tokaku.pojo.Schedule;
import com.tokaku.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    ScheduleMapper scheduleMapper;

    @Autowired
    private void ScheduleMapper(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public Schedule selectSchedule(String majorId, int term, int classNum, int timePart) {
        return scheduleMapper.selectSchedule(majorId, term, classNum, timePart);
    }

    @Override
    public int addScheduleList(List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            scheduleMapper.addSchedule(schedule);
        }
        return 1;
    }

    @Override
    public int deleteScheduleByGrade(String majorId, int term) {
        return scheduleMapper.deleteScheduleByGrade(majorId, term);
    }
}
