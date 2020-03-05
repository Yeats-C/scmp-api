package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseApplyCurrencyResponse extends PurchaseApply {

    @ApiModelProperty(value="商品件数")
    @JsonProperty("product_piece")
    private Long productPiece;

    @ApiModelProperty(value="实物返件数")
    @JsonProperty("return_piece")
    private Long returnPiece;

    @ApiModelProperty(value="赠品件数")
    @JsonProperty("gift_piece")
    private Long giftPiece;

    @ApiModelProperty(value="总件数")
    @JsonProperty("total_piece")
    private Long totalPiece;

    @ApiModelProperty(value="文件信息")
    @JsonProperty("file_list")
    private List<FileRecord> files;
}
