package com.tokaku.service.impl;

import com.tokaku.mapper.CourseMapper;
import com.tokaku.mapper.RoomMapper;
import com.tokaku.mapper.ScheduleMapper;
import com.tokaku.pojo.Course;
import com.tokaku.pojo.Room;
import com.tokaku.pojo.Schedule;
import com.tokaku.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    ScheduleMapper scheduleMapper;

    @Autowired
    private void ScheduleMapper(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }

    CourseMapper courseMapper;

    @Autowired
    private void CourseMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    RoomMapper roomMapper;

    @Autowired
    private void RoomMapper(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
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

    @Override
    public int addScheduleList(String[][] schedule, String majorId, int term) {
        List<Room> rooms = roomMapper.selectRoomList();
        for (int classNum = 0; classNum < schedule.length; classNum++) {
            for (int timePart = 0; timePart < schedule[classNum].length; timePart++) {
                if (schedule[classNum][timePart] != null) {
                    //遍历
                    for (Room room : rooms) {
                        //该教室该时段没有课程
                        if (scheduleMapper.selectScheduleByRoomId(room.getRoomId(), timePart) == null) {
                            //判断教室类型是否匹配
                            Course course = courseMapper.selectCourseByCourseId(schedule[classNum][timePart]);
                            if (course.getType() == room.getType()) {
                                scheduleMapper.addSchedule(
                                        new Schedule(
                                                majorId,
                                                term,
                                                classNum,
                                                timePart,
                                                schedule[classNum][timePart],
                                                course.getTeacherId(),
                                                room.getRoomId())
                                );
                                break;
                            }
                        }
                    }

                }
            }
        }
        return 1;
    }

    @Override
    public Set<Schedule> selectScheduleByClass(String majorId, int term, int classNum) {
        return scheduleMapper.selectClassScheduleList(majorId, term, classNum);
    }

    @Override
    public Schedule[][] selectSchedule(String majorId, int term, int classNum) {
        Set<Schedule> schedules = scheduleMapper.selectScheduleAllList(majorId, term);
        Schedule[][] strings = new Schedule[classNum + 1][25];
        for (Schedule schedule : schedules) {
            strings[schedule.getClassNum()][schedule.getTimePart()] = schedule;
        }
        return strings;
    }

    @Override
    public Set<Schedule> selectScheduleByTeacher(String teacherId) {
        return scheduleMapper.selectScheduleSetByTeacher(teacherId);
    }

    @Override
    public Set<Schedule> selectScheduleByRoom(String roomId) {
        return scheduleMapper.selectScheduleSetByRoom(roomId);
    }

    @Override
    public int updateSchedule(Schedule schedule, int newPart) {
        return scheduleMapper.updateSchedule(schedule, newPart);
    }
}
