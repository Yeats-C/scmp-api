package com.aiqin.bms.scmp.api.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class UploadFileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileUtil.class);
    @Value("${oss.end.point}")
    private String endPoint;
    @Value("${oss.access.key.id}")
    private String accessKeyId;
    @Value("${oss.access.key.secret}")
    private String accessKeySecret;
    @Value("${oss.bucket.name}")
    private String bucketName;
    @Value("${oss.dir}")
    private String dir;

    public String uploadFileByBase64(String base64) throws UnsupportedEncodingException {
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        JSONObject jsonObject =  JSONObject.parseObject(base64);
        base64 = jsonObject.getString("base64");
        String fileType = "";
        if (base64.startsWith("data:image")) {
            fileType = base64.substring(11, base64.indexOf(";"));
        }
        if (base64.startsWith("data:img")) {
            fileType = base64.substring(9, base64.indexOf(";"));
        }
        if (base64.startsWith("data:file")) {
            fileType = base64.substring(10, base64.indexOf(";"));
        }
        String fileName = dir + UUID.randomUUID() + "." + fileType;
        if (StringUtils.indexOf(base64, ",") > 0) {
            base64 = StringUtils.substringAfterLast(base64, ",");
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64);
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(bytes1));
            ossClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        //返回文件路径
        String url = ossClient.generatePresignedUrl(bucketName, fileName, expiration).toString();
        //LOGGER.info("oss图片链接,{}", url);
        return url;
    }

    public String uploadFile(MultipartFile file) throws Exception {
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = dir + UUID.randomUUID() + type;
        ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(file.getBytes()));
        ossClient.shutdown();
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(bucketName, fileName, expiration).toString();
        //LOGGER.info("oss文件链接,{}", url);
        return url;
    }

    public boolean deleteFile(String filePath) {
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        boolean exist = ossClient.doesObjectExist(bucketName, filePath);
        if (!exist) {
            //LOGGER.error("文件不存在,filePath={}", filePath);
            return false;
        }
        //LOGGER.info("删除文件,filePath={}", filePath);
        ossClient.deleteObject(bucketName, filePath);
        ossClient.shutdown();
        return true;
    }

    public String upload(MultipartFile file) {
        String url = null;
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        InputStream inputStream = null;
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = dir + UUID.randomUUID() + type;
        try {
            inputStream = file.getInputStream();
            ossClient.putObject(bucketName,fileName, inputStream);
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
            URL url1 = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
            assert StringUtils.isNotBlank(url1.toString());
            log.info("上传成功：url=[{}]",url1.toString());
            url = url1.toString();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("上传文件失败");
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
        return url;
    }

    public String uploadImage(String base64) {
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        String imageType = "jpg";
        if (base64.startsWith("data:image")) {
            imageType = base64.substring(11, base64.indexOf(";"));
        }
        if (base64.startsWith("data:img")) {
            imageType = base64.substring(9, base64.indexOf(";"));
        }
        String fileName = dir + UUID.randomUUID() + "." + imageType;
        if (StringUtils.indexOf(base64, ",") > 0) {
            base64 = StringUtils.substringAfterLast(base64, ",");
        }
        ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(Base64.getDecoder().decode(base64)));
        ossClient.shutdown();
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(bucketName, fileName, expiration).toString();
        LOGGER.info("oss图片链接,{}", url);
        return url;
    }
}
