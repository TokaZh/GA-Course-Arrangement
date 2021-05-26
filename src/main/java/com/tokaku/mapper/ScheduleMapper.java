package com.tokaku.mapper;

import com.tokaku.pojo.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
@Repository
public interface ScheduleMapper {

    Schedule selectSchedule(String majorId, int term, int classNum, int timePart);

    Set<Schedule> selectClassScheduleList(String majorId, int term, int classNum);

    int addSchedule(Schedule schedule);

    int deleteScheduleByGrade(String majorId, int term);

    int updateSchedule(Schedule schedule, int newPart);

    Schedule selectScheduleByRoomId(String roomId, int timePart);

    Set<Schedule> selectScheduleSetByTeacher(String teacherId);

    Set<Schedule> selectScheduleSetByRoom(String roomId);

    Set<Schedule> selectScheduleAllList(String majorId, int term);
}
