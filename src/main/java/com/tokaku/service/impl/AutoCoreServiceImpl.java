package com.tokaku.service.impl;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.Individual;
import com.tokaku.service.AutoCoreService;
import com.tokaku.service.GeneticAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

@Service
public class AutoCoreServiceImpl implements AutoCoreService {
    private static final int POPULATION_SIZE = 10;
    private static final int N = 10;

    GeneticAlgorithmService service;

    @Autowired
    public void GeneticAlgorithmService(GeneticAlgorithmService service) {
        this.service = service;
    }

    public String[][] AutoArrangementCourse(Set<Course> courses, int classNum) {
        //生成种群
        Set<Individual> population = service.initPopulation(courses, classNum, POPULATION_SIZE);
        //进行n次进化
//        int n = 0;
//        while (n<=N){
//            population = service.evolution(population);
//            n++;
//        }

        //二维数组课表，填入
        //筛选出适应度最高的课表
        Iterator<Individual> iterator = population.iterator();

        return iterator.next().getSchedule();
    }
}
