package com.aiqin.bms.scmp.api.product.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("发运单推送京东数据（b2c发运接口推送）")
public class JdB2cDespatchTarget {

    @ApiModelProperty(value="订单类型")
    @JsonProperty("so_type")
    @NotBlank(message ="so_type不能为空" )
    private Integer soType;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    @NotBlank(message ="warehouse_code不能为空" )
    private String warehouseCode;

    @ApiModelProperty(value="订单号")
    @JsonProperty("order_id")
    @NotBlank(message ="orderId不能为空" )
    private String orderId;

    @ApiModelProperty(value="寄件人姓名，说明：不能为生僻字)")
    @JsonProperty("sender_ame")
    private String senderName;

    @ApiModelProperty(value="寄件人地址说明：不能为生僻字")
    @JsonProperty("sender_address")
    private String senderAddress;

    @ApiModelProperty(value="寄件人手机(寄件人电话、手机至少有一个不为空)")
    @JsonProperty("sender_mobile")
    private String senderMobile;

    @ApiModelProperty(value="收件人名称，说明：不能为生僻字")
    @JsonProperty("receive_name")
    @NotBlank(message = "receive_name不能为空")
    private String receiveName;

    @ApiModelProperty(value="收件人地址，说明：不能为生僻字")
    @JsonProperty("receive_address")
    @NotBlank(message = "receive_address不能为空")
    private String receiveAddress;

    @ApiModelProperty(value="收件人省")
    @JsonProperty("province")
    private String province;

    @ApiModelProperty(value="收件人市")
    @JsonProperty("city")
    private String city;

    @ApiModelProperty(value="收件人县")
    @JsonProperty("county")
    private String county;

    @ApiModelProperty(value="收件人镇")
    @JsonProperty("town")
    private String town;

    @ApiModelProperty(value="收件人手机号(收件人电话、手机至少有一个不为空)")
    @JsonProperty("receiveMobile")
    private String receiveMobile;

    @ApiModelProperty(value="收件人邮编，长度：6")
    @JsonProperty("postcode")
    private String postcode;

    @ApiModelProperty(value="包裹数(大于0，小于1000)")
    @JsonProperty("packageCount")
    private String packageCount;

    @ApiModelProperty(value="重量(单位：kg，保留小数点后两位)")
    @JsonProperty("weight")
    private String weight;


}
