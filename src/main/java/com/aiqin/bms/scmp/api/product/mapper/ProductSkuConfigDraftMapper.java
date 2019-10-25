package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuConfigDraft;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ConfigSearchVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.SaveSkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigDetailRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigId;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuConfigDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuConfigDraft record);

    int insertSelective(ProductSkuConfigDraft record);

    ProductSkuConfigDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuConfigDraft record);

    int updateByPrimaryKey(ProductSkuConfigDraft record);

    List<SkuConfigsRepsVo> getList(List<Long> list);
    List<Long> getListCount(@Param("vo") ConfigSearchVo vo);

    List<ProductSkuConfigDraft> selectByCodes(List<String> codes);

    int deleteByConfigCodes(List<String> codes);
    int deleteOutByConfigCodes(List<String> codes);

    List<ProductSkuConfigDraft> getListBySkuCodes(List<String> skuCodes);


    List<SkuConfigsRepsVo> getListBySkuCode(String skuCode);


    int deleteBySkuCodes(List<String> skuCodes);


    /**
     * 批量添加
     * @param records
     * @return
     */
    int insertBatch(List<ProductSkuConfigDraft> records);
    /**
     * 通过sku编码查询
     * @author NullPointException
     * @date 2019/7/18
     * @param configReqVos
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuConfigDraft>
     */
    List<ProductSkuConfigDraft> getListBySkuVo(List<SaveSkuConfigReqVo> configReqVos);
    /**
     * 根据id删除
     * @author NullPointException
     * @date 2019/7/18
     * @param ids
     * @return int
     */
    int deleteByIds(List<Long> ids);

    List<ProductSkuConfigDraft> selectbyConfigCode(List<String> list);

    int deleteByTransportCenterCodes(@Param("skuCode") String skuCode, @Param("deleteCodes") List<String> deleteTransportCenterCodes);

    SkuConfigDetailRepsVo detailForDraft(@Param("skuCode")  String skuCode,@Param("draftId") Long draftId);

    SkuConfigsRepsVo getList2(SkuConfigId id);
}