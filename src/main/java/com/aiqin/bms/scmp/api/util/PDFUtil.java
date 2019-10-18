package com.aiqin.bms.scmp.api.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class PDFUtil {

    private String tempFileName;

    public PDFUtil(String tempFileName) {
        this.tempFileName=tempFileName;
    }

    /**
     * 填充模板
     * @param paramMap
     * @throws Exception
     */
    public byte[] fillTemplate(Map<String, Object> paramMap) throws Exception {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(PDFUtil.class,"/ftl/");
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        //这个一定要设置，不然在生成的页面中 会乱码
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        //获取或创建一个模版。
        Template template = configuration.getTemplate(tempFileName);
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        template.process(paramMap, writer); //把值写进模板
        String htmlStr = stringWriter.toString();
        writer.flush();
        writer.close();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        createPDFFile(htmlStr, out);
        return out.toByteArray();
    }

    /**
     * 根据HTML字符串创建PDF文件
     * @param htmlStr
     * @param os
     * @throws Exception
     */
    private void createPDFFile(String htmlStr, OutputStream os) throws Exception{
        ByteArrayInputStream in = new ByteArrayInputStream(htmlStr.getBytes(StandardCharsets.UTF_8));
        Document document = new Document(new RectangleReadOnly(842F,595F));
        try {
            PdfWriter writer = PdfWriter.getInstance(document, os);
            document.open();
            FontProvider provider = new FontProvider();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, in, StandardCharsets.UTF_8, provider);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            try {
                document.close();
            } catch (Exception e) {
                log.error("close pdf document error, ", e);
            }
            try {
                in.close();
            } catch (Exception e) {
                log.error("close input stream error, ", e);
            }
        }
    }

    /**
     * 字体
     */
    private static class FontProvider extends XMLWorkerFontProvider {
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
}
