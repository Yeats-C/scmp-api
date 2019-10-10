package com.aiqin.bms.scmp.api.purchase.jobs.impl;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.dao.StockDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.response.SkuWarehouseResponse;
import com.aiqin.bms.scmp.api.purchase.jobs.SynchronizationStockService;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.Warehouse;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
    @Resource
    private WarehouseDao warehouseDao;

    //数据库地址
    private final static String driver = " com.mysql.jdbc.Driver";
    private final static String url = "jdbc:mysql://39.105.36.111:1024/dareader?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private static final String username = "dareader1";
    private static final String password = "DA-Reader-20190110@1344";


    public HttpResponse synchronizationStock(PagesRequest request, Integer isPage) {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try{
            Class.forName(driver);
        }catch(java.lang.ClassNotFoundException e){
            LOGGER.error("Cant't load Driver");
        }
        try{
            con = DriverManager.getConnection(url,username,password);
            LOGGER.error("Connect Success full...");
            statement = con.createStatement();
            // 查询sku
            List<Warehouse> list = warehouseDao.warehouseList();
            Stock stock;
            Stock stock1;
            List<Stock> stocks;
            List<SkuWarehouseResponse> skuList;
            Integer pageSize;
            for(Warehouse warehouse:list){
                Integer count = productSkuDao.skuByWarehouseCount(warehouse.getWarehouseCode(),
                        warehouse.getLogisticsCenterCode());
                if(isPage == 0){
                    pageSize = request.getPageSize();
                }else {
                    pageSize = 1000;
                }
                Integer len = count/pageSize + 1;
                // 查询耘链的对应的仓库的sku
                for(int i =1;i<=len;i++){
                    request.setPageSize(pageSize);
                    if(isPage == 0){
                        request.setPageNo(request.getPageNo());
                    }else {
                        request.setPageNo(i);
                    }
                    skuList = productSkuDao.skuByWarehouse(warehouse.getWarehouseCode(),
                            warehouse.getLogisticsCenterCode(),  request);
                    if(CollectionUtils.isNotEmptyCollection(skuList)){
                        stocks = Lists.newArrayList();
                        for(SkuWarehouseResponse sku:skuList){
                            // 查询dl的库存
                            String sql =
                                    "SELECT p.`Code`,p.`Name`,dc.StockQty,dc.NormalQty,dc.ReturnQty,dc.TaxNormalAmount,dc.TaxReturnAmount,dc.DCId " +
                                            "from biz_tbl_product p INNER JOIN biz_tbl_dcproduct dc on p.Id = dc.ProductId " +
                                            "where p.`Code` = "+ sku.getSkuCode() +" and dc.DCId = " + warehouse.getWmsWarehouseCode();
                            Double normalNum = 0D;
                            Double returnNum = 0D;
                            Long TaxNormalAmount = 0L;
                            Long TaxReturnAmount = 0L;
                            rs = statement.executeQuery(sql);
                            if (rs == null) {
                                continue;
                            }else {
                                while (rs.next()) {
                                    normalNum = Double.valueOf(rs.getString("NormalQty").trim());
                                    Double normalAmount = Double.valueOf(rs.getString("TaxNormalAmount").trim()) * 100;
                                    if(normalNum == 0 || normalAmount == 0){
                                        TaxNormalAmount = 0L;
                                    }else {
                                        TaxNormalAmount = Math.round(normalAmount/normalNum);
                                    }
                                    returnNum = Double.valueOf(rs.getString("ReturnQty").trim());
                                    Double returnAmount = Double.valueOf(rs.getString("TaxReturnAmount").trim()) * 100;
                                    if(returnNum == 0 || returnAmount == 0){
                                        TaxReturnAmount = 0L;
                                    }else {
                                        TaxReturnAmount = Math.round(returnAmount/returnNum);
                                    }
                                }
                            }
                            // 查询耘链的库存
                            stock = stockDao.stockInfo(sku.getSkuCode(), sku.getWarehouseCode(), sku.getTransportCenterCode(), sku.getWarehouseTypeCode());
                            if(stock == null){
                                stock1 = new Stock();
                                Double num = 0D;
                                Double amount = 0D;
                                if(warehouse.getWmsWarehouseType() == 1) {
                                    num = Double.valueOf(normalNum);
                                    amount = Double.valueOf(TaxNormalAmount) * 100;
                                }else if(warehouse.getWmsWarehouseType() == 2){
                                    num = Double.valueOf(returnNum);
                                    amount = Double.valueOf(TaxReturnAmount) * 100;
                                }
                                stock1.setInventoryNum(num.longValue());
                                stock1.setAvailableNum(num.longValue());
                                if (num == 0 || amount == 0) {
                                    stock1.setTaxCost(0L);
                                } else {
                                    stock1.setTaxCost(Math.round(amount/ num));
                                }
                                stock1.setSkuName(sku.getSkuName());
                                stock1.setSkuCode(sku.getSkuCode());
                                stock1.setStockCode("ST" + IdSequenceUtils.getInstance().nextId());
                                stock1.setCompanyCode("09");
                                stock1.setCompanyName("宁波熙耘科技有限公司");
                                stock1.setWarehouseType(warehouse.getWarehouseTypeCode().toString());
                                stock1.setWarehouseCode(warehouse.getWarehouseCode());
                                stock1.setWarehouseName(warehouse.getWarehouseName());
                                stock1.setTransportCenterCode(warehouse.getLogisticsCenterCode());
                                stock1.setTransportCenterName(warehouse.getLogisticsCenterName());
                                stock1.setStockupNum(0);
                                stock1.setLockNum(0L);
                                stock1.setPurchaseWayNum(0L);
                                stock1.setAllocationWayNum(0L);
                                stock1.setTotalWayNum(0L);
                                stock1.setNewPurchasePrice(0L);
                                stock1.setTaxPrice(0L);
                                stock1.setUpdateBy("0");
                                stock1.setCreateBy("0");
                                stocks.add(stock1);
                            }else {
                                if(warehouse.getWmsWarehouseType() == 1){
                                    if(stock.getAvailableNum() == normalNum.longValue() || stock.getTaxCost() == TaxNormalAmount){
                                        continue;
                                    }else {
                                        stock.setAvailableNum(normalNum.longValue());
                                        stock.setTaxCost(TaxNormalAmount);
                                    }
                                }else {
                                    if(stock.getAvailableNum() == returnNum.longValue() || stock.getTaxCost() == TaxReturnAmount){
                                        continue;
                                    }else {
                                        stock.setAvailableNum(returnNum.longValue());
                                        stock.setTaxCost(TaxReturnAmount);
                                    }
                                }
                                stockDao.update(stock);
                            }
                        }
                        stockDao.insertBatch(stocks);
                        if(isPage == 0){
                            return HttpResponse.success();
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Connect fail:" , e.getMessage());
        } finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                if(statement!=null){
                    statement.close();
                }
                if(con!=null){
                    con.close();
                }
            }catch(Exception e){
                LOGGER.error("Close fail:",e.getMessage());
            }
        }
        return HttpResponse.success();
    }
}
