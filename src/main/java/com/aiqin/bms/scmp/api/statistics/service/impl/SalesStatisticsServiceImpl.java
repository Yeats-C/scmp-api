package com.aiqin.bms.scmp.api.statistics.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.statistics.dao.StatComSalesMonthlyDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatComSalesYearlyDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatDeptSalesMonthlyDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatDeptSalesYearlyDao;
import com.aiqin.bms.scmp.api.statistics.service.SalesStatisticsService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: zhao shuai
 * @create: 2019-09-03
 **/
@Service
public class SalesStatisticsServiceImpl implements SalesStatisticsService {

    @Resource
    private StatComSalesYearlyDao statComSalesYearlyDao;
    @Resource
    private StatComSalesMonthlyDao statComSalesMonthlyDao;
    @Resource
    private StatDeptSalesYearlyDao statDeptSalesYearlyDao;
    @Resource
    private StatDeptSalesMonthlyDao statDeptSalesMonthlyDao;

    @Override
    public HttpResponse saleInfo(String date, Integer type, Integer reportType, Integer storeTypeCode, Integer productPropertyCode){
        if(StringUtils.isBlank(date) || type == null || reportType == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        if(type.equals(Global.COMPANY)){
            // 销售统计 - 公司
            if(reportType.equals(Global.ANNUAL_REPORT)){
                // 公司 - 年报
                Long year = Long.valueOf(date);
                this.companySale(year, null, 0);
            }else if(reportType.equals(Global.MONTHLY_REPORT)){
                // 公司 - 月报
                Long year = Long.valueOf(date.substring(0, 4));
                Long month = Long.valueOf(date.substring(5, date.length()));
            }
            return HttpResponse.success();
        }else if(type.equals(Global.DEPARTMENT)){
            // 销售统计 - 部门
            if(reportType.equals(Global.ANNUAL_REPORT)){
                // 部门 - 年报
                Long year = Long.valueOf(date);

            }else if(reportType.equals(Global.MONTHLY_REPORT)){
                // 部门 - 月报
                Long year = Long.valueOf(date.substring(0, 4));
                Long month = Long.valueOf(date.substring(5, date.length()));

            }
            return HttpResponse.success();
        }
        return HttpResponse.success();
    }

    private void companySale(Long year, Long month, int i){

    }

}
