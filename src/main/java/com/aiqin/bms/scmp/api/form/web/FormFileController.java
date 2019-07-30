package com.aiqin.bms.scmp.api.form.web;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.platform.flows.client.domain.vo.FileVo;
import com.aiqin.platform.flows.client.service.FormFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/form/file/activiti")
@Api(tags = "审批流-上传附件")
@Slf4j
public class FormFileController {

    @Resource
    private FormFileService formFileService;

    @PostMapping("/")
    @ApiOperation("历史消息中上传附件")
    HttpResponse saveFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            List<FileVo> fileList = new ArrayList<>();
            if (files != null && files.length > 0) {
                FileVo fileVo;
                for (MultipartFile file : files) {
                    String encode = Base64.getEncoder().encodeToString(file.getBytes());
                    String fileName = file.getOriginalFilename();
                    String fileType = fileName.substring(file.getOriginalFilename().lastIndexOf("."));
                    fileVo = new FileVo(fileType, fileName, encode, "msg", file.getSize());
                    fileList.add(fileVo);
                }
            }
            log.info("历史消息上传附件,fileList={}", fileList);
            return formFileService.saveFiles(fileList);
        } catch (Exception e) {
            log.info("历史消息上传附件异常,{}", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }

    }

    @DeleteMapping("/")
    @ApiOperation("历史消息中删除附件")
    HttpResponse saveFiles(@RequestParam() String fileKey) {
        return formFileService.deleteFile(fileKey);
    }

}
