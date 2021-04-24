package com.tokaku.service;

import com.tokaku.pojo.Course;
import com.tokaku.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ImportExcelService {

    /**
     * 获取导入的Excel表中数据
     *
     * @param file 文件
     * @param req
     * @param resp
     * @return 返回集合
     */
//    List<User> importExcelWithStudent(MultipartFile file, HttpServletRequest req, HttpServletResponse resp);
    List<User> importExcelWithStudent(MultipartFile file);

    Set<Course> importExcelWithCourse(MultipartFile file);
}
