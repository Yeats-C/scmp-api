package com.aiqin.bms.scmp.api.product.domain.trans;

import com.aiqin.bms.scmp.api.product.domain.request.ILockStockReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.QueryStockSkuReqVo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ILockStockReqVoToQueryStockSkuReqVo
 * @date 2019/1/9 14:11
 * @description TODO
 */
public class ILockStockReqVoToQueryStockSkuReqVo implements Function<ILockStockReqVO, QueryStockSkuReqVo> {


    @Nullable
    @Override
    public QueryStockSkuReqVo apply(@Nullable ILockStockReqVO iLockStockReqVO) {
        QueryStockSkuReqVo reqVo = new QueryStockSkuReqVo();
        reqVo.setCompanyCode(iLockStockReqVO.getCompanyCode());
        reqVo.setSupplyCode(iLockStockReqVO.getSupplyCode());
        reqVo.setTransportCenterCode(iLockStockReqVO.getTransportCenterCode());
        reqVo.setWarehouseCode(iLockStockReqVO.getWarehouseCode());
        reqVo.setPurchaseGroupCode(iLockStockReqVO.getPurchaseGroupCode());
        List<String> skuList = Lists.newLinkedList();
        iLockStockReqVO.getItemReqVos().forEach(item->{
            skuList.add(item.getSkuCode());
        });
        reqVo.setSkuList(skuList);
        return reqVo;
    }
}
