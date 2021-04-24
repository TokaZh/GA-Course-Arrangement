package com.tokaku.service.impl;

import com.tokaku.mapper.TeacherMapper;
import com.tokaku.pojo.Teacher;
import com.tokaku.service.TeacherService;
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
    public List<Teacher> selectTeacherList() {
        return teacherMapper.selectTeacherList();
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
