package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.product.dao.ProductSkuInspReportDao;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.QueryInspectionReportReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.InspectionReportRespVO;
import com.aiqin.bms.scmp.api.product.service.InspectionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/21 0021 16:20
 */
@Service
public class InspectionReportServiceImpl implements InspectionReportService {
    @Autowired
    ProductSkuInspReportDao productSkuInspReportDao;

    @Override
    public List<InspectionReportRespVO> getInspectionReportByCode(String saleCode) {
        try {
            List<InspectionReportRespVO> inspectionReportRespVOS = productSkuInspReportDao.getInspectionReportsByCode(saleCode);
            return inspectionReportRespVOS;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public InspectionReportRespVO getInspectionReport(QueryInspectionReportReqVO queryInspectionReportReqVO) {
        try {
            InspectionReportRespVO inspectionReportRespVO = productSkuInspReportDao.getInspectionReport(queryInspectionReportReqVO);
            return inspectionReportRespVO;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }
}
