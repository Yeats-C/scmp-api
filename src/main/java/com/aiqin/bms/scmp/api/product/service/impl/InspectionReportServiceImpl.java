package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuInspReportDao;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.QueryInspectionReportReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.InspectionReportRespVO;
import com.aiqin.bms.scmp.api.product.service.InspectionReportService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public HttpResponse<List<InspectionReportRespVO>> getInspectionReportByCode(String saleCode) {
        try {
            InspectionReportRespVO inspectionReportRespVO = productSkuInspReportDao.getInspectionReportsByCode(saleCode);
            if (StringUtils.isEmpty(inspectionReportRespVO.getSkuCode())) {
                return HttpResponse.success("没有该商品信息");
            }
            List<InspectionReportRespVO> inspectionReportRespVOS = productSkuInspReportDao.getInspectionReportsBySkuCode(inspectionReportRespVO.getSkuCode());
            if(inspectionReportRespVOS.size() > 0 && inspectionReportRespVOS != null){
                return HttpResponse.success(inspectionReportRespVOS);
            }else {
                return HttpResponse.success(inspectionReportRespVO.getSkuName());
            }
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
