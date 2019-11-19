package com.aiqin.bms.scmp.api.abutment.service.impl;

import com.aiqin.bms.scmp.api.abutment.domain.conts.ScmpOrderEnum;
import com.aiqin.bms.scmp.api.abutment.domain.conts.ScmpStorageChangeEnum;
import com.aiqin.bms.scmp.api.abutment.domain.conts.StringConvertUtil;
import com.aiqin.bms.scmp.api.abutment.domain.request.*;
import com.aiqin.bms.scmp.api.abutment.domain.response.StockResponse;
import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.QueryPriceChannelReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.QueryPriceChannelRespVo;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.response.InnerValue;
import com.aiqin.bms.scmp.api.purchase.mapper.*;
import com.aiqin.bms.scmp.api.supplier.dao.contract.ContractDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompanyAccount;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.dto.ContractDTO;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplyCompanyAccountMapper;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@Service
public class SapBaseDataServiceImpl implements SapBaseDataService {

    private static Logger LOGGER = LoggerFactory.getLogger(SapBaseDataServiceImpl.class);
    private static Integer SAP_API_COUNT = 10;
    @Value("${sap.order}")
    private String ORDER_URL;
    @Value("${sap.product}")
    private String PRODUCT_URL;
    @Value("${sap.supply}")
    private String SUPPLY_URL;
    @Value("${sap.purchase}")
    private String PURCHASE_URL;
    @Value("${sap.storage}")
    private String STORAGE_URL;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private OrderInfoItemMapper orderInfoItemMapper;
    @Resource
    private OrderInfoItemProductBatchMapper orderInfoItemProductBatchMapper;
    @Resource
    private ReturnOrderInfoMapper returnOrderInfoMapper;
    @Resource
    private ReturnOrderInfoItemMapper returnOrderInfoItemMapper;
    @Resource
    private InboundDao inboundDao;
    @Resource
    private InboundProductDao inboundProductDao;
    @Resource
    private InboundBatchDao inboundBatchDao;
    @Resource
    private OutboundDao outboundDao;
    @Resource
    private OutboundProductDao outboundProductDao;
    @Resource
    private OutboundBatchDao outboundBatchDao;
    @Resource
    private ProductSkuDao productSkuDao;
    @Resource
    private AllocationMapper allocationMapper;
    @Resource
    private PurchaseOrderDao purchaseOrderDao;
    @Resource
    private PurchaseOrderProductDao purchaseOrderProductDao;
    @Resource
    private PurchaseOrderDetailsDao purchaseOrderDetailsDao;
    @Resource
    private RejectRecordDao rejectRecordDao;
    @Resource
    private RejectRecordDetailDao rejectRecordDetailDao;
    @Resource
    private StockDao stockDao;
    @Resource
    private SupplyCompanyDao supplyCompanyDao;
    @Resource
    private ProfitLossMapper profitLossMapper;
    @Resource
    private ProfitLossProductMapper profitLossProductMapper;
    @Resource
    private ProfitLossProductBatchMapper profitLossProductBatchMapper;
    @Resource
    private PriceChannelMapper priceChannelMapper;
    @Resource
    private ProductSkuCheckoutMapper productSkuCheckoutMapper;
    @Resource
    private SupplyCompanyAccountMapper supplyCompanyAccountMapper;
    @Resource
    private ContractDao contractDao;

    /**
     * 商品数据同步
     */
    public void productSynchronization(SapOrderRequest sapOrderRequest) {
        List<ProductSkuInfo> productSkuInfoList = productSkuDao.listForSap(sapOrderRequest);

        if (CollectionUtils.isNotEmpty(productSkuInfoList)) {
            int total = (int) Math.ceil(productSkuInfoList.size() / (SAP_API_COUNT * 1.0));
            int endIndex;
            List<ProductSkuInfo> subLists;
            List<SapSkuSale> baseInfo2;
            SapProductSku sapProductSku;
            SapProductSkuBase baseInfo;
            SapSkuStorageFinancial sapSkuStorageFinancial;
            List<SapSkuStorageFinancial> sapSkuStorageFinancialList;
            List<String> skuCodes;
            List<StockResponse> stockList;
            //查询所有渠道
            List<ProductSkuCheckout> skuCheckoutList;
            Map<String, ProductSkuCheckout> skuCheckoutMap;
            ProductSkuCheckout productSkuCheckout;
            List<QueryPriceChannelRespVo> priceChannels = priceChannelMapper.getList(new QueryPriceChannelReqVo());
            List<SapProductSku> productSkuList = Lists.newArrayList();
            for (int i = 0; i < total; i++) {
                endIndex = SAP_API_COUNT * (i + 1);
                if (SAP_API_COUNT * (i + 1) >= productSkuInfoList.size()) {
                    endIndex = productSkuInfoList.size();
                }
                subLists = productSkuInfoList.subList(SAP_API_COUNT * i, endIndex);
                skuCodes = subLists.stream().map(ProductSkuInfo::getProductCode).collect(Collectors.toList());
                skuCheckoutList = productSkuCheckoutMapper.listForSap(skuCodes);
                skuCheckoutMap = skuCheckoutList.stream().collect(Collectors.toMap(ProductSkuCheckout::getSkuCode, Function.identity()));
                stockList = stockDao.listBySkuCodes(skuCodes);
                Map<String, List<SapSkuStorageFinancial>> SapSkuStorageFinancialMap = Maps.newHashMap();
                for (StockResponse stock : stockList) {
                    sapSkuStorageFinancial = new SapSkuStorageFinancial();
                    sapSkuStorageFinancial.setPriceControlTag("V");
                    sapSkuStorageFinancial.setEvaluateCategory(stock.getProductCategoryCode().substring(0, 2));
                    sapSkuStorageFinancial.setDynamicAvgPrice(stock.getTaxCost().toString());
                    sapSkuStorageFinancial.setCheckGroup("KP");
                    sapSkuStorageFinancial.setFactoryCode(stock.getTransportCenterCode());
                    sapSkuStorageFinancial.setStorageLocationCode(stock.getWarehouseCode());
                    sapSkuStorageFinancial.setPriceUnit("1");
                    sapSkuStorageFinancial.setPurchaseGroupCode(stock.getProcurementSectionCode());
                    //存入map中
                    if (SapSkuStorageFinancialMap.containsKey(stock.getSkuCode())) {
                        sapSkuStorageFinancialList = SapSkuStorageFinancialMap.get(stock.getSkuCode());
                        sapSkuStorageFinancialList.add(sapSkuStorageFinancial);
                        SapSkuStorageFinancialMap.put(stock.getSkuCode(), sapSkuStorageFinancialList);
                    } else {
                        sapSkuStorageFinancialList = Lists.newArrayList();
                        sapSkuStorageFinancialList.add(sapSkuStorageFinancial);
                        SapSkuStorageFinancialMap.put(stock.getSkuCode(), sapSkuStorageFinancialList);
                    }
                }
                for (ProductSkuInfo productSkuInfo : subLists) {
                    baseInfo2 = Lists.newArrayList();
                    sapProductSku = new SapProductSku();
                    sapProductSku.setSapSkuCode(productSkuInfo.getProductCode());
                    sapProductSku.setIndustryDepartmentCode("A");
                    //取一级品类
                    sapProductSku.setCategoryCode(productSkuInfo.getProductCategoryCode().substring(0, 2));
                    sapProductSku.setSkuName(productSkuInfo.getProductName());
                    //基本视图
                    baseInfo = new SapProductSkuBase();
                    baseInfo.setUnit("EA");
                    baseInfo.setProductGroupCode(productSkuInfo.getProductCategoryCode().substring(0, 2));
                    baseInfo.setGroupCode(productSkuInfo.getProductCategoryCode());
                    baseInfo.setWeight("KG");
                    baseInfo.setStandard(productSkuInfo.getSpec());
                    sapProductSku.setBaseInfo(baseInfo);
                    sapProductSku.setBaseInfo1(SapSkuStorageFinancialMap.get(productSkuInfo.getSkuCode()));
                    productSkuCheckout = skuCheckoutMap.get(productSkuInfo.getProductCode());
                    sapProductSku.setBaseInfo2(sapSkuSaleListHandler(productSkuCheckout, baseInfo2, priceChannels));
                    productSkuList.add(sapProductSku);
                }
                sapStorageAbutment(productSkuList);
                productSkuList.clear();
            }
        }
    }


