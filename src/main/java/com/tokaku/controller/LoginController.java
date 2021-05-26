package com.tokaku.controller;

import com.tokaku.pojo.Student;
import com.tokaku.pojo.Teacher;
import com.tokaku.service.StudentService;
import com.tokaku.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

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

    @RequestMapping("/login")
    public String login(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @RequestMapping("loginCheck")
    public String loginCheck(
            Model model, HttpSession session,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        if (!username.equals("") && !password.equals("")) {
            if (username.equals("admin") && password.equals("admin")) {
                session.setAttribute("Role", "admin");
                return "index";
            }
            if (username.length() < 9) {//老师
                int check = teacherService.TeacherCheck(username, password);
                if (check == 1) {//登录成功
                    Teacher teacher = teacherService.selectTeacherById(username);
                    session.setAttribute("Role", "teacher");
                    session.setAttribute("Name", teacher.getTeacherName() + "老师");
                    session.setAttribute("username", username);
                    return "user";
                } else {
                    if (check == 0) {//密码错误
                        model.addAttribute("msg", "密码错误！");
                    } else {//用户名不存在
                        model.addAttribute("msg", "用户名不存在！");
                    }
                    return "/login";
                }
            } else {//学生
                int check = studentService.StudentCheck(username, password);
                if (check == 1) {//登录成功
                    Student student = studentService.selectStudentById(username);
                    session.setAttribute("Role", "student");
                    session.setAttribute("Name", student.getStudentName() + "同学");
                    session.setAttribute("username", username);
                    return "user";
                } else {
                    if (check == 0) {//密码错误
                        model.addAttribute("msg", "密码错误！");
                    } else {//用户名不存在
                        model.addAttribute("msg", "用户名不存在！");
                    }
                    return "/login";
                }
            }
        }
        return "/login";
    }
}
