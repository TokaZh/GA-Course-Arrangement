package com.tokaku.controller;

import com.tokaku.pojo.Course;
import com.tokaku.service.CourseService;
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
import java.util.Set;

@Controller
public class CourseController {

    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping("/course")
    public String GetUserLimit(Model model) {
        Set<Course> courses = courseService.selectCourseList();
        model.addAttribute("courses", courses);
        return "/course/list";
    }

    @GetMapping("/course/add")
    public String add() {
        return "/course/add";
    }


    @PostMapping("/course/addCourse")
    public String addCourse(Course course, HttpServletResponse response) {
        System.out.println(course);
        if (courseService.addCourse(course))
            return "redirect:/course";
        else {
            try {
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("<script type='text/javascript'>alert('学号重复！！');window.history.back()</script>");
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "/course";
        }
    }

    @GetMapping("/deleteCourse/{courseId}")
    public String deleteCourse(@PathVariable("courseId") int courseId) {
        courseService.deleteCourseByCourseId(courseId);
        return "redirect:/course";
    }

    @GetMapping("/updateCourse/{course}")
    public String updateCourse(@PathVariable("course") Course course, Model model) {
//        courseService.updateCourse(course);
        model.addAttribute("stu", course);
        return "/course/edit";
    }

    @PostMapping("/updateCourse")
    public String updateStu(Course course) {
        courseService.updateCourse(course);
        return "redirect:/course";
    }

}
