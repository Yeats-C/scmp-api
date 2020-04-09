package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.request.WarehouseConfigReq;
import com.aiqin.bms.scmp.api.product.domain.response.WarehouseConfigResp;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2020-04-09 17:57
 * @Description:
 */
@Mapper
public interface WarehouseConfigDao {

    List<WarehouseConfigResp> getList(WarehouseConfigReq warehouseConfigReq);



    void insert(WarehouseConfigReq warehouseConfigReq);

    WarehouseConfigResp load(Long id);

    void updateById(WarehouseConfigReq warehouseConfigReq);
}
