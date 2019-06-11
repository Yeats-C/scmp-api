package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPictures;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPictures;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicturesDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuPicturesRespVo;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:05
 */
public interface ProductSkuPicturesService {
    int insertDraftList(List<ProductSkuPicturesDraft> productSkuPicturesDrafts);

    int insertList(List<ProductSkuPictures> productSkuPictures);

    int saveList(String skuCode, String applyCode);

    int saveApplyList(List<ApplyProductSku> productSkus);

    int insertApplyList(List<ApplyProductSkuPictures> applyProductSkuPictures);

    /**
     * 临时表数据
     * @param skuCode
     * @return
     */
    List<ProductSkuPicturesRespVo> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);
}
