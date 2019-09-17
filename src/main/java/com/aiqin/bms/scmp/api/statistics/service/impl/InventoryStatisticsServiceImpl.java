package com.aiqin.bms.scmp.api.statistics.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.statistics.dao.StatDeptLowInventoryMonthlyDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatDeptLowInventoryQuarterlyDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatDeptLowInventoryWeeklyDao;
import com.aiqin.bms.scmp.api.statistics.dao.StatDeptLowInventoryYearlyDao;
import com.aiqin.bms.scmp.api.statistics.domain.request.InventoryStatisticsRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.inventory.InventoryStatisticsResponse;
import com.aiqin.bms.scmp.api.statistics.service.InventoryStatisticsService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: zhao shuai
 * @create: 2019-09-16
 **/
@Service
public class InventoryStatisticsServiceImpl implements InventoryStatisticsService {

    @Resource
    private StatDeptLowInventoryWeeklyDao statDeptLowInventoryWeeklyDao;
    @Resource
    private StatDeptLowInventoryMonthlyDao statDeptLowInventoryMonthlyDao;
    @Resource
    private StatDeptLowInventoryQuarterlyDao statDeptLowInventoryQuarterlyDao;
    @Resource
    private StatDeptLowInventoryYearlyDao statDeptLowInventoryYearlyDao;

    private final static int YEAR = 0;
    private final static int QUARTER = 1;
    private final static int MONTH = 2;
    private final static int WEEK = 3;

    @Override
    public HttpResponse<InventoryStatisticsResponse> lowInventory(InventoryStatisticsRequest request){
        if(request == null || StringUtils.isBlank(request.getDate()) || request.getType() == null || request.getReportType() == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        if(request.getType().equals(Global.COMPANY)){
            // 公司低库存统计
            if(request.getReportType().equals(Global.ANNUAL_REPORT)){
               // 公司年报

            }else if(request.getReportType().equals(Global.QUARTERLY_REPORT)){
                // 公司季报

            }else if (request.getReportType().equals(Global.MONTHLY_REPORT)){
                // 公司月报

            }else {
                // 公司周报

            }
        }else {
            // 部门低库存统计
            if(request.getReportType().equals(Global.ANNUAL_REPORT)){
                // 公司年报

            }else if(request.getReportType().equals(Global.QUARTERLY_REPORT)){
                // 公司季报

            }else if (request.getReportType().equals(Global.MONTHLY_REPORT)){
                // 公司月报

            }else {
                // 公司周报

            }
        }
        return HttpResponse.success();
    }
}
