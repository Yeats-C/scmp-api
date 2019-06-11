package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.request.sku.store.QueryInspectionReportReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.InspectionReportRespVO;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/21 0021 16:20
 */
public interface InspectionReportService {
    List<InspectionReportRespVO> getInspectionReportByCode(String saleCode);

    InspectionReportRespVO getInspectionReport(QueryInspectionReportReqVO queryInspectionReportReqVO);
}
