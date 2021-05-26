package com.tokaku.mapper;

import com.tokaku.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherMapper {


    int addTeacher(Teacher teacher);

    int deleteTeacher(String teacherId);

    List<Teacher> selectTeacherList();

    Teacher selectTeacherById(String teacherId);

    int updateTeacher(Teacher teacher);
}
