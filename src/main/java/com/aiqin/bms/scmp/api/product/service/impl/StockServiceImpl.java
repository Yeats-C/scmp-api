package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.StockStatusEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.dao.StockDao;
import com.aiqin.bms.scmp.api.product.dao.StockFlowDao;
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
import com.aiqin.bms.scmp.api.product.domain.request.merchant.QueryMerchantStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.*;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.SkuBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.changeprice.QuerySkuInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.merchant.MerchantLockStockRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.merchant.QueryMerchantStockRepVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchProductSkuRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockBatchRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockFlowRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.stock.StockRespVO;
import com.aiqin.bms.scmp.api.product.domain.trans.ILockStockReqVoToQueryStockSkuReqVo;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.bms.scmp.api.product.service.OutboundService;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.LockOrderItemBatchReqVO;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.WarehouseListReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter.LogisticsCenterApiResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseApiResVo;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
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
public class StockServiceImpl implements StockService {


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
    private EncodingRuleDao encodingRuleDao;
    @Autowired
    private StockFlowDao stockFlowDao;
    @Autowired
    private ProductSkuDao productSkuDao;
    @Autowired
    private WarehouseService supplierApiService;
    @Autowired
    private PurchaseGroupService purchaseGroupService;


    /**
     * 功能描述: 查询库存商品(采购退供使用)
     *
     * @param reqVO
     * @paramreqVO
     * @returnPageInfo
     * @auther knight.xie
     * @date 2019/1/4 17:23
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
     *
     * @param reqVO
     * @paramreqVO
     * @returnPageInfo
     * @auther knight.xie
     * @date 2019/1/4 17:23
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
            List<StockRespVO> totals = stockDao.countStockSumInfoByPage(stockRequest);
            HashMap<String, StockRespVO> stockRespMap = new HashMap<>();
            List<StockRespVO> lists = new ArrayList<>();
            Stock stock = null;
            int i = 0;
            for (StockRespVO stockRespVO : stockList) {
                String str = stockRespVO.getSkuCode();
                if(stockRespMap.get(str) == null){
                    stockRespMap.put(str,stockRespVO);
                    stockCommon(stockRespMap, stockRespVO, str, stock, i);
                }else {
                    stockCommon(stockRespMap, stockRespVO, str, stock, i);
                }
            }
            for(Map.Entry<String, StockRespVO> entry : stockRespMap.entrySet()){
                lists.add(entry.getValue());
            }

            // 遍历获取总total
            HashMap<String, StockRespVO> totalMap = new HashMap<>();
            List<StockRespVO> totalLists = new ArrayList<>();
            for (StockRespVO total : totals) {
                String str = total.getSkuCode();
                if(totalMap.get(str) == null){
                    totalMap.put(str,total);
                    stockCommon(stockRespMap, total, str, stock, i);
                }else {
                    stockCommon(stockRespMap, total, str, stock, i);
                }
            }
            for(Map.Entry<String, StockRespVO> entry : stockRespMap.entrySet()){
                totalLists.add(entry.getValue());
            }

            pageResData.setTotalCount(totalLists.size());
            pageResData.setDataList(lists);
            return pageResData;
        } catch (Exception e) {
            LOGGER.error("中央列表查询失败", e);
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    private void stockCommon(HashMap<String, StockRespVO> stockRespMap, StockRespVO stockRespVO, String str, Stock stock, int i) {
        StockRespVO key = stockRespMap.get(str);
        if(StringUtils.isNotBlank(stockRespVO.getCompanyCode()) || StringUtils.isNotBlank(stockRespVO.getSkuCode())){
            stock = new Stock();
            Stock stockInfo;
            stock.setSkuCode(stockRespVO.getSkuCode());
            stock.setCompanyCode(stockRespVO.getCompanyCode());
            if(i == 1){
                stock.setTransportCenterCode(stockRespVO.getTransportCenterCode());
            }
            stock.setWarehouseType(Global.SALE_TYPE);
            stockInfo = stockDao.selectStockSum(stock);
            if(stockInfo != null){
                key.setSaleNum(stockInfo.getAvailableNum());
                key.setSaleLockNum(stockInfo.getLockNum());
                key.setSaleWayNum(stockInfo.getTotalWayNum());
                key.setPurchaseWayNum(stockInfo.getPurchaseWayNum());
            }
            stock.setWarehouseType(Global.GIFT_TYPE);
            stockInfo = stockDao.selectStockSum(stock);
            if (stockInfo != null){
                key.setGiftNum(stockInfo.getAvailableNum());
                key.setGiftLockNum(stockInfo.getLockNum());
                key.setGiftWayNum(stockInfo.getTotalWayNum());
                key.setGiftPurchaseWayNum(stockInfo.getPurchaseWayNum());
            }
            stock.setWarehouseType(Global.SPECIAL_TYPE);
            stockInfo = stockDao.selectStockSum(stock);
            if (stockInfo != null){
                key.setSpecialSaleNum(stockInfo.getAvailableNum());
                key.setSpecialSaleLockNum(stockInfo.getLockNum());
                key.setSpecialSaleWayNum(stockInfo.getTotalWayNum());
            }
            stock.setWarehouseType(Global.DEFECTIVE_TYPE);
            stockInfo = stockDao.selectStockSum(stock);
            if(stockInfo != null){
                key.setBadNum(stockInfo.getAvailableNum());
                key.setBadLockNum(stockInfo.getLockNum());
                key.setBadWayNum(stockInfo.getTotalWayNum());
            }
            stockRespMap.put(str,key);
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
            List<StockRespVO> totals = stockDao.countTransportStockInfoByPage(stockRequest);
            HashMap<String, StockRespVO> stockRespMap = new HashMap<>();
            List<StockRespVO> lists = new ArrayList<>();
            Stock stock = null;
            int i = 1;
            // 遍历获取分页数据
            for (StockRespVO stockRespVO : stockList) {
                String str = stockRespVO.getTransportCenterCode()+ stockRespVO.getSkuCode();
                if(stockRespMap.get(str) == null){
                    stockRespMap.put(str,stockRespVO);
                    stockCommon(stockRespMap, stockRespVO, str, stock, i);
                }else {
                    stockCommon(stockRespMap, stockRespVO, str, stock, i);
                }
            }
            for(Map.Entry<String, StockRespVO> entry : stockRespMap.entrySet()){
                lists.add(entry.getValue());
            }

            // 遍历获取总total
            HashMap<String, StockRespVO> totalMap = new HashMap<>();
            List<StockRespVO> totalLists = new ArrayList<>();
            for (StockRespVO total : totals) {
                String str = total.getTransportCenterCode()+ total.getSkuCode();
                if(totalMap.get(str) == null){
                    totalMap.put(str,total);
                    stockCommon(stockRespMap, total, str, stock, i);
                }else {
                    stockCommon(stockRespMap, total, str, stock, i);
                }
            }
            for(Map.Entry<String, StockRespVO> entry : stockRespMap.entrySet()){
                totalLists.add(entry.getValue());
            }
            pageResData.setTotalCount(totalLists.size());
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
            if (org.apache.commons.collections.CollectionUtils.isEmpty(groupVoList)) {
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
     *
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
            log.error("error", e);
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
            log.error("error", e);
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
            log.error("error", e);
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
            log.error("error", e);
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
            log.error("error", e);
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
            log.error("error", e);
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
            log.error("error", e);
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
     *
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
            log.error("error", e);
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

    /**
     * 根据公司编码和sku集合查询商品库存
     *
     * @param reqVo
     * @return
     */
    @Override
    public List<QueryMerchantStockRepVo> selectStockByCompanyCodeAndSkuList(QueryMerchantStockReqVo reqVo) {
        return stockDao.selectStockByCompanyCodeAndSkuList(reqVo);
    }

