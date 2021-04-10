package com.tokaku.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Iterator;

@Component
public class ImportExcelBaseService {

    /**
     * 单元格校验
     *
     * @param sheet     工作表
     * @param row       行
     * @param colNum    列编号
     * @param errorHint 错误提示
     * @return 校验通过返回空，否则抛出异常
     */
    public void validCellValue(Sheet sheet, Row row, int colNum, String errorHint) {
        if ("".equals(this.getCellValue(sheet, row, colNum))) {
            throw new RuntimeException("校验 :第" + (row.getRowNum() + 1) + "行" + (colNum + 1) + "列" + errorHint + "不能为空");
        }
    }

    /**
     * 从输入流中获取excel工作表
     *
     * @param fileStream 输入流
     * @param fileName   带 .xls或.xlsx 后缀的文件名
     * @return 文件名为空返回空;
     * 格式不正确抛出异常;
     * 正常返回excel工作空间对象
     */
    public Workbook getWorkbookByInputStream(InputStream fileStream, String fileName) {
        Workbook workbook = null;

        try {
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
                fileStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return workbook;
    }

    /**
     * 从Workbook中获取sheet
     *
     * @param workbook 工作空间
     * @return 返回sheet
     */
    public Sheet getSheetByWorkbook(Workbook workbook, int index) {
        if (workbook == null) {
            throw new RuntimeException("目标表不存在");
        }
        return workbook.getSheetAt(index);
    }

    /**
     * 获取指定sheet指定row中指定column的cell值
     *
     * @param sheet  工作表
     * @param row    行
     * @param column 列
     * @return 返回单元格的值或""
     */
    public String getCellValue(Sheet sheet, Row row, int column) {
        if (sheet == null || row == null) {
            return "";
        }

        return this.getCellValue(row.getCell(column));
    }

    /**
     * 从单元格中获取单元格的值
     *
     * @param cell 单元格
     * @return 返回值或""
     */
    public String getCellValue(Cell cell) {
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

    /**
     * 判断该行是否为空行
     *
     * @param row 行号
     * @return 空行返回true, 非空行返回false
     */
    public boolean isBlankRow(Row row) {
        if (row == null) {
            return true;
        }

        Iterator<Cell> iter = row.cellIterator();
        while (iter.hasNext()) {
            Cell cell = iter.next();
            if (cell == null) {
                continue;
            }

            String value = this.getCellValue(cell);
            if (!this.isNULLOrBlank(value)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return 为空返回true, 不为空返回false
     */
    public boolean isNULLOrBlank(Object obj) {
        return obj == null || "".equals(obj.toString());
    }
}
