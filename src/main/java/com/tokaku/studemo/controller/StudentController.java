package com.tokaku.studemo.controller;

import com.tokaku.studemo.pojo.Student;
import com.tokaku.studemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService stuService;

    @RequestMapping("/student")
    public String GetStudents(Model model) {
        List<Student> students = stuService.queryStudentList();
        model.addAttribute("students", students);
        return "student";
    }

    //    @PostMapping("/stu")
//    public String addStu(Student student, HttpServletResponse response) {
//        if (stuService.addStu(student))
//            return "redirect:/stus";
//        else {
//            try {
//                response.setContentType("text/html;charset=utf-8");
//                PrintWriter out = response.getWriter();
//                out.print("<script type='text/javascript'>alert('学号重复！！');window.history.back()</script>");
//                out.flush();
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return "/stus";
//        }
//    }
//
//    //跳转到修改页面 (链结形式-getmapping)
//    @GetMapping("/updateStu/{studyid}")
//    public String addstu(@PathVariable("studyid") String studyid, Model model) {
////        查出原来的数据
//        Student student = stuService.queryStuById(studyid);
//        model.addAttribute("stu", student);
//        return "student/student_update";
//    }
//
//    @PostMapping("/updateStu")
//    public String updateStu(Student student) {
//        stuService.updateStu(student);
//        return "redirect:/stus";
//    }
//
//
    @GetMapping("/deleteStudent/{studyid}")
    public String deleteStu(@PathVariable("studyid") String studyid) {
        stuService.deleteStudent(studyid);
        return "redirect:/student";
    }

}
