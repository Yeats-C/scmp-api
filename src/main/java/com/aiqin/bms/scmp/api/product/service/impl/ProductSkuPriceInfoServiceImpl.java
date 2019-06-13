package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.request.price.QueryProductSkuPriceInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.QueryProductSkuPriceInfoRespVO;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuPriceInfoMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuPriceInfoService;
import com.aiqin.bms.scmp.api.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-30
 * @time: 11:14
 */
@Service
@Slf4j
public class ProductSkuPriceInfoServiceImpl implements ProductSkuPriceInfoService {
    @Autowired
    private ProductSkuPriceInfoMapper productSkuPriceInfoMapper;

    @Override
    public BasePage<QueryProductSkuPriceInfoRespVO> list(QueryProductSkuPriceInfoReqVO reqVO) {
        List<QueryProductSkuPriceInfoRespVO> list = productSkuPriceInfoMapper.selectListByQueryVO(reqVO);
        return PageUtil.getPageList(reqVO.getPageNo(),list);
    }

    @Override
    public ProductSkuPriceInfoRespVO view(String code) {
        return productSkuPriceInfoMapper.selectInfoByCode(code);
    }
}
