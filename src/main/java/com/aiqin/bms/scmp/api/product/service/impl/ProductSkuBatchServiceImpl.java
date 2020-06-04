package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.request.ProductSkuBatchReq;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QueryProductSkuBatchReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QuerySkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuBatchRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuBatchMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuBatchService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2020-04-15 18:09
 * @Description:
 */
@Service
public class ProductSkuBatchServiceImpl extends BaseServiceImpl implements ProductSkuBatchService {

    @Autowired
  private   ProductSkuBatchMapper productSkuBatchMapper;

    @Override
    public BasePage<ProductSkuBatchRespVo> getList(QueryProductSkuBatchReqVO queryProductSkuBatchReqVO) {

        PageHelper.startPage(queryProductSkuBatchReqVO.getPageNo(),queryProductSkuBatchReqVO.getPageSize());
        List<ProductSkuBatchRespVo> productSkuInfoList= productSkuBatchMapper.getList(queryProductSkuBatchReqVO);
        return PageUtil.getPageList(queryProductSkuBatchReqVO.getPageNo(),productSkuInfoList);
    }

    @Override
    public Boolean banById(Long id) {
         productSkuBatchMapper.banById(id);
        return true;
    }

    @Override
    public Boolean add(List<ProductSkuBatchReq> productSkuBatchReqList) {
        AuthToken authToken = getUser();
        productSkuBatchReqList.stream().forEach(x->{
            x.setCreateById(authToken.getPersonId());
            x.setCreateById(authToken.getPersonName());
        }
        );
        productSkuBatchMapper.inserts(productSkuBatchReqList);
        return true;
    }


}
