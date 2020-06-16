package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplierFile;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyUseTagRecord;
import com.aiqin.bms.scmp.api.supplier.domain.request.approvalfile.ApprovalFileInfoReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @功能说明:供货单位申请详情返回对象
 * @author: wangxu
 * @date: 2018/12/7 0007 16:46
 */
@ApiModel("供货单位申请详情返回对象")
@Data
public class ApplySupplyComDetailRespVO {
    @ApiModelProperty("申请信息")
    private ApplyInfoRespVO applyInfoRespVO;

    @ApiModelProperty("属性")
    private String property;

    @ApiModelProperty("供货单位申请信息")
    private ApplySupplyComInfoRespVO applySupplyComInfoRespVO;

    @ApiModelProperty("发货申请信息")
    private List<ApplyDeliveryInfoRespVO> applyDeliveryInfoRespVO;

    @ApiModelProperty("供应商申请文件列表")
    private List<ApplySupplierFile> applySupplierFileList;

    @ApiModelProperty("采购组")
    private List<SupplyCompanyPurchaseGroupResVo> purchaseGroupVos;

    @ApiModelProperty("供货单位账户申请")
    private ApplySupplyComAcctInfoRespVO applySupplyComAcctInfoRespVO;

    @ApiModelProperty("是否展示供货单位账户信息")
    private Byte showAccount;


    @ApiModelProperty("操作日志列表")
    private List<LogData> logDataList;

    @ApiModelProperty(value = "标签",hidden = true)
    private List<ApplyUseTagRecord> applyUseTagRecords;
}
