package com.aiqin.bms.scmp.api.product.domain.converter.returnorder;

import com.aiqin.bms.scmp.api.product.domain.dto.returnorder.ReturnOrderInfoDTO;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-27
 * @time: 17:54
 */
public class ReturnOrderToInboundConverter implements Converter<ReturnOrderInfoDTO, List<InboundReqSave>> {

    @Override
    public List<InboundReqSave> convert(ReturnOrderInfoDTO source) {
        //根据仓分组
        List<InboundReqSave> list = Lists.newArrayList();
        return list;
    }
}
