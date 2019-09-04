package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPictures;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPictures;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicturesDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuPicturesRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/28 0028 15:50
 */
public interface ProductSkuPicturesDao {
    /**
     * 批量新增sku图片介绍草稿信息
     * @param productSkuPicturesDrafts
     * @return
     */
    int insertDraftList(List<ProductSkuPicturesDraft> productSkuPicturesDrafts);

    int insertList(List<ProductSkuPictures> productSkuPictures);

    int insertApplyList(List<ApplyProductSkuPictures> applyProductSkuPictures);

    List<ProductSkuPicturesRespVo> getDraftInfo(String skuCode);

    List<ProductSkuPicturesRespVo> getRespInfoBySkuCode(String skuCode);

    List<ProductSkuPictures> getInfo(String skuCode);

    List<ProductSkuPicturesDraft> getDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteList(String skuCode);

    List<ApplyProductSkuPictures> getApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    List<ProductSkuPicturesRespVo> getApplys(@Param("skuCode") String skuCode, @Param("applyCode")String applyCode);

    ProductSkuPictures getPicInfoBySkuCode(String skuCode);

    List<ProductSkuPictures> listBySkuCodes(@Param("list")List<String> skuCodes);
}
