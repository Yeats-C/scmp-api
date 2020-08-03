package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

public interface OrderOutboundService {
    HttpResponse insertOutboundByOrderCode(List<String> orderCodes);
}
