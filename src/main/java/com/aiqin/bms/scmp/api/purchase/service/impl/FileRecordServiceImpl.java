package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicDescDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicturesDraft;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPicDescDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPicturesDraftMapper;
import com.aiqin.bms.scmp.api.purchase.dao.FileRecordDao;
import com.aiqin.bms.scmp.api.purchase.dao.OperationLogDao;
import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import com.aiqin.bms.scmp.api.purchase.domain.OperationLog;
import com.aiqin.bms.scmp.api.purchase.service.FileRecordService;
import com.aiqin.bms.scmp.api.supplier.service.impl.FileInfoServiceImpl;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-06-26
 **/
@Service
public class FileRecordServiceImpl implements FileRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRecordServiceImpl.class);

    @Resource
    private FileRecordDao fileRecordDao;
    @Resource
    private OperationLogDao operationLogDao;
    @Resource
    private ProductSkuPicDescDraftMapper productSkuPicDescDraftMapper;
    @Resource
    private ProductSkuPicturesDraftMapper productSkuPicturesDraftMapper;
    @Resource
    private ProductSkuDraftMapper productSkuDraftMapper;
    @Resource
    private FileInfoServiceImpl fileInfoService;

    public static void main(String[] args) {
        String ss = "ss/sd/ff/ss.gg";
        System.out.println(ss.substring(0, ss.indexOf(".")));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse deleteFile(FileRecord fileRecord) {
        if (fileRecord == null || StringUtils.isBlank(fileRecord.getFileId())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Integer count = fileRecordDao.update(fileRecord);
        if (count == 0) {
            LOGGER.info("删除采购文件失败");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse fileList(String fileId) {
        if (StringUtils.isBlank(fileId)) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<FileRecord> files = fileRecordDao.fileList(fileId);
        return HttpResponse.success(files);
    }

    @Override
    public HttpResponse downloadFile(String id, String fileId, String createById, String createByName, String fileName) {
        //增加操作记录 操作状态  : 0 新增 1 修改 2 下载
        operationLogDao.insert(new OperationLog(id, 2, String.format("下载文件,文件名:{%s},文件编码:{%s}", fileName, fileId), "", createById, createByName));
        return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public HttpResponse<String> uploadImageFolder(MultipartFile[] folders, String create_by_id, String create_by_name) {
        List<String> fileNames = Arrays.asList("1", "2", "3", "4", "5", "sm_1", "sm_2", "sm_3", "sm_4", "sm_5");
        try {
            String url;
            String fileName;
            String folderName = "";
            MultipartFile multipartFile;
            ProductSkuDraft productSkuDraft;
            // 1.png对应图片及介绍
            List<ProductSkuPicDescDraft> productSkuPicDescDraftList = new ArrayList<>();
            ProductSkuPicDescDraft productSkuPicDescDraft;
            //sm_1.png对应介绍图
            List<ProductSkuPicturesDraft> productSkuPicturesDraftList = new ArrayList<>();
            ProductSkuPicturesDraft productSkuPicturesDraft;
            for (int i = 0; i < folders.length; i++) {
                multipartFile = folders[i];
                String[] split = multipartFile.getOriginalFilename().split("/");
                folderName = split[split.length - 2];
                //todo 优化
                productSkuDraft = productSkuDraftMapper.selectProductByFolderCode(folderName);
                if (productSkuDraft == null) {
                    LOGGER.error("通过文件夹编码:{},未查询到商品信息", folderName);
                    throw new GroundRuntimeException(String.format("通过文件夹编码:%s,未查询到商品信息", folderName));
                }
                fileName = split[split.length - 1];
                if ((fileName.contains("jpg")||fileName.contains("png"))&&!fileNames.contains(fileName.substring(0, fileName.indexOf(".")))) {
                    LOGGER.info("文件名:{},未包含在导入范围内", fileName);
                    continue;
                }
                url = fileInfoService.upload(multipartFile);
                LOGGER.info("fileName:{},folderName:{},url:{}", fileName, folderName, url);
                if (fileName.contains("sm_")) {
                    productSkuPicturesDraft = new ProductSkuPicturesDraft();
                    productSkuPicturesDraft.setProductPicturePath(url);
                    productSkuPicturesDraft.setProductPictureName(fileName);
                    productSkuPicturesDraft.setProductSkuCode(productSkuDraft.getSkuCode());
                    productSkuPicturesDraft.setProductSkuName(productSkuDraft.getSkuName());
                    productSkuPicturesDraft.setCreateBy(create_by_name);
                    productSkuPicturesDraftList.add(productSkuPicturesDraft);
                }else{
                    productSkuPicDescDraft = new ProductSkuPicDescDraft();
                    productSkuPicDescDraft.setSortingNumber((long) productSkuPicDescDraftList.size()+1);
                    productSkuPicDescDraft.setPicDescPath(url);
                    productSkuPicDescDraft.setSkuCode(productSkuDraft.getSkuCode());
                    productSkuPicDescDraft.setSkuName(productSkuDraft.getSkuName());
                    productSkuPicDescDraft.setCreateBy(create_by_name);
                    productSkuPicDescDraft.setUpdateBy(create_by_name);
                    productSkuPicDescDraftList.add(productSkuPicDescDraft);
                }
            }
            Integer picturesCount = productSkuPicturesDraftMapper.insertAll(productSkuPicturesDraftList);
            LOGGER.info("sm_1.png对应介绍图,添加条数:{}",picturesCount);
            Integer picCount = productSkuPicDescDraftMapper.insertAll(productSkuPicDescDraftList);
            LOGGER.info("1.png对应图片及介绍,添加条数:{}",picCount);
            return HttpResponse.successGenerics(folderName);
        } catch (GroundRuntimeException e) {
            LOGGER.error("导入商品图片信息异常:{}", e.getMessage());
            throw new GroundRuntimeException(String.format("导入商品图片信息异常:%s", e.getMessage()));
        }
    }
}
