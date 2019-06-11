package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDraft;
import com.aiqin.bms.scmp.api.product.domain.request.draft.DetailReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.draft.SaveReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.salearea.ApplySaleAreaReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.SaveSkuApplyInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.ApplySkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaMainRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className DraftServiceImpl
 * @date 2019/5/14 11:24
 * @description TODO
 */
@Service
@Slf4j
public class DraftServiceImpl implements DraftService {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private ApplyProductDraftService applyProductDraftService;

    @Autowired
    private ProductSaleAreaService productSaleAreaService;

    @Autowired
    private ProductSkuConfigService productSkuConfigService;


    /**
     * 根据审批类型获取审批单数据
     *
     * @param approvalType
     * @return
     */
    @Override
    public HttpResponse list(Integer approvalType) {
        ApprovalTypeEnum approvalTypeEnum = ApprovalTypeEnum.getByType(approvalType);
        if(null == approvalTypeEnum){
            throw new BizException(ResultCode.OBJECT_NOT_FOUND);
        }
        String companyCode = "";
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            companyCode = authToken.getCompanyCode();
        }
        log.info("获取的公司信息编码[{}]",companyCode);
        HttpResponse httpResponse = null;
        if (Objects.equals(ApprovalTypeEnum.PRODUCT_SKU,approvalTypeEnum)) {
            log.info("获取商品信息");
            List<ProductSkuDraftRespVo> productSkuDraftRespVos = Lists.newArrayList();
            productSkuDraftRespVos.addAll(applyProductDraftService.getProductListDraftByCompanyCode(companyCode));
            productSkuDraftRespVos.addAll(skuInfoService.getProductSkuDraftsByCompanyCode(companyCode));
            log.info("获取商品信息,结果{}", JSON.toJSON(productSkuDraftRespVos));
            httpResponse = HttpResponse.success(productSkuDraftRespVos);
        }else if (Objects.equals(ApprovalTypeEnum.PRODUCT_CONFIG,approvalTypeEnum)) {
            log.info("获取商品配置信息数据");
            List<SkuConfigsRepsVo> configsRepsVos = productSkuConfigService.findDraftList(companyCode);
            log.info("获取商品配置信息,结果{}", JSON.toJSON(configsRepsVos));
            httpResponse = HttpResponse.success(configsRepsVos);
        }else if (Objects.equals(ApprovalTypeEnum.SALES_AREA,approvalTypeEnum)) {
            log.info("获取销售区域信息数据");
            List<QueryProductSaleAreaMainRespVO> saleAreaRespVOS = productSaleAreaService.queryListForDraft(companyCode);
            log.info("获取销售区域信息,结果{}", JSON.toJSON(saleAreaRespVOS));
            httpResponse = HttpResponse.success(saleAreaRespVOS);
        }
        return httpResponse;
    }

    /**
     * 获取商品申请单详情
     *
     * @param reqVo
     * @return
     */
    @Override
    public HttpResponse detail(DetailReqVo reqVo) {
        ApprovalTypeEnum approvalTypeEnum = ApprovalTypeEnum.getByType(reqVo.getApprovalType());
        if(null == approvalTypeEnum){
            throw new BizException(ResultCode.OBJECT_NOT_FOUND);
        }
        HttpResponse httpResponse = null;
        if (Objects.equals(ApprovalTypeEnum.PRODUCT_SKU,approvalTypeEnum)) {
            if(Objects.equals(Global.SKU,reqVo.getApplySort())){
                httpResponse = HttpResponse.success(skuInfoService.getSkuDraftInfo(reqVo.getCode()));
            } else if (Objects.equals(Global.SPU,reqVo.getApplySort())) {
                httpResponse = HttpResponse.success(applyProductDraftService.getDraftByProductCode(reqVo.getCode()));
            }
        }else if (Objects.equals(ApprovalTypeEnum.PRODUCT_CONFIG,approvalTypeEnum)) {

        }else if (Objects.equals(ApprovalTypeEnum.SALES_AREA,approvalTypeEnum)) {

        }
        return httpResponse;
    }

    /**
     * 删除商品申请单明细
     *
     * @param reqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse<Integer> delete(DetailReqVo reqVo) {
        ApprovalTypeEnum approvalTypeEnum = ApprovalTypeEnum.getByType(reqVo.getApprovalType());
        if(null == approvalTypeEnum){
            throw new BizException(ResultCode.OBJECT_NOT_FOUND);
        }
        if (Objects.equals(ApprovalTypeEnum.PRODUCT_SKU,approvalTypeEnum)) {
            if(Objects.equals(Global.SKU,reqVo.getApplySort())){
                List<String> skuCodes = Lists.newArrayList();
                skuCodes.add(reqVo.getCode());
                skuInfoService.deleteProductSkuDraft(skuCodes);
            } else if (Objects.equals(Global.SPU,reqVo.getApplySort())) {
                //先判断SPU是新增还是修改
                ApplyProductDraft applyProductDraft = applyProductDraftService.getDraftByProductCode(reqVo.getCode());
                //如果是新增,则需要删除商品下SKU信息
                if(Objects.equals(StatusTypeCode.ADD_APPLY.getStatus(),applyProductDraft.getApplyType())){
                    //再判断删除商品下是否存在SKU
                    List<ProductSkuDraft> skuDraftList = skuInfoService.getProductSkuDraftsByProductCode(reqVo.getCode());
                    if (CollectionUtils.isNotEmpty(skuDraftList)) {
                        List<String> skuCodes = skuDraftList.stream().map(item -> item.getSkuCode()).collect(Collectors.toList());
                        skuInfoService.deleteProductSkuDraft(skuCodes);
                    }
                }
                List<String> productCodes = Lists.newArrayList();
                productCodes.add(reqVo.getCode());
                applyProductDraftService.deleteCode(productCodes);
            }
        }else if (Objects.equals(ApprovalTypeEnum.PRODUCT_CONFIG,approvalTypeEnum)) {
            productSkuConfigService.deleteDraftById(reqVo.getId());
        }else if (Objects.equals(ApprovalTypeEnum.SALES_AREA,approvalTypeEnum)) {
            productSaleAreaService.deleteDraft(reqVo.getCode());
        }
        return HttpResponse.success(1);
    }

    /**
     * 保存申请单
     *
     * @param reqVo
     * @return
     */
    @Override
    public HttpResponse<Integer> save(SaveReqVo reqVo) {
        ApprovalTypeEnum approvalTypeEnum = ApprovalTypeEnum.getByType(reqVo.getApprovalType());
        if(null == approvalTypeEnum){
            throw new BizException(ResultCode.OBJECT_NOT_FOUND);
        }
        if (Objects.equals(ApprovalTypeEnum.PRODUCT_SKU,approvalTypeEnum)) {
            SaveSkuApplyInfoReqVO saveSkuApplyInfoReqVO = new SaveSkuApplyInfoReqVO();
            BeanCopyUtils.copy(reqVo,saveSkuApplyInfoReqVO);
            Map<String,List<String>> dataMap = (Map<String, List<String>>) reqVo.getData();
            saveSkuApplyInfoReqVO.setProductCodes(dataMap.get(Global.PRODUCT_CODE));
            saveSkuApplyInfoReqVO.setSkuCodes(dataMap.get(Global.SKU_CODE));
            skuInfoService.saveSkuApplyInfo(saveSkuApplyInfoReqVO);
        }else if (Objects.equals(ApprovalTypeEnum.PRODUCT_CONFIG,approvalTypeEnum)) {
            ApplySkuConfigReqVo applySkuConfigReqVo = new ApplySkuConfigReqVo();
            BeanCopyUtils.copy(reqVo,applySkuConfigReqVo);
            List<String> configCodes = (List<String>) reqVo.getData();
            List<String> collect = configCodes.stream().distinct().collect(Collectors.toList());
            applySkuConfigReqVo.setSkuConfigs(collect);
            productSkuConfigService.insertApplyList(applySkuConfigReqVo);
        }else if (Objects.equals(ApprovalTypeEnum.SALES_AREA,approvalTypeEnum)) {
            ApplySaleAreaReqVO saleAreaReqVO = new ApplySaleAreaReqVO();
            BeanCopyUtils.copy(reqVo,saleAreaReqVO);
            List<String> areaCodes = (List<String>) reqVo.getData();
            saleAreaReqVO.setAreaCodes(areaCodes);
            productSaleAreaService.addSaleAreaApply(saleAreaReqVO);
        }
        return HttpResponse.success(1);
    }
}
