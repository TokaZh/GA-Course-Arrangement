package com.tokaku.studemo.mapper;

import com.tokaku.studemo.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {
    Student StudentCheck(String username, String password);

    int addStu();

    int updateStu();

    int deleteStu(String sno);

    List<Student> queryStuList();
}
