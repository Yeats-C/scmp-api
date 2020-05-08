package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.ApplyStatus;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.HandlingExceptionCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.mapper.ApplyProductSkuMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuInfoMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ApplyProductSkuServiceImp
 * @date 2019/3/29 16:06
 */
@Service
@Slf4j
public class ApplyProductSkuServiceImpl extends BaseServiceImpl implements ApplyProductSkuServiceProduct {

    @Autowired
    private ApplyProductSkuMapper applyProductSkuMapper;

    @Override
    public List<ApplyProductSku> getProductApplyList(List<String> skuCodes, Byte number) {
        return applyProductSkuMapper.getProductApplyList(skuCodes,number);
    }
}
