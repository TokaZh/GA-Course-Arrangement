package com.tokaku.controller;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.Individual;
import com.tokaku.pojo.Major;
import com.tokaku.service.CourseService;
import com.tokaku.service.GeneticAlgorithmService;
import com.tokaku.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
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

    GeneticAlgorithmService geneticAlgorithmService;

    @Autowired
    public void GeneticAlgorithmService(GeneticAlgorithmService geneticAlgorithmService) {
        this.geneticAlgorithmService = geneticAlgorithmService;
    }

    @RequestMapping("arrangement")
    public String step1(Model model) {
        Set<Major> majorSet = majorService.selectMajorList();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        model.addAttribute("majorSet", majorSet);
        model.addAttribute("year", year);
        return "arrangement/step1";
    }

    @RequestMapping("arrangement/step2")
    public String step2(Model model,
                        @RequestParam("majorId") String majorId,
                        @RequestParam("term") String term) {
        //专业、年级对应的课程计划
        Set<Course> courses = courseService.selectCourseByGrade(majorId, term);
        Major major = new Major(majorId, majorService.selectMajorNameByMajorId(majorId));

        model.addAttribute("courses", courses);
        model.addAttribute("major", major);
        model.addAttribute("term", term);
        //导入教师和教师
        return "arrangement/step2";
    }

    @RequestMapping("arrangement/savePlan")
    public String save(Course course, HttpServletResponse response, Model model) {
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
        String majorId = course.getMajorId();
        String term = String.valueOf(course.getTerm());
        Set<Course> courses = courseService.selectCourseByGrade(majorId, term);
        model.addAttribute("courses", courses);
        Major major = new Major(majorId, majorService.selectMajorNameByMajorId(majorId));

        model.addAttribute("major", major);
        model.addAttribute("term", term);
        return "arrangement/step2";


    }

    @RequestMapping("arrangement/step3")
    public String step3(Model model,
                        @RequestParam("majorId0") String majorId,
                        @RequestParam("term0") String term) {
        //排课   //获得专业号和学期——>学期转为学年 今年年份-学期/2
        Set<Course> courses = courseService.selectCourseByGrade(majorId, term);
        //2.排课
        HashMap<String, Integer> gene = geneticAlgorithmService.initGene(courses, 25);
        HashMap<String, Integer> scoreWeight = geneticAlgorithmService.getScoreWeight(courses);
        Individual individual = geneticAlgorithmService.initIndividual(4, 25, gene, scoreWeight);
        String[][] schedule = individual.getSchedule();
        String[][] sche = new String[25][4];


        HashMap<String, String> courseMap = new HashMap<>();
        for (Course cours : courses) {
            courseMap.put(cours.getCourseId(), cours.getCourseName());
        }
        for (int classNum = 0; classNum < schedule.length; classNum++) {
            for (int timePart = 0; timePart < schedule[classNum].length; timePart++) {
                sche[timePart][classNum] = courseMap.get(schedule[classNum][timePart]);
            }
        }

        model.addAttribute("schedule", sche);
        //保存到数据库


        return "arrangement/schedule";
    }

    //删除课程
    @GetMapping("/arrangement/delete/{courseId}")
    public String deleteCourse(Model model, @PathVariable("courseId") String courseId) {
        Course course = courseService.selectCourseByCourseId(courseId);
        String majorId = course.getMajorId();
        model.addAttribute("major",
                new Major(majorId, majorService.selectMajorNameByMajorId(majorId)));
        model.addAttribute("term", course.getTerm());
        courseService.deleteCourseByCourseId(courseId);

        Set<Course> courses = courseService.selectCourseByGrade(majorId, String.valueOf(course.getTerm()));
        model.addAttribute("courses", courses);
        return "arrangement/step2";
    }

    //编辑课程
    @GetMapping("/arrangement/edit/{courseId}")
    public String updateCourse(@PathVariable("courseId") String courseId, Model model) {
        Course course = courseService.selectCourseByCourseId(courseId);
        model.addAttribute("majorName", majorService.selectMajorNameByMajorId(course.getMajorId()));
        model.addAttribute("course", course);
        return "/arrangement/edit";
    }

    @PostMapping("/arrangement/updateCourse")
    public String updateStu(Model model, Course course) {
        courseService.updateCourse(course);
        String majorId = course.getMajorId();
        model.addAttribute("major",
                new Major(majorId, majorService.selectMajorNameByMajorId(majorId)));
        model.addAttribute("term", course.getTerm());

        Set<Course> courses = courseService.selectCourseByGrade(majorId, String.valueOf(course.getTerm()));
        model.addAttribute("courses", courses);
        return "arrangement/step2";
    }
}
