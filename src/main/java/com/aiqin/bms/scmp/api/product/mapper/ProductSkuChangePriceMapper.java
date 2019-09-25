package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.dto.changeprice.ProductSkuChangePriceDTO;
import com.aiqin.bms.scmp.api.product.domain.dto.changeprice.SaleCountDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChangePrice;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.PriceMeasurementReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QueryChangePriceRepeatVO;
import com.aiqin.bms.scmp.api.product.domain.response.ExportChangePriceVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.ProductSkuChangePriceRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QueryChangePriceRepeatRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuChangePriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuChangePrice record);

    int insertSelective(ProductSkuChangePrice record);

    ProductSkuChangePrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuChangePrice record);

    int updateByPrimaryKey(ProductSkuChangePrice record);
    /**
     * 根据数据查询
     * @author NullPointException
     * @date 2019/5/22
     * @param temp
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.changeprice.QueryChangePriceRepeatRespVO>
     */
    List<QueryChangePriceRepeatRespVO> checkRepeat(@Param("vo") QueryChangePriceRepeatVO vo);
    /**
     * 编码查询详情
     * @author NullPointException
     * @date 2019/5/23
     * @param code
     * @return com.aiqin.mgs.product.api.domain.response.changeprice.ProductSkuChangePriceRespVO
     */
    ProductSkuChangePriceRespVO selectInfoByCode(String code);
    ProductSkuChangePriceRespVO selectInfoByFormNo1(String code);

    /**
     * 非空更新
     * @author NullPointException
     * @date 2019/5/23
     * @param copy
     * @return int
     */
    int updateByCodeSelective(ProductSkuChangePrice copy);
    /**
     * 根据表单号查询
     * @author NullPointException
     * @date 2019/5/24
     * @param formNo
     * @return com.aiqin.mgs.product.api.domain.dto.changeprice.ProductSkuChangePriceDTO
     */
    ProductSkuChangePriceDTO selectByFormNo(String formNo);
    /**
     * 查询需要同步的数据集合
     * @author NullPointException
     * @date 2019/5/27
     * @param
     * @return java.util.List<com.aiqin.mgs.product.api.domain.pojo.ProductSkuChangePriceInfo>
     */
    List<ProductSkuChangePriceDTO> selectByPriceStatusAndDate();

    /**
     * 查sku上月数量
     * @param req
     * @return
     */
    List<SaleCountDTO> selectSaleNumBySkuCode(@Param("list") List<PriceMeasurementReqVO> req, @Param("date") String date);

    List<ExportChangePriceVO> exportChangePriceData(String code);
}