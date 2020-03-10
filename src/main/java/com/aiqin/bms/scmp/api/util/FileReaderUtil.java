package com.aiqin.bms.scmp.api.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@SuppressWarnings("deprecation")
public class FileReaderUtil {
    private static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";
    private static Logger LOGGER = LoggerFactory.getLogger(FileReaderUtil.class);
    private static DecimalFormat decimalFormat = new DecimalFormat("0");

    public static String[][] readExcel(MultipartFile mFile, int headSize) throws IOException {
        String[][] result = null;
        if (FileReaderUtil.isExcel2003(mFile.getInputStream())) {
            result = readExcel2003(mFile.getInputStream(), 0, headSize);
        } else if (FileReaderUtil.isExcel2007(mFile.getInputStream())) {
            result = readExcel2007(mFile.getInputStream(), 0, headSize);
        }
        return result;
    }

    public static String[][] readExcel2003(InputStream file, int ignoreRows, int headSize) throws IOException {
        HSSFWorkbook workBook = new HSSFWorkbook(file);
        return processRows(workBook, ignoreRows, headSize, defaultPattern);
    }

    public static String[][] readExcel2007(InputStream file, int ignoreRows, int headSize) throws IOException {
        XSSFWorkbook workBook = new XSSFWorkbook(file);
        return processRows(workBook, ignoreRows, headSize, defaultPattern);
    }

    /**
     * 处理记录行
     *
     * @param workBook   工作表
     * @param ignoreRows 忽略的行数
     * @param headSize   数据列数
     * @return String[rows][cells]
     */
    private static String[][] processRows(Workbook workBook, int ignoreRows, int headSize, String formatPattern) {
        List<String[]> rowValues = new ArrayList<>();
        formatPattern = StringUtils.isNotBlank(formatPattern) ? formatPattern : defaultPattern;
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        int cellNum = 1;
        int sheetNumber = 1;
        for (int readSheetIndex = 0; readSheetIndex < sheetNumber; readSheetIndex++) {
            Sheet sheet = workBook.getSheetAt(readSheetIndex);
            LOGGER.info("SHEET页 : " + readSheetIndex);
            if (sheet != null) {
                for (int rowIndex = ignoreRows; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row == null) {
                        LOGGER.info("空行,跳过");
                        continue;
                    }
                    cellNum = processCells(row, rowValues, headSize, format);
                }
            }
        }
        String[][] returnArray = new String[rowValues.size()][cellNum];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = rowValues.get(i);
        }
        return returnArray;
    }

    private static int processCells(Row row, List<String[]> rowValues, int headSize, SimpleDateFormat format) {
        Cell cell;
        int cellNum = row.getLastCellNum();
        if (cellNum > headSize) {
            LOGGER.error("header size not match, expect size : {}, actual size : {}", headSize, cellNum);
            throw new RuntimeException("Excel表头数量不匹配!");
        }
        String[] values = new String[headSize];
        Arrays.fill(values, "");
        boolean hasValue = false;
        for (short cellIndex = 0; cellIndex < cellNum; cellIndex++) {
            String value = "";
            cell = row.getCell(cellIndex);
            if (cell != null) {
                switch (cell.getCellType()) {
                    case HSSFCell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                            value = format.format(date);
                        } else {
                            value = cell.toString();
                        }
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        String strCell;
                        try {
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                Date date = cell.getDateCellValue();
                                strCell = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                            } else {
                                strCell = String.valueOf(cell.getNumericCellValue());
                            }
                        } catch (IllegalStateException e) {
                            strCell = String.valueOf(cell.getRichStringCellValue());
                        }
                        value = decimalFormat.format(Double.valueOf(strCell));
                        break;
                    case HSSFCell.CELL_TYPE_BLANK:
                        break;
                    case HSSFCell.CELL_TYPE_ERROR:
                        value = "";
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        value = (cell.getBooleanCellValue() ? "Y" : "N");
                        break;
                    default:
                        value = " ";
                }
            }
            values[cellIndex] = rightTrim(value);
            hasValue = true;
        }
        if (hasValue) {
            rowValues.add(values);
        }
        return cellNum;
    }

    /**
     * 去掉字符串右边的空格
     *
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */
    private static String rightTrim(String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        for (int i = length - 1; i >= 0; i--) {
            if (str.charAt(i) != 0x20) {
                break;
            }
            length--;
        }
        return str.substring(0, length);
    }

    public static boolean isExcel2003(InputStream is) {
        try {
            new HSSFWorkbook(is);
        } catch (Exception e) {
            LOGGER.error("isExcel2003 error:{}", e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean isExcel2007(InputStream is) {
        try {
            new XSSFWorkbook(is);
        } catch (Exception e) {
            LOGGER.error("isExcel2007 error:{}", e.getMessage());
            return false;
        }
        return true;
    }

    public static String validStoreValue(String[][] result, String[] header) {
        int rowLength = result.length;
        LOGGER.info("总行数 : {}", rowLength);
        if (rowLength == 0) {
            return "空文件";
        }
        for (int i = 0; i < header.length; i++) {
            if (!header[i].equals(result[0][i])) {
                String message = "导入模板标题不正确第" + (i + 1) + "列【" + result[1][i] + "】应为【" + header[i] + "】";
                LOGGER.error(message, "");
                return message;
            }
        }
        return null;
    }
}
