package com.tokaku.controller;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.Individual;
import com.tokaku.pojo.Major;
import com.tokaku.pojo.Schedule;
import com.tokaku.service.CourseService;
import com.tokaku.service.GeneticAlgorithmService;
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
import java.util.*;

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

    GeneticAlgorithmService geneticAlgorithmService;

    @Autowired
    public void GeneticAlgorithmService(GeneticAlgorithmService geneticAlgorithmService) {
        this.geneticAlgorithmService = geneticAlgorithmService;
    }

    ScheduleService scheduleService;

    @Autowired
    public void ScheduleMapper(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
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
        System.out.println(term);
        System.out.println(classNum);
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


        //搜索班级数
        //排课   //获得专业号和学期——>学期转为学年 今年年份-学期/2
        Set<Course> courses = courseService.selectCourseByTerm(major.getMajorId(), term);
        //2.排课
        HashMap<String, Integer> gene = geneticAlgorithmService.initGene(courses, 25);
        Individual individual = geneticAlgorithmService.initIndividual(classNum, 25, gene);
        String[][] schedule = individual.getSchedule();
        String[][] sche = new String[25][classNum];
        List<Schedule> scheduleList = new ArrayList<>();


        HashMap<String, String> courseMap = new HashMap<>();
        for (Course cours : courses) {
            courseMap.put(cours.getCourseId(), cours.getCourseName());
        }
        for (int classnum = 0; classnum < schedule.length; classnum++) {
            for (int timepart = 0; timepart < schedule[classnum].length; timepart++) {
                sche[timepart][classnum] = courseMap.get(schedule[classnum][timepart]);
            }
        }
        session.setAttribute("schedule", sche);
        //保存到数据库
        //教师 教室未匹配
        //       专业-年级 => 年级下所有班级id
        String majorId = major.getMajorId();
        //先删除专业-年级所有课表
        scheduleService.deleteScheduleByGrade(majorId, term);
        //保存
        // 然后将String[][]转为List<Schedule>存入

//        scheduleMapper.deleteScheduleByClassId()
        return "arrangement/step3";
    }

    //手动调课
    @GetMapping("/arrangement/edit/{classNum}/{timaPart}")
    public String editSchedule(HttpServletRequest request,
                               @PathVariable("classNum") String classNum,
                               @PathVariable("timaPart") String timaPart,
                               Model model) {
        HttpSession session = request.getSession();
        Major major = (Major) session.getAttribute("major");
        String term = (String) session.getAttribute("term");

//        Course course = courseService.selectCourseByCourseId(courseId);
//        model.addAttribute("course", course);
        return "/arrangement/step3";
    }


    //添加课程计划
    @RequestMapping("arrangement/addPlan")
    public String addPlan(Course course, Model model,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        HttpSession session = request.getSession();
        Major major = (Major) session.getAttribute("major");
        int term = Integer.parseInt((String) session.getAttribute("term"));
        System.out.println(course);
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
}
