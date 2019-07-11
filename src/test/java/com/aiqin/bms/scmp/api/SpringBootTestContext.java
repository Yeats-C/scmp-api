package com.aiqin.bms.scmp.api;

import com.aiqin.bms.scmp.api.product.domain.pojo.PriceChannelItem;
import com.aiqin.bms.scmp.api.product.domain.request.price.SkuPriceDraftReqVO;
import com.aiqin.bms.scmp.api.product.service.PriceChannelService;
import com.aiqin.bms.scmp.api.product.service.impl.ProductSkuPriceInfoServiceImpl;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= ScmpApiBootApplication.class)// 指定spring-boot的启动类
public class SpringBootTestContext {

    @Autowired
    private PriceChannelService priceChannelService;

    @Test
    public void test1(){
        List<String> s = Lists.newArrayList();
        s.add("1006");
        List<PriceChannelItem> priceChannelItems = priceChannelService.selectByChannelCodes(s);
        System.out.println(priceChannelItems);
    }
    @Autowired
    ProductSkuPriceInfoServiceImpl productSkuPriceInfoService;
    @Test
    public void test2() {
        List<SkuPriceDraftReqVO> skuPriceDraftReqVOS = Lists.newArrayList();
        SkuPriceDraftReqVO o1 = new SkuPriceDraftReqVO();
        o1.setSkuCode("1");
        SkuPriceDraftReqVO o2 = new SkuPriceDraftReqVO();
        o2.setSkuCode("2");
        SkuPriceDraftReqVO o3 = new SkuPriceDraftReqVO();
        o3.setSkuCode("3");
        SkuPriceDraftReqVO o4 = new SkuPriceDraftReqVO();
        o4.setSkuCode("4");
        skuPriceDraftReqVOS.add(o1);
        skuPriceDraftReqVOS.add(o2);
        skuPriceDraftReqVOS.add(o3);
        skuPriceDraftReqVOS.add(o4);
        productSkuPriceInfoService.saveSkuPriceDraft(skuPriceDraftReqVOS);
    }
}
