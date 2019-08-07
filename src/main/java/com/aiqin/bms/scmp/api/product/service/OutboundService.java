package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.pojo.Outbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundProduct;
import com.aiqin.bms.scmp.api.product.domain.request.BoundRequest;
import com.aiqin.bms.scmp.api.product.domain.request.UpdateOutBoundReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.UpdateStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.order.OrderInfo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundCallBackReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.QueryOutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.returnsupply.ReturnSupplyToOutBoundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.outbound.*;
import com.aiqin.bms.scmp.api.product.service.impl.OutboundServiceImpl;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectStockRequest;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

/**
 * 描述:
 *
 * @author Kt.w
 * @date 2019/1/5
 * @Version 1.0
 * @since 1.0
 */
public interface OutboundService {
    /**
     * 分页查询以及搜索
     * @param vo
     * @return
     */
    BasePage<QueryOutboundResVo> getOutboundList(QueryOutboundReqVo vo);

    /**
     * 查询出库信息
     *
     * @param boundRequest
     * @return
     */
    List<OutboundResponse> selectOutBoundInfoByBoundSearch(BoundRequest boundRequest);

    /**
     * 保存出库信息
     * @param stockReqVO
     * @return
     */
    Integer saveOutBoundInfo(OutboundReqVo stockReqVO);

    /**
     * 保存出库信息
     * @param stockReqVO
     * @return  返回出库单编码
     */
    String save(OutboundReqVo stockReqVO);


    /**
     * 根据原始单据号封装更新库存信息
     * @param sourceOderCode
     * @return
     */
    List<UpdateStockReqVo> selectUpdateStockInfoBySourceOrderCode(String sourceOderCode, String stockStatusCode);

    /**
     * 更新出库信息
     * @param reqVO
     * @return
     */
    void updateOutBoundInfo(UpdateOutBoundReqVO reqVO);


    /**
     * 根据id查询出库单
     * @param id
     * @return
     */
    OutboundResVo view(Long id);

    /**
     * 查看出库类型
     * @return
     */
    List<EnumReqVo> getOutboundType();
    /**
     * 订单生成出库单
     * @author zth
     * @date 2019/3/1
     * @param req
     * @return java.lang.Integer
     */
    Integer orderSave(List<OrderInfo> req);
    /**
     * 退供生成出库单
     * @author zth
     * @date 2019/3/1
     * @param req
     * @return java.lang.Integer
     */
    Integer returnSupplySave(ReturnSupplyToOutBoundReqVo req);




    /**
     * 出库单传送给wms
     * @param id
     * @return
     */
    void pushWms(String Code);

    /**
     * 出库单回调接口
     * @param reqVo
     * @return
     */
    int  workFlowCallBack(OutboundCallBackReqVo reqVo);


    /**
     * 根据类型回传给来源单号状态
     * @param id
     */
     void returnSource(Long id);

    /**
     * 订单回传接口
     * @param reqVO
     */
    void  returnOder(SupplyOrderInfoReqVO reqVO);

    /**
     * 退供会传接口
     * @param reqVO
     */
    void returnStorageResult(RejectStockRequest reqVO);


    /**
     * 调拨生成入库单并且改变在途数
     * @param id
     */
    void createInbound(String formNo);




    /**
     * 移库生成入库单并且改变在途数
     * @param id
     */
    void movementCreateInbound(Long id);

    HttpResponse selectOutboundBatchInfoByOutboundOderCode(OutboundBatch outboundBatch);
    /**
     * 批量保存出库单数据
     * @author NullPointException
     * @date 2019/6/25
     * @param outboundReqVoList
     * @return java.lang.Boolean
     */
    Boolean saveList(List<OutboundReqVo> outboundReqVoList);
    /**
     * 批量保存入库数据
     * @author NullPointException
     * @date 2019/6/26
     * @param list
     * @param productList
     * @param batchList
     * @return void
     */
    void saveData(List<Outbound> list, List<OutboundProduct> productList, List<OutboundBatch> batchList);
}


