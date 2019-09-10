package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by hechao on 2018/1/8.
 */
@Slf4j
public class ExcelUtil {
	public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, List<?> list, HSSFWorkbook wb) {
		// 第一步，创建一个webbook，对应一个Excel文件
		if (wb == null){
			wb = new HSSFWorkbook();
		}
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

		HSSFCell cell = null;
		// 创建标题
		for (int i = 0; i < title.length; i++){
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		// 创建内容
		for (int i = 0; i < list.size(); i++){
			Object obj = list.get(i);
			Field[] fields = obj.getClass().getDeclaredFields();
			row = sheet.createRow(i + 1);
			int columnNum = 0;
			for (Field field : fields){
				if (field.getModifiers() == 26){
					continue;
				}

				field.setAccessible(true);
				try{
					row.createCell(columnNum).setCellValue(field.get(obj) == null ? "" : field.get(obj).toString());
				}
				catch (IllegalArgumentException e){
					log.error(Global.ERROR, e);
				}
				catch (IllegalAccessException e){
					log.error(Global.ERROR, e);
				}
				columnNum++;
			}
		}

		return wb;
	}


	/**
	 * 根据传入的数据生成表格，第一行为标题行,每列宽度会随着内容加大而增加
	 *
	 * @param fileName 文件路径+名称
	 * @param title    标题
	 * @param list     内容
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static HSSFWorkbook newHSSFWorkbook(String fileName,
											   String[] title,
											   List<Object[]> list, HSSFWorkbook wb) throws Exception {
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet(fileName);
		// 创建字体样式
		HSSFFont font = wb.createFont();
		// 宽度
		font.setBold(true);
		// 高度
		font.setFontHeight((short) 200);
		// 字体
		font.setFontName("楷体");

		// 创建Excel的sheet的一行(标题行)
		if (title != null && title.length > 0) {
			HSSFRow row = sheet.createRow(0);
			row.setHeight((short) 300);// 设定行的高度
			// 创建单元格样式
			HSSFCellStyle style = wb.createCellStyle();
			// 左右居中
			style.setAlignment(HorizontalAlignment.CENTER);
			// 垂直居中
			style.setVerticalAlignment(VerticalAlignment.CENTER);

			style.setFont(font);// 设置字体
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);

			for (int titleNum = 0; titleNum < title.length; titleNum++) {
				// 创建一个Excel的单元格
				HSSFCell cell = row.createCell(titleNum);
				// 设置一个宽度，避免字体溢出
				sheet.setColumnWidth(titleNum,
						title[titleNum].length() * 500 + 1000);
				// 给Excel的单元格设置样式和赋值
				cell.setCellStyle(style);
				cell.setCellValue(title[titleNum]);
			}
		}

		// 完了设置内容
		if (list != null && list.size() > 0) {
			for (int listNum = 0; listNum < list.size(); listNum++) {
				HSSFRow row = sheet.createRow(listNum + 1);
				Object[] obj = list.get(listNum);
				for (int mnum = 0; mnum < obj.length; mnum++) {
					// 创建一个Excel的单元格
					HSSFCell cell = row.createCell(mnum);
					if (obj[mnum] != null) {
						cell.setCellValue(obj[mnum].toString());
					}
				}
			}
		}

		return wb;
	}
    /**
     * 判断导入时参数是否是数字
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        boolean ret = true;
        try{
            double d = Double.parseDouble(str);
            ret = true;
        } catch (Exception ex){
            ret = false;
        }
        return ret;
    }
    /**
     * 导入数据时装换纯数字的值为字符串
     */
    public static String convertNumToString(Object object){
        try {
            String s="";
            s = object.toString().trim();
            if(isDouble(s)) {
                BigDecimal e = new BigDecimal(s);
                s = e.stripTrailingZeros().toPlainString();
            }
            return s;
        } catch (Exception e) {
            return object.toString().trim();
        }
    }
	/**
	 * 把所有内容读取出来
	 *
	 * @param efile
	 * @return
	 */
	public static List<Object[]> getExcelAll(MultipartFile efile) {
		List<Object[]> list = new ArrayList<>();
		try{
			Workbook wb = null;
			String fileName = efile.getOriginalFilename();
			if (fileName.toUpperCase().endsWith("XLS")){
				wb = new HSSFWorkbook(efile.getInputStream());
			}
			else if (fileName.toUpperCase().endsWith("XLSX")){
				wb = new XSSFWorkbook(efile.getInputStream());
			}
			// 把一张xls的数据表读到wb里
			// 读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作 HSSFSheet sheet =
			// wb.getSheetAt(0);
			Sheet sheet = wb.getSheetAt(0);

			// 循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
			int maxNum = sheet.getLastRowNum();
			// 如果总共有3条记录，那获取到的最后记录号就为2，因为是从0开始的
			for (int j = 0; j < maxNum + 1; j++){
				// 创建一个行对象
				Row row = sheet.getRow(j);
				// 把一行里的每一个字段遍历出来
				if (row == null){
					continue;
				} else {
					int maxRow = row.getLastCellNum();
					//跳过纯空数据
					if (maxRow < 1) {
						continue;
					}
					String[] str2 = new String[maxRow];
					for (int i = 0; i < maxRow; i++){
						// 创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
						Cell cell = row.getCell(i);
						// if (cell != null) {
						// System.out.println("类型:" + cell.getCellType());
						// }

						// 在这里我们就可以做很多自己想做的操作了，比如往数据库中添加数据等
						// System.out.println("第" + (j + 1) + "行的第" + i + "列的值："
						// + cell);
						if (cell != null){

							str2[i] = cell + "";
						}
					}
					list.add(str2);
				}
			}
		} catch (Exception e){
			log.error(Global.ERROR, e);
			throw new GroundRuntimeException("表格解析异常");
		}
		return list;
	}

	public static <T> List<T> validValue( List<Object[]> data, String title, Class<T> clz) throws Exception{
		if(Objects.isNull(data)){
			return null;
		}
		//验证表头是否正确
		if(validHeader(data.get(0),title)){
		    //移除第一个元素
            data.remove(0);
            Class<?>[] cz = null;
            Constructor<?>[] cons = clz.getConstructors();
            for(Constructor<?> ct : cons) {
                Class<?>[] clazz = ct.getParameterTypes();
                if(data.get(0).length == clazz.length) {
                    cz = clazz;
                    break;
                }
            }
            List<T> list = new ArrayList<T>();
            for(Object[] obj : data) {
                Constructor<T> cr = clz.getConstructor(cz);
                list.add(cr.newInstance(obj));
            }
            return list;
        } else {
            return null;
        }

	}

	private static Boolean validHeader(Object[] data, String title){
        String headerStr = StringUtils.join(data, ",");
        if(Objects.equals(headerStr,title)){
            return true;
        } else {
            return false;
        }
    }
}
