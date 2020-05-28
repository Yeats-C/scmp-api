package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.request.ProductSkuBatchReq;
import com.aiqin.bms.scmp.api.product.domain.request.ProductSkuBatchReq2;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QueryProductSkuBatchReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QuerySkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuBatchRespVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuBatchMapper;
import com.aiqin.bms.scmp.api.product.service.ProductSkuBatchService;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.Warehouse;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.QueryWarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.QueryWarehouseResVo2;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
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

    @Autowired
    private WarehouseDao warehouseDao;

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
    public Boolean add(ProductSkuBatchReq2 productSkuBatchReq2) {
        AuthToken authToken = getUser();
        List<ProductSkuBatchReq> productSkuBatchReqList= Lists.newArrayList();

        for (Warehouse warehouse:
        productSkuBatchReq2.getWarehouses()) {
            for (ProductSkuBatchReq productSkuBatchReq:
                    productSkuBatchReq2.getProductSkuBatchReqList()) {
                ProductSkuBatchReq productSkuBatchReq1=new ProductSkuBatchReq();
                BeanCopyUtils.copy(productSkuBatchReq,productSkuBatchReq1);
                productSkuBatchReq1.setCreateById(authToken.getPersonId());
                productSkuBatchReq1.setCreateById(authToken.getPersonName());
                productSkuBatchReq1.setTransportCenterCode(warehouse.getLogisticsCenterCode());
                productSkuBatchReq1.setTransportCenterName(warehouse.getLogisticsCenterName());
                productSkuBatchReq1.setWarehouseCode(warehouse.getWarehouseCode());
                productSkuBatchReq1.setWarehouseName(warehouse.getWarehouseName());
                productSkuBatchReq1.setWarehouseType(warehouse.getWarehouseTypeCode());
                productSkuBatchReqList.add(productSkuBatchReq1);
            }
        }
//        productSkuBatchReqList.stream().forEach(x->{
//            x.setCreateById(authToken.getPersonId());
//            x.setCreateById(authToken.getPersonName());
//        }
//        );
        productSkuBatchMapper.inserts(productSkuBatchReqList);
        return true;
    }

    @Override
    public List<WarehouseDTO> getWarehousetList() {
        return  warehouseDao.findWarehouseListForBatch();
    }


}
