package com.tokaku.mapper;

import com.tokaku.pojo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
@Repository
public interface CourseMapper {

    Set<Course> selectCourseList();

    Course selectCourseByCourseId(String courseId);

    int addCourse(Course course);

    int updateCourse(Course course);

    int deleteCourseByCourseId(String courseId);

    Course selectCourse(Course course);

    Set<Course> selectCourseByTerm(String majorId, int term);

    int addCourseHasId(Course course);
}
