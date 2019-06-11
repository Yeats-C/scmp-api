package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.supplier.service.FileInfoService;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.UploadFileUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @功能说明:文件相关service实现
 * @author: wangxu
 * @date: 2018/12/18 0018 14:52
 */
@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Autowired
    private UploadFileUtil uploadFileUtil;

    @Override
    public HttpResponse uploadFile(String base64) {
        try {
            String url = uploadFileUtil.uploadFileByBase64(base64);
            return HttpResponse.success(url);
        } catch (Exception e){
            return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public String fileUpload(MultipartFile file) {
        String url = null;
        try {
            if (file.getBytes().length<=0){
                throw new GroundRuntimeException("文件不能为空");
            }
            url = uploadFileUtil.upload(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public String upload(MultipartFile file) {
        String url = null;
        try {
            if (file.getBytes().length<=0){
                throw new BizException(ResultCode.FILE_EMPTY);
            }
            if(!file.getContentType().contains("image")){
                throw new BizException(ResultCode.PLEASE_UPLOAD_AN_IMAGE);
            }
            url = uploadFileUtil.upload(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
    @Override
    public List<String>  multiUpload(List<MultipartFile> files){
        if(CollectionUtils.isEmptyCollection(files)){
            throw new BizException(ResultCode.FILE_EMPTY);
        }
        List<String> urlList = Lists.newArrayList();
        for (MultipartFile file : files) {
            urlList.add(upload(file));
        }
        return urlList;
    }
}
