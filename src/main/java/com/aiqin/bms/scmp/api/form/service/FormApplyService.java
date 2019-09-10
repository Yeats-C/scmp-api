package com.aiqin.bms.scmp.api.form.service;


import com.aiqin.bms.scmp.api.form.domain.FormApplyRequest;
import com.aiqin.ground.util.protocol.http.HttpResponse;

public interface FormApplyService {

    HttpResponse submitActBaseProcess(FormApplyRequest request);

}
