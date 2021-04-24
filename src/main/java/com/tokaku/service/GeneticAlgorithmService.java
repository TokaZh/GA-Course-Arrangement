package com.tokaku.service;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.Schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface GeneticAlgorithmService {

    HashMap<Integer, Integer> initGene(Set<Course> courseList, int weekSize);

    String[] initChromosome(int timeSize, HashMap<Integer, Integer> genes);

    String[][] initIndividual(int classNum, int timeSize, HashMap<Integer, Integer> genes);

    HashMap<Integer, Set<Integer>> checkConflict(int classNum, int timeSize, String[][] individual);

    String[][] dealConflict(String[][] individual, HashMap<Integer, Set<Integer>> signMap);

    Set<String[][]> initPopulation(int populationSize);

    int getFitness(String[][] chromosome);

    //选择(selection)；交叉(crossover)；变异(mutation)
    Set<List<Schedule>> selection();

    Set<List<Schedule>> crossover();

    Set<List<Schedule>> mutation();
}
