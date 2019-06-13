package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.WorkFlowReturn;
import com.aiqin.bms.scmp.api.product.domain.dto.changeprice.ProductSkuChangePriceDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.*;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.ProductSkuChangePriceInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.ProductSkuChangePriceRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QueryProductSkuChangePriceRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.ProductSkuChangePriceService;
import com.aiqin.bms.scmp.api.product.service.SkuInfoService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-20
 * @time: 17:34
 */
@Service
@Slf4j
@WorkFlowAnnotation(WorkFlow.VARIABLE_PRICE)
public class ProductSkuChangePriceServiceImpl extends BaseServiceImpl implements ProductSkuChangePriceService, WorkFlowHelper {

    @Autowired
    private ProductSkuChangePriceMapper productSkuChangePriceMapper;

    @Autowired
    private ProductSkuChangePriceInfoMapper productSkuChangePriceInfoMapper;

    @Autowired
    private ProductSkuChangePriceImageMapper productSkuChangePriceImageMapper;

    @Autowired
    private ProductSkuChangePriceAreaInfoMapper productSkuChangePriceAreaInfoMapper;

    @Autowired
    private ProductSkuPriceInfoMapper productSkuPriceInfoMapper;

    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;

    @Autowired
    private ProductSkuPriceAreaInfoMapper productSkuPriceAreaInfoMapper;

    @Autowired
    private ProductSkuPriceInfoLogMapper productSkuPriceInfoLogMapper;

    @Autowired
    private SkuInfoService skuInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(ProductSkuChangePriceReqVO reqVO) throws Exception {
        //验重
        StringBuilder errorMsg = checkDataRepeat(reqVO);
        if (Objects.nonNull(errorMsg)) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, errorMsg.toString()));
        }
        String formNo = "CP" + new IdSequenceUtils().nextId();
        reqVO.setFormNo(formNo);
        //获取编码
        String code = getCode("CP", EncodingRuleType.CHANGE_PRICE_CODE);
        reqVO.setCode(code);
        saveData(reqVO);
        if (CommonConstant.SUBMIT.equals(reqVO.getOperation())) {
            callWorkflow(reqVO);
        }
        return true;
    }
    /**
     * 验证数据重复性
     *
     * @param reqVO
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.changeprice.QueryChangePriceRepeatRespVO>
     * @author NullPointException
     * @date 2019/5/22
     */
    private StringBuilder checkDataRepeat(ProductSkuChangePriceReqVO reqVO) {
        List<ProductSkuChangePriceInfoReqVO> infoLists = reqVO.getInfoLists();
        infoLists.stream().map(ProductSkuChangePriceInfoReqVO::getSkuCode).distinct().collect(Collectors.toList());
        //TODO 暂时不验重。应该不需要验重。
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callWorkflow(ProductSkuChangePriceReqVO reqVO) {
        WorkFlowVO workFlowVO = new WorkFlowVO();
        workFlowVO.setFormUrl(workFlowBaseUrl.variableUrl + "?code=" + reqVO.getCode() + "&" + workFlowBaseUrl.authority);
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        workFlowVO.setFormNo(reqVO.getFormNo());
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + WorkFlow.VARIABLE_PRICE.getNum());
        workFlowVO.setTitle(Optional.ofNullable(reqVO.getUpdateBy()).orElse(reqVO.getCreateBy()) + "创建变价单");
        WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.VARIABLE_PRICE);
        //判断是否成功
        if (workFlowRespVO.getSuccess()) {
            //TODO 这里暂时没有任何操作
        } else {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "创建变价审批流失败！无法提价！"));
        }
    }

    /**
     * 验证数据重复性
     *
     * @param reqVO
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.changeprice.QueryChangePriceRepeatRespVO>
     * @author NullPointException
     * @date 2019/5/22
     */
