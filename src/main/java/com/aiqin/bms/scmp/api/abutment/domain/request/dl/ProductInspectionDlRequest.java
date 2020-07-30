package com.aiqin.bms.scmp.api.abutment.domain.request.dl;

import com.aiqin.bms.scmp.api.abutment.domain.request.product.ProductInspectionRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductInspectionDlRequest {

    @ApiModelProperty(value="单据编码")
    @JsonProperty("document_code")
    private String documentCode;

    @ApiModelProperty(value="质检报告信息")
    @JsonProperty("list")
    private List<ProductInspectionRequest> list;
}
