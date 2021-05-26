package com.tokaku.service.impl;

import com.tokaku.mapper.CourseMapper;
import com.tokaku.pojo.Course;
import com.tokaku.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    public Set<Course> selectCourseByTerm(String majorId, int term) {
        return courseMapper.selectCourseByTerm(majorId, term);
    }

    @Override
    public HashMap<String, String> selectCourseMapByTerm(String majorId, int term) {
        Set<Course> courseSet = courseMapper.selectCourseByTerm(majorId, term);
        HashMap<String, String> resultMap = new HashMap<>();
        for (Course course : courseSet) {
            resultMap.put(course.getCourseId(), course.getCourseName());
        }
        return resultMap;
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
    public boolean addCourseHasId(Course course) {
        if (courseMapper.selectCourse(course) == null) {
            courseMapper.addCourseHasId(course);
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
