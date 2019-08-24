package com.aiqin.bms.scmp.api.supplier.domain.request.score;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryScoreReqVo
 * @date 2019/5/23 15:41

 */
@ApiModel("查询评分VO")
@Data
public class QueryScoreReqVo extends PageReq {

    @ApiModelProperty("评分编号")
    private String scoreCode;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;

    @ApiModelProperty(value = "公司编码",hidden = true)
    private String companyCode;




}
