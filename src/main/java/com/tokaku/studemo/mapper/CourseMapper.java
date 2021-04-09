package com.tokaku.studemo.mapper;

import com.tokaku.studemo.pojo.Course;
import com.tokaku.studemo.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseMapper {
    int addCourse();

    int updateCourse();

    int deleteCourse();

    List<Course> queryCourseList();
}
