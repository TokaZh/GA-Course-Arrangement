package com.tokaku.service.impl;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.Individual;
import com.tokaku.service.GeneticAlgorithmService;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.tokaku.utils.RandomUtil.initTimePart;

@Service
public class GeneticAlgorithmServiceImpl implements GeneticAlgorithmService {
    //生成基因库（周课时）,传入：学期课表 传出:周课表
    @Override
    public HashMap<String, Integer> initGene(Set<Course> courseList, int weekSize) {
        HashMap<String, Integer> genes = new HashMap<>();
        for (Course course : courseList) {
            int weekTime = course.getTime() / weekSize;//2位
            genes.put(course.getCourseId(), weekTime);
        }
        return genes;
    }

    //生成染色体(一个班的周课表)
    @Override
    public String[] initChromosome(int timeSize, HashMap<String, Integer> genes) {
        String[] chromosome = new String[timeSize];
        for (String gene : genes.keySet()) {
            for (int i = 0; i < genes.get(gene); i++) {
                //随机生成空时间片
                int timePart = initTimePart(timeSize);
                if (chromosome[timePart] != null) {
                    while (chromosome[timePart] == null) {
                        timePart = initTimePart(timeSize);
                    }
                }
                //将课程插入空时间片
                chromosome[timePart] = String.valueOf(gene);
            }
        }
        return chromosome;
    }

    @Override
    public Individual initIndividual(int classNum, int timeSize, HashMap<String, Integer> genes, HashMap<String, Integer> scoreWeight) {

        String[][] conflictSchedule = new String[classNum][timeSize];
        for (int i = 0; i < classNum; i++) {
            conflictSchedule[i] = initChromosome(timeSize, genes);
        }
        //解决冲突
        //遍历时间节点,找出冲突点
        HashMap<Integer, Set<Integer>> signSet = checkConflict(conflictSchedule);
        //遍历冲突点，进行交换，解决冲突，直到无冲突
        String[][] schedule = dealConflict(conflictSchedule, signSet);

        return new Individual(schedule, getFitness(schedule, scoreWeight));
    }

    //应当返回班级,冲突
    @Override
    public HashMap<Integer, Set<Integer>> checkConflict(String[][] individual) {
        int classNum = individual.length;
        int timeSize = individual[0].length;
        HashMap<Integer, Set<Integer>> signMap = new HashMap<>();
        boolean[][] sign = new boolean[classNum][timeSize];
        //按时间遍历 目的为查看某一时间片的重复课程
        for (int timePart = 0; timePart < timeSize; timePart++) {
            //用于检测某时间片是否有重复
            Set<String> checkSet = new HashSet<>();
            int setSize = 0;
            for (int clazz = 0; clazz < classNum; clazz++) {
                checkSet.add(individual[clazz][timePart]);
                if (checkSet.size() != setSize) {//也就是这个数据没有重复
                    setSize++;
                } else {
                    sign[clazz][timePart] = true;
                }
            }
        }
        for (int i = 0; i < sign.length; i++) {
            Set<Integer> classSign = new HashSet<>();
            for (int j = 0; j < sign[i].length; j++) {
                if (sign[i][j]) {
                    classSign.add(j);
                }
            }
            signMap.put(i, classSign);
        }

        return signMap;
    }

    //解决冲突,大致思路为将冲突课程右移一位
    @Override
    public String[][] dealConflict(String[][] individual, HashMap<Integer, Set<Integer>> signMap) {
        for (Integer classNum : signMap.keySet()) {
            Set<Integer> classSign = signMap.get(classNum);
            if (classSign.isEmpty()) continue;
            String firstPart = null;
            int prePart = -1;
            for (Integer timePart : classSign) {
                if (prePart < 0) {
                    firstPart = individual[classNum][timePart];
                } else {
                    String temp = individual[classNum][prePart];
                    individual[classNum][prePart] = individual[classNum][timePart];
                    individual[classNum][timePart] = temp;
                }
                prePart = timePart;
            }
            individual[classNum][prePart] = firstPart;
        }

        return individual;
    }

