package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuCheckout;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckout;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckoutDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuCheckoutRespVo;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 14:43
 */
public interface ProductSkuCheckoutService {

    int insertDraft(ProductSkuCheckoutDraft productSkuCheckoutDraft);

    /**
     * 根据申请sku集合保存对应的结算申请信息
     * @param productSkus
     * @return
     */
    int saveApply(List<ApplyProductSku> productSkus);

    /**
     * 批量新增申请信息
     * @param applyProductSkuCheckouts
     * @return
     */
    int insertApply(List<ApplyProductSkuCheckout> applyProductSkuCheckouts);

    /**
     * 传入申请sku列表，新增对应正式数据
     * @param skuCode
     * @return
     */
    int saveInfo(String skuCode, String applyCode);

    /**
     * 批量新增正式结算信息
     * @param productSkuCheckouts
     * @return
     */
    int insertList(List<ProductSkuCheckout> productSkuCheckouts);

    int insert(ProductSkuCheckout productSkuCheckout);

    int update(ProductSkuCheckout productSkuCheckout);

    /**
     * 获取临时结算信息
     * @param skuCode
     * @return
     */
    ProductSkuCheckoutRespVo getDraft(String skuCode);

    /**
     * 删除临时表信息
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);

    /**
     *
     * 功能描述: 根据skuCode编码查询正式结算信息
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 17:00
     */
    ProductSkuCheckoutRespVo getBySkuCode(String skuCode);

    /**
     *
     * 功能描述: 获取申请结算信息
     *
     * @param skuCode
     * @param applyCode
     * @return 
     * @auther knight.xie
     * @date 2019/7/6 22:52
     */
    ProductSkuCheckoutRespVo getApply(String skuCode, String applyCode);
}
