package com.tokaku.service;

import com.tokaku.pojo.Course;

import java.util.List;
import java.util.Set;

public interface CourseService {

    Set<Course> selectCourseList();

    int deleteCourseByCourseId(int courseId);

    List<Course> queryCourseByGradeAndMajor(int grade, int major);

    boolean addCourse(Course course);

    int updateCourse(Course course);

//    int querySize();
//
//    Student queryStuById(String studyid);
//
//    List<Student> queryStuByLimit(int startpage, int pageSize);


//    boolean addStu(Student student);
//
//    int updateStu(Student stu);
//
//    int deleteStu(String studyid);
}
