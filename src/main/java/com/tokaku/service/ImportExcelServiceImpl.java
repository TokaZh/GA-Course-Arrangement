package com.tokaku.service;

import com.tokaku.pojo.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportExcelServiceImpl extends ImportExcelBaseService implements IImportExcelService {

    @Override
    public List<User> importExcelWithSimple(MultipartFile file, HttpServletRequest req, HttpServletResponse resp) {
        int colNum;
        User user;
        List<User> list = new ArrayList<>();

        Workbook workbook = null;
        Sheet sheet = null;

        //得到工作空间
        try {
            workbook = super.getWorkbookByInputStream(file.getInputStream(), file.getOriginalFilename());
            sheet = super.getSheetByWorkbook(workbook, 0);
            if (sheet.getRow(2000) != null) {
                throw new RuntimeException("系统已限制单批次导入数据必须小于或等于2000条！");
            }
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            System.out.println("----------------------------");
            System.out.println(e.getMessage());
            return null;
        }

        for (Row row : sheet) {
            colNum = 0;
            user = new User();

            if (super.isBlankRow(row)) {//空行跳过
                continue;
            }

            if (row.getRowNum() == -1) {
                continue;
            } else {
                if (row.getRowNum() == 0) {//第一行表头跳过
                    continue;
                }
            }

            //这里要添加个try/catch用于捕捉行出现错误值，并且跳过
            try {
                super.validCellValue(sheet, row, colNum, "账号");
                user.setId(super.getCellValue(sheet, row, colNum++));

                super.validCellValue(sheet, row, colNum, "姓名");
                user.setName(super.getCellValue(sheet, row, colNum++));

                super.validCellValue(sheet, row, colNum, "密码");
                user.setPassword(super.getCellValue(sheet, row, colNum));
            } catch (RuntimeException e) {
                e.printStackTrace();
                continue;
            }
            list.add(user);
        }

        return list;
    }
}
