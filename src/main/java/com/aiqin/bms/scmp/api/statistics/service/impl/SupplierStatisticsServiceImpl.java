package com.aiqin.bms.scmp.api.statistics.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.statistics.dao.StatSupplierArrivalRateMonthlyDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatSupplierArrivalRateYearlyDao;
import com.aiqin.bms.scmp.api.statistics.domain.response.SupplierDeliveryResponse;
import com.aiqin.bms.scmp.api.statistics.service.SupplierStatisticsService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-09-07
 **/
@Service
public class SupplierStatisticsServiceImpl implements SupplierStatisticsService {

    @Resource
    private StatSupplierArrivalRateYearlyDao statSupplierArrivalRateYearlyDao;
    @Resource
    private StatSupplierArrivalRateMonthlyDao statSupplierArrivalRateMonthlyDao;

    @Override
    public HttpResponse<SupplierDeliveryResponse> supplierDelivery(String date, Integer reportType, String productSortCode){
        if(StringUtils.isBlank(date) || reportType == null || StringUtils.isBlank(productSortCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        if(reportType.equals(Global.ANNUAL_REPORT)){
            // 供应商到货率统计 - 部门 - 年报
            Long year = Long.valueOf(date);
            this.supplierArrivalYear(year, productSortCode);
        }else {
            // 供应商到货率统计 - 部门 - 月报
            Long year = Long.valueOf(date.substring(0, 4));
            Long month = Long.valueOf(date.substring(5));

        }
        return HttpResponse.success();
    }

    private void supplierArrivalYear(Long year, String productSortCode){
        List<SupplierDeliveryResponse> sum;
        sum = statSupplierArrivalRateYearlyDao.supplierArrivalSum(year, productSortCode);
        
    }

}
