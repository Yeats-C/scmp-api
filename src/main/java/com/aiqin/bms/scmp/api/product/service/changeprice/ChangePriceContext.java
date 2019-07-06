package com.aiqin.bms.scmp.api.product.service.changeprice;

import com.aiqin.bms.scmp.api.product.service.SaveChangePrice;
import com.aiqin.bms.scmp.api.product.service.changeprice.annotation.ChangePrice;
import com.aiqin.bms.scmp.api.product.service.changeprice.enumerate.ChangePriceType;
import com.aiqin.bms.scmp.api.util.SpringContextUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-12
 * @time: 10:52
 */
@Component
public class ChangePriceContext {

    @Autowired
    private SpringContextUtil springContextUtil;

    public static Map<ChangePriceType, SaveChangePrice> beanContent = Maps.newConcurrentMap();

    //工厂将 Spring 装配的相关的 Bean 用 Map 保存起来
    @PostConstruct
    public void init() {
        Map<String, Object> beanMap = springContextUtil.getContext().getBeansWithAnnotation(ChangePrice.class);
        for (Object workFlow : beanMap.values()) {
            ChangePrice annotation = workFlow.getClass().getAnnotation(ChangePrice.class);
            beanContent.put(annotation.value(), (SaveChangePrice)workFlow);
        }
    }
    public static SaveChangePrice createSaveChangePrice(ChangePriceType priceType) {
        return beanContent.get(priceType);
    }
}
