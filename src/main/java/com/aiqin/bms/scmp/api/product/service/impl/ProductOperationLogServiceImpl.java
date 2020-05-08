package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.Update;
import com.aiqin.bms.scmp.api.product.dao.ProductOperationLogDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductOperationLog;
import com.aiqin.bms.scmp.api.product.domain.request.OperationLogBean;
import com.aiqin.bms.scmp.api.product.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.product.domain.response.LogData;
import com.aiqin.bms.scmp.api.product.service.ProductOperationLogService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductOperationLogServiceImpl implements ProductOperationLogService {
    @Autowired
    private ProductOperationLogDao productOperationLogDao;

    @Override
    @Async("myTaskAsyncPool")
    public Long saveLog(OperationLogBean operationLogBean) {
        ProductOperationLog supplierOperationLog = new ProductOperationLog();
        try {
            if (operationLogBean != null) {
                BeanCopyUtils.copy(operationLogBean, supplierOperationLog);
                return insert(supplierOperationLog);
            } else {
                log.error("operationLogBean=>不能为空");
                throw new GroundRuntimeException("operationLogBean 不能为空");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @Override
    @Save
    public Long insert(ProductOperationLog operationLog) {
        int k = productOperationLogDao.insert(operationLog);
        if (k > 0) {
            return operationLog.getId();
        } else {
            log.error("插入日志错误");
            throw new GroundRuntimeException("插入日志错误");
        }
    }

    @Override
    @Update
    public int update(ProductOperationLog operationLog) {
        int k = productOperationLogDao.updateByPrimaryKeySelective(operationLog);
        if (k > 0) {
            return k;
        } else {
            log.error("更新日志错误");
            throw new GroundRuntimeException("更新日志错误");
        }
    }

    @Override
    @Async("myTaskAsyncPool")
    public Integer saveList(Collection<ProductOperationLog> users) {
        Integer k = productOperationLogDao.insertList(users);
        if (k!=null) {
            return k;
        } else {
            log.error("批量插入失败");
            throw new GroundRuntimeException("批量插入失败");
        }
    }

    @Override
    public List<LogData> getLogType(OperationLogVo objectType) {
        try {
            PageHelper.startPage(objectType.getPageNo(), objectType.getPageSize());
            List<LogData> objectTypes = productOperationLogDao.getLogType(objectType.getObjectType(),objectType.getObjectId());
            return objectTypes;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GroundRuntimeException(ex.getMessage());
        }
    }
    @Override
    public Map<String, Object> list(Integer page, Integer count) {
        try {
            if (page == null) {
                page = 0;
            }
            if (count == null || count == 0) {
                count = 10;
            }
            PageHelper.startPage(page, count);
            List<LogData> userDetails = productOperationLogDao.getList();
            return PageUtil.startPage(page, userDetails);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GroundRuntimeException(ex.getMessage());
        }
    }
}