    @Override
    public int getFitness(String[][] individual, HashMap<String, Integer> scoreWeight) {

        int fitness = 0;
        HashMap<Integer, HashMap<String, List<Integer>>> course = new HashMap<>();
        //1. 课程越靠前加分越多
        for (int classNum = 0; classNum < individual.length; classNum++) {
            HashMap<String, List<Integer>> classMap = new HashMap<>();
            for (int timePart = 0; timePart < individual[classNum].length; timePart++) {
                if (individual[classNum][timePart] != null) {
                    //计算课程时间适应度
                    fitness = fitness + (5 - timePart % 5) * scoreWeight.get(individual[classNum][timePart]);

                    //为之后的计算离散度建立集合
                    List<Integer> courseList = new ArrayList<>();
                    if (classMap.containsKey(individual[classNum][timePart])) {
                        courseList = classMap.get(individual[classNum][timePart]);
                    }
                    courseList.add(timePart);
                    classMap.put(individual[classNum][timePart], courseList);
                }
            }
            course.put(classNum, classMap);
        }

        //2.同班课程越离散加分越多
        for (Integer classNun : course.keySet()) {
            HashMap<String, List<Integer>> classMap = course.get(classNun);
            for (String courseId : classMap.keySet()) {
                List<Integer> list = classMap.get(courseId);
                //进行排序，然后计算间隔
                Collections.sort(list);
                int pre = -1;
                for (Integer integer : list) {
                    if (pre > 0) {
                        fitness = integer - pre;
                    }
                    pre = integer;
                }

            }
        }

        return fitness;
    }

    @Override
    public HashMap<String, Integer> getScoreWeight(Set<Course> courses) {
        HashMap<String, Integer> scoreWeight = new HashMap<>();
        for (Course course : courses) {
            scoreWeight.put(course.getCourseId(), course.getScore());
        }
        return scoreWeight;
    }

    //生成种群并计算适应度
    @Override
    public Set<Individual> initPopulation(Set<Course> courses, int weekSize, int populationSize) {
        int timeSize = 25;//从课程得到
        int classNum = 4;//从年级数据得到
        Set<Individual> population = new HashSet<>();

        HashMap<String, Integer> scoreWeight = getScoreWeight(courses);
        HashMap<String, Integer> genes = initGene(courses, weekSize);

        if (genes.size() > 25) timeSize = 36;

        for (int i = 0; i < populationSize; i++) {
            Individual individual = initIndividual(classNum, timeSize, genes, scoreWeight);
            population.add(individual);
        }

        return population;
    }

    private static final int DAISHU = 300;

    //进化
    @Override
    public Set<Individual> evolution(Set<Individual> population) {
        Set<Individual> childPopulation = new HashSet<>();
        float sumFitness = 0;
        for (Individual individual : population) {
            sumFitness += individual.getFitness();
        }
        float averageFitness = sumFitness / population.size();
        for (Individual individual : population) {
            int fitness = individual.getFitness();

            //适应度高直接保留到下一代
            if (fitness > averageFitness) {
                childPopulation.add(individual);
                continue;
            } else {//适应度低进行进化
                float persent = fitness / sumFitness;
                String[][] schedule = individual.getSchedule();


            }


            //生成随机数 对应概率

        }

        return childPopulation;
    }

    //敷衍  基于n次进化


    @Override
    public String[][] selection(String[][] individual) {
        return null;
    }

    //交叉  按概率交叉 9
    @Override
    public String[][] crossover(String[][] individual) {


        return dealConflict(individual, checkConflict(individual));
    }

    //变异 按概率变异
    @Override
    public String[][] mutation(String[][] individual) {


        return dealConflict(individual, checkConflict(individual));
    }
}
