package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @功能说明:文件相关service
 * @author: wangxu
 * @date: 2018/12/18 0018 14:51
 */
public interface FileInfoService {
    /**
     * 文件图片上传
     * @param base64
     * @return
     */
    HttpResponse uploadFile(String base64);

    String fileUpload(MultipartFile file);
    /**
     * 文件上传
     * @author zth
     * @date 2019/3/15
     * @param file
     * @return java.lang.String
     */
    String upload(MultipartFile file);
    /**
     * 批量上传
     * @author NullPointException
     * @date 2019/5/31
     * @param files
     * @return java.util.List<java.lang.String>
     */
    List<String> multiUpload(List<MultipartFile> files);
}
