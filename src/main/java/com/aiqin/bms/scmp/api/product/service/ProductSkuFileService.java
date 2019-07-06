package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuFile;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFile;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFileDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuFileRespVO;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:39
 */
public interface ProductSkuFileService {

    int insertDraftList(List<ProductSkuFileDraft> productSkuFileDrafts);

    int insertList(List<ProductSkuFile> productSkuFiles);

    int insertApplyList(List<ApplyProductSkuFile> applyProductSkuFiles);

    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    int saveList(String skuCode, String applyCode);

    /**
     * 获取临时数据
     * @param skuCode
     * @return
     */
    List<ProductSkuFileRespVO> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);

    /**
     *
     * 功能描述: 获取申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:19
     */
    List<ProductSkuFileRespVO> getApply(String skuCode, String applyCode);
}
