package com.tokaku.service.impl;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.Schedule;
import com.tokaku.service.GeneticAlgorithmService;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.tokaku.utils.RandomUtil.initTimePart;

@Service
public class GeneticAlgorithmServiceImpl implements GeneticAlgorithmService {
    //生成基因库（周课时）,传入：学期课表 传出:周课表
    @Override
    public HashMap<Integer, Integer> initGene(Set<Course> courseList, int weekSize) {
        HashMap<Integer, Integer> genes = new HashMap<>();
        for (Course course : courseList) {
            int weekTime = course.getTime() / weekSize;//2位
            genes.put(course.getCourseId(), weekTime);
        }
        return genes;
    }

    //生成染色体(一个班的周课表)
    @Override
    public String[] initChromosome(int timeSize, HashMap<Integer, Integer> genes) {
        String[] chromosome = new String[timeSize];
        for (int gene : genes.keySet()) {
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
    public String[][] initIndividual(int classNum, int timeSize, HashMap<Integer, Integer> genes) {
        String[][] individual = new String[classNum][timeSize];
        for (int i = 0; i < classNum; i++) {
            individual[i] = initChromosome(timeSize, genes);
        }
        //解决冲突
//        遍历时间节点,找出冲突点
        HashMap<Integer, Set<Integer>> signSet = checkConflict(classNum, timeSize, individual);
//        遍历冲突点，进行交换，解决冲突，直到无冲突
        //dealConflict


        return dealConflict(individual, signSet);
    }

    //应当返回班级,冲突
    @Override
    public HashMap<Integer, Set<Integer>> checkConflict(int classNum, int timeSize, String[][] individual) {
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
    public int getFitness(String[][] chromosome) {
        int fitness = 0;
        HashMap<Integer, HashMap<String, List<Integer>>> course = new HashMap<>();
        //1. 课程越靠前加分越多
        for (int classNum = 0; classNum < chromosome.length; classNum++) {
            HashMap<String, List<Integer>> classMap = new HashMap<>();
            for (int timePart = 0; timePart < chromosome[classNum].length; timePart++) {
                if (chromosome[classNum][timePart] != null) {
                    //计算课程时间适应度
                    fitness += (5 - timePart % 5);

                    //为之后的计算离散度建立集合
                    List<Integer> courseList = new ArrayList<>();
                    if (classMap.containsKey(chromosome[classNum][timePart])) {
                        courseList = classMap.get(chromosome[classNum][timePart]);
                    }
                    courseList.add(timePart);
                    classMap.put(chromosome[classNum][timePart], courseList);
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
    public Set<String[][]> initPopulation(int populationSize) {
        int timeSize = 25;//从课程得到
        Set<Course> courses = null;//从课程数据中得到
        int weekSize = 0;//从学期数据得到
        int classNum = 0;//从年级数据得到
        Set<String[][]> population = new HashSet<>();
        HashMap<String[][], Integer> fitnessMap = new HashMap<>();

        HashMap<Integer, Integer> genes = initGene(courses, weekSize);
        if (genes.size() > 25) {
            timeSize = 36;
        }
        int fitnessSun = 0;
        for (int i = 0; i < populationSize; i++) {
            String[][] individual = initIndividual(classNum, timeSize, genes);
            population.add(individual);
            int fitness = getFitness(individual);
            fitnessSun += fitness;
            fitnessMap.put(individual, fitness);
        }
        return population;
    }


    @Override
    public Set<List<Schedule>> selection() {
        return null;
    }

    @Override
    public Set<List<Schedule>> crossover() {
        return null;
    }

    @Override
    public Set<List<Schedule>> mutation() {
        return null;
    }
}
