package com.tokaku.service;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.Schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface GeneticAlgorithmService {

    HashMap<String, Integer> initGene(Set<Course> courseList, int weekSize);

    String[] initChromosome(int timeSize, HashMap<String, Integer> genes);

    String[][] initIndividual(int classNum, int timeSize, HashMap<String, Integer> genes);

    HashMap<Integer, Set<Integer>> checkConflict(int classNum, int timeSize, String[][] individual);

    String[][] dealConflict(String[][] individual, HashMap<Integer, Set<Integer>> signMap);

    //weeksize从学期数据得到
    Set<String[][]> initPopulation(Set<Course> courses, int populationSize, int weekSize);

    int getFitness(String[][] chromosome, Set<Course> courses);

    //选择(selection)；交叉(crossover)；变异(mutation)
    Set<List<Schedule>> selection();

    Set<List<Schedule>> crossover();

    Set<List<Schedule>> mutation();
}
