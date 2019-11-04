package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPriceInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.request.price.QueryProductSkuPriceInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.price.SkuPriceDraftReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.QueryProductSkuPriceInfoRespVO;
import io.swagger.models.auth.In;

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
    /**
     * 保存价格临时表数据
     * @author NullPointException
     * @date 2019/6/17
     * @param reqVOList
     * @return java.lang.Boolean
     */
    Boolean saveSkuPriceDraft(List<SkuPriceDraftReqVO> reqVOList);
    Integer saveSkuPriceDraft(String applyCode);
    /**
     * 查询临时表价格
     * @author NullPointException
     * @date 2019/6/17
     * @param skuCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfoDraft>
     */
    List<ProductSkuPriceInfoDraft> getSkuPriceListDraftBySkuCodes(List<String> skuCode);
    /**
     * 查询申请编价格
     * @author NullPointException
     * @date 2019/6/17
     * @param skuCode
     * @param applyCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfoDraft>
     */
    List<ApplyProductSkuPriceInfo> getSkuPriceListApplyBySkuCodes(List<String> skuCode,String applyCode);
    /**
     * 删除临时表
     * @author NullPointException
     * @date 2019/6/17
     * @param SkuCode
     * @return java.lang.Boolean
     */
    Boolean deleteSkuPriceDraft(List<String> SkuCode);
    /**
     * 保存申请表数据
     * @author NullPointException
     * @date 2019/6/17
     * @param applyList
     * @return java.lang.Boolean
     */
    Boolean saveSkuPriceApply(List<ApplyProductSkuPriceInfo> applyList);
    /**
     * 保存正式表数据
     * @author NullPointException
     * @date 2019/6/17
     * @param list
     * @return java.lang.Boolean
     */
    Boolean saveSkuPriceOfficial(List<ProductSkuPriceInfo> list);
    /**
     * 正式查看价格
     * @author NullPointException
     * @date 2019/7/9
     * @param skuCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.response.price.SkuPriceRespVO>
     */
    List<ProductSkuPriceRespVo> getSkuPriceBySkuCodeForOfficial(String skuCode);
    /**
     * 申请查看价格
     * @author NullPointException
     * @date 2019/7/9
     * @param skuCode
     * @param applyCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.response.price.SkuPriceRespVO>
     */
    List<ProductSkuPriceRespVo> getSkuPriceBySkuCodeForApply(String skuCode, String applyCode);
    /**
     * 临时表
     * @author NullPointException
     * @date 2019/7/9
     * @param skuCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceRespVo>
     */
    List<ProductSkuPriceRespVo> getSkuPriceBySkuCodeForDraft(String skuCode);
}
