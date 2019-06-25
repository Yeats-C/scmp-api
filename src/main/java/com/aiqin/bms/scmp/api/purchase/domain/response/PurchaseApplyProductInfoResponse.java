package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-06-18
 **/
@Data
public class PurchaseApplyProductInfoResponse {

    @ApiModelProperty(value="商品采购件数")
    @JsonProperty("product_piece_sum")
    private Integer productPieceSum;

    @ApiModelProperty(value="商品单品总数")
    @JsonProperty("product_single_sum")
    private Integer productSingleSum;

    @ApiModelProperty(value="商品含税总金额")
    @JsonProperty("product_tax_sum")
    private Integer productTaxSum;

    @ApiModelProperty(value="实物采购件数")
    @JsonProperty("matter_piece_sum")
    private Integer matterPieceSum;

    @ApiModelProperty(value="实物返单品总数")
    @JsonProperty("matter_single_sum")
    private Integer matterSingleSum;

    @ApiModelProperty(value="实物返含税总金额")
    @JsonProperty("matter_tax_sum")
    private Integer matterTaxSum;

    @ApiModelProperty(value="赠品采购件数")
    @JsonProperty("gift_piece_sum")
    private Integer giftPieceSum;

    @ApiModelProperty(value="赠品单品总数")
    @JsonProperty("gift_single_sum")
    private Integer giftSingleSum;

    @ApiModelProperty(value="赠品含税总金额")
    @JsonProperty("gift_tax_sum")
    private Integer giftTaxSum;

    @ApiModelProperty(value="采购件数")
    @JsonProperty("piece_sum")
    private Integer pieceSum;

    @ApiModelProperty(value="单品总数")
    @JsonProperty("single_sum")
    private Integer singleSum;

    @ApiModelProperty(value="含税总金额")
    @JsonProperty("tax_sum")
    private Integer taxSum;
}
