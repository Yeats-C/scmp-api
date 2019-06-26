package com.aiqin.bms.scmp.api.purchase.web;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import com.aiqin.bms.scmp.api.purchase.service.FileRecordService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
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
}
