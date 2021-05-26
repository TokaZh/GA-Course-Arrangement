package com.tokaku.controller;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.User;
import com.tokaku.service.CourseService;
import com.tokaku.service.ImportExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

@Controller
public class ImportExcelController {

    ImportExcelService importExcelService;
    CourseService courseService;

    @Autowired
    public void iImportExcelService(ImportExcelService importExcelService) {
        this.importExcelService = importExcelService;
    }

    @Autowired
    public void courseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping("/upload")
    public String upload() {
        return "upload";
    }

    @RequestMapping("/importCourseExcel")
    public String withCourse(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp) {
        Set<Course> list = importExcelService.importExcelWithCourse(file);

        //遍历数据
        for (Course bean : list) {
            //批量插入list到数据库
            courseService.addCourseHasId(bean);

        }
        return "redirect:/course";
    }


    @PostMapping("/importExcel")
    @ResponseBody
    public String withUser(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp) {
        List<User> list = importExcelService.importExcelWithStudent(file);
        if (list == null || list.size() == 0) {
            return "redirect:/student";
        }
        //遍历看看数据
        for (User bean : list) {
            System.out.println(bean.toString());
        }

        //批量插入list到数据库
        //待添加

        return "redirect:/student";
    }


}
