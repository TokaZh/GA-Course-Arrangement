package com.tokaku.service;

import com.tokaku.pojo.Major;

import java.util.HashMap;
import java.util.Set;

public interface MajorService {
    Set<Major> selectMajorList();

    HashMap<String, String> selectMajorMap();

    String selectMajorNameByMajorId(String majorId);
}
