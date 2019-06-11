package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.ProductBrandType;
import com.aiqin.bms.scmp.api.product.domain.request.brand.ProductBrandReqDTO;
import com.aiqin.bms.scmp.api.product.domain.request.brand.ProductBrandReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.brand.QueryProductBrandReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.ProductBrandRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.QueryProductBrandRespVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Description:
 * 商品品牌管理
 * @author zth
 * @date 2018-12-12
 * @time: 11:15
 */
public interface ProductBrandService {

    /**
     * 品牌新增
     * @author zth
     * @date 2018/12/13
     * @param reqVO
     * @return int
     */
    Integer save(ProductBrandReqVO reqVO);
    /**
     * 保存方法
     * @author zth
     * @date 2018/12/13
     * @param reqDTO
     * @return int
     */
    Integer saveBrandData(ProductBrandReqDTO reqDTO);
    /**
     * 上传图片
     * @author zth
     * @date 2018/12/13
     * @param base64
     * @return java.lang.String
     */
    String uploadImage(String base64);
    /**
     * 品牌修改
     * @author zth
     * @date 2018/12/13
     * @param reqVO
     * @return int
     */
    Integer edit(ProductBrandReqVO reqVO);
    /**
     * 保存品牌修改
     * @author zth
     * @date 2018/12/13
     * @param t
     * @return int
     */
    Integer editBrandData(ProductBrandReqDTO t);
    /**
     * 品牌状态修改
     * @author zth
     * @date 2018/12/13
     * @param id
     * @param enable
     * @return int
     */
    Integer enable(Long id, Integer enable);
    /**
     * 修改启用禁用状态
     * @author zth
     * @date 2018/12/13
     * @param productBrandType
     * @return int
     */
    Integer enableBrandData(ProductBrandReqDTO productBrandType);
    /**
     * 通过id查询商品品牌详情
     * @author zth
     * @date 2018/12/13
     * @param id
     * @return com.aiqin.mgs.product.api.domain.response.ProductBrandRespVO
     */
    ProductBrandRespVO view(Long id);
    /**
     * 查询列表数据
     * @author zth
     * @date 2018/12/13
     * @param reqVO
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    PageInfo<QueryProductBrandRespVO> selectBrandListByQueryVO(QueryProductBrandReqVO reqVO);
    /**
     * 通过codes查询品牌集合
     * @author zth
     * @date 2018/12/29
     * @param codes
     * @return java.util.List<com.aiqin.mgs.product.api.domain.ProductBrandType>
     */
    List<ProductBrandType> selectByBrandCodes(List<String> codes);
}
