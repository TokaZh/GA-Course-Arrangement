package com.tokaku.controller;

import com.tokaku.pojo.Grade;
import com.tokaku.pojo.Major;
import com.tokaku.service.GradeService;
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
public class GradeController {

    private GradeService gradeService;

    @Autowired
    public void GradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    private MajorService majorService;

    @Autowired
    public void MajorService(MajorService majorService) {
        this.majorService = majorService;
    }

    @RequestMapping("/grade")
    public String GetUserLimit(Model model) {
        Set<Grade> grades = gradeService.selectGradeList();
        HashMap<String, String> majorMap = majorService.selectMajorMap();
        model.addAttribute("grades", grades);
        model.addAttribute("majorMap", majorMap);

        return "/grade/grade";
    }

    @GetMapping("/grade/add")
    public String add(Model model) {
        Set<Major> majorSet = majorService.selectMajorList();
        model.addAttribute("majorSet", majorSet);
        return "/grade/add";
    }

    //添加课程
    @PostMapping("/grade/saveGrade")
    public String addGrade(Grade grade, HttpServletResponse response) {
        grade.setGradeId("" + grade.getMajorId() + grade.getGrade());

        System.out.println(grade);
        if (gradeService.addGrade(grade))
            return "redirect:/grade";
        else {//报错 该年级已存在 请尝试修改数据
            try {
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("<script type='text/javascript'>alert('学号重复！！');window.history.back()</script>");
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "/grade/grade";
        }
    }

    //删除
    @GetMapping("/grade/delete/{gradeId}")
    public String deleteCourse(@PathVariable("gradeId") String gradeId) {
        gradeService.deleteGradeByGradeId(gradeId);
        return "redirect:/grade";
    }
//
//    //编辑课程
//    @GetMapping("/course/edit/{courseId}")
//    public String updateCourse(@PathVariable("courseId") String courseId, Model model) {
//        Course course = courseService.selectCourseByCourseId(courseId);
//        model.addAttribute("course", course);
//        System.out.println(course);
//        return "/course/edit";
//    }
//
//    @PostMapping("/updateCourse")
//    public String updateStu(Course course) {
//        courseService.updateCourse(course);
//        return "redirect:/course";
//    }

}
