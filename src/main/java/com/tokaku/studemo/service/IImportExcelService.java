package com.tokaku.studemo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tokaku.studemo.pojo.User;
import org.springframework.web.multipart.MultipartFile;

public interface IImportExcelService {

    /**
     * 获取导入的Excel表中数据
     *
     * @param file 文件
     * @param req
     * @param resp
     * @return 返回集合
     */
    public List<User> importExcelWithSimple(MultipartFile file, HttpServletRequest req, HttpServletResponse resp);
}
