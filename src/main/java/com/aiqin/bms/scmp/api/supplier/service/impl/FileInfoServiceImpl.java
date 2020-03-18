package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.supplier.domain.FilePathEnum;
import com.aiqin.bms.scmp.api.supplier.service.FileInfoService;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.UploadFileUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @功能说明:文件相关service实现
 * @author: wangxu
 * @date: 2018/12/18 0018 14:52
 */
@Service
@Slf4j
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
    public String fileUpload(MultipartFile file,Boolean isRename) {
        String url = null;
        try {
            if (file.getBytes().length<=0){
                throw new GroundRuntimeException("文件不能为空");
            }
            url = uploadFileUtil.upload(file,isRename);
        } catch (IOException e) {
            log.error(Global.ERROR, e);
        }
        return url;
    }

    @Override
    public String upload(MultipartFile file,Boolean isRename) {
        String url = null;
        try {
            if (file.getBytes().length<=0){
                throw new BizException(ResultCode.FILE_EMPTY);
            }
            if(!file.getContentType().contains("image")){
                throw new BizException(ResultCode.PLEASE_UPLOAD_AN_IMAGE);
            }
            url = uploadFileUtil.upload(file,isRename);
        } catch (IOException e) {
            log.error(Global.ERROR, e);
        }
        return url;
    }

    @Override
    public String upload(MultipartFile file, String fileName) {
        String url = null;
        try {
            if (file.getBytes().length<=0){
                throw new BizException(ResultCode.FILE_EMPTY);
            }
            if(StringUtils.isBlank(fileName)){
                throw new BizException(ResultCode.FILE_NAME_EMPTY);
            }
            if(!file.getContentType().contains("image")){
                throw new BizException(ResultCode.PLEASE_UPLOAD_AN_IMAGE);
            }
            url = uploadFileUtil.uploadSkuImage(file,fileName);
        } catch (IOException e) {
            log.error(Global.ERROR, e);
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
            urlList.add(upload(file,true));
        }
        return urlList;
    }

    /**
     * 功能描述: 图片下载
     *
     * @param filePath
     * @return
     * @auther knight.xie
     * @date 2019/7/25 19:13
     */
    @Override
    public String down(String filePath) {
        try {
            return uploadFileUtil.downImage(filePath);
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            throw new BizException(ResultCode.FILE_DOWN_ERROR);
        }
    }

    /**
     * 功能描述: 文件上传
     *
     * @param file
     * @param type
     * @return
     * @auther knight.xie
     * @date 2019/10/15 14:42
     */
    @Override
    public String fileUpload(MultipartFile file, String type) {
        String url = null;
        try {
            if (file.getBytes().length<=0){
                throw new GroundRuntimeException("文件不能为空");
            }
            String filePath = FilePathEnum.getFilePathEnumByCode(type);
            url = uploadFileUtil.upload(file,filePath);
        } catch (IOException e) {
            log.error(Global.ERROR, e);
        }
        return url;
    }

    /**
     * 功能描述: 拷贝文件
     *
     * @param key
     * @param destinationKey
     * @param isDelSource
     * @return
     * @auther knight.xie
     * @date 2019/10/16 11:07
     */
    @Override
    public String copyObject(String key, String destinationKey, Boolean isDelSource) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        return uploadFileUtil.copyObject(key, destinationKey, isDelSource);
    }

    /**
     * 功能描述: 根据URL获取key
     *
     * @param url
     * @return
     * @auther knight.xie
     * @date 2019/10/16 11:07
     */
    @Override
    public Map<String,String> getKeyAndType(String url) {
        return uploadFileUtil.getKey(url);
    }
}
