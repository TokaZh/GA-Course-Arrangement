package com.tokaku.controller;

import com.tokaku.pojo.User;
import com.tokaku.service.IImportExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ImportExcelController {

    IImportExcelService iImportExcelService;

    @Autowired
    public void iImportExcelService(IImportExcelService iImportExcelService) {
        this.iImportExcelService = iImportExcelService;
    }

    @PostMapping("/importExcel")
    @ResponseBody
    public String withSimple(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp) {
        List<User> list = iImportExcelService.importExcelWithSimple(file, req, resp);

        if (list == null || list.size() == 0) {
            return "redirect:/student";
        }

        //遍历看看数据
        for (User bean : list) {
            System.out.println(bean.toString());
        }

        //批量插入list到数据库
        //待添加

        return "redirect:/student";
    }

}
