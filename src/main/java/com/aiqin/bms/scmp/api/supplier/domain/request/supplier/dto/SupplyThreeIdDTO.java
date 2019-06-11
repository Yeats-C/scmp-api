package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto;

import lombok.Data;

/**
 * @功能说明:根据供货单位编码查询出三张申请表ID
 * @author: wangxu
 * @date: 2018/12/13 0013 10:21
 */
@Data
public class SupplyThreeIdDTO {
    /**
     * 申请供货单位id
     */
    private Long applySupplyId;

}
