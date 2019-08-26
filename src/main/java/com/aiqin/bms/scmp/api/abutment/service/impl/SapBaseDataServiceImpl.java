package com.aiqin.bms.scmp.api.abutment.service.impl;

import com.aiqin.bms.scmp.api.abutment.domain.request.SapProductSku;
import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import org.springframework.stereotype.Service;

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
@Service
public class SapBaseDataServiceImpl implements SapBaseDataService {

    /**
     * 商品数据同步
     */
    public void productSynchronization() {
        SapProductSku sapProductSku = new SapProductSku();
        sapProductSku.setSapSkuCode("sku");
    }

    /**
     * 供应商数据同步
     */
    public void supplySynchronization() {

    }


    /**
     * 出入库数据同步
     */
    public void stockSynchronization() {

    }

    /**
     * 采购/退供数据同步
     */
    public void purchaseSynchronization() {

    }

    /**
     * 销售入库/销售退货数据同步
     */
    public void saleSynchronization() {

    }

}
