package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.common.workflow.WorkFlowCallbackVO;
import com.aiqin.bms.scmp.api.product.domain.dto.changeprice.ProductSkuChangePriceDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChangePriceInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceAreaInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfoLog;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.ProductSkuChangePriceReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QueryProductSkuChangePriceReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.ProductSkuChangePriceRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QueryProductSkuChangePriceRespVO;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-20
 * @time: 17:33
 */
public interface ProductSkuChangePriceService {
    /**
     * 保存变价
     * @author NullPointException
     * @date 2019/5/20
     * @param reqVO
     * @return java.lang.Boolean
     */
    Boolean save(ProductSkuChangePriceReqVO reqVO) throws Exception;
    /**
     * 提交审批
     * @author NullPointException
     * @date 2019/5/22
     * @param reqVO
     * @return void
     */
    void callWorkflow(ProductSkuChangePriceReqVO reqVO);

    /**
     * 变价保存
     * @author NullPointException
     * @date 2019/5/21
     * @param reqVO
     * @return java.lang.Boolean
     */
    void saveData(ProductSkuChangePriceReqVO reqVO) throws Exception;
    /**
     * 保存附表数据
     * @author NullPointException
     * @date 2019/5/23
     * @param reqVO
     * @return void
     */
    void saveAttachData(ProductSkuChangePriceReqVO reqVO) throws Exception;

    /**
     * 详情vo
     * @author NullPointException
     * @date 2019/5/23
     * @param code
     * @return com.aiqin.mgs.product.api.domain.response.changeprice.ProductSkuChangePriceRespVO
     */
    ProductSkuChangePriceRespVO view(String code);
    /**
     * 编辑时的查看，需要组装数据
     * @author NullPointException
     * @date 2019/5/29
     * @param code
     * @return com.aiqin.mgs.product.api.domain.response.changeprice.ProductSkuChangePriceRespVO
     */
    ProductSkuChangePriceRespVO editView(String code);

    /**
     * 更新变价信息
     * @author NullPointException
     * @date 2019/5/23
     * @param reqVO
     * @return java.lang.Boolean
     */
    Boolean update(ProductSkuChangePriceReqVO reqVO) throws Exception;
    /**
     * 更新数据
     * @author NullPointException
     * @date 2019/5/23
     * @param reqVO
     * @return void
     */
    void updateData(ProductSkuChangePriceReqVO reqVO) throws Exception;

    /**
     * 通过code删除附表数据
     * @author NullPointException
     * @date 2019/5/23
     * @param code
     * @return void
     */
    void deleteAttachDataByCode(ProductSkuChangePriceRespVO view);
    /**
     * 变价列表
     * @author NullPointException
     * @date 2019/5/23
     * @param reqVO
     * @return java.util.List<com.aiqin.mgs.product.api.domain.response.changeprice.QueryProductSkuChangePriceRespVO>
     */
    BasePage<QueryProductSkuChangePriceRespVO> list(QueryProductSkuChangePriceReqVO reqVO);
    /**
     * 保存正式数据
     * @author NullPointException
     * @date 2019/5/24
     * @param newVO
     * @param dto
     * @return void
     */
    void saveOfficial(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) throws Exception;
    /**
     * 临时区域变价
     * @author NullPointException
     * @date 2019/5/25
     * @param newVO
     * @param dto
     * @return void
     */
    void saveTemporaryAreaChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) throws Exception;

    /**
     * 保存销售区域价
     * @author NullPointException
     * @date 2019/5/25
     * @param newVO
     * @param dto
     * @return void
     */
    void saveSaleAreaChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) throws Exception;

    /**
     * 临时价
     * @author NullPointException
     * @date 2019/5/25
     * @param newVO
     * @param dto
     * @return void
     */
    void saveTemporaryChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) throws Exception;

    /**
     * 保存销售变价
     * @author NullPointException
     * @date 2019/5/25
     * @param newVO
     * @param dto
     * @return void
     */
    void saveSaleChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) throws Exception;

    /**
     * 判重条件 sku + 供应商 + 价格项目
     * 1)不存在
     * 立即生效的->直接添加->记录日志
     * 不是立即生效的->直接添加->日志表记录为未生效
     * 2)存在
     * 1.新数据是立即生效的 ->直接替换老数据->并且记录日志 之前的失效，现在的生效
     * 2.新数据不是立即生效的->审批通过后记录未生效的日志->定时任务定时去刷数据->记录日志 之前的失效，现在的生效
     * @author NullPointException
     * @date 2019/5/24
     * @param newVO
     * @param dto
     * @return void
     */
    void savePurchaseChangePrice(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto) throws Exception;
    /**
     * 保存数据
     * @author NullPointException
     * @date 2019/5/25
     * @param priceInsertInfos
     * @param infoList
     * @param priceUpdateInfos
     * @return void
     */
    void saveData(List<ProductSkuPriceInfo> priceInsertInfos, List<ProductSkuChangePriceInfo> infoList, List<ProductSkuPriceInfo> priceUpdateInfos, List<ProductSkuPriceAreaInfo> areaInfos, List<ProductSkuPriceInfoLog> logList);

    /**
     * 取消
     * @author NullPointException
     * @date 2019/5/24
     * @param newVO
     * @param dto
     * @return void
     */
    void cancel(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto);
    /**
     * 驳回
     * @author NullPointException
     * @date 2019/5/24
     * @param newVO
     * @param dto
     * @return void
     */
    void rejection(WorkFlowCallbackVO newVO, ProductSkuChangePriceDTO dto);
    /**
     * 取消
     * @author NullPointException
     * @date 2019/5/31
     * @param code
     * @return java.lang.Boolean
     */
    Boolean cancelData(String code);
}
