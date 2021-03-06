package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChangePriceInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

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

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("创建人")
    private String updateBy;

    @ApiModelProperty("变价类型名称")
    private String changePriceName;

    @ApiModelProperty("变价类型名称")
    private String changePriceType;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("与之前相同")
    private Integer applyStatus;

    @ApiModelProperty("是否含有区域0否1是")
    private Integer beContainArea;

    @ApiModelProperty("操作人")
    private String operateBy;

    @ApiModelProperty("流程编号")
    private String formNo;

    @ApiModelProperty("操作时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    @ApiModelProperty("sku信息")
    private List<ProductSkuChangePriceInfo> skuList;
}
