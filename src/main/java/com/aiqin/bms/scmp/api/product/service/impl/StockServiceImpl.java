package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.StockStatusEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.converter.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.SkuBatchReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.changeprice.QuerySkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundItemReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.merchant.MerchantLockStockItemReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.merchant.MerchantLockStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.stock.ChangeStockRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockBatchInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.response.*;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.SkuBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SpareWarehouseRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchProductSkuRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockFlowRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockRespVO;
import com.aiqin.bms.scmp.api.product.domain.trans.ILockStockReqVoToQueryStockSkuReqVo;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.LockOrderItemBatchReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className StockServiceImpl
 * @date 2019/1/4 16:08
 * @description 库存
 */
@Service
@Slf4j
public class StockServiceImpl extends BaseServiceImpl implements StockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private StockDao stockDao;
    @Autowired
    private OutboundService outboundService;
    @Autowired
    private InboundService inboundService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private StockFlowDao stockFlowDao;
    @Autowired
    private ProductSkuDao productSkuDao;
    @Autowired
    private PurchaseGroupService purchaseGroupService;
    @Autowired
    private RedisLockService redisLockService;
    @Autowired
    private StockBatchDao stockBatchDao;
    @Autowired
    private StockBatchFlowDao stockBatchFlowDao;


    /**
     * 功能描述: 查询库存商品(采购退供使用)
     */
    @Override
    public PageInfo<QueryStockSkuRespVo> selectStockSkuPage(QueryStockSkuReqVo reqVO) {
        try {
            PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
            List<QueryStockSkuRespVo> queryStockSkuRespVos = stockDao.selectStockSkuInfoByPage(reqVO);
            return new PageInfo<QueryStockSkuRespVo>(queryStockSkuRespVos);
        } catch (Exception ex) {
            log.error("查询库存商失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     * 功能描述: 查询库存商品(编辑时 退供使用)
     */
    @Override
    public List<QueryStockSkuRespVo> selectStockSku(QueryStockSkuReqVo reqVO) {
        try {
            return stockDao.selectStockSkuInfoByPage(reqVO);
        } catch (Exception ex) {
            log.error("查询库存商失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @Override
    public PageResData selectStockSumInfoByPage(StockRequest stockRequest) {
        try {
            LOGGER.info("中央库存列表查询");
            List<PurchaseGroupVo> groupVoList = purchaseGroupService.getPurchaseGroup(null);
            PageResData pageResData = new PageResData();
            if (org.apache.commons.collections.CollectionUtils.isEmpty(groupVoList)) {
                return pageResData;
            }
            stockRequest.setGroupList(groupVoList);
            List<StockRespVO> stockList = stockDao.selectStockSumInfoByPage(stockRequest);
            HashMap<String, StockRespVO> stockRespMap = new HashMap<>();
            List<StockRespVO> lists = new ArrayList<>();
            Stock stock = null;
            int i = 0;
            for (StockRespVO stockRespVO : stockList) {
                String str = stockRespVO.getSkuCode();
                if (stockRespMap.get(str) == null) {
                    stockRespMap.put(str, stockRespVO);
                    stockCommon(stockRespMap, stockRespVO, str, stock, i);
                } else {
                    stockCommon(stockRespMap, stockRespVO, str, stock, i);
                }
            }
            for (Map.Entry<String, StockRespVO> entry : stockRespMap.entrySet()) {
                lists.add(entry.getValue());
            }
            Integer count = stockDao.selectStockSumInfoByPageCount(stockRequest);
            pageResData.setTotalCount(count);
            pageResData.setDataList(lists);
            return pageResData;
        } catch (Exception e) {
            LOGGER.error("中央列表查询失败", e);
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    private void stockCommon(HashMap<String, StockRespVO> stockRespMap, StockRespVO stockRespVO, String str, Stock stock, int i) {
        StockRespVO key = stockRespMap.get(str);
        if (StringUtils.isNotBlank(stockRespVO.getCompanyCode()) || StringUtils.isNotBlank(stockRespVO.getSkuCode())) {
            stock = new Stock();
            Stock stockInfo;
            stock.setSkuCode(stockRespVO.getSkuCode());
            stock.setCompanyCode(stockRespVO.getCompanyCode());
            if (i == 1) {
                stock.setTransportCenterCode(stockRespVO.getTransportCenterCode());
            }
            stock.setWarehouseType(Global.SALE_TYPE);
            stockInfo = stockDao.selectStockSum(stock);
            if (stockInfo != null) {
                key.setSaleNum(stockInfo.getAvailableCount());
                key.setSaleLockNum(stockInfo.getLockCount());
                key.setSaleWayNum(stockInfo.getTotalWayCount());
                key.setPurchaseWayNum(stockInfo.getPurchaseWayCount());
            }
            stock.setWarehouseType(Global.GIFT_TYPE);
            stockInfo = stockDao.selectStockSum(stock);
            if (stockInfo != null) {
                key.setGiftNum(stockInfo.getAvailableCount());
                key.setGiftLockNum(stockInfo.getLockCount());
                key.setGiftWayNum(stockInfo.getTotalWayCount());
                key.setGiftPurchaseWayNum(stockInfo.getPurchaseWayCount());
            }
            stock.setWarehouseType(Global.SPECIAL_TYPE);
            stockInfo = stockDao.selectStockSum(stock);
            if (stockInfo != null) {
                key.setSpecialSaleNum(stockInfo.getAvailableCount());
                key.setSpecialSaleLockNum(stockInfo.getLockCount());
                key.setSpecialSaleWayNum(stockInfo.getTotalWayCount());
            }
            stock.setWarehouseType(Global.DEFECTIVE_TYPE);
            stockInfo = stockDao.selectStockSum(stock);
            if (stockInfo != null) {
                key.setBadNum(stockInfo.getAvailableCount());
                key.setBadLockNum(stockInfo.getLockCount());
                key.setBadWayNum(stockInfo.getTotalWayCount());
            }
            stockRespMap.put(str, key);
        }
    }

    /**
     * 功能描述: 查询库存商品(采购退供使用) 不分页
     *
     * @param reqVO
     * @param reqVO
     * @return List
     * @auther knight.xie
     * @date 2019/1/7 17:38
     */
    @Override
    public List<QueryStockSkuRespVo> selectStockSkus(QueryStockSkuReqVo reqVO) {
        try {
            return stockDao.selectStockSkuInfoByPage(reqVO);
        } catch (Exception ex) {
            log.error("查询库存商失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @Override
    public PageResData selectWarehouseStockInfoByPage(StockRequest stockRequest) {
        try {
            LOGGER.info("总库存管理列表查询");
            List<PurchaseGroupVo> groupVoList = purchaseGroupService.getPurchaseGroup(null);
            PageResData pageResData = new PageResData();
            if (org.apache.commons.collections.CollectionUtils.isEmpty(groupVoList)) {
                return pageResData;
            }
            stockRequest.setGroupList(groupVoList);
            List<StockRespVO> stockList = stockDao.selectWarehouseStockInfoByPage(stockRequest);
            Integer total = stockDao.countWarehouseStockInfoByPage(stockRequest);
            pageResData.setTotalCount(total);
            pageResData.setDataList(stockList);
            return pageResData;
        } catch (Exception e) {
            LOGGER.error("总库存管理列表查询失败", e);
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    @Override
    public PageResData selectTransportStockInfoByPage(StockRequest stockRequest) {
        try {
            LOGGER.info("物流中心库存列表查询");
            List<PurchaseGroupVo> groupVoList = purchaseGroupService.getPurchaseGroup(null);
            PageResData pageResData = new PageResData();
            if (org.apache.commons.collections.CollectionUtils.isEmpty(groupVoList)) {
                return pageResData;
            }
            stockRequest.setGroupList(groupVoList);
            List<StockRespVO> stockList = stockDao.selectTransportStockInfoByPage(stockRequest);
            HashMap<String, StockRespVO> stockRespMap = new HashMap<>();
            List<StockRespVO> lists = new ArrayList<>();
            Stock stock = null;
            int i = 1;
            // 遍历获取分页数据
            for (StockRespVO stockRespVO : stockList) {
                String str = stockRespVO.getTransportCenterCode() + stockRespVO.getSkuCode();
                if (stockRespMap.get(str) == null) {
                    stockRespMap.put(str, stockRespVO);
                    stockCommon(stockRespMap, stockRespVO, str, stock, i);
                } else {
                    stockCommon(stockRespMap, stockRespVO, str, stock, i);
                }
            }
            for (Map.Entry<String, StockRespVO> entry : stockRespMap.entrySet()) {
                lists.add(entry.getValue());
            }
            Integer count = stockDao.selectTransportStockInfoByPageCount(stockRequest);
            pageResData.setTotalCount(count);
            pageResData.setDataList(lists);
            return pageResData;
        } catch (Exception e) {
            LOGGER.error("物流中心库存列表查询失败", e);
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    @Override
    public PageResData selectStorehouseStockInfoByPage(StockRequest stockRequest) {
        try {
            LOGGER.info("物流中心库存列表查询");
            List<PurchaseGroupVo> groupVoList = purchaseGroupService.getPurchaseGroup(null);
            PageResData pageResData = new PageResData();
            if (CollectionUtils.isEmpty(groupVoList)) {
                return pageResData;
            }
            stockRequest.setGroupList(groupVoList);
            List<StockRespVO> stockList = stockDao.selectStorehouseStockInfoByPage(stockRequest);
            Integer total = stockDao.countStorehouseStockInfoByPage(stockRequest);
            pageResData.setTotalCount(total);
            pageResData.setDataList(stockList);
            return pageResData;
        } catch (Exception e) {
            LOGGER.error("物流中心库存列表查询失败", e);
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    @Override
    public PageInfo<StockFlowRespVo> selectOneStockInfoByStockId(String stockCode, Integer page_no, Integer page_size) {
        try {
            LOGGER.info("根据stockId查询单个stock信息");
            PageHelper.startPage(page_no, page_size);
            // List<StockRespVO> stockRespVOs = stockDao.selectOneStockInfoByStockId(stockId);
            // Long total = stockDao.selectOneStockInfoByStockIdInfoByPage(stockId);
            // new PageResData<>(total.intValue(), stockRespVOs);
            return new PageInfo<>(stockDao.selectOneStockInfoByStockId(stockCode));
        } catch (Exception e) {
            LOGGER.error("根据stockId查询单个stock信息失败", e);
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    /**
     * 功能描述: 验证退供商品信息,有错误则会返回list,否则list为空
     * @param reqVo
     * @return List
     * @auther knight.xie
     * @date 2019/1/8 15:14
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse<PurchaseOutBoundRespVO> verifyReturnSupply(VerifyReturnSupplyReqVo reqVo) {
        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpResponse.success();
            if (StringUtils.isBlank(reqVo.getCompanyCode())) {
                return httpResponse.setMessageId(ResultCode.STOCK_RETURN_SUPPLY_COMPANY_EMPTY);
            }
            if (StringUtils.isBlank(reqVo.getSupplyCode())) {
                return httpResponse.setMessageId(ResultCode.STOCK_RETURN_SUPPLY_SUPPLY_EMPTY);
            }
            if (StringUtils.isBlank(reqVo.getTransportCenterCode())) {
                return httpResponse.setMessageId(ResultCode.STOCK_RETURN_SUPPLY_TRANSPORT_CENTER_EMPTY);
            }
            if (StringUtils.isBlank(reqVo.getWarehouseCode())) {
                return httpResponse.setMessageId(ResultCode.STOCK_RETURN_SUPPLY_WAREHOUSE_EMPTY);
            }
            if (StringUtils.isBlank(reqVo.getPurchaseGroupCode())) {
                return httpResponse.setMessageId(ResultCode.STOCK_RETURN_SUPPLY_PURCHASE_GROUP_EMPTY);
            }
            //如果没有sku
            if (CollectionUtils.isEmpty(reqVo.getItemReqVos())) {
                return httpResponse.setMessageId(ResultCode.STOCK_RETURN_SUPPLY_SKU_EMPTY);
            }
            //验证item数据  TODO 商品数据不全 暂时不验证
//            List<VerifyReturnSupplyErrorRespVo> errorRespVos = lockStock(reqVo);
            PurchaseOutBoundRespVO respVO = lockStock(reqVo);
//            List<VerifyReturnSupplyErrorRespVo> errorRespVos = Lists.newArrayList();
//            PurchaseOutBoundRespVO respVO = new PurchaseOutBoundRespVO();
//            respVO.setOutBoundCode("123456");
//            respVO.setSupplyErrorRespVos(errorRespVos);
            httpResponse.setData(respVO);
//            if(CollectionUtils.isNotEmpty(errorRespVos)) {
//                httpResponse.setData(errorRespVos);
//            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            if (e instanceof BizException) {
                throw e;
            } else {
                throw new BizException(ResultCode.STOCK_RETURN_SUPPLY_VERIFY_ERROR);
            }
        }
        return httpResponse;
    }

    /**
     * 库存锁定
     *
     * @param reqVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PurchaseOutBoundRespVO lockStock(ILockStockReqVO reqVO) {
        PurchaseOutBoundRespVO respVO = new PurchaseOutBoundRespVO();
        respVO.setSupplyErrorRespVos(Lists.newArrayList());
        respVO.setOutBoundCode("LS123");
        try {
            //验证商品数据
            List<VerifyReturnSupplyErrorRespVo> errorRespVos = verifyReturnSupplyItem(reqVO);
            if (CollectionUtils.isNotEmpty(errorRespVos)) {
                respVO.setSupplyErrorRespVos(errorRespVos);
                return respVO;
            }
            List<ILockStockItemReqVo> itemReqVos = (List<ILockStockItemReqVo>) reqVO.getItemReqVos();
            List<String> skuCodes = itemReqVos.stream().map(ILockStockItemReqVo::getSkuCode).collect(Collectors.toList());
//            List<UpdateStockReqVo> updateStockReqVos = Lists.newLinkedList();
//            Map<String, Long> map = skuService.getSkuConvertNumBySkuCodes(skuCodes);
//            for (ILockStockItemReqVo itemReqVo : itemReqVos) {
//                UpdateStockReqVo updateStockReqVo = new UpdateStockReqVo(reqVO.getCompanyCode(), reqVO.getTransportCenterCode(), reqVO.getWarehouseCode(), itemReqVo.getSkuCode(), itemReqVo.getNum(), StockStatusEnum.LOCK_STOCK.getCode());
//                updateStockReqVos.add(updateStockReqVo);
//            }
//            log.info("需要更新库存的数据{}", JSON.toJSON(updateStockReqVos));
//            //更新库存信息
//            Integer updateNum = stockDao.updateBatchStock(updateStockReqVos);
//            log.info("更新成功条数:{}", updateNum);
//            if (updateNum > 0) {
//                //创建出库单信息（废弃）
//                // 这里应该生成流水生成流水
////                String s = outboundService.saveOutBoundInfo(reqVO);
//                respVO.setOutBoundCode("LS123");
//            }
            Boolean b = returnSupplyLockStock(reqVO);
            if (!b) {
                throw new BizException(ResultCode.STOCK_LOCK_ERROR);
            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            if (e instanceof BizException) {
                throw e;
            } else {
                throw new BizException(ResultCode.STOCK_LOCK_ERROR);
            }

        }
        return respVO;

    }

    @Override
    public Boolean returnSupplyLockStock(ILockStockReqVO reqVO) {
        try {
            //生成库存数据
            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            //操作类型
            Integer integer1 = 1;
            stockChangeRequest.setOperationType(integer1);
            //sku信息
            List<StockVoRequest> convert = new ReturnSupplyToStockConverter().convert(reqVO);
            stockChangeRequest.setStockVoRequests(convert);
            //采购编码
            stockChangeRequest.setOrderCode(reqVO.getSourceOderCode());
            HttpResponse httpResponse = changeStock(stockChangeRequest);
            if (MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                return true;
            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
        }
        return false;
    }

    // 退供加锁
    @Override
    public Boolean returnSupplyLockStocks(ILockStocksReqVO reqVO) {
        try {
            //生成库存数据
            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            //操作类型
            Integer integer1 = 1;
            stockChangeRequest.setOperationType(integer1);
            //sku信息
            List<StockVoRequest> convert = new ReturnSupplyToStocksConverter().convert(reqVO);
            stockChangeRequest.setStockVoRequests(convert);
            //采购编码
            stockChangeRequest.setOrderCode(reqVO.getSourceOderCode());
            HttpResponse httpResponse = changeStock(stockChangeRequest);
            if (MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("调用退供加锁接口失败", e);
            log.error(Global.ERROR, e);
            throw new GroundRuntimeException(e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean returnSupplyUnLockStock(ILockStockReqVO reqVO) {
        try {
            //生成库存数据
            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            //操作类型
            Integer integer1 = 3;
            stockChangeRequest.setOperationType(integer1);
            //sku信息
            List<StockVoRequest> convert = new ReturnSupplyToStockConverter().convert(reqVO);
            stockChangeRequest.setStockVoRequests(convert);
            //采购编码
            stockChangeRequest.setOrderCode(reqVO.getSourceOderCode());
            HttpResponse httpResponse = changeStock(stockChangeRequest);
            if (MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                return true;
            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
        }
        return false;
    }

    // 退供解锁
    @Override
    public Boolean returnSupplyUnLockStocks(ILockStocksReqVO reqVO) {
        try {
            //生成库存数据
            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            //操作类型
            Integer integer1 = 3;
            stockChangeRequest.setOperationType(integer1);
            //sku信息
            List<StockVoRequest> convert = new ReturnSupplyToStocksConverter().convert(reqVO);
            stockChangeRequest.setStockVoRequests(convert);
            //采购编码
            stockChangeRequest.setOrderCode(reqVO.getSourceOderCode());
            HttpResponse httpResponse = changeStock(stockChangeRequest);
            if (MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("调用退供解锁接口失败", e);
            log.error(Global.ERROR, e);
            throw new GroundRuntimeException(e.getMessage());
        }
        return false;
    }

    /**
     * 解锁库存
     *
     * @param reqVo 原始订单号
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse unLockStock(UnLockStockReqVo reqVo) {
        log.info("进入解锁方法,原始单号{}", reqVo.getSourceOrderCode());
        try {
            //根据原始单号查询出库信息
            List<UpdateStockReqVo> updateStockReqVos = outboundService.selectUpdateStockInfoBySourceOrderCode(reqVo.getSourceOrderCode(), String.valueOf(StockStatusEnum.UNLOCK_STOCK.getCode()));
            //根据库存信息
            Integer updateNum = stockDao.updateBatchStock(updateStockReqVos);
            if (updateNum > 0) {
                //更新出库单信息
                UpdateOutBoundReqVO reqVO = new UpdateOutBoundReqVO();
                reqVO.setSourceOrderCode(reqVo.getSourceOrderCode());
                reqVO.setOperator(reqVo.getOperator());
                reqVO.setStockStatusCode(StockStatusEnum.UNLOCK_STOCK.getCode());
                reqVO.setStockStatusName(StockStatusEnum.UNLOCK_STOCK.getName());
                outboundService.updateOutBoundInfo(reqVO);
            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            if (e instanceof BizException) {
                throw e;
            } else {
                throw new BizException(ResultCode.STOCK_UNLOCK_ERROR);
            }
        }
        return HttpResponse.success();
    }

    /**
     * 减少并解锁库存
     * @param reqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse reduceUnlockStock(UpdateOutBoundReqVO reqVo) {
        log.info("进入解锁方法,原始单号{}", reqVo.getSourceOrderCode());
        try {
            //根据原始单号查询出库信息
            List<UpdateStockReqVo> updateStockReqVos = outboundService.selectUpdateStockInfoBySourceOrderCode(reqVo.getSourceOrderCode(),
                    String.valueOf(reqVo.getStockStatusCode()));
            //根据库存信息
            Integer updateNum = stockDao.updateBatchStock(updateStockReqVos);
            if (updateNum > 0) {
                outboundService.updateOutBoundInfo(reqVo);
            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            if (e instanceof BizException) {
                throw e;
            } else {
                throw new BizException(ResultCode.STOCK_REDUCE_UNLOCK_STOCK_ERROR);
            }
        }
        return HttpResponse.success();
    }

    /**
     * @param reqVo
     * @return
     * @throws BizException
     */
    private List<VerifyReturnSupplyErrorRespVo> verifyReturnSupplyItem(ILockStockReqVO reqVo) throws BizException {
        List<VerifyReturnSupplyErrorRespVo> errorRespVos = Lists.newLinkedList();
        List<ILockStockItemReqVo> itemRespVos = (List<ILockStockItemReqVo>) reqVo.getItemReqVos();
        VerifyReturnSupplyErrorRespVo errorRespVo = null;
        StringBuilder errorReason = null;
        List<String> queryList = Lists.newLinkedList();
        //拼装查询
        QueryStockSkuReqVo queryStockSkuReqVo = new ILockStockReqVoToQueryStockSkuReqVo().apply(reqVo);
        List<String> skuList = Lists.newLinkedList();
        itemRespVos.forEach(item -> {
            skuList.add(item.getSkuCode());
        });
        //根据公司,物流中心,库存,采购组和skus查询sku库存数据
        List<QueryStockSkuRespVo> queryStockSkuRespVos = stockDao.selectStockSkuInfoByPage(queryStockSkuReqVo);
        queryStockSkuRespVos.forEach(item -> {
            queryList.add(item.getSkuCode());
        });
        if (skuList.removeAll(queryList)) {
            //如果skuList数据不为空,则说明有sku数据不在公司,物流中心,库存,采购组
            if (CollectionUtils.isNotEmpty(skuList)) {
                errorReason = new StringBuilder("%s").append(",不在").append(reqVo.getCompanyCode()).append("/")
                        .append(reqVo.getSupplyCode()).append("/").append(reqVo.getTransportCenterCode()).append("/")
                        .append(reqVo.getWarehouseCode()).append("/").append(reqVo.getPurchaseGroupCode());
                for (String s : skuList) {
                    errorRespVo = new VerifyReturnSupplyErrorRespVo(s, String.format(errorReason.toString(), s));
                    errorRespVos.add(errorRespVo);
                }
            }
        } else {
            throw new BizException(ResultCode.STOCK_RETURN_SUPPLY_VERIFY_ERROR);
        }
        //验证库存数量
        for (ILockStockItemReqVo itemRespVo : itemRespVos) {
            for (QueryStockSkuRespVo queryStockSkuRespVo : queryStockSkuRespVos) {
                //如果sku相等,并且库存数量小于退供数量则记录异常
                if (itemRespVo.getSkuCode().equals(queryStockSkuRespVo.getSkuCode()) && itemRespVo.getNum() > queryStockSkuRespVo.getStock()) {
                    errorReason = new StringBuilder("%s").append(",退供数量(%s)大于库存数量(%s)");
                    errorRespVo = new VerifyReturnSupplyErrorRespVo(itemRespVo.getSkuCode(),
                            String.format(errorReason.toString(), itemRespVo.getSkuCode(), itemRespVo.getNum(), queryStockSkuRespVo.getStock()));
                    errorRespVos.add(errorRespVo);
                }
            }
        }
        return errorRespVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InboundReqVo save(InboundReqVo reqVo) {
        //生成入库单
        try {
//            Integer stock = saveStock(reqVo);
            Boolean aBoolean = purchaseSaveStockInfo(reqVo);
            InboundReqVo vo = new InboundReqVo();
            if (aBoolean) {
                vo.setCode(reqVo.getCode());
                vo.setActualNum(reqVo.getTotalNum());
                vo.setActualAmount(reqVo.getTotalAmount());
                vo.setNoTaxActualAmount(reqVo.getNoTaxTotalAmount());
                vo.setSaleUnitActualNum(reqVo.getSaleUnitTotalNum());
                List<InboundItemReqVo> voList = Lists.newArrayList();
                for (InboundItemReqVo o : reqVo.getPurchaseItemVos()) {
                    InboundItemReqVo itemReqVo = new InboundItemReqVo();
                    itemReqVo.setPurchaseCode(o.getPurchaseCode());
                    itemReqVo.setActualNum(o.getNum());
                    itemReqVo.setActualTotalPrice(o.getTotalPrice());
                    itemReqVo.setId(o.getId());
                    voList.add(itemReqVo);
                }
                vo.setPurchaseItemVos(voList);
                //保存入库信息
                InboundReqSave convert = new InboundReqVo2InboundSaveConverter(skuService).convert(reqVo);
                inboundService.saveInbound(convert);
            }
            return vo;
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            if (e instanceof BizException) {
                throw new BizException(e.getMessage());
            } else {
                throw new BizException("入库单入库失败");
            }
        }
    }

    @Override
    public Boolean purchaseSaveStockInfo(InboundReqVo reqVo) {
        try {
            //生成库存数据
            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            //操作类型
            Integer integer1 = 6;
            stockChangeRequest.setOperationType(integer1);
            //sku信息
            List<StockVoRequest> convert = new PurchaseToStockConverter().convert(reqVo);
            stockChangeRequest.setStockVoRequests(convert);
            //采购编码
            stockChangeRequest.setOrderCode(reqVo.getCode());
            HttpResponse httpResponse = changeStock(stockChangeRequest);
            if (MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                return true;
            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Save
    public String stockFlow(StockFlowRequest reqVo) {
        try {
            Stock stock = stockFlowDao.selectOneStockInfoByStockFlow(reqVo);
            if (null == stock) {
                if (reqVo.getLockType() == 0) {
                    throw new BizException(ResultCode.STOCK_LOCK_ERROR);
                } else {
                    throw new BizException(ResultCode.STOCK_UNLOCK_ERROR);
                }
            }
            String lockCode;
            StockFlow stockFlow = new StockFlow();
            BeanCopyUtils.copy(reqVo, stockFlow);
            long id = IdSequenceUtils.getInstance().nextId();
            lockCode = "LOCK" + id;
//            stockFlow.setLockCode(lockCode);
            stockFlow.setCreateTime(new Date());
            stockFlow.setBeforeLockCount(stock.getLockCount());
            stockFlow.setAfterLockCount(stock.getLockCount() + reqVo.getChangeNum());
//            stockFlow.setLockType(reqVo.getLockType());
//            stockFlow.setBeforeAuthenticNum(stock.getAuthenticNum());
            stockFlow.setBeforeAvailableCount(stock.getAvailableCount());
            stockFlow.setBeforeInventoryCount(stock.getInventoryCount());
//            stockFlow.setBeforeSpareNum(stock.getSpareNum());
            if (reqVo.getLockType() == 1) {//解锁库存
                Long unLockNum = reqVo.getLockNum() - reqVo.getChangeNum();
                stock.setLockCount(stock.getLockCount() - reqVo.getLockNum());
                if (reqVo.getChangeType() == 1) {
                    if (reqVo.getChangeNum() == 0) {
//                        stock.setAuthenticNum(stock.getAuthenticNum() + reqVo.getLockNum());
                        stock.setAvailableCount(stock.getAvailableCount() + reqVo.getLockNum());
                    } else {//解锁并减少库存
                        stock.setAvailableCount(stock.getAvailableCount() + unLockNum);
//                        stock.setAuthenticNum(stock.getAuthenticNum() + unLockNum);
                        stock.setInventoryCount(stock.getInventoryCount() - reqVo.getChangeNum());
                    }
                } else {
//                    stock.setSpareNum(stock.getSpareNum() - reqVo.getChangeNum());
                }
                stockDao.updateByPrimaryKey(stock);
            } else {//锁定库存
                stock.setLockCount(stock.getLockCount() + reqVo.getChangeNum());
                if (reqVo.getChangeType() == 1) {
//                    stock.setAuthenticNum(stock.getAuthenticNum() - reqVo.getChangeNum());
                    stock.setAvailableCount(stock.getAvailableCount() - reqVo.getChangeNum());
                } else {
//                    stock.setSpareNum(stock.getSpareNum() - reqVo.getChangeNum());
                }
                stock.setUpdateTime(new Date());
                stockDao.updateByPrimaryKey(stock);
            }
//            stockFlow.setAfterAuthenticNum(stock.getAuthenticNum());
            stockFlow.setAfterAvailableCount(stock.getAvailableCount());
            stockFlow.setAfterInventoryCount(stock.getInventoryCount());
//            stockFlow.setAfterSpareNum(stock.getSpareNum());
            stockFlowDao.insertOne(stockFlow);
            return lockCode;
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            if (reqVo.getLockType() == 0) {
                throw new BizException(ResultCode.STOCK_LOCK_ERROR);
            } else {
                throw new BizException(ResultCode.STOCK_UNLOCK_ERROR);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse changeStock(StockChangeRequest request) {
        return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse stockAndBatchChange(ChangeStockRequest request) {
        LOGGER.info("对库存开始操作, 入参为：" + JsonUtil.toJson(request));
        if (CollectionUtils.isEmpty(request.getStockList())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        try {
            // 查询需要做修改的库存数据
            List<Stock> stocks = stockDao.stockByWarehouseAndSku(request.getStockList());
            Map<String, Stock> stockMap = new HashMap<>();
            stocks.forEach(stock -> {
                stockMap.put(stock.getSkuCode() + stock.getWarehouseCode(), stock);
            });

            StockFlow stockFlow;
            Stock stock;
            List<Stock> updates = new ArrayList<>();
            List<Stock> adds = new ArrayList<>();
            Boolean flage = false;

            // 将需要修改的库存进行逻辑计算
            List<StockFlow> stockFlows = new ArrayList<>();
            for (StockInfoRequest stockInfoRequest : request.getStockList()) {
                // 给sku加锁
                long time = System.currentTimeMillis() + 30;
                if (!redisLockService.lock(stockInfoRequest.getSkuCode(), String.valueOf(time))) {
                    LOGGER.info("redis给sku加锁失败：" + stockInfoRequest.getSkuCode());
                    throw new BizException("redis给sku加锁失败：" + stockInfoRequest.getSkuCode());
                }

                // 设置库存公共流水值
                stockFlow = new StockFlow();
                stockFlow.setFlowCode("FL" + IdSequenceUtils.getInstance().nextId());
                stockFlow.setCreateByName(stockInfoRequest.getOperatorName());
                stockFlow.setUpdateByName(stockInfoRequest.getOperatorName());
                stockFlow.setCreateById(stockInfoRequest.getOperatorId());
                stockFlow.setUpdateById(stockInfoRequest.getOperatorId());
                stockFlow.setChangeCount(stockInfoRequest.getChangeCount());
                stockFlow.setOperationType(request.getOperationType());

                if (stockMap.containsKey(stockInfoRequest.getSkuCode() + stockInfoRequest.getWarehouseCode())) {
                    stock = stockMap.get(stockInfoRequest.getSkuCode() + stockInfoRequest.getWarehouseCode());
                    // 设置库存流水变更前的值
                    stockFlow.setStockCode(stock.getStockCode());
                    stockFlow.setSkuCode(stock.getSkuCode());
                    stockFlow.setSkuName(stock.getSkuName());
                    stockFlow.setOperationType(request.getOperationType());
                    stockFlow.setBeforeInventoryCount(stock.getInventoryCount());
                    stockFlow.setBeforeLockCount(stock.getLockCount());
                    stockFlow.setBeforeAvailableCount(stock.getAvailableCount());
                    stockFlow.setBeforeAllocationWayCount(stock.getAllocationWayCount());
                    stockFlow.setBeforePurchaseWayCount(stock.getPurchaseWayCount());
                    stockFlow.setBeforeTotalWayCount(stock.getTotalWayCount());

                    // 按照不同的单据类型变更相对应的库存数量
                    stock = stockVoRequestToStock(stock, stockInfoRequest, request.getOperationType());
                    if (null != stock) {
                        updates.add(stock);
                    } else {
                        flage = true;
                        break;
                    }

                    // 设置库存流水变化后值
                    stockFlow.setAfterInventoryCount(stock.getInventoryCount());
                    stockFlow.setAfterLockCount(stock.getLockCount());
                    stockFlow.setAfterAvailableCount(stock.getAvailableCount());
                    stockFlow.setAfterAllocationWayCount(stock.getAllocationWayCount());
                    stockFlow.setAfterPurchaseWayCount(stock.getPurchaseWayCount());
                    stockFlow.setAfterTotalWayCount(stock.getTotalWayCount());
                    stockFlow.setDocumentCode(stockInfoRequest.getDocumentCode());
                    stockFlow.setDocumentType(stockInfoRequest.getDocumentType());
                    stockFlow.setSourceDocumentCode(stockInfoRequest.getSourceDocumentCode());
                    stockFlow.setSourceDocumentType(stockInfoRequest.getSourceDocumentType());
                    stockFlow.setRemark(stockInfoRequest.getRemark());
                    stockFlow.setStockCost(stockInfoRequest.getNewPurchasePrice());
                    stockFlows.add(stockFlow);
                } else {
                    stock = new Stock();
                    // 设置库存流水变更前的值
                    stockFlow.setSkuCode(stockInfoRequest.getSkuCode());
                    stockFlow.setSkuName(stockInfoRequest.getSkuName());
                    stockFlow.setBeforeInventoryCount(0L);
                    stockFlow.setBeforeLockCount(0L);
                    stockFlow.setBeforeAvailableCount(0L);
                    stockFlow.setBeforeAllocationWayCount(0L);
                    stockFlow.setBeforePurchaseWayCount(0L);
                    stockFlow.setBeforeTotalWayCount(0L);

                    // 按照不同的单据类型变更相对应的库存数量
                    stock = stockVoRequestToStock(stock, stockInfoRequest, request.getOperationType());
                    ProductSkuInfo productSkuInfo = productSkuDao.getSkuInfo(stock.getSkuCode());
                    if (productSkuInfo == null) {
                        flage = true;
                        break;
                    }

                    stock.setUnitCode(productSkuInfo.getUnitCode());
                    stock.setUnitName(productSkuInfo.getUnitName());
                    if (stock != null) {
                        adds.add(stock);
                    } else {
                        flage = true;
                        break;
                    }

                    // 设置库存流水变更后的值
                    stockFlow.setStockCode(stock.getStockCode());
                    stockFlow.setAfterInventoryCount(stock.getInventoryCount());
                    stockFlow.setAfterLockCount(stock.getLockCount());
                    stockFlow.setAfterAvailableCount(stock.getAvailableCount());
                    stockFlow.setAfterAllocationWayCount(stock.getAllocationWayCount());
                    stockFlow.setAfterPurchaseWayCount(stock.getPurchaseWayCount());
                    stockFlow.setAfterTotalWayCount(stock.getTotalWayCount());
                    stockFlow.setDocumentCode(stockInfoRequest.getDocumentCode());
                    stockFlow.setDocumentType(stockInfoRequest.getDocumentType());
                    stockFlow.setSourceDocumentCode(stockInfoRequest.getSourceDocumentCode());
                    stockFlow.setSourceDocumentType(stockInfoRequest.getSourceDocumentType());
                    stockFlow.setRemark(stockInfoRequest.getRemark());
                    stockFlow.setStockCost(stockInfoRequest.getNewPurchasePrice());
                    stockFlows.add(stockFlow);
                }

                // 给sku解锁 - redis
                redisLockService.unlock(stockInfoRequest.getSkuCode(), String.valueOf(time));
            }

            if (flage) {
                return HttpResponse.failure(ResultCode.STOCK_CHANGE_ERROR);
            }
            if (CollectionUtils.isNotEmpty(stockFlows)) {
                stockFlowDao.insertAll(stockFlows);
            }
            if (CollectionUtils.isNotEmpty(updates)) {
                stockDao.updateBatch(updates);
            }
            if (CollectionUtils.isNotEmpty(adds)) {
                stockDao.insertBatch(adds);
            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            LOGGER.error("操作库存失败", e);
            throw new BizException("操作库存失败");
        }
        return HttpResponse.success();
    }

    /** 参数转换成库存数据
     * @param stock  库存实体
     * @param request 请求改变实体
     * @param operationType  操作类型
     */
    private Stock stockVoRequestToStock(Stock stock, StockInfoRequest request, Integer operationType) {
        if (request.getWarehouseCode() != null) {
            Integer warehouseType = stockDao.selectWarehouseType(request.getWarehouseCode());
            request.setWarehouseType(warehouseType);
        }
        if (null == stock.getId()) {
            BeanCopyUtils.copy(request, stock);
            stock.setStockCode("ST" + IdSequenceUtils.getInstance().nextId());
            stock.setInventoryCount(0L);
            stock.setAvailableCount(0L);
            stock.setLockCount(0L);
            stock.setPurchaseWayCount(0L);
            stock.setAllocationWayCount(0L);
            stock.setTotalWayCount(0L);
            stock.setTaxRate(BigDecimal.ZERO);
            stock.setTaxCost(BigDecimal.ZERO);
            stock.setCreateById(request.getOperatorId());
            stock.setCreateByName(request.getOperatorName());
        }
        stock.setUpdateById(request.getOperatorId());
        stock.setUpdateByName(request.getOperatorName());

        // 赋值最新采购价
        if (request.getNewPurchasePrice() != null) {
            stock.setNewPurchasePrice(request.getNewPurchasePrice());
        }

        // 赋值库存税率
        if (request.getTaxRate() != null) {
            stock.setTaxRate(request.getTaxRate());
        }
        stock.setNewDelivery(request.getNewDelivery());
        stock.setNewDeliveryName(request.getNewDeliveryName());
        Long inventoryCount = stock.getInventoryCount() == null ? 0L : stock.getInventoryCount();
        Long availableCount = stock.getAvailableCount() == null ? 0L : stock.getAvailableCount();
        Long lockCount = stock.getLockCount() == null ? 0L : stock.getLockCount();
        Long purchaseWayCount = stock.getPurchaseWayCount() == null ? 0L : stock.getPurchaseWayCount();
        Long totalWayCount = stock.getTotalWayCount() == null ? 0L : stock.getTotalWayCount();
        Long allocationWayCount = stock.getAllocationWayCount() == null ? 0L : stock.getAllocationWayCount();

        // 变更库存数
        Long changeCount = request.getChangeCount() == null ? 0L : request.getChangeCount();
        // 操作类型 1.锁定库存 2.减库存并解锁 3.解锁库存. 4.减库存 5.加并锁定库存 6.加库存 7.加在途 8.减在途 9.锁转移(锁定库存移入/移出)
        switch (operationType) {
            //锁定库存 - 库存不变，锁定库存增加，可用库存减少
            case 1:
                // 验证可用库存在操作前后都不能为负。实际操作为：加锁定库存、减可用库存。
                if(availableCount < changeCount){
                    LOGGER.error("锁定库存: 可用库存在操作前后都不能为负，sku:" + request.getSkuCode());
                    throw new BizException("锁定库存: 可用库存在操作前后都不能为负，sku:" + request.getSkuCode());
                }
                stock.setLockCount(lockCount + changeCount);
                stock.setAvailableCount(availableCount - changeCount);
                break;

            // 减库存并解锁 - 库存减少，锁定库存减少，可用库存不变
            case 2:
                // 验证锁定库存在操作前后都不能为负。实际操作为：减总库存、减锁定库存。
                if(lockCount < changeCount || inventoryCount < changeCount){
                    LOGGER.error("减库存并解锁: 锁定库存、总库存在操作前后都不能为负,sku:" + request.getSkuCode());
                    throw new BizException("减库存并解锁: 锁定库存、总库存在操作前后都不能为负，sku:" + request.getSkuCode());
                }
                stock.setInventoryCount(inventoryCount - changeCount);
                stock.setLockCount(lockCount - changeCount);
                break;

            // 解锁库存 - 库存不变，锁定库存减少，可用库存增加
            case 3:
                // 验证锁定库存在操作前后都不能为负。实际操作为：减锁定库存、加可用库存。
                if(lockCount < changeCount){
                    LOGGER.error("解锁库存: 锁定库存在操作前后都不能为负,sku:" + request.getSkuCode());
                    throw new BizException("解锁库存: 锁定库存在操作前后都不能为负，sku:" + request.getSkuCode());
                }
                stock.setAvailableCount(availableCount + changeCount);
                stock.setLockCount(lockCount - changeCount);
                break;

            // 减库存 - 库存减少，锁定库存不变，可用库存减少
            case 4:
                // 验证总库存、可用库存在操作前后都不能为负。实际操作为：减总库存、减可用库存。
                if(availableCount < changeCount || inventoryCount < changeCount ){
                    LOGGER.error("减库存: 可用库存、总库存在操作前后都不能为负,sku:" + request.getSkuCode());
                    throw new BizException("减库存:可用库存、总库存在操作前后都不能为负，sku:" + request.getSkuCode());
                }
                stock.setAvailableCount(availableCount - changeCount);
                stock.setInventoryCount(inventoryCount - changeCount);
                break;

            // 加并锁定库存 - 库存增加，锁定库存增加，可用库存不变
            case 5:
                // 不验证任何库存。实际操作为：加总库存、加锁定库存。
                stock.setInventoryCount(inventoryCount + changeCount);
                stock.setLockCount(lockCount + changeCount);
                break;

            // 加库存 - 库存增加，锁定库存不变，可用库存增加
            case 6:
                // 不验证任何库存。实际操作为：加总库存、加可用库存
                stock.setInventoryCount(inventoryCount + changeCount);
                stock.setAvailableCount(availableCount + changeCount);
                break;

            // 加在途(采购、调拨) - 不验证在途数。实际操作为：加在途数。
            case 7:
                if(request.getSourceDocumentType() == 3){
                    // 采购加在途
                    stock.setPurchaseWayCount(purchaseWayCount + changeCount);
                    stock.setTotalWayCount(totalWayCount + changeCount);
                }else if(request.getSourceDocumentType() == 4){
                    // 加调拨加在途
                    stock.setAllocationWayCount(allocationWayCount + changeCount);
                    stock.setTotalWayCount(totalWayCount + changeCount);
                }
                break;

            // 减在途(采购、调拨) - 验证在途数不能为负，操作后在途数不能为负。
            case 8:
                // 预计在途数
                Long preWayCount = request.getPreWayCount() == null ? 0L : request.getPreWayCount();
                if(request.getSourceDocumentType() == 3){
                    // 采购减在途
                    if(purchaseWayCount < preWayCount){
                        LOGGER.error("采购减在途: 采购在途数不能为负,sku:" + request.getSkuCode());
                        throw new BizException("采购减在途: 采购在途数不能为负，sku:" + request.getSkuCode());
                    }
                    stock.setPurchaseWayCount(purchaseWayCount - preWayCount);
                    stock.setTotalWayCount(stock.getPurchaseWayCount() + stock.getAllocationWayCount());
                    stock.setInventoryCount(inventoryCount + changeCount);
                    stock.setAvailableCount(availableCount + changeCount);

                }else if(request.getSourceDocumentType() == 4){
                    // 调拨减在途
                    if(allocationWayCount < preWayCount){
                        LOGGER.error("调拨减在途: 采购在途数不能为负,sku:" + request.getSkuCode());
                        throw new BizException("调拨减在途: 采购在途数不能为负，sku:" + request.getSkuCode());
                    }
                    stock.setAllocationWayCount(allocationWayCount - preWayCount);
                    stock.setTotalWayCount(stock.getPurchaseWayCount() + stock.getAllocationWayCount());
                    stock.setInventoryCount(inventoryCount + changeCount);
                    stock.setAvailableCount(availableCount + changeCount);
                }
                break;

            // 锁转移(锁定库存移入/移出)
            case 9:
                // 不验证任何库存。实际操作为：库存不变动，库存日志新增了减锁定和加锁定
                break;
            default:
                return null;
        }
        return stock;
    }

    @Override
    public Map<String, Long> getLatestPurchasePriceBySkuCodes(List<String> skuCodes) {
        List<Stock> list = stockDao.getLatestPurchasePriceBySkuCodes(skuCodes);
        Map<String, Long> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(o -> {
                String countString = String.valueOf(o.getNewPurchasePrice());
                Long count = Long.valueOf(countString);
                map.put(o.getSkuCode(), count);
            });
        }
        return map;
    }

    @Override
    public HttpResponse logs(StockLogsRequest stockLogsRequest) {
        PageHelper.startPage(stockLogsRequest.getPageNo(), stockLogsRequest.getPageSize());
        List<StockFlow> list = stockDao.selectStockLogs(stockLogsRequest);
        return HttpResponse.success(new PageInfo<StockFlow>(list));
    }

    @Override
    public List<Stock> selectGroup() {
        return stockDao.selectGroup();
    }

    @Override
    public List<Stock> selectListByWareHouseCode(Stock stock) {
        List<Stock> list = stockDao.selectListByWareHouseCode(stock);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<OrderInfoItemProductBatch> lockBatchStock(List<LockOrderItemBatchReqVO> vo) {
        List<OrderInfoItemProductBatch> list = Lists.newArrayList();
        Date date = new Date();
        for (int i = 0; i < vo.size(); i++) {
            LockOrderItemBatchReqVO reqVO = vo.get(i);
            OrderInfoItemProductBatch copy = BeanCopyUtils.copy(reqVO, OrderInfoItemProductBatch.class);
            copy.setBatchNumber(System.currentTimeMillis() + i + "");
            copy.setProductTime(date);
            copy.setProductLineNum((long) i);
            list.add(copy);
        }
        return list;
    }

    /**
     * 批次库存管理查询
     *
     * @param stockBatchRequest
     * @return
     */
    @Override
    public PageResData selectStockBatchInfoByPage(StockBatchRequest stockBatchRequest) {
        try {
            LOGGER.info("批次库存管理列表条件查询");
            List<StockBatchRespVO> stockLists = stockDao.selectStockBatchInfoByPage(stockBatchRequest);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (StockBatchRespVO stockList : stockLists) {
                String format = sdf.format(sdf.parse(stockList.getProductionDate()));
                stockList.setProductionDate(format);
            }
            Integer total = stockDao.countStockBatchInfoByPage(stockBatchRequest);
            return new PageResData<>(total, stockLists);
        } catch (Exception e) {
            LOGGER.error("批次库存管理列表条件查询失败", e);
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    /**
     * 根据stockBatchId查询单个库存信息
     *
     * @param stockBatchId
     * @return
     */
    @Override
    public PageInfo<StockBatchRespVO> selectOneStockBatchInfoByStockBatchId(Long stockBatchId, Integer page_no, Integer page_size) {
        try {
            LOGGER.info("根据stockBatchId查询单个stockBatch信息");
            PageHelper.startPage(page_no, page_size);
            // List<StockBatchRespVO> stockBatchRespVOs = stockDao.selectOneStockBatchInfoByStockBatchId(stockBatchId);
            // Long total = stockDao.selectOneStockBatchInfoByStockBatchIdInfoByPage(stockBatchId);
            // new PageResData<>(total.intValue(),stockBatchRespVOs);
            return new PageInfo<StockBatchRespVO>(stockDao.selectOneStockBatchInfoByStockBatchId(stockBatchId));
        } catch (Exception e) {
            LOGGER.error("根据stockBatchId查询单个stockBatch信息失败", e);
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse operationStockBatch(StockChangeRequest stockChangeRequest) {
        if (CollectionUtils.isEmpty(stockChangeRequest.getStockBatchVoRequest())) {
            return HttpResponse.failure(ResultCode.STOCK_CHANGE_ERROR);
        }
        try {
            LOGGER.info("查stock和inbound表给stockBatch中插入数据");
            // 查询商品表数据
            List<StockBatchProductSkuRespVO> stockBatchProductSkuRespVOs = stockDao.selectProductSku();
            // 创建批次库存实体类
            StockBatch stockBatch = new StockBatch();
            // 插入数据集合
            ArrayList stockBatchs = new ArrayList();
            // 创建批次库存流水表实体类
            StockBatchFlow stockBatchFlow = new StockBatchFlow();
            // 插入流水集合
            ArrayList flows = new ArrayList();
            // 批次表中拿批次号和入库批次号进行比对，不同直接入库，相同判断库类型，更新数据。
            List<StockBatchRespVO> stockBatchCodes = stockDao.selectStockBatchDistinct();
            // 取出表批次号
            for (StockBatchRespVO stockBatchCode : stockBatchCodes) {
                // 入库批次号
                for (StockBatchVoRequest stockBatchVoRequest : stockChangeRequest.getStockBatchVoRequest()) {
                    // 不同-不是一批次-增
                    if (!stockBatchVoRequest.getBatchCode().equals(stockBatchCode.getBatchCode())) {
                        // 判断商品skuCode和入库的商品Sku
                        for (StockBatchProductSkuRespVO stockBatchProductSkuRespVO : stockBatchProductSkuRespVOs) {
                            if (stockBatchVoRequest.getSkuCode().equals(stockBatchProductSkuRespVO.getSkuCode())) {
                                stockBatch.setStockBatchCode("SB" + IdSequenceUtils.getInstance().nextId());
                                stockBatch.setCompanyCode(stockBatchVoRequest.getCompanyCode());
                                stockBatch.setCompanyName(stockBatchVoRequest.getCompanyName());
                                stockBatch.setTransportCenterCode(stockBatchVoRequest.getTransportCenterCode());
                                stockBatch.setTransportCenterName(stockBatchVoRequest.getTransportCenterName());
                                stockBatch.setWarehouseName(stockBatchVoRequest.getWarehouseName());
                                stockBatch.setWarehouseCode(stockBatchVoRequest.getWarehouseCode());
                                stockBatch.setWarehouseType(stockBatchVoRequest.getWarehouseType());
                                stockBatch.setSkuCode(stockBatchVoRequest.getSkuCode());
                                stockBatch.setSkuName(stockBatchVoRequest.getSkuName());
                                stockBatch.setBatchCode(stockBatchVoRequest.getBatchCode());
                                stockBatch.setProductDate(stockBatchVoRequest.getProductionDate());
                                stockBatch.setBatchRemark(stockBatchVoRequest.getBatchRemark());
                                stockBatch = stockBatchVoRequestToStock(stockBatch, stockBatchCode, stockBatchVoRequest, stockChangeRequest.getOperationType());
                                stockBatch.setSupplierCode(stockBatchVoRequest.getSupplierCode());
                                stockBatch.setPurchasePrice(stockBatchVoRequest.getPurchasePrice());
                                stockBatch.setTaxRate(stockBatchVoRequest.getTaxRate());
                                stockBatchs.add(stockBatch);
                                // 设置批次库存流水
                                stockBatchFlow.setStockBatchCode(stockBatch.getStockBatchCode());
                                stockBatchFlow.setFlowBatchCode("FL" + IdSequenceUtils.getInstance().nextId());
                                stockBatchFlow.setBatchCode(stockBatchVoRequest.getBatchCode());
                                stockBatchFlow.setSkuCode(stockBatch.getSkuCode());
                                stockBatchFlow.setSkuName(stockBatch.getSkuName());
                                stockBatchFlow.setOperationType(1);
                                stockBatchFlow.setDocumentType(stockChangeRequest.getOrderType());
                                stockBatchFlow.setDocumentCode(stockChangeRequest.getOrderCode());
                                stockBatchFlow.setSourceDocumentType(stockBatchVoRequest.getSourceDocumentType());
                                stockBatchFlow.setSourceDocumentCode(stockBatchVoRequest.getSourceDocumentNum());
                                stockBatchFlow.setUpdateById(stockBatchVoRequest.getUpdateByCode());
                                stockBatchFlow.setUpdateByName(stockBatchVoRequest.getUpdateByName());
                                stockBatchFlow.setRemark(stockBatchVoRequest.getRemark());
                                stockBatchFlow.setBeforeInventoryCount(stockBatchCode.getInventoryNum());
                                stockBatchFlow.setAfterInventoryCount(stockBatch.getInventoryCount());
                                stockBatchFlow.setBeforeAvailableCount(stockBatchCode.getAvailableNum());
                                stockBatchFlow.setAfterAvailableCount(stockBatch.getAvailableCount());
                                stockBatchFlow.setBeforeLockCount(stockBatchCode.getLockNum());
                                stockBatchFlow.setAfterLockCount(stockBatch.getLockCount());
                                stockBatchFlow.setChangeCount(stockBatchVoRequest.getChangeNum());
                                stockBatchFlow.setOperationType(stockChangeRequest.getOperationType());
                                flows.add(stockBatchFlow);
                            } else {
                                throw new BizException("新增入库单数据时,更改stockBatch表失败");
                            }
                        }
                        // 批次表插入数据
                        stockDao.insertStockBatch(stockBatchs);
                        stockDao.insertStockBatchFlow(flows);
                    } else {
                        // 相同-判断商品skuCode和入库的商品Sku
                        for (StockBatchProductSkuRespVO stockBatchProductSkuRespVO : stockBatchProductSkuRespVOs) {
                            if (stockBatchVoRequest.getSkuCode().equals(stockBatchProductSkuRespVO.getSkuCode())) {
                                stockBatch.setStockBatchCode(stockBatchCode.getStockBatchCode());
                                stockBatch.setCompanyCode(stockBatchVoRequest.getCompanyCode());
                                stockBatch.setCompanyName(stockBatchVoRequest.getCompanyName());
                                stockBatch.setTransportCenterCode(stockBatchVoRequest.getTransportCenterCode());
                                stockBatch.setTransportCenterName(stockBatchVoRequest.getTransportCenterName());
                                stockBatch.setWarehouseName(stockBatchVoRequest.getWarehouseName());
                                stockBatch.setWarehouseCode(stockBatchVoRequest.getWarehouseCode());
                                stockBatch.setWarehouseType(stockBatchVoRequest.getWarehouseType());
                                stockBatch.setSkuCode(stockBatchVoRequest.getSkuCode());
                                stockBatch.setSkuName(stockBatchVoRequest.getSkuName());
                                stockBatch.setBatchCode(stockBatchVoRequest.getBatchCode());
                                stockBatch.setProductDate(stockBatchVoRequest.getProductionDate());
                                stockBatch.setBatchRemark(stockBatchVoRequest.getBatchRemark());
                                stockBatch = stockBatchVoRequestToStock(stockBatch, stockBatchCode, stockBatchVoRequest, stockChangeRequest.getOperationType());
                                stockBatch.setSupplierCode(stockBatchVoRequest.getSupplierCode());
                                stockBatch.setPurchasePrice(stockBatchVoRequest.getPurchasePrice());
                                stockBatch.setTaxRate(stockBatchVoRequest.getTaxRate());
                                stockBatchs.add(stockBatch);
                                // 设置批次库存流水
                                stockBatchFlow.setStockBatchCode(stockBatch.getStockBatchCode());
                                stockBatchFlow.setFlowBatchCode("FL" + IdSequenceUtils.getInstance().nextId());
                                stockBatchFlow.setBatchCode(stockBatchVoRequest.getBatchCode());
                                stockBatchFlow.setSkuCode(stockBatch.getSkuCode());
                                stockBatchFlow.setSkuName(stockBatch.getSkuName());
                                stockBatchFlow.setOperationType(stockChangeRequest.getOperationType());
                                stockBatchFlow.setDocumentType(stockChangeRequest.getOrderType());
                                stockBatchFlow.setDocumentCode(stockChangeRequest.getOrderCode());
                                stockBatchFlow.setSourceDocumentType(stockBatchVoRequest.getSourceDocumentType());
                                stockBatchFlow.setSourceDocumentCode(stockBatchVoRequest.getSourceDocumentNum());
                                stockBatchFlow.setUpdateById(stockBatchVoRequest.getUpdateByCode());
                                stockBatchFlow.setUpdateByName(stockBatchVoRequest.getUpdateByName());
                                stockBatchFlow.setRemark(stockBatchVoRequest.getRemark());
                                stockBatchFlow.setBeforeInventoryCount(stockBatchCode.getInventoryNum());
                                stockBatchFlow.setAfterInventoryCount(stockBatch.getInventoryCount());
                                stockBatchFlow.setBeforeAvailableCount(stockBatchCode.getAvailableNum());
                                stockBatchFlow.setAfterAvailableCount(stockBatch.getAvailableCount());
                                stockBatchFlow.setBeforeLockCount(stockBatchCode.getLockNum());
                                stockBatchFlow.setAfterLockCount(stockBatch.getLockCount());
                                stockBatchFlow.setChangeCount(stockBatchVoRequest.getChangeNum());
                                flows.add(stockBatchFlow);
                            } else {
                                throw new BizException("修改入库单数据时,更改stockBatch表失败");
                            }
                        }
                        stockDao.updateStockBatch(stockBatchs);
                        stockDao.insertStockBatchFlow(flows);
                    }
                }
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("查stock和inbound表给stockBatch中插入数据失败", e);
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    /**
     * 参数转换成库存数据
     *
     * @param stockBatch          批次库存实体
     * @param stockBatchCode      批次库存数据
     * @param stockBatchVoRequest 请求改变实体
     * @param operationType       操作类型
     * @return
     */

    private StockBatch stockBatchVoRequestToStock(StockBatch stockBatch, StockBatchRespVO stockBatchCode, StockBatchVoRequest stockBatchVoRequest, Integer operationType) {
        /*
          // stockBatch.setAvailableNum(stockBatchVoRequest.getChangeNum());  // 可用库存数
          // stockBatch.setLockNum(0L); //   新增不锁定没有值
          // stockBatch.setInventoryNum(stockBatch.getAvailableNum()+stockBatch.getLockNum());  // 总库存数
        */
        switch (operationType) {
            //直接加库存
            case 1:
                stockBatch.setInventoryCount(stockBatchVoRequest.getChangeNum().longValue());
                stockBatch.setAvailableCount(stockBatchVoRequest.getChangeNum().longValue());
                break;
            //锁定库存数
            case 2:
                stockBatch.setLockCount(stockBatch.getLockCount() + stockBatchVoRequest.getChangeNum());
                stockBatch.setAvailableCount(stockBatchCode.getAvailableNum() - stockBatchVoRequest.getChangeNum());
                break;
            //减少库存并解锁
            case 3:
                stockBatch.setInventoryCount(stockBatchCode.getInventoryNum() - stockBatchVoRequest.getChangeNum());
                stockBatch.setLockCount(stockBatchCode.getLockNum() - stockBatchVoRequest.getChangeNum());
                break;
            //解锁锁定库存
            case 4:
                stockBatch.setAvailableCount(stockBatchCode.getAvailableNum() + stockBatchVoRequest.getChangeNum());
                stockBatch.setLockCount(stockBatchCode.getLockNum() - stockBatchVoRequest.getChangeNum());
                break;
            //无锁定直接减库存 总库存减可用库存减
            case 5:
                stockBatch.setAvailableCount(stockBatchCode.getAvailableNum() - stockBatchVoRequest.getChangeNum());
                stockBatch.setInventoryCount(stockBatchCode.getInventoryNum() - stockBatchVoRequest.getChangeNum());
                break;
            //锁定库存转移--暂时不会用到
            //case 6:
            // break;
            //加库存并锁定库存
            case 7:
                stockBatch.setInventoryCount(stockBatchCode.getInventoryNum() + stockBatchVoRequest.getChangeNum());
                stockBatch.setLockCount(stockBatchCode.getLockNum() + stockBatchVoRequest.getChangeNum());
                break;
            default:
                return null;
        }
        //库存不管是锁定数还是可以数还是库存数都不能为负
        if (stockBatchCode.getLockNum() < 0 || stockBatchCode.getInventoryNum() < 0 || stockBatchCode.getAvailableNum() < 0) {
            return null;
        }
        return stockBatch;
    }


    /**
     * 功能描述: 查询批次库存商品(采购退供使用)
     *
     * @param reqVO
     * @paramreqVO
     * @returnPageInfo
     * @date 2019/6/26 17:47
     */
    @Override
    public PageInfo<QueryStockBatchSkuRespVo> selectStockBatchSkuPage(QueryStockBatchSkuReqVo reqVO) {
        try {
            PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
            List<QueryStockBatchSkuRespVo> queryStockBatchSkuRespVos = stockDao.selectStockBatchSkuInfoByPage(reqVO);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (QueryStockBatchSkuRespVo queryStockBatchSkuRespVo : queryStockBatchSkuRespVos) {
                String format = sdf.format(sdf.parse(queryStockBatchSkuRespVo.getProductionDate()));
                queryStockBatchSkuRespVo.setProductionDate(format);
            }
            return new PageInfo<QueryStockBatchSkuRespVo>(queryStockBatchSkuRespVos);
        } catch (Exception ex) {
            log.error("查询批次库存商失败");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     * 库房管理新增调拨,移库,报废列表查询
     *
     * @param reqVO
     * @return
     */
    @Override
    public PageInfo<QueryStockSkuListRespVo> selectStockSkuList(QueryStockSkuListReqVo reqVO) {
        try {
            PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
            List<QueryStockSkuListRespVo> queryStockSkuListRespVos = stockDao.selectStockSkuList(reqVO);
            for (QueryStockSkuListRespVo queryStockSkuListRespVo : queryStockSkuListRespVos) {
                List<QueryStockSkuListRespVo> repsVos = stockDao.selectSkuCodeByQueryBatchCodeList(queryStockSkuListRespVo.getWarehouseCode(), queryStockSkuListRespVo.getSkuCode());
                ArrayList<StockSkuListItemRespVo> list = new ArrayList();
                for (QueryStockSkuListRespVo repsVo : repsVos) {
                    StockSkuListItemRespVo respVo = new StockSkuListItemRespVo();
                    respVo.setBatchCode(repsVo.getBatchCode());
                    respVo.setProductionDate(repsVo.getProductionDate());
                    respVo.setBatchRemark(repsVo.getBatchRemark());
                    respVo.setAvailableNum(repsVo.getAvailableNum());
                    list.add(respVo);
                    queryStockSkuListRespVo.setItemRespVos(list);
                }
            }
            return new PageInfo<QueryStockSkuListRespVo>(queryStockSkuListRespVos);
        } catch (Exception ex) {
            log.error("库房管理新增调拨,移库,报废列表查询");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     * 库房库存数据保存
     *
     * @return
     */
    @Override
    public HttpResponse updateStorehouseById(List<StockRespVO> stockRespVO) {
        stockDao.updateStorehouseById(stockRespVO);
        return HttpResponse.success();
    }


    /**
     * 退供锁定批次库存
     *
     * @param reqVO
     * @return
     */
    @Override
    public Boolean returnSupplyLockStockBatch(ILockStockBatchReqVO reqVO) {
        try {
            //生成库存数据
            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            //操作类型
            Integer integer1 = 2;
            stockChangeRequest.setOperationType(integer1);
            //sku信息
            List<StockBatchVoRequest> convert = new ReturnSupplyToStockBatchConverter().convert(reqVO);
            stockChangeRequest.setStockBatchVoRequest(convert);
            //采购编码
            stockChangeRequest.setOrderCode(reqVO.getSourceOderCode());
//            HttpResponse httpResponse = changeStockBatch(stockChangeRequest);
//            if (MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
//                return true;
//            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    //@Save
    public HttpResponse changeStockBatch(ChangeStockRequest request) {
        LOGGER.info("对批次库存开始操作, 入参为：" + JsonUtil.toJson(request));
        if (CollectionUtils.isEmpty(request.getStockBatchList())) {
            return HttpResponse.failure(ResultCode.STOCK_BATCH_INFO_NULL);
        }

        //查询需要做修改的库存数据
        List<StockBatch> stockBatches = stockBatchDao.stockBatchAndSku(request.getStockBatchList());
        Map<String, StockBatch> stockBatchMap = new HashMap<>();
        stockBatches.forEach(stockBatch -> {
            stockBatchMap.put(stockBatch.getSkuCode() + stockBatch.getWarehouseCode(), stockBatch);
        });

        StockBatch stockBatch = null;
        List<StockBatch> updates = new ArrayList<>();
        List<StockBatch> adds = new ArrayList<>();
        Boolean flage = false;

        //将需要修改的库存进行逻辑计算
        List<StockBatchFlow> flows = new ArrayList<>();
        for (StockBatchInfoRequest stockBatchInfo: request.getStockBatchList()) {

            // 给条批次加锁
            long time = System.currentTimeMillis() + 30;
            if (!redisLockService.lock(stockBatchInfo.getBatchCode(), String.valueOf(time))) {
                LOGGER.info("redis给sku加锁失败：" + stockBatchInfo.getBatchCode());
                throw new BizException("redis给sku加锁失败：" + stockBatchInfo.getBatchCode());
            }
            // 添加批次库存流水
            StockBatchFlow stockBatchFlow = new StockBatchFlow();
            stockBatchFlow.setFlowBatchCode("FL" + IdSequenceUtils.getInstance().nextId());
            stockBatchFlow.setOperationType(request.getOperationType());
            stockBatchFlow.setSkuCode(stockBatchInfo.getSkuCode());
            stockBatchFlow.setSkuName(stockBatchInfo.getSkuName());
            stockBatchFlow.setDocumentType(stockBatchInfo.getDocumentType());
            stockBatchFlow.setDocumentCode(stockBatchInfo.getDocumentCode());
            stockBatchFlow.setSourceDocumentType(stockBatchInfo.getSourceDocumentType());
            stockBatchFlow.setSourceDocumentCode(stockBatchInfo.getSourceDocumentCode());
            stockBatchFlow.setChangeCount(stockBatchInfo.getChangeCount());
            stockBatchFlow.setRemark(stockBatchInfo.getBatchRemark());
            stockBatchFlow.setCreateById(stockBatchInfo.getOperatorId());
            stockBatchFlow.setCreateByName(stockBatchInfo.getOperatorName());
            stockBatchFlow.setUpdateById(stockBatchInfo.getOperatorId());
            stockBatchFlow.setUpdateByName(stockBatchInfo.getOperatorName());

            if (stockBatchMap.containsKey(stockBatchInfo.getSkuCode() + stockBatchInfo.getWarehouseCode())) {
                stockBatch = stockBatchMap.get(stockBatchInfo.getSkuCode() + stockBatchInfo.getWarehouseCode());

                //设置库存流水变化前值
                stockBatchFlow.setStockBatchCode(stockBatch.getStockBatchCode());
                stockBatchFlow.setBatchCode(stockBatch.getBatchCode());
                stockBatchFlow.setBeforeInventoryCount(stockBatch.getInventoryCount());
                stockBatchFlow.setBeforeAvailableCount(stockBatch.getAvailableCount());
                stockBatchFlow.setBeforeLockCount(stockBatch.getLockCount());
                stockBatch = stockBatchRequestToStockBatch(stockBatch, stockBatchInfo, request.getOperationType());
                if (null != stockBatch) {
                    updates.add(stockBatch);
                } else {
                    flage = true;
                    break;
                }

                // 设置库存流水修改后的值
                stockBatchFlow.setAfterInventoryCount(stockBatch.getInventoryCount());
                stockBatchFlow.setAfterAvailableCount(stockBatch.getAvailableCount());
                stockBatchFlow.setAfterLockCount(stockBatch.getLockCount());
                flows.add(stockBatchFlow);
            } else {
                // 设置库存流水修改前的值
                stockBatchFlow.setBeforeInventoryCount(0L);
                stockBatchFlow.setBeforeAvailableCount(0L);
                stockBatchFlow.setBeforeLockCount(0L);
                stockBatch = stockBatchRequestToStockBatch(stockBatch, stockBatchInfo, request.getOperationType());
                if (stockBatch != null) {
                    adds.add(stockBatch);
                } else {
                    flage = true;
                    break;
                }

                ProductSkuInfo productSkuInfo = productSkuDao.getSkuInfo(stockBatch.getSkuCode());
                if (productSkuInfo == null) {
                    flage = true;
                    break;
                }

                //设置库存流水修改后的值
                stockBatchFlow.setBatchCode(stockBatch.getBatchCode());
                stockBatchFlow.setStockBatchCode(stockBatch.getStockBatchCode());
                stockBatchFlow.setAfterInventoryCount(stockBatch.getInventoryCount());
                stockBatchFlow.setAfterAvailableCount(stockBatch.getAvailableCount());
                stockBatchFlow.setAfterLockCount(stockBatch.getLockCount());
                flows.add(stockBatchFlow);
            }

            // 给批次解锁 - redis
            redisLockService.unlock(stockBatchInfo.getBatchCode(), String.valueOf(time));
        }
        if (flage) {
            return HttpResponse.failure(ResultCode.STOCK_CHANGE_ERROR);
        }
        if (CollectionUtils.isNotEmpty(flows)) {
            stockBatchFlowDao.insertAll(flows);
        }
        if (CollectionUtils.isNotEmpty(updates)) {
            stockBatchDao.updateBatchAll(updates);
        }
        if (CollectionUtils.isNotEmpty(adds)) {
            stockBatchDao.insertAll(adds);
        }
        LOGGER.info("对批次库存操作结束");
        return HttpResponse.success();
    }

    /** 参数转换成库存数据*/
    private StockBatch stockBatchRequestToStockBatch(StockBatch stockBatch, StockBatchInfoRequest request, Integer operationType) {
        if (null == stockBatch.getId()) {
            BeanCopyUtils.copy(request, stockBatch);
            stockBatch.setStockBatchCode("SB" + IdSequenceUtils.getInstance().nextId());
            stockBatch.setLockCount(0L);
            stockBatch.setInventoryCount(0L);
            stockBatch.setAvailableCount(0L);
        }
        stockBatch.setTaxRate(request.getTaxRate());
        Long inventoryCount = stockBatch.getInventoryCount() == null ? 0L : stockBatch.getInventoryCount();
        Long availableCount = stockBatch.getAvailableCount() == null ? 0L : stockBatch.getAvailableCount();
        Long lockCount = stockBatch.getLockCount() == null ? 0L : stockBatch.getLockCount();
        // 变更库存数
        Long changeCount = request.getChangeCount() == null ? 0L : request.getChangeCount();
        // 操作类型 1.锁定库存 2.减库存并解锁 3.解锁库存. 4.减库存 5.加并锁定库存 6.加库存 7.加在途 8.减在途 9.锁转移(锁定库存移入/移出)
        switch (operationType) {
            //锁定库存 - 库存不变，锁定库存增加，可用库存减少
            case 1:
                // 验证批次可用库存在操作前后都不能为负。实际操作为：加锁定库存、减可用库存。
                if(availableCount < changeCount){
                    LOGGER.error("锁定批次库存: 可用库存在操作前后都不能为负，sku:" + request.getSkuCode());
                    throw new BizException("锁定批次库存: 可用库存在操作前后都不能为负，sku:" + request.getSkuCode());
                }
                stockBatch.setLockCount(stockBatch.getLockCount() + changeCount);
                stockBatch.setAvailableCount(stockBatch.getAvailableCount() - changeCount);
                break;

            // 减库存并解锁 - 库存减少，锁定库存减少，可用库存不变
            case 2:
                // 验证批次锁定库存在操作前后都不能为负。实际操作为：减总库存、减锁定库存。
                if(lockCount < changeCount || inventoryCount < changeCount){
                    LOGGER.error("批次减库存并解锁: 锁定库存、总库存在操作前后都不能为负,sku:" + request.getSkuCode());
                    throw new BizException("批次减库存并解锁: 锁定库存、总库存在操作前后都不能为负，sku:" + request.getSkuCode());
                }
                stockBatch.setInventoryCount(inventoryCount - changeCount);
                stockBatch.setLockCount(lockCount - changeCount);
                break;

            // 解锁库存 - 库存不变，锁定库存减少，可用库存增加
            case 3:
                // 验证批次锁定库存在操作前后都不能为负。实际操作为：减锁定库存、加可用库存。
                if(lockCount < changeCount){
                    LOGGER.error("批次解锁库存: 锁定库存在操作前后都不能为负,sku:" + request.getSkuCode());
                    throw new BizException("批次解锁库存: 锁定库存在操作前后都不能为负，sku:" + request.getSkuCode());
                }
                stockBatch.setAvailableCount(availableCount + changeCount);
                stockBatch.setLockCount(lockCount - changeCount);
                break;

            // 减库存 - 库存减少，锁定库存不变，可用库存减少
            case 4:
                // 验证总库存、可用库存在操作前后都不能为负。实际操作为：减总库存、减可用库存。
                if(availableCount < changeCount || inventoryCount < changeCount ){
                    LOGGER.error("批次减库存: 可用库存、总库存在操作前后都不能为负,sku:" + request.getSkuCode());
                    throw new BizException("批次减库存:可用库存、总库存在操作前后都不能为负，sku:" + request.getSkuCode());
                }
                stockBatch.setAvailableCount(availableCount - changeCount);
                stockBatch.setInventoryCount(inventoryCount - changeCount);
                break;

            // 加并锁定库存 - 库存增加，锁定库存增加，可用库存不变
            case 5:
                // 不验证任何库存。实际操作为：加总库存、加锁定库存。
                stockBatch.setInventoryCount(inventoryCount + changeCount);
                stockBatch.setLockCount(lockCount + changeCount);
                break;

            // 加库存 - 库存增加，锁定库存不变，可用库存增加
            case 6:
                // 不验证任何库存。实际操作为：加总库存、加可用库存
                stockBatch.setInventoryCount(inventoryCount + changeCount);
                stockBatch.setAvailableCount(availableCount + changeCount);
                break;

            // 锁转移(锁定库存移入/移出)
            case 9:
                // 不验证任何库存。实际操作为：库存不变动，库存日志新增了减锁定和加锁定
                break;
            default:
                return null;
        }
        return stockBatch;
    }

    /**
     * 库房管理新增调拨,移库,报废列表查询导入操作
     *
     * @return
     */
    public PageInfo<QueryStockSkuListRespVo> importStockSkuList(QueryImportStockSkuListReqVo reqVO) {
        try {
            /*List<String> skuCodes = stockDao.importStockSkuList();
            ArrayList<QueryStockSkuListRespVo> list = new ArrayList();
            for (String skuCode: skuCodes) {
                QueryStockSkuListRespVo queryStockSkuListRespVo = new QueryStockSkuListRespVo();
                    queryStockSkuListRespVo.setSkuCode(skuCode);
                    Long availableNum = stockDao.selectSkuCodeByQueryAvailableNum(skuCode);
                    List<String> batchCodeLists = stockDao.selectSkuCodeByQueryBatchCode(skuCode);
                    List<String> productionDateList = stockDao.selectSkuCodeByQueryProductionDateList(skuCode);
                    queryStockSkuListRespVo.setAvailableNum(availableNum);
                    queryStockSkuListRespVo.setBatchCodeList(batchCodeLists);
                    queryStockSkuListRespVo.setProductionDateList(productionDateList);
                list.add(queryStockSkuListRespVo);
            }*/

            PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
            return new PageInfo<QueryStockSkuListRespVo>(stockDao.queryStockBatch(reqVO));
        } catch (Exception ex) {
            log.error("库房管理新增调拨,移库,报废列表查询导入操作");
            ex.printStackTrace();
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    @Override
    public BasePage<QuerySkuInfoRespVO> querySkuBatchList(QuerySkuInfoReqVO reqVO) {
        return PageUtil.getPageList(reqVO.getPageNo(), stockDao.getSkuBatchForChangePrice(reqVO));
    }

    @Override
    public List<SkuBatchRespVO> querySkuBatchList(SkuBatchReqVO reqVO) {
        return stockDao.queryStockBatchForAllo(reqVO);
    }

    @Override
    public List<Stock> selectSkuCost() {
        return stockDao.selectSkuCost();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse lockErpStock(MerchantLockStockReqVo vo) {
        if (vo == null) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        StockChangeRequest lock = new StockChangeRequest();
        lock.setOperationType(1);
        lock.setOrderCode(vo.getOrderCode());
        List<StockVoRequest> stockVoRequests = Lists.newArrayList();
        lock.setStockVoRequests(stockVoRequests);
        // 运营中台需要锁库存的sku集合
        List<MerchantLockStockItemReqVo> skuList = vo.getSkuList();
        if (CollectionUtils.isNotEmpty(skuList)) {
            StockVoRequest stockTemp;
            StockVoRequest stockTemp1;
            for (MerchantLockStockItemReqVo sku : skuList) {
                List<StockTransportResponse> centerList = sku.getTransportCenterList();
                if (CollectionUtils.isEmpty(centerList)) {
                    LOGGER.info("此sku没有查询到对应的仓库:" + sku.getSkuCode());
                    return HttpResponse.failure(ResultCode.ERP_NO_TRANSPORT);
                }
                // 计算主备仓的库存总数
                Long stockCount = centerList.get(0).getStockCount() + centerList.get(1).getStockCount();
                if (sku.getNum() > stockCount) {
                    LOGGER.info("无法锁库存 sku锁库存的数量大于实际主备仓的库存总和:" + sku.getSkuCode());
                    return HttpResponse.failure(ResultCode.STOCK_LOCK_ERROR);
                }
                // 把仓库按类型排序
                centerList = centerList.stream().sorted(Comparator.comparing(StockTransportResponse::getTransportCenterType))
                        .collect(Collectors.toList());
                // 判断是否分仓锁库
                stockTemp = new StockVoRequest();
                stockTemp.setWarehouseCode(centerList.get(0).getWarehouseCode());
                stockTemp.setWarehouseName(centerList.get(0).getWarehouseName());
                stockTemp.setTransportCenterCode(centerList.get(0).getTransportCenterCode());
                stockTemp.setTransportCenterName(centerList.get(0).getTransportCenterName());
                stockTemp.setSkuCode(sku.getSkuCode());
                stockTemp.setSkuName(sku.getSkuName());
                stockTemp.setCompanyCode(vo.getCompanyCode());
                stockTemp.setCompanyName(vo.getCompanyCode());
                stockTemp.setDocumentType(0);
                stockTemp.setDocumentNum(vo.getOrderCode());
                if (centerList.get(0).getStockCount() >= sku.getNum()) {
                    // 主仓库存大于等于锁库数量，不需要分库锁
                    stockTemp.setChangeNum(sku.getNum());
                    stockVoRequests.add(stockTemp);
                } else {
                    // 优先锁主库库存，在锁备库库存
                    stockTemp.setChangeNum(centerList.get(0).getStockCount());
                    stockVoRequests.add(stockTemp);
                    // 备库
                    stockTemp1 = new StockVoRequest();
                    stockTemp1.setWarehouseCode(centerList.get(1).getWarehouseCode());
                    stockTemp1.setWarehouseName(centerList.get(1).getWarehouseName());
                    stockTemp1.setTransportCenterCode(centerList.get(1).getTransportCenterCode());
                    stockTemp1.setTransportCenterName(centerList.get(1).getTransportCenterName());
                    stockTemp1.setSkuCode(sku.getSkuCode());
                    stockTemp1.setSkuName(sku.getSkuName());
                    stockTemp1.setCompanyCode(vo.getCompanyCode());
                    stockTemp1.setCompanyName(vo.getCompanyCode());
                    stockTemp1.setChangeNum(sku.getNum() - centerList.get(0).getStockCount());
                    stockTemp1.setDocumentType(0);
                    stockTemp1.setDocumentNum(vo.getOrderCode());
                    stockVoRequests.add(stockTemp1);
                }
            }
        }
        //调用库存接口锁库
        HttpResponse httpResponse = changeStock(lock);
        if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
            return HttpResponse.failure(ResultCode.ERP_LOCK_FAIL);
        }
        return HttpResponse.success();
    }

    @Override
    public String byCityCodeAndprovinceCode(String provinceCode, String cityCode, String tagCode, String exitStock, String orderByType) {
        List<String> stockBatchRespVOList = stockDao.byCityCodeAndprovinceCode(provinceCode, cityCode, tagCode, exitStock, orderByType, getUser().getCompanyCode());
        StringBuffer stringBuffer = new StringBuffer();
        for (int num = 0; num < stockBatchRespVOList.size(); num++) {
            if (num < stockBatchRespVOList.size() - 1) {
                stringBuffer.append(stockBatchRespVOList.get(num)).append(",");
            } else {
                stringBuffer.append(stockBatchRespVOList.get(num));
            }
        }
        String str=  stringBuffer.toString();
        return str;
    }

    @Override
    public List<StockBatchRespVO> byCityAndProvinceAndskuCode(String skuCode, String provinceCode, String cityCode) {
        List<StockBatchRespVO> stockBatchRespVOList = Lists.newArrayList();
        stockBatchRespVOList.add(stockDao.byCityAndProvinceAndskuCode(skuCode, provinceCode, cityCode));
        //得出备用仓库
        SkuConfigsRepsVo respVO = stockDao.findSpareWarehouse(stockBatchRespVOList.get(0));
        List<SpareWarehouseRepsVo> spareWarehouses = respVO.getSpareWarehouses();
        //对仓库使用顺序进行排序
        spareWarehouses.stream().sorted(Comparator.comparing(SpareWarehouseRepsVo::getUseOrder)).collect(Collectors.toList());

        for (SpareWarehouseRepsVo spareWarehouseRepsVo :
                spareWarehouses) {
            StockBatchRespVO stockBatchRespVO = stockDao.byCityAndProvinceAndtransportCenterCode(spareWarehouseRepsVo.getTransportCenterCode(), provinceCode, cityCode);
            if (stockBatchRespVO.getAvailableNum() > 0) {
                stockBatchRespVOList.add(stockBatchRespVO);
                break;
            }
        }
        return stockBatchRespVOList;
    }
}
