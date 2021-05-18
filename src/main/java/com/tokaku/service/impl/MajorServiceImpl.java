package com.tokaku.service.impl;

import com.tokaku.mapper.MajorMapper;
import com.tokaku.pojo.Major;
import com.tokaku.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
public class MajorServiceImpl implements MajorService {
    MajorMapper majorMapper;

    @Autowired
    public void majorMapper(MajorMapper majorMapper) {
        this.majorMapper = majorMapper;
    }

    @Override
    public Set<Major> selectMajorList() {
        return majorMapper.selectMajorList();
    }

    @Override
    public HashMap<String, String> selectMajorMap() {
        Set<Major> majors = majorMapper.selectMajorList();
        HashMap<String, String> majorMap = new HashMap<>();
        for (Major major : majors) {
            majorMap.put(major.getMajorId(), major.getMajorName());
        }
        return majorMap;
    }

    @Override
    public String selectMajorNameByMajorId(String majorId) {
        return majorMapper.selectMajorNameByMajorId(majorId);
    }
}
