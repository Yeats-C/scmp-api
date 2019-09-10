package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuInspReport;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInspReport;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInspReportDraft;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QueryProductSkuInspReportReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.QueryInspectionReportReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuInspReportRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.InspectionReportRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/21 0021 16:03
 */
public interface ProductSkuInspReportDao {

    /**
     * 根据销售码获取质检信息
     * @param saleCode
     * @return
     */
    List<InspectionReportRespVO> getInspectionReportsByCode(String saleCode);

    InspectionReportRespVO getInspectionReport(QueryInspectionReportReqVO queryInspectionReportReqVO);

    List<ProductSkuInspReport> getInfo(String skuCode);

    List<ApplyProductSkuInspReport> getApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    List<ProductSkuInspReportRespVo> getApplys(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    int insertApplyList(List<ApplyProductSkuInspReport> applyProductSkuInspReports);

    int insertInspReportList(@Param("list") List<ProductSkuInspReport> productSkuInspReports);

    List<ProductSkuInspReportDraft> getDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    List<ProductSkuInspReportRespVo> getDraft(String skuCode);

    int deleteDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteList(String skuCode);

    int deleteById(Long id);

    int insertDraftList(@Param("productSkuInspReportDrafts") List<ProductSkuInspReportDraft> productSkuInspReportDrafts);

    List<ProductSkuInspReportRespVo> getList(String skuCode);

    List<ProductSkuInspReportRespVo> getListBySkuCodeAndProductDate(QueryProductSkuInspReportReqVo reportReqVo);

    Integer updateInspection(ProductSkuInspReport productSkuInspReport);

}
