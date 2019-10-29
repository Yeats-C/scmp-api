package com.aiqin.bms.scmp.api.product.service.impl;

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
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import io.netty.util.internal.StringUtil;
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
    public ProductSkuFileRespVo loadFileProduct(@Valid @NotNull(message = "传入id不能为空") String code) {
        ProductSkuFileRespVo productSkuFileRespVo = new ProductSkuFileRespVo();
        ProductSkuInfo productSkuInfo =productSkuInfoMapper.selectByskuCode(code);
        BeanCopyUtils.copy(productSkuInfo, productSkuFileRespVo);
        List<ProductSkuFile> productSkuFileList = productSkuFileDao.getInfo(productSkuFileRespVo.getSkuCode());
        productSkuFileRespVo.setProductSkuFileList(productSkuFileList);
        return productSkuFileRespVo;
    }

    @Override
    public int updateoradd(ProductSkuFile productSkuFile) {
        List<ProductSkuFile> productSkuFileList = Lists.newArrayList();
        if (StringUtil.isNullOrEmpty(String.valueOf(productSkuFile.getId()))) {

            productSkuFileList.add(productSkuFile);
            //进行数据库的存储
            return productSkuFileDao.insertSkuFileList(productSkuFileList);
        }
        //进行数据库的修改
        return productSkuFileDao.updateById(productSkuFile);
    }

    @Override
    public int deleteFile(Long id) {
        return productSkuFileDao.deleteById(id);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse<String> uploadFiles(MultipartFile[] multipartFiles, String skuCode) {
      //  for (ProductSkuFileRespVo productSkuFileRespVo : productSkuFileRespVos) {
            List<ProductSkuFile> productSkuFileList = Lists.newArrayList();
        List<MultipartFile> multipartFileList= Arrays.stream(multipartFiles).collect(Collectors.toList());
        if (multipartFileList == null | multipartFileList.size() == 0) {
            throw new GroundRuntimeException("上传文件为空");
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
            if (ordProductSkuFileNames != null & ordProductSkuFileNames.size() > 0) {
                throw new GroundRuntimeException("该Sku商品下有相同文件名重复文件，请检查");
            }
            //进行文件的添加
            productSkuFileDao.insertSkuFileList(productSkuFileList);

        //}

        return HttpResponse.success();
    }


}

