package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.service.BaseService;
import com.aiqin.bms.scmp.api.product.domain.pojo.SupervisoryWarehouseOrder;
import com.aiqin.bms.scmp.api.product.domain.pojo.SupervisoryWarehouseOrderProduct;
import com.aiqin.bms.scmp.api.product.domain.request.supervisory.SaveSupervisoryWarehouseOrderReqVo;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className SupervisoryWarehouseOrderService
 * @date 2019/6/29 15:26
 * @description TODO
 */
public interface SupervisoryWarehouseOrderService extends BaseService {

    /**
     *
     * 功能描述: 保存
     *
     * @param reqVo
     * @return
     * @auther knight.xie
     * @date 2019/6/29 18:02
     */
    Integer save(SaveSupervisoryWarehouseOrderReqVo reqVo);

    /**
     *
     * 功能描述: 插入数据库
     *
     * @param record
     * @return
     * @auther knight.xie
     * @date 2019/6/29 18:16
     */
    int insert(SupervisoryWarehouseOrder record);

    /**
     *
     * 功能描述: 批量插入商品
     *
     * @param records
     * @return
     * @auther knight.xie
     * @date 2019/6/29 18:18
     */
    int insertBatchProduct(List<SupervisoryWarehouseOrderProduct> records);

}
