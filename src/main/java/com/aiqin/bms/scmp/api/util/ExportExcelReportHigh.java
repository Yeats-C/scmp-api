package com.aiqin.bms.scmp.api.util;


import com.aiqin.bms.scmp.api.bireport.domain.response.HighInventoryRespVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.util.ArrayList;
import java.util.List;

public class ExportExcelReportHigh {

    public static XSSFWorkbook exportData(List<HighInventoryRespVo>list) {
        // 创建工作空间
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建表
        XSSFSheet sheet = workbook.createSheet("高库存");
        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeightInPoints(20);

        // 生成一个样式
        XSSFCellStyle style = workbook.createCellStyle();
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
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 15);
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontName("宋体");


        // 把字体 应用到当前样式
        style.setFont(font);


        // 添加表头数据
        XSSFRow row0 = sheet.createRow(0);
        XSSFCell cell_00 = row0.createCell(0);
        cell_00.setCellStyle(style);
        cell_00.setCellValue("所属部门");
        XSSFCell cell_01 = row0.createCell(1);
        cell_01.setCellStyle(style);
        cell_01.setCellValue("采购组");
        XSSFCell cell_02 = row0.createCell(2);
        cell_02.setCellStyle(style);
        cell_02.setCellValue("负责人");
        XSSFCell cell_03 = row0.createCell(3);
        cell_03.setCellStyle(style);
        cell_03.setCellValue("上周全国合计");
        XSSFCell cell_04 = row0.createCell(6);
        cell_04.setCellStyle(style);
        cell_04.setCellValue("本周全国合计");
        XSSFCell cell_05 = row0.createCell(9);
        cell_05.setCellStyle(style);
        cell_05.setCellValue("华北仓");
        XSSFCell cell_06 = row0.createCell(12);
        cell_06.setCellStyle(style);
        cell_06.setCellValue("华南仓");
        XSSFCell cell_07 = row0.createCell(15);
        cell_07.setCellStyle(style);
        cell_07.setCellValue("西南仓");
        XSSFCell cell_08 = row0.createCell(18);
        cell_08.setCellStyle(style);
        cell_08.setCellValue("华东仓");

        XSSFRow row1 = sheet.createRow(1);
        XSSFCell cell_10 = row1.createCell(3);
        cell_10.setCellStyle(style);
        cell_10.setCellValue("高库存金额");
        XSSFCell cell_11 = row1.createCell(4);
        cell_11.setCellStyle(style);
        cell_11.setCellValue("库存总金额");
        XSSFCell cell_12 = row1.createCell(5);
        cell_12.setCellStyle(style);
        cell_12.setCellValue("高库存占比");
        XSSFCell cell_13 = row1.createCell(6);
        cell_13.setCellStyle(style);
        cell_13.setCellValue("高库存金额");
        XSSFCell cell_14 = row1.createCell(7);
        cell_14.setCellStyle(style);
        cell_14.setCellValue("库存总金额");
        XSSFCell cell_15 = row1.createCell(8);
        cell_15.setCellStyle(style);
        cell_15.setCellValue("高库存占比");
        XSSFCell cell_16 = row1.createCell(9);
        cell_16.setCellStyle(style);
        cell_16.setCellValue("高库存金额");
        XSSFCell cell_17 = row1.createCell(10);
        cell_17.setCellStyle(style);
        cell_17.setCellValue("库存总金额");
        XSSFCell cell_18 = row1.createCell(11);
        cell_18.setCellStyle(style);
        cell_18.setCellValue("高库存占比");
        XSSFCell cell_19 = row1.createCell(12);
        cell_19.setCellStyle(style);
        cell_19.setCellValue("高库存金额");
        XSSFCell cell_20 = row1.createCell(13);
        cell_20.setCellStyle(style);
        cell_20.setCellValue("库存总金额");
        XSSFCell cell_21 = row1.createCell(14);
        cell_21.setCellStyle(style);
        cell_21.setCellValue("高库存占比");
        XSSFCell cell_22 = row1.createCell(15);
        cell_22.setCellStyle(style);
        cell_22.setCellValue("高库存金额");
        XSSFCell cell_23 = row1.createCell(16);
        cell_23.setCellStyle(style);
        cell_23.setCellValue("库存总金额");
        XSSFCell cell_24 = row1.createCell(17);
        cell_24.setCellStyle(style);
        cell_24.setCellValue("高库存占比");
        XSSFCell cell_25 = row1.createCell(18);
        cell_25.setCellStyle(style);
        cell_25.setCellValue("高库存金额");
        XSSFCell cell_26 = row1.createCell(19);
        cell_26.setCellStyle(style);
        cell_26.setCellValue("库存总金额");
        XSSFCell cell_27 = row1.createCell(20);
        cell_27.setCellStyle(style);
        cell_27.setCellValue("高库存占比");

