package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPriceInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.request.price.QueryProductSkuPriceInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.price.SkuPriceDraftReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.QueryProductSkuPriceInfoRespVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 * 价格service
 * @author: NullPointException
 * @date: 2019-05-30
 * @time: 10:26
 */
public interface ProductSkuPriceInfoService {
    /**
     * 查询价格列表
     * @author NullPointException
     * @date 2019/5/30
     * @param reqVO
     * @return com.aiqin.mgs.product.api.base.BasePage<com.aiqin.mgs.product.api.domain.response.price.QueryProductSkuPriceInfoRespVO>
     */
    BasePage<QueryProductSkuPriceInfoRespVO> list(QueryProductSkuPriceInfoReqVO reqVO);
    /**
     * 价格查看
     * @author NullPointException
     * @date 2019/5/30
     * @param code
     * @return com.aiqin.mgs.product.api.domain.response.price.ProductSkuPriceInfoRespVO
     */
    ProductSkuPriceInfoRespVO view(String code);

    Boolean saveSkuPriceDraft(List<SkuPriceDraftReqVO> reqVOList);

    List<ProductSkuPriceInfoDraft> getSkuPriceListDraftBySkuCodes(List<String> skuCode);

    List<ApplyProductSkuPriceInfo> getSkuPriceListApplyBySkuCodes(List<String> skuCode);

    Boolean deleteSkuPriceDraft(List<String> SkuCode);

    Boolean saveSkuPriceApply(List<ApplyProductSkuPriceInfo> applyList);

    @Transactional(rollbackFor = Exception.class)
    Boolean saveSkuPriceOfficial(List<ProductSkuPriceInfo> list);
}
