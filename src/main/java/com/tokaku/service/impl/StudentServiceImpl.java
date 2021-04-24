package com.tokaku.service.impl;

import com.tokaku.mapper.StudentMapper;
import com.tokaku.pojo.Student;
import com.tokaku.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    private StudentMapper studentMapper;

    @Autowired
    private void StudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

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
    public List<Student> selectStudentList() {
        return studentMapper.selectStudentList();
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
    @Override
    public int deleteStudent(String sno) {
        return studentMapper.deleteStu(sno);
    }
}
