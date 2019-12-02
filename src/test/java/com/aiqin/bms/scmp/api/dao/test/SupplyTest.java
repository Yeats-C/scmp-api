package com.aiqin.bms.scmp.api.dao.test;

import com.aiqin.bms.scmp.api.SpringBootTestContext;
import com.aiqin.bms.scmp.api.product.domain.pojo.Stock;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.jobs.DoPrice;
import com.aiqin.bms.scmp.api.product.service.PriceJobService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.util.PriceThreadDo;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SupplyTest  extends SpringBootTestContext  {

    @Resource
    private StockService stockService;

    @Test
    public void test(){
        ILockStockBatchReqVO reqVO = new ILockStockBatchReqVO();
        reqVO.setCompanyCode("04");
        reqVO.setTransportCenterCode("1025");
        reqVO.setWarehouseCode("1026");
        reqVO.setPurchaseGroupCode("1044");
        List<ILockStockBatchItemReqVo> itemReqVos = new ArrayList<>();
        ILockStockBatchItemReqVo itemReqVo1 = new ILockStockBatchItemReqVo();
        itemReqVo1.setNum(10L);
        itemReqVo1.setSkuCode("295110");
        itemReqVo1.setSkuName("惠氏金装爱儿复无乳糖配");
        itemReqVo1.setBatchCode("123456786");
        itemReqVo1.setDocumentType(2);
        itemReqVo1.setDocumentNum("111111111");
        itemReqVo1.setSourceDocumentType(3);
        itemReqVo1.setSourceDocumentNum("222222222");
        itemReqVo1.setUpdateByCode("001");
        itemReqVo1.setUpdateByName("AAA");
        itemReqVo1.setRemark("退供生成单");

        itemReqVos.add(itemReqVo1);

        ILockStockBatchItemReqVo itemReqVo11 = new ILockStockBatchItemReqVo();
        itemReqVo11.setNum(10L);
        itemReqVo11.setSkuCode("1002");
        itemReqVo11.setSkuName("惠氏金装爱儿复无乳糖配");
        itemReqVo11.setBatchCode("123456787");
        itemReqVo11.setDocumentType(2);
        itemReqVo11.setDocumentNum("111111111");
        itemReqVo11.setSourceDocumentType(3);
        itemReqVo11.setSourceDocumentNum("222222222");
        itemReqVo11.setUpdateByCode("002");
        itemReqVo11.setUpdateByName("BBB");
        itemReqVo11.setRemark("退供生成单");
        itemReqVos.add(itemReqVo11);

        reqVO.setItemReqVos(itemReqVos);
        // 生成退供单 加锁
        stockService.returnSupplyLockStockBatch(reqVO);
        // 退供单 解锁
       // stockService.returnSupplyUnLockStockBatch(reqVO);
    }

    @Test
    public void test1(){
        ILockStocksReqVO reqVO = new ILockStocksReqVO();
        reqVO.setCompanyCode("04");
        reqVO.setTransportCenterCode("1085");
        reqVO.setWarehouseCode("1086");
        reqVO.setPurchaseGroupCode("1114");

        List<ILockStocksItemReqVo> itemReqVos = new ArrayList<>();
        ILockStocksItemReqVo itemReqVo1 = new ILockStocksItemReqVo();
        itemReqVo1.setNum(1L);
        itemReqVo1.setSkuCode("10000000330");
        itemReqVo1.setSkuName("请别使用");
        itemReqVo1.setDocumentType(2);
        itemReqVo1.setDocumentNum("TGD123456789");
        itemReqVo1.setOperator("ch");
        itemReqVo1.setRemark("退供单生成");

        itemReqVos.add(itemReqVo1);

        /*ILockStocksItemReqVo itemReqVo11 = new ILockStocksItemReqVo();
        itemReqVo11.setNum(100L);
        itemReqVo11.setSkuCode("1002");
        itemReqVo11.setSkuName("惠氏金装爱儿复无乳糖配");
        itemReqVo11.setDocumentType(2);
        itemReqVo11.setDocumentNum("TGD123456789");
        itemReqVo11.setOperator("ch");
        itemReqVo11.setRemark("退供单生成");
        itemReqVos.add(itemReqVo11);*/

        reqVO.setItemReqVos(itemReqVos);
        // 生成退供单 加锁
         stockService.returnSupplyLockStocks(reqVO);
        // 退供单 解锁
        //stockService.returnSupplyUnLockStocks(reqVO);
    }
/*
StockVoRequest(companyCode=04, companyName=北京天佳辰宇科技有限公司, transportCenterCode=1081, transportCenterName=华北仓,
				 warehouseCode=1071, warehouseName=华北销售库, warehouseType=销售库, skuCode=10000000201, skuName=小猪威比AQNP001防爆玻璃奶瓶210ML, purchaseGroupCode=null,
				 purchaseGroupName=null, taxRate=1600, changeNum=10, newDelivery=10000062, newDeliveryName=我的供应商3, newPurchasePrice=100, documentType=1, documentNum=103922,
				sourceDocumentType=1, sourceDocumentNum=CG100342, operator=赵帅, remark=null, stockCost=null),
 */
    @Test
    public void test2(){
        StockChangeRequest stockChangeRequest = new StockChangeRequest();
        stockChangeRequest.setOperationType(10);

        List<StockVoRequest> stockVoRequests = new ArrayList<>();


        StockVoRequest stockVoRequest1 = new StockVoRequest();
        stockVoRequest1.setCompanyCode("04");
        stockVoRequest1.setCompanyName("北京天佳辰宇科技有限公司");
        stockVoRequest1.setTransportCenterCode("1083");
        stockVoRequest1.setTransportCenterName("华东仓");
        stockVoRequest1.setWarehouseCode("1080");
        stockVoRequest1.setWarehouseName("华东特卖库");
        stockVoRequest1.setWarehouseType("1080");
        stockVoRequest1.setChangeNum(10L);

        stockVoRequest1.setSkuCode("10000000247");
        stockVoRequest1.setSkuName("何超测试导入1");

        stockVoRequest1.setNewDelivery("10000062");
        stockVoRequest1.setNewDeliveryName("我的供应商3");
        stockVoRequest1.setTaxRate(BigDecimal.valueOf(1600));

        stockVoRequest1.setDocumentType(4);
        stockVoRequest1.setDocumentNum("103988");
        stockVoRequest1.setSourceDocumentType(10);
        stockVoRequest1.setSourceDocumentNum("12323730");
        stockVoRequest1.setOperator("ch");
        stockVoRequest1.setRemark("ch测试数据");

        stockVoRequests.add(stockVoRequest1);
/*
        StockVoRequest stockVoRequest2 = new StockVoRequest();
        stockVoRequest2.setCompanyCode("04");
        stockVoRequest2.setCompanyName("北京天佳辰宇科技有限公司");
        stockVoRequest2.setTransportCenterCode("1081");
        stockVoRequest2.setTransportCenterName("华北仓");
        stockVoRequest2.setWarehouseCode("1071");
        stockVoRequest2.setWarehouseName("华北销售库");
        stockVoRequest2.setWarehouseType("销售库");
        stockVoRequest2.setChangeNum(10L);

        stockVoRequest2.setSkuCode("10000000201");
        stockVoRequest2.setSkuName("小猪威比AQNP001防爆玻璃奶瓶210ML");

        stockVoRequest2.setNewDelivery("10000062");
        stockVoRequest2.setNewDeliveryName("我的供应商3");
        stockVoRequest2.setNewPurchasePrice(100L);
        stockVoRequest2.setTaxRate(1600L);

        stockVoRequest2.setDocumentType(1);
        stockVoRequest2.setDocumentNum("103922");
        stockVoRequest2.setSourceDocumentType(1);
        stockVoRequest2.setSourceDocumentNum("CG100342");
        stockVoRequest2.setOperator("赵帅");
        stockVoRequest2.setRemark("ch测试数据");

        stockVoRequests.add(stockVoRequest2);


        StockVoRequest stockVoRequest3 = new StockVoRequest();
        stockVoRequest3.setCompanyCode("04");
        stockVoRequest3.setCompanyName("北京天佳辰宇科技有限公司");
        stockVoRequest3.setTransportCenterCode("1081");
        stockVoRequest3.setTransportCenterName("华北仓");
        stockVoRequest3.setWarehouseCode("1071");
        stockVoRequest3.setWarehouseName("华北销售库");
        stockVoRequest3.setWarehouseType("销售库");
        stockVoRequest3.setChangeNum(10L);

        stockVoRequest3.setSkuCode("10000000201");
        stockVoRequest3.setSkuName("小猪威比AQNP001防爆玻璃奶瓶210ML");

        stockVoRequest3.setNewDelivery("10000062");
        stockVoRequest3.setNewDeliveryName("我的供应商3");
        stockVoRequest3.setNewPurchasePrice(100L);
        stockVoRequest3.setTaxRate(1600L);

        stockVoRequest3.setDocumentType(1);
        stockVoRequest3.setDocumentNum("103922");
        stockVoRequest3.setSourceDocumentType(1);
        stockVoRequest3.setSourceDocumentNum("CG100342");
        stockVoRequest3.setOperator("赵帅");
        stockVoRequest3.setRemark("ch测试数据");

        stockVoRequests.add(stockVoRequest3);

        StockVoRequest stockVoRequest4 = new StockVoRequest();
        stockVoRequest4.setCompanyCode("04");
        stockVoRequest4.setCompanyName("北京天佳辰宇科技有限公司");
        stockVoRequest4.setTransportCenterCode("1081");
        stockVoRequest4.setTransportCenterName("华北仓");
        stockVoRequest4.setWarehouseCode("1071");
        stockVoRequest4.setWarehouseName("华北销售库");
        //stockVoRequest4.setWarehouseType(null);
        stockVoRequest4.setChangeNum(10L);

        stockVoRequest4.setSkuCode("10000000201");
        stockVoRequest4.setSkuName("小猪威比AQNP001防爆玻璃奶瓶210ML");

        stockVoRequest4.setNewDelivery("10000062");
        stockVoRequest4.setNewDeliveryName("我的供应商3");
        stockVoRequest4.setNewPurchasePrice(100L);
        stockVoRequest4.setTaxRate(1600L);

        stockVoRequest4.setDocumentType(1);
        stockVoRequest4.setDocumentNum("103922");
        stockVoRequest4.setSourceDocumentType(1);
        stockVoRequest4.setSourceDocumentNum("CG100342");
        stockVoRequest4.setOperator("赵帅");
        stockVoRequest4.setRemark("ch测试数据");

        stockVoRequests.add(stockVoRequest4);*/


        stockChangeRequest.setStockVoRequests(stockVoRequests);

        stockService.changeStock(stockChangeRequest);
    }

    @Autowired
    private PriceJobService priceJobService;
    @Test
    public void test3(){
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        int days = 0;
        ca.add(Calendar.DATE,days);
        Date date = ca.getTime();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        System.err.println(sf.format(date));
       /* String encoding = System.getProperty("file.encoding");
        System.out.println("Default System Encoding:" + encoding);*/

    }
}
/*

10000000201    小猪威比AQNP001防爆玻璃奶瓶210ML
10000000202     明治金装倍护系列纸尿裤S82
10000000204     `贝亲训练牙刷-2(黄色)10505/10374
10000000203     `爱亲贝贝外出服女单品系列闭裆哈衣AQWE852-2绿色/80
10000000200     `爱亲贝贝外出服女单品系列闭裆哈衣AQWE852-2粉色/90
10000000271     小猪威比AQSJ005手口湿巾80片（三联包）
10000000272     小猪威比AQSJ005手口湿巾90片
10000000273     小猪威比AQSJ005手口湿巾180片
10000000274     小猪威比AQSJ005手口湿巾10片
10000000275     小猪威比AQSJ005手口湿巾20片
10000000276     小猪威比AQSJ005手口湿巾950片
10000000277     小猪威比手口湿巾20片
10000000278     方广铁盒核桃味磨牙棒90g
10000000279     方广宝宝营养配方野生鳕鱼肉酥100g
10000000280     方广宝宝配方营养深海金枪鱼肉酥100g
10000000281     小白熊高级吸奶器HL0611
10000000282     雀巢妈妈奶粉900g/听
10000000283     亨氏乐维滋果汁泥苹果香橙120g
10000000284     亨氏乐维滋果汁泥苹果黑加仑120g
10000000285     贝亲200ml婴儿润肤油IA106

and st.transport_center_code = '1081'
			and st.warehouse_code = '1071';

			and st.transport_center_code = '1026'
			and st.warehouse_code = '1029';

 */