package com.aiqin.bms.scmp.api;

import com.aiqin.bms.scmp.api.product.domain.pojo.PriceChannelItem;
import com.aiqin.bms.scmp.api.product.service.PriceChannelService;
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
}
