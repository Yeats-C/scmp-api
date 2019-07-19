package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuApplyVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface ApplyProductSkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSku record);

    int insertSelective(ApplyProductSku record);

    ApplyProductSku selectNoExistsApprovalBySkuCode(String skuCode);

    ApplyProductSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSku record);

    int updateByPrimaryKey(ApplyProductSku record);

    List<ApplyProductSku> selectByFormNO(@Param("formNo") String formNo);


    int updateList(@Param("list") Collection<ApplyProductSku> list);


    /**
     * 修改申请中的状态
     * @param applyStatus
     * @param formNo
     * @return
     */
    int updateStatusByFormNo(@Param("applyStatus") byte applyStatus, @Param("formNo") String formNo,
                             @Param("auditorBy") String auditorBy, @Param("auditorTime") Date auditorTime);

    List<ApplyProductSku> getProductApplyList(@Param("skuCodes") List<String> skuCodes, @Param("number") Byte number);


    List<QueryProductApplyRespVO> queryApplyList(QueryProductApplyReqVO reqVo);


    String findFormNoByCode(String applyCode);

    List<ProductSkuApplyVo> selectByApplyCode(String applyCode);
}