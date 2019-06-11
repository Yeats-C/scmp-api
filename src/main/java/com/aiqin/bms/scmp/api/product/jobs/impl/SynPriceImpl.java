package com.aiqin.bms.scmp.api.product.jobs.impl;

import com.aiqin.bms.scmp.api.base.CommonConstant;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuChangePriceInfoMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuChangePriceMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPriceInfoLogMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPriceInfoMapper;
import com.aiqin.bms.scmp.api.product.domain.dto.changeprice.ProductSkuChangePriceDTO;
import com.aiqin.bms.scmp.api.product.domain.dto.changeprice.ProductSkuPriceInfoDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChangePriceInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfoLog;
import com.aiqin.bms.scmp.api.product.jobs.SynPrice;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 * 更改正式表价格状态
 * 每天凌晨1点执行
 * @author: NullPointException
 * @date: 2019-05-27
 * @time: 14:23
 */
@Component
@Slf4j
public class SynPriceImpl implements SynPrice {

    @Autowired
    private ProductSkuPriceInfoMapper productSkuPriceInfoMapper;

    @Autowired
    private ProductSkuPriceInfoLogMapper productSkuPriceInfoLogMapper;

    @Autowired
    private ProductSkuChangePriceMapper productSkuChangePriceMapper;

    @Autowired
    private ProductSkuChangePriceInfoMapper productSkuChangePriceInfoMapper;


    @Scheduled(cron = "0 0 1 1/1 * ?")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePriceStatusForEffective() throws Exception {
        log.info("开始执行updatePriceStatusForEffective定时任务");
        List<ProductSkuPriceInfoDTO> list = productSkuPriceInfoMapper.selectByPriceStatusAndDate();
        if (CollectionUtils.isEmpty(list)) {
            log.info("需要更新的数据为空，updatePriceStatusForEffective定时任务执行完成！");
            return;
        }
        List<ProductSkuPriceInfo> priceInfos = BeanCopyUtils.copyList(list, ProductSkuPriceInfo.class);
        List<ProductSkuPriceInfoLog> logs = Lists.newArrayList();
        list.forEach(o -> {
            ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(o.getCode(), o.getPriceTax(), o.getPriceNoTax(), o.getTax(), o.getEffectiveTimeStart(), o.getEffectiveTimeEnd(), 1, o.getCreateBy(), new Date());
            logs.add(log);
            o.setBeSynchronous(1);
        });
        int i1 = productSkuPriceInfoMapper.updateBatch(priceInfos);
        log.info("需要更新日志条数[{}]，插入更新数据条数：[{}]", list.size(), i1);
        int i = productSkuPriceInfoLogMapper.insertBatch(logs);
        log.info("需要插入日志条数[{}]，插入日志数据条数：[{}]", logs.size(), i);
        log.info("updatePriceStatusForEffective定时任务执行完成");
    }

    @Scheduled(cron = "0 0 1 1/1 * ?")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePriceStatusForUnEffective() throws Exception {
        log.info("开始执行updatePriceStatusForUnEffective定时任务");
        List<ProductSkuPriceInfoDTO> list = productSkuPriceInfoMapper.selectUnEffectiveData();
        if (CollectionUtils.isEmpty(list)) {
            log.info("需要更新的数据为空，updatePriceStatusForUnEffective定时任务执行完成！");
            return;
        }
        List<ProductSkuPriceInfo> priceInfos = BeanCopyUtils.copyList(list, ProductSkuPriceInfo.class);
        List<ProductSkuPriceInfoLog> logs = Lists.newArrayList();
        int i1 = productSkuPriceInfoMapper.updateBatch(priceInfos);
        log.info("需要更新条数[{}]，插入更新数据条数：[{}]", list.size(), i1);
        int i = productSkuPriceInfoLogMapper.insertBatch(logs);
        log.info("需要插入日志条数[{}]，插入日志数据条数：[{}]", logs.size(), i);
        log.info("updatePriceStatusForUnEffective定时任务执行完成");
    }

