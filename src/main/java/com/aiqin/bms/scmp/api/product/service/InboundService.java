package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundProduct;
import com.aiqin.bms.scmp.api.product.domain.request.BoundRequest;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.*;
import com.aiqin.bms.scmp.api.product.domain.request.returngoods.SupplyReturnOrderMainReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.InboundResVo;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.InboundResponse;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.QueryInboundResVo;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.SupplyReturnOrderMainReqVOReturn;
import com.aiqin.bms.scmp.api.product.service.impl.InboundServiceImpl;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

/**
 * @Classname: InboundService
 * @Author: Kt.w
 * @Date: 2019/3/4
 */
public interface InboundService {

    /**
     * 分页查询以及模糊搜索
     */
    BasePage<QueryInboundResVo> getInboundList(QueryInboundReqVo vo);

    /**
     * 查询入库信息
     */
    List<InboundResponse> selectInBoundInfoByBoundSearch(BoundRequest boundRequest);

    /**
     * 查看入库单详情
     */
    InboundResVo view(Long id);

    /**
     * 新建入库单
     */
    String saveInbound(InboundReqSave reqVo);

    /**
     * 新建入库单
     */
    String saveInbound2(InboundReqSave reqVo);

    /**
     * 获取入库类型
     */
    List<EnumReqVo> getInboundType();

    /**
     * 获取出入库状态类型
     */
    List<EnumReqVo> getInboundOutboundStatus();

    /**
     * 生成退货的入库单
     */
    String saveReturnGoodsToInbound(SupplyReturnOrderMainReqVO reqVo);

    /**
     * 推送给wms
     */
    //void pushWms(String code, InboundServiceImpl inboundService);
    void pushWms(String code);

    void workFlowCallBack(InboundCallBackRequest request);

    /**
     * 根据类型回传给来源单号状态
     */
    void returnSource(Long id);

    /**
     * 入库单回传调拨
     */
    void inBoundReturn(String allocationCode);

    /**
     * 入库单回传给移库
     */
    void inBoundReturnMovement(String allocationCode);

    HttpResponse selectInboundBatchInfoByInboundOderCode(InboundBatch inboundBatch);

    /**
     * 批量保存入库单
     */
    Boolean saveList(List<InboundReqSave> list);

    void saveData(List<Inbound> inboundList, List<InboundProduct> productList, List<InboundBatch> batchList);

    String repealOrder(String orderId, String createById, String createByName, String cancel);

}
