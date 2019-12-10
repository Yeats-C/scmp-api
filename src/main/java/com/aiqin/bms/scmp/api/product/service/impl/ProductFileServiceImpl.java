package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.StatusTypeCode;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuFileDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFile;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuFileReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuFileRespVO;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: mamingze
 * @Date: 2019-10-28 17:06
 * @Description:
 */
@Service
public class ProductFileServiceImpl extends BaseServiceImpl implements ProductFileService  {
    @Autowired
    private ProductSkuDao productSkuDao;
    @Autowired
    private ProductSkuInfoMapper productSkuInfoMapper;
    @Autowired
    private ProductSkuFileDao productSkuFileDao;

    @Autowired
    private FileInfoService fileInfoService;


    @Override
    public HttpResponse loadFileProduct(@Valid @NotNull(message = "传入code不能为空") String code) throws ParseException {
        ProductSkuFileRespVo productSkuFileRespVo = new ProductSkuFileRespVo();
        ProductSkuInfo productSkuInfo =productSkuInfoMapper.selectBySkuCode(code);
        if (null==productSkuInfo){
           return HttpResponse.failure(ResultCode.FIND_NULL);
        }
        BeanCopyUtils.copy(productSkuInfo, productSkuFileRespVo);
        productSkuFileRespVo.setProductSortName(productSkuInfo.getProductSortName());
        productSkuFileRespVo.setPurchaseGroupName(productSkuInfo.getProcurementSectionName());
        List<ProductSkuFileRespVO> productSkuFileList = productSkuFileDao.getInfoBySkuCode(productSkuFileRespVo.getSkuCode());

        for (ProductSkuFileRespVO productSkuFileRespVO:
        productSkuFileList ) {
            if (productSkuFileRespVO.getCreateTime()!=null&&String.valueOf(productSkuFileRespVO.getCreateTime())!=""){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str=sdf.format(productSkuFileRespVO.getCreateTime());
            Date time=sdf.parse(str);
            productSkuFileRespVO.setCreateTime(time);
            }
        }

        productSkuFileRespVo.setProductSkuFileList(productSkuFileList);
        return HttpResponse.success(productSkuFileRespVo);
    }

    @Override
    public HttpResponse updateOrAdd(ProductSkuFileReqVo productSkuFileRespVo) throws ParseException {
        if(null == productSkuFileRespVo || StringUtils.isBlank(productSkuFileRespVo.getFileName())
                || StringUtils.isBlank(productSkuFileRespVo.getFilePath()) || StringUtils.isBlank(productSkuFileRespVo.getSkuCode())
                || StringUtils.isBlank(productSkuFileRespVo.getSkuCode())){
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        }
        String userName= getUser().getPersonName();
        List<ProductSkuFile> productSkuFileList = Lists.newArrayList();
        ProductSkuFile productSkuFile=new ProductSkuFile();
        BeanCopyUtils.copy(productSkuFileRespVo,productSkuFile);
        if (null==productSkuFileRespVo.getId()) {

            productSkuFile.setCreateBy(userName);
            productSkuFile.setCreateTime(new Date());
            productSkuFile.setUpdateBy(userName);
            productSkuFile.setUpdateTime(new Date());
            productSkuFile.setDelFlag(StatusTypeCode.DEL_FLAG.getStatus());
            productSkuFile.setFileCode(getCode());
            productSkuFileList.add(productSkuFile);
            //进行数据库的存储
             productSkuFileDao.insertSkuFileList(productSkuFileList);
            List<ProductSkuFileRespVO> productSkuFiles = productSkuFileDao.getInfoBySkuCode(productSkuFileRespVo.getSkuCode());
            for (ProductSkuFileRespVO productSkuFileRespVO:
                    productSkuFiles ) {
                if (productSkuFileRespVO.getCreateTime()!=null&&String.valueOf(productSkuFileRespVO.getCreateTime())!=""){
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String str=sdf.format(productSkuFileRespVO.getCreateTime());
                    Date time=sdf.parse(str);
                    productSkuFileRespVO.setCreateTime(time);
                }
            }
            return HttpResponse.success(productSkuFiles);
        }
        productSkuFile.setUpdateBy(userName);
        productSkuFile.setUpdateTime(new Date());
        //进行数据库的修改
          int num= productSkuFileDao.updateById(productSkuFile);
       if (num==0){
           return HttpResponse.failure(ResultCode.FIND_NULL);
       }
        return HttpResponse.success(productSkuFileDao.getInfoBySkuCode(productSkuFile.getSkuCode()));
    }

    private String getCode() {
        String fileCode = String.valueOf(System.currentTimeMillis()) + String.valueOf(Math.random() + (int) ((Math.random() * 9 + 1) * 1000));
        return fileCode.replace(".", "");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse deleteFile(Long id, String skuCode) {
        int num=productSkuFileDao.deleteById(id);
        if (num==0){
            return HttpResponse.failure(ResultCode.FIND_NULL);
        }
        return HttpResponse.success(productSkuFileDao.getInfoBySkuCode(skuCode));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse<String> uploadFiles(MultipartFile[] multipartFiles, String skuCode) {
      //  for (ProductSkuFileRespVo productSkuFileRespVo : productSkuFileRespVos) {
            List<ProductSkuFile> productSkuFileList = Lists.newArrayList();
        List<MultipartFile> multipartFileList= Arrays.stream(multipartFiles).collect(Collectors.toList());
        if (CollectionUtils.isEmptyCollection(multipartFileList)) {
            return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR2);
        }
        ProductSkuInfo productSkuInfo=productSkuDao.getSkuInfo(skuCode);
        ProductSkuFile productSkuFile;
        if(null==productSkuInfo){
            return HttpResponse.failure(ResultCode.FIND_NULL);
        }
        String name=productSkuInfo.getSkuName();
            for (MultipartFile multipartFile :
                    multipartFileList) {
                productSkuFile=new ProductSkuFile();
                //进行sku编码的书写
                productSkuFile.setSkuCode(skuCode);
                productSkuFile.setSkuName(name);
                productSkuFile.setDelFlag(StatusTypeCode.DEL_FLAG.getStatus());
                productSkuFile.setFileCode(getCode());
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
        return HttpResponse.success(productSkuFileList);
    }


}

