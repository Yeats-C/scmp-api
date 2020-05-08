package com.aiqin.bms.scmp.api.product.jobs.impl;

import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.TaxCostLogDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.pojo.TaxCostLogStock;
import com.aiqin.bms.scmp.api.product.jobs.DoPrice;
import com.aiqin.bms.scmp.api.product.service.PriceJobService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.util.DayUtil;
import com.aiqin.bms.scmp.api.util.PriceThreadDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ProductPriceJob {

    @Autowired
    private PriceJobService priceJobService;
    @Autowired
    private StockService stockService;
    @Autowired
    private TaxCostLogDao taxCostLogDao;

    /**
     * 每天晚上零点统计产品成本价
     */
    @Scheduled(cron = "0 0 0 * * ?")
   // @Scheduled(cron = "* */4 * * * ?")
    public void productPrice() {
        //查询仓库数
        List<Stock> warehouse = stockService.selectGroup();
        PriceThreadDo.setPool(warehouse.size());
        for (Stock stock : warehouse) {
            DoPrice doPrice=null;
            try {
                doPrice=new DoPrice(priceJobService,stock);
                PriceThreadDo.doExecutor(doPrice);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error(Global.ERROR, e);
                Thread.currentThread().interrupt();
            }
        }
        skuCost();
    }

    /**
        每晚凌晨计算sku库存成本
     */
    public void skuCost(){
        List<Stock> stocks = stockService.selectSkuCost();
        List<TaxCostLogStock> list = new ArrayList();
        for (Stock stock : stocks) {
            BigDecimal stockSumCost = stock.getTaxCost();
            Long stockSumNum = stock.getInventoryCount();
            BigDecimal stockTaxCost;
            if (stockSumNum == 0 || stockSumNum == null){
                stockTaxCost = BigDecimal.valueOf(0);
            }else{
                stockTaxCost =  stockSumCost.divide(BigDecimal.valueOf(stockSumNum)).setScale(4, BigDecimal.ROUND_HALF_UP);
            }
            TaxCostLogStock taxCostLogStock = new TaxCostLogStock();
            taxCostLogStock.setTaxDate(DayUtil.getDayStr(-1));
            taxCostLogStock.setSkuCode(stock.getSkuCode());
            taxCostLogStock.setSkuName(stock.getSkuName());
            taxCostLogStock.setWarehousType(0L);
            taxCostLogStock.setWarehousName("全国");
            taxCostLogStock.setStockSumCost(stockSumCost);
            taxCostLogStock.setStockSumNum(stockSumNum);
            taxCostLogStock.setStockTaxCost(stockTaxCost);
            list.add(taxCostLogStock);
        }
        TaxCostLogStock taxCostLogStock = taxCostLogDao.selectTimeByTaxDate(DayUtil.getDayStr(-1));
        if(taxCostLogStock == null){
            taxCostLogDao.insertOneSku(list);
        }else{
            taxCostLogDao.updateOneSku(list);
        }
    }
}
