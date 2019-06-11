package com.aiqin.bms.scmp.api.product.service;


import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.pojo.NewProduct;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductSaveReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductUpdateReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.QueryNewProductReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewProductResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewSkuDetailsResponseVO;

import java.util.List;

public interface NewProductService {

    int insertProduct(NewProductSaveReqVO newProductSaveReqVO);

    int updateProduct(NewProductUpdateReqVO newProductUpdateReqVO);

    int save(NewProduct newProduct);

    int update(NewProduct newProduct);


    int insertList(List<NewProduct> list);

    void ExceptionId(String productCode);

    BasePage<NewProductResponseVO> getList(QueryNewProductReqVO queryNewProductReqVO);

    List<NewSkuDetailsResponseVO>productSku(String productCode, String productName);

    List<NewProductResponseVO>getPageList(QueryNewProductReqVO queryNewProductReqVO);



}
