package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.purchase.domain.request.wms.CancelSource;
import com.aiqin.ground.util.protocol.http.HttpResponse;

/**
 * @author: zhao shuai
 * @create: 2020-06-03
 **/
public interface WmsCancelService {

    HttpResponse wmsCancel(CancelSource cancelSource);
}
