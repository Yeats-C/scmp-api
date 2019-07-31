package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.domain.ProductSku;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundDetailRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.ReturnRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.OrderProductSkuResponse;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.service.OrderCallbackService;
import com.aiqin.bms.scmp.api.supplier.domain.response.rule.DetailRespVo;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplierRuleMapper;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
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
@Service
public class OrderCallbackServiceImpl implements OrderCallbackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCallbackServiceImpl.class);
    /**
     * 宁波熙耘
     */
    private final static String COMPANY_CODE = "09";
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private OrderInfoItemMapper orderInfoItemMapper;
    @Resource
    private SupplierRuleMapper supplierRuleMapper;
    @Resource
    private ProductSkuDao productSkuDao;
    @Override
    public HttpResponse outboundOrder(OutboundRequest request) {
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(request, orderInfo);
        DetailRespVo detailRespVo = supplierRuleMapper.findByCompanyCode(COMPANY_CODE);
        //订单体积计算系数
        BigDecimal orderVolumeCoefficient = new BigDecimal(1);
        //订单重量计算系数
        BigDecimal orderWeightCoefficient = new BigDecimal(1);
        if (detailRespVo != null) {
            orderWeightCoefficient = detailRespVo.getOrderWeightCoefficient();
            orderVolumeCoefficient = detailRespVo.getOrderVolumeCoefficient();
        }
        OrderProductSkuResponse productSku;
        List<OrderInfoItem> detailList = new ArrayList<>();
        OrderInfoItem orderInfoItem;
        for (OutboundDetailRequest outboundDetailRequest : request.getDetail()) {
            //查询商品信息
            orderInfoItem = new OrderInfoItem();
            BeanUtils.copyProperties(outboundDetailRequest,orderInfoItem);
            productSku = productSkuDao.selectSkuInfo(outboundDetailRequest.getSkuCode());
            if(productSku!=null){
                orderInfoItem.setSkuName(productSku.getProductName());
                orderInfoItem.setPictureUrl(productSku.getPictureUrl());
                orderInfoItem.setColorCode(productSku.getColorCode());
                orderInfoItem.setColorName(productSku.getProductName());
                orderInfoItem.setSpec(productSku.getSpec());
                orderInfoItem.setModel(productSku.getModel());
            }

            detailList.add(orderInfoItem);
        }
        Integer count = orderInfoMapper.insert(orderInfo);
        Integer detailCount = orderInfoItemMapper.insertList(detailList);

        LOGGER.info("添加订单:{}", count);
        return HttpResponse.success();
    }

    @Override
    public HttpResponse returnOrder(ReturnRequest request) {
        return null;
    }
}
