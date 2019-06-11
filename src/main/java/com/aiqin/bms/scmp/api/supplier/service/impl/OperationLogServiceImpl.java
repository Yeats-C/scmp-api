package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.UrlConfig;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.common.Update;
import com.aiqin.bms.scmp.api.supplier.dao.SupplierOperationLogDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierOperationLog;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogBean;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplierOperationLogMapper;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
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

@Slf4j
@Service
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    private SupplierOperationLogDao supplierOperationLogDao;
    @Autowired
    private SupplierOperationLogMapper supplierOperationLogMapper;
    @Autowired
    private UrlConfig urlConfig;

    @Override
    @Async("myTaskAsyncPool")
    public Long saveLog(OperationLogBean operationLogBean) {
        SupplierOperationLog supplierOperationLog = new SupplierOperationLog();
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
    public Long insert(SupplierOperationLog operationLog) {
        int k = supplierOperationLogDao.insert(operationLog);
        if (k > 0) {
            return operationLog.getId();
        } else {
            log.error("插入日志错误");
            throw new GroundRuntimeException("插入日志错误");
        }
    }

    @Override
    @Update
    public int update(SupplierOperationLog operationLog) {
        int k = supplierOperationLogMapper.updateByPrimaryKeySelective(operationLog);
        if (k > 0) {
            return k;
        } else {
            log.error("更新日志错误");
            throw new GroundRuntimeException("更新日志错误");
        }
    }

    @Override
    @SaveList
    @Async("myTaskAsyncPool")
    public Integer inserList(Collection<SupplierOperationLog> users) {
        int k = supplierOperationLogDao.insertList(users);
        if (k > 0) {
            return k;
        } else {
            log.error("批量插入失败");
            throw new GroundRuntimeException("批量插入失败");
        }
    }

    @Override
    public List<LogData> selectListByVO(OperationLogBean operationLogBean) {
        if (operationLogBean != null) {
            return supplierOperationLogDao.getLogList(operationLogBean);
        } else {
            log.error("operationLogBean=>不能为空");
            throw new GroundRuntimeException("operationLogBean 不能为空");
        }
    }

    @Override
    public BasePage<LogData> getLogType(OperationLogVo operationLogVo, Integer project) {
        try {
            PageHelper.startPage(operationLogVo.getPageNo(), operationLogVo.getPageSize());
           // if (ServiceType.SUPPLIER_API.equals(project)) {
                List<LogData> objectTypes = supplierOperationLogDao.getLogType(operationLogVo.getObjectType(),operationLogVo.getObjectId());
                return PageUtil.getPageList(operationLogVo.getPageNo(), objectTypes);
            //}
//            else {
//                StringBuilder sb = new StringBuilder();
//                sb.append(urlConfig.PRODUCT_API_URL).append("/com.aiqin.bms.scmp.api/operation/objectType");
//                HttpClient orderOperationClient = HttpClientHelper.getCurrentClientEx(HttpClient.post(sb.toString()).json(operationLogVo));
//                HttpResponse orderDto = orderOperationClient.action().result(HttpResponse.class);
//                List<LogData> logData = (List<LogData>) orderDto.getData();
//                return PageUtil.getPageList(operationLogVo.getPageNo(), logData);
//            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @Override
    public BasePage<LogData> list(Integer page, Integer count) {
        try {
            if (page == null) {
                page = 1;
            }
            if (count == null || count == 0) {
                count = 10;
            }
            PageHelper.startPage(page, count);
            List<LogData> userDetails = supplierOperationLogDao.getList();
            return PageUtil.getPageList(page, userDetails);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GroundRuntimeException(ex.getMessage());
        }
    }


}
