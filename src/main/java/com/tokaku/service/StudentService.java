package com.tokaku.service;

import com.tokaku.pojo.Student;

import java.util.List;

public interface StudentService {

    Student selectStudentById(String studentId);

    List<Student> selectStudentList();

    int deleteStudent(String sno);

    int StudentCheck(String username, String password);
}
