package com.aiqin.bms.scmp.api.product.service;


import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuImportReq;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuInfoImportMain;
import com.aiqin.bms.scmp.api.product.domain.pojo.NewProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductSaveReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductUpdateReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.QueryNewProductReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.info.SaveSkuInfoReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewProductResponseVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewSkuDetailsResponseVO;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface NewProductService {

    String insertProduct(NewProductSaveReqVO newProductSaveReqVO);

    int updateProduct(NewProductUpdateReqVO newProductUpdateReqVO);

    int save(NewProduct newProduct);

    int update(NewProduct newProduct);


    int insertList(List<NewProduct> list);

    void ExceptionId(String productCode);

    BasePage<NewProductResponseVO> getList(QueryNewProductReqVO queryNewProductReqVO);

    List<NewSkuDetailsResponseVO>productSku(String productCode, String productName);

    List<NewProductResponseVO>getPageList(QueryNewProductReqVO queryNewProductReqVO);


    Map<String,NewProduct> selectBySpuName(Set<String> list, String companyCode);

    NewProductResponseVO getDetail(String productCode);

    /** 保存导入商品信息（真实表） */
    HttpResponse saveImportSkuInfo(SkuImportReq saveSkuInfoReqVo);

    /** 保存导入商品信息（校验） */
    SkuInfoImportMain saveImportSkuInfoCheck(MultipartFile file);

    /**
     * 通过名称查询
     * @author NullPointException
     * @date 2019/7/21
     * @param skuNameList
     * @param companyCode
     * @return java.util.Map<java.lang.String,com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo>
     */
    Map<String, ProductSkuInfo> selectBySkuNames(Set<String> skuNameList, String companyCode);
}
