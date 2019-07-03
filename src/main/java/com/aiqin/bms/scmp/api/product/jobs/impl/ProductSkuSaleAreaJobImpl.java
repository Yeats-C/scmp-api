package com.aiqin.bms.scmp.api.product.jobs.impl;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.domain.dto.salearea.ApplyProductSkuSaleAreaMainDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaMain;
import com.aiqin.bms.scmp.api.product.jobs.ProductSkuSaleAreaJob;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuSaleAreaMainMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuSaleAreaMainMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSaleAreaService;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-06
 * @time: 13:20
 */
@Slf4j
@Component
public class ProductSkuSaleAreaJobImpl implements ProductSkuSaleAreaJob {

    @Autowired
    private ProductSkuSaleAreaMainMapper productSkuSaleAreaMainMapper;

    @Autowired
    private ApplyProductSkuSaleAreaMainMapper applyProductSkuSaleAreaMainMapper;

    @Autowired
    private ProductSaleAreaService productSaleAreaService;

    @Override
    @Scheduled(cron = "0 0 1 1/1 * ?")
//    @Scheduled(cron = "* * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void updateSaleAreaStatus() {
        List<ProductSkuSaleAreaMain> list = productSkuSaleAreaMainMapper.selectListByStatusAndDate();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(o -> o.setBeEffective(1));
        int i = productSkuSaleAreaMainMapper.updateByCode(list);
        if (i != list.size()) {
            log.error("需要更新的条数：[{}] 实际更新的条数: [{}]", list.size(), i);
            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, "更新失败"));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 0 1 1/1 * ?")
//    @Scheduled(cron = "* * * * * ?")
    public void updateSaleAreaData() throws Exception {
        List<ApplyProductSkuSaleAreaMainDTO> list = applyProductSkuSaleAreaMainMapper.selectListByStatusAndDate();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        productSaleAreaService.updateBatchForOfficial(null, list);
        //更新状态
        List<String> strings = list.stream().map(ApplyProductSkuSaleAreaMainDTO::getCode).distinct().collect(Collectors.toList());
        int i = applyProductSkuSaleAreaMainMapper.updateByCode(strings);
        if (i != list.size()) {
            log.error("需要更新申请表的条数：[{}] 实际更新的条数: [{}]", list.size(), i);
            throw new BizException(MessageId.create(Project.PRODUCT_API, 98, "更新失败"));
        }
    }
}
