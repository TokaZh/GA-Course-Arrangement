package com.tokaku.service.impl;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.Individual;
import com.tokaku.service.GeneticAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AutoCoreServiceImpl {
    private static final int POPULATION_SIZE = 200;

    GeneticAlgorithmService service;

    @Autowired
    public void GeneticAlgorithmService(GeneticAlgorithmService service) {
        this.service = service;
    }

    public String[][] AutoArrangementCourse(Set<Course> courses) {
        //生成种群
        Set<Individual> population = service.initPopulation(courses, 25, POPULATION_SIZE);
        //进行进化

        //二维数组课表，填入

        return new String[25][0];
    }
}
