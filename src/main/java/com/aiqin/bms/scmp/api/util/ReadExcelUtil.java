package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.product.domain.request.variableprice.ExcelData;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class ReadExcelUtil {
        // 总行数
        private int totalRows = 0;
        // 总条数
        private int totalCells = 0;
        // 错误信息接收器
        private String errorMsg;

        // 构造方法
        public ReadExcelUtil() {
        }

        // 获取总行数
        public int getTotalRows() {
            return totalRows;
        }

        // 获取总列数
        public int getTotalCells() {
            return totalCells;
        }

        // 获取错误信息
        public String getErrorInfo() {
            return errorMsg;
        }

        /**
         * 读EXCEL文件，获取信息集合
         *
         * @param mFile
         * @return
         */
        public List<ExcelData> getExcelInfo(MultipartFile mFile) {
            String fileName = mFile.getOriginalFilename();//
            List<ExcelData> userList=null;
            try {
                if (!validateExcel(fileName)) {// 验证文件名是否合格
                    return null;
                }
                boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
                if (isExcel2007(fileName)) {
                    isExcel2003 = false;
                }
                userList = createExcel(mFile.getInputStream(), isExcel2003);
            } catch (Exception e) {
                throw new GroundRuntimeException(e.getMessage());
            }
            return userList;
        }

        /**
         * 根据excel里面的内容读取客户信息
         *
         * @param
         * @param isExcel2003 excel是2003还是2007版本
         * @return
         * @throws IOException
         */
        public List<ExcelData> createExcel(InputStream is, boolean isExcel2003) {
            List<ExcelData>userList=null;
            try {
                Workbook wb = null;
                if (isExcel2003) {// 当excel是2003时,创建excel2003
                    wb = new HSSFWorkbook(is);
                } else {// 当excel是2007时,创建excel2007
                    wb = new XSSFWorkbook(is);
                }
                userList = readExcelValue(wb);// 读取Excel里面客户的信息
            } catch (IOException e) {
                throw new GroundRuntimeException(e.getMessage());
            }
            return userList;
        }

        /**
         * 读取Excel里面客户的信息
         *
         * @param wb
         * @return
         */
        private List<ExcelData> readExcelValue(Workbook wb) {
            List<ExcelData> userList = new LinkedList<>();
            try {
            // 得到第一个shell
            Sheet sheet = wb.getSheetAt(0);
            // 得到Excel的行数
            this.totalRows = sheet.getPhysicalNumberOfRows();
            // 得到Excel的列数(前提是有行数)
            if (totalRows > 1 && sheet.getRow(0) != null) {
                this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
            }
            // 循环Excel行数
            for (int r = 1; r < totalRows; r++) {
                    Row row = sheet.getRow(r);
                    if (row == null) {
                        continue;
                    }
                    ExcelData user = new ExcelData();
                    // 循环Excel的列
                    for (int c = 0; c < this.totalCells; c++) {
                        Cell cell = row.getCell(c);
                        if (null != cell) {
                            if (c == 0) {
                                if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                    String name = String.valueOf(cell.getNumericCellValue());
                                    if(!StringUtils.isNotBlank(name)){
                                        throw new GroundRuntimeException("第"+r+"行第"+c+"列sku编号不能为空");
                                    }
                                    user.setSkuCode(name.substring(0, name.length() - 2 > 0 ? name.length() - 2 : 1));// 名称
                                } else { ;// 名称
                                    String names =cell.getStringCellValue();
                                    if(!StringUtils.isNotBlank(names)){
                                        throw new GroundRuntimeException("第"+r+"行第"+c+"列sku编号不能为空");
                                    }
                                    user.setSkuCode(names);
                                }
                            } else if (c == 1) {
                                if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                    String sex = String.valueOf(cell.getNumericCellValue());
                                    if(!StringUtils.isNotBlank(sex)){
                                        throw new GroundRuntimeException("第"+r+"行第"+c+"列sku名称不能为空");
                                    }
                                    user.setSkuName(sex.substring(0, sex.length() - 2 > 0 ? sex.length() - 2 : 1));// 性别
                                } else {
                                    String sexs = cell.getStringCellValue();
                                    if(!StringUtils.isNotBlank(sexs)){
                                        throw new GroundRuntimeException("第"+r+"行第"+c+"列sku名称不能为空");
                                    }
                                    user.setSkuName(sexs);//
                                }
                            }else if (c==2){
                                if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                    BigDecimal price =new BigDecimal(cell.getNumericCellValue());
                                    user.setPriceValue(price);
                                } else {
                                    String sexs = cell.getStringCellValue();
                                    user.setPriceValue(BigDecimal.valueOf(Float.valueOf(sexs)));//
                                }
                            }
                        }
                        // 添加到list
                    }
                    userList.add(user);
                   }
            }catch (Exception ex){
                throw new GroundRuntimeException(ex.getMessage());
            }
            return userList;
        }


        /**
         * 验证EXCEL文件
         * @param filePath
         * @return
         */
        public boolean validateExcel(String filePath) {
            if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
                errorMsg = "文件名不是excel格式";
                return false;
            }
            return true;
        }

        // @描述：是否是2003的excel，返回true是2003
        public static boolean isExcel2003(String filePath) {
            return filePath.matches("^.+\\.(?i)(xls)$");
        }

        // @描述：是否是2007的excel，返回true是2007
        public static boolean isExcel2007(String filePath) {
            return filePath.matches("^.+\\.(?i)(xlsx)$");
        }

}

