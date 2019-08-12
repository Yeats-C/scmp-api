//package com.aiqin.bms.scmp.api.dao.test.callback;
//
//import com.aiqin.bms.scmp.api.SpringBootTestContext;
//import com.aiqin.bms.scmp.api.purchase.service.impl.
//import com.google.common.collect.Lists;
//import lombok.Data;
//import org.junit.Test;
//
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
///**
// * <p>
// * ━━━━━━神兽出没━━━━━━
// * 　　┏┓　　　┏┓+ +
// * 　┏┛┻━━━┛┻┓ + +
// * 　┃　　　　　　　┃
// * 　┃　　　━　　　┃ ++ + + +
// * ████━████ ┃+
// * 　┃　　　　　　　┃ +
// * 　┃　　　┻　　　┃
// * 　┃　　　　　　　┃
// * 　┗━┓　　　┏━┛
// * 　　　┃　　　┃                  神兽保佑, 永无BUG!
// * 　　　┃　　　┃
// * 　　　┃　　　┃     Code is far away from bug with the animal protecting
// * 　　　┃　 　　┗━━━┓
// * 　　　┃ 　　　　　　　┣┓
// * 　　　┃ 　　　　　　　┏┛
// * 　　　┗┓┓┏━┳┓┏┛
// * 　　　　┃┫┫　┃┫┫
// * 　　　　┗┻┛　┗┻┛
// * ━━━━━━感觉萌萌哒━━━━━━
// * <p>
// * <p>
// * 思维方式*热情*能力
// */
//public class callback extends SpringBootTestContext {
//    @Data
//    class  UserTrade{
//        private String userId;
//        private String tradeNo;
//
//        public UserTrade(String userId, String tradeNo) {
//            this.userId = userId;
//            this.tradeNo = tradeNo;
//        }
//    }
//    @Data
//    class BaseOrder{
//        private String userId;
//        private String tradeNo;
//    }
//    @Test
//    public void listGroupBy(){
//        List<BaseOrder> orderList = Lists.newArrayList();
//
////分组
//        Map<UserTrade, List<BaseOrder>> collect = orderList.stream().
//                collect(Collectors.groupingBy(new Function<BaseOrder, UserTrade>() {
//                    @Override
//                    public UserTrade apply(BaseOrder baseOrder) {
//                        return new UserTrade(baseOrder.getUserId(), baseOrder.getTradeNo());
//                    }
//                });
//    }
//
//}
