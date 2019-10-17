package com.aiqin.bms.scmp.api.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PDFUtil {

    private static String tempFilePath;
    private static String tempFileName;

    public PDFUtil(String tempFilePath, String tempFileName) {
        this.tempFilePath=tempFilePath;
        this.tempFileName=tempFileName;
    }

    public String getTempFilePath() {
        return tempFilePath;
    }

    public void setTempFilePath(String tempFilePath) {
        this.tempFilePath = tempFilePath;
    }

    public String getTempFileName() {
        return tempFileName;
    }

    public void setTempFileName(String tempFileName) {
        this.tempFileName = tempFileName;
    }

    /**
     * 测试
     * @param args
     */
//    public static void main(String[] args) {
//        String name = "订货单模板.htm";
//        PDFUtil pdfUtil = new PDFUtil("C:\\Users\\Z\\Desktop",name);
//        /** 用于组装word页面需要的数据 */
//        Map<String, Object> dataMap = new HashMap<>();
//
//        /** 组装数据 */
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//        dataMap.put("time", sdf.format(new Date()));
//        dataMap.put("number", "CG001");
//        dataMap.put("supplyName", "张三");
//        dataMap.put("address", "北京");
//        dataMap.put("phone", "123");
//        dataMap.put("fax", "55555555");
//        dataMap.put("contacts", "张三1");
//        dataMap.put("code", "123");
//        dataMap.put("dept", "服纺");
//        dataMap.put("goodsAddress", "北京1");
//        dataMap.put("mobile", "1231");
//        dataMap.put("goodsPerson", "张三11");
//
//        dataMap.put("boxSum", "123");
//        dataMap.put("singleSum", "服纺");
//        dataMap.put("amountSum", "北京1");
//
//        List<Map<String, Object>> productList = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", "0");
//            map.put("skuCode", "内容" + i);
//            map.put("distribution", "1" + i);
//            map.put("skuName", "123" + i);
//            map.put("spec", "123" + i);
//            map.put("type", "123" + i);
//            map.put("goodsCount", "123" + i);
//            map.put("goodsMin", "123" + i);
//            map.put("price", "123" + i);
//            map.put("priceSum", "123" + i);
//            productList.add(map);
//        }
//        dataMap.put("productList", productList);
//        try {
//            String uploadfile = pdfUtil.fillTemplate(dataMap, "订货单");
//            System.out.println(uploadfile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 填充模板
     * @param paramMap
     * @throws Exception
     */
    public  String  fillTemplate(Map<String, Object> paramMap, String fileName) throws Exception {
//        File modelFile = new File(tempFilePath);
//        if(!modelFile.exists()) {
//            modelFile.mkdirs();
//        }
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        //configuration.setDirectoryForTemplateLoading(modelFile);
        configuration.setClassForTemplateLoading(PDFUtil.class,"/ftl/");
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        //这个一定要设置，不然在生成的页面中 会乱码
        configuration.setDefaultEncoding("UTF-8");
        //获取或创建一个模版。
        Template template = configuration.getTemplate(tempFileName);
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        template.process(paramMap, writer); //把值写进模板
        String htmlStr = stringWriter.toString();
        writer.flush();
        writer.close();
        String tmpPath = tempFilePath;
        File tmepFilePath = new File(tmpPath);
        if (!tmepFilePath.exists()) {
            tmepFilePath.mkdirs();
        }
        String tmpFileName = fileName + ".pdf";
        String outputFile = tempFilePath + File.separatorChar + tmpFileName;
        FileOutputStream outFile = new FileOutputStream(outputFile);
        createPDFFile(htmlStr, outFile);
        return outputFile;
    }

    /**
     * 根据HTML字符串创建PDF文件
     * @param htmlStr
     * @param os
     * @throws Exception
     */
    private  void createPDFFile(String htmlStr, OutputStream os) throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream(htmlStr.getBytes("UTF-8"));
        Document document = new Document(new RectangleReadOnly(842F,595F));
        try {
            PdfWriter writer = PdfWriter.getInstance(document, os);
            document.open();
            FontProvider provider = new FontProvider();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, bais, Charset.forName("UTF-8"),provider);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            try {
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bais.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 字体
     */
    private  class FontProvider extends XMLWorkerFontProvider {
        public Font getFont(final String fontname, final String encoding,
                            final boolean embedded, final float size, final int style,
                            final BaseColor color) {
            BaseFont bf = null;
            try {
                bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Font font = new Font(bf, size, style, color);
            font.setColor(color);
            return font;
        }
    }

    /**
     * 生成html模板
     * @param content
     * @return
     */
    public static String createdHtmlTemplate(String content){
        String fileName = tempFilePath + "/" + tempFileName;
        try{
            File file = new File(tempFilePath);
            if(!file.isDirectory()) {
                file.mkdir();
            }
            file = new File(fileName);
            if(!file.isFile()) {
                file.createNewFile();
            }
            //打开文件
            PrintStream printStream = new PrintStream(new FileOutputStream(fileName));
            //将HTML文件内容写入文件中
            printStream.println(content);
            printStream.flush();
            printStream.close();
            System.out.println("生成html模板成功!");
        }catch(Exception e){
            e.printStackTrace();
        }
        return fileName;
    }

}
