package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
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
import com.aiqin.bms.scmp.api.product.domain.request.stock.ChangeStockRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockBatchInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockInfoRequest;
import com.aiqin.bms.scmp.api.product.domain.response.*;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.SkuBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SpareWarehouseRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockFlowRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockRespVO;
import com.aiqin.bms.scmp.api.product.domain.trans.ILockStockReqVoToQueryStockSkuReqVo;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuStockInfoMapper;
import com.aiqin.bms.scmp.api.product.service.*;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.LockOrderItemBatchReqVO;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
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
    @Autowired
    private ProductSkuStockInfoMapper productSkuStockInfoDao;
    @Autowired
    private WarehouseDao warehouseDao;

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

            // 查询入库单批次商品信息
//            Map<String, WarehouseDTO> batchMangeMap = new HashMap<>();
//            for(StockInfoRequest stockInfoRequest : request.getStockList()) {
//                String key = String.format("%s", stockInfoRequest.getWarehouseCode());
//                if (batchMangeMap.get(key) == null) {
//                    batchMangeMap.put(key, warehouseDao.getWarehouseByCode(stockInfoRequest.getWarehouseCode()));
//                }
//            }

            // 将需要修改的库存进行逻辑计算
            List<StockFlow> stockFlows = new ArrayList<>();
            for (StockInfoRequest stockInfoRequest : request.getStockList()) {
//                String batchMange = String.format("%s", stockInfoRequest.getWarehouseCode());
//                WarehouseDTO warehouseDTO = batchMangeMap.get(batchMange);
//                if(warehouseDTO != null){
//                    stockInfoRequest.setBatchManage(warehouseDTO.getBatchManage());
//                }
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
                stockFlow.setUseStatus(0);

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
                    ProductSkuStockInfo skuStockInfo = productSkuStockInfoDao.getBySkuCode(stock.getSkuCode());
                    if (skuStockInfo == null) {
                        flage = true;
                        break;
                    }

                    stock.setUnitCode(skuStockInfo.getUnitCode());
                    stock.setUnitName(skuStockInfo.getUnitName());
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
            LOGGER.info("库存操作结束");
            // 调用批次库存操作
            if(!request.getOperationType().equals(7)){
                this.changeStockBatch(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("操作库存失败", e.getMessage());
            throw new BizException("操作库存失败");
        }
        return HttpResponse.success();
    }

    /** 参数转换成库存数据*/
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

            // 出库减并解锁库存逻辑
            case 10:
                Long preLockCount = request.getPreLockCount() == null ? 0L : request.getPreLockCount();
                if(lockCount < preLockCount || inventoryCount < changeCount){
                    LOGGER.error("wms回传出库减并解锁库存: 锁定库存、总库存在操作前后都不能为负,sku:" + request.getSkuCode());
                    throw new BizException("wms回传出库减并解锁库存: 锁定库存、总库存在操作前后都不能为负，sku:" + request.getSkuCode());
                }
                stock.setInventoryCount(inventoryCount - changeCount);
                stock.setLockCount(lockCount - preLockCount);
                if(changeCount > preLockCount){
                    if(availableCount < (changeCount - preLockCount)){
                        LOGGER.error("wms回传出库减并解锁库存: 可用库存在操作前后不能为负,sku:" + request.getSkuCode());
                        throw new BizException("wms回传出库减并解锁库存: 可用库存在操作前后不能为负，sku:" + request.getSkuCode());
                    }
                    stock.setAvailableCount(availableCount - (changeCount - preLockCount));
                }else {
                    stock.setAvailableCount(availableCount + (changeCount - preLockCount));
                }
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
            copy.setBatchCode(System.currentTimeMillis() + i + "");
            copy.setProductDate(date);
            copy.setLineCode((long) i);
            list.add(copy);
        }
        return list;
    }

    /**
     * 库房管理新增调拨,移库,报废列表查询
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
     */
    @Override
    public HttpResponse updateStorehouseById(List<StockRespVO> stockRespVO) {
        stockDao.updateStorehouseById(stockRespVO);
        return HttpResponse.success();
    }

    /**
     * 退供锁定批次库存
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
        stockBatches.forEach(s -> {
            if(StringUtils.isNotBlank(s.getBatchInfoCode())){
                stockBatchMap.put(s.getBatchInfoCode(), s);
            }else {
                stockBatchMap.put(s.getSkuCode() + "_" + s.getWarehouseCode() + "_" +
                        s.getBatchCode() + "_" + s.getSupplierCode() + "_"
                        + s.getTaxCost().stripTrailingZeros().toPlainString(), s);
            }
        });

        StockBatch stockBatch;
        List<StockBatch> updates = new ArrayList<>();
        List<StockBatch> adds = new ArrayList<>();
        Boolean flage = false;

        //将需要修改的库存进行逻辑计算
        List<StockBatchFlow> flows = new ArrayList<>();
        for (StockBatchInfoRequest stockBatchInfo: request.getStockBatchList()) {

            String taxCost = stockBatchInfo.getTaxCost().stripTrailingZeros().toPlainString();
            String redisKey = stockBatchInfo.getSkuCode() + "_" + stockBatchInfo.getWarehouseCode() + "_" +
                        stockBatchInfo.getBatchCode() + "_" + stockBatchInfo.getSupplierCode() + "_" + taxCost;
            // 给条批次加锁
            long time = System.currentTimeMillis() + 30;
            if (!redisLockService.lock(redisKey, String.valueOf(time))) {
                LOGGER.info("爱亲- redis给sku加锁失败：" + redisKey);
                throw new BizException("爱亲- redis给sku加锁失败：" + redisKey);
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

            String key = stockBatchInfo.getSkuCode() + "_" + stockBatchInfo.getWarehouseCode() + "_" +
                    stockBatchInfo.getBatchCode() + "_" + stockBatchInfo.getSupplierCode() + "_" + stockBatchInfo.getTaxCost();
            if (stockBatchMap.containsKey(key)) {
                stockBatch = stockBatchMap.get(key);

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
                stockBatch = new StockBatch();
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

                //设置库存流水修改后的值
                String cost = stockBatch.getTaxCost().stripTrailingZeros().toPlainString();
                String batchInfoCode = stockBatch.getSkuCode() + "_" + stockBatch.getWarehouseCode() + "_" +
                        stockBatch.getBatchCode() + "_" + stockBatch.getSupplierCode() + "_" + cost;
                stockBatch.setBatchInfoCode(batchInfoCode);
                stockBatchFlow.setBatchCode(stockBatch.getBatchCode());
                stockBatchFlow.setStockBatchCode(stockBatch.getStockBatchCode());
                stockBatchFlow.setAfterInventoryCount(stockBatch.getInventoryCount());
                stockBatchFlow.setAfterAvailableCount(stockBatch.getAvailableCount());
                stockBatchFlow.setAfterLockCount(stockBatch.getLockCount());
                flows.add(stockBatchFlow);
            }

            // 给批次解锁 - redis
            redisLockService.unlock(redisKey, String.valueOf(time));
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
    private StockBatch stockBatchRequestToStockBatch(StockBatch stockBatch, StockBatchInfoRequest stockBatchInfo, Integer operationType) {
        if (null == stockBatch.getId()) {
            BeanCopyUtils.copy(stockBatchInfo, stockBatch);
            stockBatch.setStockBatchCode("SB" + IdSequenceUtils.getInstance().nextId());
            stockBatch.setLockCount(0L);
            stockBatch.setInventoryCount(0L);
            stockBatch.setAvailableCount(0L);
        }
        stockBatch.setCreateById(stockBatchInfo.getOperatorId());
        stockBatch.setCreateByName(stockBatchInfo.getOperatorName());
        stockBatch.setUpdateByName(stockBatchInfo.getOperatorName());
        stockBatch.setUpdateById(stockBatchInfo.getOperatorId());
        Long inventoryCount = stockBatch.getInventoryCount() == null ? 0L : stockBatch.getInventoryCount();
        Long availableCount = stockBatch.getAvailableCount() == null ? 0L : stockBatch.getAvailableCount();
        Long lockCount = stockBatch.getLockCount() == null ? 0L : stockBatch.getLockCount();
        // 变更库存数
        Long changeCount = stockBatchInfo.getChangeCount() == null ? 0L : stockBatchInfo.getChangeCount();
        // 操作类型 1.锁定库存 2.减库存并解锁 3.解锁库存. 4.减库存 5.加并锁定库存 6.加库存 7.加在途 8.减在途 9.锁转移(锁定库存移入/移出)
        switch (operationType) {
            //锁定库存 - 库存不变，锁定库存增加，可用库存减少
            case 1:
                // 验证批次可用库存在操作前后都不能为负。实际操作为：加锁定库存、减可用库存。
                if(availableCount < changeCount){
                    LOGGER.error("锁定批次库存: 可用库存在操作前后都不能为负，sku:" + stockBatchInfo.getSkuCode());
                    throw new BizException("锁定批次库存: 可用库存在操作前后都不能为负，sku:" + stockBatchInfo.getSkuCode());
                }
                stockBatch.setLockCount(stockBatch.getLockCount() + changeCount);
                stockBatch.setAvailableCount(stockBatch.getAvailableCount() - changeCount);
                break;

            // 减库存并解锁 - 库存减少，锁定库存减少，可用库存不变
            case 2:
                // 验证批次锁定库存在操作前后都不能为负。实际操作为：减总库存、减锁定库存。
                if(lockCount < changeCount || inventoryCount < changeCount){
                    LOGGER.error("批次减库存并解锁: 锁定库存、总库存在操作前后都不能为负,sku:" + stockBatchInfo.getSkuCode());
                    throw new BizException("批次减库存并解锁: 锁定库存、总库存在操作前后都不能为负，sku:" + stockBatchInfo.getSkuCode());
                }
                stockBatch.setInventoryCount(inventoryCount - changeCount);
                stockBatch.setLockCount(lockCount - changeCount);
                break;

            // 解锁库存 - 库存不变，锁定库存减少，可用库存增加
            case 3:
                // 验证批次锁定库存在操作前后都不能为负。实际操作为：减锁定库存、加可用库存。
                if(lockCount < changeCount){
                    LOGGER.error("批次解锁库存: 锁定库存在操作前后都不能为负,sku:" + stockBatchInfo.getSkuCode());
                    throw new BizException("批次解锁库存: 锁定库存在操作前后都不能为负，sku:" + stockBatchInfo.getSkuCode());
                }
                stockBatch.setAvailableCount(availableCount + changeCount);
                stockBatch.setLockCount(lockCount - changeCount);
                break;

            // 减库存 - 库存减少，锁定库存不变，可用库存减少
            case 4:
                // 验证总库存、可用库存在操作前后都不能为负。实际操作为：减总库存、减可用库存。
                if(availableCount < changeCount || inventoryCount < changeCount ){
                    LOGGER.error("批次减库存: 可用库存、总库存在操作前后都不能为负,sku:" + stockBatchInfo.getSkuCode());
                    throw new BizException("批次减库存:可用库存、总库存在操作前后都不能为负，sku:" + stockBatchInfo.getSkuCode());
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

            // 出库减并解锁库存逻辑
            case 10:
                Long preLockCount = stockBatchInfo.getPreLockCount() == null ? 0L : stockBatchInfo.getPreLockCount();
                if(lockCount < preLockCount || inventoryCount < changeCount){
                    LOGGER.error("wms回传出库减并解锁批次库存: 锁定库存、总库存在操作前后都不能为负,sku:" + stockBatchInfo.getSkuCode());
                    throw new BizException("wms回传出库减并批次库存: 锁定库存、总库存在操作前后都不能为负，sku:" + stockBatchInfo.getSkuCode());
                }
                stockBatch.setInventoryCount(inventoryCount - changeCount);
                stockBatch.setLockCount(lockCount - preLockCount);
                if(changeCount > preLockCount){
                    if(availableCount < (changeCount - preLockCount)){
                        LOGGER.error("wms回传出库减并解锁批次库存: 可用库存在操作前后不能为负,sku:" + stockBatchInfo.getSkuCode());
                        throw new BizException("wms回传出库减并解锁批次库存: 可用库存在操作前后不能为负，sku:" + stockBatchInfo.getSkuCode());
                    }
                    stockBatch.setAvailableCount(availableCount - (changeCount - preLockCount));
                }else {
                    stockBatch.setAvailableCount(availableCount + (changeCount - preLockCount));
                }
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