    private List<SapSkuSale> sapSkuSaleListHandler(ProductSkuCheckout productSkuCheckout, List<SapSkuSale> baseInfo2, List<QueryPriceChannelRespVo> priceChannels) {
        SapSkuSale sapSkuSale;
        for (QueryPriceChannelRespVo priceChannel : priceChannels) {
            sapSkuSale = new SapSkuSale();
            sapSkuSale.setSaleOrg(priceChannel.getPriceChannelCode());
            sapSkuSale.setSubjectGroupCode("14");
            sapSkuSale.setMainCategoryCode("NORM");
            sapSkuSale.setTaxCategoryCode(Objects.isNull(productSkuCheckout)?"0":productSkuCheckout.getInputTaxRate().toString());
            sapSkuSale.setDistributionChannelCode("00");
            baseInfo2.add(sapSkuSale);
        }
        return baseInfo2;
    }

    /**
     * sap商品对接
     *
     * @param productSkuList
     */
    private void sapStorageAbutment(List<SapProductSku> productSkuList) {
        LOGGER.info("调用sap商品对接参数:{} ", JsonUtil.toJson(productSkuList));
        HttpClient client = HttpClient.post(PRODUCT_URL).json(productSkuList).timeout(10000);
        HttpResponse httpResponse = client.action().result(HttpResponse.class);
        if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
            LOGGER.info("调用sap商品对接成功:{}", httpResponse.getMessage());
        } else {
            LOGGER.error("调用sap商品对接异常:{}", httpResponse.getMessage());
            throw new GroundRuntimeException(String.format("调用sap商品对接异常:%s", httpResponse.getMessage()));
        }
    }


    /**
     * 供应商数据同步
     */
    @Override
    public void supplySynchronization(SapOrderRequest sapOrderRequest) {
        List<SupplyCompany> supplierList = supplyCompanyDao.listForSap(sapOrderRequest);
        if (CollectionUtils.isNotEmpty(supplierList)) {
            int total = (int) Math.ceil(supplierList.size() / (SAP_API_COUNT * 1.0));
            int endIndex;
            List<SapSupplier> sapSupplierList;
            List<String> supplyCodes;
            List<ContractDTO> contracts;
            List<SupplyCompany> subLists;
            List<ContractDTO> contractList;
            List<SupplierCompany> companyList;
            List<SupplierBank> supplierBankList;
            List<SupplyCompanyAccount> accountList;
            List<SupplyCompanyAccount> supplyCompanyAccount;
            SupplierBank supplierBank;
            SupplierCompany supplierCompany;
            SapSupplier sapSupplier;
            SupplierPurchase supplierPurchase;
            Map<String, List<SupplyCompanyAccount>> accountMap = Maps.newHashMap();
            List<SupplierPurchase> supplierPurchases;
            for (int i = 0; i < total; i++) {
                endIndex = SAP_API_COUNT * (i + 1);
                if (SAP_API_COUNT * (i + 1) >= supplierList.size()) {
                    endIndex = supplierList.size();
                }
                sapSupplierList = Lists.newArrayList();
                subLists = supplierList.subList(SAP_API_COUNT * i, endIndex);
                supplyCodes = subLists.stream().map(SupplyCompany::getSupplyCode).collect(Collectors.toList());
                sapOrderRequest.setOrderCodeList(supplyCodes);
                accountList = supplyCompanyAccountMapper.listForSap(sapOrderRequest);
                if (CollectionUtils.isNotEmpty(accountList)) {
                    accountMap = accountList.stream().collect(Collectors.groupingBy(SupplyCompanyAccount::getSupplyCompanyAccountCode));
                }
                Map<String, List<ContractDTO>> contractMap = Maps.newHashMap();
                contractList = contractDao.listForSap(sapOrderRequest);
                if (CollectionUtils.isNotEmpty(contractList)) {
                    contractMap = contractList.stream().collect(Collectors.groupingBy(ContractDTO::getSupplierCode));
                }

                for (SupplyCompany supplyCompany : supplierList) {
                    sapSupplier = new SapSupplier();
                    sapSupplier.setFlag("M");
                    sapSupplier.setSupplierCode(supplyCompany.getSupplyCode());
                    sapSupplier.setSupplierName(supplyCompany.getSupplyName());
                    sapSupplier.setSupplierGroupCode("Z001");
                    sapSupplier.setTaxNo(supplyCompany.getTaxId());
                    supplyCompanyAccount = accountMap.get(supplyCompany.getSupplyCode());
                    if (CollectionUtils.isNotEmpty(supplyCompanyAccount)) {
                        supplierBankList = Lists.newArrayList();
                        for (SupplyCompanyAccount companyAccount : supplyCompanyAccount) {
                            //供应商银行数据
                            supplierBank = new SupplierBank();
                            supplierBank.setFlag("M");
                            supplierBank.setBankName(companyAccount.getBankAccount());
                            supplierBank.setBankCountryNo("CN");
                            supplierBank.setBankNo(companyAccount.getAccount());
                            supplierBankList.add(supplierBank);
                        }
                        sapSupplier.setBanks(supplierBankList);
                    }
                    contracts = contractMap.get(supplyCompany.getSupplyCode());
                    if (CollectionUtils.isNotEmpty(contracts)) {
                        companyList = Lists.newArrayList();
                        supplierPurchases = Lists.newArrayList();
                        for (ContractDTO contract : contracts) {
                            //供应商公司数据
                            supplierCompany = new SupplierCompany();
                            supplierPurchase = new SupplierPurchase();
                            supplierCompany.setCompanyCode("1000");
                            supplierCompany.setFlag("M");
                            //合同里的供应商付款条件
                            supplierCompany.setPayConditionCode(contract.getSettlementMethod().toString());
                            companyList.add(supplierCompany);
                            //供应商采购数据
                            supplierPurchase.setFlag("M");
                            supplierPurchase.setPurchaseOrg("1000");
                            supplierPurchase.setPayConditionCode(contract.getSettlementMethod().toString());
                            supplierPurchase.setCurrencyCode("CNY");
                            supplierPurchase.setReceiptTag("X");
                            supplierPurchases.add(supplierPurchase);
                        }
                        sapSupplier.setCompanyList(companyList);
                        sapSupplier.setPurchaseList(supplierPurchases);
                    }
                    sapSupplierList.add(sapSupplier);
                }
                sapSupplyAbutment(sapSupplierList);
            }
        }
    }

    /**
     * sap供应商对接
     *
     * @param sapSupplierList
     */
    private void sapSupplyAbutment(List<SapSupplier> sapSupplierList) {
        LOGGER.info("调用sap供应商对接参数:{} ", JsonUtil.toJson(sapSupplierList));
        LOGGER.info("对接条数:{} ", sapSupplierList.size());
        HttpClient client = HttpClient.post(SUPPLY_URL).json(sapSupplierList).timeout(10000);
        HttpResponse httpResponse = client.action().result(HttpResponse.class);
        if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
            LOGGER.info("调用sap供应商对接成功:{}", httpResponse.getMessage());
        } else {
            LOGGER.error("调用sap供应商对接异常:{}", httpResponse.getMessage());
            throw new GroundRuntimeException(String.format("调用sap供应商对接异常:%s", httpResponse.getMessage()));
        }
    }


    /**
     * sap对接报损单转换为出入库单
     *
     * @param sapOrderRequest
     */
    private void sapProfitLossStock(List<Storage> storageList, SapOrderRequest sapOrderRequest) {
        List<ProfitLoss> profitLossList = profitLossMapper.listForSap(sapOrderRequest);
        if (CollectionUtils.isNotEmpty(profitLossList)) {
            List<String> orderCodes = profitLossList.stream().map(ProfitLoss::getOrderCode).collect(Collectors.toList());
            sapOrderRequest.setOrderCodeList(orderCodes);
            List<ProfitLossProduct> profitLossProductList = profitLossProductMapper.listForSap(sapOrderRequest);
            Map<String, ProfitLossProduct> profitLossProductMap = profitLossProductList.stream().collect(Collectors.toMap(profitLossProduct -> profitLossProduct.getOrderCode() + profitLossProduct.getSkuCode(), Function.identity()));
            List<String> skuCodes = profitLossProductList.stream().map(ProfitLossProduct::getSkuCode).collect(Collectors.toList());
            ProfitLossProduct profitLossProduct;
            List<ProfitLossProductBatch> profitLossProductBatches = profitLossProductBatchMapper.listForSap(sapOrderRequest);
            StorageDetail storageDetail;
            List<StorageDetail> storageDetailList;
            List<ProductSkuInfo> productSkuList = productSkuDao.getSkuInfoByCodeList(skuCodes);
            Map<String, Long> productMap = productSkuList.stream().collect(Collectors.toMap(ProductSkuInfo::getSkuCode, ProductSkuInfo::getManufacturerGuidePrice));
            Map<String, List<StorageDetail>> storageDetailMap = new HashMap<>();
            Storage storage;
            for (ProfitLossProductBatch profitLossProductBatch : profitLossProductBatches) {
                profitLossProduct = profitLossProductMap.get(profitLossProductBatch.getOrderCode() + profitLossProductBatch.getSkuCode());
                storageDetail = new StorageDetail();
                //供应商
                storageDetail.setSupplierCode(profitLossProductBatch.getSupplierCode());
                storageDetail.setSupplierName(profitLossProductBatch.getSupplierName());
                storageDetail.setSkuCode(profitLossProduct.getSkuCode());
                storageDetail.setSkuName(profitLossProduct.getSkuName());
                storageDetail.setSkuDesc(StringConvertUtil.productDesc(profitLossProduct.getColor(), profitLossProduct.getSpecification(), profitLossProduct.getModel()));
                storageDetail.setUnit(profitLossProduct.getUnit());
                //固定为1
                storageDetail.setUnitCount(1);
                storageDetail.setTradeExponent(1);
                storageDetail.setTaxRate(profitLossProduct.getTax().intValue());
                storageDetail.setExpectCount(profitLossProduct.getQuantity().intValue());
                storageDetail.setExpectMinUnitCount(profitLossProduct.getQuantity().intValue());
                storageDetail.setSingleCount(profitLossProduct.getQuantity().intValue());
                storageDetail.setMinUnitCount(profitLossProduct.getQuantity().intValue());
                //厂商指导价
                storageDetail.setGuidePrice(productMap.containsKey(profitLossProduct.getSkuCode()) ? productMap.get(profitLossProduct.getSkuCode()).toString() : "0");
                if (storageDetailMap.containsKey(profitLossProduct.getOrderCode())) {
                    storageDetailList = storageDetailMap.get(profitLossProduct.getOrderCode());
                    storageDetailList.add(storageDetail);
                    storageDetailMap.put(profitLossProduct.getOrderCode(), storageDetailList);
                } else {
                    storageDetailList = Lists.newArrayList();
                    storageDetailList.add(storageDetail);
                    storageDetailMap.put(profitLossProduct.getOrderCode(), storageDetailList);
                }
            }
            InnerValue innerValue;
            Long quantity;
            for (ProfitLoss profitLoss : profitLossList) {
                storage = new Storage();
                innerValue = StringConvertUtil.profitLossStockTypeConvert(profitLoss.getOrderType());
                storage.setOrderId(String.format("%s-%s", profitLoss.getOrderCode(), innerValue.getValue()));
                storage.setSubOrderType(innerValue.getValue());
                storage.setSubOrderTypeName(innerValue.getName());
                storage.setOrderCode(profitLoss.getOrderCode());
                //0:报损 1:报溢
                if (0 == profitLoss.getOrderType()) {
                    quantity = profitLoss.getLossQuantity();
                } else if (1 == profitLoss.getOrderType()) {
                    quantity = profitLoss.getProfitQuantity();
                } else {
                    quantity = 0L;
                }
                storage.setOrderCount(quantity.intValue());
                storage.setDiscountPrice("0");
                storage.setOptTime(profitLoss.getCreateTime());
                storage.setCreateTime(profitLoss.getCreateTime());
                storage.setCreateByName(profitLoss.getCreateBy());
                //调拨没有来源没有金额
                storage.setTransportCode(profitLoss.getLogisticsCenterCode());
                storage.setTransportName(profitLoss.getLogisticsCenterName());
                storage.setStorageCode(profitLoss.getWarehouseCode());
                storage.setStorageName(profitLoss.getWarehouseName());
                storage.setDetails(storageDetailMap.get(profitLoss.getOrderCode()));
                storageList.add(storage);
            }

        }
    }


    /**
     * 出入库数据同步
     */
    public void stockSynchronization(SapOrderRequest sapOrderRequest) {
        List<Storage> storageList = Lists.newArrayList();
        this.inboundToStock(storageList, sapOrderRequest);
        sapStorageAbutment(storageList, 1);
        storageList.clear();
        this.outboundToStock(storageList, sapOrderRequest);
        sapStorageAbutment(storageList, 2);
        this.sapProfitLossStock(storageList, sapOrderRequest);
        sapStorageAbutment(storageList, 3);
    }

    private void sapStorageAbutment(List<Storage> storageList, Integer type) {
        LOGGER.info("调用结算sap出入库单据参数:{} ", JsonUtil.toJson(storageList));
        LOGGER.info("type:{}", type);
        int total = (int) Math.ceil(storageList.size() / (SAP_API_COUNT * 1.0));
        int endIndex;
        List<Storage> subLists;
        List<String> orderCodes;
        for (int i = 0; i < total; i++) {
            endIndex = SAP_API_COUNT * (i + 1);
            if (SAP_API_COUNT * (i + 1) >= storageList.size()) {
                endIndex = storageList.size();
            }
            subLists = storageList.subList(SAP_API_COUNT * i, endIndex);
            HttpClient client = HttpClient.post(STORAGE_URL).json(subLists).timeout(10000);
            HttpResponse httpResponse = client.action().result(HttpResponse.class);
            if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
                LOGGER.info("调用结算sap出入库单据成功:{}", httpResponse.getMessage());
                //1 销售出库单 2 销售退货 更新同步状态
                orderCodes = subLists.stream().map(Storage::getOrderCode).collect(Collectors.toList());
                if (type.equals(1)) {
                    inboundDao.updateByOrderCodes(orderCodes);
                } else if (type.equals(2)) {
                    outboundDao.updateByOrderCodes(orderCodes);
                } else if (type.equals(3)) {
                    profitLossMapper.updateByOrderCodes(orderCodes);
                }
            } else {
                LOGGER.error("调用结算sap出入库单据异常:{}", httpResponse.getMessage());
                throw new GroundRuntimeException(String.format("调用结算sap出入库单据异常:%s", httpResponse.getMessage()));
            }
        }
    }

    /**
     * 出库单转结算sap对接
     *
     * @param sapOrderRequest
     */
    private void inboundToStock(List<Storage> storageList, SapOrderRequest sapOrderRequest) {
        Storage storage;
        List<Inbound> inboundList = inboundDao.listForSap(sapOrderRequest);
        if (CollectionUtils.isNotEmpty(inboundList)) {
            Map<String, Inbound> inboundMap = inboundList.stream().collect(Collectors.toMap(Inbound::getInboundOderCode, Function.identity()));
            List<String> orderCodes = inboundList.stream().map(Inbound::getInboundOderCode).collect(Collectors.toList());
            List<String> inboundCodes = inboundList.stream().filter(inbound -> inbound.getInboundTypeCode().equals(InboundTypeEnum.ALLOCATE.getCode()) || inbound.getInboundTypeCode().equals(InboundTypeEnum.MOVEMENT.getCode())).map(Inbound::getInboundOderCode).collect(Collectors.toList());
            sapOrderRequest.setOrderCodeList(orderCodes);
            //调拨出入库单据使用
            Map<String, Allocation> allocationMap = Maps.newHashMap();
            if (CollectionUtils.isNotEmpty(inboundCodes)) {
                List<Allocation> allocations = allocationMapper.listByInboundCodes(inboundCodes);
                allocationMap = allocations.stream().collect(Collectors.toMap(Allocation::getInboundOderCode, Function.identity()));
            }
            List<InboundProduct> inboundProducts = inboundProductDao.listDetailForSap(sapOrderRequest);
            List<InboundBatch> batchList = inboundBatchDao.listByOrderCode(sapOrderRequest);
            List<String> skuCodes = inboundProducts.stream().map(InboundProduct::getSkuCode).collect(Collectors.toList());
            List<ProductSkuInfo> productSkuList = productSkuDao.getSkuInfoByCodeList(skuCodes);
            Map<String, Long> productMap = productSkuList.stream().collect(Collectors.toMap(ProductSkuInfo::getSkuCode, ProductSkuInfo::getManufacturerGuidePrice));
            StorageDetail storageDetail;
            List<StorageDetail> storageDetailList;
            InboundProduct inboundProduct;
            Inbound inbounds;
            Map<String, InboundProduct> inboundProductMap = inboundProducts.stream().collect(Collectors.toMap(inboundProduct1 -> inboundProduct1.getInboundOderCode() + inboundProduct1.getSkuCode(), Function.identity()));
            Map<String, List<StorageDetail>> storageDetailMap = new HashMap<>();
            for (InboundBatch batch : batchList) {
                inboundProduct = inboundProductMap.get(batch.getInboundOderCode() + batch.getSkuCode());
                storageDetail = new StorageDetail();
                //供应商
                storageDetail.setSupplierCode(batch.getSupplierCode());
                storageDetail.setSupplierName(batch.getSupplierName());
                storageDetail.setSkuCode(inboundProduct.getSkuCode());
                storageDetail.setSkuName(inboundProduct.getSkuName());
                storageDetail.setSkuDesc(StringConvertUtil.productDesc(inboundProduct.getColorName(), inboundProduct.getNorms(), inboundProduct.getModel()));
                storageDetail.setUnit(inboundProduct.getUnitName());
                //固定为1
                storageDetail.setUnitCount(1);
                storageDetail.setTradeExponent(1);
                storageDetail.setTaxRate(inboundProduct.getTax().intValue());
                storageDetail.setExpectCount(inboundProduct.getPreInboundNum().intValue());
                storageDetail.setExpectMinUnitCount(inboundProduct.getPreInboundNum().intValue());
                storageDetail.setSingleCount(inboundProduct.getPraInboundNum().intValue());
                storageDetail.setMinUnitCount(inboundProduct.getPraInboundNum().intValue());
                //退货和采购才有金额
                inbounds = inboundMap.get(batch.getInboundOderCode());
                if ((null != inbounds) && (inbounds.getInboundTypeCode().equals(InboundTypeEnum.ORDER.getCode()) || inbounds.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode()))) {
                    storageDetail.setExpectTaxPrice(inboundProduct.getPreTaxPurchaseAmount().intValue());
                    storageDetail.setTaxPrice(inboundProduct.getPraTaxPurchaseAmount().intValue());
                }
                //厂商指导价
                storageDetail.setGuidePrice(productMap.containsKey(inboundProduct.getSkuCode()) ? productMap.get(inboundProduct.getSkuCode()).toString() : "0");
                if (storageDetailMap.containsKey(inboundProduct.getInboundOderCode())) {
                    storageDetailList = storageDetailMap.get(inboundProduct.getInboundOderCode());
                    storageDetailList.add(storageDetail);
                    storageDetailMap.put(inboundProduct.getInboundOderCode(), storageDetailList);
                } else {
                    storageDetailList = Lists.newArrayList();
                    storageDetailList.add(storageDetail);
                    storageDetailMap.put(inboundProduct.getInboundOderCode(), storageDetailList);
                }
            }
            InnerValue innerValue;
            InnerValue innerValueType;
            Allocation allocation;
            for (Inbound inbound : inboundList) {
                storage = new Storage();
                innerValue = StringConvertUtil.inboundTypeConvert(inbound.getInboundTypeCode());
                storage.setOrderId(String.format("%s-%s", inbound.getInboundOderCode(), innerValue.getValue()));
                storage.setSubOrderType(innerValue.getValue());
                storage.setSubOrderTypeName(innerValue.getName());
                storage.setOrderCode(inbound.getInboundOderCode());
                storage.setOrderCount(inbound.getPraInboundNum().intValue());
                storage.setDiscountPrice("0");
                storage.setOptTime(inbound.getInboundTime());
                storage.setCreateTime(inbound.getCreateTime());
                storage.setCreateByName(inbound.getCreateBy());
                //采购和退货订单才传来源类型 才有金额
                if (inbound.getInboundTypeCode().equals(InboundTypeEnum.ORDER.getCode()) || inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())) {
                    innerValueType = StringConvertUtil.inboundSourceTypeConvert(inbound.getInboundTypeCode());
                    storage.setSourceOrderId(String.format("%s-%s", inbound.getSourceOderCode(), innerValueType.getValue()));
                    storage.setSourceOrderCode(inbound.getSourceOderCode());
                    storage.setSourceOrderType(innerValueType.getValue());
                    storage.setSourceOrderTypeName(innerValueType.getValue());
                    storage.setAmount(inbound.getPraTaxAmount().toString());
                    storage.setTransportCode(inbound.getLogisticsCenterCode());
                    storage.setTransportName(inbound.getLogisticsCenterName());
                    storage.setStorageCode(inbound.getWarehouseCode());
                    storage.setStorageName(inbound.getWarehouseName());
                } else if (inbound.getInboundTypeCode().equals(InboundTypeEnum.ALLOCATE.getCode()) || inbound.getInboundTypeCode().equals(InboundTypeEnum.MOVEMENT.getCode())) {
                    // 调拨单据 传调出调入仓库
                    allocation = allocationMap.get(inbound.getInboundOderCode());
                    if (null != allocation) {
                        //调出
                        storage.setTransportCode(allocation.getCallOutLogisticsCenterCode());
                        storage.setTransportName(allocation.getCallOutLogisticsCenterName());
                        storage.setStorageCode(allocation.getCallOutWarehouseCode());
                        storage.setStorageName(allocation.getCallOutWarehouseName());
                        //调入
                        storage.setTransportCode1(allocation.getCallInLogisticsCenterCode());
                        storage.setTransportName1(allocation.getCallInLogisticsCenterName());
                        storage.setStorageName1(allocation.getCallInWarehouseName());
                        storage.setStorageCode1(allocation.getCallInWarehouseCode());
                    }
                }
                storage.setDetails(storageDetailMap.get(inbound.getInboundOderCode()));
                storageList.add(storage);
            }
        }
    }

    /**
     * 入库单转结算sap对接
     *
     * @param sapOrderRequest
     */
    private void outboundToStock(List<Storage> storageList, SapOrderRequest sapOrderRequest) {
        Storage storage;
        List<Outbound> outboundList = outboundDao.listForSap(sapOrderRequest);
        if (CollectionUtils.isNotEmpty(outboundList)) {
            Map<String, Outbound> outboundMap = outboundList.stream().collect(Collectors.toMap(Outbound::getOutboundOderCode, Function.identity()));
            List<String> orderCodes = outboundList.stream().map(Outbound::getOutboundOderCode).collect(Collectors.toList());
            List<String> outBoundCodes = outboundList.stream().filter(outbound -> outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ALLOCATE.getCode()) || outbound.getOutboundTypeCode().equals(OutboundTypeEnum.MOVEMENT.getCode())).map(Outbound::getOutboundOderCode).collect(Collectors.toList());
            sapOrderRequest.setOrderCodeList(orderCodes);
            //调拨出入库单据使用出库和入库的信息  其他出入库单据统一传第一个中
            Map<String, Allocation> allocationMap = Maps.newHashMap();
            if (CollectionUtils.isNotEmpty(outBoundCodes)) {
                List<Allocation> allocations = allocationMapper.listByOutboundCodes(outBoundCodes);
                allocationMap = allocations.stream().collect(Collectors.toMap(Allocation::getOutboundOderCode, Function.identity()));
            }
            List<OutboundBatch> batchList = outboundBatchDao.listByOrderCode(sapOrderRequest);
            List<OutboundProduct> outboundProducts = outboundProductDao.listDetailForSap(sapOrderRequest);
            Map<String, OutboundProduct> outboundProductMap = outboundProducts.stream().collect(Collectors.toMap(outboundProduct -> outboundProduct.getOutboundOderCode() + outboundProduct.getSkuCode(), Function.identity()));
            List<String> skuCodes = outboundProducts.stream().map(OutboundProduct::getSkuCode).collect(Collectors.toList());
            List<ProductSkuInfo> productSkuList = productSkuDao.getSkuInfoByCodeList(skuCodes);
            Map<String, Long> productMap = productSkuList.stream().collect(Collectors.toMap(ProductSkuInfo::getSkuCode, ProductSkuInfo::getManufacturerGuidePrice));
            StorageDetail storageDetail;
            List<StorageDetail> storageDetailList;
            Map<String, List<StorageDetail>> storageDetailMap = new HashMap<>();
            Outbound outbounds;
            OutboundProduct outboundProduct;
            for (OutboundBatch batch : batchList) {
                outboundProduct = outboundProductMap.get(batch.getOutboundOderCode() + batch.getSkuCode());
                if (outboundProduct == null) {
                    throw new GroundRuntimeException(String.format("未查询到商品信息!,sku_code:%s", batch.getSkuCode()));
                }
                storageDetail = new StorageDetail();
                storageDetail.setSkuCode(outboundProduct.getSkuCode());
                storageDetail.setSkuName(outboundProduct.getSkuName());
                storageDetail.setSkuDesc(StringConvertUtil.productDesc(outboundProduct.getColorName(), outboundProduct.getNorms(), outboundProduct.getModel()));
                storageDetail.setUnit(outboundProduct.getUnitName());
                storageDetail.setSupplierCode(batch.getSupplierCode());
                storageDetail.setSupplierName(batch.getSupplierName());
                //固定为1
                storageDetail.setUnitCount(1);
                storageDetail.setTradeExponent(1);
                storageDetail.setTaxRate(outboundProduct.getTax().intValue());
                storageDetail.setExpectCount(outboundProduct.getPreOutboundNum().intValue());
                storageDetail.setExpectMinUnitCount(outboundProduct.getPreOutboundNum().intValue());
                storageDetail.setSingleCount(outboundProduct.getPraOutboundNum().intValue());
                storageDetail.setMinUnitCount(outboundProduct.getPraOutboundMainNum().intValue());
                //销售和退供才有金额
                outbounds = outboundMap.get(outboundProduct.getOutboundOderCode());
                if ((null != outbounds) && (outbounds.getOutboundTypeCode().equals(OutboundTypeEnum.ORDER.getCode()) || outbounds.getOutboundTypeCode().equals(OutboundTypeEnum.RETURN_SUPPLY.getCode()))) {
                    storageDetail.setExpectTaxPrice(outboundProduct.getPreTaxPurchaseAmount().intValue());
                    storageDetail.setTaxPrice(outboundProduct.getPraTaxPurchaseAmount().intValue());
                }
                //厂商指导价
                storageDetail.setGuidePrice(productMap.containsKey(outboundProduct.getSkuCode()) ? productMap.get(outboundProduct.getSkuCode()).toString() : "0");
                if (storageDetailMap.containsKey(outboundProduct.getOutboundOderCode())) {
                    storageDetailList = storageDetailMap.get(outboundProduct.getOutboundOderCode());
                    storageDetailList.add(storageDetail);
                    storageDetailMap.put(outboundProduct.getOutboundOderCode(), storageDetailList);
                } else {
                    storageDetailList = Lists.newArrayList();
                    storageDetailList.add(storageDetail);
                    storageDetailMap.put(outboundProduct.getOutboundOderCode(), storageDetailList);
                }
            }
            InnerValue innerValue;
            InnerValue innerValueType;
            Allocation allocation;
            //销售单的id  出库单中没有上级销售单据的具体类型  所以筛选重新查询
            List<String> orderIds = outboundList.stream().filter((Outbound) -> Outbound.getOutboundTypeCode().equals((byte) 3)).map(Outbound::getSourceOderCode).collect(Collectors.toList());
            Map<String, OrderInfo> orderInfoMap = Maps.newHashMap();
            if (CollectionUtils.isNotEmpty(orderIds)) {
                List<OrderInfo> orderInfoList = orderInfoMapper.listByIds(orderIds);
                orderInfoMap = orderInfoList.stream().collect(Collectors.toMap(OrderInfo::getOrderCode, Function.identity()));
            }
            OrderInfo orderInfo;
            Integer type;
            for (Outbound outbound : outboundList) {
                storage = new Storage();
                innerValue = StringConvertUtil.outboundTypeConvert(outbound.getOutboundTypeCode());
                storage.setOrderId(String.format("%s-%s", outbound.getOutboundOderCode(), innerValue.getValue()));
                storage.setSubOrderType(innerValue.getValue());
                storage.setSubOrderTypeName(innerValue.getName());
                //销售订单/退供才传来源类型
                if (outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ORDER.getCode()) || outbound.getOutboundTypeCode().equals(OutboundTypeEnum.RETURN_SUPPLY.getCode())) {
                    if (outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ORDER.getCode())) {
                        orderInfo = orderInfoMap.get(outbound.getSourceOderCode());
                        type = orderInfo.getOrderTypeCode();
                        innerValueType = StringConvertUtil.outboundSourceTypeConvert(type);
                        storage.setSourceOrderType(innerValueType.getValue());
                        storage.setSourceOrderTypeName(innerValueType.getName());
                    } else {
                        storage.setSourceOrderType(ScmpStorageChangeEnum.getByCode("5").getCode());
                        storage.setSourceOrderTypeName(ScmpStorageChangeEnum.getByCode("5").getDesc());
                    }
                    storage.setSourceOrderId(String.format("%s-%d", outbound.getSourceOderCode(), 5));
                    storage.setSourceOrderCode(outbound.getSourceOderCode());
                    storage.setAmount(outbound.getPraTaxAmount().toString());
                    storage.setTransportCode(outbound.getLogisticsCenterCode());
                    storage.setTransportName(outbound.getLogisticsCenterName());
                    storage.setStorageCode(outbound.getWarehouseCode());
                    storage.setStorageName(outbound.getWarehouseName());
                }
                if (outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ALLOCATE.getCode()) || outbound.getOutboundTypeCode().equals(OutboundTypeEnum.MOVEMENT.getCode())) {
                    // 调拨单据 传调出调入仓库
                    allocation = allocationMap.get(outbound.getOutboundOderCode());
                    if (null != allocation) {
                        //调出
                        storage.setTransportCode(allocation.getCallOutLogisticsCenterCode());
                        storage.setStorageCode(allocation.getCallOutWarehouseCode());
                        storage.setTransportName(allocation.getCallOutLogisticsCenterName());
                        storage.setStorageName(allocation.getCallOutWarehouseName());
                        //调入
                        storage.setTransportCode1(allocation.getCallInLogisticsCenterCode());
                        storage.setStorageCode1(allocation.getCallInWarehouseCode());
                        storage.setTransportName1(allocation.getCallInLogisticsCenterName());
                        storage.setStorageName1(allocation.getCallInWarehouseName());
                    }
                }
                storage.setOrderCode(outbound.getOutboundOderCode());
                storage.setOrderCount(outbound.getPraOutboundNum().intValue());
                storage.setDiscountPrice("0");
                storage.setOptTime(outbound.getOutboundTime());
                storage.setCreateTime(outbound.getCreateTime());
                storage.setCreateByName(outbound.getCreateBy());
                storage.setDetails(storageDetailMap.get(outbound.getOutboundOderCode()));
                storageList.add(storage);
            }
        }
    }

    /**
     * 采购/退供数据同步
     */
    public void purchaseSynchronization(SapOrderRequest sapOrderRequest) {
        List<Purchase> purchases = Lists.newArrayList();
        purchaseOrder(sapOrderRequest, purchases);
        sapPurchaseAbutment(purchases, 1);
        purchases.clear();
        rejectOrder(sapOrderRequest, purchases);
        sapPurchaseAbutment(purchases, 2);

    }

    /**
     * 采购/退供单据对接
     *
     * @param orderList
     * @param type
     */
    private void sapPurchaseAbutment(List<Purchase> orderList, Integer type) {
        LOGGER.info("调用退供单据对接单据参数:{} ", JsonUtil.toJson(orderList));
        LOGGER.info("type:{}", type);
        int total = (int) Math.ceil(orderList.size() / (SAP_API_COUNT * 1.0));
        int endIndex;
        List<Purchase> subLists;
        List<String> orderCodes;
        for (int i = 0; i < total; i++) {
            endIndex = SAP_API_COUNT * (i + 1);
            if (SAP_API_COUNT * (i + 1) >= orderList.size()) {
                endIndex = orderList.size();
            }
            subLists = orderList.subList(SAP_API_COUNT * i, endIndex);
            orderCodes = subLists.stream().map(Purchase::getOrderId).collect(Collectors.toList());
            HttpClient client = HttpClient.post(PURCHASE_URL).json(subLists).timeout(10000);
            HttpResponse httpResponse = client.action().result(HttpResponse.class);
            if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
                LOGGER.info("调用结算sap销售单据成功:{}", httpResponse.getMessage());
                //1 采购 2 退供 更新同步状态
                if (type.equals(1)) {
                    purchaseOrderDao.updateByOrderCodes(orderCodes);
                } else if (type.equals(2)) {
                    rejectRecordDao.updateByOrderCodes(orderCodes);
                }
            } else {
                LOGGER.error("调用结算sap销售单据异常:{}", httpResponse.getMessage());
                throw new GroundRuntimeException(String.format("调用结算sap销售单据异常:%s", httpResponse.getMessage()));
            }
        }
    }


    /**
     * 采购订单
     */
    private void purchaseOrder(SapOrderRequest sapOrderRequest, List<Purchase> purchases) {
        List<PurchaseOrder> purchaseList = purchaseOrderDao.listForSap(sapOrderRequest);
        if (CollectionUtils.isNotEmpty(purchaseList)) {
            List<String> purchaseIds = purchaseList.stream().map(PurchaseOrder::getPurchaseOrderId).collect(Collectors.toList());
            sapOrderRequest.setOrderCodeList(purchaseIds);
            List<PurchaseOrderDetails> orderDetailsList = purchaseOrderDetailsDao.listByCodes(purchaseIds);
            Map<String, PurchaseOrderDetails> orderDetailsMap = orderDetailsList.stream().collect(Collectors.toMap(PurchaseOrderDetails::getPurchaseOrderId, Function.identity()));
            List<PurchaseOrderProduct> purchaseOrderProductList = purchaseOrderProductDao.listForSap(sapOrderRequest);
            List<String> skuCodes = purchaseOrderProductList.stream().map(PurchaseOrderProduct::getSkuCode).collect(Collectors.toList());
            //查询商品的厂商指导价和属性
            Map<String, ProductSkuInfo> productSkuInfoMap = productInfoBySkuCode(skuCodes);
            PurchaseDetail purchaseDetail;
            ProductSkuInfo productSkuInfo;
            PurchaseOrderDetails purchaseOrderDetails;
            Map<String, List<PurchaseDetail>> purchaseProductMap = Maps.newHashMap();
            List<PurchaseDetail> purchaseDetailList;
            for (PurchaseOrderProduct purchaseOrderProduct : purchaseOrderProductList) {
                purchaseDetail = new PurchaseDetail();
                purchaseDetail.setOrderId(purchaseOrderProduct.getPurchaseOrderId());
                purchaseDetail.setSkuCode(purchaseOrderProduct.getSkuCode());
                purchaseDetail.setSkuName(purchaseOrderProduct.getSkuName());
                purchaseDetail.setSkuDesc(StringConvertUtil.productDesc(purchaseOrderProduct.getColorName(), purchaseOrderProduct.getProductSpec(), purchaseOrderProduct.getModelNumber()));
                purchaseDetail.setPrice(purchaseOrderProduct.getProductAmount().toString());
                purchaseDetail.setSingleCount(purchaseOrderProduct.getSingleCount());
                purchaseDetail.setProductType(purchaseOrderProduct.getProductType());
                purchaseDetail.setUnit(purchaseOrderProduct.getBoxGauge());
                purchaseDetail.setInputRate(purchaseOrderProduct.getTaxRate());
                purchaseDetail.setStorageCount(purchaseOrderProduct.getSingleCount());
                purchaseDetail.setUniteCount("1");
                purchaseDetail.setCategoryCode(purchaseOrderProduct.getCategoryId());
                purchaseDetail.setCategoryName(purchaseOrderProduct.getCategoryName());
                purchaseDetail.setBrandCode(purchaseOrderProduct.getBrandId());
                purchaseDetail.setBrandName(purchaseOrderProduct.getBrandName());
                //厂商指导价
                productSkuInfo = productSkuInfoMap.get(purchaseOrderProduct.getSkuCode());
                if (productSkuInfo != null) {
                    purchaseDetail.setGuidePrice(productSkuInfo.getManufacturerGuidePrice().toString());
                    purchaseDetail.setProductProperty(productSkuInfo.getProductPropertyName());
                } else {
                    throw new GroundRuntimeException(String.format("商品sku:%s,未查询到信息", purchaseOrderProduct.getSkuCode()));
                }
                if (purchaseProductMap.containsKey(purchaseOrderProduct.getPurchaseOrderId())) {
                    purchaseDetailList = purchaseProductMap.get(purchaseOrderProduct.getPurchaseOrderId());
                    purchaseDetailList.add(purchaseDetail);
                    purchaseProductMap.put(purchaseOrderProduct.getPurchaseOrderId(), purchaseDetailList);
                } else {
                    purchaseDetailList = Lists.newArrayList();
                    purchaseDetailList.add(purchaseDetail);
                    purchaseProductMap.put(purchaseOrderProduct.getPurchaseOrderId(), purchaseDetailList);
                }
            }
            Purchase purchase;
            for (PurchaseOrder purchaseOrder : purchaseList) {
                purchase = new Purchase();
                purchase.setOrderId(purchaseOrder.getPurchaseOrderId());
                purchase.setOrderCode(purchaseOrder.getPurchaseOrderCode());
                purchase.setSupplierCode(purchaseOrder.getSupplierCode());
                purchase.setSupplierName(purchaseOrder.getSupplierName());
                purchase.setOrderType(Integer.valueOf(ScmpOrderEnum.PURCHASE.getCode()));
                purchase.setOrderTypeDesc(ScmpOrderEnum.PURCHASE.getDesc());
                purchase.setTransportCode(purchaseOrder.getTransportCenterCode());
                purchase.setTransportName(purchaseOrder.getTransportCenterName());
                purchase.setWarehouseCode(purchaseOrder.getWarehouseCode());
                purchase.setWarehouseName(purchaseOrder.getWarehouseName());
                purchase.setSkuCount(purchaseOrder.getSingleCount());
                purchase.setAmount(purchaseOrder.getProductTotalAmount().toString());
                purchase.setGroupCode(purchaseOrder.getPurchaseGroupCode());
                purchase.setGroupName(purchaseOrder.getPurchaseGroupName());
                purchase.setCompanyCode(purchaseOrder.getCompanyCode());
                purchase.setCompanyName(purchaseOrder.getCompanyName());
                purchase.setSettlementType(purchaseOrder.getSettlementMethodCode());
                purchase.setSettlementTypeDesc(purchaseOrder.getSettlementMethodName());
                purchase.setCreateTime(purchaseOrder.getCreateTime());
                purchase.setCreateById(purchaseOrder.getUpdateById());
                purchase.setCreateByName(purchaseOrder.getUpdateByName());
                //合同编码 等信息查询详情表
                purchaseOrderDetails = orderDetailsMap.get(purchaseOrder.getPurchaseOrderId());
                if (purchaseOrderDetails == null) {
                    throw new GroundRuntimeException(String.format("未查询到采购详情数据id:%s", purchaseOrder.getPurchaseOrderId()));
                }
                purchase.setContractNo(purchaseOrderDetails.getContractCode());
                purchase.setAccountName(purchaseOrderDetails.getAccountName());
                //固定传转账
                purchase.setPayType("1");
                purchase.setPayTypeDesc("转账");
                purchase.setPayDate(purchaseOrderDetails.getPayableTime() == null ? 0 : purchaseOrderDetails.getPayableTime());
                purchase.setDetails(purchaseProductMap.get(purchaseOrder.getPurchaseOrderId()));
                purchases.add(purchase);
            }
        }
    }

    private Map<String, ProductSkuInfo> productInfoBySkuCode(List<String> skuCodes) {
        List<ProductSkuInfo> productSkuList = productSkuDao.getSkuInfoByCodeList(skuCodes);
        if (CollectionUtils.isEmpty(productSkuList)) {
            throw new GroundRuntimeException(String.format("商品sku:%s,未查询到信息", JsonUtil.toJson(skuCodes)));
        }
        return productSkuList.stream().collect(Collectors.toMap(ProductSkuInfo::getSkuCode, Function.identity()));
    }

    /**
     * 退供订单
     */
    private void rejectOrder(SapOrderRequest sapOrderRequest, List<Purchase> purchases) {
        List<RejectRecord> rejectRecordList = rejectRecordDao.listForSap(sapOrderRequest);
        if (CollectionUtils.isNotEmpty(rejectRecordList)) {
            List<String> rejectIds = rejectRecordList.stream().map(RejectRecord::getRejectRecordId).collect(Collectors.toList());
            List<RejectRecordDetail> rejectRecordDetailList = rejectRecordDetailDao.listByRejectIds(rejectIds);
            List<String> skuCodes = rejectRecordDetailList.stream().map(RejectRecordDetail::getSkuCode).collect(Collectors.toList());
            //查询商品的厂商指导价和属性
            Map<String, ProductSkuInfo> productSkuInfoMap = productInfoBySkuCode(skuCodes);
            Map<String, List<PurchaseDetail>> detailMap = Maps.newHashMap();
            PurchaseDetail purchaseDetail;
            ProductSkuInfo productSkuInfo;
            List<PurchaseDetail> purchaseDetailList;
            for (RejectRecordDetail recordDetail : rejectRecordDetailList) {
                purchaseDetail = new PurchaseDetail();
                purchaseDetail.setOrderId(recordDetail.getRejectRecordId());
                purchaseDetail.setSkuCode(recordDetail.getSkuCode());
                purchaseDetail.setSkuName(recordDetail.getSkuName());
                purchaseDetail.setSkuDesc(StringConvertUtil.productDesc(recordDetail.getColorName(), recordDetail.getProductSpec(), recordDetail.getModelNumber()));
                purchaseDetail.setPrice(recordDetail.getProductAmount().toString());
                purchaseDetail.setSingleCount(recordDetail.getSingleCount());
                purchaseDetail.setProductType(recordDetail.getProductType());
                purchaseDetail.setUnit(recordDetail.getUnitName());
                purchaseDetail.setInputRate(recordDetail.getTaxRate());
                purchaseDetail.setStorageCount(recordDetail.getSingleCount());
                purchaseDetail.setUniteCount("1");
                purchaseDetail.setCategoryCode(recordDetail.getCategoryId());
                purchaseDetail.setCategoryName(recordDetail.getCategoryName());
                purchaseDetail.setBrandCode(recordDetail.getBrandId());
                purchaseDetail.setBrandName(recordDetail.getBrandName());
                //厂商指导价
                productSkuInfo = productSkuInfoMap.get(recordDetail.getSkuCode());
                if (productSkuInfo != null) {
                    purchaseDetail.setGuidePrice(productSkuInfo.getManufacturerGuidePrice().toString());
                    purchaseDetail.setProductProperty(productSkuInfo.getProductPropertyName());
                } else {
                    throw new GroundRuntimeException(String.format("商品sku:%s,未查询到信息", recordDetail.getSkuCode()));
                }
                if (detailMap.containsKey(recordDetail.getRejectRecordId())) {
                    purchaseDetailList = detailMap.get(recordDetail.getRejectRecordId());
                    purchaseDetailList.add(purchaseDetail);
                    detailMap.put(recordDetail.getRejectRecordId(), purchaseDetailList);
                } else {
                    purchaseDetailList = Lists.newArrayList();
                    purchaseDetailList.add(purchaseDetail);
                    detailMap.put(recordDetail.getRejectRecordId(), purchaseDetailList);
                }
            }
            Purchase purchase;
            for (RejectRecord rejectRecord : rejectRecordList) {
                purchase = new Purchase();
                purchase.setOrderId(rejectRecord.getRejectRecordId());
                purchase.setOrderCode(rejectRecord.getRejectRecordCode());
                purchase.setSupplierCode(rejectRecord.getSupplierCode());
                purchase.setSupplierName(rejectRecord.getSupplierName());
                purchase.setOrderType(Integer.valueOf(ScmpOrderEnum.PURCHASE.getCode()));
                purchase.setOrderTypeDesc(ScmpOrderEnum.PURCHASE.getDesc());
                purchase.setTransportCode(rejectRecord.getTransportCenterCode());
                purchase.setTransportName(rejectRecord.getTransportCenterName());
                purchase.setWarehouseCode(rejectRecord.getWarehouseCode());
                purchase.setWarehouseName(rejectRecord.getWarehouseName());
                purchase.setSkuCount(rejectRecord.getSingleCount());
                //总金额,三者实际金额加一起
                BigDecimal actualGiftAmount = rejectRecord.getActualGiftAmount() == null ? BigDecimal.valueOf(0) : rejectRecord.getActualGiftAmount();
                BigDecimal actualProductAmount = rejectRecord.getActualProductAmount() == null ? BigDecimal.valueOf(0) : rejectRecord.getActualProductAmount();
                BigDecimal actualReturnAmount = rejectRecord.getActualReturnAmount() == null ? BigDecimal.valueOf(0) : rejectRecord.getActualReturnAmount();
                purchase.setAmount(String.valueOf(actualGiftAmount.add(actualProductAmount).add(actualReturnAmount)));
                purchase.setGroupCode(rejectRecord.getPurchaseGroupCode());
                purchase.setGroupName(rejectRecord.getPurchaseGroupName());
                purchase.setCompanyCode(rejectRecord.getCompanyCode());
                purchase.setCompanyName(rejectRecord.getCompanyName());
                purchase.setSettlementType(rejectRecord.getSettlementMethodCode());
                purchase.setSettlementTypeDesc(rejectRecord.getSettlementMethodName());
                purchase.setCreateTime(rejectRecord.getCreateTime());
                purchase.setCreateById(rejectRecord.getUpdateById());
                purchase.setCreateByName(rejectRecord.getUpdateByName());
                purchase.setPayType("1");
                purchase.setPayTypeDesc("转账");
                purchase.setDetails(detailMap.get(rejectRecord.getRejectRecordId()));
                purchases.add(purchase);
            }

        }
    }

    private Long amountHandler(Long amount) {
        if (amount == null){
            amount = 0L;
        }
        return amount;
    }

    /**
     * 销售入库/销售退货数据同步
     */
    @Transactional(rollbackFor = Exception.class)
    public void saleSynchronization(SapOrderRequest sapOrderRequest) {
        //销售单list
        List<Order> orderList = Lists.newArrayList();
        orderInfoToOrder(orderList, sapOrderRequest);
        sapOrderAbutment(orderList, 1);
        orderList.clear();
        returnInfoToOrder(orderList, sapOrderRequest);
        sapOrderAbutment(orderList, 2);
    }

    /**
     * sap订单对接
     *
     * @param orderList
     * @param type
     */
    private void sapOrderAbutment(List<Order> orderList, Integer type) {
        LOGGER.info("调用结算sap销售单据参数:{} ", JsonUtil.toJson(orderList));
        LOGGER.info("type:{}", type);
        int total = (int) Math.ceil(orderList.size() / (SAP_API_COUNT * 1.0));
        int endIndex;
        List<Order> subLists;
        List<String> orderCodes;
        for (int i = 0; i < total; i++) {
            endIndex = SAP_API_COUNT * (i + 1);
            if (SAP_API_COUNT * (i + 1) >= orderList.size()) {
                endIndex = orderList.size();
            }
            subLists = orderList.subList(SAP_API_COUNT * i, endIndex);
            HttpClient client = HttpClient.post(ORDER_URL).json(subLists).timeout(10000);
            HttpResponse httpResponse = client.action().result(HttpResponse.class);
            if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
                LOGGER.info("调用结算sap销售单据成功:{}", httpResponse.getMessage());
                //1 销售出库单 2 销售退货 更新同步状态
                orderCodes = subLists.stream().map(Order::getOrderCode).collect(Collectors.toList());
                if (type.equals(1)) {
                    orderInfoMapper.updateByOrderCodes(orderCodes);
                } else if (type.equals(2)) {
                    returnOrderInfoMapper.updateByOrderCodes(orderCodes);
                }
            } else {
                LOGGER.error("调用结算sap销售单据异常:{}", httpResponse.getMessage());
                throw new GroundRuntimeException(String.format("调用结算sap销售单据异常:%s", httpResponse.getMessage()));
            }
        }
    }

    /**
     * 销售订单
     *
     * @param orderList
     * @param sapOrderRequest
     */
    private void orderInfoToOrder(List<Order> orderList, SapOrderRequest sapOrderRequest) {
        Order order;
        List<OrderInfo> orderInfoList = orderInfoMapper.listForSap(sapOrderRequest);
        if (CollectionUtils.isNotEmpty(orderInfoList)) {
            List<String> orderCodes = orderInfoList.stream().map(OrderInfo::getOrderCode).collect(Collectors.toList());
            sapOrderRequest.setOrderCodeList(orderCodes);
            List<OrderInfoItem> orderInfoItems = orderInfoItemMapper.listDetailForSap(sapOrderRequest);
            Map<String, OrderInfoItem> orderInfoItemMap = orderInfoItems.stream().collect(Collectors.toMap(OrderInfoItem::getSkuCode, Function.identity()));
            List<OrderInfoItemProductBatch> batchList = orderInfoItemProductBatchMapper.listDetailForSap(sapOrderRequest);
            OrderDetail orderDetail;
            List<OrderDetail> orderDetails;
            OrderInfoItem orderInfoItem;
            Map<String, List<OrderDetail>> orderDetailMap = new HashMap<>();
            for (OrderInfoItemProductBatch itemProductBatch : batchList) {
                orderInfoItem = orderInfoItemMap.get(itemProductBatch.getSkuCode());
                if (orderInfoItem == null) {
                    throw new GroundRuntimeException("未查询到对应的商品信息");
                }
                orderDetail = new OrderDetail();
                orderDetail.setSkuCode(orderInfoItem.getSkuCode());
                orderDetail.setSkuName(orderInfoItem.getSkuName());
                orderDetail.setSkuDesc(StringConvertUtil.productDesc(orderInfoItem.getColorName(), orderInfoItem.getSpec(), orderInfoItem.getModel()));
                orderDetail.setUnit(orderInfoItem.getUnitName());
                orderDetail.setScatteredUnit(orderInfoItem.getZeroDisassemblyCoefficient());
                orderDetail.setChannelPrice(orderInfoItem.getChannelUnitPrice().toString());
                orderDetail.setGiftFlag(orderInfoItem.getGivePromotion());
                orderDetail.setSingleCount(orderInfoItem.getNum().intValue());
                orderDetail.setDeliveryCount(orderInfoItem.getActualDeliverNum().intValue());
                //取供应商对应商品的表中数据
                orderDetail.setSupplierCode(itemProductBatch.getSupplierCode());
                orderDetail.setSupplierName(itemProductBatch.getSupplierName());
                orderDetail.setSingleCount(itemProductBatch.getNum().intValue());
                orderDetail.setDeliveryCount(itemProductBatch.getActualDeliverNum().intValue());
                if (orderDetailMap.containsKey(orderInfoItem.getOrderCode())) {
                    orderDetails = orderDetailMap.get(orderInfoItem.getOrderCode());
                    orderDetails.add(orderDetail);
                    orderDetailMap.put(orderInfoItem.getOrderCode(), orderDetails);
                } else {
                    orderDetails = new ArrayList<>();
                    orderDetails.add(orderDetail);
                    orderDetailMap.put(orderInfoItem.getOrderCode(), orderDetails);
                }
            }
            InnerValue innerValue;
            for (OrderInfo orderInfo : orderInfoList) {
                order = new Order();
                order.setOrderId(String.format("%s-%s", orderInfo.getOrderCode(), orderInfo.getOrderTypeCode()));
                order.setOrderCode(orderInfo.getOrderCode());
                innerValue = StringConvertUtil.orderInfoConvert(orderInfo.getOrderTypeCode());
                //单据类型
                order.setOrderType(Integer.valueOf(innerValue.getValue()));
                order.setOrderTypeDesc(innerValue.getName());
                //支付方式
                order.setPayType("1");
                order.setPayTypeDesc("转账");
                //1 是未支付 2 是已支付
                order.setPayStatus(2);
                //支付时间
                order.setPayTime(orderInfo.getPaymentTime());
                //订单类别
                order.setOrderCategoryCode(orderInfo.getOrderCategoryCode());
                order.setOrderCategoryDesc(orderInfo.getOrderCategory());
                //仓库
                order.setStorageCode(orderInfo.getTransportCenterCode());
                order.setStorageName(orderInfo.getTransportCenterName());
                //库房
                order.setWarehouseCode(orderInfo.getWarehouseCode());
                order.setWarehouseName(orderInfo.getWarehouseName());
                //供应商
                order.setOrderCount(orderInfo.getProductNum().intValue());
                order.setWeight(orderInfo.getWeight().toString());
                order.setVolume(orderInfo.getVolume().toString());
                order.setInvoiceFlag(Integer.valueOf(orderInfo.getInvoiceType()));
                order.setInvoiceTitle(orderInfo.getInvoiceTitle());
                order.setDeliveryTime(orderInfo.getDeliveryTime());
                order.setFreightTime(orderInfo.getTransportTime());
                order.setReceiptTime(orderInfo.getReceivingTime());
                order.setCustomerCode(orderInfo.getCustomerCode());
                order.setCustomerName(orderInfo.getCustomerName());
                order.setReceiptUserName(orderInfo.getConsignee());
                order.setReceiptMobile(orderInfo.getConsigneePhone());
                order.setReceiptAddress(orderInfo.getDetailAddress());
                order.setPayChannelAmount(orderInfo.getProductChannelTotalAmount().toString());
                //渠道信息
                order.setOrderChannelCode(orderInfo.getOrderOriginal());
                order.setOrderChannelName(orderInfo.getOrderOriginalName());
                order.setCreateTime(orderInfo.getCreateTime());
                order.setCreateById(orderInfo.getCreateById());
                order.setCreateByName(orderInfo.getCreateByName());
                order.setOrderStatus(orderInfo.getOrderStatus().toString());
                //目前只是完成订单先固定
                order.setOrderStatusDesc("完成");
                //详情信息
                order.setDetails(orderDetailMap.get(orderInfo.getOrderCode()));
                orderList.add(order);
            }
        }

    }

    /**
     * 销售退货
     *
     * @param orderList
     * @param sapOrderRequest
     */
    private void returnInfoToOrder(List<Order> orderList, SapOrderRequest sapOrderRequest) {
        Order order;
        List<ReturnOrderInfo> returnList = returnOrderInfoMapper.listForSap(sapOrderRequest);
        if (CollectionUtils.isNotEmpty(returnList)) {
            List<String> orderCodes = returnList.stream().map(ReturnOrderInfo::getReturnOrderCode).collect(Collectors.toList());
            sapOrderRequest.setOrderCodeList(orderCodes);
            List<ReturnOrderInfoItem> orderInfoItems = returnOrderInfoItemMapper.listDetailForSap(sapOrderRequest);
            List<InboundBatch> batchList = inboundBatchDao.listBySourceCodes(orderCodes);
            Map<String, InboundBatch> batchMap = batchList.stream().collect(Collectors.toMap(InboundBatch::getInboundOderCode, Function.identity()));
            InboundBatch batch;
            OrderDetail orderDetail;
            List<OrderDetail> orderDetails;
            Map<String, List<OrderDetail>> orderDetailMap = new HashMap<>();
            for (ReturnOrderInfoItem returnOrderInfoItem : orderInfoItems) {
                orderDetail = new OrderDetail();
                orderDetail.setSkuCode(returnOrderInfoItem.getSkuCode());
                orderDetail.setSkuName(returnOrderInfoItem.getSkuName());
                orderDetail.setSkuDesc(StringConvertUtil.productDesc(returnOrderInfoItem.getColorName(), returnOrderInfoItem.getSpec(), returnOrderInfoItem.getModel()));
                orderDetail.setUnit(returnOrderInfoItem.getUnitName());
                orderDetail.setScatteredUnit(returnOrderInfoItem.getZeroDisassemblyCoefficient());
                orderDetail.setChannelPrice(returnOrderInfoItem.getChannelUnitPrice().toString());
                //退货没有赠品
                orderDetail.setGiftFlag(0);
                orderDetail.setSingleCount(returnOrderInfoItem.getNum().intValue());
                orderDetail.setDeliveryCount(returnOrderInfoItem.getActualInboundNum());
                //供应商信息
                batch = batchMap.get(returnOrderInfoItem.getReturnOrderCode() + returnOrderInfoItem.getSkuCode());
                orderDetail.setSupplierName(batch.getSupplierName());
                orderDetail.setSupplierCode(batch.getSupplierCode());
                if (orderDetailMap.containsKey(returnOrderInfoItem.getReturnOrderCode())) {
                    orderDetails = orderDetailMap.get(returnOrderInfoItem.getReturnOrderCode());
                    orderDetails.add(orderDetail);
                    orderDetailMap.put(returnOrderInfoItem.getReturnOrderCode(), orderDetails);
                } else {
                    orderDetailMap.put(returnOrderInfoItem.getReturnOrderCode(), Collections.singletonList(orderDetail));
                }

            }
            for (ReturnOrderInfo returnOrderInfo : returnList) {
                order = new Order();
                order.setOrderId(String.format("%s-%s", returnOrderInfo.getReturnOrderCode(), ScmpOrderEnum.ORDER_BACK.getCode()));
                order.setOrderCode(returnOrderInfo.getReturnOrderCode());
                //单据类型
                order.setOrderType(Integer.valueOf(ScmpOrderEnum.ORDER_BACK.getCode()));
                order.setOrderTypeDesc(ScmpOrderEnum.ORDER_BACK.getDesc());
                //支付方式
                order.setPayType("1");
                order.setPayTypeDesc("转账");
                //1 是未支付 2 是已支付
                order.setPayStatus(2);
                //支付时间
                order.setPayTime(returnOrderInfo.getOperatorTime());
                //订单类别
                order.setOrderCategoryCode(returnOrderInfo.getReturnOrderTypeCode().toString());
                order.setOrderCategoryDesc(returnOrderInfo.getReturnOrderType());
                //仓库
                order.setStorageCode(returnOrderInfo.getTransportCenterCode());
                order.setStorageName(returnOrderInfo.getTransportCenterName());
                //库房
                order.setWarehouseCode(returnOrderInfo.getWarehouseCode());
                order.setWarehouseName(returnOrderInfo.getWarehouseName());
                //供应商
//                order.setSupplierCode(returnOrderInfo.getSupplierCode());
//                order.setSupplierName(returnOrderInfo.getSupplierName());
                order.setOrderCount(returnOrderInfo.getProductNum().intValue());
                order.setWeight(returnOrderInfo.getWeight().toString());
                order.setVolume(returnOrderInfo.getVolume().toString());
//                order.setInvoiceFlag(Integer.valueOf(returnOrderInfo.getInvoiceType()));
//                order.setInvoiceTitle(returnOrderInfo.getInvoiceTitle());
                order.setDeliveryTime(returnOrderInfo.getDeliveryTime());
                order.setFreightTime(returnOrderInfo.getDeliveryTime());
                order.setReceiptTime(returnOrderInfo.getReceivingTime());
                order.setCustomerCode(returnOrderInfo.getCustomerCode());
                order.setCustomerName(returnOrderInfo.getCustomerName());
                order.setReceiptUserName(returnOrderInfo.getConsignee());
                order.setReceiptMobile(returnOrderInfo.getConsigneePhone());
                order.setReceiptAddress(returnOrderInfo.getDetailAddress());
                order.setPayChannelAmount(returnOrderInfo.getProductChannelTotalAmount().toString());
                //渠道信息
                order.setOrderChannelCode(returnOrderInfo.getOrderOriginal());
                order.setOrderChannelName(returnOrderInfo.getOrderOriginalName());
                order.setCreateTime(returnOrderInfo.getCreateTime());
                order.setCreateById(returnOrderInfo.getCreateById());
                order.setCreateByName(returnOrderInfo.getCreateByName());
                order.setOrderStatusDesc(returnOrderInfo.getOrderStatus().toString());
                //目前只是完成订单先固定
                order.setOrderStatusDesc("完成");
                //详情信息
                order.setDetails(orderDetailMap.get(returnOrderInfo.getReturnOrderCode()));
                orderList.add(order);
            }

        }
    }
}
