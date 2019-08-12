package com.aiqin.bms.scmp.api.product.domain.request.changeprice;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-23
 * @time: 14:24
 */
@ApiModel("列表查询vo")
@Data
public class QueryProductSkuChangePriceReqVO extends PageReq {
    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;

    @ApiModelProperty("变价类型编码")
    private String changePriceType;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("与之前相同")
    private Integer applyStatus;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("是否含有区域0否1是")
    private Integer beContainArea;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("标记不查供应商平台添加的")
    private Boolean flag;
}
