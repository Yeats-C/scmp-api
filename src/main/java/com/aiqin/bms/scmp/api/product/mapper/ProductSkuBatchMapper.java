package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.request.ProductSkuBatchReq;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QueryProductSkuBatchReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuBatchRespVo;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2020-04-15 18:15
 * @Description:
 */
public interface ProductSkuBatchMapper {

     List<ProductSkuBatchRespVo> getList(QueryProductSkuBatchReqVO queryProductSkuBatchReqVO) ;

    void banById(Long id);

    void inserts(List<ProductSkuBatchReq> productSkuBatchReqList);
}
