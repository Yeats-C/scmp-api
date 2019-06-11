package com.aiqin.bms.scmp.api.product.service.api;

import com.aiqin.bms.scmp.api.product.domain.request.WarehouseListReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.LogisticsCenterApiResVo;
import com.aiqin.bms.scmp.api.product.domain.response.warehouse.WarehouseResVo;

import java.util.List;

/**
 * Description:
 * 供应链api
 * @author: zth
 * @date: 2019-03-13
 * @time: 17:40
 */
public interface SupplierApiService {
    /**
     * 通过code获取库房详情
     * @param code
     * @return
     */
     WarehouseResVo getWarehouseByCode(String code);

    /**
     * 根据地区编码查询库房信息
     * @author zth
     * @date 2019/3/16
     */
     List<LogisticsCenterApiResVo> getWarehouseApi(WarehouseListReqVo warehouseListReqVo);
}
