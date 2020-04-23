package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.dao.WarehouseConfigDao;
import com.aiqin.bms.scmp.api.product.domain.request.WarehouseConfigReq;
import com.aiqin.bms.scmp.api.product.domain.response.WarehouseConfigResp;
import com.aiqin.bms.scmp.api.product.service.WarehouseConfigService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2020-04-09 17:39
 * @Description:
 */
@Service
@Slf4j
public class WarehouseConfigServiceImpl implements WarehouseConfigService {
    @Autowired
    private WarehouseConfigDao warehouseConfigDao;
    @Override
    public BasePage<WarehouseConfigResp> getPage(WarehouseConfigReq warehouseConfigReq) {
        try {
            PageHelper.startPage(warehouseConfigReq.getPageNo(),warehouseConfigReq.getPageSize());
            List<WarehouseConfigResp> warehouseConfigRespPageInfo=warehouseConfigDao.getList(warehouseConfigReq);
            return PageUtil.getPageList(warehouseConfigReq.getPageNo(),warehouseConfigRespPageInfo);
        } catch (Exception e) {
            log.error("查询仓库配置失败");
            e.printStackTrace();
            throw new GroundRuntimeException(e.getMessage());

        }
    }

    @Override
    public Boolean save(WarehouseConfigReq warehouseConfigReq) {

        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        warehouseConfigReq.setCreateBy(currentAuthToken.getPersonName());
                try {
           warehouseConfigDao.insert(warehouseConfigReq);
            return true;
        } catch (Exception e) {
            log.error("新增仓库配置失败");
            e.printStackTrace();
            throw new GroundRuntimeException(e.getMessage());

        }
    }

    @Override
    public WarehouseConfigResp load(Long id) {
        try {
            return warehouseConfigDao.load(id);
        } catch (Exception e) {
            log.error("查询仓库配置失败");
            e.printStackTrace();
            throw new GroundRuntimeException(e.getMessage());

        }
    }

    @Override
    public Boolean update(WarehouseConfigReq req) {
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        req.setUpdateBy(currentAuthToken.getPersonName());
        try {
            warehouseConfigDao.updateById(req);
            return true;
        } catch (Exception e) {
            log.error("修改仓库配置失败");
            e.printStackTrace();
            throw new GroundRuntimeException(e.getMessage());

        }
    }

    @Override
    public WarehouseConfigResp refresh(String stock_code) {
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();;
        try {
           return warehouseConfigDao.refresh(stock_code);

        } catch (Exception e) {
            log.error("修改仓库配置失败");
            e.printStackTrace();
            throw new GroundRuntimeException(e.getMessage());
        }
    }
}
