package com.tokaku.controller;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.Major;
import com.tokaku.pojo.Schedule;
import com.tokaku.service.AutoCoreService;
import com.tokaku.service.CourseService;
import com.tokaku.service.MajorService;
import com.tokaku.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Controller
public class ArrangementController {

    CourseService courseService;

    @Autowired
    public void CourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    MajorService majorService;

    @Autowired
    public void MajorService(MajorService majorService) {
        this.majorService = majorService;
    }
//
//    GeneticAlgorithmService geneticAlgorithmService;
//
//    @Autowired
//    public void GeneticAlgorithmService(GeneticAlgorithmService geneticAlgorithmService) {
//        this.geneticAlgorithmService = geneticAlgorithmService;
//    }

    ScheduleService scheduleService;

    @Autowired
    public void ScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    AutoCoreService autoCoreService;

    @Autowired
    public void AutoCoreService(AutoCoreService autoCoreService) {
        this.autoCoreService = autoCoreService;
    }

    @RequestMapping("arrangement")
    public String step1(Model model) {
        Set<Major> majorSet = majorService.selectMajorList();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        model.addAttribute("majorSet", majorSet);
        model.addAttribute("year", year);
        return "arrangement/step1";
    }

    //获取并查看课程计划
    @RequestMapping("arrangement/step2")
    public String step2(Model model, HttpServletRequest request,
                        @RequestParam("majorId") String majorId,
                        @RequestParam("term") String term,
                        @RequestParam("classNum") String classNum) {
        HttpSession session = request.getSession();
        session.setAttribute("major", new Major(majorId, majorService.selectMajorNameByMajorId(majorId)));
        session.setAttribute("term", term);
        session.setAttribute("classNum", classNum);
        //获取专业、年级对应的课程计划
        Set<Course> courses = courseService.selectCourseByTerm(majorId, Integer.parseInt(term));
        model.addAttribute("courses", courses);
        //导入教师和教师
        return "arrangement/step2";
    }

    //自动排课后获取并打印年级总表
    @RequestMapping("arrangement/step3")
    public String step3(HttpServletRequest request) {
        //获取年级信息以进行定位
        HttpSession session = request.getSession();
        Major major = (Major) session.getAttribute("major");
        int term = Integer.parseInt((String) session.getAttribute("term"));
        int classNum = Integer.parseInt((String) session.getAttribute("classNum"));

        //排课
        Set<Course> courses = courseService.selectCourseByTerm(major.getMajorId(), term);
        String[][] schedule = autoCoreService.AutoArrangementCourse(courses, 4);
        //教师 教室未匹配
        //保存到数据库
        scheduleService.deleteScheduleByGrade(major.getMajorId(), term);
        scheduleService.addScheduleList(schedule, major.getMajorId(), term);

        //将行列互换 便于网页端浏览
        String[][] output = new String[25][classNum];
        HashMap<String, String> courseMap = new HashMap<>();
        for (Course course : courses) {
            courseMap.put(course.getCourseId(), course.getCourseName());
        }
        for (int classnum = 0; classnum < schedule.length; classnum++) {
            for (int timepart = 0; timepart < schedule[classnum].length; timepart++) {
                output[timepart][classnum] = courseMap.get(schedule[classnum][timepart]);
            }
        }
        session.setAttribute("schedule", output);

        return "arrangement/step3";
    }



