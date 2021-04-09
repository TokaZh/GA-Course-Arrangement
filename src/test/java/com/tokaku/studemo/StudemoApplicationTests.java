package com.tokaku.studemo;

import com.tokaku.studemo.mapper.StudentMapper;
import com.tokaku.studemo.mapper.UserMapper;
import com.tokaku.studemo.pojo.Student;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@MapperScan({"com.tokaku.studemo.mapper"})
@SpringBootTest
class StudemoApplicationTests {

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
