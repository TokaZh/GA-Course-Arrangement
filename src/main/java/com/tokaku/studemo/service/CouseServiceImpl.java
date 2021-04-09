package com.tokaku.studemo.service;

import com.tokaku.studemo.mapper.CourseMapper;
import com.tokaku.studemo.mapper.StudentMapper;
import com.tokaku.studemo.pojo.Course;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CouseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Override
    public List<Course> queryCourseList() {
        return courseMapper.queryCourseList();
    }
}
