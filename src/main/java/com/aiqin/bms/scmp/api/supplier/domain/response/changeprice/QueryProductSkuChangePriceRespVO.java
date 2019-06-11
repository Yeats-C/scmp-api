package com.aiqin.bms.scmp.api.supplier.domain.response.changeprice;

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
 * @time: 14:17
 */
@Data
@ApiModel("变价列表返回vo")
public class QueryProductSkuChangePriceRespVO {

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("变价类型名称")
    private String changePriceName;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("与之前相同")
    private Integer applyStatus;

    @ApiModelProperty("操作人")
    private String operateBy;

    @ApiModelProperty("操作时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;
}