//    private StringBuffer checkDataRepeat(ProductSkuChangePriceReqVO reqVO) throws Exception {
//        List<ProductSkuChangePriceInfoReqVO> infoLists = reqVO.getInfoLists();
//        List<ProductSkuChangePriceAreaInfoReqVO> areaList = reqVO.getAreaList();
//        List<String> skuCode = Lists.newArrayList();
//        List<String> supplierCode = Lists.newArrayList();
//        List<String> transportCenterCode = Lists.newArrayList();
//        List<String> warehouseBatchNumber = Lists.newArrayList();
//        List<String> warehouseCode = Lists.newArrayList();
//        List<String> code = Lists.newArrayList();
//        List<String> priceItemCode = Lists.newArrayList();
//        infoLists.forEach(o -> {
//            skuCode.add(o.getSkuCode());
//            supplierCode.add(o.getSupplierCode());
//            transportCenterCode.add(o.getTransportCenterCode());
//            warehouseBatchNumber.add(o.getWarehouseBatchNumber());
//            warehouseCode.add(o.getWarehouseCode());
//            priceItemCode.add(o.getPriceItemCode());
//        });
//        if (CollectionUtils.isNotEmpty(areaList)) {
//            for (ProductSkuChangePriceAreaInfoReqVO o : areaList) {
//                code.add(o.getCode());
//            }
//        }
//        QueryChangePriceRepeatVO vo = new QueryChangePriceRepeatVO(reqVO.getChangePriceType(), reqVO.getCompanyCode(), skuCode, supplierCode, warehouseBatchNumber, transportCenterCode, warehouseCode, code, priceItemCode);
//        List<QueryChangePriceRepeatRespVO> repeats = productSkuChangePriceMapper.checkRepeat(vo);
//        //拼装重复信息
//        //TODO 需要重新写验重逻辑
//        if (CollectionUtils.isNotEmpty(repeats)) {
//            StringBuffer sb = new StringBuffer();
//            for (QueryChangePriceRepeatRespVO repeat : repeats) {
//                sb.append(repeat.getChangePriceName()).append("下")
//                        .append(repeat.getSkuCode())
//                        .append(" ")
//                        .append(Optional.ofNullable(repeat.getSupplierName()).orElse(""))
//                        .append(" ")
//                        .append(Optional.ofNullable(repeat.getPriceItemName()).orElse(""))
//                        .append(" ")
//                        .append(Optional.ofNullable(repeat.getTransportCenterName()).orElse(""))
//                        .append("-")
//                        .append(Optional.ofNullable(repeat.getWarehouseName()).orElse(""))
//                        .append("-")
//                        .append(Optional.ofNullable(repeat.getWarehouseBatchNumber()).orElse(""))
//                        .append(" ")
//                        .append(Optional.ofNullable(repeat.getName()).orElse(""))
//                        .append("重复").append(" ");
//            }
//            sb.append("请检查数据后提交");
//            return sb;
//        }
//        return null;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveData(ProductSkuChangePriceReqVO reqVO) throws Exception {
        //主表数据
        ProductSkuChangePrice copy = BeanCopyUtils.copy(reqVO, ProductSkuChangePrice.class);
        copy.setOriginal(0);
        if (CommonConstant.ADD.equals(reqVO.getOperation())) {
            copy.setApplyStatus(CommonConstant.PENDING_SUBMISSION);
        } else {
            copy.setApplyStatus(CommonConstant.UNDER_REVIEW);
        }
        //保存
        int insert = productSkuChangePriceMapper.insert(copy);
        if (insert < 1) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "变价主表数据插入异常！"));
        }
        //保存附表数据
        saveAttachData(reqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAttachData(ProductSkuChangePriceReqVO reqVO) throws Exception {
        //附表
        List<ProductSkuChangePriceInfo> infoList = BeanCopyUtils.copyList(reqVO.getInfoLists(), ProductSkuChangePriceInfo.class);
        infoList.forEach(
                o -> {
                    o.setCompanyCode(reqVO.getCompanyCode());
                    o.setCompanyName(reqVO.getCompanyName());
                    o.setCode(reqVO.getCode());
                }
        );
        int i = productSkuChangePriceInfoMapper.insertBatch(infoList);
        if (i != infoList.size()) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "变价附表数据插入异常！"));
        }
        //图片数据
        if (CollectionUtils.isNotEmpty(reqVO.getPicUrlList())) {
            List<ProductSkuChangePriceImage> images = BeanCopyUtils.copyList(reqVO.getPicUrlList(), ProductSkuChangePriceImage.class);
            images.forEach(o -> o.setCode(reqVO.getCode()));
            int num = productSkuChangePriceImageMapper.insertBatch(images);
            if (num != images.size()) {
                throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "图片数据插入异常！"));
            }
        }
        //区域数据
        if (CollectionUtils.isNotEmpty(reqVO.getAreaList())) {
            List<ProductSkuChangePriceAreaInfo> area = BeanCopyUtils.copyList(reqVO.getAreaList(), ProductSkuChangePriceAreaInfo.class);
            area.forEach(
                    o -> {
                        o.setMainCode(reqVO.getCode());
                        o.setCompanyCode(reqVO.getCompanyCode());
                        o.setCompanyName(reqVO.getCompanyName());
                    }
            );
            int num = productSkuChangePriceAreaInfoMapper.insertBatch(area);
            if (num != area.size()) {
                throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "区域数据插入异常！"));
            }
        }
    }

    @Override
    public ProductSkuChangePriceRespVO view(String code) {
        return productSkuChangePriceMapper.selectInfoByCode(code);
    }

    @Override
    public ProductSkuChangePriceRespVO editView(String code) {
        ProductSkuChangePriceRespVO respVO = this.view(code);
        QuerySkuInfoReqVO vo =  new QuerySkuInfoReqVO();
        vo.setCompanyCode(respVO.getCompanyCode());
        vo.setPurchaseGroupCode(respVO.getPurchaseGroupCode());
        vo.setChangePriceType(respVO.getChangePriceType());
        vo.setSkuCodes(respVO.getInfoList().stream().map(ProductSkuChangePriceInfoRespVO::getSkuCode).distinct().collect(Collectors.toList()));
        Map<String, QuerySkuInfoRespVO> map = skuInfoService.getSkuListByQueryNoPage(vo).stream().collect(Collectors.toMap(QuerySkuInfoRespVO::getSkuCode, Function.identity(), (k1, k2) -> k2));
        for (ProductSkuChangePriceInfoRespVO infoRespVO : respVO.getInfoList()) {
            QuerySkuInfoRespVO skuInfoRespVO = map.get(infoRespVO.getSkuCode());
            if(Objects.isNull(skuInfoRespVO)){
                throw  new BizException(ResultCode.DATA_ERROR);
            }
            infoRespVO.setPriceChannelList(skuInfoRespVO.getPriceChannelList());
            infoRespVO.setSupplierInfoVOS(skuInfoRespVO.getSupplierInfoVOS());
        }
        return respVO;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(ProductSkuChangePriceReqVO reqVO) throws Exception {
        ProductSkuChangePriceRespVO view = view(reqVO.getCode());
        reqVO.setFormNo(view.getFormNo());
        if (Objects.isNull(view)) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, "数据异常！保存失败！"));
        }
        //删除数据
        deleteAttachDataByCode(view);
        //验重
        StringBuilder errorMsg = checkDataRepeat(reqVO);
        if (Objects.nonNull(errorMsg)) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, errorMsg.toString()));
        }
        //保存
        updateData(reqVO);
        //提交
        if (reqVO.getOperation().equals(CommonConstant.SUBMIT)) {
            callWorkflow(reqVO);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateData(ProductSkuChangePriceReqVO reqVO) throws Exception {
        ProductSkuChangePrice copy = BeanCopyUtils.copy(reqVO, ProductSkuChangePrice.class);
        if (reqVO.getOperation().equals(CommonConstant.ADD)) {
            copy.setApplyStatus(CommonConstant.PENDING_SUBMISSION);
        } else {
            copy.setApplyStatus(CommonConstant.UNDER_REVIEW);
        }
        //更新数据
        int update = productSkuChangePriceMapper.updateByCodeSelective(copy);
        if (update < 1) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "变价主表数据更新失败！"));
        }
        //保存附表数据
        saveAttachData(reqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAttachDataByCode(ProductSkuChangePriceRespVO view) {
        int i = productSkuChangePriceInfoMapper.deleteByCode(view.getCode());
        int j = productSkuChangePriceImageMapper.deleteByCode(view.getCode());
        int k = productSkuChangePriceAreaInfoMapper.deleteByCode(view.getCode());
        if (i != view.getInfoList().size() || j != view.getImages().size() || k != view.getAreaList().size()) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "数据异常！保存失败！"));
        }
    }

    @Override
    public BasePage<QueryProductSkuChangePriceRespVO> list(QueryProductSkuChangePriceReqVO reqVO) {
        PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
        List<QueryProductSkuChangePriceRespVO> list = productSkuChangePriceInfoMapper.selectListByQueryVO(reqVO);
        return PageUtil.getPageList(reqVO.getPageNo(),list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String workFlowCallback(WorkFlowCallbackVO vo){
        WorkFlowCallbackVO newVO = updateSupStatus(vo);
        //审批中，直接返回成功
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL.getNumber())) {
            return WorkFlowReturn.SUCCESS;
        }
        //首先通过formNO查找数据
        ProductSkuChangePriceDTO dto = productSkuChangePriceMapper.selectByFormNo(newVO.getFormNo());
        if (Objects.isNull(dto)) {
            log.error("通过formNo查询数据异常,传入数据:{}", JSON.toJSONString(vo));
            throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "通过编码查询数据异常！"));
        }
        //判断查出来的是否是在审批中的数据
        if (!CommonConstant.UNDER_REVIEW.equals(dto.getApplyStatus())) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "数据异常，不是在审批中的数据！"));
        }
        //审批驳回
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL_FAILED.getNumber())) {
            rejection(newVO, dto);
            return WorkFlowReturn.SUCCESS;
        }
        //撤销
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.REVOKED.getNumber())) {
            cancel(newVO, dto);
            return WorkFlowReturn.SUCCESS;
        }
        //审批通过
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL_SUCCESS.getNumber())) {
            //保存正式数据数据
            try {
                saveOfficial(newVO, dto);
            } catch (Exception e) {
                log.error(e.getMessage());
               return WorkFlowReturn.FALSE;
            }
            return WorkFlowReturn.SUCCESS;
        }
        return WorkFlowReturn.FALSE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOfficial(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) throws Exception {
        //根据价格作不同的处理
        //判断类型
        switch (dto.getChangePriceType()) {
            case CommonConstant.PURCHASE_CHANGE_PRICE:
                savePurchaseChangePrice(newVO, dto);
                break;
            case CommonConstant.SALE_CHANGE_PRICE:
                saveSaleChangePrice(newVO, dto);
                break;
            case CommonConstant.TEMPORARY_CHANGE_PRICE:
                saveTemporaryChangePrice(newVO, dto);
                break;
            case CommonConstant.SALE_AREA_CHANGE_PRICE:
                saveSaleAreaChangePrice(newVO, dto);
                break;
            case CommonConstant.TEMPORARY_AREA_CHANGE_PRICE:
                saveTemporaryAreaChangePrice(newVO, dto);
                break;
            default:
                throw new BizException(ResultCode.NOT_HAVE_PARAM);
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTemporaryAreaChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) throws Exception {
        List<ProductSkuChangePriceInfo> infos = dto.getInfos();
        List<ProductSkuPriceInfo> list = Lists.newArrayList();
        List<ProductSkuPriceAreaInfo> areaInfos = Lists.newArrayList();
        List<ProductSkuPriceInfoLog> logList = Lists.newArrayList();
        for (ProductSkuChangePriceInfo info : infos) {
            ProductSkuPriceInfo priceInfo = BeanCopyUtils.copy(info, ProductSkuPriceInfo.class);
            priceInfo.setCreateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
            priceInfo.setCreateTime(new Date());
            priceInfo.setPurchaseGroupName(dto.getPurchaseGroupName());
            priceInfo.setPurchaseGroupCode(dto.getPurchaseGroupCode());
            priceInfo.setApplyCode(dto.getCode());
            priceInfo.setCode("TPA" + new IdSequenceUtils().nextId());
            priceInfo.setPriceTax(info.getTemporaryPrice());
            priceInfo.setTax(0L); //TODO 需要从商品上取
            priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(info.getNewPrice(), 0L));
            priceInfo.setExtField5(1);
            info.setOfficialCode(priceInfo.getCode());
            List<ProductSkuPriceAreaInfo> areaInfo = BeanCopyUtils.copyList(dto.getAreaInfos(), ProductSkuPriceAreaInfo.class);
            ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),null,null,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
            List<String> area = Lists.newArrayList();
            areaInfo.forEach(o->{
                o.setCode(priceInfo.getCode());
                area.add(o.getName());
            });
            log.setAreaInfo(Joiner.on(",").join(area));
            areaInfos.addAll(areaInfo);
            if (info.getEffectiveTimeStart().after(new Date())) {
                //未生效的
                //这里在日志表中插入一条未生效的数据
                log.setStatus(0);
            } else {
                //生效的
                //TODO  插入生效的日志
                priceInfo.setBeSynchronous(1);
            }
            logList.add(log);
            list.add(priceInfo);
            info.setBeSynchronize(1);
        }
        saveData(list,infos,Lists.newArrayList(),areaInfos,logList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSaleAreaChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) throws Exception {
        List<ProductSkuChangePriceInfo> infos = dto.getInfos();
        List<ProductSkuPriceInfo> list = Lists.newArrayList();
        List<ProductSkuPriceAreaInfo> areaInfos = Lists.newArrayList();
        List<ProductSkuPriceInfoLog> logList = Lists.newArrayList();
        for (ProductSkuChangePriceInfo info : infos) {
            ProductSkuPriceInfo priceInfo = BeanCopyUtils.copy(info, ProductSkuPriceInfo.class);
            priceInfo.setPurchaseGroupName(dto.getPurchaseGroupName());
            priceInfo.setPurchaseGroupCode(dto.getPurchaseGroupCode());
            priceInfo.setApplyCode(dto.getCode());
            priceInfo.setCode("SPA" + new IdSequenceUtils().nextId());
            priceInfo.setCreateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
            priceInfo.setCreateTime(new Date());
            priceInfo.setPriceTax(info.getNewPrice());
            priceInfo.setTax(0L); //TODO 需要从商品上取
            priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(info.getNewPrice(), 0L));
            priceInfo.setExtField5(1);
            info.setOfficialCode(priceInfo.getCode());
            List<ProductSkuPriceAreaInfo> areaInfo = BeanCopyUtils.copyList(dto.getAreaInfos(), ProductSkuPriceAreaInfo.class);
            ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),null,null,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
            List<String> area = Lists.newArrayList();
            areaInfo.forEach(o->{
                o.setCode(priceInfo.getCode());
                area.add(o.getName());
            });
            log.setAreaInfo(Joiner.on(",").join(area));
            areaInfos.addAll(areaInfo);
            if (info.getEffectiveTimeStart().after(new Date())) {
                //未生效的
                //这里在日志表中插入一条未生效的数据
                log.setStatus(0);
            } else {
                //生效的
                //TODO  插入生效的日志
                priceInfo.setBeSynchronous(1);
            }
            logList.add(log);
            list.add(priceInfo);
            info.setBeSynchronize(1);
        }
        saveData(list,infos,Lists.newArrayList(),areaInfos,logList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTemporaryChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) throws Exception {
        List<ProductSkuChangePriceInfo> infos = dto.getInfos();
        List<ProductSkuPriceInfo> list = Lists.newArrayList();
        List<ProductSkuPriceInfoLog> logList = Lists.newArrayList();
        for (ProductSkuChangePriceInfo info : infos) {
            ProductSkuPriceInfo priceInfo = BeanCopyUtils.copy(info, ProductSkuPriceInfo.class);
            priceInfo.setCreateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
            priceInfo.setCreateTime(new Date());
            priceInfo.setPurchaseGroupName(dto.getPurchaseGroupName());
            priceInfo.setApplyCode(dto.getCode());
            priceInfo.setPurchaseGroupCode(dto.getPurchaseGroupCode());
            priceInfo.setCode("TP" + new IdSequenceUtils().nextId());
            priceInfo.setPriceTax(info.getTemporaryPrice());
            priceInfo.setTax(0L); //TODO 需要从商品上取
            priceInfo.setExtField5(0);
            info.setOfficialCode(priceInfo.getCode());
            priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(info.getNewPrice(), 0L));
            ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),null,null,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
            if (info.getEffectiveTimeStart().after(new Date())) {
                //未生效的
                //TODO 这里在日志表中插入一条未生效的数据
                log.setStatus(0);
            } else {
                //生效的
                priceInfo.setBeSynchronous(1);
            }
            list.add(priceInfo);
            logList.add(log);
            info.setBeSynchronize(1);
        }
        saveData(list,infos,Lists.newArrayList(),Lists.newArrayList(),logList);
    }

    @Override
    public void saveSaleChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) throws Exception {
        //处理数据
        QueryProductSkuPriceInfo queryVO = dealPurchaseChangePriceData(dto);
        //验重
        List<ProductSkuPriceInfo> list = productSkuPriceInfoMapper.checkRepeat(queryVO);
        //skuCode+价格项目+仓库批次号
        Map<String, ProductSkuPriceInfo> infoMap = list.stream().collect(Collectors.toMap(
                o -> o.getSkuCode() + o.getPriceItemCode()+o.getTransportCenterCode()+o.getWarehouseCode()+o.getWarehouseBatchNumber()+o.getWarehouseBatchNumber(),
                Function.identity()));
        //数据对比，分类
        List<ProductSkuChangePriceInfo> noRepeat = dto.getInfos();
        List<ProductSkuChangePriceInfo> repeat = dto.getInfos().stream().filter(o -> Objects.nonNull(infoMap.get(o.getSkuCode() + o.getPriceItemCode()+o.getTransportCenterCode()+o.getWarehouseCode()+o.getWarehouseBatchNumber()+o.getWarehouseBatchNumber()))).collect(Collectors.toList());
        List<ProductSkuPriceInfo> priceInsertInfos = Lists.newArrayList();
        List<ProductSkuChangePriceInfo> infoList = Lists.newArrayList();
        List<ProductSkuPriceInfoLog> logList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(noRepeat)) {
            //处理插入数据
            for (ProductSkuChangePriceInfo info : noRepeat) {
                ProductSkuPriceInfo priceInfo = BeanCopyUtils.copy(info, ProductSkuPriceInfo.class);
                priceInfo.setPurchaseGroupCode(dto.getPurchaseGroupCode());
                priceInfo.setPurchaseGroupName(dto.getPurchaseGroupName());
                priceInfo.setApplyCode(dto.getCode());
                priceInfo.setCode("SP" + new IdSequenceUtils().nextId());
                priceInfo.setCreateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
                priceInfo.setCreateTime(new Date());
                priceInfo.setPriceTax(info.getNewPrice());
                priceInfo.setTax(0L); //TODO 需要从商品上取
                priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(info.getNewPrice(), 0L));
                priceInfo.setExtField5(0);
                priceInsertInfos.add(priceInfo);
                info.setBeSynchronize(1);
                info.setOfficialCode(priceInfo.getCode());
                ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),null,null,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
                //判断生效日期
                if (info.getEffectiveTimeStart().after(new Date())) {
                    //未生效的
                    log.setStatus(0);
                } else {
                    //生效的
                    priceInfo.setBeSynchronous(1);
                }
                logList.add(log);
                infoList.add(info);
            }
        }
        List<ProductSkuPriceInfo> priceUpdateInfos = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(repeat)) {
            //处理更新数据
            for (ProductSkuChangePriceInfo productSkuChangePriceInfo : repeat) {
                ProductSkuPriceInfo priceInfo = infoMap.get(productSkuChangePriceInfo.getSkuCode() + productSkuChangePriceInfo.getSupplierCode());
                ProductSkuPriceInfo copy = BeanCopyUtils.copy(priceInfo, ProductSkuPriceInfo.class);
                priceInfo.setApplyCode(productSkuChangePriceInfo.getCode());
                priceInfo.setPriceTax(productSkuChangePriceInfo.getNewPrice());
                priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(productSkuChangePriceInfo.getNewPrice(), 0L));
                priceInfo.setUpdateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
                priceInfo.setUpdateTime(new Date());
                priceInfo.setTax(0L); //TODO 需要从商品上取
                ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),null,null,priceInfo.getCreateBy(),new Date());
                //判断生效日期
                if (productSkuChangePriceInfo.getEffectiveTimeStart().after(new Date())) {
                    //未生效的
                    log.setStatus(0);
                } else {
                    //生效的
                    productSkuChangePriceInfo.setBeSynchronize(1);
                    ProductSkuPriceInfoLog log2 = new ProductSkuPriceInfoLog(copy.getCode(),copy.getPriceTax(),copy.getPriceNoTax(),copy.getTax(),copy.getEffectiveTimeStart(),new Date(),2,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
                    logList.add(log2);
                    priceUpdateInfos.add(priceInfo);
                }
                productSkuChangePriceInfo.setOfficialCode(priceInfo.getCode());
                logList.add(log);
                infoList.add(productSkuChangePriceInfo);
            }
        }
        //保存数据
        saveData(priceInsertInfos, infoList, priceUpdateInfos,Lists.newArrayList(),logList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePurchaseChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) throws Exception {
        //处理数据
        QueryProductSkuPriceInfo queryVO = dealPurchaseChangePriceData(dto);
        //验重
        List<ProductSkuPriceInfo> list = productSkuPriceInfoMapper.checkRepeat(queryVO);
        Map<String, ProductSkuPriceInfo> infoMap = list.stream().collect(Collectors.toMap(o -> o.getSkuCode() + o.getSupplierCode(), Function.identity()));
        //数据对比，分类
        List<ProductSkuChangePriceInfo> noRepeat = dto.getInfos();
        List<ProductSkuChangePriceInfo> repeat = dto.getInfos().stream().filter(o -> Objects.nonNull(infoMap.get(o.getSkuCode() + o.getSupplierCode()))).collect(Collectors.toList());
        noRepeat.removeAll(repeat);
        List<ProductSkuPriceInfo> priceInsertInfos = Lists.newArrayList();
        List<ProductSkuChangePriceInfo> infoList = Lists.newArrayList();
        List<ProductSkuPriceInfoLog> logList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(noRepeat)) {
            //处理插入数据
            for (ProductSkuChangePriceInfo info : noRepeat) {
                ProductSkuPriceInfo priceInfo = BeanCopyUtils.copy(info, ProductSkuPriceInfo.class);
                priceInfo.setApplyCode(dto.getCode());
                priceInfo.setPurchaseGroupCode(dto.getPurchaseGroupCode());
                priceInfo.setPurchaseGroupName(dto.getPurchaseGroupName());
                priceInfo.setCreateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
                priceInfo.setCreateTime(new Date());
                priceInfo.setCode("PP" + new IdSequenceUtils().nextId());
                priceInfo.setPriceTax(info.getPurchasePriceNew());
                priceInfo.setExtField5(0);
                priceInfo.setTax(0L); //TODO 需要从商品上取
                priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(info.getPurchasePriceNew(), 0L));
                info.setBeSynchronize(1);
                info.setOfficialCode(priceInfo.getCode());
                priceInsertInfos.add(priceInfo);
                ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),null,null,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
                //判断生效日期
                if (info.getEffectiveTimeStart().after(new Date())) {
                    //未生效的
                    log.setStatus(0);
                } else {
                    //生效的
                    priceInfo.setBeSynchronous(1);
                }
                logList.add(log);
                infoList.add(info);
            }
        }
        List<ProductSkuPriceInfo> priceUpdateInfos = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(repeat)) {
            //处理更新数据
            for (ProductSkuChangePriceInfo productSkuChangePriceInfo : repeat) {
                ProductSkuPriceInfo priceInfo = infoMap.get(productSkuChangePriceInfo.getSkuCode() + productSkuChangePriceInfo.getSupplierCode());
                ProductSkuPriceInfo copy = BeanCopyUtils.copy(priceInfo, ProductSkuPriceInfo.class);
                priceInfo.setApplyCode(productSkuChangePriceInfo.getCode());
                priceInfo.setPriceTax(productSkuChangePriceInfo.getPurchasePriceNew());
                priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(productSkuChangePriceInfo.getPurchasePriceNew(), 0L));
                priceInfo.setTax(0L); //TODO 需要从商品上取
                priceInfo.setUpdateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
                priceInfo.setUpdateTime(new Date());
                productSkuChangePriceInfo.setOfficialCode(priceInfo.getCode());
                ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),productSkuChangePriceInfo.getEffectiveTimeStart(),null,null,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
                //判断生效日期
                if (productSkuChangePriceInfo.getEffectiveTimeStart().after(new Date())) {
                    //未生效的
                    //这里在日志表中插入一条未生效的数据
                    log.setStatus(0);
                } else {
                    //生效的
                    productSkuChangePriceInfo.setBeSynchronize(1);
                    //插入失效日志，再更新数据, 插入生效的日志
                    ProductSkuPriceInfoLog log2 = new ProductSkuPriceInfoLog(copy.getCode(),copy.getPriceTax(),copy.getPriceNoTax(),copy.getTax(),copy.getEffectiveTimeStart(),new Date(),2,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
                    logList.add(log2);
                    infoList.add(productSkuChangePriceInfo);
                    priceUpdateInfos.add(priceInfo);
                }
                logList.add(log);
            }
        }
        //保存数据
        saveData(priceInsertInfos, infoList, priceUpdateInfos,Lists.newArrayList(),logList);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveData(List<ProductSkuPriceInfo> priceInsertInfos, List<ProductSkuChangePriceInfo> infoList, List<ProductSkuPriceInfo> priceUpdateInfos, List<ProductSkuPriceAreaInfo> areaInfos,List<ProductSkuPriceInfoLog> logList) {
        //保存
        if(CollectionUtils.isNotEmpty(priceInsertInfos)) {
            int i = productSkuPriceInfoMapper.insertBatch(priceInsertInfos);
            if (i != priceInsertInfos.size()) {
                log.error("ProductSkuChangePriceServiceImpl--saveData--需要插入的数据条数:[{}], 实际插入数据的条数[{}]", priceInsertInfos.size(), i);
                throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "插入价格表数据异常！"));
            }
        }
        if(CollectionUtils.isNotEmpty(priceUpdateInfos)) {
            int j = productSkuPriceInfoMapper.updateBatch(priceUpdateInfos);
            if (j != priceUpdateInfos.size()) {
                log.error("ProductSkuChangePriceServiceImpl--saveData--需要更新的数据条数:[{}], 实际更新数据的条数[{}]", priceUpdateInfos.size(), j);
                throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "更新价格表数据异常！"));
            }
        }
        if(CollectionUtils.isNotEmpty(logList)) {
            int n = productSkuPriceInfoLogMapper.insertBatch(logList);
            if (n != logList.size()) {
                log.error("ProductSkuChangePriceServiceImpl--saveData--需要插入的数据条数:[{}], 实际插入数据的条数[{}]", logList.size(), n);
                throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "插入日志表数据异常！"));
            }
        }
        if(CollectionUtils.isNotEmpty(areaInfos)) {
            int m = productSkuPriceAreaInfoMapper.insertBatch(areaInfos);
            if (m != areaInfos.size()) {
                log.error("ProductSkuChangePriceServiceImpl--saveData--需要插入的数据条数:[{}], 实际插入数据的条数[{}]", areaInfos.size(), m);
                throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "插入附表数据异常！"));
            }
        }
        if(CollectionUtils.isNotEmpty(infoList)) {
            //更新申请表
            int k = productSkuChangePriceInfoMapper.updateBatch(infoList);
            if (k != infoList.size()) {
                log.error("ProductSkuChangePriceServiceImpl--saveData--需要更新的数据条数:[{}], 实际更新数据的条数[{}]", infoList.size(), k);
                throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "更新变价申请表数据异常！"));
            }
        }
    }

    /**
     * 拼装数据
     *
     * @param dto
     * @return void
     * @author NullPointException
     * @date 2019/5/24
     */
    private QueryProductSkuPriceInfo dealPurchaseChangePriceData(ProductSkuChangePriceDTO dto) {
        List<ProductSkuChangePriceInfo> infos = dto.getInfos();
        if (CollectionUtils.isEmpty(infos)) {
            throw new BizException(ResultCode.NOT_HAVE_PARAM);
        }
        List<String> skuCode = Lists.newArrayList();
        List<String> supplierCode = Lists.newArrayList();
        List<String> priceItemCode = Lists.newArrayList();
        List<String> warehouseBatchNumber = Lists.newArrayList();
        List<String> transportCenterCode = Lists.newArrayList();
        List<String> warehouseCode = Lists.newArrayList();
        infos.forEach(
                o -> {
                    skuCode.add(o.getSkuCode());
//                    priceItemCode.add(o.getPriceItemCode());
//                    supplierCode.add(o.getSupplierCode());
//                    transportCenterCode.add(o.getTransportCenterCode());
//                    warehouseCode.add(o.getWarehouseCode());
//                    warehouseBatchNumber.add(o.getWarehouseBatchNumber());
                }
        );
        return new QueryProductSkuPriceInfo(dto.getCompanyCode(), skuCode, priceItemCode, supplierCode, transportCenterCode, warehouseCode, warehouseBatchNumber);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) {
        ProductSkuChangePrice p = new ProductSkuChangePrice();
        p.setApplyStatus(CommonConstant.CANCEL);
        p.setAuditorBy(newVO.getApprovalUserName());
        p.setAuditorTime(new Date());
        p.setId(dto.getId());
        int update = productSkuChangePriceMapper.updateByPrimaryKeySelective(p);
        if (update < 1) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "状态变更异常！"));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejection(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) {
        ProductSkuChangePrice p = new ProductSkuChangePrice();
        p.setApplyStatus(CommonConstant.REVIEWR_EJECTION);
        p.setAuditorBy(newVO.getApprovalUserName());
        p.setId(dto.getId());
        p.setAuditorTime(new Date());
        int update = productSkuChangePriceMapper.updateByPrimaryKeySelective(p);
        if (update < 1) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "状态变更异常！"));
        }
    }

    @Override
    public Boolean cancelData(String code) {
        WorkFlowVO workFlowVO = new WorkFlowVO();
        ProductSkuChangePriceRespVO view = view(code);
        if(Objects.nonNull(view)&& CommonConstant.UNDER_REVIEW.equals(view.getApplyStatus())){
            workFlowVO.setFormNo(view.getFormNo());
            WorkFlowRespVO workFlowRespVO = cancelWorkFlow(workFlowVO);
            log.info(workFlowRespVO.getMsg());
            return workFlowRespVO.getSuccess();
        }else{
            throw new BizException(ResultCode.DATA_ERROR);
        }
    }
}
