package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProfitLoss;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProfitLossProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProfitLossProductBatch;
import com.aiqin.bms.scmp.api.product.domain.request.profitloss.QueryProfitLossVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.DetailProfitLossRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.ProfitLossProductBatchRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.ProfitLossProductRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.profitloss.QueryProfitLossRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProfitLossMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProfitLossProductBatchMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProfitLossProductMapper;
import com.aiqin.bms.scmp.api.product.service.ProfitLossService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProfitLossServiceImpl
 * @date 2019/6/28 12:19
 * @description TODO
 */
@Service
public class ProfitLossServiceImpl extends BaseServiceImpl implements ProfitLossService {

    @Autowired
    private ProfitLossMapper profitLossMapper;
    @Autowired
    private ProfitLossProductMapper productMapper;
    @Autowired
    private ProfitLossProductBatchMapper batchProductMapper;
    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    @Override
    public BasePage<QueryProfitLossRespVo> findPage(QueryProfitLossVo vo) {
        PageHelper.startPage(vo.getPageNo(),vo.getPageSize());
        vo.setCompanyCode(getUser().getCompanyCode());
        List<QueryProfitLossRespVo> list = profitLossMapper.getList(vo);

        return PageUtil.getPageList(vo.getPageNo(),list);
    }

    /**
     * 根据Id查询详情
     *
     * @param id
     */
    @Override
    public DetailProfitLossRespVo view(Long id) {
        if(Objects.isNull(id)){
            throw new BizException(ResultCode.ID_EMPTY);
        }
        DetailProfitLossRespVo respVo = new DetailProfitLossRespVo();
        ProfitLoss profitLoss = profitLossMapper.selectByPrimaryKey(id);
        if(Objects.isNull(profitLoss)){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        BeanCopyUtils.copy(profitLoss,respVo);
        respVo.setOrderTypeName(respVo.getOrderType() == 0 ? "指定损溢" : "盘点损溢");
        List<ProfitLossProduct> products = productMapper.getListByOrderCode(respVo.getOrderCode());
        if(CollectionUtils.isNotEmptyCollection(products)){
            List<ProfitLossProductRespVo> profitLossProductRespVos = BeanCopyUtils.copyList(products, ProfitLossProductRespVo.class);
            respVo.setProducts(profitLossProductRespVos);
        }
        List<ProfitLossProductBatch> productBatches = batchProductMapper.getListByOrderCode(respVo.getOrderCode());
        if(CollectionUtils.isNotEmptyCollection(products)){
            List<ProfitLossProductBatchRespVo> productBatchRespVos = BeanCopyUtils.copyList(productBatches, ProfitLossProductBatchRespVo.class);
            respVo.setBatchProducts(productBatchRespVos);
        }
        return respVo;
    }
}
