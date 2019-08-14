package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.dto.changeprice.ProductSkuPriceInfoDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfo;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QueryProductSkuPriceInfo;
import com.aiqin.bms.scmp.api.product.domain.request.price.QueryProductSkuPriceInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.PriceJog;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.price.ProductSkuPriceRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.QueryProductSkuPriceInfoRespVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductSkuPriceInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuPriceInfo record);

    int insertSelective(ProductSkuPriceInfo record);

    ProductSkuPriceInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuPriceInfo record);

    int updateByPrimaryKey(ProductSkuPriceInfo record);
    /**
     * 验证数据是否存在
     * @author NullPointException
     * @date 2019/5/24
     * @param queryVO
     * @return java.util.List<com.aiqin.mgs.product.api.domain.pojo.ProductSkuPriceInfo>
     */
    List<ProductSkuPriceInfo> checkRepeat(@Param("vo") QueryProductSkuPriceInfo queryVO);
    /**
     * TODO
     * @author NullPointException
     * @date 2019/5/24
     * @param priceInsertInfos
     * @return int
     */
    int insertBatch(@Param("items") List<ProductSkuPriceInfo> priceInsertInfos);
    /**
     * 批量更新数据
     * @author NullPointException
     * @date 2019/5/24
     * @param priceUpdateInfos
     * @return int
     */
    int updateBatch(@Param("items") List<ProductSkuPriceInfo> priceUpdateInfos);
    /**
     * 通过状态和时间查询
     * @author NullPointException
     * @date 2019/5/27
     * @return java.util.List<com.aiqin.mgs.product.api.domain.pojo.ProductSkuPriceInfo>
     */
    List<ProductSkuPriceInfoDTO> selectByPriceStatusAndDate();
    /**
     * 查询需要失效的数据
     * @author NullPointException
     * @date 2019/5/27
     * @param
     * @return java.util.List<com.aiqin.mgs.product.api.domain.dto.changeprice.ProductSkuPriceInfoDTO>
     */
    List<ProductSkuPriceInfoDTO> selectUnEffectiveData();
    /**
     * 通过编码集合查询数据
     * @author NullPointException
     * @date 2019/5/27
     * @param codes
     * @return java.util.List<com.aiqin.mgs.product.api.domain.pojo.ProductSkuPriceInfo>
     */
    List<ProductSkuPriceInfo> selectByCodes(List<String> codes);
    /**
     * 查询价格列表
     * @author NullPointException
     * @date 2019/5/30
     * @param reqVO
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.price.QueryProductSkuPriceInfoRespVO>
     */
    List<QueryProductSkuPriceInfoRespVO> selectListByQueryVO(List<Long> ids);
    List<Long> selectListByQueryVOCount(QueryProductSkuPriceInfoReqVO reqVO);
    /**
     * TODO
     * @author NullPointException
     * @date 2019/5/30
     * @param code
     * @return com.aiqin.mgs.product.api.domain.response.price.ProductSkuPriceInfoRespVO
     */
    ProductSkuPriceInfoRespVO selectInfoByCode(String code);

    Long selectPriceTax(@Param("skuCode") String skuCode, @Param("supplierCode") String supplierCode);
    /**
     * 通过sku编码查询sku信息
     * @author NullPointException
     * @date 2019/7/9
     * @param skuCode
     * @param companyCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.response.price.SkuPriceRespVO>
     */
    List<ProductSkuPriceRespVo> selectBySkuCodeForOfficial(@Param("skuCode") String skuCode, @Param("companyCode") String companyCode);
    /**
     * 申请详情查看
     * @author NullPointException
     * @date 2019/7/9
     * @param skuCode
     * @param applyCode
     * @param companyCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.response.price.SkuPriceRespVO>
     */
    List<ProductSkuPriceRespVo> selectBySkuCodeForApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode, @Param("companyCode") String companyCode);
    /**
     * 申请详情查看
     * @author NullPointException
     * @date 2019/7/9
     * @param skuCode
     * @param companyCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.response.price.SkuPriceRespVO>
     */
    List<ProductSkuPriceRespVo> selectBySkuCodeForDraft(@Param("skuCode") String skuCode, @Param("companyCode") String companyCode);

    /**
     * 查year内的价格 目前价格是每个月的平均值
     * @param skuCode
     * @param year
     * @return
     */
    List<PriceJog> getPriceJog(@Param("skuCode") String skuCode, @Param("year") int year);
    @MapKey("skuCode")
    Map<String, ProductSkuPriceInfo> selectChannelPriceBySkuCode(List<QuerySkuInfoRespVO> respVos);
}