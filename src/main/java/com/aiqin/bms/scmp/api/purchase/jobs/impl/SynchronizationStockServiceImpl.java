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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Component
public class SynchronizationStockServiceImpl implements SynchronizationStockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SynchronizationStockServiceImpl.class);

    @Resource
    private ProductSkuDao productSkuDao;
    @Resource
    private StockDao stockDao;

    //数据库地址
    private final static String driver = " com.mysql.jdbc.Driver";
    private final static String url = "jdbc:mysql://39.105.36.111:1024/dareader?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private static final String username = "dareader1";
    private static final String password = "DA-Reader-20190110@1344";

    public HttpResponse synchronizationStock(PagesRequest request, Integer isPage){
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try{
            Class.forName(driver);
        }catch(java.lang.ClassNotFoundException e){
            LOGGER.error("Cant't load Driver");
        }
        try {
            con = DriverManager.getConnection(url, username, password);
            LOGGER.error("Connect Success full...");
            statement = con.createStatement();
            List<SkuWarehouseResponse> skuList;
            List<Stock> stockList;
            Stock stock;
            Integer size;
            // 是否执行全部sku 0执行 否则只执行某100个sku的
            if(isPage == 0){
                // 查询所有sku数量
                Integer count = productSkuDao.skuByWarehouseCount();
                size = count/request.getPageSize() + 1;
            }else{
                size = 1;
            }
            for(int i = 1; i <= size; i++) {
                // 查询sku及库房信息
                skuList = productSkuDao.skuByWarehouse(request);
                stockList = Lists.newArrayList();
                if(CollectionUtils.isNotEmptyCollection(skuList)){
//                    long startTime = java.lang.System.currentTimeMillis();
//                    java.lang.System.out.println("执行代码块/方法" + i);
                    for (SkuWarehouseResponse sku : skuList) {
                        // 查询dl的库存
                        String sql = "SELECT p.`Code`,p.`Name`,dc.StockQty,dc.NormalQty,dc.ReturnQty,dc.TaxNormalAmount,dc.TaxReturnAmount,dc.DCId " +
                                "from biz_tbl_product p INNER JOIN biz_tbl_dcproduct dc on p.Id = dc.ProductId " +
                                "where p.`Code` = " + sku.getSkuCode() + " and dc.DCId = " + sku.getWmsWarehouseCode();
                        rs = statement.executeQuery(sql);
                        BigDecimal normalNum = new BigDecimal("0");
                        BigDecimal returnNum= new BigDecimal("0");
                        BigDecimal normalAmount = new BigDecimal("0");
                        BigDecimal returnAmount = new BigDecimal("0");
                        if (rs == null) {
                            continue;
                        } else {
                            while (rs.next()) {
                                normalNum = new BigDecimal(rs.getString("NormalQty"));
                                normalAmount = new BigDecimal(rs.getString("TaxNormalAmount").trim());
                                returnNum = new BigDecimal(rs.getString("ReturnQty"));
                                returnAmount = new BigDecimal(rs.getString("TaxReturnAmount").trim());
                            }
                        }
                        stock = new Stock();
                        BigDecimal num = new BigDecimal("0");
                        BigDecimal amount = new BigDecimal("0");
                        if (sku.getWmsWarehouseType() == 1) {
                            num = normalNum;
                            amount = normalAmount;
                        } else if (sku.getWmsWarehouseType() == 2) {
                            num = returnNum;
                            amount = returnAmount;
                        }
                        stock.setInventoryNum(num.longValue());
                        stock.setAvailableNum(num.longValue());
                        if (num.longValue() == 0) {
                            stock.setTaxCost(0L);
                        } else {
                            Long cost = amount.divide(num, 2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal("100")).longValue();
                            stock.setTaxCost(cost);
                        }
                        stock.setSkuCode(sku.getSkuCode());
                        stock.setSkuName(sku.getSkuName());
                        stock.setStockCode("ST" + IdSequenceUtils.getInstance().nextId());
                        stock.setCompanyCode("09");
                        stock.setCompanyName("宁波熙耘科技有限公司");
                        stock.setWarehouseType(sku.getWarehouseTypeCode());
                        stock.setWarehouseCode(sku.getWarehouseCode());
                        stock.setWarehouseName(sku.getWarehouseName());
                        stock.setTransportCenterCode(sku.getTransportCenterCode());
                        stock.setTransportCenterName(sku.getTransportCenterName());
                        stock.setStockupNum(0);
                        stock.setLockNum(0L);
                        stock.setPurchaseWayNum(0L);
                        stock.setAllocationWayNum(0L);
                        stock.setTotalWayNum(0L);
                        stock.setNewPurchasePrice(0L);
                        stock.setTaxPrice(0L);
                        stock.setUpdateBy("0");
                        stock.setCreateBy("0");
                        stockList.add(stock);
                    }
                    stockDao.insertReplaceAll(stockList);
//                    long endTime=java.lang.System.currentTimeMillis();
//                    java.lang.System.out.println("程序运行时间 " + i + ":"+(endTime - startTime)+"ms");
                    if(isPage == 1) {
                        return HttpResponse.success();
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Connect fail:" , e.getMessage());
        } finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                statement.close();
                con.close();
            }catch(Exception e){
                LOGGER.error("Close fail:",e.getMessage());
            }
        }
        return HttpResponse.success();
    }
}
