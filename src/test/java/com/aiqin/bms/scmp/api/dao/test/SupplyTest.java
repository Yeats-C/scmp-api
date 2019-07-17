package com.aiqin.bms.scmp.api.dao.test;

import com.aiqin.bms.scmp.api.SpringBootTestContext;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.google.common.collect.Lists;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        reqVO.setTransportCenterCode("1025");
        reqVO.setWarehouseCode("1026");
        reqVO.setPurchaseGroupCode("1044");

        List<ILockStocksItemReqVo> itemReqVos = new ArrayList<>();
        ILockStocksItemReqVo itemReqVo1 = new ILockStocksItemReqVo();
        itemReqVo1.setNum(100L);
        itemReqVo1.setSkuCode("295110");
        itemReqVo1.setSkuName("惠氏金装爱儿复无乳糖配");
        itemReqVo1.setDocumentType(2);
        itemReqVo1.setDocumentNum("111111111");
        itemReqVo1.setSourceDocumentType(3);
        itemReqVo1.setSourceDocumentNum("222222222");
        itemReqVo1.setOperator("AAA");
        itemReqVo1.setRemark("退供生成单");

        itemReqVos.add(itemReqVo1);

        ILockStocksItemReqVo itemReqVo11 = new ILockStocksItemReqVo();
        itemReqVo11.setNum(100L);
        itemReqVo11.setSkuCode("1002");
        itemReqVo11.setSkuName("惠氏金装爱儿复无乳糖配");
        itemReqVo11.setDocumentType(2);
        itemReqVo11.setDocumentNum("111111111");
        itemReqVo11.setSourceDocumentType(3);
        itemReqVo11.setSourceDocumentNum("222222222");
        itemReqVo11.setOperator("BBB");
        itemReqVo11.setRemark("退供生成单");
        itemReqVos.add(itemReqVo11);

        reqVO.setItemReqVos(itemReqVos);
        // 生成退供单 加锁
        // stockService.returnSupplyLockStocks(reqVO);
        // 退供单 解锁
        stockService.returnSupplyUnLockStocks(reqVO);
    }
}