    /**
     * 门店库存锁定
     *
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MerchantLockStockRespVo> lockMerchantStock(MerchantLockStockReqVo vo) {
        //1.通过省市查询物流中心
        WarehouseListReqVo warehouseReq = new WarehouseListReqVo();
        warehouseReq.setProvinceCode(vo.getProvinceId());
        warehouseReq.setCityCode(vo.getCityId());
        warehouseReq.setWarehouseTypeCode(WarehouseTypeCode.LARGEPERIODANDSALES.getTypeCode());
        List<LogisticsCenterApiResVo> warehouseApi = supplierApiService.getWarehouseApi(warehouseReq);
        if (CollectionUtils.isEmpty(warehouseApi)) {
            throw new GroundRuntimeException("查询物流中心失败！");
        }
        List<String> centerCodes = Lists.newArrayList();
        List<String> warehouseCodes = Lists.newArrayList();
        List<WarehouseApiResVo> warehouseApiResVos = Lists.newArrayList();
        //拿到物流中心和库房
        warehouseApi.forEach(o -> {
            centerCodes.add(o.getLogisticsCenterCode());
            List<String> collect = o.getList().stream().map(WarehouseApiResVo::getWarehouseCode).collect(Collectors.toList());
            warehouseApiResVos.addAll(o.getList());
            warehouseCodes.addAll(collect);
        });
        if (CollectionUtils.isEmpty(warehouseCodes)) {
            throw new GroundRuntimeException("查询库房失败！");
        }
        Map<String, WarehouseApiResVo> collect = warehouseApiResVos.stream().collect(Collectors.toMap(WarehouseApiResVo::getWarehouseCode, Function.identity(), (k1, k2) -> k2));
        List<String> skuCodes = vo.getSkuList().stream().map(MerchantLockStockItemReqVo::getSkuCode).collect(Collectors.toList());
        //k: skuCode + 仓库类型 v: 总数
        Map<String, Long> vos = Maps.newHashMap();
        for (MerchantLockStockItemReqVo itemReqVo : vo.getSkuList()) {
            Long num = vos.get(itemReqVo.getSkuCode() + itemReqVo.getProductType());
            if (Objects.isNull(num)) {
                vos.put(itemReqVo.getSkuCode() + itemReqVo.getProductType(), itemReqVo.getNum());
            } else {
                vos.put(itemReqVo.getSkuCode() + itemReqVo.getProductType(), itemReqVo.getNum() + num);
            }
        }

        //2.通过物流中心找到对应仓库
        //3.根据仓库和skuList查询是否有库存
        List<Stock> stocks = selectByQueryList(centerCodes, warehouseCodes, skuCodes, vo.getCompanyCode());
        Map<String, Long> map1 = Maps.newHashMap();
        //k: skuCode + 仓库类型 v: 该sku下仓的集合
        Map<String, List<Stock>> map2 = Maps.newHashMap();
        for (Stock stock : stocks) {
            Long num = map1.get(stock.getSkuCode() + stock.getWarehouseType());
            Byte warehouseTypeCode = collect.get(stock.getWarehouseCode()).getWarehouseTypeCode();
            if (Objects.isNull(num)) {
                map1.put(stock.getSkuCode() + warehouseTypeCode, stock.getAvailableNum());
            } else {
                map1.put(stock.getSkuCode() + warehouseTypeCode, stock.getAvailableNum() + num);
            }
            List<Stock> stocks1 = map2.get(stock.getSkuCode() + warehouseTypeCode);
            if (CollectionUtils.isEmpty(stocks1)) {
                List<Stock> temp = Lists.newArrayList();
                temp.add(stock);
                map2.put(stock.getSkuCode() + warehouseTypeCode, temp);
            } else {
                stocks1.add(stock);
                map2.put(stock.getSkuCode() + warehouseTypeCode, stocks1);
            }
        }
        List<String> errorMsg = Lists.newArrayList();
        vos.forEach((k, v) -> {
            Long aLong = map1.get(k);
            if (Objects.isNull(aLong) || aLong < v) {
                errorMsg.add("sku:" + k.substring(0, k.length() - 1) + "数量不足，保存失败");
            }
        });
        if (CollectionUtils.isNotEmpty(errorMsg)) {
            throw new GroundRuntimeException(errorMsg.toString());
        }

        //4.验证,进行锁库操作
        List<MerchantLockStockRespVo> respVos = Lists.newArrayList();
        StockChangeRequest lock = new StockChangeRequest();
        lock.setOperationType(1);
//        lock.setOrderType();
        lock.setOrderCode(vo.getOrderCode());
        List<StockVoRequest> stockVoRequests = Lists.newArrayList();
        lock.setStockVoRequests(stockVoRequests);
        for (MerchantLockStockItemReqVo itemReqVo : vo.getSkuList()) {
            //TODO  若是相同的sku 应该从同一仓库出 数量不满足时，应该数量多的
            //记录满足条件的最小值索引
            int temp = 0;
            //该sku下满足条件的仓的集合
            List<Stock> stocks1 = map2.get(itemReqVo.getSkuCode() + itemReqVo.getProductType());
            //按照可用数量排序
            stocks1.sort(Comparator.comparing(Stock::getAvailableNum).reversed());
//            assert Optional.ofNullable(stocks1.get(0).getAvailableNum()).orElse(-1L)>Optional.ofNullable(stocks1.get(1).getAvailableNum()).orElse(0L);
            //拿到刚刚大于数量的索引
            for (int i = 0; i < stocks1.size(); i++) {
                if (stocks1.get(i).getAvailableNum() < itemReqVo.getNum()) {
                    temp = i - 1;
                    break;
                }
            }
            if (temp < 0) {
                //说明货不够，需要分仓发货
                int index = 0;
                while (itemReqVo.getNum() > 0) {
                    Stock stock = stocks1.get(index);
                    MerchantLockStockRespVo respVo = new MerchantLockStockRespVo();
                    //拼装门店返回信息
                    respVo.setTransportCenterCode(stock.getTransportCenterCode());
                    respVo.setTransportCenterName(stock.getTransportCenterName());
                    respVo.setWarehouseCode(stock.getWarehouseCode());
                    respVo.setWarehouseName(stock.getWarehouseName());
                    respVo.setProductType(itemReqVo.getProductType());
                    respVo.setSkuCode(itemReqVo.getSkuCode());
                    respVo.setLineNum(itemReqVo.getLineNum());
                    respVo.setLockNum(stock.getAvailableNum());
                    Long lockNum = 0L;
                    //扣减数量
                    if (itemReqVo.getNum() - stock.getAvailableNum() < 0) {
                        stock.setAvailableNum(stock.getAvailableNum() - itemReqVo.getNum());
                        lockNum = itemReqVo.getNum();
                        respVo.setLockNum(lockNum);
                        itemReqVo.setNum(0L);
                    } else {
                        stock.setAvailableNum(0L);
                        itemReqVo.setNum(itemReqVo.getNum() - stock.getAvailableNum());
                        lockNum = stock.getAvailableNum();
                        respVo.setLockNum(lockNum);
                    }
                    index++;
                    respVos.add(respVo);
                    //拼装调用锁库vo
                    StockVoRequest stockTemp = new StockVoRequest();
                    stockTemp.setWarehouseCode(stock.getWarehouseCode());
                    stockTemp.setTransportCenterCode(stock.getTransportCenterCode());
                    stockTemp.setSkuCode(itemReqVo.getSkuCode());
                    stockTemp.setCompanyCode(vo.getCompanyCode());
                    stockTemp.setChangeNum(lockNum);
                    stockVoRequests.add(stockTemp);
                }
                //这里还可以移除
            } else {
                //货够
                Stock stock = stocks1.get(temp);
                MerchantLockStockRespVo respVo = new MerchantLockStockRespVo();
                //拼装门店返回信息
                respVo.setTransportCenterCode(stock.getTransportCenterCode());
                respVo.setTransportCenterName(stock.getTransportCenterName());
                respVo.setProductType(itemReqVo.getProductType());
                respVo.setWarehouseCode(stock.getWarehouseCode());
                respVo.setWarehouseName(stock.getWarehouseName());
                respVo.setLineNum(itemReqVo.getLineNum());
                respVo.setSkuCode(itemReqVo.getSkuCode());
                respVo.setLockNum(itemReqVo.getNum());
                //扣减数量
                stock.setAvailableNum(stock.getAvailableNum() - itemReqVo.getNum());
                respVos.add(respVo);
                //拼装调用锁库vo
                StockVoRequest stockTemp = new StockVoRequest();
                stockTemp.setTransportCenterCode(stock.getTransportCenterCode());
                stockTemp.setWarehouseCode(stock.getWarehouseCode());
                stockTemp.setCompanyCode(vo.getCompanyCode());
                stockTemp.setSkuCode(itemReqVo.getSkuCode());
                stockTemp.setChangeNum(itemReqVo.getNum());
                stockVoRequests.add(stockTemp);
            }
        }
        //调用库存接口锁库
        try {
            HttpResponse httpResponse = changeStock(lock);
            if (!MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                throw new GroundRuntimeException("调用库存接口锁定库存失败");
            }
        } catch (Exception e) {
            throw new GroundRuntimeException("调用库存接口锁定库存失败");
        }
        return respVos;
    }

    //    /**
//     * 门店库存锁定
//     *
//     * @param vo
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public List<MerchantLockStockRespVo> lockMerchantStock(MerchantLockStockReqVo vo) {
//        //1.通过省市查询物流中心
//        //2.通过物流中心找到对应仓库
//        //3.根据仓库和skuList查询是否有库存
//        //4.验证,进行锁库操作
//        List<MerchantLockStockRespVo> respVos = Lists.newLinkedList();
//        MerchantLockStockRespVo respVo = null;
//
//        for (MerchantLockStockItemReqVo itemReqVo : vo.getSkuList()) {
//            respVo = new MerchantLockStockRespVo();
//            respVo.setSkuCode(itemReqVo.getSkuCode());
//            respVo.setLockNum(itemReqVo.getNum());
//            respVo.setTransportCenterCode("1025");
//            respVo.setWarehouseCode("1026");
//            respVo.setProductType(itemReqVo.getProductType());
//            respVo.setLineNum(itemReqVo.getLineNum());
//            respVos.add(respVo);
//
//        return respVos;
//    }
    @Override
    public List<Stock> selectByQueryList(List<String> centerCodes, List<String> warehouseCodes, List<String> skuCodes, String companyCode) {
        return stockDao.selectByQueryList(centerCodes, warehouseCodes, skuCodes, companyCode);
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
            log.error("error", e);
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
            log.error("error", e);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    //增加库存(如果某些商品库存数据不存在,则需要新增一条数据,如果存在则需要增加存库数据)
    public Integer saveStock(InboundReqVo reqVo) {
        //1,根据采购单的sku编码,公司编码,物流中心编码,库房编码查看该维度下的商品存在与否,不存在新增
        try {
            List<String> skuCodes = reqVo.getPurchaseItemVos().stream().filter(Objects::nonNull).map(InboundItemReqVo::getSkuCode).collect(Collectors.toList());
            Map<String, Long> maps = skuService.getSkuConvertNumBySkuCodes(skuCodes);
            QueryStockSkuReqVo skuReqVo = BeanCopyUtils.copy(reqVo, QueryStockSkuReqVo.class);
            skuReqVo.setSkuList(skuCodes);
            List<QueryStockSkuRespVo> queryStockSkuRespVos = selectStockSkus(skuReqVo);
            Set<String> sets = Sets.newHashSet();
            if (CollectionUtils.isNotEmpty(queryStockSkuRespVos)) {
                queryStockSkuRespVos.forEach(o -> {
                    sets.add(o.getSkuCode());
                });
            }
            //将stock表存在和不存在数据分开
            List<InboundItemReqVo> notExist = Lists.newArrayList();
            List<InboundItemReqVo> exist = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(queryStockSkuRespVos)) {
                for (InboundItemReqVo o : reqVo.getPurchaseItemVos()) {
                    if (sets.contains(o.getSkuCode())) {
                        exist.add(o);
                    } else {
                        notExist.add(o);
                    }
                }
            } else {
                notExist = reqVo.getPurchaseItemVos();
            }
            if (CollectionUtils.isNotEmpty(notExist)) {
                //批量新增不存在的库存数据
                List<Stock> stocks = transformStocks(reqVo, notExist, maps);
                Integer i = stockDao.insertBatch(stocks);
            }
            if (CollectionUtils.isNotEmpty(exist)) {
                //批量更新存在的库存数据
                List<UpdateStockReqVo> updateStockReqVos = transformUpdateStocksVo(reqVo, exist, queryStockSkuRespVos, maps);
                Integer y = stockDao.updateBatchStock(updateStockReqVos);
            }
            return Integer.valueOf(1);
        } catch (Exception e) {
            log.error("error", e);
            if (e instanceof BizException) {
                throw new BizException(e.getMessage());
            } else {
                throw new BizException("新增入库单数据时,更改stock表失败");
            }
        }
    }

    private List<Stock> transformStocks(InboundReqVo reqVo, List<InboundItemReqVo> itemReqVos, Map<String, Long> maps) {
        try {
            List<Stock> list = Lists.newArrayList();
            Map<String, InboundItemReqVo> reqVoMap = itemReqVos.stream().collect(Collectors.toMap(InboundItemReqVo::getSkuCode, Function.identity(), (k1, k2) -> k1));
            Date date = new Date();
            for (InboundItemReqVo o : itemReqVos) {
                InboundItemReqVo itemReqVo = reqVoMap.get(o.getSkuCode());
                Stock stock = BeanCopyUtils.copy(itemReqVo, Stock.class);
                stock.setCreateTime(date);
                stock.setCompanyCode(reqVo.getCompanyCode());
                stock.setCompanyName(reqVo.getCompanyName());
//                stock.setAllocateProductWayNum(0L);
//                stock.setAllocateSpareWayNum(0L);
//                Long convertNum = maps.get(o.getSkuCode());
                long saleUnitNum = o.getNum() * Optional.ofNullable(o.getConvertNum()).orElse(1L);
                stock.setPurchaseWayNum(saleUnitNum);
//                stock.setAuthenticNum(saleUnitNum);
                stock.setAvailableNum(saleUnitNum);
                stock.setInventoryNum(saleUnitNum);
                stock.setLockNum(0L);
//                stock.setModelNumberName(o.getModelNumber());
                stock.setNewDelivery(reqVo.getSupplyCode());
                stock.setNewDeliveryName(reqVo.getSupplyName());
                stock.setNewPurchasePrice(o.getPrice());
                //仓库类型 TODO
//                stock.setProductCategoryId(o.getProductCategoryCode());
                stock.setPurchaseGroupCode(reqVo.getPurchaseGroupCode());
                stock.setPurchaseGroupName(reqVo.getPurchaseGroupName());
//                stock.setTypeCode(o.getProductTypeCode());
//                stock.setTypeName(o.getProductTypeName());
                stock.setTransportCenterCode(reqVo.getTransportCenterCode());
                stock.setTransportCenterName(reqVo.getTransportCenterName());
                stock.setWarehouseCode(reqVo.getWarehouseCode());
                stock.setWarehouseName(reqVo.getWarehouseName());
                //包装编码  包装名称 todo
//                stock.setSortCode(o.getProductSortCode());
//                stock.setSortName(o.getProductSortName());
//                stock.setWayNum(0L);
                stock.setStockUnitCode(o.getSaleUnitCode());
                stock.setStockUnitName(o.getSaleUnitName());
                stock.setTaxPrice(o.getPrice());
                stock.setTaxRate(o.getTaxRate());
//                stock.setBrandCode(o.getProductBrandCode());
//                stock.setBrandName(o.getProductBrandName());
                list.add(stock);
            }
            return list;
        } catch (Exception e) {
            log.error("error", e);
            throw new BizException("采购单数据转换为 [新增] 库存数据集合失败");
        }
    }

    private List<UpdateStockReqVo> transformUpdateStocksVo(InboundReqVo reqVo, List<InboundItemReqVo> itemReqVos, List<QueryStockSkuRespVo> queryStockSkuRespVos, Map<String, Long> maps) {
        try {
            List<UpdateStockReqVo> list = Lists.newArrayList();
            Map<String, Long> stockNums = queryStockSkuRespVos.stream().collect(Collectors.toMap(QueryStockSkuRespVo::getSkuCode, QueryStockSkuRespVo::getStock, (k1, k2) -> k2));
            for (InboundItemReqVo o : itemReqVos) {
                //计算销售单位数量
//                Long convertNum = maps.get(o.getSkuCode());
//                long saleUnitNum = o.getNum() * convertNum;
                long saleUnitNum = o.getNum() * Optional.ofNullable(o.getConvertNum()).orElse(1L);
                UpdateStockReqVo vo = new UpdateStockReqVo(reqVo.getCompanyCode(), reqVo.getTransportCenterCode(), reqVo.getWarehouseCode(), o.getSkuCode(), saleUnitNum, Byte.valueOf("1"));
                list.add(vo);
            }
            return list;
        } catch (Exception e) {
            log.error("error", e);
            throw new BizException("采购单数据转换为 [更新] 库存数据集合失败");
        }
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
            stockFlow.setBeforeLockNum(stock.getLockNum());
            stockFlow.setAfterLockNum(stock.getLockNum() + reqVo.getChangeNum());
//            stockFlow.setLockType(reqVo.getLockType());
//            stockFlow.setBeforeAuthenticNum(stock.getAuthenticNum());
            stockFlow.setBeforeAvailableNum(stock.getAvailableNum());
            stockFlow.setBeforeInventoryNum(stock.getInventoryNum());
//            stockFlow.setBeforeSpareNum(stock.getSpareNum());
            if (reqVo.getLockType() == 1) {//解锁库存
                Long unLockNum = reqVo.getLockNum() - reqVo.getChangeNum();
                stock.setLockNum(stock.getLockNum() - reqVo.getLockNum());
                if (reqVo.getChangeType() == 1) {
                    if (reqVo.getChangeNum() == 0) {
//                        stock.setAuthenticNum(stock.getAuthenticNum() + reqVo.getLockNum());
                        stock.setAvailableNum(stock.getAvailableNum() + reqVo.getLockNum());
                    } else {//解锁并减少库存
                        stock.setAvailableNum(stock.getAvailableNum() + unLockNum);
//                        stock.setAuthenticNum(stock.getAuthenticNum() + unLockNum);
                        stock.setInventoryNum(stock.getInventoryNum() - reqVo.getChangeNum());
                    }
                } else {
//                    stock.setSpareNum(stock.getSpareNum() - reqVo.getChangeNum());
                }
                stockDao.updateByPrimaryKey(stock);
            } else {//锁定库存
                stock.setLockNum(stock.getLockNum() + reqVo.getChangeNum());
                if (reqVo.getChangeType() == 1) {
//                    stock.setAuthenticNum(stock.getAuthenticNum() - reqVo.getChangeNum());
                    stock.setAvailableNum(stock.getAvailableNum() - reqVo.getChangeNum());
                } else {
//                    stock.setSpareNum(stock.getSpareNum() - reqVo.getChangeNum());
                }
                stock.setUpdateTime(new Date());
                stockDao.updateByPrimaryKey(stock);
            }
//            stockFlow.setAfterAuthenticNum(stock.getAuthenticNum());
            stockFlow.setAfterAvailableNum(stock.getAvailableNum());
            stockFlow.setAfterInventoryNum(stock.getInventoryNum());
//            stockFlow.setAfterSpareNum(stock.getSpareNum());
            stockFlowDao.insertOne(stockFlow);
            return lockCode;
        } catch (Exception e) {
            log.error("error", e);
            if (reqVo.getLockType() == 0) {
                throw new BizException(ResultCode.STOCK_LOCK_ERROR);
            } else {
                throw new BizException(ResultCode.STOCK_UNLOCK_ERROR);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Save
    public HttpResponse changeStock(StockChangeRequest stockChangeRequest) {

        try{
            LOGGER.info("对库存进行操作");
            if (CollectionUtils.isEmpty(stockChangeRequest.getStockVoRequests())) {
                return HttpResponse.failure(ResultCode.STOCK_CHANGE_ERROR);
            }
            //查询需要做修改的库存数据
            List<Stock> stocks = stockDao.selectListByCodesAndSkuCode(stockChangeRequest.getStockVoRequests());
            Map<String, Stock> stockMap = new HashMap<>();
            stocks.forEach(stock -> {
                stockMap.put(stock.getSkuCode() + stock.getWarehouseCode(), stock);
            });
            List<Stock> updates = new ArrayList<>();
            List<Stock> adds = new ArrayList<>();
            Boolean flage = false;
            //将需要修改的库存进行逻辑计算
            List<StockFlow> flows = new ArrayList<>();
            for (StockVoRequest stockVoRequest : stockChangeRequest.getStockVoRequests()) {
                if (stockMap.containsKey(stockVoRequest.getSkuCode() + stockVoRequest.getWarehouseCode())) {
                    Stock stock = stockMap.get(stockVoRequest.getSkuCode() + stockVoRequest.getWarehouseCode());
                    //设置库存流水期初值
                    StockFlow stockFlow = new StockFlow();
                    stockFlow.setStockCode(stock.getStockCode());
                    stockFlow.setFlowCode("FL" + IdSequenceUtils.getInstance().nextId());
                    stockFlow.setOrderCode(stockChangeRequest.getOrderCode());
                    stockFlow.setOrderType(stockChangeRequest.getOrderType());
//                    stockFlow.setOrderSource();
                    stockFlow.setSkuCode(stock.getSkuCode());
                    stockFlow.setSkuName(stock.getSkuName());
                    stockFlow.setOperationType(stockChangeRequest.getOperationType());
                    stockFlow.setBeforeInventoryNum(stock.getInventoryNum());
                    stockFlow.setBeforeLockNum(stock.getLockNum());
                    stockFlow.setBeforeAvailableNum(stock.getAvailableNum());
                    stockFlow.setBeforeAllocationWayNum(stock.getAllocationWayNum());
                    stockFlow.setBeforePurchaseWayNum(stock.getPurchaseWayNum());
                    stockFlow.setBeforeTotalWayNum(stock.getTotalWayNum());
                    stock = stockVoRequestToStock(stock, stockVoRequest, stockChangeRequest.getOperationType());
                    if (null != stock) {
                        updates.add(stock);
                    } else {
                        flage = true;
                        break;
                    }
                    //设置库存流水变化后值
                    stockFlow.setChangeNum(stockVoRequest.getChangeNum());
                    stockFlow.setLockStatus(stockChangeRequest.getOperationType());
                    stockFlow.setAfterInventoryNum(stock.getInventoryNum());
                    stockFlow.setAfterLockNum(stock.getLockNum());
                    stockFlow.setAfterAvailableNum(stock.getAvailableNum());
                    stockFlow.setAfterAllocationWayNum(stock.getAllocationWayNum());
                    stockFlow.setAfterPurchaseWayNum(stock.getPurchaseWayNum());
                    stockFlow.setAfterTotalWayNum(stock.getTotalWayNum());
                    stockFlow.setCreateBy(stockVoRequest.getOperator());
                    stockFlow.setUpdateBy(stockVoRequest.getOperator());
                    stockFlow.setDocumentNum(stockVoRequest.getDocumentNum());
                    stockFlow.setDocumentType(stockVoRequest.getDocumentType());
                    //1入库
                    if(stockVoRequest.getDocumentType() == 1){
                        //采购
                        if(stockVoRequest.getSourceDocumentType() == 1){
                            stockFlow.setSourceDocumentType(3);
                            //调拨
                        }else if(stockVoRequest.getSourceDocumentType() == 2){
                            stockFlow.setSourceDocumentType(4);
                            //退货
                        }else if(stockVoRequest.getSourceDocumentType() == 3){
                            stockFlow.setSourceDocumentType(5);
                            //移库
                        }else if(stockVoRequest.getSourceDocumentType() == 4){
                            stockFlow.setSourceDocumentType(6);
                            //监管仓入库
                        }else if(stockVoRequest.getSourceDocumentType() == 5){
                            stockFlow.setSourceDocumentType(7);
                            //报废
                        }else if(stockVoRequest.getSourceDocumentType() == 6){
                            stockFlow.setSourceDocumentType(8);
                        }
                        //0 出库
                    }else if(stockVoRequest.getDocumentType() == 0){
                        //退供
                        if(stockVoRequest.getSourceDocumentType() == 1){
                            stockFlow.setSourceDocumentType(2);
                            //调拨
                        }else if(stockVoRequest.getSourceDocumentType() == 2){
                            stockFlow.setSourceDocumentType(4);
                            //订单
                        }else if(stockVoRequest.getSourceDocumentType() == 3){
                            stockFlow.setSourceDocumentType(9);
                            //移库
                        }else if(stockVoRequest.getSourceDocumentType() == 4){
                            stockFlow.setSourceDocumentType(6);
                            //监管仓出库
                        }else if(stockVoRequest.getSourceDocumentType() == 5){
                            stockFlow.setSourceDocumentType(10);
                        }
                    }
                    stockFlow.setSourceDocumentNum(stockVoRequest.getSourceDocumentNum());
                    // stockFlow.setSourceDocumentType(stockVoRequest.getSourceDocumentType());
                    stockFlow.setRemark(stockVoRequest.getRemark());
                    stockFlow.setStockCost(stockVoRequest.getNewPurchasePrice());
                    flows.add(stockFlow);
                } else {
                    Stock stock = new Stock();
                    //设置库存流水期初值
                    StockFlow stockFlow = new StockFlow();
                    stockFlow.setFlowCode("FL" + IdSequenceUtils.getInstance().nextId());
                    stockFlow.setOrderCode(stockChangeRequest.getOrderCode());
                    stockFlow.setOrderType(stockChangeRequest.getOrderType());
//                    stockFlow.setOrderSource();
                    stockFlow.setSkuCode(stockVoRequest.getSkuCode());
                    stockFlow.setSkuName(stockVoRequest.getSkuName());
                    stockFlow.setOperationType(stockChangeRequest.getOperationType());
                    stockFlow.setChangeNum(stockVoRequest.getChangeNum());
                    stockFlow.setBeforeInventoryNum(0l);
                    stockFlow.setBeforeLockNum(0l);
                    stockFlow.setBeforeAvailableNum(0l);
                    stockFlow.setBeforeAllocationWayNum(0l);
                    stockFlow.setBeforePurchaseWayNum(0l);
                    stockFlow.setBeforeTotalWayNum(0l);
                    stock = stockVoRequestToStock(stock, stockVoRequest, stockChangeRequest.getOperationType());
                    ProductSkuInfo productSkuInfo = productSkuDao.getSkuInfo(stock.getSkuCode());
                    if (productSkuInfo == null) {
                        flage = true;
                        break;
                    }
                    stock.setStockUnitCode(productSkuInfo.getUnitCode());
                    stock.setStockUnitName(productSkuInfo.getUnitName());
                    if (stock != null) {
                        adds.add(stock);
                    } else {
                        flage = true;
                        break;
                    }
                    //设置库存流水修改后的值
                    stockFlow.setLockStatus(stockChangeRequest.getOperationType());
                    stockFlow.setStockCode(stock.getStockCode());
                    stockFlow.setAfterInventoryNum(stock.getInventoryNum());
                    stockFlow.setAfterLockNum(stock.getLockNum());
                    stockFlow.setAfterAvailableNum(stock.getAvailableNum());
                    stockFlow.setAfterAllocationWayNum(stock.getAllocationWayNum());
                    stockFlow.setAfterPurchaseWayNum(stock.getPurchaseWayNum());
                    stockFlow.setAfterTotalWayNum(stock.getTotalWayNum());
                    stockFlow.setCreateBy(stockVoRequest.getOperator());
                    stockFlow.setUpdateBy(stockVoRequest.getOperator());
                    stockFlow.setDocumentNum(stockVoRequest.getDocumentNum());
                    stockFlow.setDocumentType(stockVoRequest.getDocumentType());
                    //1入库
                    if(stockVoRequest.getDocumentType() == 1){
                        //采购
                        if(stockVoRequest.getSourceDocumentType() == 1){
                            stockFlow.setSourceDocumentType(3);
                            //调拨
                        }else if(stockVoRequest.getSourceDocumentType() == 2){
                            stockFlow.setSourceDocumentType(4);
                            //退货
                        }else if(stockVoRequest.getSourceDocumentType() == 3){
                            stockFlow.setSourceDocumentType(5);
                            //移库
                        }else if(stockVoRequest.getSourceDocumentType() == 4){
                            stockFlow.setSourceDocumentType(6);
                            //监管仓入库
                        }else if(stockVoRequest.getSourceDocumentType() == 5){
                            stockFlow.setSourceDocumentType(7);
                            //报废
                        }else if(stockVoRequest.getSourceDocumentType() == 6){
                            stockFlow.setSourceDocumentType(8);
                        }
                        //0 出库
                    }else if(stockVoRequest.getDocumentType() == 0){
                        //退供
                        if(stockVoRequest.getSourceDocumentType() == 1){
                            stockFlow.setSourceDocumentType(2);
                            //调拨
                        }else if(stockVoRequest.getSourceDocumentType() == 2){
                            stockFlow.setSourceDocumentType(4);
                            //订单
                        }else if(stockVoRequest.getSourceDocumentType() == 3){
                            stockFlow.setSourceDocumentType(9);
                            //移库
                        }else if(stockVoRequest.getSourceDocumentType() == 4){
                            stockFlow.setSourceDocumentType(6);
                            //监管仓出库
                        }else if(stockVoRequest.getSourceDocumentType() == 5){
                            stockFlow.setSourceDocumentType(10);
                        }
                    }
                    stockFlow.setSourceDocumentNum(stockVoRequest.getSourceDocumentNum());
                    //  stockFlow.setSourceDocumentType(stockVoRequest.getSourceDocumentType());
                    stockFlow.setRemark(stockVoRequest.getRemark());
                    stockFlow.setStockCost(stockVoRequest.getNewPurchasePrice());
                    flows.add(stockFlow);
                }
            }
            if (flage) {
                return HttpResponse.failure(ResultCode.STOCK_CHANGE_ERROR);
            }
            if (CollectionUtils.isNotEmpty(flows)) {
                stockFlowDao.insertBatch(flows);
            }
            if (CollectionUtils.isNotEmpty(updates)) {
                stockDao.updateBatch(updates);
            }
            if (CollectionUtils.isNotEmpty(adds)) {
                stockDao.insertBatch(adds);
            }
        }catch (Exception e){
            log.error("error", e);
            LOGGER.error("操作库存失败", e);
            throw new BizException("操作库存失败");
        }
        return HttpResponse.success();
    }

    /**
     * 参数转换成库存数据
     *
     * @param stock          库存实体
     * @param stockVoRequest 请求改变实体
     * @param operationType  操作类型
     * @return
     */
    private Stock stockVoRequestToStock(Stock stock, StockVoRequest stockVoRequest, Integer operationType) {
        if (stockVoRequest.getWarehouseCode() != null){
            StockVoRequest warehouseTypeName = stockDao.selectWarehouseTypeBycode(stockVoRequest.getWarehouseCode());
            stockVoRequest.setWarehouseType(warehouseTypeName.getWarehouseType());
        }
        if (null == stock.getId()) {
            BeanCopyUtils.copy(stockVoRequest, stock);
            stock.setLockNum(0L);
            stock.setInventoryNum(0L);
            stock.setAvailableNum(0L);
            stock.setPurchaseWayNum(0L);
            stock.setAllocationWayNum(0L);
            stock.setTotalWayNum(0L);
            stock.setStockCode("ST" + IdSequenceUtils.getInstance().nextId());
            stock.setTaxRate(0L);
            stock.setTaxCost(0L);
            stock.setUpdateBy(stockVoRequest.getOperator());
            stock.setCreateBy(stockVoRequest.getOperator());
        }
        stock.setUpdateBy(stockVoRequest.getOperator());
        if(stockVoRequest.getNewPurchasePrice() != null && stockVoRequest.getNewPurchasePrice() != 0){
            stock.setNewPurchasePrice(stockVoRequest.getNewPurchasePrice());
        }
        stock.setTaxPrice(stockVoRequest.getNewPurchasePrice());
        if (stockVoRequest.getTaxRate() != null){
            stock.setTaxRate(stockVoRequest.getTaxRate());
        }
        stock.setNewDelivery(stockVoRequest.getNewDelivery());
        stock.setNewDeliveryName(stockVoRequest.getNewDeliveryName());
        switch (operationType) {
            //锁定库存数
            case 1:
                stock.setLockNum(stock.getLockNum() + stockVoRequest.getChangeNum());
                stock.setAvailableNum(stock.getAvailableNum() - stockVoRequest.getChangeNum());
                break;
            //减少库存并解锁
            case 2:
                stock.setInventoryNum(stock.getInventoryNum() - stockVoRequest.getChangeNum());
                stock.setLockNum(stock.getLockNum() - stockVoRequest.getChangeNum());
                break;
            //解锁锁定库存
            case 3:
                stock.setAvailableNum(stock.getAvailableNum() + stockVoRequest.getChangeNum());
                stock.setLockNum(stock.getLockNum() - stockVoRequest.getChangeNum());
                break;
            //无锁定直接减库存 总库存减可用库存减
            case 4:
                stock.setAvailableNum(stock.getAvailableNum() - stockVoRequest.getChangeNum());
                stock.setInventoryNum(stock.getInventoryNum() - stockVoRequest.getChangeNum());
                break;
            //加库存并锁定库存
            case 5:
                stock.setInventoryNum(stock.getInventoryNum() + stockVoRequest.getChangeNum());
                stock.setLockNum(stock.getLockNum() + stockVoRequest.getChangeNum());
                break;
            //只改变采购在途数和在途总数
            case 6:
                stock.setPurchaseWayNum(stock.getPurchaseWayNum() + stockVoRequest.getChangeNum());
                stock.setTotalWayNum(stock.getTotalWayNum() + stockVoRequest.getChangeNum());
                break;
            //只改变调拨在途和在途总数
            case 7:
                stock.setAllocationWayNum(stock.getAllocationWayNum() + stockVoRequest.getChangeNum());
                stock.setTotalWayNum(stock.getTotalWayNum() + stockVoRequest.getChangeNum());
                break;
            //调拨在途变成可用及库存数
            case 8:
                stock.setAllocationWayNum(stock.getAllocationWayNum() - stockVoRequest.getChangeNum());
                stock.setTotalWayNum(stock.getPurchaseWayNum() + stock.getAllocationWayNum());
                stock.setInventoryNum(stock.getInventoryNum() + stockVoRequest.getChangeNum());
                stock.setAvailableNum(stock.getAvailableNum() + stockVoRequest.getChangeNum());
                break;
            //采购在途变成可用及库存数
            case 9:
                stock.setPurchaseWayNum(stock.getPurchaseWayNum() - stockVoRequest.getChangeNum());
                stock.setTotalWayNum(stock.getPurchaseWayNum() + stock.getAllocationWayNum());
                stock.setInventoryNum(stock.getInventoryNum() + stockVoRequest.getChangeNum());
                stock.setAvailableNum(stock.getAvailableNum() + stockVoRequest.getChangeNum());
                break;
            //直接加库存
            case 10:
                stock.setInventoryNum(stock.getInventoryNum() + stockVoRequest.getChangeNum());
                stock.setAvailableNum(stock.getAvailableNum() + stockVoRequest.getChangeNum());
                break;
            //只改变采购在途数和在途总数(减订单完成差异价)
            case 11:
                stock.setPurchaseWayNum(stock.getPurchaseWayNum() - stockVoRequest.getChangeNum());
                stock.setTotalWayNum(stock.getTotalWayNum() - stockVoRequest.getChangeNum());
                break;
            default:
                return null;
        }
        //库存不管是锁定数还是可以数还是库存数都不能为负
        if (stock.getLockNum() < 0 || stock.getInventoryNum() < 0 || stock.getAvailableNum() < 0 || stock.getAllocationWayNum() < 0 || stock.getTotalWayNum() < 0) {
            return null;
        }
        return stock;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changeWayNum(StockWayNumRequest stockWayNumRequest) throws Exception {
        StockFlowRequest stockFlowRequest = BeanCopyUtils.copy(stockWayNumRequest, StockFlowRequest.class);
        Stock stock = stockFlowDao.selectOneStockInfoByStockFlow(stockFlowRequest);
        StockFlow stockFlow = BeanCopyUtils.copy(stockWayNumRequest, StockFlow.class);
//        stockFlow.setBeforeAuthenticNum(stock.getAuthenticNum());
        stockFlow.setBeforeAvailableNum(stock.getAvailableNum());
        stockFlow.setBeforeLockNum(stock.getLockNum());
        stockFlow.setBeforeInventoryNum(stock.getInventoryNum());
//        stockFlow.setBeforeSpareNum(stock.getSpareNum());
        if (null == stock) {
            throw new BizException(ResultCode.STOCK_CHANGE_ERROR);
        }
//        stock.setWayNum(stock.getWayNum()-stockWayNumRequest.getChangeNum());
        if (stockWayNumRequest.getChangeType() == 0) {//正品
            stock.setAvailableNum(stock.getAvailableNum() + stockWayNumRequest.getChangeNum());
            stock.setInventoryNum(stock.getInventoryNum() + stockWayNumRequest.getChangeNum());
//            stock.setAuthenticNum(stock.getAuthenticNum()+stockWayNumRequest.getChangeNum());
//            stock.setWayNum(stock.getWayNum()-stockWayNumRequest.getWayNum());
        } else if (stockWayNumRequest.getChangeType() == 1) {//备品
//            stock.setWayNum(stock.getWayNum()-stockWayNumRequest.getWayNum());
//            stock.setSpareNum(stock.getSpareNum()+stockWayNumRequest.getChangeNum());
        }
        stockDao.updateByPrimaryKey(stock);
//        stockFlow.setAfterAuthenticNum(stock.getAuthenticNum());
        stockFlow.setAfterAvailableNum(stock.getAvailableNum());
        stockFlow.setAfterLockNum(stock.getLockNum());
        stockFlow.setAfterInventoryNum(stock.getInventoryNum());
//        stockFlow.setAfterSpareNum(stock.getSpareNum());
//        stockFlow.setLockType(3);
        stockFlowDao.insertOne(stockFlow);
        //添加库存流水
        return true;
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
    public PageInfo<StockBatchRespVO> selectOneStockBatchInfoByStockBatchId(Long stockBatchId,Integer page_no,Integer page_size) {
        try {
            LOGGER.info("根据stockBatchId查询单个stockBatch信息");
            PageHelper.startPage(page_no, page_size);
           // List<StockBatchRespVO> stockBatchRespVOs = stockDao.selectOneStockBatchInfoByStockBatchId(stockBatchId);
           // Long total = stockDao.selectOneStockBatchInfoByStockBatchIdInfoByPage(stockBatchId);
           // new PageResData<>(total.intValue(),stockBatchRespVOs);
            return  new PageInfo<StockBatchRespVO>(stockDao.selectOneStockBatchInfoByStockBatchId(stockBatchId));
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
                            if (stockBatchVoRequest.getSkuCode().equals(stockBatchProductSkuRespVO.getSkuCode())){
                                stockBatch.setStockBatchCode("SB"+ IdSequenceUtils.getInstance().nextId());
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
                                stockBatch.setProductionDate(stockBatchVoRequest.getProductionDate());
                                stockBatch.setBatchRemark(stockBatchVoRequest.getBatchRemark());
                                stockBatch.setCategoryTypeCode(stockBatchProductSkuRespVO.getProductCategoryCode());
                                stockBatch.setCategoryTypeName(stockBatchProductSkuRespVO.getProductCategoryName());
                                stockBatch.setSpec(stockBatchProductSkuRespVO.getSpec());
                                stockBatch.setColorCode(stockBatchProductSkuRespVO.getColorCode());
                                stockBatch.setColorName(stockBatchProductSkuRespVO.getColorName());
                                stockBatch.setModelNumber(stockBatchProductSkuRespVO.getModelNumber());
                                stockBatch.setUnitCode(stockBatchProductSkuRespVO.getUnitCode());
                                stockBatch.setUnitName(stockBatchProductSkuRespVO.getUnitName());
                                stockBatch.setPack(stockBatchProductSkuRespVO.getBaseProductContent()+"/"+stockBatchProductSkuRespVO.getUnitCode());
                                stockBatch.setConfigStatus(stockBatchProductSkuRespVO.getConfigStatus());
                                stockBatch = stockBatchVoRequestToStock(stockBatch,stockBatchCode,stockBatchVoRequest,stockChangeRequest.getOperationType());
                                stockBatch.setSupplierCode(stockBatchVoRequest.getSupplierCode());
                                stockBatch.setSupplierName(stockBatchVoRequest.getSupplierName());
                                stockBatch.setNewDelivery(stockBatchVoRequest.getNewDelivery());
                                stockBatch.setNewDeliveryName(stockBatchVoRequest.getNewDeliveryName());
                                stockBatch.setPurchasePrice(stockBatchVoRequest.getPurchasePrice());
                                stockBatch.setTaxRate(stockBatchVoRequest.getTaxRate());
                                stockBatchs.add(stockBatch);
                                // 设置批次库存流水
                                stockBatchFlow.setStockBatchCode(stockBatch.getStockBatchCode());
                                stockBatchFlow.setFlowBatchCode("FL"+ IdSequenceUtils.getInstance().nextId());
                                stockBatchFlow.setBatchCode(stockBatchVoRequest.getBatchCode());
                                stockBatchFlow.setOrderCode(stockChangeRequest.getOrderCode());
                                stockBatchFlow.setOrderType(stockChangeRequest.getOrderType());
                                // stockBatchFlow.setOrderSource();
                                stockBatchFlow.setSkuCode(stockBatch.getSkuCode());
                                stockBatchFlow.setSkuName(stockBatch.getSkuName());
                                stockBatchFlow.setLockStatus(1);
                                stockBatchFlow.setDocumentType(stockBatchVoRequest.getDocumentType());
                                stockBatchFlow.setDocumentNum(stockBatchVoRequest.getDocumentNum());
                                stockBatchFlow.setSourceDocumentType(stockBatchVoRequest.getSourceDocumentType());
                                stockBatchFlow.setSourceDocumentNum(stockBatchVoRequest.getSourceDocumentNum());
                                stockBatchFlow.setUpdateByCode(stockBatchVoRequest.getUpdateByCode());
                                stockBatchFlow.setUpdateByName(stockBatchVoRequest.getUpdateByName());
                                stockBatchFlow.setRemark(stockBatchVoRequest.getRemark());
                                stockBatchFlow.setBeforeInventoryNum(stockBatchCode.getInventoryNum());
                                stockBatchFlow.setAfterInventoryNum(stockBatch.getInventoryNum());
                                stockBatchFlow.setBeforeAvailableNum(stockBatchCode.getAvailableNum());
                                stockBatchFlow.setAfterAvailableNum(stockBatch.getAvailableNum());
                                stockBatchFlow.setBeforeLockNum(stockBatchCode.getLockNum());
                                stockBatchFlow.setAfterLockNum(stockBatch.getLockNum());
                                stockBatchFlow.setChangeNum(stockBatchVoRequest.getChangeNum());
                                stockBatchFlow.setOperationType(stockChangeRequest.getOperationType());
                                flows.add(stockBatchFlow);
                            }else{
                                throw new BizException("新增入库单数据时,更改stockBatch表失败");
                            }
                        }
                        // 批次表插入数据
                        stockDao.insertStockBatch(stockBatchs);
                        stockDao.insertStockBatchFlow(flows);
                    } else {
                        // 相同-判断商品skuCode和入库的商品Sku
                        for (StockBatchProductSkuRespVO stockBatchProductSkuRespVO : stockBatchProductSkuRespVOs) {
                            if (stockBatchVoRequest.getSkuCode().equals(stockBatchProductSkuRespVO.getSkuCode())){
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
                                stockBatch.setProductionDate(stockBatchVoRequest.getProductionDate());
                                stockBatch.setBatchRemark(stockBatchVoRequest.getBatchRemark());
                                stockBatch.setCategoryTypeCode(stockBatchProductSkuRespVO.getProductCategoryCode());
                                stockBatch.setCategoryTypeName(stockBatchProductSkuRespVO.getProductCategoryName());
                                stockBatch.setSpec(stockBatchProductSkuRespVO.getSpec());
                                stockBatch.setColorCode(stockBatchProductSkuRespVO.getColorCode());
                                stockBatch.setColorName(stockBatchProductSkuRespVO.getColorName());
                                stockBatch.setModelNumber(stockBatchProductSkuRespVO.getModelNumber());
                                stockBatch.setUnitCode(stockBatchProductSkuRespVO.getUnitCode());
                                stockBatch.setUnitName(stockBatchProductSkuRespVO.getUnitName());
                                stockBatch.setPack(stockBatchProductSkuRespVO.getBaseProductContent()+"/"+stockBatchProductSkuRespVO.getUnitCode());
                                stockBatch.setConfigStatus(stockBatchProductSkuRespVO.getConfigStatus());
                                stockBatch = stockBatchVoRequestToStock(stockBatch, stockBatchCode, stockBatchVoRequest, stockChangeRequest.getOperationType());
                                stockBatch.setSupplierCode(stockBatchVoRequest.getSupplierCode());
                                stockBatch.setSupplierName(stockBatchVoRequest.getSupplierName());
                                stockBatch.setNewDelivery(stockBatchVoRequest.getNewDelivery());
                                stockBatch.setNewDeliveryName(stockBatchVoRequest.getNewDeliveryName());
                                stockBatch.setPurchasePrice(stockBatchVoRequest.getPurchasePrice());
                                stockBatch.setTaxRate(stockBatchVoRequest.getTaxRate());
                                stockBatchs.add(stockBatch);
                                // 设置批次库存流水
                                stockBatchFlow.setStockBatchCode(stockBatch.getStockBatchCode());
                                stockBatchFlow.setFlowBatchCode("FL"+ IdSequenceUtils.getInstance().nextId());
                                stockBatchFlow.setBatchCode(stockBatchVoRequest.getBatchCode());
                                stockBatchFlow.setOrderCode(stockChangeRequest.getOrderCode());
                                stockBatchFlow.setOrderType(stockChangeRequest.getOrderType());
                                stockBatchFlow.setSkuCode(stockBatch.getSkuCode());
                                stockBatchFlow.setSkuName(stockBatch.getSkuName());
                                stockBatchFlow.setLockStatus(stockChangeRequest.getOperationType());
                                stockBatchFlow.setDocumentType(stockBatchVoRequest.getDocumentType());
                                stockBatchFlow.setDocumentNum(stockBatchVoRequest.getDocumentNum());
                                stockBatchFlow.setSourceDocumentType(stockBatchVoRequest.getSourceDocumentType());
                                stockBatchFlow.setSourceDocumentNum(stockBatchVoRequest.getSourceDocumentNum());
                                stockBatchFlow.setUpdateByCode(stockBatchVoRequest.getUpdateByCode());
                                stockBatchFlow.setUpdateByName(stockBatchVoRequest.getUpdateByName());
                                stockBatchFlow.setRemark(stockBatchVoRequest.getRemark());
                                stockBatchFlow.setBeforeInventoryNum(stockBatchCode.getInventoryNum());
                                stockBatchFlow.setAfterInventoryNum(stockBatch.getInventoryNum());
                                stockBatchFlow.setBeforeAvailableNum(stockBatchCode.getAvailableNum());
                                stockBatchFlow.setAfterAvailableNum(stockBatch.getAvailableNum());
                                stockBatchFlow.setBeforeLockNum(stockBatchCode.getLockNum());
                                stockBatchFlow.setAfterLockNum(stockBatch.getLockNum());
                                stockBatchFlow.setChangeNum(stockBatchVoRequest.getChangeNum());
                                stockBatchFlow.setOperationType(stockChangeRequest.getOperationType());
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

    private StockBatch stockBatchVoRequestToStock(StockBatch stockBatch,StockBatchRespVO stockBatchCode,StockBatchVoRequest stockBatchVoRequest, Integer operationType) {
        /*
          // stockBatch.setAvailableNum(stockBatchVoRequest.getChangeNum());  // 可用库存数
          // stockBatch.setLockNum(0L); //   新增不锁定没有值
          // stockBatch.setInventoryNum(stockBatch.getAvailableNum()+stockBatch.getLockNum());  // 总库存数
        */
        switch (operationType) {
            //直接加库存
            case 1:
                stockBatch.setInventoryNum(stockBatchVoRequest.getChangeNum().longValue());
                stockBatch.setAvailableNum(stockBatchVoRequest.getChangeNum().longValue());
                break;
            //锁定库存数
            case 2:
                stockBatch.setLockNum(stockBatch.getLockNum() + stockBatchVoRequest.getChangeNum());
                stockBatch.setAvailableNum(stockBatchCode.getAvailableNum() - stockBatchVoRequest.getChangeNum());
                break;
            //减少库存并解锁
            case 3:
                stockBatch.setInventoryNum(stockBatchCode.getInventoryNum() - stockBatchVoRequest.getChangeNum());
                stockBatch.setLockNum(stockBatchCode.getLockNum() - stockBatchVoRequest.getChangeNum());
                break;
            //解锁锁定库存
            case 4:
                stockBatch.setAvailableNum(stockBatchCode.getAvailableNum() + stockBatchVoRequest.getChangeNum());
                stockBatch.setLockNum(stockBatchCode.getLockNum() - stockBatchVoRequest.getChangeNum());
                break;
            //无锁定直接减库存 总库存减可用库存减
            case 5:
                stockBatch.setAvailableNum(stockBatchCode.getAvailableNum() - stockBatchVoRequest.getChangeNum());
                stockBatch.setInventoryNum(stockBatchCode.getInventoryNum() - stockBatchVoRequest.getChangeNum());
                break;
            //锁定库存转移--暂时不会用到
            //case 6:
               // break;
            //加库存并锁定库存
            case 7:
                stockBatch.setInventoryNum(stockBatchCode.getInventoryNum() + stockBatchVoRequest.getChangeNum());
                stockBatch.setLockNum(stockBatchCode.getLockNum() + stockBatchVoRequest.getChangeNum());
                break;
            default:
                return null;
        }
        //库存不管是锁定数还是可以数还是库存数都不能为负
        if (stockBatchCode.getLockNum() < 0 || stockBatchCode.getInventoryNum() < 0 || stockBatchCode.getAvailableNum() < 0 ) {
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
     * @param reqVO
     * @return
     */
    @Override
    public PageInfo<QueryStockSkuListRespVo> selectStockSkuList(QueryStockSkuListReqVo reqVO) {
        try {
            PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
            List<QueryStockSkuListRespVo> queryStockSkuListRespVos = stockDao.selectStockSkuList(reqVO);
            for (QueryStockSkuListRespVo queryStockSkuListRespVo: queryStockSkuListRespVos) {
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
     * @return
     */
   @Override
    public HttpResponse updateStorehouseById(List<StockRespVO> stockRespVO){
       stockDao.updateStorehouseById(stockRespVO);
       return HttpResponse.success();
    }


    /**
     * 退供锁定批次库存
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
            HttpResponse httpResponse = changeStockBatch(stockChangeRequest);
            if (MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                return true;
            }
        } catch (Exception e) {
            log.error("error", e);
        }
        return false;
    }

    /**
     * 退供解锁批次库存
     * @param reqVO
     * @return
     */
    @Override
    public Boolean returnSupplyUnLockStockBatch(ILockStockBatchReqVO reqVO) {
        try {
            //生成库存数据
            StockChangeRequest stockChangeRequest = new StockChangeRequest();
            //操作类型
            Integer integer1 = 4;
            stockChangeRequest.setOperationType(integer1);
            //sku信息
            List<StockBatchVoRequest> convert = new ReturnSupplyToStockBatchConverter().convert(reqVO);
            stockChangeRequest.setStockBatchVoRequest(convert);
            //采购编码
            stockChangeRequest.setOrderCode(reqVO.getSourceOderCode());
            HttpResponse httpResponse = changeStockBatch(stockChangeRequest);
            if (MsgStatus.SUCCESS.equals(httpResponse.getCode())) {
                return true;
            }
        } catch (Exception e) {
            log.error("error", e);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Save
    public HttpResponse changeStockBatch(StockChangeRequest stockChangeRequest) {
        if (CollectionUtils.isEmpty(stockChangeRequest.getStockBatchVoRequest())) {
            return HttpResponse.failure(ResultCode.STOCK_CHANGE_ERROR);
        }
        //查询需要做修改的库存数据
        List<StockBatch> stockBatches = stockDao.selectListByCodesAndSkuCodeBatch(stockChangeRequest.getStockBatchVoRequest());
        Map<String, StockBatch> stockBatchMap = new HashMap<>();
        stockBatches.forEach(stockBatch -> {
            stockBatchMap.put(stockBatch.getSkuCode() + stockBatch.getWarehouseCode(), stockBatch);
        });
        List<StockBatch> updates = new ArrayList<>();
        List<StockBatch> adds = new ArrayList<>();
        Boolean flage = false;
        //将需要修改的库存进行逻辑计算
        List<StockBatchFlow> flows = new ArrayList<>();
        for (StockBatchVoRequest stockBatchVoRequest : stockChangeRequest.getStockBatchVoRequest()) {
            if (stockBatchMap.containsKey(stockBatchVoRequest.getSkuCode() + stockBatchVoRequest.getWarehouseCode())) {
                StockBatch stockBatch = stockBatchMap.get(stockBatchVoRequest.getSkuCode() + stockBatchVoRequest.getWarehouseCode());
                //设置库存流水期初值
                StockBatchFlow stockBatchFlow = new StockBatchFlow();
                stockBatchFlow.setStockBatchCode(stockBatch.getStockBatchCode());
                stockBatchFlow.setFlowBatchCode("FL" + IdSequenceUtils.getInstance().nextId());
                stockBatchFlow.setOrderCode(stockChangeRequest.getOrderCode());
                stockBatchFlow.setOrderType(stockChangeRequest.getOrderType());
//                    stockBatchFlow.setOrderSource();
                stockBatchFlow.setSkuCode(stockBatch.getSkuCode());
                stockBatchFlow.setSkuName(stockBatch.getSkuName());
                stockBatchFlow.setOperationType(stockChangeRequest.getOperationType());
                stockBatchFlow.setBeforeInventoryNum(stockBatch.getInventoryNum());
                stockBatchFlow.setBeforeLockNum(stockBatch.getLockNum());
                stockBatchFlow.setBeforeAvailableNum(stockBatch.getAvailableNum());
                stockBatch = stockBatchVoRequestToStockBatch(stockBatch,stockBatchVoRequest, stockChangeRequest.getOperationType());
                if (null != stockBatch) {
                    updates.add(stockBatch);
                } else {
                    flage = true;
                    break;
                }
                //设置库存流水变化后值
                stockBatchFlow.setBatchCode(stockBatch.getBatchCode());
                stockBatchFlow.setLockStatus(stockChangeRequest.getOperationType());
                stockBatchFlow.setDocumentType(stockBatchVoRequest.getDocumentType());
                stockBatchFlow.setDocumentNum(stockBatchVoRequest.getDocumentNum());
                stockBatchFlow.setSourceDocumentType(stockBatchVoRequest.getSourceDocumentType());
                stockBatchFlow.setSourceDocumentNum(stockBatchVoRequest.getSourceDocumentNum());
                stockBatchFlow.setUpdateByCode(stockBatchVoRequest.getUpdateByCode());
                stockBatchFlow.setUpdateByName(stockBatchVoRequest.getUpdateByName());
                stockBatchFlow.setRemark(stockBatchVoRequest.getRemark());
                stockBatchFlow.setChangeNum(stockBatchVoRequest.getChangeNum());
                stockBatchFlow.setAfterInventoryNum(stockBatch.getInventoryNum());
                stockBatchFlow.setAfterLockNum(stockBatch.getLockNum());
                stockBatchFlow.setAfterAvailableNum(stockBatch.getAvailableNum());
                flows.add(stockBatchFlow);
            } else {
                StockBatch stockBatch = new StockBatch();
                //设置库存流水期初值
                StockBatchFlow stockBatchFlow = new StockBatchFlow();
                stockBatchFlow.setFlowBatchCode("FL" + IdSequenceUtils.getInstance().nextId());
                stockBatchFlow.setBatchCode(stockBatchFlow.getBatchCode());
                stockBatchFlow.setOrderCode(stockChangeRequest.getOrderCode());
                stockBatchFlow.setOrderType(stockChangeRequest.getOrderType());
//                    stockBatchFlow.setOrderSource();
                stockBatchFlow.setSkuCode(stockBatchVoRequest.getSkuCode());
                stockBatchFlow.setSkuName(stockBatchVoRequest.getSkuName());
                stockBatchFlow.setOperationType(stockChangeRequest.getOperationType());
                stockBatchFlow.setChangeNum(stockBatchVoRequest.getChangeNum());
                stockBatchFlow.setBeforeInventoryNum(0l);
                stockBatchFlow.setBeforeLockNum(0l);
                stockBatchFlow.setBeforeAvailableNum(0l);
                stockBatch = stockBatchVoRequestToStockBatch(stockBatch, stockBatchVoRequest, stockChangeRequest.getOperationType());
                ProductSkuInfo productSkuInfo = productSkuDao.getSkuInfo(stockBatch.getSkuCode());
                if (productSkuInfo == null) {
                    flage = true;
                    break;
                }
                stockBatch.setUnitCode(productSkuInfo.getUnitCode());
                stockBatch.setUnitName(productSkuInfo.getUnitName());
                if (stockBatch != null) {
                    adds.add(stockBatch);
                } else {
                    flage = true;
                    break;
                }
                //设置库存流水修改后的值
                stockBatchFlow.setBatchCode(stockBatch.getBatchCode());
                stockBatchFlow.setLockStatus(stockChangeRequest.getOperationType());
                stockBatchFlow.setDocumentType(stockBatchVoRequest.getDocumentType());
                stockBatchFlow.setDocumentNum(stockBatchVoRequest.getDocumentNum());
                stockBatchFlow.setSourceDocumentType(stockBatchVoRequest.getSourceDocumentType());
                stockBatchFlow.setSourceDocumentNum(stockBatchVoRequest.getSourceDocumentNum());
                stockBatchFlow.setUpdateByCode(stockBatchVoRequest.getUpdateByCode());
                stockBatchFlow.setUpdateByName(stockBatchVoRequest.getUpdateByCode());
                stockBatchFlow.setRemark(stockBatchVoRequest.getRemark());
                stockBatchFlow.setStockBatchCode(stockBatch.getStockBatchCode());
                stockBatchFlow.setAfterInventoryNum(stockBatch.getInventoryNum());
                stockBatchFlow.setAfterLockNum(stockBatch.getLockNum());
                stockBatchFlow.setAfterAvailableNum(stockBatch.getAvailableNum());
                flows.add(stockBatchFlow);
            }
        }
        if (flage) {
            return HttpResponse.failure(ResultCode.STOCK_CHANGE_ERROR);
        }
        if (CollectionUtils.isNotEmpty(flows)) {
            stockDao.insertStockBatchFlows(flows);
        }
        if (CollectionUtils.isNotEmpty(updates)) {
            stockDao.updateBatchStocks(updates);
        }
        if (CollectionUtils.isNotEmpty(adds)) {
            stockDao.insertBatchStockAdd(adds);
        }
        return HttpResponse.success();
    }


    /**
     * 参数转换成库存数据
     * @param stockBatch
     * @param stockBatchVoRequest
     * @param operationType
     * @return
     */

    private StockBatch stockBatchVoRequestToStockBatch(StockBatch stockBatch,StockBatchVoRequest stockBatchVoRequest, Integer operationType) {
        /*
          // stockBatch.setAvailableNum(stockBatchVoRequest.getChangeNum());  // 可用库存数
          // stockBatch.setLockNum(0L); //   新增不锁定没有值
          // stockBatch.setInventoryNum(stockBatch.getAvailableNum()+stockBatch.getLockNum());  // 总库存数
        */
        if (null == stockBatch.getId()) {
            BeanCopyUtils.copy(stockBatchVoRequest, stockBatch);
            stockBatch.setLockNum(0L);
            stockBatch.setInventoryNum(0L);
            stockBatch.setAvailableNum(0L);
            stockBatch.setStockBatchCode("SB" + IdSequenceUtils.getInstance().nextId());
        }
        stockBatch.setTaxRate(stockBatchVoRequest.getTaxRate());
        stockBatch.setNewDelivery(stockBatchVoRequest.getNewDelivery());
        stockBatch.setNewDeliveryName(stockBatchVoRequest.getNewDeliveryName());
        switch (operationType) {
            //直接加库存
            case 1:
                stockBatch.setInventoryNum(stockBatchVoRequest.getChangeNum().longValue());
                stockBatch.setAvailableNum(stockBatchVoRequest.getChangeNum().longValue());
                break;
            //锁定库存数
            case 2:
                stockBatch.setLockNum(stockBatch.getLockNum() + stockBatchVoRequest.getChangeNum());
                stockBatch.setAvailableNum(stockBatch.getAvailableNum() - stockBatchVoRequest.getChangeNum());
                break;
            //减少库存并解锁
            case 3:
                stockBatch.setInventoryNum(stockBatch.getInventoryNum() - stockBatchVoRequest.getChangeNum());
                stockBatch.setLockNum(stockBatch.getLockNum() - stockBatchVoRequest.getChangeNum());
                break;
            //解锁锁定库存
            case 4:
                stockBatch.setAvailableNum(stockBatch.getAvailableNum() + stockBatchVoRequest.getChangeNum());
                stockBatch.setLockNum(stockBatch.getLockNum() - stockBatchVoRequest.getChangeNum());
                break;
            //无锁定直接减库存 总库存减可用库存减
            case 5:
                stockBatch.setAvailableNum(stockBatch.getAvailableNum() - stockBatchVoRequest.getChangeNum());
                stockBatch.setInventoryNum(stockBatch.getInventoryNum() - stockBatchVoRequest.getChangeNum());
                break;
            //锁定库存转移--暂时不会用到
            //case 6:
            // break;
            //加库存并锁定库存
            case 7:
                stockBatch.setInventoryNum(stockBatch.getInventoryNum() + stockBatchVoRequest.getChangeNum());
                stockBatch.setLockNum(stockBatch.getLockNum() + stockBatchVoRequest.getChangeNum());
                break;
            default:
                return null;
        }
        //库存不管是锁定数还是可以数还是库存数都不能为负
        if (stockBatch.getLockNum() < 0 || stockBatch.getInventoryNum() < 0 || stockBatch.getAvailableNum() < 0 ) {
            return null;
        }
        return stockBatch;
    }

    /**
     * 库房管理新增调拨,移库,报废列表查询导入操作
     * @return
     */
    public PageInfo<QueryStockSkuListRespVo> importStockSkuList(QueryImportStockSkuListReqVo reqVO){
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
        return  stockDao.queryStockBatchForAllo(reqVO);
    }

    @Override
    public List<Stock> selectSkuCost() {
        return  stockDao.selectSkuCost();
    }
}
