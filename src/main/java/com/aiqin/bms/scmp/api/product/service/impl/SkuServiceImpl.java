package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.UrlConfig;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductLabelDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuCheckoutDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuDao;
import com.aiqin.bms.scmp.api.product.domain.ProductCategory;
import com.aiqin.bms.scmp.api.product.domain.SkuWarehouseStockNum;
import com.aiqin.bms.scmp.api.product.domain.SupplyComDetailRespVO;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckout;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QuerySkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.ocenter.QueryCenterSkuListReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.OmsProductSkuListReq;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.QuerySkuListPageReq;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.SearchOmsSkuListReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.SearchOrderReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.purchase.CheckPurchaseSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.purchase.QueryPurchaseSkuReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.store.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuLabelInfo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuResponse;
import com.aiqin.bms.scmp.api.product.domain.response.sku.QueryProductSkuListResp;
import com.aiqin.bms.scmp.api.product.domain.response.sku.merchant.MerchantSkuItemRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ocenter.QueryCenterSkuListRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.oms.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.purchase.PurchaseItemRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.*;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuCheckoutMapper;
import com.aiqin.bms.scmp.api.product.mapper.ProductSkuInfoDao;
import com.aiqin.bms.scmp.api.product.service.ProductCategoryService;
import com.aiqin.bms.scmp.api.product.service.SkuService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author wangxu
 * @功能说明:
 * @date 2018/12/27 0027 16:20
 */