    //添加课程计划
    @RequestMapping("arrangement/addPlan")
    public String addPlan(Course course, Model model,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        HttpSession session = request.getSession();
        Major major = (Major) session.getAttribute("major");
        int term = Integer.parseInt((String) session.getAttribute("term"));
        if (!courseService.addCourse(course)) {
            try {
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("<script type='text/javascript'>alert('学号重复！！');window.history.back()</script>");
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Set<Course> courses = courseService.selectCourseByTerm(major.getMajorId(), term);
        model.addAttribute("courses", courses);

        return "arrangement/step2";


    }

    //删除课程
    @GetMapping("/arrangement/delete/{courseId}")
    public String deletePlan(Model model, @PathVariable("courseId") String courseId,
                             HttpServletRequest request) {
        HttpSession session = request.getSession();
        Major major = (Major) session.getAttribute("major");
        int term = Integer.parseInt((String) session.getAttribute("term"));
        courseService.deleteCourseByCourseId(courseId);

        Set<Course> courses = courseService.selectCourseByTerm(major.getMajorId(), term);
        model.addAttribute("courses", courses);
        return "arrangement/step2";
    }

    //跳转编辑课程页面
    @GetMapping("/arrangement/edit/{courseId}")
    public String editPlan(@PathVariable("courseId") String courseId, Model model) {
        Course course = courseService.selectCourseByCourseId(courseId);
        model.addAttribute("course", course);
        return "/arrangement/edit";
    }

    //编辑课程后保存课程
    @PostMapping("/arrangement/updatePlan")
    public String updatePlan(Model model, Course course, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Major major = (Major) session.getAttribute("major");
        int term = Integer.parseInt((String) session.getAttribute("term"));

        courseService.updateCourse(course);

        Set<Course> courses = courseService.selectCourseByTerm(major.getMajorId(), term);
        model.addAttribute("courses", courses);
        return "arrangement/step2";
    }


    //手动调课
    @GetMapping("/arrangement/edit/{classNum}/{timePart}")
    public String editSchedule(HttpServletRequest request,
                               @PathVariable("classNum") String clazz,
                               @PathVariable("timePart") String part,
                               Model model) {
        HttpSession session = request.getSession();
        Major major = (Major) session.getAttribute("major");
        int term = Integer.parseInt((String) session.getAttribute("term"));
        int classNum = Integer.parseInt((String) session.getAttribute("classNum"));

        Schedule schedule = scheduleService.selectSchedule(
                major.getMajorId(),
                term,
                Integer.parseInt(clazz),
                Integer.parseInt(part));
        Course course = courseService.selectCourseByCourseId(schedule.getCourseId());
        Schedule[][] schedules = scheduleService.selectSchedule(major.getMajorId(),
                term,
                classNum);
        Set<Integer> nullPart = new HashSet<>();
        //将行列互换 便于网页端浏览
        int[] sign = new int[25];
        for (int classnum = 0; classnum < schedules.length; classnum++) {
            for (int timepart = 0; timepart < schedules[classnum].length; timepart++) {
                if (schedules[classnum][timepart] != null) {
                    if (classnum == Integer.parseInt(clazz)) {
                        sign[timepart] = 1;
                    } else if (schedules[classnum][timepart].getCourseId().equals(schedule.getCourseId())) {
                        sign[timepart] = 1;
                    }
                }
            }

        }
        for (int i = 0; i < sign.length; i++) {
            if (sign[i] == 0) {
                nullPart.add(i);
            }
        }

        model.addAttribute("course", course);
        model.addAttribute("schedule", schedule);
        model.addAttribute("nullPart", nullPart);
        session.setAttribute("schedule", schedule);
        return "/arrangement/edit2";
    }


    @RequestMapping("arrangement/saveSchedule")
    public String saveSchedule(HttpServletRequest request,
                               @RequestParam("newPart") String newPart) {
        HttpSession session = request.getSession();
        Schedule schedule = (Schedule) session.getAttribute("schedule");
        scheduleService.updateSchedule(schedule, Integer.parseInt(newPart));
        return "redirect:/arrangement/schedule";
    }

    @RequestMapping("arrangement/schedule")
    public String schedule(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Major major = (Major) session.getAttribute("major");
        int term = Integer.parseInt((String) session.getAttribute("term"));
        int classNum = Integer.parseInt((String) session.getAttribute("classNum"));
        Schedule[][] schedule = scheduleService.selectSchedule(major.getMajorId(), term, classNum);
        Set<Course> courses = courseService.selectCourseByTerm(major.getMajorId(), term);

        //将行列互换 便于网页端浏览
        String[][] output = new String[25][classNum];
        HashMap<String, String> courseMap = new HashMap<>();
        for (Course course : courses) {
            courseMap.put(course.getCourseId(), course.getCourseName());
        }
        for (int classnum = 0; classnum < schedule.length; classnum++) {
            for (int timepart = 0; timepart < schedule[classnum].length; timepart++) {
                if (schedule[classnum][timepart] != null)
                    output[timepart][classnum] = courseMap.get(schedule[classnum][timepart].getCourseId());
            }
        }
        session.setAttribute("schedule", output);

        return "arrangement/step3";
    }
}
