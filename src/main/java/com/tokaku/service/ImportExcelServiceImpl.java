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
        int rowNum = 0;//已取值的行数
        int colNum = 0;//列号
        int realRowCount = 0;//真正有数据的行数

        //得到工作空间
        Workbook workbook = null;
        try {
            workbook = super.getWorkbookByInputStream(file.getInputStream(), file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //得到工作表
        Sheet sheet = super.getSheetByWorkbook(workbook, 0);
        if (sheet.getRow(2000) != null) {
            throw new RuntimeException("系统已限制单批次导入数据必须小于或等于2000条！");
        }

        realRowCount = sheet.getPhysicalNumberOfRows();
        List<User> list = new ArrayList<>();
        User user = null;

        for (Row row : sheet) {
            if (realRowCount == rowNum) {
                break;
            }

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

            rowNum++;
            colNum = 1;
            user = new User();

            super.validCellValue(sheet, row, ++colNum, "账号");
            user.setId(super.getCellValue(sheet, row, colNum - 1));

            super.validCellValue(sheet, row, ++colNum, "姓名");
            user.setName(super.getCellValue(sheet, row, colNum - 1));

            super.validCellValue(sheet, row, ++colNum, "密码");
            user.setPassword(super.getCellValue(sheet, row, colNum - 1));

            list.add(user);
        }

        return list;
    }
}
