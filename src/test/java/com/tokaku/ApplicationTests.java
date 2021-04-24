package com.tokaku;

import com.tokaku.pojo.Course;
import com.tokaku.service.CourseService;
import com.tokaku.service.ImportExcelService;
import com.tokaku.service.impl.GeneticAlgorithmServiceImpl;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Set;

@MapperScan({"com.tokaku.mapper"})
@SpringBootTest
class ApplicationTests {

    //    @Autowired
//    TeacherService teacherService;
//    @Autowired
//    StudentService studentService;
    @Autowired
    CourseService courseService;


    @Test
    void contextLoads() {
    }

    @Autowired
    ImportExcelService importExcelService;
    @Autowired
    GeneticAlgorithmServiceImpl geneticAlgorithmService;

    @Test
    void GATest() {
//        1.获取课表
        Set<Course> courses = courseService.selectCourseList();

        HashMap<Integer, Integer> genes = geneticAlgorithmService.initGene(courses, 25);//25周
//        String[] schedule = geneticAlgorithmService.initChromosome(25, genes);//一周至多25节
        String[][] chromosome = geneticAlgorithmService.initIndividual(4, 25, genes);
        for (int i = 0; i < chromosome.length; i++) {
            System.out.println(i + "班课表");
            for (int i1 = 0; i1 < chromosome[i].length; i1++) {
                if (i1 % 5 == 0) System.out.println();
                System.out.printf("%-9s", chromosome[i][i1]);

            }
        }


    }


}
