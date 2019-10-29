package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuFileDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFile;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.file.ProductSkuFileRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuInfoMapper;
import com.aiqin.bms.scmp.api.product.service.ProductFileService;
import com.aiqin.bms.scmp.api.supplier.domain.FilePathEnum;
import com.aiqin.bms.scmp.api.supplier.service.FileInfoService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: mamingze
 * @Date: 2019-10-28 17:06
 * @Description:
 */
@Service
public class ProductFileServiceImpl implements ProductFileService {
    @Autowired
    private ProductSkuDao productSkuDao;
    @Autowired
    private ProductSkuInfoMapper productSkuInfoMapper;
    @Autowired
    private ProductSkuFileDao productSkuFileDao;

    @Autowired
    private FileInfoService fileInfoService;


    @Override
    public HttpResponse loadFileProduct(@Valid @NotNull(message = "传入code不能为空") String code) {
        ProductSkuFileRespVo productSkuFileRespVo = new ProductSkuFileRespVo();
        ProductSkuInfo productSkuInfo =productSkuInfoMapper.selectBySkuCode(code);
        if (null==productSkuInfo){
           return HttpResponse.failure(ResultCode.FIND_NULL);
        }
        BeanCopyUtils.copy(productSkuInfo, productSkuFileRespVo);
        List<ProductSkuFile> productSkuFileList = productSkuFileDao.getInfo(productSkuFileRespVo.getSkuCode());
        productSkuFileRespVo.setProductSkuFileList(productSkuFileList);
        return HttpResponse.success(productSkuFileRespVo);
    }

    @Override
    public HttpResponse updateoradd(ProductSkuFileRespVo productSkuFileRespVo) {
        List<ProductSkuFile> productSkuFileList = Lists.newArrayList();
        ProductSkuFile productSkuFile=new ProductSkuFile();
       BeanCopyUtils.copy(productSkuFileRespVo,productSkuFile);

        if (StringUtil.isNullOrEmpty(String.valueOf(productSkuFile.getId()))) {
            productSkuFileList.add(productSkuFile);
            //进行数据库的存储
             productSkuFileDao.insertSkuFileList(productSkuFileList);

            return HttpResponse.success(productSkuFileDao.getInfo(productSkuFile.getSkuCode()));

        }
        //进行数据库的修改
          int num= productSkuFileDao.updateById(productSkuFile);
       if (num==0){
           return HttpResponse.failure(ResultCode.NOT_HAVE_PARAM);
       }
        return HttpResponse.success(productSkuFileDao.getInfo(productSkuFile.getSkuCode()));
    }

    @Override
    public HttpResponse deleteFile(Long id, String skuCode) {
        productSkuFileDao.deleteById(id);
        List<ProductSkuFile> productSkuFileList = productSkuFileDao.getInfo(skuCode);
        return HttpResponse.success(productSkuFileList);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse<String> uploadFiles(MultipartFile[] multipartFiles, String skuCode) {
      //  for (ProductSkuFileRespVo productSkuFileRespVo : productSkuFileRespVos) {
            List<ProductSkuFile> productSkuFileList = Lists.newArrayList();
        List<MultipartFile> multipartFileList= Arrays.stream(multipartFiles).collect(Collectors.toList());
        if (CollectionUtils.isNotEmptyCollection(multipartFileList)) {
            return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR2);
        }
        ProductSkuInfo productSkuInfo=productSkuDao.getSkuInfo(skuCode);
        String name=productSkuInfo.getSkuName();
            for (MultipartFile multipartFile :
                    multipartFileList) {
                ProductSkuFile productSkuFile=new ProductSkuFile();
                //进行sku编码的书写
                productSkuFile.setSkuCode(skuCode);
                productSkuFile.setSkuName(name);
                productSkuFile.setDelFlag(Byte.valueOf("0"));
                String fileCode = String.valueOf(System.currentTimeMillis()) + String.valueOf(Math.random() + (int) ((Math.random() * 9 + 1) * 1000));
                productSkuFile.setFileCode(fileCode.replace(".",""));
                productSkuFile.setFilePath(fileInfoService.fileUpload(multipartFile, FilePathEnum.PRODUCT_FILE.getCode()));
                productSkuFile.setFileName(multipartFile.getOriginalFilename());
                productSkuFileList.add(productSkuFile);
            }
            //新的加入的文件
            List<String> newProductSkuFileNames = productSkuFileList.stream().map(x -> x.getFileName()).collect(Collectors.toList());
            //原来的文件
            List<String> ordProductSkuFileNames = productSkuFileDao.getInfo(skuCode).stream().map(x -> x.getFileName()).collect(Collectors.toList());
            ordProductSkuFileNames.retainAll(newProductSkuFileNames);
            if (CollectionUtils.isNotEmptyCollection(ordProductSkuFileNames)) {
                return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR3);
            }
            //进行文件的添加
            productSkuFileDao.insertSkuFileList(productSkuFileList);

        //}
        return HttpResponse.success();
    }


}

