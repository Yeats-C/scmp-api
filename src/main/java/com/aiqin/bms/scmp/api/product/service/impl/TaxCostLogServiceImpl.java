package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.product.dao.TaxCostLogDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.pojo.TaxCostLog;
import com.aiqin.bms.scmp.api.product.service.TaxCostLogService;
import com.aiqin.bms.scmp.api.util.DayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TaxCostLogServiceImpl implements TaxCostLogService {
    @Autowired
    private TaxCostLogDao taxCostLogDao;

    @Override
    public void log(Stock st) {
        String theDayBeforeYesterday = DayUtil.getDayStr(-2);
        String yesterday = DayUtil.getDayStr(-1);
        TaxCostLog log = taxCostLogDao.selectByTaxDate(theDayBeforeYesterday, st.getWarehouseCode(), st.getSkuCode());
        TaxCostLog yesterdayLog = taxCostLogDao.selectByTaxDate(yesterday, st.getWarehouseCode(), st.getSkuCode());
        if (null!=yesterdayLog){
            return;
        }
        //前天数据不存在
        if (null==log){
            log=new  TaxCostLog();
            log.setCreateTime(new Date());
            log.setTaxDate(theDayBeforeYesterday);
            log.setNewPurchasePrice(st.getNewPurchasePrice());
            log.setSkuCode(st.getSkuCode());
            log.setSkuName(st.getSkuName());
            log.setTransportCenterCode(st.getTransportCenterCode());
            log.setTransportCenterName(st.getTransportCenterName());
            log.setStockChangeNum(0L);
            log.setStockNum(0L);
            log.setWarehouseCode(st.getWarehouseCode());
            log.setWarehouseName(st.getWarehouseName());
            log.setStockCode(st.getStockCode());
            log.setTaxCost(0L);
            log.setTaxCostLastDay(0L);
        }
        //前天的正品含税总成本
        long total = log.getStockNum() * log.getTaxCost();
        //昨天库存数减去前天库存数
        long changeNum = st.getInventoryNum() - log.getStockNum();
        //昨天含税入库成本
        long price = changeNum * st.getNewPurchasePrice();
        //昨天含税成本
        long num = log.getStockNum() + changeNum;
        long taxCost;
        if (num==0){
            taxCost=0;
        }else {
            taxCost = (total + price) / num;
        }

        TaxCostLog yesterLog = new TaxCostLog();
        yesterLog.setCreateTime(new Date());
        yesterLog.setTaxDate(yesterday);
        yesterLog.setNewPurchasePrice(st.getNewPurchasePrice());
        yesterLog.setSkuCode(st.getSkuCode());
        yesterLog.setSkuName(st.getSkuName());
        yesterLog.setTransportCenterCode(st.getTransportCenterCode());
        yesterLog.setTransportCenterName(st.getTransportCenterName());
        yesterLog.setStockChangeNum(changeNum);
        yesterLog.setStockNum(st.getInventoryNum());
        yesterLog.setStockCode(st.getStockCode());
        yesterLog.setTaxCost(taxCost);
        yesterLog.setTaxCostLastDay(log.getTaxCost());
        yesterLog.setWarehouseCode(st.getWarehouseCode());
        yesterLog.setWarehouseName(st.getWarehouseName());
        taxCostLogDao.insertOne(yesterLog);
        taxCostLogDao.updateStockTaxCost(st.getId(),taxCost);
    }
}
