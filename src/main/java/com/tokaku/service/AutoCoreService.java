package com.tokaku.service;

import com.tokaku.pojo.Course;

import java.util.Set;

public interface AutoCoreService {
    String[][] AutoArrangementCourse(Set<Course> courses, int classNum);
}
