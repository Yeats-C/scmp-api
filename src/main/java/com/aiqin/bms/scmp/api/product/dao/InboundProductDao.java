package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.pojo.InboundProduct;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.ReturnInboundProduct;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.InboundProductWmsReqVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InboundProductDao {


    int deleteByPrimaryKey(Long id);

    int insert(InboundProduct record);

    int insertSelective(InboundProduct record);

    /**
     * 通过入库单编码查询sku
     * @param inboundOderCode
     * @return
     */
    List<InboundProduct> selectByInboundOderCode(String inboundOderCode);

    InboundProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InboundProduct record);

    int updateByPrimaryKey(InboundProduct record);

    /**
     * sku 批量插入
     * @param inboundProducts
     * @return
     */
    int insertBatch(List<InboundProduct> inboundProducts);


    List<InboundProduct> selectInboundProductListByInboundOderCodeList(@Param("inBoundOderCodeList") List<String> inboundOderCodeList);


    /**
     * 通过入库单编码查询sku
     * @param inboundOderCode
     * @return
     */
    List<InboundProductWmsReqVO> selectMmsReqByInboundOderCode(String inboundOderCode);

    ReturnInboundProduct selectByLinenum(@Param("inboundOderCode") String inboundOderCode, @Param("skuCode") String skuCode, @Param("linenum") Long linenum);

}