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

    @Override
    public Student selectStudentById(String studentId) {
        return studentMapper.selectStudentById(studentId);
    }

    @Override
    public List<Student> selectStudentList() {
        return studentMapper.selectStudentList();
    }

    @Override
    public int deleteStudent(String sno) {
        return studentMapper.deleteStu(sno);
    }

    @Override
    public int StudentCheck(String studentId, String studentPassword) {
        Student student = studentMapper.selectStudentById(studentId);
        //用户名不存在
        if (student == null) {
            return -1;
        } else {
            if (student.getStudentPassword().equals(studentPassword)) {
                //登录成功
                return 1;
            } else {
                //密码错误
                return 0;
            }
        }
    }

}
