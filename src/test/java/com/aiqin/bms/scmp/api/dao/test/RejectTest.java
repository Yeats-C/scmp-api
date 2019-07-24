package com.aiqin.bms.scmp.api.dao.test;

import com.aiqin.bms.scmp.api.SpringBootTestContext;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyDetailHandleRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyHandleRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectStockRequest;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
import com.aiqin.bms.scmp.api.purchase.service.impl.GoodsRejectApprovalServiceImpl;
import com.aiqin.bms.scmp.api.supplier.dao.logisticscenter.LogisticsCenterDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.LogisticsCenter;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.Warehouse;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
public class RejectTest extends SpringBootTestContext {

    @Resource
    private GoodsRejectService goodsRejectService;

    @Resource
    private GoodsRejectApprovalServiceImpl goodsRejectApprovalService;
    @Resource
    private WarehouseDao warehouseDao;
    @Resource
    private LogisticsCenterDao logisticsCenterDao;

    /**
     * 供应商确认
     */
    @Test
    public void rejectSupplier() {
        RejectRecord request = new RejectRecord();
        request.setRejectRecordId("BI3A6DE17B116B49FAA6FA00F0BBFF6E59");
        goodsRejectService.rejectSupplier(request);
    }

    @Test
    public void finishStock() {
        RejectStockRequest request = new RejectStockRequest();
        request.setRejectRecordCode("RR100000");
        request.setOutStockTime(new Date());
        goodsRejectService.finishStock(request);

    }

    @Test
    public void categoryName() {
        String categoryName = goodsRejectService.selectCategoryName("103001001001");
        System.out.println(categoryName);
    }

    @Test
    public void rejectApply() {
        RejectApplyHandleRequest rejectApplyQueryRequest = new RejectApplyHandleRequest();
        rejectApplyQueryRequest.setCreateById("10001");
        rejectApplyQueryRequest.setCreateByName("小明");
        rejectApplyQueryRequest.setPurchaseGroupCode("1044");
        rejectApplyQueryRequest.setPurchaseGroupName("奶粉组");
        rejectApplyQueryRequest.setCompanyName("爱亲");
        rejectApplyQueryRequest.setApplyType(1);
        rejectApplyQueryRequest.setCompanyCode("01");
        List<RejectApplyDetailHandleRequest> detailList = new ArrayList<>();
        RejectApplyDetailHandleRequest detail = new RejectApplyDetailHandleRequest();
        detail.setPurchaseGroupCode("1044");
        detail.setPurchaseGroupName("奶粉组");
        detail.setApplyType(1);
        detail.setBarcode("112");
        detail.setSkuCode("295110");
        detail.setSkuName("爱亲贝贝外出服夏季卡通中性梭织九分裤9812203170584浅灰100");
        detail.setProductId("product11111");
        detail.setCategoryId("01050201");
        detail.setCategoryName("乳糖不耐受奶粉");
        detail.setBrandId("3001");
        detail.setBrandName("惠氏");
        detail.setProductType(0);
        detail.setColorCode("c1");
        detail.setColorName("颜色");
        detail.setModelNumber("型号");
        detail.setProductSpec("规格");
        detail.setUnitCode("10036");
        detail.setUnitName("单位");
        detail.setStockCount(10);
        detail.setSingleCount(5);
        detail.setProductCount(5);
        detail.setTaxRate(10);
        detail.setTransportCenterCode("1025");
        detail.setTransportCenterName("华北");
        detail.setWarehouseCode("1026");
        detail.setWarehouseName("华北销售库");
        detail.setProductAmount(10L);
        detail.setProductCost(10L);
        detail.setBatchNo("123456788");
        detail.setBatchCreateTime("2019-06-28");
        detail.setBatchRemark("第二次入库");
        detail.setSettlementMethodCode("s11");
        detail.setSettlementMethodName("结算方式1");
        detail.setSupplierCode("10000042");
        detail.setSupplierName("B供应商");
        detailList.add(detail);

        RejectApplyDetailHandleRequest detail1 = new RejectApplyDetailHandleRequest();
        detail1.setPurchaseGroupCode("1044");
        detail1.setPurchaseGroupName("奶粉组");
        detail1.setApplyType(1);
        detail1.setBarcode("112");
        detail1.setSkuCode("295110");
        detail1.setSkuName("爱亲贝贝外出服夏季卡通中性梭织九分裤9812203170584浅灰100");
        detail1.setProductId("product11111");
        detail1.setCategoryId("01050201");
        detail1.setCategoryName("乳糖不耐受奶粉");
        detail1.setBrandId("3001");
        detail1.setBrandName("惠氏");
        detail1.setProductType(0);
        detail1.setColorCode("c1");
        detail1.setColorName("颜色");
        detail1.setModelNumber("型号");
        detail1.setProductSpec("规格");
        detail1.setUnitCode("10036");
        detail1.setUnitName("单位");
        detail1.setStockCount(10);
        detail1.setSingleCount(5);
        detail1.setProductCount(5);
        detail1.setTaxRate(10);
        detail.setTransportCenterCode("1025");
        detail.setTransportCenterName("华北");
        detail.setWarehouseCode("1026");
        detail.setWarehouseName("华北销售库");
        detail1.setProductAmount(10L);
        detail1.setProductCost(10L);
        detail1.setBatchNo("123456788");
        detail1.setBatchCreateTime("2019-06-28");
        detail1.setBatchRemark("第二次入库");
        detail1.setSettlementMethodCode("S55");
        detail1.setSettlementMethodName("结算方式1");
        detail1.setSupplierCode("10000042");
        detail1.setSupplierName("B供应商");
        detailList.add(detail1);

        rejectApplyQueryRequest.setDetailList(detailList);
        goodsRejectService.rejectApply(rejectApplyQueryRequest);
    }

    @Test
    public void wolf() {
        LogisticsCenter logisticsCenter = logisticsCenterDao.selectByCenterName("西南");
        System.out.println(logisticsCenter);
        Warehouse warehouse = warehouseDao.selectByWarehouseName("华北销售库");
        System.out.println(warehouse);
    }

}
