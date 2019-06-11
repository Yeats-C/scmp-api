package com.aiqin.bms.scmp.api.product.domain.request.skuconfig;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/18 0018 16:00
 */
@Data
@ApiModel("查询sku配置请求对象")
public class QuerySkuConfigListReqVO extends PageReq {
    @ApiModelProperty("仓库code")
    private String warehouseCode;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("进货状态,0 不可进货 1可进货")
    private Byte purchaseStatus;

    @ApiModelProperty("销售状态,0 不可销售 1可销售")
    private Byte saleStatus;

    @ApiModelProperty("sku编码")
    private String skuCode;

}
