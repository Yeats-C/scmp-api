package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.pojo.VariablePrice;
import com.aiqin.bms.scmp.api.product.domain.pojo.VariablePriceSku;
import com.aiqin.bms.scmp.api.product.domain.request.variableprice.*;
import com.aiqin.bms.scmp.api.product.domain.response.variableprice.*;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;

import java.util.List;
import java.util.Map;


public interface VariablePriceService {

    int insertSelective(VariablePrice record);

    int updateByPrimaryKeySelective(VariablePrice record);

    int insertList(List<VariablePrice> variablePrices);

    int saveList(VariablePriceReqVo variablePriceReqVos);

    List<VariablePriceSku>conversion(List<VariablePriceListReqVo> variablePriceListReqVos, String code, String variablePriceName);


    BasePage<SkuDataListResponse>getDataList(SkuDataListReqVo skuDataListReqVo);

    void conversion(List<SkuDataListResponse> skuDataListResponses, String priceTypeCode, Map<String, Long> objectMap);

    int getIsDefault(List<VariablePriceListReqVo> variablePriceListReqVos, String code);

    Integer updateList(List<VariablePriceSku> variablePrices, String priceTypeCode);

     VariableDetailList getVariableDetail(String priceTypeCode, String variablePriceCode);

    BasePage<PriceManagement>getManagement(PriceManagementReqVo priceManagementReqVo);

    BasePage<VariablePriceManagementResVO>getPriceDataList(VariablePriceManagementReqVO variablePriceManagementReqVO);


    List<ErrorVariableResponse>batchImport(List<ExcelData> useList, String priceTypeCode);

    List<ErrorVariableResponse> encapsulation(List<ExcelData> excelData, String priceTypeCode);

     Map<String, Long> getLatestPurchasePriceBySkuCodes(List<String> skuCodes);

    ConfirmPriceList getConfirm(String variablePriceCode, String priceTypeCode);


     ErrorVariableResponse getSwitch(String priceType, ErrorVariableResponse errorVariableResponse, Long priceValue);


    PriceDetailedResponse getPriceSku(Long priceSkuId);

    /**
     * 审批流接口
     * @param
     */
    void workFlow(String variablePriceName, String priceTypeCode, String variablePriceCode) ;

    /**
     * 回调接口
     * @param vo
     * @return
     */
    String workFlowCallback(WorkFlowCallbackVO vo);

    Integer priceRevoke(PriceRevokeReqVo priceRevokeReqVo);

    Integer getVarName(String name);














}
