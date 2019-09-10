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

    @Autowired
    private ProductSkuInfoMapper productSkuInfoMapper;

    @Autowired
    private ProductSkuCheckoutService productSkuCheckoutService;

    @Autowired
    private ProductSkuPicturesService productSkuPicturesService;

    @Autowired
    private ProductSkuPriceService productSkuPriceService;

    @Autowired
    private ProductSkuPicDescService productSkuPicDescService;

    @Autowired
    private ProductSkuPurchaseInfoService productSkuPurchaseInfoService;

    @Autowired
    private ProductSkuDisInfoService productSkuDisInfoService;

    @Autowired
    private ProductSkuSalesInfoService productSkuSalesInfoService;

    @Autowired
    private ProductSkuBoxPackingService productSkuBoxPackingService;

    @Autowired
    private ProductSkuSupplyUnitService productSkuSupplyUnitService;

    @Autowired
    private ProductSkuManufacturerService productSkuManufacturerService;

    @Autowired
    private ProductSkuFileService productSkuFileService;

    @Autowired
    private ProductSkuConfigService productSkuConfigService;

    @Autowired
    private ProductSkuInspReportService productSkuInspReportService;

    @Override
    public String productSkuFlow(List<ApplyProductSku> applyProductSkus, WorkFlowCallbackVO vo) {
        if (CollectionUtils.isNotEmpty(applyProductSkus)) {
            if (ApplyStatus.APPROVAL_SUCCESS.getNumber().equals(vo.getApplyStatus())) {
                applyProductSkus.forEach(applyProductSku -> {
                    applyProductSku.setApplyStatus(ApplyStatus.APPROVAL_SUCCESS.getNumber());
                    applyProductSku.setAuditorBy(vo.getApprovalUserName());
                    applyProductSku.setAuditorTime(new Date());
                });
                applyProductSkuMapper.updateList(applyProductSkus);
                List<ProductSkuInfo> productSkus = Lists.newArrayList();
                try {
                   productSkus = BeanCopyUtils.copyList(applyProductSkus, ProductSkuInfo.class);
                } catch (Exception e) {
                    log.error(Global.ERROR, e);
                    log.info("转化数据SKU数据失败");
                }


                //批量新增SKU信息
                productSkuInfoMapper.insertBatch(productSkus);
                //批量操作sku相关申请信息
                productSkuCheckoutService.saveApply(applyProductSkus);
                productSkuPicturesService.saveApplyList(applyProductSkus);
                productSkuPriceService.saveApplyList(applyProductSkus);
                productSkuPicDescService.saveApplyList(applyProductSkus);
                productSkuPurchaseInfoService.saveApplyList(applyProductSkus);
                productSkuDisInfoService.saveApplyList(applyProductSkus);
                productSkuSalesInfoService.saveApplyList(applyProductSkus);
                productSkuBoxPackingService.saveApplyList(applyProductSkus);
                productSkuSupplyUnitService.saveApplyList(applyProductSkus);
                productSkuManufacturerService.saveApplyList(applyProductSkus);
                productSkuFileService.saveApplyList(applyProductSkus);
                //productSkuConfigService.saveApplyList(applyProductSkus);
                productSkuInspReportService.saveApplyList(applyProductSkus);
                return HandlingExceptionCode.FLOW_CALL_BACK_SUCCESS;
            }

        }
        return HandlingExceptionCode.FLOW_CALL_BACK_FALSE;
    }

    @Override
    public List<ApplyProductSku> getProductApplyList(List<String> skuCodes, Byte number) {
        return applyProductSkuMapper.getProductApplyList(skuCodes,number);
    }
}
