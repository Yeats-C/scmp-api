package com.aiqin.bms.scmp.api.purchase.jobs.impl;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.dao.StockDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.response.SkuWarehouseResponse;
import com.aiqin.bms.scmp.api.purchase.jobs.SynchronizationStockService;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SynchronizationStockServiceImpl implements SynchronizationStockService {

    //数据库地址
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String URL = "jdbc:mysql://39.105.36.111:1024/dareader?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private static final String USERNAME = "dareader";
    private static final String PASSWORD = "x4p6ayYI6yvE6AvKorTG";
    @Resource
    private ProductSkuDao productSkuDao;
    @Resource
    private StockDao stockDao;

    public HttpResponse synchronizationStock(PagesRequest request) {
        log.error("Connect Success full...");
        List<SkuWarehouseResponse> skuList;
        List<SkuWarehouseResponse> wareList;
        List<Stock> stockList;
        Stock stock;
        Stock oldStock;
        BigDecimal count = new BigDecimal("100");
        BigDecimal num = new BigDecimal("0");
        BigDecimal amount = new BigDecimal("0");
        // 查询sku信息
        skuList = productSkuDao.skuList(request);
        stockList = Lists.newArrayList();
        if (CollectionUtils.isNotEmptyCollection(skuList)) {
            long startTime = java.lang.System.currentTimeMillis();
            log.info("执行代码块/方法");
            List<String> skuCodeList = new ArrayList<>();
            skuList.forEach(s -> skuCodeList.add(s.getSkuCode()));
            Map<String, SourceStock> sourceStockMap = querySourceStock(skuCodeList);
            Map<String, Stock> localStockMap = queryLocalStock(skuCodeList);
            SourceStock sourceStock;
            for (SkuWarehouseResponse sku : skuList) {
                // 查询sku对应的库房
                wareList = productSkuDao.skuByWarehouse(sku.getSkuCode());
                if (CollectionUtils.isEmptyCollection(wareList)) {
                    continue;
                }
                for (SkuWarehouseResponse ware : wareList) {
                    sourceStock = sourceStockMap.get(sku.getSkuCode() + ware.getWmsWarehouseCode());
                    if (sourceStock == null) {
                        continue;
                    }
                    stock = new Stock();
                    if (ware.getWmsWarehouseType() == 1) {
                        num = sourceStock.getNormalNum();
                        amount = sourceStock.getNormalAmount();
                    } else if (ware.getWmsWarehouseType() == 2) {
                        num = sourceStock.getReturnNum();
                        amount = sourceStock.getReturnAmount();
                    }
                    if(ware.getWarehouseCode().equals("1099")){
                        stock.setInventoryNum(100000L);
                        stock.setAvailableNum(100000L);
                    }else {
                        stock.setInventoryNum(num.longValue());
                        stock.setAvailableNum(num.longValue());
                    }
                    if (num.longValue() == 0) {
                        stock.setTaxCost(0L);
                    } else {
                        Long cost = amount.divide(num, 2, BigDecimal.ROUND_CEILING).multiply(count).longValue();
                        stock.setTaxCost(cost);
                    }
                    stock.setSkuCode(sku.getSkuCode());
                    stock.setSkuName(sku.getSkuName());
                    // 查询库存
                    stock.setWarehouseType(ware.getWarehouseTypeCode());
                    stock.setWarehouseCode(ware.getWarehouseCode());
                    stock.setTransportCenterCode(ware.getTransportCenterCode());
                    stock.setCompanyCode("09");
                    oldStock = localStockMap.get(stock.getSkuCode() + stock.getWarehouseCode() + stock.getWarehouseType() + stock.getTransportCenterCode());
                    if (oldStock == null) {
                        stock.setStockCode("ST" + IdSequenceUtils.getInstance().nextId());
                    } else {
                        stock.setStockCode(oldStock.getStockCode());
                    }
                    stock.setCompanyName("宁波熙耘科技有限公司");
                    stock.setWarehouseName(ware.getWarehouseName());
                    stock.setTransportCenterName(ware.getTransportCenterName());
                    stock.setStockupNum(0);
                    stock.setLockNum(0L);
                    stock.setPurchaseWayNum(0L);
                    stock.setAllocationWayNum(0L);
                    stock.setTotalWayNum(0L);
                    stock.setNewPurchasePrice(0L);
                    stock.setTaxPrice(0L);
                    stock.setUpdateBy("0");
                    stockList.add(stock);
                }
            }
            if (stockList.size() > 0) {
                stockDao.insertReplaceAll(stockList);
            }
            long endTime = java.lang.System.currentTimeMillis();
            log.info("程序运行时间 " + (endTime - startTime) + "ms");
        } else {
            return HttpResponse.successGenerics("sku is empty");
        }

        return HttpResponse.success();
    }

    private Map<String, Stock> queryLocalStock(List<String> skuList) {
        Map<String, Stock> result = new HashMap<>();
        List<Stock> stockList = stockDao.stockInfoList(skuList);
        for (Stock stock : stockList) {
            result.put(stock.getSkuCode() + stock.getWarehouseCode() + stock.getWarehouseType() + stock.getTransportCenterCode(), stock);
        }
        return result;
    }

    private Map<String, SourceStock> querySourceStock(List<String> skuList) {
        Map<String, SourceStock> result = new HashMap<>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("Cant't load Driver");
        }
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = con.createStatement();
            String sql = "SELECT p.`Code`,p.`Name`,dc.StockQty,dc.NormalQty,dc.ReturnQty,dc.TaxNormalAmount,dc.TaxReturnAmount,dc.DCId " +
                    " FROM biz_tbl_product p INNER JOIN biz_tbl_dcproduct dc ON p.Id = dc.ProductId " +
                    " WHERE p.`Code` IN ('" + String.join("','", skuList) + "');";
            rs = statement.executeQuery(sql);
            SourceStock stock;
            while (rs.next()) {
                stock = new SourceStock();
                stock.setSkuCode(rs.getString("Code"));
                stock.setWarehouseId(rs.getString("DCId"));
                stock.setNormalNum(new BigDecimal(rs.getString("NormalQty")));
                stock.setNormalAmount(new BigDecimal(rs.getString("TaxNormalAmount").trim()));
                stock.setReturnNum(new BigDecimal(rs.getString("ReturnQty")));
                stock.setReturnAmount(new BigDecimal(rs.getString("TaxReturnAmount").trim()));
                result.put(stock.getSkuCode() + stock.getWarehouseId(), stock);
            }
        } catch (Exception e) {
            log.error("Connect fail:", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                log.error("Close fail:", e);
            }
        }

        return result;
    }

    @Data
    private static class SourceStock {
        private String skuCode;
        private String warehouseId;
        private BigDecimal normalNum = new BigDecimal("0");
        private BigDecimal returnNum = new BigDecimal("0");
        private BigDecimal normalAmount = new BigDecimal("0");
        private BigDecimal returnAmount = new BigDecimal("0");
    }
}
