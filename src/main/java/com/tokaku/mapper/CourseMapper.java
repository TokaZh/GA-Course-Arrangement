package com.tokaku.mapper;

import com.tokaku.pojo.Course;
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
