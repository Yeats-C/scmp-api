package com.aiqin.bms.scmp.api.supplier.service.impl;


import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.MsgStatus;
import com.aiqin.bms.scmp.api.base.UrlConfig;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.response.sku.QueryProductSkuListResp;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPushWmsMapper;
import com.aiqin.bms.scmp.api.product.service.SkuInfoService;
import com.aiqin.bms.scmp.api.purchase.domain.response.reject.RejectResponse;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplierFileDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyAccountDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierFile;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierWms;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompanyPurchaseGroup;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.SupplyCompanyDetailDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplierComAcctReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplyComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.SupplierFileReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.DetailTagUseRespVo;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplyCompanyMapper;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplyCompanyPurchaseGroupMapper;
import com.aiqin.bms.scmp.api.supplier.service.DeliveryInfoService;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import com.aiqin.bms.scmp.api.supplier.service.SupplyComService;
import com.aiqin.bms.scmp.api.supplier.service.TagInfoService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/12/3 0003 14:27
 */
@Service
@Slf4j
public class SupplyComServiceImpl implements SupplyComService {
    @Autowired
    private SupplyCompanyDao supplyCompanyDao;
    @Autowired
    private SupplyCompanyMapper supplyCompanyMapper;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private SupplierFileDao supplierFileDao;
    @Autowired
    private SupplyCompanyAccountDao supplyCompanyAccountDao;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private DeliveryInfoService deliveryInfoService;
    @Autowired
    private TagInfoService tagInfoService;
    @Autowired
    private SupplyCompanyPurchaseGroupMapper supplyCompanyPurchaseGroupMapper;
    @Autowired
    private ProductSkuPushWmsMapper productSkuPushWmsMapper;
    @Autowired
    private UrlConfig urlConfig;
    @Override
    public BasePage<SupplyComListRespVO> getSupplyCompanyInfoList(QuerySupplyComReqVO querySupplyComReqVO) {
        try {
            //前端调用需要封装
            if(StringUtils.isBlank(querySupplyComReqVO.getCompanyCode())){
                AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
                if(null != authToken){
                    querySupplyComReqVO.setCompanyCode(authToken.getCompanyCode());
                    querySupplyComReqVO.setPersonId(authToken.getPersonId());
                }
            }
            PageHelper.startPage(querySupplyComReqVO.getPageNo(), querySupplyComReqVO.getPageSize());
            List<SupplyComListRespVO> supplyComListRespVOS = supplyCompanyDao.getSupplyCompanyList(querySupplyComReqVO);
            return PageUtil.getPageList(querySupplyComReqVO.getPageNo(),supplyComListRespVOS);
        } catch (Exception e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }

    @Override
    public List<SupplyComListRespVO> getAllSupplyComList(String code, String name) {
        QuerySupplyComReqVO querySupplyComReqVO = new QuerySupplyComReqVO();
        try {
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                querySupplyComReqVO.setCompanyCode(authToken.getCompanyCode());
                querySupplyComReqVO.setPersonId(authToken.getPersonId());
            }
            querySupplyComReqVO.setEnable(StatusTypeCode.EN_ABLE.getStatus().toString());
            querySupplyComReqVO.setSupplyComNameOrShort(name);
            querySupplyComReqVO.setSupplyCompanyCode(code);
            List<SupplyComListRespVO> supplyComListRespVOS = supplyCompanyDao.getSupplyCompanyList(querySupplyComReqVO);
            return supplyComListRespVOS;
        } catch (Exception e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int logicDelete(Long id) {
        int num;
        try {
            SupplyCompany supplyCompany = new SupplyCompany();
            supplyCompany.setId(id);
            supplyCompany.setDelFlag(StatusTypeCode.DEL_FLAG.getStatus());
            num = ((SupplyComService) AopContext.currentProxy()).update(supplyCompany);
        } catch (GroundRuntimeException e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
        return num;
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int update(SupplyCompany supplyCompany) {
        try {
            int num = supplyCompanyMapper.updateByPrimaryKeySelective(supplyCompany);
            if (num > 0){
                return num;
            } else {
                throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"修改失败"));
            }
        } catch (GroundRuntimeException e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public int insert(SupplyCompany supplyCompany) {
        try {
            int num = supplyCompanyMapper.insertSelective(supplyCompany);
            if (num > 0){
                return num;
            } else {
                throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"新增失败"));
            }
        } catch (GroundRuntimeException e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }

    @Override
    public SupplyComDetailRespVO getSupplyComDetail(Long id, StatusTypeCode statusTypeCode) {
        SupplyComDetailRespVO supplyComDetailRespVO = new SupplyComDetailRespVO();
        try {
            //根据ID查询供货单位,结算,收货信息
            SupplyCompanyDetailDTO supplyCompanyDetailDTO = supplyCompanyDao.getSupplyComDetail(id);
            if (null != supplyCompanyDetailDTO){
                BeanCopyUtils.copy(supplyCompanyDetailDTO,supplyComDetailRespVO);
                supplyComDetailRespVO.setId(supplyCompanyDetailDTO.getSupplyComId());
                supplyComDetailRespVO.setPurchasingGroupCode(supplyCompanyDetailDTO.getSupplyPurchasingGroupCode());
                supplyComDetailRespVO.setPurchasingGroupName(supplyCompanyDetailDTO.getSupplyPurchasingGroupName());
                supplyComDetailRespVO.setApplyAbbreviation(supplyCompanyDetailDTO.getSupplyAbbreviation());
                supplyComDetailRespVO.setApplySupplyCode(supplyCompanyDetailDTO.getSupplyCode());
                supplyComDetailRespVO.setApplySupplyType(supplyCompanyDetailDTO.getSupplyType());
                supplyComDetailRespVO.setApplySupplyTypeName(supplyCompanyDetailDTO.getSupplyTypeName());
                supplyComDetailRespVO.setApplySupplyName(supplyCompanyDetailDTO.getSupplyName());
                //获取操作日志
                OperationLogVo operationLogVo = new OperationLogVo();
                operationLogVo.setPageNo(1);
                operationLogVo.setPageSize(100);
                operationLogVo.setObjectType(ObjectTypeCode.SUPPLY_COMPANY.getStatus());
                operationLogVo.setObjectId(supplyCompanyDetailDTO.getSupplyCode());
                BasePage<LogData> pageList = operationLogService.getLogType(operationLogVo,62);
                List<LogData> logDataList = new ArrayList<>();
                if (null != pageList.getDataList() && pageList.getDataList().size() > 0){
                    logDataList = pageList.getDataList();
                }
                supplyComDetailRespVO.setLogDataList(logDataList);
                //根据供货单位编码查询发货/退货信息
                List<DeliveryInfoRespVO> deliveryInfoRespVOS = deliveryInfoService.getDeliveryInfoBySupplyCompanyCode(supplyCompanyDetailDTO.getSupplyCode());
                supplyComDetailRespVO.setDeliveryInfoRespVOS(deliveryInfoRespVOS);
                //根据供货单位编码查询标签信息
                List<DetailTagUseRespVo> useTagRecordReqVos = tagInfoService.getUseTagRecordByUseObjectCode2(supplyCompanyDetailDTO.getSupplyCode(), TagTypeCode.SUPPLIER.getStatus());
                supplyComDetailRespVO.setUseTagRecordReqVos(useTagRecordReqVos);

                List<SupplierFile> supplierFile = supplierFileDao.getSupplierFile(supplyCompanyDetailDTO.getSupplyCode());
                if(CollectionUtils.isNotEmptyCollection(supplierFile)){
                    List<SupplierFileReqVO> list = BeanCopyUtils.copyList(supplierFile, SupplierFileReqVO.class);
                    list.forEach(item->{
                        item.setApplySupplierCode(supplyCompanyDetailDTO.getSupplyCode());
                        item.setApplySupplierName(supplyCompanyDetailDTO.getSupplyName());
                    });
                    supplyComDetailRespVO.setFileReqVOList(list);
                }
                List<SupplyCompanyPurchaseGroup> supplyCompanyPurchaseGroups = supplyCompanyPurchaseGroupMapper.selectBySupplyCompanyCode(supplyCompanyDetailDTO.getSupplyCode());
                if(CollectionUtils.isNotEmptyCollection(supplyCompanyPurchaseGroups)){
                    List<SupplyCompanyPurchaseGroupResVo> purchaseGroupVos = BeanCopyUtils.copyList(supplyCompanyPurchaseGroups, SupplyCompanyPurchaseGroupResVo.class);
                    supplyComDetailRespVO.setPurchaseGroupVos(purchaseGroupVos);
                }
                if (Objects.equals(StatusTypeCode.SHOW_ACCOUNT_SKU,statusTypeCode)) {
                    QuerySupplierComAcctReqVo vo = new QuerySupplierComAcctReqVo();
                    vo.setSupplyCompanyCode(supplyComDetailRespVO.getApplySupplyCode());
                    List<QuerySupplierComAcctRespVo> querySupplierComAcctRespVos = supplyCompanyAccountDao.selectListByQueryVO(vo);
                    supplyComDetailRespVO.setSupplierComAcctRespVos(querySupplierComAcctRespVos);
                    List<QueryProductSkuListResp> queryProductSkuListResps = null;
                    queryProductSkuListResps = skuInfoService.querySkuListBySupplyUnitCode(supplyComDetailRespVO.getApplySupplyCode());
                    supplyComDetailRespVO.setSkuListRespVos(queryProductSkuListResps);
                }
            } else {
                throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"未查询信息"));
            }
        } catch (GroundRuntimeException e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
        }
        return supplyComDetailRespVO;
    }

    /**
     * 通过编码获取供货单位详情
     * @param supplyCode
     * @return
     */
    @Override
    public SupplyComDetailByCodeRespVO detailByCode(String supplyCode) {
        SupplyComDetailByCodeRespVO supplyComDetailRespVO = new SupplyComDetailByCodeRespVO();
        try {
            //根据ID查询供货单位,结算,收货信息
            String companyCode = "";
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                companyCode = authToken.getCompanyCode();
            }
            SupplyCompany supplyCompanyDetailDTO = supplyCompanyDao.detailByCode(supplyCode,companyCode);
            if (null != supplyCompanyDetailDTO){
                BeanCopyUtils.copy(supplyCompanyDetailDTO,supplyComDetailRespVO);
                return  supplyComDetailRespVO;

                }else {
                throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"未查询信息"));
            }
        } catch (GroundRuntimeException e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }

    /**
     * 更新评分
     *
     * @param supplierCode
     * @param starScore
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateStarScore(String supplierCode, BigDecimal starScore) {
        //根据供应商编号获取供应商综合评分
        SupplyCompany supplyCompany = supplyCompanyDao.detailByCode(supplierCode, null);
        BigDecimal newStarScore = starScore;
        if(null != supplyCompany && null != supplyCompany.getStarScore() &&  supplyCompany.getStarScore().compareTo(BigDecimal.ZERO) != 0){
            newStarScore = (starScore.add(supplyCompany.getStarScore())).divide(new BigDecimal(2),1,BigDecimal.ROUND_HALF_UP);
        }
        return supplyCompanyMapper.updateStarScore(supplierCode,newStarScore);
    }

    @Override
    public Map<String, SupplyCompany> selectBySupplyComCodes(Set<String> supplierList,String companyCode) {
        return supplyCompanyMapper.selectBySupplyComCodes(supplierList, companyCode);
    }

    @Override
    public Map<String, SupplyCompany> selectBySupplyComNames(Set<String> supplierList, String companyCode) {
        return supplyCompanyMapper.selectBySupplyComNames(supplierList, companyCode);
    }

    @Override
    public HttpResponse supplyImportWms() {
        // 查询出所有没有推送到wms的供应商信息
        List<String> supplyCodes = productSkuPushWmsMapper.selectAllSupplyCode();
        log.info("查询出所有没有推送到wms供应商信息：[{}]", JsonUtil.toJson(supplyCodes));
        for (String supplyCode : supplyCodes) {
            log.info("当前准备推送到wms供应商信息：[{}]", JsonUtil.toJson(supplyCode));
            SupplyCompany supplyCompany = supplyCompanyDao.detailByCode(supplyCode, Global.COMPANY_09);
            SupplierWms supplierWms=new SupplierWms();
            supplierWms.setAddress(supplyCompany.getAddress());
            supplierWms.setArea(supplyCompany.getArea());
            supplierWms.setContactName(supplyCompany.getContactName());
            supplierWms.setDelFlag(supplyCompany.getDelFlag());
            supplierWms.setEmail(supplyCompany.getEmail());
            supplierWms.setMobilePhone(supplyCompany.getMobilePhone());
            supplierWms.setRemark(supplyCompany.getRemark());
            supplierWms.setSupplierAbbreviation(supplyCompany.getSupplyAbbreviation());
            supplierWms.setSupplyCode(supplyCompany.getSupplyCode());
            supplierWms.setSupplyName(supplyCompany.getSupplyName());
            supplierWms.setSupplyType(supplyCompany.getSupplyType());
            supplierWms.setUpdateTime(supplyCompany.getUpdateTime());
            log.info("传入wms的供应商信息为{}", JsonUtil.toJson(supplierWms));
            try {
                StringBuilder url = new StringBuilder();
                url.append(urlConfig.WMS_API_URL2).append("/infoPushAndInquiry/source/supplierInfoPush" );
                HttpClient httpClient = HttpClient.post(String.valueOf(url)).json(supplierWms).timeout(30000);
                HttpResponse result = httpClient.action().result(new TypeReference<HttpResponse>(){
                });
                if (!Objects.equals(result.getCode(), MsgStatus.SUCCESS)) {
                    log.info("传入wms的供应商信息失败，传入参数是[{}]", JsonUtil.toJson(supplierWms));
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info("传入wms的供应商信息失败，传入参数是[{}]", JsonUtil.toJson(supplierWms));
            }

            // 执行完 改变商品状态
            productSkuPushWmsMapper.updateWmsStatusBySupplyCode(supplyCode);
        }
        return HttpResponse.success();
    }
}
