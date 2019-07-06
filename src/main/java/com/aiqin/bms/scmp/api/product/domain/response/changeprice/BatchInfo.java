package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class BatchInfo {
    @ApiModelProperty("仓库批次号编码")
    private String warehouseBatchNumber;

    @ApiModelProperty("仓库批次号名称")
    private String warehouseBatchName;

    @ApiModelProperty("仓库编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("批次备注")
    private String batchRemark;

    @ApiModelProperty("生产日期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date productTime;

    public String getWarehouseBatchName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return this.transportCenterName +"-"+ this.warehouseName +"-"+ this.warehouseBatchNumber +"-"+ sdf.format(this.productTime);
    }
}
