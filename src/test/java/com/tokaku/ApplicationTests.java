package com.tokaku;

import com.tokaku.mapper.ScheduleMapper;
import com.tokaku.mapper.TeacherMapper;
import com.tokaku.pojo.Course;
import com.tokaku.pojo.Schedule;
import com.tokaku.service.AutoCoreService;
import com.tokaku.service.CourseService;
import com.tokaku.service.ImportExcelService;
import com.tokaku.service.ScheduleService;
import com.tokaku.service.impl.GeneticAlgorithmServiceImpl;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@MapperScan({"com.tokaku.mapper"})
@SpringBootTest
class ApplicationTests {
    @Autowired
    CourseService courseService;

    @Autowired
    ImportExcelService importExcelService;

    @Autowired
    GeneticAlgorithmServiceImpl geneticAlgorithmService;

    @Autowired
    AutoCoreService autoCoreService;

    @Test
    void GATest() {
//        1.获取课表
//        根据专业和年级检索
        Set<Course> courses = courseService.selectCourseList();

//        2.排课
//        Set<Individual> population = geneticAlgorithmService.initPopulation(courses,4,50);

        String[][] strings = autoCoreService.AutoArrangementCourse(courses, 4);
        for (String[] string : strings) {
            for (String s : string) {
                System.out.print(s + " ");
            }
            System.out.println();
        }

    }

    @Test
    void Test() {
        String[][] i1 = new String[][]{
                {"1", "1"},
                {"1", "1"}
        };
        String[][] i2 = new String[][]{
                {"2", "2"},
                {"2", "2"}
        };
        String[] temp = i1[0];
        i1[0] = i2[0];
        i2[0] = temp;

        for (String[] ints : i1) {
            for (String anInt : ints) {
                System.out.print(anInt);
            }
        }
        System.out.println();
        for (String[] ints : i2) {
            for (String anInt : ints) {
                System.out.print(anInt);
            }
        }
    }

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ScheduleMapper scheduleMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Test
    void test1() {
        Schedule[][] schedules = scheduleService.selectSchedule("1", 1, 4);
        Schedule s = new Schedule();
        for (Schedule[] schedule : schedules) {
            for (Schedule schedule1 : schedule) {
                if (schedule1 != null) {
                    s = schedule1;
                    break;
                }
            }
        }
        System.out.println(s);
        scheduleMapper.updateSchedule(s, 6);
    }
}
