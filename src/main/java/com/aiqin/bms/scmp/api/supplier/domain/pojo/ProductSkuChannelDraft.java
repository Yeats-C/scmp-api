package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("SKU覆盖渠道")
@Data
public class ProductSkuChannelDraft {
    private String priceChannelCode;

    private String priceChannelName;

}