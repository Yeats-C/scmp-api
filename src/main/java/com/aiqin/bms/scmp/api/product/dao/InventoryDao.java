package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.*;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorQuVo;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorQuVoPage;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorVo;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.ProductDistributorReVo;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.ProductDistributorReVoPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wuyongqiang
 * @description 库存管理持久化接口
 * @date 2018/11/20 13:43
 */
public interface InventoryDao {
    /**
     * 获取库存数量（未分页）
     *
     * @param param 请求参数
     * @return 库存数量
     */
    int getInventoryCount(InventoryRequest param);

    /**
     * 获取库存列表（分页）
     *
     * @param param 请求参数
     * @return 库存列表
     */
    List<Inventory> getInventories(InventoryRequest param);

    /**
     * 获取库存分布数量（未分页）
     *
     * @param param 请求参数
     * @return 库存分布数量
     */
    int getInventoryDistributionCount(InventoryDistributionRequest param);

    /**
     * 获取库存分布列表（分页）
     *
     * @param param 请求参数
     * @return 库存分布列表
     */
    List<InventoryDistribution> getInventoryDistributions(InventoryDistributionRequest param);

    /**
     * 批量设置库存预警
     *
     * @param param 设置参数
     */
    void updateInventoryWarning(InventoryWarningRequest param);

    /**
     * 获取库存出入库记录数量（未分页）
     *
     * @param param 请求参数
     * @return 库存出入库记录数量
     */
    int getInventoryAccountCount(InventoryAccountRequest param);

    /**
     * 获取库存出入库记录列表（分页）
     *
     * @param param 请求参数
     * @return 库存出入库记录列表
     */
    List<InventoryAccount> getInventoryAccounts(InventoryAccountRequest param);

    /**
     * 获取库存出入库记录明细列表
     *
     * @param masterNumber 出/入库单号
     * @return 库存出入库记录明细列表
     */
    List<InventoryAccount> getInventoryAccountDetails(String masterNumber);

    /**
     * 更新库存数量
     *
     * @param param 更新参数
     */
    void updateInventoryNumber(InventoryRecordRequest param);

    /**
     * 插入库存操作记录
     *
     * @param param 库存记录参数
     */
    void insertInventoryRecord(InventoryRecordRequest param);

    Boolean addProductDistributor(ProductDistributor params);

    ProductDistributor getSelectByCodeAndId(@Param("distributorId") String distributorId, @Param("productCode") String productCode);

    Boolean updateByCodeAndId(ProductDistributor param);

    List<ProductDistributor> getByidslu(ProductDistributorVo vo);

    Boolean insertInventoryRecordList(@Param("list") List<InventoryRecordRequest> kcList);

    List<ProductDistributorReVo> getPtDiReVo(ProductDistributorQuVo vo);

    List<ProductDistributorReVoPage> getPtDiReVoPage(ProductDistributorQuVoPage vo);

    Integer getPtDiReVoPageCount(ProductDistributorQuVoPage vo);

    /**
     * 获取畅缺商品信息
     *
     * @param vo
     * @return
     */
    List<ProductDistributorOrder> queryProductDistributorOrder(ProductDistributorOrderRequest vo);
}
