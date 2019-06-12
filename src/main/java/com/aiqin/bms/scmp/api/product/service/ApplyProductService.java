package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.NewProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductOperationLog;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.*;
import com.aiqin.bms.scmp.api.common.workflow.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.ApplyProductDetailsResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.ApplyProductResponseVO;

import java.util.List;

public interface ApplyProductService {


    int insertSelective(ApplyProduct record);

    ApplyProduct selectByPrimaryKey(Long id);

    String saveList(ApplyDraftReqVO applyDraftReqVO, Long code);

    int updateByPrimaryKeySelective(ApplyProduct record);

    int insertProduct(NewProductSaveReqVO newProductSaveReqVO);

    void ExceptionId(String productCode);

    BasePage<ApplyProductResponseVO> getList(QueryApplyProductReqVO queryApplyProductReqVO);


    List<ApplyProductDetailsResponseVO>getApplyProduct(String applyode);

;
    Integer getName(String proName);

    int insertList(List<ApplyProduct> applyProducts);

    List<ProductOperationLog> conversion(List<ApplyProduct> applyProducts);

    /**
     * 审批流接口
     * @param
     */
    String workFlow(String applyCode) ;

    /**
     * 封装 申请商品状态修改->日志->迁移正式表->日志
     */
    String productFlow(List<ApplyProduct> applyProducts, WorkFlowCallbackVO workFlowCallbackVO);

    ProductOperationLog malloc(NewProduct newProduct);

    /**
     * 撤销
     * @param applyCode
     * @return
     */
    Integer revoke(String applyCode);

    /**
     * 通过商品编码集合和申请状态查询集合
     * @author zth
     * @date 2019/4/4
     * @param productCodes 商品编码集合
     * @param number 状态
     * @return java.util.List<com.aiqin.mgs.product.api.domain.pojo.ApplyProduct>
     */
    List<ApplyProduct> getProductApplyList(List<String> productCodes, Byte number);
}
