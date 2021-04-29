package com.tokaku.mapper;

import com.tokaku.pojo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface CourseMapper {

    Set<Course> selectCourseList();

    Course selectCourseByCourseId(String courseId);

    List<Course> queryCourseByGradeAndMajor(int major, int grade);

    int addCourse(Course course);

    int updateCourse(Course course);

    int deleteCourseByCourseId(int courseId);

}
