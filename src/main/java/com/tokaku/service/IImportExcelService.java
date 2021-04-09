package com.tokaku.service;

import com.tokaku.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
