package com.tokaku.controller;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.Individual;
import com.tokaku.service.CourseService;
import com.tokaku.service.GeneticAlgorithmService;
import com.tokaku.service.MajorService;
import com.tokaku.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Set;

@Controller
public class ScheduleController {

    ScheduleService scheduleService;

    @Autowired
    public void scheduleMapper(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    MajorService majorService;

    @Autowired
    public void majorService(MajorService majorService) {
        this.majorService = majorService;
    }

    GeneticAlgorithmService geneticAlgorithmService;

    @Autowired
    public void GeneticAlgorithmService(GeneticAlgorithmService geneticAlgorithmService) {
        this.geneticAlgorithmService = geneticAlgorithmService;
    }

    CourseService courseService;

    @Autowired
    public void CourseService(CourseService courseService) {
        this.courseService = courseService;
    }


    @RequestMapping("/schedule")
    public String GetCurriculum(Model model) {
        //搜索对应课程表 参数：专业MajorId，年级Grade
        String majorId = "1";
        String term = "1";
        Set<Course> courses = courseService.selectCourseByGrade(majorId, term);
////        2.排课
        HashMap<String, Integer> gene = geneticAlgorithmService.initGene(courses, 25);
        HashMap<String, Integer> scoreWeight = geneticAlgorithmService.getScoreWeight(courses);
        Individual individual = geneticAlgorithmService.initIndividual(4, 25, gene, scoreWeight);
        String[][] schedule = individual.getSchedule();

        model.addAttribute("schedule", schedule[0]);
        String[] head = new String[]{"第一节", "第二节", "第三节", "第四节", "第五节"};
        model.addAttribute("head", head);

        HashMap<String, String> courseMap = new HashMap<>();
        for (Course cours : courses) {
            courseMap.put(cours.getCourseId(), cours.getCourseName());
        }
        for (int i = 0; i < schedule[0].length; i++) {
            if (schedule[0][i] != null) {
                schedule[0][i] = courseMap.get(schedule[0][i]);
            }
        }
//        System.out.println(Arrays.toString(schedule[0]));
        return "schedule/schedule";
    }
}
