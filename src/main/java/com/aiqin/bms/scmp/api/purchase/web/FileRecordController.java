package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import com.aiqin.bms.scmp.api.purchase.service.FileRecordService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: zhao shuai
 * @create: 2019-06-26 21:19
 **/

@Api(tags = "文件记录")
@RequestMapping("/file")
@RestController
public class FileRecordController {

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
            @ApiImplicitParam(name = "id", value = "id(可能是退供/采购或者其他)", type = "String"),
            @ApiImplicitParam(name = "file_id", value = "文件编码", type = "String"),
            @ApiImplicitParam(name = "file_name", value = "文件名", type = "String"),
            @ApiImplicitParam(name = "create_by_id", value = "创建人", type = "String"),
            @ApiImplicitParam(name = "create_by_name", value = "创建人", type = "String"),
    })
    public HttpResponse downloadFile(@RequestParam("id") String id,@RequestParam("file_id") String fileId,@RequestParam("file_name") String fileName,
                                     @RequestParam("create_by_id") String createById,@RequestParam("create_by_name") String createByName) {
        return fileRecordService.downloadFile(id,fileId,createById,createByName,fileName);
    }

}
