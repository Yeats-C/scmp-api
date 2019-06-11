package com.aiqin.bms.scmp.api.product.domain.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname: ResponseWms
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/3/7
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("wms 接口接受实体")
public class ResponseWms implements Serializable {

    private static final long serialVersionUID = 1L;
    private String reason;
    private   String result;
    private   String  resultCode;
    private   String uniquerRequestNumber;
    private  List<ResponseParam>  responseParam;
}
