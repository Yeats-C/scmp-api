package com.aiqin.bms.scmp.api.product.domain.request.inbound;

import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundProduct;
import lombok.Data;

import java.util.List;

/**
 * @author HuangLong
 * @date 2019/1/17
 */
@Data
public class InboundSavePo {

   private Inbound inbound;

   private List<InboundProduct> inboundProducts;
}
