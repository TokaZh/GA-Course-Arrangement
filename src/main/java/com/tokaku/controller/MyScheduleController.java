package com.tokaku.controller;

import com.tokaku.pojo.Schedule;
import com.tokaku.pojo.Student;
import com.tokaku.pojo.Teacher;
import com.tokaku.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Controller
public class MyScheduleController {

    private TeacherService teacherService;
    private StudentService studentService;

    @Autowired
    public void TeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void StudentService(StudentService studentService) {
        this.studentService = studentService;
    }

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

    @RequestMapping("/mySchedule")
    public String GetCurriculum(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = String.valueOf(session.getAttribute("username"));
        String role = String.valueOf(session.getAttribute("Role"));

        Set<Schedule> scheduleSet = new HashSet<>();
        HashMap<String, String> courseMap = new HashMap<>();
        if (role.equals("teacher")) {
            Teacher teacher = teacherService.selectTeacherById(username);
            scheduleSet = scheduleService.selectScheduleByTeacher(teacher.getTeacherId());
            courseMap.put(teacher.getCourseId(), courseService.selectCourseByCourseId(teacher.getCourseId()).getCourseName());
        } else if (role.equals("student")) {
            Student student = studentService.selectStudentById(username);
            String majorId = "1";
            int term = 1;
            int classNum = student.getClazz();
            scheduleSet = scheduleService.selectScheduleByClass(majorId, term, classNum);
            courseMap = courseService.selectCourseMapByTerm(majorId, term);

        } else {
            return "/error/500";
        }

        //从数据库获取课程

        String[] head = new String[]{"第一节", "第二节", "第三节", "第四节", "第五节"};
        model.addAttribute("head", head);

        Schedule[] schedule = new Schedule[25];
        for (Schedule s : scheduleSet) {
            s.setCourseId(
                    courseMap.get(
                            s.getCourseId()) + " " + (
                            s.getClassNum() + 1) + "班" + "  " +
                            s.getRoomId());
            schedule[s.getTimePart()] = s;
        }
        model.addAttribute("schedule", schedule);

        return "schedule/myschedule";
    }
}
