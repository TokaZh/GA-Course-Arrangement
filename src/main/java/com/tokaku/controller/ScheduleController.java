package com.tokaku.controller;

import com.tokaku.pojo.Major;
import com.tokaku.pojo.Room;
import com.tokaku.pojo.Schedule;
import com.tokaku.pojo.Teacher;
import com.tokaku.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Controller
public class ScheduleController {

    ScheduleService scheduleService;

    @Autowired
    public void scheduleMapper(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    MajorService majorService;

    @Autowired
    public void majorService(MajorService majorService) {
        this.majorService = majorService;
    }

    GeneticAlgorithmService geneticAlgorithmService;

    @Autowired
    public void GeneticAlgorithmService(GeneticAlgorithmService geneticAlgorithmService) {
        this.geneticAlgorithmService = geneticAlgorithmService;
    }

    CourseService courseService;

    @Autowired
    public void CourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    RoomService roomService;

    @Autowired
    public void RoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/schedule")
    public String selectClass(Model model, HttpServletRequest request) {
        Set<Major> majorSet = majorService.selectMajorList();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        model.addAttribute("majorSet", majorSet);
        model.addAttribute("year", year);
        List<Room> rooms = roomService.selectRoomList();
        model.addAttribute("rooms", rooms);
        List<Teacher> teachers = teacherService.selectTeacherList();
        model.addAttribute("teachers", teachers);
        return "schedule/step1";
    }

    @RequestMapping("/schedule/step2")
    public String GetCurriculum(Model model, HttpServletRequest request,
                                @RequestParam("majorId") String majorId,
                                @RequestParam("term") String term,
                                @RequestParam("classNum") String classNum) {
        HttpSession session = request.getSession();
        Major major = new Major(majorId, majorService.selectMajorNameByMajorId(majorId));

        session.setAttribute("major", major);
        session.setAttribute("term", term);
        session.setAttribute("classNum", classNum);

        //从数据库获取课程
        Set<Schedule> scheduleSet = scheduleService.selectScheduleByClass(major.getMajorId(), Integer.parseInt(term), Integer.parseInt(classNum));
        HashMap<String, String> courseMap = courseService.selectCourseMapByTerm(major.getMajorId(), Integer.parseInt(term));

        String[] head = new String[]{"第一节", "第二节", "第三节", "第四节", "第五节"};
        model.addAttribute("head", head);
        Schedule[] schedule = new Schedule[25];
        for (Schedule s : scheduleSet) {
            s.setCourseId(courseMap.get(s.getCourseId()));
            schedule[s.getTimePart()] = s;
        }
        model.addAttribute("schedule", schedule);

        return "schedule/schedule";
    }

    @RequestMapping("/schedule/teacher")
    public String Teacher(Model model, HttpServletRequest request,
                          @RequestParam("teacherId") String teacherId) {
        //从数据库获取课程
        Set<Schedule> scheduleSet = scheduleService.selectScheduleByTeacher(teacherId);
        HashMap<String, String> courseMap = new HashMap<>();
        Teacher teacher = teacherService.selectTeacherById(teacherId);
        courseMap.put(teacher.getCourseId(), courseService.selectCourseByCourseId(teacher.getCourseId()).getCourseName());
        String[] head = new String[]{"第一节", "第二节", "第三节", "第四节", "第五节"};
        model.addAttribute("head", head);
        Schedule[] schedule = new Schedule[25];
        for (Schedule s : scheduleSet) {
            s.setCourseId(courseMap.get(s.getCourseId()) + s.getRoomId());
            schedule[s.getTimePart()] = s;
        }
        model.addAttribute("schedule", schedule);

        return "schedule/schedule";
    }

    @RequestMapping("/schedule/room")
    public String Room(Model model, HttpServletRequest request,
                       @RequestParam("roomId") String roomId) {
        HttpSession session = request.getSession();
        //从数据库获取课程
        Set<Schedule> scheduleSet = scheduleService.selectScheduleByRoom(roomId);
        String[] head = new String[]{"第一节", "第二节", "第三节", "第四节", "第五节"};
        model.addAttribute("head", head);
        Schedule[] schedule = new Schedule[25];
        for (Schedule s : scheduleSet) {
            System.out.println(s);
            s.setCourseId(courseService.selectCourseByCourseId(s.getCourseId()).getCourseName());
            schedule[s.getTimePart()] = s;
        }
        model.addAttribute("schedule", schedule);

        return "schedule/schedule";
    }
}
