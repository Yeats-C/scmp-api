package com.aiqin.bms.scmp.api.supplier.service.impl;


import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplierFileDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyAccountDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierFile;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.SupplyCompanyDetailDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplierComAcctReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplyComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.SupplierFileReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.domain.response.sku.QueryProductSkuListResp;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.DetailTagUseRespVo;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplyCompanyMapper;
import com.aiqin.bms.scmp.api.supplier.service.*;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
//    @Autowired
//    private SkuInfoService skuInfoService;
    @Autowired
    private DeliveryInfoService deliveryInfoService;
    @Autowired
    private TagInfoService tagInfoService;
    @Override
    public BasePage<SupplyComListRespVO> getSupplyCompanyInfoList(QuerySupplyComReqVO querySupplyComReqVO) {
        try {
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                querySupplyComReqVO.setCompanyCode(authToken.getCompanyCode());
            }
            PageHelper.startPage(querySupplyComReqVO.getPageNo(), querySupplyComReqVO.getPageSize());
            List<SupplyComListRespVO> supplyComListRespVOS = supplyCompanyDao.getSupplyCompanyList(querySupplyComReqVO);
            return PageUtil.getPageList(querySupplyComReqVO.getPageNo(),supplyComListRespVOS);
        } catch (Exception e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }

    @Override
    public List<SupplyComListRespVO> getAllSupplyComList() {
        QuerySupplyComReqVO querySupplyComReqVO = new QuerySupplyComReqVO();
        try {
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                querySupplyComReqVO.setCompanyCode(authToken.getCompanyCode());
            }
            querySupplyComReqVO.setEnable(StatusTypeCode.EN_ABLE.getStatus().toString());
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
                List<DetailTagUseRespVo> useTagRecordReqVos = tagInfoService.getUseTagRecordByUseObjectCode(supplyCompanyDetailDTO.getSupplyCode(), TagTypeCode.SUPPLIER.getStatus());
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
                if (Objects.equals(StatusTypeCode.SHOW_ACCOUNT_SKU,statusTypeCode)) {
                    QuerySupplierComAcctReqVo vo = new QuerySupplierComAcctReqVo();
                    vo.setSupplyCompanyCode(supplyComDetailRespVO.getApplySupplyCode());
                    List<QuerySupplierComAcctRespVo> querySupplierComAcctRespVos = supplyCompanyAccountDao.selectListByQueryVO(vo);
                    supplyComDetailRespVO.setSupplierComAcctRespVos(querySupplierComAcctRespVos);
                    List<QueryProductSkuListResp> queryProductSkuListResps = null;
                    try {
                        //queryProductSkuListResps = skuInfoService.querySkuListBySupplyUnitCode(supplyComDetailRespVO.getApplySupplyCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.info("查询SKU信息失败,失败原因[{}]",e.getMessage());
                    }
                    supplyComDetailRespVO.setSkuListRespVos(queryProductSkuListResps);
                }
            } else {
                throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"未查询信息"));
            }
        } catch (GroundRuntimeException e) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
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
        return supplyCompanyMapper.updateStarScore(supplierCode,starScore);
    }
}
