package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.ScmpApiBootApplication;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.OrderInfoReqVO;
import com.aiqin.bms.scmp.api.purchase.service.OrderService;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.google.common.collect.Lists;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-14
 * @time: 12:04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= ScmpApiBootApplication.class)
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;
    @Test
    public void testSave() {
        EasyRandomParameters p = new EasyRandomParameters();
        p.setStringLengthRange(new EasyRandomParameters.Range<>(10,12));
        List<OrderInfoReqVO> list = Lists.newArrayList();
        for (int i = 0; i < 4; i++) {
            OrderInfoReqVO person = new EasyRandom(p).nextObject(OrderInfoReqVO.class);
            person.setOrderCode(IdSequenceUtils.getInstance().nextId()+"");
            list.add(person);
        }
        orderService.save(list);
    }
}