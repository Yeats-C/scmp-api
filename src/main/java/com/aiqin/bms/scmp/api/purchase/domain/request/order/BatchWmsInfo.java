package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@ApiModel(value = "批次信息")
@Data
public class BatchWmsInfo {

    @ApiModelProperty(value = "批次号")
    private String batch_code;

    @NotBlank(message = "sku编码不能为空")
    @ApiModelProperty(value = "sku编码")
    private String sku_code;

    @NotBlank(message = "sku名称不能为空")
    @ApiModelProperty(value = "sku名称")
    private String sku_name;

    @NotBlank(message = "生产日期不能为空")
    @ApiModelProperty(value = "生产日期")
    private String prodcut_date;

    @ApiModelProperty(value = "批次备注")
    private String batch_remark;

    @ApiModelProperty(value = "最小单位数量")
    private Long total_count;

    @ApiModelProperty(value = "实际最小单位数量")
    private Long actual_total_count;

    @ApiModelProperty(value = "行号")
    private Integer line_code;

    @ApiModelProperty(value = "过期日期")
    private String be_overdue_data;

}
