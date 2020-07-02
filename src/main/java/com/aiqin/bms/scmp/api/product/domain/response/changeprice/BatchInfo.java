package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-05
 * @time: 19:46
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchInfo  implements Serializable {
    @ApiModelProperty("仓库批次号编码")
    @JsonProperty("warehouse_batch_number")
    private String warehouseBatchNumber;

    @ApiModelProperty("批次号编码")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty(value = "仓库批次号名称",hidden = true)
    @JsonProperty("warehouse_batch_name")
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

//    public String getWarehouseBatchName() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        return this.transportCenterName +"-"+ this.warehouseName +"-"+ this.warehouseBatchNumber +"-"+ sdf.format(this.productTime);
//    }
}
