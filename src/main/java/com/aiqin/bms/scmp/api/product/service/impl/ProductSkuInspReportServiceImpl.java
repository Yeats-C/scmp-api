package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuInspReportDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuInspReport;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInspReport;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInspReportDraft;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QueryProductSkuInspReportReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.SaveProductSkuInspReportItemReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.SaveProductSkuInspReportReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuInspReportRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuInspReportDraftMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuInspReportMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuInspReportService;
import com.aiqin.bms.scmp.api.supplier.domain.FilePathEnum;
import com.aiqin.bms.scmp.api.supplier.service.FileInfoService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/3/13 0013 16:32
 */
@Service
public class ProductSkuInspReportServiceImpl extends BaseServiceImpl implements ProductSkuInspReportService {
    @Autowired
    ProductSkuInspReportDao productSkuInspReportDao;
    @Autowired
    private ProductSkuInspReportDraftMapper draftMapper;
    @Autowired
    private ProductSkuInspReportMapper productSkuInspReportMapper;
    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private  ProductSkuInspReportService productSkuInspReportService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveApplyList(List<ApplyProductSku> applyProductSkus) {
       try {
            List<ApplyProductSkuInspReport> applyProductSkuInspReports = new ArrayList<>();
            List<ProductSkuInspReportDraft> productSkuInspReportDrafts = productSkuInspReportDao.getDrafts(applyProductSkus);
            if (productSkuInspReportDrafts != null && productSkuInspReportDrafts.size() > 0){
                for (int i=0;i<productSkuInspReportDrafts.size();i++){
                    ApplyProductSkuInspReport applyProductSkuInspReport = new ApplyProductSkuInspReport();
                    BeanCopyUtils.copy(productSkuInspReportDrafts.get(i),applyProductSkuInspReport);
                    applyProductSkuInspReport.setApplyCode(applyProductSkus.get(0).getApplyCode());
                    applyProductSkuInspReports.add(applyProductSkuInspReport);
                }
                //批量新增申请
                ((ProductSkuInspReportService) AopContext.currentProxy()).insertApplyList(applyProductSkuInspReports);
                //批量删除草稿
                productSkuInspReportDao.deleteDrafts(applyProductSkus);
            }
            return 1;
       } catch (BizException e){
           throw new BizException(e.getMessage());
       }
    }

