package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("商品配置备用仓库")
@Data
public class ProductSkuConfigSpareWarehouse extends CommonBean {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("商品配置编码")
    private String configCode;

    @ApiModelProperty("物流中心(仓库)编码")
    private String transportCenterCode;

    @ApiModelProperty("物流中心(仓库)名称")
    private String transportCenterName;

    @ApiModelProperty("使用顺序")
    private Integer useOrder;

}