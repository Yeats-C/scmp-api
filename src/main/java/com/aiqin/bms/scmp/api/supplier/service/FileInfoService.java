package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    String fileUpload(MultipartFile file,Boolean isRename);
    /**
     * 文件上传
     * @author zth
     * @date 2019/3/15
     * @param file
     * @return java.lang.String
     */
    String upload(MultipartFile file,Boolean isRename);
    /**
     * 批量上传
     * @author NullPointException
     * @date 2019/5/31
     * @param files
     * @return java.util.List<java.lang.String>
     */
    List<String> multiUpload(List<MultipartFile> files);

    /**
     *
     * 功能描述: 图片下载
     *
     * @param filePath
     * @return
     * @auther knight.xie
     * @date 2019/7/25 19:13
     */
    String down(String filePath);


    /**
     *
     * 功能描述: 文件上传
     *
     * @param file
     * @param type
     * @return
     * @auther knight.xie
     * @date 2019/10/15 14:42
     */
    String fileUpload(MultipartFile file,String type);


    /**
     *
     * 功能描述: 拷贝文件
     *
     * @param key
     * @param destinationKey
     * @param isDelSource
     * @return
     * @auther knight.xie
     * @date 2019/10/16 11:07
     */
    String copyObject(String key,String destinationKey,Boolean isDelSource);

    /**
     *
     * 功能描述: 根据URL获取key
     *
     * @param url
     * @return
     * @auther knight.xie
     * @date 2019/10/16 11:07
     */
    Map<String,String> getKeyAndType(String url);
}
