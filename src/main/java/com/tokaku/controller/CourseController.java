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
        return "course";
    }

    @PostMapping("/course")
    public String addStu(Course course, HttpServletResponse response) {
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
    public String deleteStu(@PathVariable("courseId") int courseId) {
        courseService.deleteCourseByCourseId(courseId);
        return "redirect:/course";
    }
//
//
//    //跳转到修改页面 (链结形式-getmapping)

//
//    @PostMapping("/updateStu")
//    public String updateStu(Student student) {
//        stuService.updateStu(student);
//        return "redirect:/stus";
//    }
//
//
//@GetMapping("/updateStu/{studyid}")
//public String addstu(@PathVariable("studyid") String studyid, Model model) {
//    Student student = stuService.queryStuById(studyid);
//    model.addAttribute("stu", student);
//    return "student/student_update";

}
