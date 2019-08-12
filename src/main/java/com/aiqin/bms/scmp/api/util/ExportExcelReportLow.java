package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.bireport.domain.response.HighInventoryRespVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.LowInventoryRespVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExportExcelReportLow {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    public static XSSFWorkbook exportData(List<LowInventoryRespVo>list) {
        // 创建工作空间
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建表
        XSSFSheet sheet = workbook.createSheet("低库存");
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
        cell_10.setCellValue("总sku数量");
        XSSFCell cell_11 = row1.createCell(4);
        cell_11.setCellStyle(style);
        cell_11.setCellValue("低库存sku数");
        XSSFCell cell_12 = row1.createCell(5);
        cell_12.setCellStyle(style);
        cell_12.setCellValue("低库存占比");
        XSSFCell cell_13 = row1.createCell(6);
        cell_13.setCellStyle(style);
        cell_13.setCellValue("总sku数量");
        XSSFCell cell_14 = row1.createCell(7);
        cell_14.setCellStyle(style);
        cell_14.setCellValue("低库存sku数");
        XSSFCell cell_15 = row1.createCell(8);
        cell_15.setCellStyle(style);
        cell_15.setCellValue("低库存占比");
        XSSFCell cell_16 = row1.createCell(9);
        cell_16.setCellStyle(style);
        cell_16.setCellValue("总sku数量");
        XSSFCell cell_17 = row1.createCell(10);
        cell_17.setCellStyle(style);
        cell_17.setCellValue("低库存sku数");
        XSSFCell cell_18 = row1.createCell(11);
        cell_18.setCellStyle(style);
        cell_18.setCellValue("低库存占比");
        XSSFCell cell_19 = row1.createCell(12);
        cell_19.setCellStyle(style);
        cell_19.setCellValue("总sku数量");
        XSSFCell cell_20 = row1.createCell(13);
        cell_20.setCellStyle(style);
        cell_20.setCellValue("低库存sku数");
        XSSFCell cell_21 = row1.createCell(14);
        cell_21.setCellStyle(style);
        cell_21.setCellValue("低库存占比");
        XSSFCell cell_22 = row1.createCell(15);
        cell_22.setCellStyle(style);
        cell_22.setCellValue("总sku数量");
        XSSFCell cell_23 = row1.createCell(16);
        cell_23.setCellStyle(style);
        cell_23.setCellValue("低库存sku数");
        XSSFCell cell_24 = row1.createCell(17);
        cell_24.setCellStyle(style);
        cell_24.setCellValue("低库存占比");
        XSSFCell cell_25 = row1.createCell(18);
        cell_25.setCellStyle(style);
        cell_25.setCellValue("总sku数量");
        XSSFCell cell_26 = row1.createCell(19);
        cell_26.setCellStyle(style);
        cell_26.setCellValue("低库存sku数");
        XSSFCell cell_27 = row1.createCell(20);
        cell_27.setCellStyle(style);
        cell_27.setCellValue("低库存占比");

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
            LowInventoryRespVo lowInventoryRespVo = list.get(i);

            lists.add(lowInventoryRespVo.getProductSortCode());
            lists.add(lowInventoryRespVo.getProcurementSectionCode());
            lists.add(lowInventoryRespVo.getResponsiblePersonCode());

            if(StringUtils.isNotBlank(lowInventoryRespVo.getProductSortName())) {
                row03.createCell(0).setCellValue(lowInventoryRespVo.getProductSortName());
            }else {
                row03.createCell(0).setCellValue("");
            }
            if(StringUtils.isNotBlank(lowInventoryRespVo.getProcurementSectionName())) {
                row03.createCell(1).setCellValue(lowInventoryRespVo.getProcurementSectionName());
            }else {
                row03.createCell(1).setCellValue("");
            }
            if(StringUtils.isNotBlank(lowInventoryRespVo.getResponsiblePersonName())) {
                row03.createCell(2).setCellValue(lowInventoryRespVo.getResponsiblePersonName());
            }else {
                row03.createCell(2).setCellValue("");
            }

            // 全国本周
            if (lowInventoryRespVo.getSzqgTotalSkuNum() != null) {
                row03.createCell(3).setCellValue(lowInventoryRespVo.getSzqgTotalSkuNum());
            } else {
                row03.createCell(3).setCellValue("");
            }
            if (lowInventoryRespVo.getSzqgLowSkuNum() != null) {
                row03.createCell(4).setCellValue(lowInventoryRespVo.getSzqgLowSkuNum());
            } else {
                row03.createCell(4).setCellValue("");
            }
            if (lowInventoryRespVo.getSzqgLowInventoryRatio() != null) {
                row03.createCell(5).setCellValue(lowInventoryRespVo.getSzqgLowInventoryRatio());
            } else {
                row03.createCell(5).setCellValue("");
            }

            // 全国本周
            if (lowInventoryRespVo.getBzqgTotalSkuNum() != null) {
                row03.createCell(6).setCellValue(lowInventoryRespVo.getBzqgTotalSkuNum());
            } else {
                row03.createCell(6).setCellValue("");
            }
            if (lowInventoryRespVo.getBzqgLowSkuNum() != null) {
                row03.createCell(7).setCellValue(lowInventoryRespVo.getBzqgLowSkuNum());
            } else {
                row03.createCell(7).setCellValue("");
            }
            if (lowInventoryRespVo.getBzqgLowInventoryRatio() != null) {
                row03.createCell(8).setCellValue(lowInventoryRespVo.getBzqgLowInventoryRatio());
            } else {
                row03.createCell(8).setCellValue("");
            }

            // 华北仓
            if (lowInventoryRespVo.getHbTotalSkuNum() != null) {
                row03.createCell(9).setCellValue(lowInventoryRespVo.getHbTotalSkuNum());
            } else {
                row03.createCell(9).setCellValue("");
            }
            if (lowInventoryRespVo.getHbLowSkuNum() != null) {
                row03.createCell(10).setCellValue(lowInventoryRespVo.getHbLowSkuNum());
            } else {
                row03.createCell(10).setCellValue("");
            }
            if (lowInventoryRespVo.getHbLowInventoryRatio() != null) {
                row03.createCell(11).setCellValue(lowInventoryRespVo.getHbLowInventoryRatio());
            } else {
                row03.createCell(11).setCellValue("");
            }

            // 华南仓
            if (lowInventoryRespVo.getHnTotalSkuNum() != null) {
                row03.createCell(12).setCellValue(lowInventoryRespVo.getHnTotalSkuNum());
            } else {
                row03.createCell(12).setCellValue("");
            }
            if (lowInventoryRespVo.getHnLowSkuNum() != null) {
                row03.createCell(13).setCellValue(lowInventoryRespVo.getHnLowSkuNum());
            } else {
                row03.createCell(13).setCellValue("");
            }
            if (lowInventoryRespVo.getHnLowInventoryRatio() != null) {
                row03.createCell(14).setCellValue(lowInventoryRespVo.getHnLowInventoryRatio());
            } else {
                row03.createCell(14).setCellValue("");
            }

            // 西南仓
            if (lowInventoryRespVo.getXnTotalSkuNum() != null) {
                row03.createCell(15).setCellValue(lowInventoryRespVo.getXnTotalSkuNum());
            } else {
                row03.createCell(15).setCellValue("");
            }
            if (lowInventoryRespVo.getXnLowSkuNum() != null) {
                row03.createCell(16).setCellValue(lowInventoryRespVo.getXnLowSkuNum());
            } else {
                row03.createCell(16).setCellValue("");
            }
            if (lowInventoryRespVo.getXnLowInventoryRatio() != null) {
                row03.createCell(17).setCellValue(lowInventoryRespVo.getXnLowInventoryRatio());
            } else {
                row03.createCell(17).setCellValue("");
            }

            // 华东仓
            if (lowInventoryRespVo.getHdTotalSkuNum() != null) {
                row03.createCell(18).setCellValue(lowInventoryRespVo.getHdTotalSkuNum());
            } else {
                row03.createCell(18).setCellValue("");
            }
            if (lowInventoryRespVo.getHdLowSkuNum() != null) {
                row03.createCell(19).setCellValue(lowInventoryRespVo.getHdLowSkuNum());
            } else {
                row03.createCell(19).setCellValue("");
            }
            if (lowInventoryRespVo.getHdLowInventoryRatio() != null) {
                row03.createCell(20).setCellValue(lowInventoryRespVo.getHdLowInventoryRatio());
            } else {
                row03.createCell(20).setCellValue("");
            }
        }
        return workbook;
    }
}
