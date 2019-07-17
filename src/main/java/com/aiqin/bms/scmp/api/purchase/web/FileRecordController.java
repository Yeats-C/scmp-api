package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import com.aiqin.bms.scmp.api.purchase.service.FileRecordService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author: zhao shuai
 * @create: 2019-06-26 21:19
 **/

@Api(tags = "文件记录")
@RequestMapping("/file")
@RestController
public class FileRecordController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileRecordController.class);

    @Resource
    private FileRecordService fileRecordService;

    @DeleteMapping("/delete")
    @ApiOperation("删除文件")
    public HttpResponse deleteFile(@RequestBody FileRecord fileRecord) {
        return fileRecordService.deleteFile(fileRecord);
    }

    @GetMapping("/search")
    @ApiOperation("查询文件列表")
    public HttpResponse fileList(@RequestParam("file_id") String fileId) {
        return fileRecordService.fileList(fileId);
    }

    @GetMapping("/download")
    @ApiOperation("下载文件记录日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file_id", value = "file_id(可能是退供id/采购id或者其他id)", type = "String"),
            @ApiImplicitParam(name = "file_code", value = "文件编码", type = "String"),
            @ApiImplicitParam(name = "file_name", value = "文件名", type = "String"),
            @ApiImplicitParam(name = "create_by_id", value = "创建人", type = "String"),
            @ApiImplicitParam(name = "create_by_name", value = "创建人", type = "String"),
    })
    public HttpResponse downloadFile(@RequestParam("file_id") String fileId, @RequestParam("file_code") String fileCode, @RequestParam("file_name") String fileName,
                                     @RequestParam("create_by_id") String createById, @RequestParam("create_by_name") String createByName) {
        return fileRecordService.downloadFile(fileId, fileCode, createById, createByName, fileName);
    }

    @ApiOperation(value = "下载导入模板 传不同参数")
    @GetMapping("/download/model/{fileName}")
    public void getModel(HttpServletResponse response, HttpServletRequest request, @PathVariable String fileName) {
        OutputStream outputStream = null;
        BufferedInputStream bis = null;
        try {
            byte[] buff = new byte[1024];
            response.setContentType("text/plain;charset=utf-8");
            response.setHeader("Content-Type", "multipart/form-data");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            // 循环取出流中的数据
            String excelPath = request.getSession().getServletContext().getRealPath("/WEB-INF/template/" + fileName + ".xlsx");
            InputStream inStream = new FileInputStream(excelPath);//文件的存放路径
            bis = new BufferedInputStream(inStream);
            int i = bis.read(buff);
            outputStream = response.getOutputStream();
            while (i != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                i = bis.read(buff);
            }
            bis.close();
            inStream.close();
        } catch (Exception e) {
            LOGGER.error("下载模板异常,message:{},cause:{}", e.getMessage(), e.getCause());
        }
    }

}
