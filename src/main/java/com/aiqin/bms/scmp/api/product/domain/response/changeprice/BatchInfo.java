package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-05
 * @time: 19:46
 */
@Data
public class BatchInfo {
    @ApiModelProperty("仓库批次号编码")
    @JsonProperty("batch_code")
    private String warehouseBatchNumber;

    @ApiModelProperty(value = "仓库批次号名称",hidden = true)
    private String warehouseBatchName;

    @ApiModelProperty("仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty("生产日期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @JsonProperty("production_date")
    private Date productTime;

    public String getWarehouseBatchName() {
        return this.transportCenterName +"-"+ this.warehouseName +"-"+ this.warehouseBatchNumber +"-"+ this.productTime;
    }
}
