package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuInspReportRespVo;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/3/13 0013 16:30
 */
public interface ProductSkuInspReportService {
    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    int insertApplyList(List<ApplyProductSkuInspReport> applyProductSkuInspReports);

    int saveList(String skuCode, String applyCode);

    int insertList(List<ProductSkuInspReport> productSkuInspReports);

    int insertDraftList(List<ProductSkuInspReportDraft> productSkuInspReportDrafts);

    /**
     * 获取临时数据
     * @param skuCode
     * @return
     */
    List<ProductSkuInspReportRespVo> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);
}
