package com.tokaku.mapper;

import com.tokaku.pojo.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ScheduleMapper {

    Schedule selectSchedule(String majorId, int term, int classNum, int timePart);

    List<Schedule> selectClassScheduleList(String majorId, int term, int classNum);

    int addSchedule(Schedule schedule);

    int deleteScheduleByGrade(String majorId, int term);


    int updateSchedule(Schedule schedule);

}
