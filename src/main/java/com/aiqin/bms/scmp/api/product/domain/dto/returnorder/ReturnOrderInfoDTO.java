package com.aiqin.bms.scmp.api.product.domain.dto.returnorder;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-27
 * @time: 17:08
 */
@Data
public class ReturnOrderInfoDTO extends ReturnOrderInfo {
    List<ReturnOrderInfoItemDTO> itemList;
    List<ReturnOrderInfoInspectionItem> items;
}
