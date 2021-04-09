package com.tokaku.studemo.mapper;

import com.tokaku.studemo.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ScheduleMapper {
    int addCur();

    int updateCur();

    int deleteCur();

    List<Student> queryCurList();
}
