package com.tokaku.service.impl;

import com.tokaku.mapper.TeacherMapper;
import com.tokaku.pojo.Teacher;
import com.tokaku.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TeacherServiceImpl implements TeacherService {
    private TeacherMapper teacherMapper;

    @Autowired
    public void TeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Override
    public int TeacherCheck(String teacherId, String teacherPassword) {
        Teacher teacher = teacherMapper.selectTeacherById(teacherId);
        //用户名不存在
        if (teacher == null) {
            return -1;
        } else {
            if (teacher.getTeacherPassword().equals(teacherPassword)) {
                //登录成功
                return 1;
            } else {
                //密码错误
                return 0;
            }
        }
    }

    @Override
    public Teacher selectTeacherById(String teacherId) {
        return teacherMapper.selectTeacherById(teacherId);
    }

    @Override
    public List<Teacher> selectTeacherList() {
        return teacherMapper.selectTeacherList();
    }

    @Override
    public int addTeacher(Teacher teacher) {
        if (teacherMapper.selectTeacherById(teacher.getTeacherId()) == null) {
            teacherMapper.addTeacher(teacher);
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteTeacher(String teacherId) {
        return teacherMapper.deleteTeacher(teacherId);
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        return teacherMapper.updateTeacher(teacher);
    }
}
