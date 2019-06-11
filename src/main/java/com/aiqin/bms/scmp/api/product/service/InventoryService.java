package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.domain.*;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorQuVo;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorQuVoPage;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorVo;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.ProductDistributorReVo;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.ProductDistributorReVoPage;

import java.util.List;

/**
 * @author wuyongqiang
 * @description 库存管理业务操作接口
 * @date 2018/11/20 15:07
 */
public interface InventoryService {
    /**
     * 库存查询
     *
     * @param param 查询参数
     * @return 库存信息分页数据
     */
    PageResData<Inventory> searchInventories(InventoryRequest param);

    /**
     * 获取仓库
     *
     * @param param
     * @return
     */
    PageResData<InventoryWarehouse> getInventoryWarehouse(String distributorId, String distributorName);

    /**
     * 库存分布查询
     *
     * @param param 查询参数
     * @return 库存分布分页数据
     */
    PageResData<InventoryDistribution> searchInventoryDistributions(InventoryDistributionRequest param);

    /**
     * 批量设置库存预警
     *
     * @param param 设置参数
     */
    void updateInventoryWarning(InventoryWarningRequest param);

    /**
     * 库存出入库记录查询
     *
     * @param param 查询参数
     * @return 库存出入库记录分页数据
     */
    PageResData<InventoryAccount> searchInventoryAccounts(InventoryAccountRequest param);

    /**
     * 查询库存出入库记录明细
     *
     * @param masterNumber 出/入库单号
     * @return 库存出入库记录明细
     */
    InventoryAccountDetail searchInventoryAccountDetails(String masterNumber);

    /**
     * 更新库存记录
     *
     * @param params 更新参数
     */
    void updateInventoryRecord(List<InventoryRecordRequest> params);


    Boolean addProductDistributor(List<ProductDistributor> params);

    List<ProductDistributor> getByidslu(ProductDistributorVo vo);

    List<ProductDistributorReVo> getPtDiReVo(ProductDistributorQuVo vo);

    PageResData<ProductDistributorReVoPage> getPtDiReVoPage(ProductDistributorQuVoPage vo);

    /**
     * 获取畅缺商品信息
     *
     * @param vo
     * @return
     */
    List<ProductDistributorOrder> getProductDistributorOrder(ProductDistributorOrderRequest vo);
}