@Service
@Transactional
public class SkuServiceImpl implements SkuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkuServiceImpl.class);

    @Autowired
    private ProductSkuDao productSkuDao;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductLabelDao productLabelDao;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private ProductSkuInfoDao productSkuInfoDao;
    @Autowired
    private ProductSkuCheckoutMapper productSkuCheckoutMapper;
    @Autowired
    private ProductSkuCheckoutDao productSkuCheckoutDao;


    @Override
    public BasePage<ProductSkuInfo> getSkuList(QuerySkuListReqVO querySkuListReqVO) {
        try {
            PageHelper.startPage(querySkuListReqVO.getPageNo(), querySkuListReqVO.getPageSize());
            List<ProductSkuInfo> productSkuInfos = productSkuDao.getProductSkuInfoList(querySkuListReqVO);
            return PageUtil.getPageList(querySkuListReqVO.getPageNo(), productSkuInfos);
        } catch (GroundRuntimeException e) {
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    @Override
    public BasePage<PurchaseItemRespVo> getPurchaseSkuList(QueryPurchaseSkuReqVO queryPurchaseSkuReqVO) {
        try {
            PageHelper.startPage(queryPurchaseSkuReqVO.getPageNo(), queryPurchaseSkuReqVO.getPageSize());
            List<PurchaseItemRespVo> productSkuInfos = productSkuDao.getPurchaseSkuList(queryPurchaseSkuReqVO);
            return PageUtil.getPageList(queryPurchaseSkuReqVO.getPageNo(), productSkuInfos);
        } catch (GroundRuntimeException e) {
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public List<MerchantSkuItemRespVO> getMerchantSkuListByCodes(QueryMerchantSkuListReqVO queryMerchantSkuListReqVO) {
        try {
            List<MerchantSkuItemRespVO> merchantSkuItemRespVOS = productSkuDao.getMerchantSkuListByCodes(queryMerchantSkuListReqVO);
            List<ProductCategory> productCategoryList;
            if (null != merchantSkuItemRespVOS && merchantSkuItemRespVOS.size() > 0) {
                for (int i = 0; i < merchantSkuItemRespVOS.size(); i++) {
                    productCategoryList = productCategoryService.getParentCategoryList(merchantSkuItemRespVOS.get(i).getCategoryCode());
                    merchantSkuItemRespVOS.get(i).setProductCategories(productCategoryList);
                    List<ProductSkuLabelInfo> productSkuLabelInfos = productLabelDao.getLabelListBySkuCode(merchantSkuItemRespVOS.get(i).getSkuCode());
                    merchantSkuItemRespVOS.get(i).setProductLabelList(productSkuLabelInfos);
                }
            }
            return merchantSkuItemRespVOS;
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public StoreSkuListRespVO getSkuListByProductCode(QueryStoreSkuListReqVO queryStoreSkuListReqVO) {
        try {
            StoreSkuListRespVO storeSkuListRespVO = new StoreSkuListRespVO();
            List<LogisticsCenterApiResVo> logisticsCenterApiResVos = new ArrayList<>();
            //logisticsCenterApiResVos = getWarehouseList(queryStoreSkuListReqVO,logisticsCenterApiResVos);
            //queryStoreSkuListReqVO.setLogisticsCenterApiResVoList(logisticsCenterApiResVos);
            List<StoreSkuItemRespVO> storeSkuItemRespVOS = productSkuDao.getSkuListByProductCode(queryStoreSkuListReqVO);
            if (null != storeSkuItemRespVOS && storeSkuItemRespVOS.size() > 0) {
                //获取第一个sku详情作为初始化数据
                StoreSkuDetailRespVO storeSkuDetailRespVO = getStoreSkuDetailByCode(storeSkuItemRespVOS.get(0).getSkuCode());
                storeSkuListRespVO.setStoreSkuDetailRespVO(storeSkuDetailRespVO);
                storeSkuItemRespVOS = getSkuWarehouseStockNumList(queryStoreSkuListReqVO, storeSkuItemRespVOS);
            }
            storeSkuListRespVO.setStoreSkuItemRespVOList(storeSkuItemRespVOS);
            return storeSkuListRespVO;
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 根据门店省市查询所属仓库库存数量
     *
     * @param queryStoreSkuListReqVO
     * @param storeSkuItemRespVOS
     * @return
     */
    public List<StoreSkuItemRespVO> getSkuWarehouseStockNumList(QueryStoreSkuListReqVO queryStoreSkuListReqVO, List<StoreSkuItemRespVO> storeSkuItemRespVOS) {
        SkuWarehouseStockNum skuWarehouseStockNum = new SkuWarehouseStockNum();
        skuWarehouseStockNum.setStoreSkuItemRespVOList(storeSkuItemRespVOS);
        skuWarehouseStockNum.setLogisticsCenterApiResVoList(queryStoreSkuListReqVO.getWareHouses());
        List<SkuWarehouseStockNum> skuWarehouseStockNums = productSkuDao.getSkuWarehouseStockNum(skuWarehouseStockNum);
        for (int i = 0; i < storeSkuItemRespVOS.size(); i++) {
            StoreSkuItemRespVO storeSkuItemRespVO = storeSkuItemRespVOS.get(i);
            for (int j = 0; j < skuWarehouseStockNums.size(); j++) {
                if (storeSkuItemRespVO.getSkuCode().equals(skuWarehouseStockNums.get(j).getSkuCode())) {
                    List<StoreAvailableStockItem> storeAvailableStockItems = storeSkuItemRespVO.getStoreAvailableStockItemList();
                    StoreAvailableStockItem storeAvailableStockItem = new StoreAvailableStockItem();
                    storeAvailableStockItem.setStockQuantity(skuWarehouseStockNums.get(j).getInventoryNum());
                    storeAvailableStockItem.setWarehouseName(skuWarehouseStockNums.get(j).getWarehouseName());
                    if (null != storeAvailableStockItems) {
                        storeSkuItemRespVOS.get(i).getStoreAvailableStockItemList().add(storeAvailableStockItem);
                    } else {
                        List<StoreAvailableStockItem> storeAvailableStockItems1 = new ArrayList<>();
                        storeAvailableStockItems1.add(storeAvailableStockItem);
                        storeSkuItemRespVOS.get(i).setStoreAvailableStockItemList(storeAvailableStockItems1);
                    }
                }
            }
        }
        return storeSkuItemRespVOS;
    }

    /*public List<LogisticsCenterApiResVo> getWarehouseList(QueryStoreSkuListReqVO queryStoreSkuListReqVO, List<LogisticsCenterApiResVo> warehouseRespVos){
        //赋值省市县
        WarehouseListReqVo warehouseListReqVo = new WarehouseListReqVo();
        warehouseListReqVo.setCityCode(queryStoreSkuListReqVO.getCityId());
        warehouseListReqVo.setCountyCode(queryStoreSkuListReqVO.getDistrictId());
        warehouseListReqVo.setProvinceCode(queryStoreSkuListReqVO.getProvinceId());
        StringBuilder sb = new StringBuilder();
        sb.append(urlConfig.SUPPLIER_API_URL).append("/warehouse/getWarehouseApi");
        HttpClient httpClient = HttpClientHelper.getCurrentClientEx(HttpClient.post(sb.toString()).json(warehouseListReqVo));
        HttpResponse result = httpClient.action().result(HttpResponse.class);
        if (Objects.nonNull(result) ) {
            warehouseRespVos = (List<LogisticsCenterApiResVo>)result.getData();
        }
        return warehouseRespVos;
    }*/

    @Override
    public StoreSkuDetailRespVO getStoreSkuDetailByCode(String skuCode) {
        try {
            StoreSkuDetailRespVO storeSkuDetailRespVO = new StoreSkuDetailRespVO();
            StoreSkuBaseInfo storeSkuBaseInfo = productSkuDao.getStoreSkuBaseByCode(skuCode);
            if (null != storeSkuBaseInfo) {
                storeSkuDetailRespVO.setStoreSkuBaseInfo(storeSkuBaseInfo);
                StoreSkuFactoryInfo storeSkuFactoryInfo = productSkuDao.getDefaultSkuFactory(skuCode);
                storeSkuDetailRespVO.setStoreSkuFactoryInfo(storeSkuFactoryInfo);
                StoreSkuSalesInfo storeSkuSalesInfo = productSkuDao.getDefaultSkuSales(skuCode);
                storeSkuDetailRespVO.setStoreSkuSalesInfo(storeSkuSalesInfo);
                List<StoreSkuPicInfo> storeSkuPicInfos = productSkuDao.getSkuPicList(skuCode);
                storeSkuDetailRespVO.setStoreSkuPicInfo(storeSkuPicInfos);
            }
            return storeSkuDetailRespVO;
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public BasePage<ProductItemStoreRespVO> getStoreProductItem(QueryStoreProductListReqVO queryStoreProductListReqVO) {
        try {
            //QueryStoreSkuListReqVO queryStoreSkuListReqVO = new QueryStoreSkuListReqVO();
//            queryStoreSkuListReqVO.setCityId(queryStoreProductListReqVO.getCityId());
//            queryStoreSkuListReqVO.setDistrictId(queryStoreProductListReqVO.getDistrictId());
//            queryStoreSkuListReqVO.setProvinceId(queryStoreProductListReqVO.getProvinceId());
            List<WarehouseApiResVo> warehouseApiResVos = new ArrayList<>();
            if(null != queryStoreProductListReqVO.getLogisticsCenterApiResVos() && queryStoreProductListReqVO.getLogisticsCenterApiResVos().size() > 0){
                for (int i= 0; i<queryStoreProductListReqVO.getLogisticsCenterApiResVos().size();i++){
                    LogisticsCenterApiResVo logisticsCenterApiResVo = queryStoreProductListReqVO.getLogisticsCenterApiResVos().get(i);
                    if (null != logisticsCenterApiResVo.getList() && logisticsCenterApiResVo.getList().size() > 0){
                        logisticsCenterApiResVo.getList().forEach(item->{
                            warehouseApiResVos.add(item);
                        });
                    }
                }
            } else {
                throw new BizException("对应物流中心不存在");
            }
            QueryStoreProductListReqDTO queryStoreProductListReqDTO = new QueryStoreProductListReqDTO();
            BeanCopyUtils.copy(queryStoreProductListReqVO,queryStoreProductListReqDTO);
            queryStoreProductListReqDTO.setWarehouseApiResVos(warehouseApiResVos);
            PageHelper.startPage(queryStoreProductListReqVO.getPageNo(),queryStoreProductListReqVO.getPageSize());
            List<ProductItemStoreRespVO> productItemStoreRespVOS = productSkuDao.getProductItemList(queryStoreProductListReqDTO);
            return PageUtil.getPageList(queryStoreProductListReqVO.getPageNo(),productItemStoreRespVOS);
        } catch (Exception e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public List<StoreSkuItemRespVO> getProductDetailSkuList(QueryStoreSkuListReqVO queryStoreSkuListReqVO) {
        try {
            List<StoreSkuItemRespVO> storeSkuItemRespVOS = productSkuDao.getSkuListByProductCode(queryStoreSkuListReqVO);
            storeSkuItemRespVOS = getSkuWarehouseStockNumList(queryStoreSkuListReqVO, storeSkuItemRespVOS);
            List<ProductCategory> productCategoryList;
            if (null != storeSkuItemRespVOS && storeSkuItemRespVOS.size() > 0) {
                for (int i = 0; i < storeSkuItemRespVOS.size(); i++) {
                    productCategoryList = productCategoryService.getParentCategoryList(storeSkuItemRespVOS.get(i).getCategoryCode());
                    storeSkuItemRespVOS.get(i).setProductCategories(productCategoryList);
                }
            }
            return storeSkuItemRespVOS;
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public List<PurchaseItemRespVo> getCheckSkuList(CheckPurchaseSkuReqVO checkPurchaseSkuReqVO) {
        try {
            List<PurchaseItemRespVo> checkPurchaseItemRespVOS = productSkuDao.getCheckSkuList(checkPurchaseSkuReqVO);
            return checkPurchaseItemRespVOS;
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public List<ProductSkuResponse> selectSkuInfoListCanUseBySkuCodeList(List<String> skuCodeList) {
        List<ProductSkuResponse> productSkuInfoList = new ArrayList<>();
        try {
            LOGGER.info("查询有效的sku信息");
            productSkuInfoList = productSkuInfoDao.selectSkuInfoListCanUseBySkuCodeList(skuCodeList);
            if (CollectionUtils.isNotEmpty(productSkuInfoList)) {
                for (ProductSkuResponse productSkuResponse : productSkuInfoList) {
                    int skuStatus = productSkuResponse.getSkuStatus();
                    int delFlag = productSkuResponse.getDelFlag();
                    int onSale = productSkuResponse.getOnSale();
                    if (skuStatus != 1 && delFlag != 1 && onSale != 0) {
                        productSkuResponse.setUseStatus(Global.SKU_CAN_USE);
                    } else {
                        productSkuResponse.setUseStatus(Global.SKU_NOT_CAN_USE);
                    }
                }
            }
            return productSkuInfoList;
        } catch (Exception e) {
            throw new GroundRuntimeException(e.getMessage());
        }
    }

    @Override
    public List<PurchaseItemRespVo> getSalesSkuList(List<String> skuCodeList) {
        try {
            List<PurchaseItemRespVo> purchaseItemRespVos=productSkuDao.getSalesSkuList(skuCodeList);
            return purchaseItemRespVos;
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public Map<String, Long> getSkuConvertNumBySkuCodes(List<String> skuCodes) {
        Map<String, Long> map = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(skuCodes)) {
//            List<ProductSkuCheckout> productSkuCheckouts = productSkuCheckoutMapper.selectBySkuCodes(skuCodes);
//            productSkuCheckouts.forEach(item -> {
//                map.put(item.getSkuCode(), (null != item.getZeroRemovalCoefficient() && item.getZeroRemovalCoefficient() > 0L ? item.getZeroRemovalCoefficient() : 1L));
//            });
//            for (String i : skuCodes) {
//                map.put(i, 10L);
//            }
        }
        return map;
    }

    @Override
    public BasePage<QueryProductSkuListResp> getOmsSkuList(SearchOmsSkuListReqVO searchOmsSkuListReqVO) {
        try {
            PageHelper.startPage(searchOmsSkuListReqVO.getPageNo(), searchOmsSkuListReqVO.getPageSize());
            List<QueryProductSkuListResp> queryProductSkuListResps = productSkuDao.queryOmsSkuList(searchOmsSkuListReqVO);
            return PageUtil.getPageList(searchOmsSkuListReqVO.getPageNo(), queryProductSkuListResps);
        } catch (BizException e) {
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public List<StoreSkuItemRespVO> getStoreSkuListByCodes(QueryStoreSkusReqVO queryStoreSkusReqVO) {
        try {
            List<StoreSkuItemRespVO> storeSkuItemRespVOS = productSkuDao.getStoreSkuListByCodes(queryStoreSkusReqVO);
            return storeSkuItemRespVOS;
        } catch (BizException e) {
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public BasePage<OmsProductListItemResp> getOmsProductList(SearchOrderReqVO searchOrderReqVO) {
        try {
            PageHelper.startPage(searchOrderReqVO.getPageNo(), searchOrderReqVO.getPageSize());
            List<String> skuCodes = productSkuDao.getSkuCodesByCenter(searchOrderReqVO.getWareHouses());
            searchOrderReqVO.setSkuCodes(skuCodes);
            List<OmsProductListItemResp> omsProductListItemResps = productSkuDao.getOmsProductList(searchOrderReqVO);
            List<OmsSkuTagResp> omsSkuTagResps = productSkuDao.getOmsSkuTagList(omsProductListItemResps);
            List<OmsSkuBrandResp> omsSkuBrandResps = productSkuDao.getOmsProductBrandList(omsProductListItemResps);
            List<OmsSkuCategoryResp> omsSkuCategoryResps = productSkuDao.getOmsProductCategoryList(omsProductListItemResps);
            for (int i = 0; i<omsProductListItemResps.size();i++){
                OmsProductListItemResp productItem = omsProductListItemResps.get(i);
                Set<String> tags = new HashSet<>();
                for (int j = 0; j < omsSkuTagResps.size(); j++) {
                    OmsSkuTagResp tag = omsSkuTagResps.get(j);
                    if (productItem.getProductCode().equals(tag.getProductCode())) {
                        if (tag.getMainPush() == 1) {
                            tags.add("主推");
                        }
                        if (tag.getNewProduct() == 1) {
                            tags.add("新品");
                        }
                    }
                }
                Set<String> brands = new HashSet<>();
                for (int k = 0; k < omsSkuBrandResps.size();k++){
                    OmsSkuBrandResp brand = omsSkuBrandResps.get(k);
                    if (productItem.getProductCode().equals(brand.getProductCode())){
                        brands.add(brand.getBrandId());
                    }
                }
                Set<String> categorys = new HashSet<>();
                for (int a = 0; a < omsSkuCategoryResps.size();a++){
                    OmsSkuCategoryResp category = omsSkuCategoryResps.get(a);
                    if (productItem.getProductCode().equals(category.getProductCode())){
                        categorys.add(category.getCategoryId());
                    }
                }
                if (tags.size() > 0 ){
                    omsProductListItemResps.get(i).setTagList((new ArrayList<>(tags)));
                }
                if (brands.size() > 0){
                    omsProductListItemResps.get(i).setBrandIds((new ArrayList<>(brands)));
                }
                if (categorys.size() > 0){
                    omsProductListItemResps.get(i).setCategoryIds((new ArrayList<>(categorys)));
                }
            }
            return PageUtil.getPageList(searchOrderReqVO.getPageNo(), omsProductListItemResps);
        } catch (BizException e) {
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public List<OmsProductSkuItemResp> getOmsProductSkuList(OmsProductSkuListReq omsProductSkuListReq) {
        try {
            List<OmsProductSkuItemResp> omsProductSkuItemResps = productSkuDao.getOmsProductSkuList(omsProductSkuListReq);
            return omsProductSkuItemResps;
        } catch (BizException e) {
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public List<QuerySkuListResp> getOmsSkus(QueryStoreSkusReqVO queryStoreSkusReqVO) {
        try {
            List<QuerySkuListResp> querySkuListResps = productSkuDao.getOmsSkus(queryStoreSkusReqVO);
            List<ProductCategory> productCategoryList;
            if (null != querySkuListResps && querySkuListResps.size() > 0) {
                for (int i = 0; i < querySkuListResps.size(); i++) {
                    productCategoryList = productCategoryService.getParentCategoryList(querySkuListResps.get(i).getCategoryId());
                    List<String> strings= productCategoryList.stream().map(a->a.getCategoryId()).collect(Collectors.toList());
                    strings.add(querySkuListResps.get(i).getCategoryId());
                    querySkuListResps.get(i).setCategoryIds(strings);
                }
            }
            return querySkuListResps;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public BasePage<QueryOmsSkusPageResp> queryOmsSkuPage(QuerySkuListPageReq querySkuListPageReq) {
        try {
            PageHelper.startPage(querySkuListPageReq.getPageNo(),querySkuListPageReq.getPageSize());
            List<String> skuCodes = productSkuDao.getSkuCodesByCenter(querySkuListPageReq.getWareHouses());
            querySkuListPageReq.setSkuCodes(skuCodes);
            List<QueryOmsSkusPageResp> queryOmsSkusPageResps = productSkuDao.queryOmsSkuPage(querySkuListPageReq);
            return PageUtil.getPageList(querySkuListPageReq.getPageNo(),queryOmsSkusPageResps);
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public ProductSkuResponse selectSkuInfoBySkuCode(String skuCode) {
        try {
            ProductSkuResponse productSkuResponse = productSkuInfoDao.selectSkuInfoBySkuCode(skuCode);
            return productSkuResponse;
        } catch (BizException e) {
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public List<ProductSkuCheckout> getSkuCheckOuts(List<String> skuCodes) {
        try {
            List<ProductSkuCheckout> productSkuCheckouts = productSkuCheckoutDao.getSkuCheckOuts(skuCodes);
            return productSkuCheckouts;
        } catch (BizException e){
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 通过编码获取供货单位详情
     * @param supplyCode
     * @return
     */
    @Override
    public SupplyComDetailRespVO detailByCode(String supplyCode) {
try {
//        String url = urlConfig.SUPPLIER_API_URL+"/supplier/company/detailByCode?authority=0";
//        HttpClient httpClient = HttpClientHelper.getCurrentClient(HttpClient.get(url).addParameter("supplyCode",supplyCode));
//        HttpResponse orderDto = httpClient.action().result(HttpResponse.class);
//        String hello = JSON.toJSONString( orderDto.getData());
//        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
//        SupplyComDetailRespVO supplyComDetailRespVO = mapper.readValue(hello, SupplyComDetailRespVO.class);
        return null;

    }catch (Exception e){
        throw new RuntimeException("查询失败");
    }
    }

    /**
     * 运营中心查询SKU列表信息
     *
     * @param querySkuListReqVo
     */
    @Override
    public BasePage<QueryCenterSkuListRespVo> queryOcenterSkuList(QueryCenterSkuListReqVo querySkuListReqVo) {
        if(StringUtils.isBlank(querySkuListReqVo.getCompanyCode())){
            throw new BizException(ResultCode.COMPANY_CODE_EMPTY);
        }
        if(CollectionUtils.isEmpty(querySkuListReqVo.getWareHouses())){
            throw new BizException(ResultCode.LOGISTICS_CENTER_CODE_EMPTY);
        }
        //重新组装categoryId
        if (CollectionUtils.isNotEmpty(querySkuListReqVo.getProductProperties())) {
            querySkuListReqVo.getProductProperties().forEach(item->{
                String categoryId = item.getCategoryId();
                if(StringUtils.isNotBlank(categoryId)){
                    List<ProductCategory> childCategoryList = productCategoryService.getChildCategoryList(categoryId, querySkuListReqVo.getCompanyCode());
                    categoryId =  childCategoryList.stream().map(ProductCategory :: getCategoryId).collect(Collectors.joining(","));
                    item.setCategoryId(categoryId);
                }
            });
        }
        List<QueryCenterSkuListRespVo> respVos = productSkuDao.queryOcenterSkuList(querySkuListReqVo);

        return PageUtil.getPageList(querySkuListReqVo.getPageNo(),respVos);
    }
}
