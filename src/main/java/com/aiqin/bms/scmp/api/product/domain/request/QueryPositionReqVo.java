package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author knight.xie
 * @className QueryPositionReqVo
 * @date 2019/12/3 16:12
 */
@Data
@ApiModel("查询职位信息请求VO")
public class QueryPositionReqVo extends PageReq implements Serializable {

    @ApiModelProperty("accountId")
    private String accountId;

    @ApiModelProperty("personId")
    private String personId;

    @ApiModelProperty("personName")
    private String personName;

}