    @Scheduled(cron = "0 0 1 1/1 * ?")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePriceData() throws Exception {
        log.info("开始执行updatePriceStatusForEffective定时任务");
        List<ProductSkuChangePriceDTO> list = productSkuChangePriceMapper.selectByPriceStatusAndDate();
        if (CollectionUtils.isEmpty(list)) {
            log.info("查询出的数据为空，定时任务结束！");
            return;
        }
        List<ProductSkuPriceInfoLog> logs = Lists.newArrayList();
        List<ProductSkuPriceInfo> priceInfoList = Lists.newArrayList();
        List<ProductSkuChangePriceInfo> changePriceInfos = Lists.newArrayList();
        for (ProductSkuChangePriceDTO dto : list) {
            List<ProductSkuChangePriceInfo> infos = dto.getInfos();
            List<String> codes = infos.stream().map(ProductSkuChangePriceInfo::getOfficialCode).distinct().collect(Collectors.toList());
            List<ProductSkuPriceInfo> priceInfos = productSkuPriceInfoMapper.selectByCodes(codes);
            Map<String, ProductSkuPriceInfo> infoMap = priceInfos.stream().collect(Collectors.toMap(o -> o.getCode(), Function.identity()));
            if (CommonConstant.PURCHASE_CHANGE_PRICE.equals(dto.getChangePriceType())) {
                for (ProductSkuChangePriceInfo info : infos) {
                    ProductSkuPriceInfo priceInfo = infoMap.get(info.getOfficialCode());
                    ProductSkuPriceInfo copy = BeanCopyUtils.copy(priceInfo, ProductSkuPriceInfo.class);
                    priceInfo.setApplyCode(info.getCode());
                    priceInfo.setPriceTax(info.getPurchasePriceNew());
                    priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(info.getPurchasePriceNew(), 0L));
                    priceInfo.setUpdateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
                    priceInfo.setUpdateTime(new Date());
                    priceInfo.setTax(0L); //TODO 需要从商品上取
                    ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),null,1,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
                    ProductSkuPriceInfoLog log2 = new ProductSkuPriceInfoLog(copy.getCode(),copy.getPriceTax(),copy.getPriceNoTax(),copy.getTax(),priceInfo.getEffectiveTimeStart(),new Date(),2,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
                    logs.add(log);
                    logs.add(log2);
                    info.setBeSynchronize(1);
                    priceInfoList.add(priceInfo);
                }
            } else if (CommonConstant.SALE_CHANGE_PRICE.equals(dto.getChangePriceType())) {
                for (ProductSkuChangePriceInfo info : infos) {
                    ProductSkuPriceInfo priceInfo = infoMap.get(info.getOfficialCode());
                    ProductSkuPriceInfo copy = BeanCopyUtils.copy(priceInfo, ProductSkuPriceInfo.class);
                    priceInfo.setApplyCode(info.getCode());
                    priceInfo.setPriceTax(info.getNewPrice());
                    priceInfo.setPriceNoTax(Calculate.computeNoTaxPrice(info.getNewPrice(), 0L));
                    priceInfo.setTax(0L); //TODO 需要从商品上取
                    priceInfo.setUpdateBy(Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()));
                    priceInfo.setUpdateTime(new Date());
                    ProductSkuPriceInfoLog log = new ProductSkuPriceInfoLog(priceInfo.getCode(),priceInfo.getPriceTax(),priceInfo.getPriceNoTax(),priceInfo.getTax(),priceInfo.getEffectiveTimeStart(),null,1,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
                    ProductSkuPriceInfoLog log2 = new ProductSkuPriceInfoLog(copy.getCode(),copy.getPriceTax(),copy.getPriceNoTax(),copy.getTax(),priceInfo.getEffectiveTimeStart(),new Date(),2,Optional.ofNullable(dto.getUpdateBy()).orElse(dto.getCreateBy()),new Date());
                    logs.add(log);
                    logs.add(log2);
                    info.setBeSynchronize(1);
                    priceInfoList.add(priceInfo);
                }
            } else {
                log.error("数据错误：[{}]");
                continue;
            }
            changePriceInfos.addAll(infos);
        }
        int i1 = productSkuPriceInfoMapper.updateBatch(priceInfoList);
        log.info("需要更新条数[{}]，插入更新数据条数：[{}]", list.size(), i1);
        int i = productSkuPriceInfoLogMapper.insertBatch(logs);
        log.info("需要插入日志条数[{}]，插入日志数据条数：[{}]", logs.size(), i);
        int i2 = productSkuChangePriceInfoMapper.updateBatch(changePriceInfos);
        log.info("需要更新条数[{}]，更新数据条数：[{}]", changePriceInfos.size(), i2);
        log.info("updatePriceStatusForEffective定时任务结束！");
    }
}
