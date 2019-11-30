package com.aiqin.bms.scmp.api.supplier.service.impl;


import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplierDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplierFileDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.Supplier;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.DownSupplierFileReq;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplierReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplierDetailRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplierListRespVO;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplierMapper;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierService;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 11:25
 */
@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private SupplierFileDao supplierFileDao;
    @Autowired
    private SupplierCommonService supplierCommonService;
    @Autowired
    private UploadFileUtil uploadFileUtil;


    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public BasePage<SupplierListRespVO> getSupplierList(QuerySupplierReqVO querySupplierReqVO) {
        try {
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                querySupplierReqVO.setCompanyCode(authToken.getCompanyCode());
            }
            PageHelper.startPage(querySupplierReqVO.getPageNo(), querySupplierReqVO.getPageSize());
            List<SupplierListRespVO> supplierResps = supplierDao.getSupplierList(querySupplierReqVO);
            return PageUtil.getPageList(querySupplierReqVO.getPageNo(),supplierResps);
        } catch (Exception ex) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,ex.getMessage()));
        }
    }



    @Override
    @Transactional(rollbackFor = BizException.class)
    @Update
    public int update(Supplier supplier) {
        try {
            int num = supplierMapper.updateByPrimaryKeySelective(supplier);
            if (num > 0){
                return num;
            } else {
                throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"修改失败"));
            }
        } catch (GroundRuntimeException e){
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }

    @Override
    public SupplierDetailRespVO getSupplierDetail(Long id) {

            SupplierDetailRespVO supplierDetailRespVO = new SupplierDetailRespVO();
            Supplier supplier = supplierMapper.selectByPrimaryKey(id);
            if (null  == supplier){
                throw new GroundRuntimeException("没有找到对应的信息");
            }
            BeanCopyUtils.copy(supplier,supplierDetailRespVO);
            //获取操作日志
            OperationLogVo operationLogVo = new OperationLogVo();
            operationLogVo.setPageNo(1);
            operationLogVo.setPageSize(100);
            operationLogVo.setObjectType(ObjectTypeCode.SUPPLIER.getStatus());
            operationLogVo.setObjectId(supplier.getSupplierCode());
            BasePage<LogData> pageList = operationLogService.getLogType(operationLogVo,62);
            List<LogData> logDataList = new ArrayList<>();
            if (CollectionUtils.isNotEmptyCollection(pageList.getDataList())){
                logDataList = pageList.getDataList();
            }
            supplierDetailRespVO.setLogDataList(logDataList);
            return supplierDetailRespVO;

    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    @Save
    public int insert(Supplier supplier) {
        try {
            int num = supplierMapper.insertSelective(supplier);
            if (num > 0){
                return num;
            } else {
                throw new BizException(MessageId.create(Project.SUPPLIER_API,41,"新增失败"));
            }
        } catch (GroundRuntimeException e){
            throw new BizException(MessageId.create(Project.SUPPLIER_API,41,e.getMessage()));
        }
    }

    @Override
    public String addSupplierFileDownLog(DownSupplierFileReq downSupplierFileReq) {
        try {
            String image = uploadFileUtil.downImage(downSupplierFileReq.getFilePath());
            supplierCommonService.getInstance(downSupplierFileReq.getSupplierCode(), HandleTypeCoce.DOWNLOAD.getStatus(), ObjectTypeCode.SUPPLY_COMPANY.getStatus(),HandleTypeCoce.DOWNLOAD_REVOKE_SUPPLIER.getName(),null ,HandleTypeCoce.DOWNLOAD.getName());
            return image;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            querySupplierReqVO.setCompanyCode(authToken.getCompanyCode());
        }
        return supplierDao.queryApplyList(querySupplierReqVO);
    }


}
