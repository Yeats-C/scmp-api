package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.pojo.Outbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundProduct;
import com.aiqin.bms.scmp.api.product.domain.request.BoundRequest;
import com.aiqin.bms.scmp.api.product.domain.request.order.OrderInfo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundCallBackReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.QueryOutboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.returnsupply.ReturnSupplyToOutBoundReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.outbound.OutboundResVo;
import com.aiqin.bms.scmp.api.product.domain.response.outbound.OutboundResponse;
import com.aiqin.bms.scmp.api.product.domain.response.outbound.QueryOutboundResVo;
import com.aiqin.bms.scmp.api.product.domain.response.outbound.SupplyOrderInfoReqVO;
import com.aiqin.bms.scmp.api.purchase.domain.request.RejectStockRequest;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

/**
 * @author Kt.w
 */
public interface OutboundService {
    /**
     * 分页查询以及搜索
     */
    BasePage<QueryOutboundResVo> getOutboundList(QueryOutboundReqVo vo);

    /**
     * 查询出库信息
     */
    List<OutboundResponse> selectOutBoundInfoByBoundSearch(BoundRequest boundRequest);

    /**
     * 保存出库信息
     */
    Integer saveOutBoundInfo(OutboundReqVo stockReqVO);

    /**
     * 保存出库信息
     */
    String save(OutboundReqVo stockReqVO);

    String saveOutbound(OutboundReqVo stockReqVO);

    /**
     * 根据id查询出库单
     */
    OutboundResVo view(Long id);

    /**
     * 查看出库类型
     */
    List<EnumReqVo> getOutboundType();

    /**
     * 订单生成出库单
     */
    Integer orderSave(List<OrderInfo> req);

    /**
     * 退供生成出库单
     */
    Integer returnSupplySave(ReturnSupplyToOutBoundReqVo req);

    /**
     * 出库单传送给wms
     */
    void pushWms(String Code);

    /** 退供调用wms*/
    HttpResponse pushRejectWms(String outboundOderCode);

    /**
     * 出库单回调接口
     */
    HttpResponse workFlowCallBack(OutboundCallBackReqVo reqVo);

    /**
     * 根据类型回传给来源单号状态
     */
     void returnSource(Long id,OutboundCallBackReqVo requestVo);

    /**
     * 订单回传接口
     */
    void  returnOder(SupplyOrderInfoReqVO reqVO);

    /**
     * 调拨生成入库单并且改变在途数
     */
    void createInbound(String formNo);

    /**
     * 移库生成入库单并且改变在途数
     */
    void movementCreateInbound(String formNo);

    HttpResponse selectOutboundBatchInfoByOutboundOderCode(OutboundBatch outboundBatch);

    /**
     * 批量保存出库单数据
     */
    Boolean saveList(List<OutboundReqVo> outboundReqVoList);

    /**
     * 批量保存入库数据
     */
    void saveData(List<Outbound> list, List<OutboundProduct> productList, List<OutboundBatch> batchList);

}


