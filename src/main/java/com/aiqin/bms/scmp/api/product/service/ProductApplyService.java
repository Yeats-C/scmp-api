package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductApplyInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.CancelReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-14
 * @time: 17:18
 */
public interface ProductApplyService {
    /**
     * 查询申请审批列表信息
     * @author NullPointException
     * @date 2019/5/14
     * @param reqVo
     * @return com.github.pagehelper.PageInfo<com.aiqin.mgs.product.api.domain.request.product.apply.QueryProductApplyRespVO>
     */
    BasePage<QueryProductApplyRespVO> queryApplyList(QueryProductApplyReqVO reqVo);
    /**
     * 申请详情
     * @author NullPointException
     * @date 2019/5/15
     * @param code
     * @param approvalType
     * @return com.aiqin.mgs.product.api.domain.product.apply.ProductApplyInfoRespVO
     */
    ProductApplyInfoRespVO view(String code, Integer approvalType);

    /**
     * 申请撤销
     * @param reqVO
     * @return
     */
    Integer cancel(CancelReqVO reqVO);
}
