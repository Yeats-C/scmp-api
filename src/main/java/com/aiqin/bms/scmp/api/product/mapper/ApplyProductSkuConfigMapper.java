package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuConfig;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.ApplyProductSkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyProductSkuConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuConfig record);

    int insertSelective(ApplyProductSkuConfig record);

    ApplyProductSkuConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuConfig record);

    int updateByPrimaryKey(ApplyProductSkuConfig record);

    int insertBatch(List<ApplyProductSkuConfig> applys);

    List<ApplyProductSkuConfig> selectByFormNo(String formNo);
    List<ApplyProductSkuConfig> selectBySkuCodeAndApplyCode(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    Integer updateApplyInfo(ApplyProductSkuConfigReqVo reqVo);

    List<QueryProductApplyRespVO> queryApplyList(QueryProductApplyReqVO reqVo);

    List<SkuConfigsRepsVo> selectByApplyCode(String applyCode);
    List<SkuConfigsRepsVo> selectBySkuAndApplyCode(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    String findFormNoByCode(String applyCode);

    int updateBySynStatus(@Param("list") List<ApplyProductSkuConfig> list);

    List<ApplyProductSkuConfig> selectUnSynData();
}