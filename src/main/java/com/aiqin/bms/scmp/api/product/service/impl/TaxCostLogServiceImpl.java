package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.product.dao.OutboundProductDao;
import com.aiqin.bms.scmp.api.product.dao.StockFlowDao;
import com.aiqin.bms.scmp.api.product.dao.TaxCostLogDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockFlow;
import com.aiqin.bms.scmp.api.product.domain.pojo.TaxCostLog;
import com.aiqin.bms.scmp.api.product.service.TaxCostLogService;
import com.aiqin.bms.scmp.api.util.DayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class TaxCostLogServiceImpl implements TaxCostLogService {
    @Autowired
    private TaxCostLogDao taxCostLogDao;

    @Autowired
    private StockFlowDao stockFlowDao;

    @Autowired
    private OutboundProductDao outboundProductDao;

/*    @Override
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
    }*/



    public void log(Stock st) {
        String theDayBeforeYesterday = DayUtil.getDayStr(-2);
        String yesterday = DayUtil.getDayStr(-1);
        // 前两天数据
        TaxCostLog log = taxCostLogDao.selectByTaxDate(theDayBeforeYesterday, st.getWarehouseCode(), st.getSkuCode());

        // 单个sku前一天的日志流水
        List<StockFlow> stockFlows = stockFlowDao.selectFlowByStockSku(yesterday,st.getStockCode());

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
            log.setTaxCost(BigDecimal.valueOf(0));
            log.setTaxCostLastDay(BigDecimal.valueOf(0));
        }
        for (StockFlow stockFlow : stockFlows) {
            if(stockFlow.getDocumentType() != null){
                // 1==入库操作
                if (stockFlow.getDocumentType() == 1){
                    if (stockFlow.getSourceDocumentType() == 3){
                        // 前一天数据
                        TaxCostLog yesterdayLog = taxCostLogDao.selectByTaxDate(yesterday, st.getWarehouseCode(), st.getSkuCode());
                        // 采购
                        if (yesterdayLog == null){
                            noCost(st, yesterday, log, stockFlow);
                        }else{
                            cost(st, yesterday, stockFlow, yesterdayLog);
                        }
                    }else if (stockFlow.getSourceDocumentType() == 4){
                        // 前一天数据
                        TaxCostLog yesterdayLog = taxCostLogDao.selectByTaxDate(yesterday, st.getWarehouseCode(), st.getSkuCode());
                        // 调拨
                        if (yesterdayLog == null){
                            noCost(st, yesterday, log, stockFlow);
                        }else {
                            cost(st, yesterday, stockFlow, yesterdayLog);
                        }
                    }else if (stockFlow.getSourceDocumentType() == 5){
                        // 退货
                        // 前一天数据
                        TaxCostLog yesterdayLog = taxCostLogDao.selectByTaxDate(yesterday, st.getWarehouseCode(), st.getSkuCode());
                        if (yesterdayLog == null){
                            noCost(st, yesterday, log, stockFlow);
                        }else{
                            cost(st, yesterday, stockFlow, yesterdayLog);
                        }
                    }else if (stockFlow.getSourceDocumentType() == 6){
                        // 前一天数据
                        TaxCostLog yesterdayLog = taxCostLogDao.selectByTaxDate(yesterday, st.getWarehouseCode(), st.getSkuCode());
                        // 移库
                        if (yesterdayLog == null){
                            noCost(st, yesterday, log, stockFlow);
                        }else{
                            cost(st, yesterday, stockFlow, yesterdayLog);
                        }
                    }
                    // 监管仓入库7  报废8
                    // 出库
                }else if(stockFlow.getDocumentType() == 0){
                    if (stockFlow.getSourceDocumentType() == 2){
                        // 退供
                        // 前一天数据
                        TaxCostLog yesterdayLog = taxCostLogDao.selectByTaxDate(yesterday, st.getWarehouseCode(), st.getSkuCode());
                        costCommon(yesterdayLog,log,stockFlow);
                    }else if (stockFlow.getSourceDocumentType() == 4){
                        // 调拨
                        // 前一天数据
                        TaxCostLog yesterdayLog = taxCostLogDao.selectByTaxDate(yesterday, st.getWarehouseCode(), st.getSkuCode());
                        costCommon(yesterdayLog,log,stockFlow);
                    }else if (stockFlow.getSourceDocumentType() == 9){
                        // 订单
                        // 前一天数据
                        TaxCostLog yesterdayLog = taxCostLogDao.selectByTaxDate(yesterday, st.getWarehouseCode(), st.getSkuCode());
                        costCommon(yesterdayLog,log,stockFlow);
                    }else if (stockFlow.getSourceDocumentType() == 6){
                        // 移库
                        // 前一天数据
                        TaxCostLog yesterdayLog = taxCostLogDao.selectByTaxDate(yesterday, st.getWarehouseCode(), st.getSkuCode());
                        costCommon(yesterdayLog,log,stockFlow);
                    }
                    // 监管仓出库 10
                }
            }
        }
    }

    private void cost(Stock st, String yesterday, StockFlow stockFlow, TaxCostLog yesterdayLog) {
        // ==null 说明当天没有采购入库操作，拿前天的数据去算，!=null 说明当天已经有入库操作了，那当前的数据去算
        // 上次的正品含税总成本
        BigDecimal total =  yesterdayLog.getTaxCost().multiply(BigDecimal.valueOf(yesterdayLog.getStockNum())).setScale(4, BigDecimal.ROUND_HALF_UP);
        // 本次采购入库数量
        long changeNum = stockFlow.getChangeNum();
        // 本次的采购入库总成本
        BigDecimal price;
        if(stockFlow.getStockCost() != null){
            price = stockFlow.getStockCost().multiply(BigDecimal.valueOf(changeNum)).setScale(4, BigDecimal.ROUND_HALF_UP);
        }else{
            price = BigDecimal.valueOf(0);
        }
        // 本次的最终含税成本
        long num = yesterdayLog.getStockNum() + changeNum;
        BigDecimal taxCost;
        if (num==0){
            taxCost = BigDecimal.valueOf(0);
        }else {
            taxCost = (total.add(price)).divide(BigDecimal.valueOf(num)).setScale(4, BigDecimal.ROUND_HALF_UP);
        }
        common(taxCost,yesterdayLog.getTaxCost(),st,yesterday,changeNum,num);
    }


    private void noCost(Stock st, String yesterday, TaxCostLog log, StockFlow stockFlow) {
        // 前天的正品含税总成本
        BigDecimal total = log.getTaxCost().multiply(BigDecimal.valueOf(log.getStockNum())).setScale(4, BigDecimal.ROUND_HALF_UP);;
        // 本次的采购入库总成本
        BigDecimal price;
        if(stockFlow.getStockCost() != null){
            price = stockFlow.getStockCost().multiply(BigDecimal.valueOf(stockFlow.getChangeNum())).setScale(4, BigDecimal.ROUND_HALF_UP);
        }else{
            price = BigDecimal.valueOf(0);
        }
        // 本次的最终含税成本
        long num = log.getStockNum() + stockFlow.getChangeNum();
        BigDecimal taxCost;
        if (num==0){
            taxCost=BigDecimal.valueOf(0);
        }else {
            taxCost = (total.add(price)).divide(BigDecimal.valueOf(num)).setScale(4, BigDecimal.ROUND_HALF_UP);
        }
        common(taxCost,log.getTaxCost(),st,yesterday,stockFlow.getChangeNum(),num);
    }

    public void costCommon(TaxCostLog todayLog,TaxCostLog log,StockFlow stockFlow){
        if (todayLog == null){
            outboundProductDao.updateStockCost(log.getTaxCost(),stockFlow.getDocumentNum(),stockFlow.getSkuCode());
            stockFlowDao.updateStockCost(log.getTaxCost(),stockFlow.getDocumentNum(),stockFlow.getSkuCode());
        }else{
            outboundProductDao.updateStockCost(todayLog.getTaxCost(),stockFlow.getDocumentNum(),stockFlow.getSkuCode());
            stockFlowDao.updateStockCost(todayLog.getTaxCost(),stockFlow.getDocumentNum(),stockFlow.getSkuCode());
        }
    }

    public void common(BigDecimal taxCost,BigDecimal logTaxCost,Stock st,String yesterday,Long changeNum,Long num){
        if(!taxCost.equals(logTaxCost)){
            TaxCostLog yesterLog = new TaxCostLog();
            yesterLog.setCreateTime(new Date());
            yesterLog.setTaxDate(yesterday);
            yesterLog.setNewPurchasePrice(st.getNewPurchasePrice());
            yesterLog.setSkuCode(st.getSkuCode());
            yesterLog.setSkuName(st.getSkuName());
            yesterLog.setTransportCenterCode(st.getTransportCenterCode());
            yesterLog.setTransportCenterName(st.getTransportCenterName());
            yesterLog.setStockChangeNum(changeNum);
            yesterLog.setStockNum(num);
            yesterLog.setStockCode(st.getStockCode());
            yesterLog.setTaxCost(taxCost);
            yesterLog.setTaxCostLastDay(logTaxCost);
            yesterLog.setWarehouseCode(st.getWarehouseCode());
            yesterLog.setWarehouseName(st.getWarehouseName());
            taxCostLogDao.insertOne(yesterLog);
        }
        taxCostLogDao.updateStockTaxCost(st.getId(),taxCost);
    }

}
