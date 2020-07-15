package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.product.domain.request.outbound.DeliveryCallBackRequest;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.DpResponseContent;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundCallBackRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.OutboundRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.ReturnRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.ProfitLossRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.TransfersRequest;
import com.aiqin.ground.util.protocol.http.HttpResponse;

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
public interface OrderCallbackService {
    HttpResponse outboundOrder(OutboundRequest request);

    HttpResponse returnOrder(ReturnRequest request);

    HttpResponse transfersOrder(TransfersRequest request);

    HttpResponse profitLossOrder(ProfitLossRequest request);

    HttpResponse outboundOrderCallBack(OutboundCallBackRequest request);

    HttpResponse deliveryCallBack(DeliveryCallBackRequest request);

    void updateAiqinOrder(OutboundCallBackRequest request);

    HttpResponse deliveryCallBackSave(DpResponseContent request);
}
