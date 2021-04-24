package com.tokaku.service.impl;

import com.tokaku.mapper.CourseMapper;
import com.tokaku.pojo.Course;
import com.tokaku.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {
    CourseMapper courseMapper;

    @Autowired
    public void CourseMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public Set<Course> selectCourseList() {
        return courseMapper.selectCourseList();
    }

//    @Override
//    public List<Course> queryCourseByGradeId(Grade grade) {
//        return courseMapper.selectCourseByGradeId(grade.getGradeId());
//    }

    @Override
    public int deleteCourseByCourseId(int courseId) {
        return courseMapper.deleteCourseByCourseId(courseId);
    }

    @Override
    public boolean addCourse(Course course) {
        if (courseMapper.selectCourseByCourseId(course.getCourseId()) == null) {
            courseMapper.addCourse(course);
            return true;
        } else {
            return false;
        }
    }


}
