package com.tokaku.service;

import com.tokaku.pojo.Course;

import java.util.HashMap;
import java.util.Set;

public interface CourseService {

    Set<Course> selectCourseList();

    int deleteCourseByCourseId(String courseId);

    Course selectCourseByCourseId(String courseId);

    boolean addCourse(Course course);

    boolean addCourseHasId(Course course);

    int updateCourse(Course course);

    Set<Course> selectCourseByTerm(String majorId, int term);

    HashMap<String, String> selectCourseMapByTerm(String majorId, int term);

}
