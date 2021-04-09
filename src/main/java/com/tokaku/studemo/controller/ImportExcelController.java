package com.tokaku.studemo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tokaku.studemo.pojo.User;
import com.tokaku.studemo.service.IImportExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/importExlce")
public class ImportExcelController {

    @Autowired
    IImportExcelService iImportExcelService;

    @RequestMapping(value = "/withSimple", method = RequestMethod.POST)
    public String withSimple(MultipartFile file, HttpServletRequest req, HttpServletResponse resp) {
        List<User> list = iImportExcelService.importExcelWithSimple(file, req, resp);

        if (list == null || list.size() == 0) {
            return "fail";
        }

        for (User bean : list) {
            System.out.println(bean.toString());
        }

        //批量插入list到数据库

        return "success";
    }

}
