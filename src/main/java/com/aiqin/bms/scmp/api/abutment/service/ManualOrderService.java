package com.aiqin.bms.scmp.api.abutment.service;

import com.aiqin.bms.scmp.api.abutment.domain.manual.ManualOrderRequest;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

public interface ManualOrderService {

    HttpResponse<List<String>> dlOrder(ManualOrderRequest request);

    HttpResponse<List<String>> orderDl(ManualOrderRequest request);
}
