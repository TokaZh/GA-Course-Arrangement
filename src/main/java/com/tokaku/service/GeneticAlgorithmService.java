package com.tokaku.service;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.Individual;

import java.util.HashMap;
import java.util.Set;

public interface GeneticAlgorithmService {

    HashMap<String, Integer> initGene(Set<Course> courseList, int weekSize);

    String[] initChromosome(int timeSize, HashMap<String, Integer> genes);

    Individual initIndividual(int classNum, int timeSize, HashMap<String, Integer> genes);

    HashMap<Integer, Set<Integer>> checkConflict(String[][] individual);

    String[][] dealConflict(String[][] individual, HashMap<Integer, Set<Integer>> signMap);

    //weeksize从学期数据得到
    Set<Individual> initPopulation(Set<Course> courses, int classNum, int populationSize);

    double getFitness(String[][] chromosome);

    Set<Individual> evolution(Set<Individual> population);

    //选择(selection)；交叉(crossover)；变异(mutation)
    Set<Individual> selection(Set<Individual> population);

    Set<Individual> crossover(Set<Individual> population);

    Set<Individual> mutation(Set<Individual> population);
}
