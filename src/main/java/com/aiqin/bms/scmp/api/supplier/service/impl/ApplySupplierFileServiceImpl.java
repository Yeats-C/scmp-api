package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.ApplySupplierFileDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplierDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplierFileDao;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierFile;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.SupplierFileReqDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.SupplierFileReqVO;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplierFileMapper;
import com.aiqin.bms.scmp.api.supplier.service.ApplySupplierFileService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @功能说明:供应商文件service实现
 * @author: wangxu
 * @date: 2018/12/18 0018 11:16
 */
@Service
public class ApplySupplierFileServiceImpl implements ApplySupplierFileService {
    @Autowired
    private ApplySupplierFileDao applySupplierFileDao;
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private SupplierFileDao supplierFileDao;
    @Autowired
    private SupplierFileMapper supplierFileMapper;
    @Autowired
    private SupplierCommonService supplierCommonService;
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int copySaveInfo(List<SupplierFileReqVO> supplierFileReqVOS) {
        try {
            List<SupplierFileReqDTO> supplierFileReqDTOS = new ArrayList<>();
            for (int i=0;i<supplierFileReqVOS.size();i++){
                SupplierFileReqDTO supplierFileReqDTO = new SupplierFileReqDTO();
                //排除掉文件名称为空或路径为空的数据
                if(StringUtils.isBlank(supplierFileReqVOS.get(i).getFileName()) ||
                        StringUtils.isBlank(supplierFileReqVOS.get(i).getFilePath())){
                    continue;
                }
                BeanCopyUtils.copy(supplierFileReqVOS.get(i),supplierFileReqDTO);
                supplierFileReqDTOS.add(supplierFileReqDTO);
            }
            if(CollectionUtils.isNotEmptyCollection(supplierFileReqDTOS)){
                return ((ApplySupplierFileService) AopContext.currentProxy()).insertList(supplierFileReqDTOS);
            }
            return 0;
        } catch (GroundRuntimeException e){
            return 0;
        }
    }

    @Override
    @SaveList
    public int insertList(List<SupplierFileReqDTO> supplierFileReqDTOS) {
        int num;
        try {
            num = applySupplierFileDao.insertList(supplierFileReqDTOS);
            return num;
        } catch (GroundRuntimeException e){
            throw new BizException(ResultCode.ADD_ERROR);
        }
    }


    @Override
    @Transactional(rollbackFor = BizException.class)
    @Save
    public int insert(SupplierFile supplierFile) {
        return supplierFileDao.insert(supplierFile);
    }

    @Override
    public List<SupplierFileReqVO> selectByApplyCode(String oldApplyCode) {
        return supplierFileDao.selectByApplyCode(oldApplyCode);
    }
}
