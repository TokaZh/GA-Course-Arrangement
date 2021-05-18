package com.tokaku.service.impl;

import com.tokaku.mapper.GradeMapper;
import com.tokaku.pojo.Grade;
import com.tokaku.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GradeServiceImpl implements GradeService {
    GradeMapper gradeMapper;

    @Autowired
    public void gradeMapper(GradeMapper gradeMapper) {
        this.gradeMapper = gradeMapper;
    }

    @Override
    public Set<Grade> selectGradeList() {
        return gradeMapper.selectGradeList();
    }

    @Override
    public boolean addGrade(Grade grade) {
        if (gradeMapper.selectGradeByGradeId(grade.getGradeId()) == null) {
            gradeMapper.addGrade(grade);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteGradeByGradeId(String gradeId) {
        if (gradeMapper.selectGradeByGradeId(gradeId) != null) {
            gradeMapper.deleteGrade(gradeId);
            return true;
        } else {
            return false;
        }
    }


}
