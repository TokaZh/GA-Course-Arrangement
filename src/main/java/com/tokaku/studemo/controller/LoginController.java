package com.tokaku.studemo.controller;

import com.tokaku.studemo.mapper.StudentMapper;
import com.tokaku.studemo.mapper.TeacherMapper;
import com.tokaku.studemo.pojo.Student;
import com.tokaku.studemo.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    @RequestMapping("/login")
    public String login(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @RequestMapping("/loginCheck")
    public String loginCheck(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model, HttpSession session) {

        if (username != null && password != null) {
            if (username.length() < 9) {//老师
                Teacher teacher = teacherMapper.TeacherCheck(username, password);
                if (teacher != null) {
                    session.setAttribute("User", teacher);
                    session.setAttribute("Role", "t");
                    return "index";
                }
            } else {//学生
                Student student = studentMapper.StudentCheck(username, password);
                if (student != null) {
                    session.setAttribute("User", student);
                    session.setAttribute("Role", "s");
                    return "index";
                }
            }
        }

        model.addAttribute("msg", "用户名或者密码错误！");
        return "/login";
    }
}
