package com.aiqin.bms.scmp.api.dao.test.callback;

import com.aiqin.bms.scmp.api.SpringBootTestContext;
import com.google.common.collect.Lists;
import lombok.Data;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
public class callback extends SpringBootTestContext {
    @Test
    public void listGroupBy() {
        List<BaseOrder> orderList = Lists.newArrayList();
        BaseOrder baseOrder = new BaseOrder(-1,"23");

        BaseOrder baseOrder1 = new BaseOrder(-2,"23");

        BaseOrder baseOrder2 = new BaseOrder(3,"23");

        orderList.add(baseOrder);
        orderList.add(baseOrder1);
        orderList.add(baseOrder2);
        Map<Integer, List<BaseOrder>> collect = orderList.stream().
                collect(Collectors.groupingBy(new Function<BaseOrder, Integer>() {
                    @Override
                    public Integer apply(BaseOrder baseOrder) {
                        if (baseOrder.count > 0) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }));

        for (List<BaseOrder> baseOrders : collect.values()) {
            System.out.println(baseOrders.toString());
        }
    }

    @Data
    class BaseOrder {
        private Integer count;
        private String name;

        public BaseOrder(Integer count, String name) {
            this.count = count;
            this.name = name;
        }
    }

}
