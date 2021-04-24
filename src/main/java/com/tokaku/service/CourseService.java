package com.tokaku.service;

import com.tokaku.pojo.Course;

import java.util.Set;

public interface CourseService {

    Set<Course> selectCourseList();

//    List<Course> queryCourseByGradeId(Grade grade);

    int deleteCourseByCourseId(int courseId);

    boolean addCourse(Course course);

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
