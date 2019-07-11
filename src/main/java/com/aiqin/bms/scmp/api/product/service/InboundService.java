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
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/3/4
 * @Version 1.0
 * @Since 1.0
 */
public interface InboundService {

    /**
     * 分页查询以及模糊搜索
     * @param vo
     * @return
     */
  BasePage<QueryInboundResVo> getInboundList(QueryInboundReqVo vo);

  /**
   * 查询入库信息
   *
   * @param boundRequest
   * @return
   */
  List<InboundResponse> selectInBoundInfoByBoundSearch(BoundRequest boundRequest);

    /**
     * 查看入库单详情
     * @param id
     * @return
     */
    InboundResVo view(Long id);

    /**
     * 新建入库单
     * @param reqVo
     * @return
     */
    String saveInbound(InboundReqSave reqVo);


    /**
     * 获取入库类型
     * @return
     */
    List<EnumReqVo> getInboundType();


    /**
     * 获取出入库状态类型
     * @return
     */
    List<EnumReqVo> getInboundOutboundStatus();

  /**
   * 生成退货的入库单
   * @author zth
   * @date 2019/3/1
   * @param reqVo
   * @return java.lang.Integer
   */
  String saveReturnGoodsToInbound(SupplyReturnOrderMainReqVO reqVo);


  /**
   * 推送给wms
   * @param code
   * @return
   */
   void pushWms(String code, InboundServiceImpl inboundService);


    void workFlowCallBack(InboundCallBackReqVo reqVo);


    /**
     * 根据类型回传给来源单号状态
     * @param id
     */
    void returnSource(Long id);
  /**
   * 回调采购接口
   * @param storageResultItemReqVo
   */
   void returnPurchase(StorageResultReqVo storageResultItemReqVo);


    /**
     * 入库单回传调拨
     * @param allocationCode
     */
    void inBoundReturn(String allocationCode);

    /**
     * 入库单回传退货
     * @param storageResultItemReqVo
     */
    void returnOder(SupplyReturnOrderMainReqVOReturn storageResultItemReqVo);


    /**
     * 入库单回传给移库
     * @param allocationCode
     */
    void inBoundReturnMovement(String allocationCode);

    HttpResponse selectInboundBatchInfoByInboundOderCode(InboundBatch inboundBatch);
    /**
     * 批量保存入库单
     * @author NullPointException
     * @date 2019/6/27
     * @param list
     * @return java.lang.Boolean
     */
    Boolean saveList(List<InboundReqSave> list);

    void saveData(List<Inbound> inboundList, List<InboundProduct> productList, List<InboundBatch> batchList);

    HttpResponse<InboundProduct>  selectPurchaseInfoByPurchaseNum(Inbound inbound);
}