        CellRangeAddress region = new CellRangeAddress(0, 1, 0, 0);
        sheet.addMergedRegion(region);
        CellRangeAddress region1 = new CellRangeAddress(0, 1, 1, 1);
        sheet.addMergedRegion(region1);
        CellRangeAddress region2 = new CellRangeAddress(0, 1, 2, 2);
        sheet.addMergedRegion(region2);
        CellRangeAddress region3 = new CellRangeAddress(0, 0, 3, 5);
        sheet.addMergedRegion(region3);
        CellRangeAddress region4 = new CellRangeAddress(0, 0, 6, 8);
        sheet.addMergedRegion(region4);
        CellRangeAddress region5 = new CellRangeAddress(0, 0, 9, 11);
        sheet.addMergedRegion(region5);
        CellRangeAddress region6 = new CellRangeAddress(0, 0, 12, 14);
        sheet.addMergedRegion(region6);
        CellRangeAddress region7 = new CellRangeAddress(0, 0, 15, 17);
        sheet.addMergedRegion(region7);
        CellRangeAddress region8 = new CellRangeAddress(0, 0, 18, 20);
        sheet.addMergedRegion(region8);

        XSSFRow row03 = sheet.createRow(2);
        ArrayList<String> lists = new ArrayList();

        // 添加单元格数据
        for (int i = 0; i < list.size(); i++) {
            row03 = sheet.createRow(i + 2);
            HighInventoryRespVo highInventoryRespVo = list.get(i);

            lists.add(highInventoryRespVo.getProductSortCode());
            lists.add(highInventoryRespVo.getProcurementSectionCode());
            lists.add(highInventoryRespVo.getResponsiblePersonCode());

            if(StringUtils.isNotBlank(highInventoryRespVo.getProductSortName())) {
                row03.createCell(0).setCellValue(highInventoryRespVo.getProductSortName());
            }else {
                row03.createCell(0).setCellValue("");
            }
            if(StringUtils.isNotBlank(highInventoryRespVo.getProcurementSectionName())) {
                row03.createCell(1).setCellValue(highInventoryRespVo.getProcurementSectionName());
            }else {
                row03.createCell(1).setCellValue("");
            }
            if(StringUtils.isNotBlank(highInventoryRespVo.getResponsiblePersonName())) {
                row03.createCell(2).setCellValue(highInventoryRespVo.getResponsiblePersonName());
            }else {
                row03.createCell(2).setCellValue("");
            }

            // 全国上周
            if (highInventoryRespVo.getSzqgHighInventoryAmount() != null) {
                row03.createCell(3).setCellValue(String.valueOf(highInventoryRespVo.getSzqgHighInventoryAmount()));
            } else {
                row03.createCell(3).setCellValue("");
            }
            if (highInventoryRespVo.getSzqgTotalInventoryAmount() != null) {
                row03.createCell(4).setCellValue(String.valueOf(highInventoryRespVo.getSzqgTotalInventoryAmount()));
            } else {
                row03.createCell(4).setCellValue("");
            }
            if (highInventoryRespVo.getSzqgRate() != null) {
                row03.createCell(5).setCellValue(highInventoryRespVo.getSzqgRate().doubleValue());
            } else {
                row03.createCell(5).setCellValue("");
            }


            // 全国本周
            if (highInventoryRespVo.getBzqgHighInventoryAmount() != null) {
                row03.createCell(6).setCellValue(String.valueOf(highInventoryRespVo.getBzqgHighInventoryAmount()));
            } else {
                row03.createCell(6).setCellValue("");
            }
            if (highInventoryRespVo.getBzqgTotalInventoryAmount() != null) {
                row03.createCell(7).setCellValue(String.valueOf(highInventoryRespVo.getBzqgTotalInventoryAmount()));
            } else {
                row03.createCell(7).setCellValue("");
            }
            if (highInventoryRespVo.getBzqgRate() != null) {
                row03.createCell(8).setCellValue(highInventoryRespVo.getBzqgRate().doubleValue());
            } else {
                row03.createCell(8).setCellValue("");
            }

            // 华北仓
            if (highInventoryRespVo.getHbHighInventoryAmount() != null) {
                row03.createCell(9).setCellValue(String.valueOf(highInventoryRespVo.getHbHighInventoryAmount()));
            } else {
                row03.createCell(9).setCellValue("");
            }
            if (highInventoryRespVo.getHbTotalInventoryAmount() != null) {
                row03.createCell(10).setCellValue(String.valueOf(highInventoryRespVo.getHbTotalInventoryAmount()));
            } else {
                row03.createCell(10).setCellValue("");
            }
            if (highInventoryRespVo.getHbRate() != null) {
                row03.createCell(11).setCellValue(highInventoryRespVo.getHbRate().doubleValue());
            } else {
                row03.createCell(11).setCellValue("");
            }

            // 华南仓
            if (highInventoryRespVo.getHnHighInventoryAmount() != null) {
                row03.createCell(12).setCellValue(String.valueOf(highInventoryRespVo.getHnHighInventoryAmount()));
            } else {
                row03.createCell(12).setCellValue("");
            }
            if (highInventoryRespVo.getHnTotalInventoryAmount() != null) {
                row03.createCell(13).setCellValue(String.valueOf(highInventoryRespVo.getHnTotalInventoryAmount()));
            } else {
                row03.createCell(13).setCellValue("");
            }
            if (highInventoryRespVo.getHnRate() != null) {
                row03.createCell(14).setCellValue(highInventoryRespVo.getHnRate().doubleValue());
            } else {
                row03.createCell(14).setCellValue("");
            }

            // 西南仓
            if (highInventoryRespVo.getXnHighInventoryAmount() != null) {
                row03.createCell(15).setCellValue(String.valueOf(highInventoryRespVo.getXnHighInventoryAmount()));
            } else {
                row03.createCell(15).setCellValue("");
            }
            if (highInventoryRespVo.getXnTotalInventoryAmount() != null) {
                row03.createCell(16).setCellValue(String.valueOf(highInventoryRespVo.getXnTotalInventoryAmount()));
            } else {
                row03.createCell(16).setCellValue("");
            }
            if (highInventoryRespVo.getXnRate() != null) {
                row03.createCell(17).setCellValue(highInventoryRespVo.getXnRate().doubleValue());
            } else {
                row03.createCell(17).setCellValue("");
            }

            // 华东仓
            if (highInventoryRespVo.getHdHhighInventoryAmount() != null) {
                row03.createCell(18).setCellValue(String.valueOf(highInventoryRespVo.getHdHhighInventoryAmount()));
            } else {
                row03.createCell(18).setCellValue("");
            }
            if (highInventoryRespVo.getHdTotalInventoryAmount() != null) {
                row03.createCell(19).setCellValue(String.valueOf(highInventoryRespVo.getHdTotalInventoryAmount()));
            } else {
                row03.createCell(19).setCellValue("");
            }
            if (highInventoryRespVo.getHdRate() != null) {
                row03.createCell(20).setCellValue(highInventoryRespVo.getHdRate().doubleValue());
            } else {
                row03.createCell(20).setCellValue("");
            }

        }
        return workbook;
    }

}