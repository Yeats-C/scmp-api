package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.CancelReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import com.aiqin.bms.scmp.api.product.service.ProductApplyService;
import com.aiqin.bms.scmp.api.product.service.ProductSaleAreaService;
import com.aiqin.bms.scmp.api.product.service.ProductSkuConfigService;
import com.aiqin.bms.scmp.api.product.service.SkuInfoService;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-14
 * @time: 17:18
 */
@Service
public class ProductApplyServiceImpl implements ProductApplyService {

    @Autowired
    private ProductSaleAreaService productSaleAreaService;

    @Autowired
    private ProductSkuConfigService productSkuConfigService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Override
    public BasePage<QueryProductApplyRespVO> queryApplyList(QueryProductApplyReqVO reqVo) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            reqVo.setCompanyCode(authToken.getCompanyCode());
        }
        List<QueryProductApplyRespVO> list = Lists.newArrayList();
        switch (reqVo.getApprovalType()){
            case 1:
                list = skuInfoService.queryApplyList(reqVo);
                break;
            case 2:
                list = productSkuConfigService.queryApplyList(reqVo);
                break;
            case 3:
                list = productSaleAreaService.queryApplyList(reqVo);
                break;
            default: throw new BizException(MessageId.create(Project.PRODUCT_API,98,"请选择审批类型!"));
        }
        return PageUtil.getPageList(reqVo.getPageNo(),list);
    }

    @Override
    public ProductApplyInfoRespVO view(String code, Integer approvalType) {

        switch (approvalType){
            case 1:
                return skuInfoService.getSkuApplyDetail(code);
            case 2:
                return productSkuConfigService.applyView(code);
            case 3:
                return productSaleAreaService.applyView(code);
            default: throw new BizException(MessageId.create(Project.PRODUCT_API,98,"请选择审批类型!"));
        }
    }

    /**
     * 申请撤销
     *
     * @param reqVO
     * @return
     */
    @Override
    public Integer cancel(CancelReqVO reqVO) {
        Integer num = 0 ;
        switch (reqVO.getApprovalType()){
            case 1:
                skuInfoService.cancelSkuApply(reqVO.getCode());
                break;
            case 2:
                num = productSkuConfigService.cancelApply(reqVO.getCode());
                break;
            case 3:
                productSaleAreaService.cancelApply(reqVO.getCode());
                break;
            default: throw new BizException(MessageId.create(Project.PRODUCT_API,98,"请选择审批类型!"));
        }
        return num;
    }

    /**
     * 功能描述: 根据formNo获取情接口请求
     *
     * @param formNo
     * @param approvalType
     * @return
     * @auther knight.xie
     * @date 2019/8/10 15:27
     */
    @Override
    public DetailRequestRespVo getRequsetParam(String formNo, Integer approvalType) {
        DetailRequestRespVo respVo;
        switch (approvalType){
            case 1:
                respVo = skuInfoService.getInfoByForm(formNo);
                break;
            case 2:
                respVo = productSkuConfigService.getInfoByForm(formNo);
                break;
            case 3:
                respVo = productSaleAreaService.getInfoByForm(formNo);
                break;
            default: throw new BizException(MessageId.create(Project.PRODUCT_API,98,"请选择审批类型!"));
        }
        return respVo;
    }
}
