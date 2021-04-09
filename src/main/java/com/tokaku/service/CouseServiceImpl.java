package com.tokaku.service;

import com.tokaku.mapper.CourseMapper;
import com.tokaku.pojo.Course;
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
