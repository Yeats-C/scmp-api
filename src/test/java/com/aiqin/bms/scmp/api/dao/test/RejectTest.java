package com.aiqin.bms.scmp.api.dao.test;

import com.aiqin.bms.scmp.api.SpringBootTestContext;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectStockRequest;
import com.aiqin.bms.scmp.api.purchase.service.GoodsRejectService;
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

    @Test
    public void finishStock(){
        RejectStockRequest request = new RejectStockRequest();
        request.setRejectRecordCode("1");
        request.setOutStockTime(new Date());
        goodsRejectService.finishStock(request);

    }
    @Test
    public void rejectApply(){
        RejectApplyRequest rejectApplyQueryRequest = new RejectApplyRequest();
        rejectApplyQueryRequest.setCreateById("10001");
        rejectApplyQueryRequest.setCreateByName("小明");
        rejectApplyQueryRequest.setPurchaseGroupCode("1044");
        rejectApplyQueryRequest.setPurchaseGroupName("奶粉组");
        rejectApplyQueryRequest.setCompanyName("爱亲");
        rejectApplyQueryRequest.setApplyType(1);
        rejectApplyQueryRequest.setCompanyCode("01");
        List<RejectApplyRecordDetail> detailList = new ArrayList<>();
        RejectApplyRecordDetail detail = new RejectApplyRecordDetail();
        detail.setPurchaseGroupCode("1044");
        detail.setPurchaseGroupName("奶粉组");
        detail.setApplyType(1);
        detail.setApplyRecordStatus(1);
        detail.setBarcode("112");
        detail.setSkuCode("001");
        detail.setSkuName("商品1");
        detail.setProductId("product11111");
        detail.setCategoryId("f1");
        detail.setCategoryName("分类");
        detail.setBrandId("b1");
        detail.setBrandName("品牌");
        detail.setProductType(0);
        detail.setColorCode("c1");
        detail.setColorCode("颜色");
        detail.setModelNumber("型号");
        detail.setProductSpec("规格");
        detail.setUnitCode("u1");
        detail.setUnitName("单位");
        detail.setStockCount(10);
        detail.setSingleCount(5);
        detail.setProductCount(5);
        detail.setTaxRate(10);
        detail.setTransportCenterCode("ck14");
        detail.setTransportCenterName("仓库");
        detail.setWarehouseCode("kf1421");
        detail.setWarehouseName("库房");
        detail.setProductAmount(10L);
        detail.setProductTotalAmount(50L);
        detail.setProductCost(10L);
        detail.setBatchNo("batch01");
        detail.setBatchCreateTime(new Date());
        detail.setBatchRemark("batch备注");
        detail.setSettlementMethodCode("s1");
        detail.setSettlementMethodName("结算方式");
        detail.setSupplierCode("1000027");
        detail.setSupplierName("供应商1");
        detailList.add(detail);
        rejectApplyQueryRequest.setDetailList(detailList);
        goodsRejectService.rejectApply(rejectApplyQueryRequest);
    }
}