    @Override
    @SaveList
    @Transactional(rollbackFor = Exception.class)
    public int insertApplyList(List<ApplyProductSkuInspReport> applyProductSkuInspReports) {
        try {
            return productSkuInspReportDao.insertApplyList(applyProductSkuInspReports);
        } catch (BizException e){
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(String skuCode,String applyCode) {
        List<ApplyProductSkuInspReport> applyProductSkuInspReports = productSkuInspReportDao.getApply(skuCode,applyCode);
        if (null != applyProductSkuInspReports && applyProductSkuInspReports.size() > 0){
            List<ProductSkuInspReport> oldReport = productSkuInspReportDao.getInfo(skuCode);
            List<ProductSkuInspReport> productSkuInspReports = new ArrayList<>();
            applyProductSkuInspReports.forEach(item->{
                ProductSkuInspReport productSkuInspReport = new ProductSkuInspReport();
                BeanCopyUtils.copy(item,productSkuInspReport);
                productSkuInspReports.add(productSkuInspReport);
            });
            if (null != oldReport && oldReport.size() > 0){
                productSkuInspReportDao.deleteList(skuCode);
            }
            return ((ProductSkuInspReportService)AopContext.currentProxy()).insertList(productSkuInspReports);
        } else {
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertList(List<ProductSkuInspReport> productSkuInspReports) {
        int num = productSkuInspReportDao.insertInspReportList(productSkuInspReports);
        return num;
    }

    @Override
    @SaveList
    @Transactional(rollbackFor = Exception.class)
    public int insertDraftList(List<ProductSkuInspReportDraft> productSkuInspReportDrafts) {
        int num = productSkuInspReportDao.insertDraftList(productSkuInspReportDrafts);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDraftList(String applyCode) {
        List<ApplyProductSkuInspReport> applyProductSkuInspReports = productSkuInspReportDao.getApply(null,applyCode);
        if(CollectionUtils.isNotEmpty(applyProductSkuInspReports)){
            List<ProductSkuInspReportDraft> productSkuInspReportDrafts = BeanCopyUtils.copyList(applyProductSkuInspReports, ProductSkuInspReportDraft.class);
            return  ((ProductSkuInspReportService)AopContext.currentProxy()).insertDraftList(productSkuInspReportDrafts);
        }
        return 0;
    }

    /**
     * 获取临时数据
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<ProductSkuInspReportRespVo> getDraftList(String skuCode) {
        return productSkuInspReportDao.getDraft(skuCode);
    }

    /**
     * 删除临时表数据
     *
     * @param skuCodes
     * @return
     */
    @Override
    public Integer deleteDrafts(List<String> skuCodes) {
        return draftMapper.delete(skuCodes);
    }

    /**
     * 功能描述: 获取正式表数据
     *
     * @param reportReqVo
     * @return
     * @auther knight.xie
     * @date 2019/7/2 17:49
     */
    @Override
    public List<ProductSkuInspReportRespVo> getList(QueryProductSkuInspReportReqVo reportReqVo) {
        return productSkuInspReportDao.getListBySkuCodeAndProductDate(reportReqVo);
    }

    /**
     * 功能描述: 质检报告保存接口
     *
     * @param reportReqVo
     * @return
     * @auther knight.xie
     * @date 2019/7/3 17:43
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveProductSkuInspReports(SaveProductSkuInspReportReqVo reportReqVo) {
        List<ProductSkuInspReport> productSkuInspReports =
                BeanCopyUtils.copyList(reportReqVo.getItemList(),ProductSkuInspReport.class);
        productSkuInspReports.forEach(item->{
            item.setSkuCode(reportReqVo.getSkuCode());
            item.setSkuName(reportReqVo.getSkuName());
        });
        return ((ProductSkuInspReportService)AopContext.currentProxy()).insertList(productSkuInspReports);
    }

    /**
     * 功能描述: 根据Id删除正式表数据
     *
     * @param id
     * @return
     * @auther knight.xie
     * @date 2019/7/4 10:25
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id) {
        return productSkuInspReportDao.deleteById(id);
    }

    /**
     * 功能描述: 根据SkuCode删除正式表数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/4 10:28
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBySkuCode(String skuCode) {
        return productSkuInspReportDao.deleteList(skuCode);
    }

    /**
     * 功能描述: 单个保存质检报告
     *
     * @param reportReqVo
     * @return
     * @auther knight.xie
     * @date 2019/7/4 10:29
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Save
    public int saveProductSkuInspReport(ProductSkuInspReport reportReqVo) {
        //根据SKU和生产日期查询
        QueryProductSkuInspReportReqVo queryReqVo = new QueryProductSkuInspReportReqVo();
        queryReqVo.setProductionDate(reportReqVo.getProductionDate());
        queryReqVo.setSkuCode(reportReqVo.getSkuCode());
        List<ProductSkuInspReportRespVo> list = this.getList(queryReqVo);
        if(CollectionUtils.isNotEmpty(list)){
            List<Long> ids = list.stream().map(ProductSkuInspReportRespVo::getId).collect(Collectors.toList());
            productSkuInspReportMapper.deleteByIds(ids);
        }
        return productSkuInspReportMapper.insertSelective(reportReqVo);
    }

    /**
     * 功能描述: 获取申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:21
     */
    @Override
    public List<ProductSkuInspReportRespVo> getApply(String skuCode, String applyCode) {
        return productSkuInspReportDao.getApplys(skuCode,applyCode);
    }

    /**
     * 功能描述: 获取正式表数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:22
     */
    @Override
    public List<ProductSkuInspReportRespVo> getListBySkuCode(String skuCode) {
        return productSkuInspReportDao.getList(skuCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
        public HttpResponse<String> uploadFiles(MultipartFile[] multipartFiles, String skuCode) {
        AuthToken authToken=getUser();
        String personId= authToken.getPersonId();
       Integer num= productSkuInspReportDao.getPersonIdByskuCode(personId,skuCode);

        if (num<1){
            return HttpResponse.failure(ResultCode.NOT_HAVE_PARAM,"该采购组没有对应的skuCode");
        }
       List<MultipartFile> multipartFileList= Arrays.stream(multipartFiles).collect(Collectors.toList());
        List<String> multipartFileListName=multipartFileList.stream().map(x->x.getOriginalFilename()).distinct().collect(Collectors.toList());
        if(multipartFileListName.size()!=multipartFileList.size()){
            return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR3);
        }

        if (com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(multipartFileList)) {
            return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR2);
        }
        SaveProductSkuInspReportReqVo saveProductSkuInspReportReqVo=new SaveProductSkuInspReportReqVo();
        saveProductSkuInspReportReqVo.setSkuCode(skuCode);
        List<SaveProductSkuInspReportItemReqVo> saveProductSkuInspReportItemReqVos= Lists.newArrayList();
        for (MultipartFile multipartFile:
        multipartFileList ) {

            String fileName=multipartFile.getOriginalFilename();
            if (fileName.indexOf('/')>=0){
                fileName=fileName.substring(fileName.indexOf('/')+1);

            }
            SaveProductSkuInspReportItemReqVo saveProductSkuInspReportItemReqVo=new SaveProductSkuInspReportItemReqVo();
            saveProductSkuInspReportItemReqVo.setInspectionReportPath(fileInfoService.fileUpload(multipartFile, FilePathEnum.PRODUCT_FILE.getCode()));
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            //使用SimpleDateFormat的parse()方法生成Date
            try {
                Date date = sf.parse(fileName);
                saveProductSkuInspReportItemReqVo.setProductionDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return HttpResponse.failure(ResultCode.FILE_UPLOAD_ERROR4);
            }
            saveProductSkuInspReportItemReqVos.add(saveProductSkuInspReportItemReqVo);
        }
        saveProductSkuInspReportReqVo.setItemList(saveProductSkuInspReportItemReqVos);
        productSkuInspReportService.saveProductSkuInspReports(saveProductSkuInspReportReqVo);
        return HttpResponse.success();
    }
}
