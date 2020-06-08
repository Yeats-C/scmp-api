package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapOrderRequest;
import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundProduct;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.ReturnInboundProduct;
import com.aiqin.bms.scmp.api.product.domain.response.wms.PurchaseInboundDetailSource;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InboundProductDao {

    int deleteByPrimaryKey(Long id);

    int insert(InboundProduct record);

    int insertSelective(InboundProduct record);

    /**
     * 通过入库单编码查询sku
     */
    List<InboundProduct> selectByInboundOderCode(String inboundOderCode);

    InboundProduct selectByPrimaryKey(Long id);

    Integer update(InboundProduct record);

    /**
     * sku 批量插入
     */
    int insertBatch(List<InboundProduct> inboundProducts);

    List<InboundProduct> selectInboundProductListByInboundOderCodeList(@Param("inBoundOderCodeList") List<String> inboundOderCodeList);

    InboundProduct inboundByLineCode(@Param("inboundOderCode") String inboundOderCode, @Param("skuCode") String skuCode, @Param("linenum") Long linenum);

    List<PurchaseApplyDetailResponse> selectPurchaseInfoByPurchaseNum(Inbound inbound);

    Integer countPurchaseInfoByPurchaseNum(Inbound inbound);

    List<ReturnInboundProduct> selectTax(@Param("inboundOderCode") String inboundOderCode, @Param("skuCode") String skuCode);

    List<InboundProduct> listDetailForSap(SapOrderRequest sapOrderRequest);

    List<PurchaseInboundDetailSource> wmsByInboundProduct(String inboundOderCode);

}