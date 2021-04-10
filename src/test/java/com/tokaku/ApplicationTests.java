package com.tokaku;

import com.tokaku.pojo.Course;
import com.tokaku.service.CourseService;
import com.tokaku.service.IImportExcelService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@MapperScan({"com.tokaku.mapper"})
@SpringBootTest
class ApplicationTests {

    //    @Autowired
//    TeacherService teacherService;
//    @Autowired
//    StudentService studentService;
    @Autowired
    CourseService courseService;


    @Test
    void contextLoads() {
//        List<Teacher> teachers = teacherService.getTeacherList();
//        for (Teacher student : teachers) {
//            System.out.println(student);
//        }
//        List<Student> studentList = studentService.getStudentList();
//        for (Student student : studentList) {
//            System.out.println(student);
//        }
        List<Course> courseList = courseService.getCourseList();
        for (Course course : courseList) {
            System.out.println(course);
        }
    }

    @Autowired
    IImportExcelService iImportExcelService;

    @Test
    void fileTest() {

    }

}
