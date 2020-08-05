package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.ProductSkuUniqueCode;
import com.aiqin.bms.scmp.api.purchase.mapper.ProductSkuUniqueCodeMapper;
import com.aiqin.bms.scmp.api.purchase.service.ProductSkuUniqueCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class ProductSkuUniqueCodeServiceImpl implements ProductSkuUniqueCodeService{

    @Autowired
    private ProductSkuUniqueCodeMapper productSkuUniqueCodeMapper;

    @Override
    public Boolean saveUnique(@Valid List<ProductSkuUniqueCode> reqVO) {
        int i = productSkuUniqueCodeMapper.insertAll(reqVO);
        if (i <= 1){
            return false;
        }else {
            return true;
        }
    }
}
