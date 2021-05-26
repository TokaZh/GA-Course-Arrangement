package com.tokaku.service;

import com.tokaku.pojo.Teacher;

import java.util.List;

public interface TeacherService {
    int TeacherCheck(String teacherId, String teacherPassword);

    Teacher selectTeacherById(String teacherId);
//
//    List<Student> queryStuByLimit(int startpage, int pageSize);

    List<Teacher> selectTeacherList();

    int addTeacher(Teacher teacher);

    //
//    int updateStu(Student stu);
//
    int deleteTeacher(String teacherId);

    int updateTeacher(Teacher teacher);
}
