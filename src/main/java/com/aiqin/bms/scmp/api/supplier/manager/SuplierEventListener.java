package com.aiqin.bms.scmp.api.supplier.manager;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.ApplyDeliveryInfoDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.ApplySettlementInfoDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.ApplySupplyCompanyAcctDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.ApplySupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.event.SupplierEvent;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 监听器
 */
@Component
public class SuplierEventListener {
    @Autowired
    private SupplierDictionaryInfoDao supplierDictionaryInfoDao;
    @Autowired
    private ApplySupplyCompanyDao applySupplyCompanyDao;
    @Autowired
    private ApplySettlementInfoDao applySettlementInfoDao;
    @Autowired
    private ApplyDeliveryInfoDao applyDeliveryInfoDao;
    @Autowired
    private ApplySupplyCompanyAcctDao applySupplyCompanyAcctDao;
    @Async("taskExecutor")
    @EventListener
    public void handleOrderEvent(SupplierEvent event) {
        switch (event.getType()) {
            case APPLY_MANUFACTURER:
                break;
            case APPLY_SUPPLIER:
                updateApplySupplyCompany(event.getName(),event.getCode());
                break;
            case APPLY_SUPPLY_COMPANY:
                updateSupplyCompany(event.getName(),event.getCode());
                break;
            case MANUFACTURER:
                break;
            case SUPPLIER:
                break;
            case SUPPLIER_DICTIONARY:
                updateAndCode(event.getName(), event.getCode());
                break;
            case SUPPLY_COMPANY:
                break;
            default:
                break;
        }
    }
    @Transactional
    public int updateAndCode(String name, String code) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(code)) {
            int k = supplierDictionaryInfoDao.updateAndCode(name, code);
            if (k > 0) {
                return k;
            } else {
                throw new GroundRuntimeException("字典详细更新失败");
            }
        } else {
            throw new GroundRuntimeException("name和code 不能为空");
        }
    }
    @Transactional
    public int updateApplySupplyCompany(String names, String codes) {
        if (StringUtils.isNotBlank(names) && StringUtils.isNotBlank(codes)) {
            int k = applySupplyCompanyDao.updateAndCode(names, codes);
            if (k > 0) {
                return k;
            } else {
                throw new GroundRuntimeException("供货商更新失败");
            }
        } else {
            throw new GroundRuntimeException("name和code 不能为空");
        }
    }
    //同步更新 冗余供货单位name 结算，账户，发货信息
   @Transactional
   public int  updateSupplyCompany(String name,String code){
      if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(code)){
         applySettlementInfoDao.updateAndCode(name, code);
          applyDeliveryInfoDao.updateAndCode(name, code);
         int k= applySupplyCompanyAcctDao.updateAndCode(name, code);
         if(k>0){
             return k;
         }else {
             throw new GroundRuntimeException("供货单位更新名称失败");
         }
      }else {
          throw new GroundRuntimeException("name和code 不能为空");
      }
   }

}
