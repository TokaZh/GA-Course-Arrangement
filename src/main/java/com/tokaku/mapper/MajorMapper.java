package com.tokaku.mapper;

import com.tokaku.pojo.Major;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
@Repository
public interface MajorMapper {
    Set<Major> selectMajorList();

    String selectMajorNameByMajorId(String majorId);
}
