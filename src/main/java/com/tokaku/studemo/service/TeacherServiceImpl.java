package com.tokaku.studemo.service;

import com.tokaku.studemo.mapper.StudentMapper;
import com.tokaku.studemo.mapper.TeacherMapper;
import com.tokaku.studemo.pojo.Student;
import com.tokaku.studemo.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

//    @Override
//    public int querySize() {
//        return studentMapper.querySize();
//    }
//
//    @Override
//    public Student queryStuById(String studyid) {
//        return studentMapper.queryStuById(studyid);
//    }
//
//    @Override
//    public List<Student> queryStuByLimit(int startpage, int pageSize) {
//        return studentMapper.queryStuByLimit(startpage, pageSize);
//    }

    @Override
    public List<Teacher> queryTeacherList() {
        return teacherMapper.queryTeacherList();
    }

//    @Override
//    public boolean addStu(Student student) {
//        if (studentMapper.queryStuById(student.getStudyid()) == null) {
//            int age = AgeUtils.getAge(student.getBirth());
//            student.setAge(age);
//            studentMapper.addStu(student);
//            return true;
//        } else
//            return false;
//    }
//
//    @Override
//    public int updateStu(Student student) {
//        int age = AgeUtils.getAge(student.getBirth());
//        student.setAge(age);
//        return studentMapper.updateStu(student);
//    }
//
//    @Override
//    public int deleteStu(String studyid) {
//        return studentMapper.deleteStu(studyid);
//    }
}
