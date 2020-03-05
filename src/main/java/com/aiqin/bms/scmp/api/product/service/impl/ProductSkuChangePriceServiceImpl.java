package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.HandleTypeCoce;
import com.aiqin.bms.scmp.api.common.ObjectTypeCode;
import com.aiqin.bms.scmp.api.common.WorkFlowReturn;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.product.domain.dto.changeprice.ProductSkuChangePriceDTO;
import com.aiqin.bms.scmp.api.product.domain.dto.changeprice.SaleCountDTO;
import com.aiqin.bms.scmp.api.product.domain.excel.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.*;
import com.aiqin.bms.scmp.api.product.domain.response.ExportChangePriceVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.*;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.ProductSkuChangePriceService;
import com.aiqin.bms.scmp.api.product.service.SkuInfoService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionaryInfo;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogBean;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.bms.scmp.api.util.excel.exception.ExcelException;
import com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil;
import com.aiqin.bms.scmp.api.workflow.annotation.WorkFlowAnnotation;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.helper.WorkFlowHelper;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
    @Autowired
    private StockService stockService;
    @Autowired
    private SupplierCommonService supplierCommonService;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private SupplierDictionaryInfoDao supplierDictionaryInfoDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(ProductSkuChangePriceReqVO reqVO) throws Exception {
        JSON.toJSONString(reqVO);
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        reqVO.setCompanyCode(currentAuthToken.getCompanyCode());
        reqVO.setCompanyName(currentAuthToken.getCompanyName());
        reqVO.setCreateBy(currentAuthToken.getPersonName());
        reqVO.setCreateTime(new Date());
        //校验参数
        validateParam(reqVO);
        //验重
//        StringBuilder errorMsg = checkDataRepeat(reqVO);
//        if (Objects.nonNull(errorMsg)) {
//            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, errorMsg.toString()));
//        }
        String formNo = "CP" + IdSequenceUtils.getInstance().nextId();
        reqVO.setFormNo(formNo);
        //获取编码
        synchronized (ProductSkuChangePriceServiceImpl.class){
            String code = getCode("CP", EncodingRuleType.CHANGE_PRICE_CODE);
            reqVO.setCode(code);
        }
        //判断是否含有区域0否1是
        reqVO.setBeContainArea(CollectionUtils.isEmpty(reqVO.getAreaList())?0:1);
        //计算
        PriceMeasurementRespVO respVO = priceMeasurement(fullPriceMeasurementData(reqVO),DateUtils.getLastMonthString(new Date()));
        reqVO.setIncreaseCount(respVO.getIncreaseCount());
        reqVO.setIncreaseGrossProfit(respVO.getIncreaseGrossProfit2());
        reqVO.setDecreaseCount(respVO.getDecreaseCount());
        reqVO.setDecreaseGrossProfit(respVO.getDecreaseGrossProfit2());
        saveData(reqVO);
        //保存日志
        supplierCommonService.getInstance(reqVO.getCode(), HandleTypeCoce.ADD.getStatus(), ObjectTypeCode.CHANGE_PRICE.getStatus(), HandleTypeCoce.ADD_CHANGEPRICE.getName(), null, HandleTypeCoce.ADD.getName(), getUser().getPersonName());
        if (CommonConstant.SUBMIT.equals(reqVO.getOperation())) {
          //审批
            callWorkflow(reqVO);
        }
        return true;
    }

    private List<PriceMeasurementReqVO> fullPriceMeasurementData(ProductSkuChangePriceReqVO reqVO) {
        List<PriceMeasurementReqVO> tempList = Lists.newArrayList();
        List<ProductSkuChangePriceInfoReqVO> infoLists = reqVO.getInfoLists();
        for (ProductSkuChangePriceInfoReqVO vo : infoLists) {
            PriceMeasurementReqVO temp = new PriceMeasurementReqVO();
            if (CommonConstant.PURCHASE_CHANGE_PRICE.equals(reqVO.getChangePriceType())) {
                temp.setPrice(vo.getTaxCost());
            }else if (CommonConstant.SALE_CHANGE_PRICE.equals(reqVO.getChangePriceType())){
                temp.setPrice(vo.getNewPrice());
            }else if(CommonConstant.TEMPORARY_CHANGE_PRICE.equals(reqVO.getChangePriceType())){
                temp.setPrice(vo.getTemporaryPrice());
            }else {
                throw new BizException(ResultCode.DATA_ERROR);
            }
            temp.setNewGrossProfitMargin(vo.getNewGrossProfitMargin());
            temp.setOldGrossProfitMargin(vo.getOldGrossProfitMargin());
            temp.setSkuCode(vo.getSkuCode());
            tempList.add(temp);
        }
        return tempList;
    }

    /**
     * 校验参数是否重复
     * @author NullPointException
     * @date 2019/6/6
     * @param reqVO
     * @return void
     */
    private void validateParam(ProductSkuChangePriceReqVO reqVO) {
        Set checkRepeat = Sets.newLinkedHashSet();
        if(CommonConstant.PURCHASE_CHANGE_PRICE.equals(reqVO.getChangePriceType())){
            //采购变价 Sku+供应商
            for (ProductSkuChangePriceInfoReqVO vo : reqVO.getInfoLists()) {
                if(!checkRepeat.add(vo.getSkuCode()+vo.getSupplierCode())){
                    throw new BizException(ResultCode.DATA_REPEAT);
                }
            }
        }else {
            //sku+价格项目+仓库
            for (ProductSkuChangePriceInfoReqVO vo : reqVO.getInfoLists()) {
                if(!checkRepeat.add(vo.getSkuCode()+vo.getPriceItemCode()+vo.getTransportCenterCode()+vo.getWarehouseCode()+vo.getWarehouseBatchNumber())){
                    throw new BizException(ResultCode.DATA_REPEAT);
                }
            }
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
        workFlowVO.setPositionCode(reqVO.getPositionCode());
        workFlowVO.setFormUrl(workFlowBaseUrl.variableUrl + "?code=" + reqVO.getCode() + "&" + workFlowBaseUrl.authority);
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        workFlowVO.setFormNo(reqVO.getFormNo());
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + WorkFlow.VARIABLE_PRICE.getNum());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("auditPersonId",reqVO.getDirectSupervisorCode());
        workFlowVO.setVariables(jsonObject.toString());
        workFlowVO.setTitle(Optional.ofNullable(reqVO.getUpdateBy()).orElse(reqVO.getCreateBy()) + "创建变价单");
        WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.VARIABLE_PRICE);
        //判断是否成功
        if (workFlowRespVO.getSuccess()) {
            //TODO 这里暂时没有任何操作
            supplierCommonService.getInstance(reqVO.getCode(), HandleTypeCoce.APPROVAL.getStatus(), ObjectTypeCode.CHANGE_PRICE.getStatus(), HandleTypeCoce.UNDER_CHANGEPRICE.getName(), null, HandleTypeCoce.APPROVAL.getName(), getUser().getPersonName());
        } else {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 97, workFlowRespVO.getMsg()));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveData(ProductSkuChangePriceReqVO reqVO) throws Exception {
        //主表数据
        ProductSkuChangePrice copy = BeanCopyUtils.copy(reqVO, ProductSkuChangePrice.class);
        copy.setApplyStatus(CommonConstant.UNDER_REVIEW);
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
        ProductSkuChangePriceRespVO respVO = productSkuChangePriceMapper.selectInfoByCode(code);
        if (Objects.isNull(respVO)) {
            respVO = productSkuChangePriceMapper.selectInfoByFormNo1(code);
            if(Objects.isNull(respVO)){
                throw new BizException(ResultCode.CAN_NOT_FIND_CHANGE_PRICE_INFO);
            }
        }
        //查询日志信息
        OperationLogBean operationLogBean = new OperationLogBean(code, null, ObjectTypeCode.CHANGE_PRICE.getStatus(), null, null);
        List<LogData> log = operationLogService.selectListByVO(operationLogBean);
        respVO.setLogData(log);
        return respVO;
    }

    @Override
    public ProductSkuChangePriceRespVO editView(String code) {
        ProductSkuChangePriceRespVO respVO = this.view(code);
        if (Objects.isNull(respVO)) {
            throw new BizException(ResultCode.CAN_NOT_FIND_CHANGE_PRICE_INFO);
        }
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
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        reqVO.setUpdateBy(currentAuthToken.getPersonName());
        reqVO.setUpdateTime(new Date());
        ProductSkuChangePriceRespVO view = view(reqVO.getCode());
        reqVO.setFormNo(view.getFormNo());
//        if (Objects.isNull(view)) {
//            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, "数据异常！保存失败！"));
//        }
        //删除数据
        deleteAttachDataByCode(view);
        //验重
//        StringBuilder errorMsg = checkDataRepeat(reqVO);
//        if (Objects.nonNull(errorMsg)) {
//            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, errorMsg.toString()));
//        }
        //保存
        updateData(reqVO);
        supplierCommonService.getInstance(reqVO.getCode(), HandleTypeCoce.UPDATE.getStatus(), ObjectTypeCode.CHANGE_PRICE.getStatus(), HandleTypeCoce.UNDER_CHANGEPRICE.getName(), null, HandleTypeCoce.UPDATE.getName(), getUser().getPersonName());
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
        reqVO.setCompanyCode(getUser().getCompanyCode());
        List<Long> ids = productSkuChangePriceInfoMapper.selectListByQueryVOCount(reqVO);
        if(CollectionUtils.isEmpty(ids)){
            return PageUtil.getPageList(reqVO.getPageNo(), Lists.newArrayList());
        }
        List<QueryProductSkuChangePriceRespVO> list = productSkuChangePriceInfoMapper.selectListByQueryVO(PageUtil.myPage(ids, reqVO));
        return PageUtil.getPageList(reqVO.getPageNo(),reqVO.getPageSize(),ids.size(),list);
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
            supplierCommonService.getInstance(dto.getCode(), HandleTypeCoce.APPROVAL_FAILED.getStatus(), ObjectTypeCode.CHANGE_PRICE.getStatus(), HandleTypeCoce.NOPASS_CHANGEPRICE.getName(), null, HandleTypeCoce.APPROVAL_FAILED.getName(), newVO.getApprovalUserName());
            return WorkFlowReturn.SUCCESS;
        }
        //撤销
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.REVOKED.getNumber())) {
            cancel(newVO, dto);
            supplierCommonService.getInstance(dto.getCode(), HandleTypeCoce.REVOKED.getStatus(), ObjectTypeCode.CHANGE_PRICE.getStatus(), HandleTypeCoce.RECOBER_CHANGEPRICE.getName(), null, HandleTypeCoce.REVOKED.getName(), newVO.getApprovalUserName());
            return WorkFlowReturn.SUCCESS;
        }
        //审批通过
        if (Objects.equals(newVO.getApplyStatus(), ApplyStatus.APPROVAL_SUCCESS.getNumber())) {
            //保存正式数据数据
            dto.setApplyStatus(CommonConstant.EXAMINATION_PASSED);
            saveOfficial(newVO, dto);
            supplierCommonService.getInstance(dto.getCode(), HandleTypeCoce.APPROVAL_SUCCESS.getStatus(), ObjectTypeCode.CHANGE_PRICE.getStatus(), HandleTypeCoce.PASS_CHANGEPRICE.getName(), null, HandleTypeCoce.APPROVAL_SUCCESS.getName(), newVO.getApprovalUserName());
            return WorkFlowReturn.SUCCESS;
        }
        return WorkFlowReturn.FALSE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOfficial(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto){
        //根据价格作不同的处理
        //判断类型
        dto.setChangePriceType("2");
        switch (dto.getChangePriceType()) {
//            case CommonConstant.PURCHASE_CHANGE_PRICE:
//                 savePurchaseChangePrice(newVO, dto);
//                break;
            case CommonConstant.SALE_CHANGE_PRICE:
                if(dto.getBeContainArea() == 0) {
                    saveSaleChangePrice(newVO, dto);
                }else {
                    saveSaleAreaChangePrice(newVO, dto);
                }
                break;
//            case CommonConstant.TEMPORARY_CHANGE_PRICE:
//                if(dto.getBeContainArea() == 0) {
//                    saveTemporaryChangePrice(newVO, dto);
//                }else {
//                    saveTemporaryAreaChangePrice(newVO, dto);
//                }
//                break;
//            case CommonConstant.SALE_AREA_CHANGE_PRICE:
//                saveSaleAreaChangePrice(newVO, dto);
//                break;
//            case CommonConstant.TEMPORARY_AREA_CHANGE_PRICE:
//                saveTemporaryAreaChangePrice(newVO, dto);
//                break;
            default:
                throw new BizException(ResultCode.NOT_HAVE_PARAM);
        }
        changeStatus(newVO, dto);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeStatus(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) {
        ProductSkuChangePrice p = new ProductSkuChangePrice();
        p.setApplyStatus(newVO.getApplyStatus().intValue());
        p.setAuditorBy(newVO.getApprovalUserName());
        p.setAuditorTime(new Date());
        p.setCode(dto.getCode());
        int update = productSkuChangePriceMapper.updateByCodeSelective(p);
        if (update < 1) {
            throw new BizException(MessageId.create(Project.PRODUCT_API, 97, "状态变更异常！"));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTemporaryAreaChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) {
        List<ProductSkuChangePriceInfo> infos = dto.getInfos();
        List<ProductSkuPriceInfo> list = Lists.newArrayList();
        List<ProductSkuPriceAreaInfo> areaInfos = Lists.newArrayList();
        List<ProductSkuPriceInfoLog> logList = Lists.newArrayList();
        for (ProductSkuChangePriceInfo info : infos) {
            ProductSkuPriceInfo priceInfo = BeanCopyUtils.copy(info, ProductSkuPriceInfo.class);
            priceInfo.setId(null);
            priceInfo.setCreateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
            priceInfo.setCreateTime(new Date());
            priceInfo.setPurchaseGroupName(dto.getPurchaseGroupName());
            priceInfo.setPurchaseGroupCode(dto.getPurchaseGroupCode());
            priceInfo.setApplyCode(dto.getCode());
            priceInfo.setCode("TPA" + IdSequenceUtils.getInstance().nextId());
            priceInfo.setPriceTax(info.getTemporaryPrice());
            priceInfo.setTax(info.getOutTax());
            priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(Optional.ofNullable(info.getTemporaryPrice()).orElse(BigDecimal.ZERO), info.getOutTax()));
            priceInfo.setBeContainArea(1);
            info.setOfficialCode(priceInfo.getCode());
            List<ProductSkuPriceAreaInfo> areaInfo = BeanCopyUtils.copyList(dto.getAreaInfos(), ProductSkuPriceAreaInfo.class);
            ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),priceInfo.getEffectiveTimeEnd(),1,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
            List<String> area = Lists.newArrayList();
            areaInfo.forEach(o->{
                o.setMainCode(priceInfo.getCode());
                area.add(o.getName());
            });
            log.setAreaInfo(StringUtils.join(area,","));
            areaInfos.addAll(areaInfo);
            if (Optional.ofNullable(info.getEffectiveTimeStart()).orElse(new Date()).after(new Date())) {
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
    public void saveSaleAreaChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto){
        List<ProductSkuChangePriceInfo> infos = dto.getInfos();
        List<ProductSkuPriceInfo> list = Lists.newArrayList();
        List<ProductSkuPriceAreaInfo> areaInfos = Lists.newArrayList();
        List<ProductSkuPriceInfoLog> logList = Lists.newArrayList();
        for (ProductSkuChangePriceInfo info : infos) {
            ProductSkuPriceInfo priceInfo = BeanCopyUtils.copy(info, ProductSkuPriceInfo.class);
            priceInfo.setId(null);
            priceInfo.setPurchaseGroupName(dto.getPurchaseGroupName());
            priceInfo.setPurchaseGroupCode(dto.getPurchaseGroupCode());
            priceInfo.setApplyCode(dto.getCode());
            priceInfo.setCode("SPA" + IdSequenceUtils.getInstance().nextId());
            priceInfo.setCreateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
            priceInfo.setCreateTime(new Date());
            priceInfo.setPriceTax(info.getNewPrice());
            priceInfo.setTax(info.getOutTax());
            priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(Optional.ofNullable(info.getNewPrice()).orElse(BigDecimal.ZERO), info.getOutTax()));
            priceInfo.setBeContainArea(1);
            info.setOfficialCode(priceInfo.getCode());
            List<ProductSkuPriceAreaInfo> areaInfo = BeanCopyUtils.copyList(dto.getAreaInfos(), ProductSkuPriceAreaInfo.class);
            ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),priceInfo.getEffectiveTimeEnd(),1,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
            List<String> area = Lists.newArrayList();
            areaInfo.forEach(o->{
                o.setMainCode(priceInfo.getCode());
                area.add(o.getName());
            });
            log.setAreaInfo(StringUtils.join(area,","));
            areaInfos.addAll(areaInfo);
            if (Optional.ofNullable(info.getEffectiveTimeStart()).orElse(new Date()).after(new Date())) {
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
    public void saveTemporaryChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto){
        List<ProductSkuChangePriceInfo> infos = dto.getInfos();
        List<ProductSkuPriceInfo> list = Lists.newArrayList();
        List<ProductSkuPriceInfoLog> logList = Lists.newArrayList();
        for (ProductSkuChangePriceInfo info : infos) {
            ProductSkuPriceInfo priceInfo = BeanCopyUtils.copy(info, ProductSkuPriceInfo.class);
            priceInfo.setId(null);
            priceInfo.setCreateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
            priceInfo.setCreateTime(new Date());
            priceInfo.setPurchaseGroupName(dto.getPurchaseGroupName());
            priceInfo.setApplyCode(dto.getCode());
            priceInfo.setPurchaseGroupCode(dto.getPurchaseGroupCode());
            priceInfo.setCode("TP" + IdSequenceUtils.getInstance().nextId());
            priceInfo.setPriceTax(info.getTemporaryPrice());
            priceInfo.setTax(info.getOutTax());
            priceInfo.setBeContainArea(0);
            info.setOfficialCode(priceInfo.getCode());
            priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(Optional.ofNullable(info.getTemporaryPrice()).orElse(BigDecimal.ZERO), info.getOutTax()));
            ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),priceInfo.getEffectiveTimeEnd(),1,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
            if (Optional.ofNullable(info.getEffectiveTimeStart()).orElse(new Date()).after(new Date())) {
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
    @Transactional(rollbackFor = Exception.class)
    public void saveSaleChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) {
        //处理数据
        QueryProductSkuPriceInfo queryVO = dealPurchaseChangePriceData(dto,"2");
        //验重
        List<ProductSkuPriceInfo> list = productSkuPriceInfoMapper.checkRepeat(queryVO);
        //skuCode+价格项目+仓库批次号
        Map<String, ProductSkuPriceInfo> infoMap = list.stream().collect(Collectors.toMap(
                o -> o.getSkuCode() + o.getPriceItemCode()+o.getTransportCenterCode()+o.getWarehouseCode()+o.getWarehouseBatchNumber()+o.getWarehouseBatchNumber(),
                Function.identity(),(k1,k2)->k2));
        //数据对比，分类
        List<ProductSkuChangePriceInfo> noRepeat = dto.getInfos();
        List<ProductSkuChangePriceInfo> repeat = dto.getInfos().stream().filter(o -> Objects.nonNull(infoMap.get(o.getSkuCode() + o.getPriceItemCode()+o.getTransportCenterCode()+o.getWarehouseCode()+o.getWarehouseBatchNumber()+o.getWarehouseBatchNumber()))).collect(Collectors.toList());
        noRepeat.removeAll(repeat);
        List<ProductSkuPriceInfo> priceInsertInfos = Lists.newArrayList();
        List<ProductSkuChangePriceInfo> infoList = Lists.newArrayList();
        List<ProductSkuPriceInfoLog> logList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(noRepeat)) {
            //处理插入数据
            for (ProductSkuChangePriceInfo info : noRepeat) {
                ProductSkuPriceInfo priceInfo = BeanCopyUtils.copy(info, ProductSkuPriceInfo.class);
                priceInfo.setId(null);
                priceInfo.setPurchaseGroupCode(dto.getPurchaseGroupCode());
                priceInfo.setPurchaseGroupName(dto.getPurchaseGroupName());
                priceInfo.setApplyCode(dto.getCode());
                priceInfo.setCode("SP" + IdSequenceUtils.getInstance().nextId());
                priceInfo.setCreateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
                priceInfo.setCreateTime(new Date());
                priceInfo.setPriceTax(info.getNewPrice());
                priceInfo.setTax(info.getOutTax());
                priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(Optional.ofNullable(info.getNewPrice()).orElse(BigDecimal.ZERO), info.getOutTax()));
                priceInfo.setBeContainArea(0);
                priceInsertInfos.add(priceInfo);
                info.setBeSynchronize(1);
                info.setOfficialCode(priceInfo.getCode());
                ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),null,1,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
                //判断生效日期
                if (Optional.ofNullable(info.getEffectiveTimeStart()).orElse(new Date()).after(new Date())) {
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
                ProductSkuPriceInfo priceInfo = infoMap.get(productSkuChangePriceInfo.getSkuCode() + productSkuChangePriceInfo.getPriceItemCode()+productSkuChangePriceInfo.getTransportCenterCode()+productSkuChangePriceInfo.getWarehouseCode()+productSkuChangePriceInfo.getWarehouseBatchNumber()+productSkuChangePriceInfo.getWarehouseBatchNumber());
                ProductSkuPriceInfo copy = BeanCopyUtils.copy(priceInfo, ProductSkuPriceInfo.class);
                priceInfo.setApplyCode(productSkuChangePriceInfo.getCode());
                priceInfo.setPriceTax(productSkuChangePriceInfo.getNewPrice());
                priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(Optional.ofNullable(productSkuChangePriceInfo.getNewPrice()).orElse(BigDecimal.ZERO), productSkuChangePriceInfo.getOutTax()));
                priceInfo.setUpdateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
                priceInfo.setUpdateTime(new Date());
                priceInfo.setTax(productSkuChangePriceInfo.getOutTax());
                ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),null,1,priceInfo.getCreateBy(),new Date());
                //判断生效日期
                if (Optional.ofNullable(productSkuChangePriceInfo.getEffectiveTimeStart()).orElse(new Date()).after(new Date())) {
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
    public void savePurchaseChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto){
        //处理数据
        QueryProductSkuPriceInfo queryVO = dealPurchaseChangePriceData(dto, "1");
        //验重
        List<ProductSkuPriceInfo> list = productSkuPriceInfoMapper.checkRepeat(queryVO);
        Map<String, ProductSkuPriceInfo> infoMap = list.stream().collect(Collectors.toMap(o -> o.getSkuCode() + o.getSupplierCode(), Function.identity(),(k1,k2)->k2));
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
                priceInfo.setId(null);
                priceInfo.setApplyCode(dto.getCode());
                priceInfo.setPurchaseGroupCode(dto.getPurchaseGroupCode());
                priceInfo.setPurchaseGroupName(dto.getPurchaseGroupName());
                priceInfo.setCreateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
                priceInfo.setCreateTime(new Date());
                priceInfo.setCode("PP" + IdSequenceUtils.getInstance().nextId());
                priceInfo.setPriceTax(info.getPurchasePriceNew());
                priceInfo.setBeContainArea(0);
                priceInfo.setTax(info.getInTax());
                priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(Optional.ofNullable(info.getPurchasePriceNew()).orElse(BigDecimal.ZERO), info.getInTax()));
                info.setOfficialCode(priceInfo.getCode());
                priceInsertInfos.add(priceInfo);
                ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),null,1,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
                //判断生效日期
                if (Optional.ofNullable(info.getEffectiveTimeStart()).orElse(new Date()).after(new Date())) {
                    //未生效的
                    log.setStatus(0);
                    priceInfo.setBeSynchronous(0);
                } else {
                    info.setBeSynchronize(1);
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
                priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(Optional.ofNullable(productSkuChangePriceInfo.getPurchasePriceNew()).orElse(BigDecimal.ZERO), productSkuChangePriceInfo.getInTax()));
                priceInfo.setTax(productSkuChangePriceInfo.getInTax());
                priceInfo.setUpdateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
                priceInfo.setUpdateTime(new Date());
                priceInfo.setBeContainArea(0);
                productSkuChangePriceInfo.setOfficialCode(priceInfo.getCode());
                ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),productSkuChangePriceInfo.getEffectiveTimeStart(),null,1,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
                //判断生效日期
                if (Optional.ofNullable(productSkuChangePriceInfo.getEffectiveTimeStart()).orElse(new Date()).after(new Date())) {
                    //未生效的
                    //这里在日志表中插入一条未生效的数据
                    priceInfo.setBeSynchronous(0);
                    log.setStatus(0);
                } else {
                    //生效的
                    productSkuChangePriceInfo.setBeSynchronize(1);
                    priceInfo.setBeSynchronous(1);
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
            productSkuPriceInfoMapper.updateBatch(priceUpdateInfos);
        }
        if(CollectionUtils.isNotEmpty(logList)) {
            productSkuPriceInfoLogMapper.insertBatch(logList);
        }
        if(CollectionUtils.isNotEmpty(areaInfos)) {
           productSkuPriceAreaInfoMapper.insertBatch(areaInfos);
        }
        if(CollectionUtils.isNotEmpty(infoList)) {
            //更新申请表
            productSkuChangePriceInfoMapper.updateBatch(infoList);
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
    private QueryProductSkuPriceInfo dealPurchaseChangePriceData(ProductSkuChangePriceDTO dto,String priceType) {
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
        return new QueryProductSkuPriceInfo(dto.getCompanyCode(), skuCode, priceItemCode, supplierCode, transportCenterCode, warehouseCode, warehouseBatchNumber, priceType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) {
        changeStatus(newVO, dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejection(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) {
       changeStatus(newVO,dto);
    }

    @Override
    public Boolean cancelData(String code) {
        WorkFlowVO workFlowVO = new WorkFlowVO();
        ProductSkuChangePriceRespVO view = view(code);
        if(Objects.nonNull(view)&& CommonConstant.UNDER_REVIEW.equals(view.getApplyStatus())){
            workFlowVO.setFormNo(view.getFormNo());
            WorkFlowRespVO workFlowRespVO = cancelWorkFlow(workFlowVO);
            log.info(workFlowRespVO.getMsg());
            if(!workFlowRespVO.getSuccess()){
                throw new BizException(MessageId.create(Project.PRODUCT_API,98,workFlowRespVO.getMsg()));
            }
            return  workFlowRespVO.getSuccess();
        }else{
            throw new BizException(ResultCode.DATA_ERROR);
        }
    }

    @Override
    public BasePage<QuerySkuInfoRespVO> getSkuListByQueryVO(QuerySkuInfoReqVO reqVO) {
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        reqVO.setCompanyCode(currentAuthToken.getCompanyCode());
        return skuInfoService.getSkuListByQueryVO(reqVO);
    }

    @Override
    public BasePage<QuerySkuInfoRespVO> querySkuBatchList(QuerySkuInfoReqVO reqVO) {
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        reqVO.setCompanyCode(currentAuthToken.getCompanyCode());
        return stockService.querySkuBatchList(reqVO);
    }

    @Override
    public List<PriceJog> getPriceJog(String skuCode){
        return productSkuPriceInfoMapper.getPriceJog(skuCode,DateUtils.getYear());
    }
    @Override
    public List<QuerySkuInfoRespVOForIm> importForChangePrice(MultipartFile file, String purchaseGroupCode, String changePriceType){
        if (changePriceType.equals(CommonConstant.PURCHASE_CHANGE_PRICE)) {
            return importForPurchasePrice(file, purchaseGroupCode, changePriceType);
        }
        if (changePriceType.equals(CommonConstant.SALE_CHANGE_PRICE)) {
            return importForSalePrice(file, purchaseGroupCode, changePriceType);
        }
        if (changePriceType.equals(CommonConstant.TEMPORARY_CHANGE_PRICE)) {
            return importForTemporaryPrice(file, purchaseGroupCode, changePriceType);
        }
        return Lists.newArrayList();
    }
    @Override
    public List<QuerySkuInfoRespVOForIm> importForPurchasePrice(MultipartFile file, String purchaseGroupCode,String changePriceType) {
        try {
            List<PurchasePriceImport> imports = com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil.readExcel(file, PurchasePriceImport.class, 1, 0);
            dataValidation(imports);
            imports = imports.subList(1, imports.size());
            Set<String> set = Sets.newHashSet();
            imports.forEach(o->{
                set.add(o.getSkuCode());
            });
            QuerySkuInfoReqVO querySkuInfoReqVO = new QuerySkuInfoReqVO();
            querySkuInfoReqVO.setChangePriceType(CommonConstant.PURCHASE_CHANGE_PRICE);
            querySkuInfoReqVO.setSkuCodes(new ArrayList<>(set));
            querySkuInfoReqVO.setPurchaseGroupCode(purchaseGroupCode);
            querySkuInfoReqVO.setCompanyCode(getUser().getCompanyCode());
            List<QuerySkuInfoRespVO> queryNoPage = skuInfoService.getSkuListByQueryNoPage(querySkuInfoReqVO);
            List<String> s = Lists.newArrayList();
            s.add("调价原因");
            Map<String, SupplierDictionaryInfo> dicMap = supplierDictionaryInfoDao.selectByName(s, getUser().getCompanyCode());
            Map<String, QuerySkuInfoRespVO> queryNoPageMap = queryNoPage.stream().collect(Collectors.toMap(QuerySkuInfoRespVO::getSkuCode, Function.identity(), (k1, k2) -> k2));
            List<QuerySkuInfoRespVOForIm> list = Lists.newArrayList();
            Map<String,String> repeatMap = Maps.newHashMap();
            for (int i = 0; i < imports.size(); i++) {
                 CheckChangePrice checkChangePrice = new CheckChangePrice(queryNoPageMap, imports.get(i), repeatMap,dicMap,false)
                         .checkSkuInfo()//检查sku信息
                         .checkSupplier()//检查供应商信息
                         .checkPriceItemForPurchase()//补充采购的价格项目数据
                         .checkPurchasePrice()
                         .checkEffectiveTimeStart()
                         ;
                QuerySkuInfoRespVOForIm data = checkChangePrice.getData();
                String error = data.getError();
                if (StringUtils.isNotBlank(error)) {
                    String s2 = "第"+(i+2)+"行 "+error;
                    data.setError(s2);
                }
                list.add(data);
                repeatMap = checkChangePrice.getRepeatMap();
            }
            return list;
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_DATA_ERROR);
        }
    }

    @Override
    public List<QuerySkuInfoRespVOForIm> importForSalePrice(MultipartFile file, String purchaseGroupCode, String changePriceType) {
        try {
            List<SalePriceImport> imports = com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil.readExcel(file, SalePriceImport.class, 1, 0);
            dataValidation2(imports);
            imports = imports.subList(1, imports.size());
            Set<String> set = Sets.newHashSet();
            imports.forEach(o->{
                set.add(o.getSkuCode());
            });
            QuerySkuInfoReqVO querySkuInfoReqVO = new QuerySkuInfoReqVO();
            querySkuInfoReqVO.setChangePriceType(CommonConstant.SALE_CHANGE_PRICE);
            querySkuInfoReqVO.setSkuCodes(new ArrayList<>(set));
            querySkuInfoReqVO.setPurchaseGroupCode(purchaseGroupCode);
            querySkuInfoReqVO.setCompanyCode(getUser().getCompanyCode());
            List<QuerySkuInfoRespVO> queryNoPage = skuInfoService.getSkuListByQueryNoPage(querySkuInfoReqVO);
            List<String> s = Lists.newArrayList();
            s.add("调价原因");
            Map<String, SupplierDictionaryInfo> dicMap = supplierDictionaryInfoDao.selectByName(s, getUser().getCompanyCode());
            Map<String, QuerySkuInfoRespVO> queryNoPageMap = queryNoPage.stream().collect(Collectors.toMap(QuerySkuInfoRespVO::getSkuCode, Function.identity(), (k1, k2) -> k2));
            List<QuerySkuInfoRespVOForIm> list = Lists.newArrayList();
            Map<String,String> repeatMap = Maps.newHashMap();
            for (int i = 0; i < imports.size(); i++) {
                CheckChangePrice checkChangePrice = new CheckChangePrice(queryNoPageMap, imports.get(i), repeatMap,dicMap,true)
                        .checkSkuInfo()//检查sku信息
                        .checkPrice()//检查价格项目
//                        .checkSalePrice()//检查销售
                        .checkEffectiveTimeStart()//检查生效时间
//                        .checkChangeReason()//调价原因
                        ;
                QuerySkuInfoRespVOForIm data = checkChangePrice.getData();
                String error = data.getError();
                if (StringUtils.isNotBlank(error)) {
                    String s2 = "第"+(i+2)+"行 "+error;
                    data.setError(s2);
                    list.add(data);
                }else {
                    list.addAll(checkChangePrice.getDataForSale());
                }
                repeatMap = checkChangePrice.getRepeatMap();
            }
            return list;
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_DATA_ERROR);
        }
    }

    @Override
    public String getApplyCodeByFormNo(String formNo) {
        //首先通过formNO查找数据
        ProductSkuChangePriceDTO dto = productSkuChangePriceMapper.selectByFormNo(formNo);
        if(null == dto){
            throw new BizException(ResultCode.OBJECT_EMPTY_BY_FORMNO);
        }
        return dto.getCode();
    }

    @Override
    public PriceMeasurementRespVO priceMeasurement(List<PriceMeasurementReqVO> req,String date) {
        List<SaleCountDTO> list = productSkuChangePriceMapper.selectSaleNumBySkuCode(req,date);
        Map<String,SaleCountDTO> collect = list.stream().collect(Collectors.toMap(SaleCountDTO::getSkuCode,Function.identity(),(k1,k2)->k2));
        PriceMeasurementRespVO respVO = new PriceMeasurementRespVO();
        BigDecimal in = BigDecimal.ZERO;
        BigDecimal de = BigDecimal.ZERO;
        for (PriceMeasurementReqVO priceMeasurementReqVO : req) {
            if (Optional.ofNullable(priceMeasurementReqVO.getNewGrossProfitMargin()).orElse(BigDecimal.ZERO) .compareTo(Optional.ofNullable(priceMeasurementReqVO.getOldGrossProfitMargin()).orElse(BigDecimal.ZERO))==1) {
                in=in.add(BigDecimal.ONE);
            }
            if (Optional.ofNullable(priceMeasurementReqVO.getNewGrossProfitMargin()).orElse(BigDecimal.ZERO) .compareTo( Optional.ofNullable(priceMeasurementReqVO.getOldGrossProfitMargin()).orElse(BigDecimal.ZERO))==-1) {
                de=de.add(BigDecimal.ONE);
            }
        }
        long deAmount = req.stream().filter(o -> Optional.ofNullable(o.getNewGrossProfitMargin()).orElse(BigDecimal.ZERO)
                .compareTo(Optional.ofNullable(o.getOldGrossProfitMargin()).orElse(BigDecimal.ZERO))==-1).mapToLong(o -> Optional.ofNullable(collect.get(o.getSkuCode())).orElse(new SaleCountDTO()).getSaleNum() .multiply(Optional.ofNullable(o.getNewGrossProfitMargin()).orElse(BigDecimal.ZERO) .multiply(Optional.ofNullable(o.getPrice()).orElse(BigDecimal.ZERO)) ).longValue()).sum();
        long inAmount = req.stream().filter(o -> Optional.ofNullable(o.getNewGrossProfitMargin()).orElse(BigDecimal.ZERO).compareTo(Optional.ofNullable(o.getOldGrossProfitMargin()).orElse(BigDecimal.ZERO))==1).mapToLong(o -> Optional.ofNullable(collect.get(o.getSkuCode())).orElse(new SaleCountDTO()).getSaleNum() .multiply(Optional.ofNullable(o.getNewGrossProfitMargin()).orElse(BigDecimal.ZERO).multiply(Optional.ofNullable(o.getPrice()).orElse(BigDecimal.ZERO)) ).longValue()).sum();
        respVO.setDecreaseCount(de);
        respVO.setIncreaseCount(in);
        respVO.setDecreaseGrossProfit(BigDecimal.valueOf(deAmount));
        respVO.setIncreaseGrossProfit(BigDecimal.valueOf(inAmount));
        return respVO;
    }

    @Override
    public Boolean exportChangePriceData(HttpServletResponse resp,String code) {
        List<ExportChangePriceVO> list = productSkuChangePriceMapper.exportChangePriceData(code);
        try {
            ExcelUtil.writeExcel(resp,list,"价格DL格式导出模板","价格DL格式导出模板", ExcelTypeEnum.XLS, ExportChangePriceVO.class);
            return Boolean.FALSE;
        } catch (ExcelException e) {
            throw new BizException(ResultCode.EXPORT_FAILED);
        }
    }

    @Override
    public List<QuerySkuInfoRespVOForIm> importForTemporaryPrice(MultipartFile file, String purchaseGroupCode, String changePriceType) {
        try {
            List<TemporaryPriceImport> imports = com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil.readExcel(file, TemporaryPriceImport.class, 1, 0);
            dataValidation3(imports);
            imports = imports.subList(1, imports.size());
            Set<String> set = Sets.newHashSet();
            imports.forEach(o->{
                set.add(o.getSkuCode());
            });
            QuerySkuInfoReqVO querySkuInfoReqVO = new QuerySkuInfoReqVO();
            querySkuInfoReqVO.setChangePriceType(CommonConstant.TEMPORARY_CHANGE_PRICE);
            querySkuInfoReqVO.setSkuCodes(new ArrayList<>(set));
            querySkuInfoReqVO.setPurchaseGroupCode(purchaseGroupCode);
            querySkuInfoReqVO.setCompanyCode(getUser().getCompanyCode());
            List<QuerySkuInfoRespVO> queryNoPage = skuInfoService.getSkuListByQueryNoPage(querySkuInfoReqVO);
            List<String> s = Lists.newArrayList();
            s.add("调价原因");
            Map<String, SupplierDictionaryInfo> dicMap = supplierDictionaryInfoDao.selectByName(s, getUser().getCompanyCode());
            Map<String, QuerySkuInfoRespVO> queryNoPageMap = queryNoPage.stream().collect(Collectors.toMap(QuerySkuInfoRespVO::getSkuCode, Function.identity(), (k1, k2) -> k2));
            List<QuerySkuInfoRespVOForIm> list = Lists.newArrayList();
            Map<String,String> repeatMap = Maps.newHashMap();
            for (int i = 0; i < imports.size(); i++) {
                CheckChangePrice checkChangePrice = new CheckChangePrice(queryNoPageMap, imports.get(i), repeatMap,dicMap,false)
                        .checkSkuInfo()//检查sku信息
                        .checkTempPrice()//检查价格项目
//                        .checkTemporaryPrice()//检查临时价
                        .checkEffectiveTimeStart()//检查生效时间
                        .checkEffectiveTimeEnd()//检查失效时间
//                        .checkChangeReason()//调价原因
                        ;
                QuerySkuInfoRespVOForIm data = checkChangePrice.getData();
                String error = data.getError();
                if (StringUtils.isNotBlank(error)) {
                    String s2 = "第"+(i+2)+"行 "+error;
                    data.setError(s2);
                    list.add(data);
                }else {
                    list.addAll(checkChangePrice.getDataForSale());
                }
                repeatMap = checkChangePrice.getRepeatMap();
            }
            return list;
        } catch (ExcelException e) {
            throw new BizException(ResultCode.IMPORT_DATA_ERROR);
        }
    }

    /**
     * 校验数据和表头
     * @param skuInfoImports
     */
    private void dataValidation( List<PurchasePriceImport> skuInfoImports) {
        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(skuInfoImports)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        if (skuInfoImports.size()<2) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        String  head = PurchasePriceImport.HEAD;
        boolean equals = skuInfoImports.get(0).toString().equals(head);
        if(!equals){
            throw new BizException(ResultCode.IMPORT_HEDE_ERROR);
        }
    }
    /**
     * 校验数据和表头
     * @param skuInfoImports
     */
    private void dataValidation2( List<SalePriceImport> skuInfoImports) {
        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(skuInfoImports)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        if (skuInfoImports.size()<2) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        String  head = SalePriceImport.HEAD;
        boolean equals = skuInfoImports.get(0).toString().equals(head);
        if(!equals){
            throw new BizException(ResultCode.IMPORT_HEDE_ERROR);
        }
    }
    /**
     * 校验数据和表头
     * @param skuInfoImports
     */
    private void dataValidation3( List<TemporaryPriceImport> skuInfoImports) {
        if(com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(skuInfoImports)) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        if (skuInfoImports.size()<2) {
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        String  head = TemporaryPriceImport.HEAD;
        boolean equals = skuInfoImports.get(0).toString().equals(head);
        if(!equals){
            throw new BizException(ResultCode.IMPORT_HEDE_ERROR);
        }
    }

    @Getter(AccessLevel.PRIVATE)
    private class CheckChangePrice {
        private Map<String,QuerySkuInfoRespVO> queryNoPage;
        private PriceImport anImport;
        private Map<String,String> repeatMap;
        private List<String> error;
        private QuerySkuInfoRespVOForIm resp;
        private Map<String, SupplierDictionaryInfo> dicMap;
        private boolean flag =  true;
        private boolean beSale;
        private List<PriceChannelForChangePrice> priceList;
        private CheckChangePrice(Map<String,QuerySkuInfoRespVO> queryNoPage, Object purchasePriceImport,Map<String,String> repeatMap,Map<String, SupplierDictionaryInfo> dicMap,boolean beSale) {
            this.queryNoPage = queryNoPage;
            this.anImport = BeanCopyUtils.copy(purchasePriceImport,PriceImport.class);
            this.repeatMap = repeatMap;
            this.error = Lists.newArrayList();
            this.resp = new QuerySkuInfoRespVOForIm();
            this.dicMap = dicMap;
            this.priceList = Lists.newArrayList();
            this.beSale = beSale;
        }
        //校验sku信息
        private CheckChangePrice checkSkuInfo(){
            if (StringUtils.isBlank(anImport.getSkuCode())) {
                error.add("SKU编码不能为为空");
                flag = false;
            }else {
                QuerySkuInfoRespVO querySkuInfoRespVO = queryNoPage.get(anImport.getSkuCode().trim());
                if (Objects.isNull(querySkuInfoRespVO)) {
                    resp.setSkuCode(anImport.getSkuCode().trim());
                    error.add("无对应的SKU编码数据或该SKU不属于所选采购组");
                    flag = false;
                }else {
                    if (StringUtils.isBlank(anImport.getSkuName())) {
                        error.add("SKU名称不能为空");
                        flag = false;
                    }else {
                        if (!anImport.getSkuName().equals(querySkuInfoRespVO.getSkuName())) {
                            resp.setSkuName(anImport.getSkuName().trim());
                            error.add("SKU编码和SKU名称无法对应");
                            flag = false;
                        }else {
                            resp.setSkuCode(querySkuInfoRespVO.getSkuCode());
                            resp.setSkuName(querySkuInfoRespVO.getSkuName());
                        }
                    }
                }
            }
            return this;
        }
        //检查供应商
        private CheckChangePrice checkSupplier(){
            if (CollectionUtils.isEmpty(error)) {
                QuerySkuInfoRespVO querySkuInfoRespVO = queryNoPage.get(anImport.getSkuCode().trim());
                Map<String, supplierInfoVO> collect = querySkuInfoRespVO.getSupplierInfoVOS().stream().collect(Collectors.toMap(supplierInfoVO::getSupplierCode, Function.identity(), (k1, k2) -> k2));
                if (com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyMap(collect)) {
                    error.add("该sku下未含有供应商信息");
                }else {
                    if(StringUtils.isBlank(anImport.getSupplierCode())){
                        error.add("供应商编码不能为空");
                    }else {
                        supplierInfoVO vo = collect.get(anImport.getSupplierCode().trim());
                        if (Objects.isNull(vo)) {
                            this.resp.setSupplierCode(anImport.getSupplierCode().trim());
                            error.add("无法找到对应编码的供应商");
                        }else {
                            if (StringUtils.isBlank(anImport.getSupplierName())) {
                                error.add("供应商名称不能为为空");
                            }else {
                                if(!vo.getSupplierName().equals(anImport.getSupplierName())){
                                    this.resp.setSupplierName(anImport.getSupplierName().trim());
                                    error.add("供应商编码和名称不对应");
                                }else {
                                    this.resp.setSupplierCode(vo.getSupplierCode());
                                    this.resp.setSupplierName(vo.getSupplierName());
                                    this.resp.setPurchasePriceOld(vo.getPurchasePriceOld());
                                    this.resp.setPurchasePriceNewest(vo.getPurchasePriceNewest());
                                    this.resp.setBeDefault(vo.getBeDefault()?1:0);
                                }
                            }
                        }
                    }
                }
            }
            return this;
        }
        //检查采购价
        private CheckChangePrice checkPurchasePrice(){
            if (StringUtils.isNotBlank(anImport.getPurchasePriceNew())) {
                try {
                    this.resp.setPurchasePriceNew(new BigDecimal(anImport.getPurchasePriceNew().trim()));
                } catch (Exception e) {
                    error.add("采购价格式不正确");
                }
            }
            return this;
        }

        //检查含税价
        private CheckChangePrice checkSalePrice(){
            if (StringUtils.isNotBlank(anImport.getNewPrice())) {
                try {
                    this.resp.setNewPrice(new BigDecimal(anImport.getNewPrice().trim()));
                } catch (Exception e) {
                    error.add("含税价格式不正确");
                }
            }
            return this;
        }

        //检查临时含税价
        private CheckChangePrice checkTemporaryPrice(){
            if (StringUtils.isNotBlank(anImport.getTemporaryPrice())) {
                try {
                    this.resp.setTemporaryPrice(new BigDecimal(anImport.getTemporaryPrice().trim()));
                } catch (Exception e) {
                    error.add("临时含税价格式不正确");
                }
            }
            return this;
        }
        //检查生效日期
        private CheckChangePrice checkEffectiveTimeStart(){
            if (StringUtils.isNotBlank(anImport.getEffectiveTimeStart())) {
                try {
                    this.resp.setEffectiveTimeStart(DateUtils.toDate(anImport.getEffectiveTimeStart().trim()));
                } catch (Exception e) {
                    error.add("生效时间格式不正确");
                }
            }
            return this;
        }

        //检查失效日期
        private CheckChangePrice checkEffectiveTimeEnd(){
            if (StringUtils.isNotBlank(anImport.getEffectiveTimeEnd())) {
                try {
                    this.resp.setEffectiveTimeEnd(DateUtils.toDate(anImport.getEffectiveTimeEnd().trim()));
                } catch (Exception e) {
                    error.add("失效时间格式不正确");
                }
            }
            return this;
        }

        //检查价格项目
        private CheckChangePrice checkPriceItem(){
            if (StringUtils.isBlank(anImport.getPriceItemName())) {
                error.add("价格项目不能为空");
            }else {
                if (CollectionUtils.isEmpty(error)) {
                    QuerySkuInfoRespVO querySkuInfoRespVO = queryNoPage.get(anImport.getSkuCode().trim());
                    if (Objects.isNull(querySkuInfoRespVO)) {
                        return this;
                    }
                    Map<String, PriceChannelForChangePrice> collect = querySkuInfoRespVO.getPriceChannelList().stream().collect(Collectors.toMap(PriceChannelForChangePrice::getPriceItemName, Function.identity(), (k1, k2) -> k2));
                    if (com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyMap(collect)) {
                        this.resp.setPriceItemName(anImport.getPriceItemName().trim());
                        error.add("未找到对应的价格项目信息");
                    }else {
                        PriceChannelForChangePrice item = collect.get(anImport.getPriceItemName().trim());
                        if (Objects.isNull(item)) {
                            this.resp.setPriceItemName(anImport.getPriceItemName().trim());
                            error.add("未找到对应名称的价格项目");
                        }else {
                            this.resp.setPriceItemCode(item.getPriceItemCode());
                            this.resp.setPriceItemName(item.getPriceItemName());
                            this.resp.setPriceTypeCode(item.getPriceTypeCode());
                            this.resp.setPriceTypeName(item.getPriceTypeCode());
                            this.resp.setPriceAttributeCode(item.getPriceAttributeCode());
                            this.resp.setPriceAttributeName(item.getPriceAttributeCode());
                            this.resp.setOldPrice(item.getOldPrice());
                            this.resp.setOldGrossProfitMargin(item.getOldGrossProfitMargin());
                        }
                    }
                }
            }
            return this;
        }
        //采购补充价格项目信息
        private CheckChangePrice checkPriceItemForPurchase(){
            QuerySkuInfoRespVO querySkuInfoRespVO = queryNoPage.get(anImport.getSkuCode().trim());
            if (Objects.isNull(querySkuInfoRespVO)) {
                return this;
            }
            List<PriceChannelForChangePrice> collect = querySkuInfoRespVO.getPriceChannelList();
            if (com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyCollection(collect)||collect.size()>1) {
                error.add("该sku的采购价格项目异常");
            }else{
                //默认只有一条
                PriceChannelForChangePrice item = collect.get(0);
                this.resp.setPriceTypeCode(item.getPriceTypeCode());
                this.resp.setPriceTypeName(item.getPriceTypeCode());
                this.resp.setPriceItemCode(item.getPriceItemCode());
                this.resp.setPriceItemName(item.getPriceItemName());
                this.resp.setPriceAttributeCode(item.getPriceAttributeCode());
                this.resp.setPriceAttributeName(item.getPriceAttributeCode());
            }
            return this;
        }
        //检查批次号
        private CheckChangePrice checkBatch(){
            if (StringUtils.isNotBlank(anImport.getWarehouseBatchName())) {
                if (CollectionUtils.isEmpty(error)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    QuerySkuInfoRespVO querySkuInfoRespVO = queryNoPage.get(anImport.getSkuCode().trim());
                    Map<String, BatchInfo> collect = querySkuInfoRespVO.getBatchList().stream().collect(Collectors.toMap(o -> o.getTransportCenterName()+"-" + o.getWarehouseName()+"-"+ o.getWarehouseBatchName()+"-" + sdf.format(o.getProductTime()), Function.identity(), (k1, k2) -> k2));
                    if (com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyMap(collect)) {
                        error.add("未找到批次信息");
                    }else {
                        BatchInfo item = collect.get(anImport.getWarehouseBatchName().trim());
                        if (Objects.isNull(item)) {
                            this.resp.setWarehouseBatchName(anImport.getWarehouseBatchName().trim());
                            error.add("未找到对应名称的批次信息");
                        }else {
                            this.resp.setTransportCenterName(item.getTransportCenterName());
                            this.resp.setTransportCenterCode(item.getTransportCenterCode());
                            this.resp.setWarehouseCode(item.getWarehouseCode());
                            this.resp.setWarehouseName(item.getWarehouseName());
                            this.resp.setWarehouseBatchNumber(item.getWarehouseBatchNumber());
                            this.resp.setProductTime(item.getProductTime());
                            this.resp.setBatchRemark(item.getBatchRemark());

                        }
                    }
                }
            }
            return this;
        }
        //调价原因
        private CheckChangePrice checkChangeReason(){
            if (StringUtils.isNotBlank(anImport.getChangePriceReasonName())) {
                SupplierDictionaryInfo info = dicMap.get(anImport.getChangePriceReasonName().trim());
                if (Objects.isNull(info)) {
                    this.resp.setChangePriceReasonName(anImport.getChangePriceReasonName().trim());
                    error.add("未找到对应名称的调价原因");
                }else {
                    this.resp.setChangePriceReasonCode(info.getSupplierDictionaryValue());
                    this.resp.setChangePriceReasonName(info.getSupplierContent());
                }
            }
            return this;
        }

        //检查价格
        private CheckChangePrice checkPrice() {
            //爱亲渠道价
            if (StringUtils.isNotBlank(anImport.getReadyCol67())) {
                checkPriceItemForSale("爱亲渠道价",anImport.getReadyCol67());
            }
            //萌贝树渠道价
            if (StringUtils.isNotBlank(anImport.getReadyCol68())) {
                checkPriceItemForSale("萌贝树渠道价",anImport.getReadyCol68());
            }
            //小红马渠道价
            if (StringUtils.isNotBlank(anImport.getReadyCol69())) {
                checkPriceItemForSale("小红马渠道价",anImport.getReadyCol69());
            }
            //爱亲分销价
            if (StringUtils.isNotBlank(anImport.getReadyCol70())) {
                checkPriceItemForSale("爱亲分销价",anImport.getReadyCol70());
            }
            //萌贝树分销价
            if (StringUtils.isNotBlank(anImport.getReadyCol71())) {
                checkPriceItemForSale("萌贝树分销价",anImport.getReadyCol71());
            }
            //小红马分销价
            if (StringUtils.isNotBlank(anImport.getReadyCol72())) {
                checkPriceItemForSale("小红马分销价",anImport.getReadyCol72());
            }
            //爱亲售价
            if (StringUtils.isNotBlank(anImport.getReadyCol73())) {
                checkPriceItemForSale("售价",anImport.getReadyCol73());
            }
            //萌贝树售价
            if (StringUtils.isNotBlank(anImport.getReadyCol74())) {
                checkPriceItemForSale("会员价",anImport.getReadyCol74());
            }
//            //小红马售价
//            if (StringUtils.isNotBlank(anImport.getReadyCol75())) {
//                checkPriceItemForSale("小红马售价",anImport.getReadyCol75());
//            }
            return this;
        }

        //检查价格
        private CheckChangePrice checkTempPrice() {
            //爱亲临时渠道价
            if (StringUtils.isNotBlank(anImport.getReadyCol67())) {
                checkPriceItemForSale("爱亲临时渠道价",anImport.getReadyCol67());
            }
            //萌贝树临时渠道价
            if (StringUtils.isNotBlank(anImport.getReadyCol68())) {
                checkPriceItemForSale("萌贝树临时渠道价",anImport.getReadyCol68());
            }
            //小红马临时渠道价
            if (StringUtils.isNotBlank(anImport.getReadyCol69())) {
                checkPriceItemForSale("小红马临时渠道价",anImport.getReadyCol69());
            }
            //爱亲临时分销价
            if (StringUtils.isNotBlank(anImport.getReadyCol70())) {
                checkPriceItemForSale("爱亲临时分销价",anImport.getReadyCol70());
            }
            //萌贝树临时分销价
            if (StringUtils.isNotBlank(anImport.getReadyCol71())) {
                checkPriceItemForSale("萌贝树临时分销价",anImport.getReadyCol71());
            }
            //小红马临时分销价
            if (StringUtils.isNotBlank(anImport.getReadyCol72())) {
                checkPriceItemForSale("小红马临时分销价",anImport.getReadyCol72());
            }
            //爱亲临时售价
            if (StringUtils.isNotBlank(anImport.getReadyCol73())) {
                checkPriceItemForSale("临时售价",anImport.getReadyCol73());
            }
            //萌贝树临时售价
            if (StringUtils.isNotBlank(anImport.getReadyCol74())) {
                checkPriceItemForSale("临时会员价",anImport.getReadyCol74());
            }
//            //小红马售价
//            if (StringUtils.isNotBlank(anImport.getReadyCol75())) {
//                checkPriceItemForSale("小红马临时售价",anImport.getReadyCol75());
//            }
            return this;
        }

        private void checkPriceItemForSale(String priceItemName,String price){
            if(StringUtils.isNotBlank(price)){
                if (flag) {
                    QuerySkuInfoRespVO querySkuInfoRespVO = queryNoPage.get(anImport.getSkuCode().trim());
                    if (Objects.isNull(querySkuInfoRespVO)) {
                        return;
                    }
                    Map<String, PriceChannelForChangePrice> collect = querySkuInfoRespVO.getPriceChannelList().stream().collect(Collectors.toMap(PriceChannelForChangePrice::getPriceItemName, Function.identity(), (k1, k2) -> k2));
                    if (com.aiqin.bms.scmp.api.util.CollectionUtils.isEmptyMap(collect)) {
                        error.add("未找到对应的价格项目信息");
                    }else {
                        PriceChannelForChangePrice item = collect.get(priceItemName.trim());
                        if (Objects.isNull(item)) {
                            error.add("库中未找到名称为"+priceItemName+"的价格项目或此价格项目被禁用");
                        }else {
                            try {
                                PriceChannelForChangePrice copy = BeanCopyUtils.copy(item, PriceChannelForChangePrice.class);
                                copy.setNewPrice(new BigDecimal(price));
                                priceList.add(copy);
                            } catch (Exception e) {
                                error.add(priceItemName+"格式不正确");
                            }
                        }
                    }
                }
            }
        }
        //获取数据
        private QuerySkuInfoRespVOForIm getData(){
            String s = repeatMap.get(resp.getSkuCode() + resp.getPriceItemName() + resp.getSupplierCode() + resp.getWarehouseBatchName());
            if(StringUtils.isNotBlank(s)){
                error.add("该条数据与其他数据重复");
            }
            if (CollectionUtils.isNotEmpty(error)) {
                resp.setError(StringUtils.strip(error.toString(),"[]"));
                return this.resp;
            }else {
                QuerySkuInfoRespVO querySkuInfoRespVO = queryNoPage.get(resp.getSkuCode());
                //补充需要的数据
                this.resp.setBatchList(querySkuInfoRespVO.getBatchList());
                this.resp.setSupplierInfoVOS(querySkuInfoRespVO.getSupplierInfoVOS());
                this.resp.setPriceChannelList(querySkuInfoRespVO.getPriceChannelList());
            }
            repeatMap.put(resp.getSkuCode() + resp.getPriceItemName() + resp.getSupplierCode() + resp.getWarehouseBatchName(),"检查重复数据");
            return resp;
        }

        private List<QuerySkuInfoRespVOForIm> getDataForSale(){
            List<QuerySkuInfoRespVOForIm> returnList = Lists.newArrayList();
            List<PriceChannelForChangePrice> priceList = this.priceList;
            for (PriceChannelForChangePrice price : priceList) {
                String s = repeatMap.get(resp.getSkuCode() + price.getPriceItemName() + resp.getSupplierCode() + resp.getWarehouseBatchName());
                if(StringUtils.isNotBlank(s)){
                    error.add("该条数据中价格项目为"+price.getPriceItemName()+"与其他数据重复");
                }
                if (CollectionUtils.isEmpty(error)) {
                    QuerySkuInfoRespVO querySkuInfoRespVO = queryNoPage.get(resp.getSkuCode());
                    //补充需要的数据
                    QuerySkuInfoRespVOForIm copy = BeanCopyUtils.copy(this.resp, QuerySkuInfoRespVOForIm.class);
                    copy.setPriceItemCode(price.getPriceItemCode());
                    copy.setPriceItemName(price.getPriceItemName());
                    copy.setPriceTypeCode(price.getPriceTypeCode());
                    copy.setPriceTypeName(price.getPriceTypeCode());
                    copy.setPriceAttributeCode(price.getPriceAttributeCode());
                    copy.setPriceAttributeName(price.getPriceAttributeCode());
                    copy.setOldPrice(price.getOldPrice());
                    copy.setOldGrossProfitMargin(price.getOldGrossProfitMargin());
                    if(beSale){
                        copy.setNewPrice(price.getNewPrice());
                    }else {
                        copy.setTemporaryPrice(price.getNewPrice());
                    }
                    copy.setBatchList(querySkuInfoRespVO.getBatchList());
                    copy.setSupplierInfoVOS(querySkuInfoRespVO.getSupplierInfoVOS());
                    copy.setPriceChannelList(querySkuInfoRespVO.getPriceChannelList());
                    repeatMap.put(resp.getSkuCode() + resp.getPriceItemName() + resp.getSupplierCode() + resp.getWarehouseBatchName(), "检查重复数据");
                    returnList.add(copy);
                }
            }
            return returnList;
        }
    }
}
