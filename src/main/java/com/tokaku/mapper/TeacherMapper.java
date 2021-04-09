package com.tokaku.mapper;

import com.tokaku.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherMapper {
    Teacher TeacherCheck(String username, String password);

    int addTeacher();

    int updateTeacher();

    int deleteTeacher();

    List<Teacher> queryTeacherList();

}