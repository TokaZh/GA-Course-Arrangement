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
        return "/course/course";
    }

    @GetMapping("/course/add")
    public String add() {
        return "/course/add";
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
                out.print("<script type='text/javascript'>alert('学号重复！！');window.history.back()</script>");
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "/course";
        }
    }

    //删除课程
    @GetMapping("/course/delete/{courseId}")
    public String deleteCourse(@PathVariable("courseId") int courseId) {
        courseService.deleteCourseByCourseId(courseId);
        return "redirect:/course";
    }

    //编辑课程
    @GetMapping("/course/edit/{courseId}")
    public String updateCourse(@PathVariable("courseId") String courseId, Model model) {
        Course course = courseService.selectCourseByCourseId(courseId);
        model.addAttribute("course", course);
        System.out.println(course);
        return "/course/edit";
    }

    @PostMapping("/updateCourse")
    public String updateStu(Course course) {
        courseService.updateCourse(course);
        return "redirect:/course";
    }

}
