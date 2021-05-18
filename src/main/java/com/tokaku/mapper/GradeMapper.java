package com.tokaku.mapper;

import com.tokaku.pojo.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
@Repository
public interface GradeMapper {
    Set<Grade> selectGradeList();

    Grade selectGradeByGradeId(String GradeId);

    void addGrade(Grade grade);

    void deleteGrade(String gradeId);

    Grade selectGradeByMajor();
}
