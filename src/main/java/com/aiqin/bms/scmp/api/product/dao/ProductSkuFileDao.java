package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuFile;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFile;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFileDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuFileRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 10:18
 */
public interface ProductSkuFileDao {
    /**
     * 批量新增草稿
     * @param productSkuFileDrafts
     * @return
     */
    int insertDraftList(List<ProductSkuFileDraft> productSkuFileDrafts);

    int insertList(List<ProductSkuFile> productSkuFiles);

    int insertApplyList(List<ApplyProductSkuFile> applyProductSkuFiles);

    int insertSkuFileList(@Param("productSkuFiles") List<ProductSkuFile> productSkuFiles);

    List<ProductSkuFileRespVO> getDraft(String skuCode);

    List<ProductSkuFile> getInfo(String skuCode);
    List<ProductSkuFileRespVO> getInfoBySkuCode(String skuCode);

    List<ProductSkuFileDraft> getDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteList(String skuCode);

    List<ApplyProductSkuFile> getApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    List<ProductSkuFileRespVO> getApplys(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    List<ProductSkuFileRespVO> getRespVoBySkuCode(String skuCode);

    int deleteById(@Param("id")Long id);

    /**
     * 根据id来进行sku商品的修改
     * @param productSkuFile
     * @return
     */
    int updateById(ProductSkuFile productSkuFile);
}
