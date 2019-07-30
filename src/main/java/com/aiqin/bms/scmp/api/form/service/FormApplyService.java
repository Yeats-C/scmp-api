package com.aiqin.bms.scmp.api.form.service;


import com.aiqin.bms.scmp.api.form.domain.FormApplyRequest;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.platform.flows.client.domain.vo.FormCallBackVo;

public interface FormApplyService {

    HttpResponse submitActBaseProcess(FormApplyRequest request);

    String callback(FormCallBackVo formCallBackVo);

}
