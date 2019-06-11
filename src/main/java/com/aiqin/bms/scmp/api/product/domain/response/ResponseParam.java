package com.aiqin.bms.scmp.api.product.domain.response;

import lombok.Data;

/**
 * @Classname: ResponseParam
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/3/7
 * @Version 1.0
 * @Since 1.0
 */
@Data
public class ResponseParam {

    private  String  companyCode;
    private  String  channelCode;
    private  String  success;
    private  String  message;
    private  Long  quantity;
    private  String  itemCode;
}
