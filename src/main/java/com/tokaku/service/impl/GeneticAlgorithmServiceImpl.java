package com.tokaku.service.impl;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.Individual;
import com.tokaku.service.GeneticAlgorithmService;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.tokaku.utils.RandomUtil.initProbability;
import static com.tokaku.utils.RandomUtil.initTimePart;

@Service
public class GeneticAlgorithmServiceImpl implements GeneticAlgorithmService {
    private static final double CROSSOVER_PROBABILITY = 0.9;
    private static final double MUTATION_PROBABILITY = 0.1;

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
                int dayPart = initTimePart(5);
                //如果dayPart这天没有课程gene 则随机生成timePart
                //否则 重新生成dayPart
                while (!isNullDay(chromosome, gene, dayPart)) {
                    dayPart = initTimePart(5);
                }
                int timePart = dayPart * 5 + initTimePart(5);
                //将课程插入空时间片
                chromosome[timePart] = gene;
            }
        }
        return chromosome;
    }

    //如果chromosome[timePart]内有值  则返回假（不是空天）
    //如果目标位置为空 则进行循环  如果目标不为空 且内容与课程相同则返回假
    //否则返回真
    private boolean isNullDay(String[] chromosome, String course, int dayPart) {
        for (int part = dayPart * 5; part < (dayPart + 1) * 5; part++) {
            if (chromosome[part] != null) {
                if (chromosome[part].equals(course)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Individual initIndividual(int classNum, int timeSize, HashMap<String, Integer> genes) {

        String[][] conflictSchedule = new String[classNum][timeSize];
        for (int i = 0; i < classNum; i++) {
            conflictSchedule[i] = initChromosome(timeSize, genes);
        }
        //解决冲突
        //遍历时间节点,找出冲突点
        HashMap<Integer, Set<Integer>> signSet = checkConflict(conflictSchedule);
        //遍历冲突点，进行交换，解决冲突，直到无冲突
        String[][] schedule = dealConflict(conflictSchedule, signSet);

        return new Individual(schedule, getFitness(schedule));
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
//            String firstPart = null;
//            int prePart = -1;
            for (Integer timePart : classSign) {
//                if (prePart < 0) {
//                    firstPart = individual[classNum][timePart];
//                } else {
//                    String temp = individual[classNum][prePart];
//                    individual[classNum][prePart] = individual[classNum][timePart];
//                    individual[classNum][timePart] = temp;
//                }
//                prePart = timePart;
                boolean flag = false;
                for (int i = timePart / 5 * 5; i < (timePart / 5 + 1) * 5; i++) {
                    if (individual[classNum][timePart] == null && i != timePart) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    String temp = individual[classNum][timePart];
                    individual[classNum][timePart] = null;
                    int i = initTimePart(5) + (timePart / 5) * 5;
                    while (individual[classNum][i] != null || i == timePart) {
                        i = initTimePart(5) + (timePart / 5) * 5;
                    }
                    individual[classNum][i] = temp;
                }
            }
//            individual[classNum][prePart] = firstPart;
        }

        return individual;
    }

    @Override
    public double getFitness(String[][] individual) {
        double fitness = 0;
        double f1 = 0, f2 = 0, f3 = 0;
        int num = 0, num2 = 0, num3 = 0;
        double[] weight = new double[]{
                0.9, 1, 0.9, 1, 1,
                0.8, 0.9, 0.8, 0.9, 0.9,
                0.7, 0.8, 0.7, 0.8, 0.8,
                0.6, 0.7, 0.6, 0.7, 0.4,
                0.5, 0.6, 0.5, 0.6, 0.3
        };

        //1. 课程越靠前加分越多
//        for (String[] chromosome : individual) {
//            for (int timePart = 0; timePart < chromosome.length; timePart++) {
//                if (chromosome[timePart] != null) {
//                    //计算课程时间适应度
//                    fitness += weight[timePart];
//                }
//            }
//        }

        //2.同班课程越离散加分越多
//        for (String[] chromosome : individual) {
//            HashMap<String, Integer> map = new HashMap<>();
//            for (int timePart = 0; timePart < chromosome.length; timePart++) {
//                //如果已经有这个课程
//                if (map.containsKey(chromosome[timePart])) {
//                    int preTimePart = map.get(chromosome[timePart]);
//                    fitness += (timePart - preTimePart) * 0.1;
//
//                }
//                //如果没有这个课程
//                map.put(chromosome[timePart], timePart);
//            }
//        }

        for (String[] chromosome : individual) {
            HashMap<String, Integer> map = new HashMap<>();
            for (int timePart = 0; timePart < chromosome.length; timePart++) {

                //1.课程越早越好
                if (chromosome[timePart] != null) {
                    //n*classNum个课
                    num++;
                    //计算课程时间适应度
                    f1 += weight[timePart];
                }

                //2.课程排列越离散越好
                if (map.containsKey(chromosome[timePart])) {
                    //如果已经有这个课程
                    int preTimePart = map.get(chromosome[timePart]);
                    f2 += (timePart - preTimePart) * 0.1;
                    num2++;
                }
                //如果没有这个课程
                map.put(chromosome[timePart], timePart);
            }
        }

        //3.不同班的同个课相邻越近加分越多
        for (int timePart = 0; timePart < individual[0].length - 1; timePart += 2) {
            for (int classNum = 0; classNum < individual.length; classNum++) {
                Set<String> set = new HashSet<>();
                int count = 0;
                for (int step = 0; step < 2; step++) {
                    if (individual[classNum][timePart + step] == null) {
                        set.add(individual[classNum][timePart + step]);
                        count++;
                        num3++;
                    }
                }
                f3 += (count - set.size()) * 0.1;
            }
        }

        //4.一天一个班级只能上同一课程一次

        return f1 / num + f2 / num2 + f3 / num3;
    }

    //生成种群并计算适应度
    @Override
    public Set<Individual> initPopulation(Set<Course> courses, int classNum, int populationSize) {
        //每周25个课时
        int timeSize = 25;//从课程得到
        //一学期25周
        int weekSize = 25;
        Set<Individual> population = new TreeSet<>();

        HashMap<String, Integer> genes = initGene(courses, weekSize);

        if (genes.size() > 25) timeSize = 36;

        while (population.size() <= populationSize) {
            Individual individual = initIndividual(classNum, timeSize, genes);
            population.add(individual);
        }

        return population;
    }

    //进化
    @Override
    public Set<Individual> evolution(Set<Individual> population) {
        Set<Individual> childPopulation = new TreeSet<>();

        //选择
        childPopulation.addAll(selection(population));

        //交叉 按概率
        childPopulation.addAll(crossover(population));

        //变异
        childPopulation.addAll(mutation(population));

        return childPopulation;
    }

    private int getSumFitness(Set<Individual> population) {
        int sumFitness = 0;
        //计算总适应度
        for (Individual individual : population) {
            sumFitness += individual.getFitness();
        }
        return sumFitness;
    }

    @Override
    public Set<Individual> selection(Set<Individual> population) {
        //得出总适应度
        float sumFitness = getSumFitness(population);
        TreeSet<Individual> childPopulation = new TreeSet<>();
        //使用迭代器进行遍历
        Iterator<Individual> iterator = population.iterator();
        //将适应度最高的个体复制到下一代
        if (iterator.hasNext()) {
            childPopulation.add(iterator.next());
        }
        //遍历
        while (iterator.hasNext()) {
            Individual individual = iterator.next();
            //使用轮盘赌法进行选择复制
            if (initProbability() < individual.getFitness() / sumFitness) {
                childPopulation.add(individual);
            }
        }
        return childPopulation;
    }

    //交叉  按概率交叉
    @Override
    public Set<Individual> crossover(Set<Individual> population) {
        Set<Individual> childPopulation = new TreeSet<>();
//        dealConflict(individual, checkConflict(individual))
        Iterator<Individual> iterator = population.iterator();
        Individual bestIndividual = null;
        if (iterator.hasNext()) bestIndividual = iterator.next();
        while (iterator.hasNext()) {
            Individual i1 = iterator.next();
            Individual i2;
            if (iterator.hasNext()) {
                i2 = iterator.next();
            } else {
                i2 = bestIndividual;
            }
            //交叉
            if (initProbability() < CROSSOVER_PROBABILITY) {
                String[][] s1 = i1.getSchedule();
                String[][] s2 = i2.getSchedule();
                //交叉第一个班的课表
                String[] temp = s1[0];
                s1[0] = s2[0];
                s2[0] = temp;
                i1.setSchedule(dealConflict(s1, checkConflict(s1)));
                i2.setSchedule(dealConflict(s2, checkConflict(s2)));
                i1.setFitness(getFitness(i1.getSchedule()));
                i2.setFitness(getFitness(i2.getSchedule()));
                childPopulation.add(i1);
                childPopulation.add(i2);
            }
        }
        return childPopulation;
    }

    //变异 按概率变异
    @Override
    public Set<Individual> mutation(Set<Individual> population) {
        Set<Individual> childPopulation = new TreeSet<>();
        for (Individual individual : population) {
            if (initProbability() < MUTATION_PROBABILITY) {
                String[][] schedule = individual.getSchedule();
                //将第一节课和最后一节课进行交换
                for (int classNum = 0; classNum < schedule.length; classNum++) {
                    String temp = schedule[classNum][0];
                    schedule[classNum][0] = schedule[classNum][schedule[0].length - 1];
                    schedule[classNum][schedule[0].length - 1] = temp;
                }
                individual.setSchedule(schedule);
                individual.setFitness(getFitness(schedule));
                childPopulation.add(individual);
            }
        }
        return childPopulation;
    }
}
