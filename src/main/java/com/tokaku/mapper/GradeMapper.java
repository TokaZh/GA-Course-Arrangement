package com.tokaku.mapper;

import com.tokaku.pojo.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GradeMapper {
    List<Grade> selectGradeList();

    Grade selectGradeByMajor();
}
