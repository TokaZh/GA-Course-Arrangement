package com.tokaku.controller;

import com.tokaku.pojo.Teacher;
import com.tokaku.service.TeacherService;
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
import java.util.List;

@Controller
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/teacher")
    public String GetUserLimit(Model model) {
        List<Teacher> teachers = teacherService.selectTeacherList();
        model.addAttribute("teachers", teachers);
        return "/teacher/teacher";
    }

    //添加教师
    @PostMapping("/teacher/saveTeacher")
    public String addTeacher(Teacher teacher, HttpServletResponse response) {
        if (teacherService.addTeacher(teacher) != 0)
            return "redirect:/teacher";
        else {
            try {
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("<script type='text/javascript'>alert('教师重复！！');window.history.back()</script>");
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "redirect:/teacher";
        }
    }

    //删除课程
    @GetMapping("/teacher/delete/{teacherId}")
    public String deleteTeacher(@PathVariable("teacherId") String teacherId) {
        teacherService.deleteTeacher(teacherId);
        return "redirect:/teacher";
    }

    //编辑课程
    @GetMapping("/teacher/edit/{teacherId}")
    public String updateTeacher(@PathVariable("teacherId") String teacherId, Model model) {
        Teacher teacher = teacherService.selectTeacherById(teacherId);
        model.addAttribute("teacher", teacher);
        return "/teacher/edit";
    }

    @PostMapping("/teacher/updateTeacher")
    public String updateTeacher(Teacher teacher) {
        teacherService.updateTeacher(teacher);
        return "redirect:/teacher";
    }

}
