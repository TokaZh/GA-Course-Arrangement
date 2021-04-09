package com.tokaku;

import com.tokaku.mapper.StudentMapper;
import com.tokaku.pojo.Student;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@MapperScan({"com.tokaku.mapper"})
@SpringBootTest
class ApplicationTests {

    @Autowired
    StudentMapper studentMapper;

    @Test
    void contextLoads() {
        List<Student> students = studentMapper.queryStuList();
        for (Student student : students) {
            System.out.println(student);
        }
    }

}
