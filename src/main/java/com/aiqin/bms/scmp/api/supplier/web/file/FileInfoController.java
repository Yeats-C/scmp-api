package com.aiqin.bms.scmp.api.supplier.web.file;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.supplier.service.FileInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/18 0018 15:12
 */
@RestController
@RequestMapping("/file")
@Api(description = "文件/图片")
public class FileInfoController {
    @Autowired
    private FileInfoService fileInfoService;

    @PostMapping("/upload")
    @ApiModelProperty(value = "文件上传")
    @ApiOperation(value = "上传文件或图片 (请以data:image或data:file开头)")
    public HttpResponse<String> uploadFile(@RequestBody String base64){
        try {
            if (null == base64) {
                return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
            }
            return fileInfoService.uploadFile(base64);
        } catch (Exception e){
            return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR);
        }
    }

    @PostMapping("/uploadFile")
    @ApiModelProperty(value = " 文件上传")
    public HttpResponse<String> fileUpload(@NotNull MultipartFile file){
        return HttpResponse.success(fileInfoService.fileUpload(file));
    }

    @PostMapping("/upload/pic")
    @ApiModelProperty(value = "图片上传")
    public HttpResponse<String> uploadFile(@NotNull MultipartFile file){
        try {
            return HttpResponse.success(fileInfoService.upload(file));
        } catch (BizException ex) {
            return HttpResponse.failure(ex.getMessageId());
        }catch (Exception e) {
                return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR);
        }
    }
    @PostMapping("/multiUpload/pic")
    @ApiModelProperty(value = "批量图片上传")
    public HttpResponse<List<String>> uploadFile(@NotEmpty List<MultipartFile> file){
        try {
            return HttpResponse.success(fileInfoService.multiUpload(file));
        } catch (BizException ex) {
            return HttpResponse.failure(ex.getMessageId());
        }catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR);
        }
    }
}
