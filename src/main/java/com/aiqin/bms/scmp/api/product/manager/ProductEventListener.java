package com.aiqin.bms.scmp.api.product.manager;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.bms.scmp.api.product.dao.ProductDictionaryInfoDao;
import com.aiqin.bms.scmp.api.product.domain.event.ProductEvent;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductEventListener {
    @Autowired
    private ProductDictionaryInfoDao productDictionaryInfoDao;

    @EventListener
    @Async("myTaskAsyncPool")
    public void handleOrderEvent(ProductEvent event) {
        switch (event.getType()) {
            case PRODUCT_DICTIONARY:
                 updateAndCode(event.getName(),event.getCode());
                break;
            default:
                break;
        }
    }
    @Transactional
    public int updateAndCode(String name, String code) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(code)) {
            int k =productDictionaryInfoDao .updateAndCode(name, code);
            if (k > 0) {
                return k;
            } else {
                throw new GroundRuntimeException("字典详细更新失败");
            }
        } else {
            throw new GroundRuntimeException("name和code 不能为空");
        }
    }
}
