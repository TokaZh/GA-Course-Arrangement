package com.tokaku.mapper;

import com.tokaku.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {

    Student selectStudentById(String studentId);

    Student StudentCheck(String studentId, String studentPassword);

    int addStu();

    int updateStu();

    int deleteStu(String sno);

    List<Student> selectStudentList();

}
