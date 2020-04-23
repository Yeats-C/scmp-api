package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.request.WarehouseConfigReq;
import com.aiqin.bms.scmp.api.product.domain.response.WarehouseConfigResp;
import com.github.pagehelper.PageInfo;

/**
 * @Auther: mamingze
 * @Date: 2020-04-09 17:39
 * @Description:
 */
public interface WarehouseConfigService {


    BasePage<WarehouseConfigResp> getPage(WarehouseConfigReq warehouseConfigReq);

    Boolean save(WarehouseConfigReq warehouseConfigReq);

    WarehouseConfigResp load(Long id);

    Boolean update(WarehouseConfigReq warehouseConfigReq);

    WarehouseConfigResp refresh(String stock_code);
}
