package com.tokaku.service;

import com.tokaku.pojo.Grade;

import java.util.Set;

public interface GradeService {
    Set<Grade> selectGradeList();

    boolean addGrade(Grade grade);

    boolean deleteGradeByGradeId(String gradeId);
}
