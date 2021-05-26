package com.tokaku.controller;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.Major;
import com.tokaku.service.CourseService;
import com.tokaku.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;

@Controller
public class CourseController {

    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    private MajorService majorService;

    @Autowired
    public void MajorService(MajorService majorService) {
        this.majorService = majorService;
    }

    @RequestMapping("/course")
    public String GetUserLimit(Model model) {
        Set<Course> courses = courseService.selectCourseList();
        HashMap<String, String> majorMap = majorService.selectMajorMap();
        model.addAttribute("courses", courses);
        model.addAttribute("majorMap", majorMap);
        Set<Major> majorSet = majorService.selectMajorList();
        model.addAttribute("majorSet", majorSet);

        return "/course/course";
    }

    //添加课程
    @PostMapping("/course/saveCourse")
    public String addCourse(Course course, HttpServletResponse response) {
        System.out.println(course);
        if (courseService.addCourse(course))
            return "redirect:/course";
        else {
            try {
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("<script type='text/javascript'>alert('课程重复！！');window.history.back()</script>");
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "redirect:/course";
        }
    }

    //删除课程
    @GetMapping("/course/delete/{courseId}")
    public String deleteCourse(@PathVariable("courseId") String courseId) {
        courseService.deleteCourseByCourseId(courseId);
        return "redirect:/course";
    }

    //编辑课程
    @GetMapping("/course/edit/{courseId}")
    public String updateCourse(@PathVariable("courseId") String courseId, Model model) {
        Course course = courseService.selectCourseByCourseId(courseId);
        Set<Major> majorSet = majorService.selectMajorList();
        model.addAttribute("majorSet", majorSet);
        model.addAttribute("course", course);
        return "/course/edit";
    }

    @PostMapping("/course/updateCourse")
    public String updateStu(Course course) {
        courseService.updateCourse(course);
        return "redirect:/course";
    }
}
