package com.aiqin.bms.scmp.api.util;


import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewProductResponseVO;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.text.SimpleDateFormat;
import java.util.List;

public class ExportExcel {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    public static HSSFWorkbook exportData(List<NewProductResponseVO>list) {
        // 创建工作空间
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建表
        HSSFSheet sheet = wb.createSheet("刘大爷");
        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeightInPoints(20);

        // 创建行
        HSSFRow row = sheet.createRow(0);

        // 生成一个样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

        // 背景色
        style.setFillForegroundColor(HSSFColor.TAN.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillBackgroundColor(HSSFColor.DARK_RED.index);

        // 设置边框
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);

        // 生成一个字体
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 20);
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontName("宋体");


        // 把字体 应用到当前样式
        style.setFont(font);
        // 添加表头数据
        String[] excelHeader = { "商品编号", "商品名称", "SKU数", "修改人", "修改时间" };
        for (int i = 0; i < excelHeader.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader[i]);
            cell.setCellStyle(style);
        }
        // 添加单元格数据
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            NewProductResponseVO student = list.get(i);
            if(StringUtils.isNotBlank(student.getProductCode())) {
                row.createCell(0).setCellValue(student.getProductCode());
            }else {
                row.createCell(0).setCellValue("");
            }
            if(StringUtils.isNotBlank(student.getProductName())) {
                row.createCell(1).setCellValue(student.getProductName());
            }else {
                row.createCell(1).setCellValue("");
            }
            if(student.getSkuNumber()!=null) {
                row.createCell(2).setCellValue(student.getSkuNumber());
            }else {
                row.createCell(2).setCellValue(0);
            }
            if(StringUtils.isNotBlank(student.getUpdateBy())) {
                row.createCell(3).setCellValue(student.getUpdateBy());
            }else {
                row.createCell(3).setCellValue("");
            }
            if(student.getUpdateTime()!=null) {
                row.createCell(4).setCellValue(simpleDateFormat.format(student.getUpdateTime()));
            }else {
                row.createCell(4).setCellValue("");
            }
        }
        return wb;
    }

}