package com.tokaku.service.impl;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.User;
import com.tokaku.service.ImportExcelService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.tokaku.utils.PoiUtil.*;

@Service
public class ImportExcelServiceImpl implements ImportExcelService {

    @Override
    public List<User> importExcelWithStudent(MultipartFile file) {

        List<User> list = new ArrayList<>();

        Sheet sheet = getSheet(file);

        Iterator<Row> row = sheet.iterator();
        //跳过表头
        if (row.hasNext()) row.next();
        while (row.hasNext()) {
            String[] index = {"账号", "姓名", "密码"};
            User user = getUser(sheet, row.next(), index);
            if (user == null) continue;
            list.add(user);
        }

        return list;
    }

    private User getUser(Sheet sheet, Row row, String[] index) {
        User user = new User();
        int colNum = 0;
        //这里要添加个try/catch用于捕捉行出现错误值，并且跳过
        try {
            for (String s : index) {
                validCellValue(sheet, row, colNum++, s);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
        colNum = 0;
        user.setId(getCellValue(sheet, row, colNum++));
        user.setName(getCellValue(sheet, row, colNum++));
        user.setPassword(getCellValue(sheet, row, colNum));

        return user;
    }

    @Override
    public Set<Course> importExcelWithCourse(MultipartFile file) {
        Set<Course> set = new HashSet<>();
        Sheet sheet = getSheet(file);
        if (sheet == null) return null;

        Iterator<Row> row = sheet.iterator();
        if (row.hasNext()) row.next();
        while (row.hasNext()) {
            String[] index =
                    {"课程号", "课程名", "类型", "学分", "学时", "专业", "学期"};
            Course course = getCourse(sheet, row.next(), index);
            if (course == null) continue;
            set.add(course);
        }
        return set;
    }

    private Course getCourse(Sheet sheet, Row row, String[] index) {
        int colNum = 0;
        //这里要添加个try/catch用于捕捉行出现错误值，并且跳过
        try {
            for (String s : index) {
                validCellValue(sheet, row, colNum++, s);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
        colNum = 0;
        String courseId = String.valueOf(getCellValue(sheet, row, colNum++));
        String courseName = getCellValue(sheet, row, colNum++);
        int type = Integer.parseInt(getCellValue(sheet, row, colNum++));
        int courseScore = Integer.parseInt(getCellValue(sheet, row, colNum++));
        int time = Integer.parseInt(getCellValue(sheet, row, colNum++));
        String majorId = String.valueOf(getCellValue(sheet, row, colNum));
        int grade = Integer.parseInt(getCellValue(sheet, row, colNum));
        return new Course(courseId, courseName, type, courseScore, time, majorId, grade);

    }
}
