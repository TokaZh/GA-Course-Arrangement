package com.tokaku.mapper;

import com.tokaku.pojo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
@Repository
public interface CourseMapper {

    Set<Course> selectCourseList();

    Course selectCourseByCourseId(int courseId);

//    List<Course> selectCourseByGradeId(int gradeId);

    int addCourse(Course course);

    int updateCourse();

    int deleteCourseByCourseId(int courseId);

}
