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

    @Override
    public Set<Course> selectCourseByGrade(String majorId, String term) {
        return courseMapper.selectCourseByGrade(majorId, term);
    }

//    @Override
//    public List<Course> queryCourseByGradeId(Grade grade) {
//        return courseMapper.selectCourseByGradeId(grade.getGradeId());
//    }

    @Override
    public int deleteCourseByCourseId(String courseId) {
        return courseMapper.deleteCourseByCourseId(courseId);
    }

    @Override
    public Course selectCourseByCourseId(String courseId) {
        return courseMapper.selectCourseByCourseId(courseId);
    }

    @Override
    public boolean addCourse(Course course) {
        if (courseMapper.selectCourse(course) == null) {
            courseMapper.addCourse(course);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }


}
