package com.tokaku.service;

import com.tokaku.pojo.Course;

import java.util.Set;

public interface CourseService {

    Set<Course> selectCourseList();

    int deleteCourseByCourseId(String courseId);

    Course selectCourseByCourseId(String courseId);

    boolean addCourse(Course course);

    int updateCourse(Course course);

    Set<Course> selectCourseByGrade(String majorId, String term);

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
