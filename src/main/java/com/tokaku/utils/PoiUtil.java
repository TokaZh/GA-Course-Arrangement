package com.tokaku.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Iterator;

public class PoiUtil {
    public static Sheet getSheet(MultipartFile file) {
        Workbook workbook = null;

        //得到工作空间
        InputStream fileStream = null;

        try {
            fileStream = file.getInputStream();
            String fileName = file.getOriginalFilename();

            assert fileName != null;
            if (fileName.endsWith(".xls")) {
                workbook = new HSSFWorkbook(fileStream);
            } else if (fileName.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fileStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件不存在或格式错误");
        } finally {
            try {
                assert fileStream != null;
                fileStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (workbook == null) {
            throw new RuntimeException("目标表不存在");
        }
        return workbook.getSheetAt(0);
    }

    public static String getCellValue(Sheet sheet, Row row, int column) {
        Cell cell = row.getCell(column);
        if (sheet == null) {
            return "";
        }
        return getCellValue(cell);
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                Number number = cell.getNumericCellValue();
                String numberStr = String.valueOf(number);

                if (numberStr.endsWith(".0")) {
                    numberStr = numberStr.replace(".0", "");//取整数
                }
                if (numberStr.contains("E")) {
                    numberStr = new DecimalFormat("#").format(number);//取整数
                }

                return numberStr;
            case STRING:
                return cell.getStringCellValue().trim();
            case FORMULA://公式
                return "";
            case BLANK:
                return "";
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                break;
        }

        return "";
    }

    public static void validCellValue(Sheet sheet, Row row, int colNum, String errorHint) {
        if ("".equals(getCellValue(sheet, row, colNum))) {
            throw new RuntimeException("校验 :第" + (row.getRowNum() + 1) + "行" + (colNum + 1) + "列" + errorHint + "不能为空");
        }
    }

    public static boolean isBlankRow(Row row) {
        if (row == null) {
            return true;
        }

        Iterator<Cell> iter = row.cellIterator();
        while (iter.hasNext()) {
            Cell cell = iter.next();
            if (cell == null) {
                continue;
            }

            String value = getCellValue(cell);
            if (!isNULLOrBlank(value)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNULLOrBlank(Object obj) {
        return obj == null || "".equals(obj.toString());
    }

}
