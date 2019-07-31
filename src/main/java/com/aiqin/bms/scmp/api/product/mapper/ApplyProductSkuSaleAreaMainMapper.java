package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.dto.salearea.ApplyProductSkuSaleAreaMainDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSaleAreaMain;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleAreaMain;
import com.aiqin.bms.scmp.api.product.domain.product.apply.ProductSaleAreaApplyVO;
import com.aiqin.bms.scmp.api.product.domain.request.product.apply.QueryProductApplyRespVO;
import com.aiqin.bms.scmp.api.product.domain.request.salearea.ApplyProductSkuSaleAreaInfoReq;
import com.aiqin.bms.scmp.api.product.domain.response.product.apply.QueryProductApplyReqVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyProductSkuSaleAreaMainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuSaleAreaMain record);

    int insertSelective(ApplyProductSkuSaleAreaMain record);

    ApplyProductSkuSaleAreaMain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuSaleAreaMain record);

    int updateByPrimaryKey(ApplyProductSkuSaleAreaMain record);
    /**
     * 批量保存
     * @author NullPointException
     * @date 2019/6/4
     * @param temps
     * @return int
     */
    int insertBatch(@Param("items") List<ApplyProductSkuSaleAreaMain> temps);
    /**
     * 申请列表
     * @author NullPointException
     * @date 2019/6/4
     * @param reqVo
     * @return java.util.List<com.aiqin.mgs.product.api.domain.request.product.apply.QueryProductApplyRespVO>
     */
    List<QueryProductApplyRespVO> queryApplyList(QueryProductApplyReqVO reqVo);
    /**
     * 编码查询申请详情
     * @author NullPointException
     * @date 2019/6/4
     * @param code
     * @return java.util.List<com.aiqin.mgs.product.api.domain.product.apply.ProductSaleAreaApplyVO>
     */
    List<ProductSaleAreaApplyVO> selectByApplyCode(String code);
    /**
     * 通过表单号查询数据
     * @author NullPointException
     * @date 2019/6/4
     * @param formNo
     * @return java.util.List<com.aiqin.mgs.product.api.domain.dto.salearea.ApplyProductSkuSaleAreaDTO>
     */
    List<ApplyProductSkuSaleAreaMainDTO> selectByFormNo(String formNo);
    /**
     * 通过表单号批量更新数据
     * @author NullPointException
     * @date 2019/6/5
     * @param req
     * @return java.lang.Integer
     */
    Integer updateApplyInfoByVO(ApplyProductSkuSaleAreaInfoReq req);
    /**
     * 查询未生效的数据
     * @author NullPointException
     * @date 2019/6/6
     * @param
     * @return java.util.List<com.aiqin.mgs.product.api.domain.dto.salearea.ApplyProductSkuSaleAreaMainDTO>
     */
    List<ApplyProductSkuSaleAreaMainDTO> selectListByStatusAndDate();
    /**
     * 更新状态
     * @author NullPointException
     * @date 2019/6/6
     * @param strings
     * @return int
     */
    int updateByCode(@Param("items") List<String> strings);
    /**
     * 通过正式编码查询申请数据
     * @author NullPointException
     * @date 2019/6/15
     * @param officialCode
     * @param applyStatus
     * @return com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSaleAreaMain
     */
    ApplyProductSkuSaleAreaMain selectByOfficialCode(@Param("applyStatus") Integer applyStatus, @Param("officialCode") String officialCode);

    /**
     * 通过名称查询
     * @param name
     * @return
     */
    List<ProductSkuSaleAreaMain> selectByName(String name);
}