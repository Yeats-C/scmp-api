package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("申请备用仓库")
@Data
public class ApplyProductSkuConfigSpareWarehouse extends CommonBean {
    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("商品配置编码")
    private String configCode;

    @ApiModelProperty("商品配置申请编号")
    private String applyCode;

    @ApiModelProperty("物流中心(仓库)编码")
    private String transportCenterCode;

    @ApiModelProperty("物流中心(仓库)名称")
    private String transportCenterName;

    @ApiModelProperty("使用顺序")
    private Integer useOrder;
}