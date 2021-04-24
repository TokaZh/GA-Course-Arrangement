package com.tokaku.service;

import com.tokaku.pojo.Student;

import java.util.List;

public interface StudentService {

//    int querySize();
//
//    Student queryStuById(String studyid);
//
//    List<Student> queryStuByLimit(int startpage, int pageSize);

    List<Student> selectStudentList();

    //    boolean addStu(Student student);
//
//    int updateStu(Student stu);
//
    int deleteStudent(String sno);
}
