package com.aiqin.bms.scmp.api.abutment.service.impl;

import com.aiqin.bms.scmp.api.abutment.domain.conts.ScmpOrderEnum;
import com.aiqin.bms.scmp.api.abutment.domain.conts.ScmpStorageChangeEnum;
import com.aiqin.bms.scmp.api.abutment.domain.conts.StringConvertUtil;
import com.aiqin.bms.scmp.api.abutment.domain.request.*;
import com.aiqin.bms.scmp.api.abutment.domain.request.purchase.*;
import com.aiqin.bms.scmp.api.abutment.domain.request.sale.Order;
import com.aiqin.bms.scmp.api.abutment.domain.request.sale.OrderDetail;
import com.aiqin.bms.scmp.api.abutment.domain.request.sale.ScmpImportOrder;
import com.aiqin.bms.scmp.api.abutment.domain.response.StockResponse;
import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.bms.scmp.api.base.OrderStatus;
import com.aiqin.bms.scmp.api.base.ReturnOrderStatus;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.QueryPriceChannelReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.QueryPriceChannelRespVo;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.ProfitLossService;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.response.InnerValue;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoItemRespVO;
import com.aiqin.bms.scmp.api.purchase.mapper.*;
import com.aiqin.bms.scmp.api.supplier.dao.contract.ContractDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompanyAccount;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.dto.ContractDTO;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplyCompanyAccountMapper;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private ReturnOrderInfoInspectionItemMapper returnOrderInfoInspectionItemMapper;
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
    private PurchaseBatchDao purchaseBatchDao;
    @Resource
    private RejectRecordDao rejectRecordDao;
    @Resource
    private RejectRecordDetailDao rejectRecordDetailDao;
    @Resource
    private RejectRecordBatchDao rejectRecordBatchDao;
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
            Map<String, BigDecimal> productMap = productSkuList.stream().collect(Collectors.toMap(ProductSkuInfo::getSkuCode, ProductSkuInfo::getManufacturerGuidePrice));
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
                storageDetail.setTaxRate(profitLossProduct.getTax());
                storageDetail.setExpectCount(profitLossProduct.getQuantity());
                storageDetail.setExpectMinUnitCount(profitLossProduct.getQuantity());
                storageDetail.setSingleCount(profitLossProduct.getQuantity().intValue());
                storageDetail.setMinUnitCount(profitLossProduct.getQuantity());
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
                storage.setOrderCount(quantity);
                storage.setDiscountPrice(BigDecimal.ZERO);
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
            Map<String, BigDecimal> productMap = productSkuList.stream().collect(Collectors.toMap(ProductSkuInfo::getSkuCode, ProductSkuInfo::getManufacturerGuidePrice));
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
                storageDetail.setTaxRate(inboundProduct.getTax());
                storageDetail.setExpectCount(inboundProduct.getPreInboundNum());
                storageDetail.setExpectMinUnitCount(inboundProduct.getPreInboundNum());
                storageDetail.setSingleCount(inboundProduct.getPraInboundNum().intValue());
                storageDetail.setMinUnitCount(inboundProduct.getPraInboundNum());
                //退货和采购才有金额
                inbounds = inboundMap.get(batch.getInboundOderCode());
                if ((null != inbounds) && (inbounds.getInboundTypeCode().equals(InboundTypeEnum.ORDER.getCode()) || inbounds.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode()))) {
                   // storageDetail.setExpectTaxPrice(inboundProduct.getPreTaxPurchaseAmount().intValue());
                    //storageDetail.setTaxPrice(inboundProduct.getPraTaxPurchaseAmount().intValue());
                    // 查询商品类型
                    PurchaseOrderProduct orderProduct = purchaseOrderProductDao.selectPreNumAndPraNumBySkuCodeAndSource(batch.getInboundOderCode(), inboundProduct.getSkuCode(), inboundProduct.getLinenum().intValue());
                    if(orderProduct != null && orderProduct.getProductType() != null){
                        if(orderProduct.getProductType() == 1){
                            storageDetail.setProductType(10);
                        }else if(orderProduct.getProductType() == 2){
                            storageDetail.setProductType(5);
                        }else if(orderProduct.getProductType() == 0){
                            storageDetail.setProductType(0);
                        }
                    }
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
                storage.setOrderCount(inbound.getPraInboundNum());
                storage.setDiscountPrice(BigDecimal.ZERO);
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
                    storage.setAmount(inbound.getPraTaxAmount());
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
            Map<String, BigDecimal> productMap = productSkuList.stream().collect(Collectors.toMap(ProductSkuInfo::getSkuCode, ProductSkuInfo::getManufacturerGuidePrice));
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
                storageDetail.setTaxRate(outboundProduct.getTax());
                storageDetail.setExpectCount(outboundProduct.getPreOutboundNum());
                storageDetail.setExpectMinUnitCount(outboundProduct.getPreOutboundNum());
                storageDetail.setSingleCount(outboundProduct.getPraOutboundNum().intValue());
                storageDetail.setMinUnitCount(outboundProduct.getPraOutboundMainNum());
                //销售和退供才有金额
                outbounds = outboundMap.get(outboundProduct.getOutboundOderCode());
                if ((null != outbounds) && (outbounds.getOutboundTypeCode().equals(OutboundTypeEnum.ORDER.getCode()) || outbounds.getOutboundTypeCode().equals(OutboundTypeEnum.RETURN_SUPPLY.getCode()))) {
                    //storageDetail.setExpectTaxPrice(outboundProduct.getPreTaxPurchaseAmount().intValue());
                    //storageDetail.setTaxPrice(outboundProduct.getPraTaxPurchaseAmount().intValue());
                    // 查询商品类型 TODO

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
                    storage.setAmount(outbound.getPraTaxAmount());
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
                storage.setOrderCount(outbound.getPraOutboundNum());
                storage.setDiscountPrice(BigDecimal.ZERO);
                storage.setOptTime(outbound.getOutboundTime());
                storage.setCreateTime(outbound.getCreateTime());
                storage.setCreateByName(outbound.getCreateBy());
                storage.setDetails(storageDetailMap.get(outbound.getOutboundOderCode()));
                storageList.add(storage);
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
                orderDetail.setChannelPrice(orderInfoItem.getChannelUnitPrice());
                orderDetail.setGiftFlag(orderInfoItem.getGivePromotion());
                orderDetail.setSingleCount(orderInfoItem.getNum().intValue());
                orderDetail.setDeliveryCount(orderInfoItem.getActualDeliverNum().intValue());
                //取供应商对应商品的表中数据
                orderDetail.setSupplierCode(itemProductBatch.getSupplierCode());
                orderDetail.setSupplierName(itemProductBatch.getSupplierName());
                orderDetail.setSingleCount(itemProductBatch.getTotalCount().intValue());
                orderDetail.setDeliveryCount(itemProductBatch.getActualTotalCount().intValue());
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
                order.setOrderType(innerValue.getValue());
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
                order.setPayChannelAmount(orderInfo.getProductChannelTotalAmount());
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
                orderDetail.setChannelPrice(returnOrderInfoItem.getChannelUnitPrice());
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
                order.setOrderType(ScmpOrderEnum.ORDER_BACK.getCode());
                order.setOrderTypeDesc(ScmpOrderEnum.ORDER_BACK.getDesc());
                //支付方式
                order.setPayType("1");
                order.setPayTypeDesc("转账");
                //1 是未支付 2 是已支付
                order.setPayStatus(2);
                //支付时间
                order.setPayTime(returnOrderInfo.getUpdateTime());
                //订单类别
                //order.setOrderCategoryCode(returnOrderInfo.getReturnOrderTypeCode());
                order.setOrderCategoryDesc(returnOrderInfo.getReturnOrderType().toString());
                //仓库
                order.setStorageCode(returnOrderInfo.getTransportCenterCode());
                order.setStorageName(returnOrderInfo.getTransportCenterName());
                //库房
                order.setWarehouseCode(returnOrderInfo.getWarehouseCode());
                order.setWarehouseName(returnOrderInfo.getWarehouseName());
                //供应商
//                order.setSupplierCode(returnOrderInfo.getSupplierCode());
//                order.setSupplierName(returnOrderInfo.getSupplierName());
                order.setOrderCount(returnOrderInfo.getProductCount().intValue());
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
                order.setPayChannelAmount(returnOrderInfo.getProductChannelTotalAmount());
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

    /**
      * 采购/退供调用sap
     */
    @Transactional(rollbackFor = Exception.class)
    public void purchaseAndReject(String orderCode, Integer dataType) {
        // 数据类型 dataType 0.采购 1.退供
        ScmpImportPurchase sap = new ScmpImportPurchase();
        if(dataType == 0){
            Purchase purchase = purchaseOrder(orderCode);
            sap.setPurchase(purchase);
            // 查询最后的入库单信息
            Inbound inbound = inboundDao.inboundCodeOrderLast(purchase.getOrderCode(), String.valueOf(InboundTypeEnum.RETURN_SUPPLY.getCode()));
            Storage storage = this.inboundPurchaseInfo(inbound);
            storage.setTransportCode1(purchase.getTransportCode());
            storage.setTransportName1(purchase.getTransportName());
            storage.setStorageCode(purchase.getWarehouseCode());
            storage.setStorageName(purchase.getWarehouseName());
            storage.setSupplierCode(purchase.getSupplierCode());
            storage.setSupplierName(purchase.getSupplierName());
            sap.setPurchaseStorage(storage);
        }else {
            sap.setPurchase(rejectOrder(orderCode));
            // 查询出库单
            Outbound outbound = outboundDao.selectBySourceCode(orderCode, String.valueOf(OutboundTypeEnum.RETURN_SUPPLY.getCode()));
            sap.setStorage(this.outboundInfo(outbound));
        }
        // 调用sap 接口
        HttpClient client = HttpClient.post(PURCHASE_URL).json(sap).timeout(10000);
        HttpResponse httpResponse = client.action().result(HttpResponse.class);
        if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
            if(dataType == 0){
                LOGGER.info("调用结算sap采购单据成功:{}", httpResponse.getMessage());
                PurchaseOrder order = new PurchaseOrder();
                order.setPurchaseOrderId(orderCode);
                order.setSynchrStatus(1);
                purchaseOrderDao.update(order);
            }else {
                LOGGER.info("调用结算sap退供单据成功:{}", httpResponse.getMessage());
                RejectRecord rejectRecord = new RejectRecord();
                rejectRecord.setRejectRecordCode(orderCode);
                rejectRecord.setSynchrStatus(1);
                rejectRecordDao.update(rejectRecord);
            }
        } else {
            String str = "";
            if(dataType == 0){
                str = "采购";
            }else {
                str = "退供";
            }
            LOGGER.error("调用结算sap" +str + "单据异常:{}", httpResponse.getMessage());
            throw new GroundRuntimeException(String.format("调用结算sap" +str + "单据异常:%s", httpResponse.getMessage()));
        }
    }

    /**
     * 销售&退货调用sap
     */
    @Override
    public void saleAndReturn(String orderCode, Integer dataType) {
        // 数据类型 dataType 0.销售 1.退货
        ScmpImportOrder scmpImportSale = new ScmpImportOrder();
        if(dataType == 0){
            Order order = saleOrder(orderCode);
            scmpImportSale.setOrder(order);
            Outbound outbound = outboundDao.selectBySourceCode(orderCode, String.valueOf(OutboundTypeEnum.ORDER.getCode()));
            PurchaseStorage purchaseStorage = new PurchaseStorage();
            purchaseStorage.setOrderId(outbound.getOutboundOderCode());
            purchaseStorage.setOrderCode(outbound.getOutboundOderCode());
            purchaseStorage.setInOutFlag(Integer.parseInt(order.getOrderType()));
            purchaseStorage.setSourceOrderId(orderCode);
            purchaseStorage.setSourceOrderCode(orderCode);
            purchaseStorage.setSourceOrderType(order.getOrderType());
            purchaseStorage.setSourceOrderTypeName(order.getOrderTypeDesc());
            purchaseStorage.setSubOrderType(order.getOrderType());
            purchaseStorage.setSubOrderTypeName(order.getOrderTypeDesc());

            scmpImportSale.setStorage(purchaseStorage);
        }else {
            Order order = returnOrder(orderCode);
            scmpImportSale.setOrder(order);

            Inbound inbound = inboundDao.inboundCodeOrderLast(order.getOrderCode(), String.valueOf(InboundTypeEnum.ORDER.getCode()));
            PurchaseStorage purchaseStorage = new PurchaseStorage();
            purchaseStorage.setOrderId(inbound.getInboundOderCode());
            purchaseStorage.setOrderCode(inbound.getInboundOderCode());
            purchaseStorage.setInOutFlag(Integer.parseInt(order.getOrderType()));
            purchaseStorage.setSourceOrderId(orderCode);
            purchaseStorage.setSourceOrderCode(orderCode);
            purchaseStorage.setSourceOrderType("25");
            purchaseStorage.setSourceOrderTypeName("售后退货");
            purchaseStorage.setSubOrderType("25");
            purchaseStorage.setSubOrderTypeName("售后退货");
            scmpImportSale.setStorage(purchaseStorage);
        }
        // 调用sap 接口
        String s = JsonUtil.toJson(scmpImportSale);
        System.out.println(s);
        HttpClient client = HttpClient.post(ORDER_URL).json(scmpImportSale).timeout(10000);
        HttpResponse httpResponse = client.action().result(HttpResponse.class);
        if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
            if(dataType == 0){
                LOGGER.info("调用结算sap销售单据成功:{}", httpResponse.getMessage());
               // PurchaseOrder order = new PurchaseOrder();
                OrderInfo order = new OrderInfo();
                order.setOrderCode(orderCode);
                order.setSynchrStatus(1);
                order.setSynchrTime(new Date());
                orderInfoMapper.updateOrderSynchrSap(order);
            }else {
                LOGGER.info("调用结算sap退货单据成功:{}", httpResponse.getMessage());
                ReturnOrderInfo returnOrderInfo = new ReturnOrderInfo();
                returnOrderInfo.setReturnOrderCode(orderCode);
                returnOrderInfo.setSynchrStatus(1);
                returnOrderInfo.setSynchrTime(new Date());
                returnOrderInfoMapper.updateReturnOrderSynchrSap(returnOrderInfo);
            }
        } else {
            String str = "";
            if(dataType == 0){
                str = "销售";
            }else {
                str = "退货";
            }
            LOGGER.error("调用结算sap" +str + "单据异常:{}", httpResponse.getMessage());
            throw new GroundRuntimeException(String.format("调用结算sap" +str + "单据异常:%s", httpResponse.getMessage()));
        }
    }

    /**
     * 调拨&损溢调用sap
     */
    @Override
    public void allocationAndprofitLoss(String orderCode, Integer dataType) {
        // 数据类型 dataType 0.调拨 1.损溢   (损溢不记录出入库 数据从单据中拿)
        List<Storage> storages = new ArrayList<>();
        if(dataType == 0){
            storages.add(outbound(orderCode));
            storages.add(inbound(orderCode));
        }else {
            Storage storage = profitLoss(orderCode);
            storages.add(storage);
        }
        // 调用sap 接口
        LOGGER.error("调用结算saps数据:{}", JsonUtil.toJson(storages));
        HttpClient client = HttpClient.post(STORAGE_URL).json(storages).timeout(10000);
        HttpResponse httpResponse = client.action().result(HttpResponse.class);
        if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
                LOGGER.info("调用结算sap"+storages.get(0).getSubOrderTypeName()+"单据成功:{}", httpResponse.getMessage());
                Inbound inbound = new Inbound();
                inbound.setSourceOderCode(orderCode);
                inbound.setSynchrStatus(1);
                inbound.setSynchrTime(new Date());
                inboundDao.updateInboundSynchrSap(inbound);

                Outbound outbound = new Outbound();
                outbound.setSourceOderCode(orderCode);
                outbound.setSynchrStatus(1);
                outbound.setSynchrTime(new Date());
                outboundDao.updateOutboundSynchrSap(outbound);
        } else {
            LOGGER.error("调用结算sap" +storages.get(0).getSubOrderTypeName()+ "单据异常:{}", httpResponse.getMessage());
            throw new GroundRuntimeException(String.format("调用结算sap" +storages.get(0).getSubOrderTypeName()+ "单据异常:%s", httpResponse.getMessage()));
        }
    }

    private Storage profitLoss(String orderCode) {
        // 查询损溢单信息
        ProfitLoss profitLoss = profitLossMapper.selectByOrderCode(orderCode);
        // 查询损溢单商品信息
        List<ProfitLossProduct> listByOrderCode = profitLossProductMapper.getListByOrderCode(profitLoss.getOrderCode());
        List<String> skuCodes = listByOrderCode.stream().map(ProfitLossProduct::getSkuCode).collect(Collectors.toList());
        Map<String, ProductSkuInfo> productSkuInfoMap = productInfoBySkuCode(skuCodes);
        // 查询损溢单批次商品信息
        Map<String, List<ProfitLossProductBatch>> productBatchMap = new HashMap<>();
        for(ProfitLossProduct detail : listByOrderCode) {
            String key = String.format("%s,%s,%s", detail.getSkuCode(), detail.getOrderCode(), detail.getLineNum());
            if (productBatchMap.get(key) == null) {
                productBatchMap.put(key, profitLossProductBatchMapper.getBatchListByOrderCode(detail.getSkuCode(), detail.getOrderCode(), detail.getLineNum()));
            }
        }
        List<StorageDetail> details = Lists.newArrayList();
        StorageDetail detail;
        ProductSkuInfo productSkuInfo;
        Storage storage = BeanCopyUtils.copy(profitLoss, Storage.class);
        storage.setOrderId(profitLoss.getId().toString());
        storage.setOrderCode(profitLoss.getOrderCode());
        storage.setInOutFlag(1);
        storage.setSourceOrderCode(profitLoss.getOrderCode());
        storage.setSourceOrderType("50");
        storage.setSourceOrderTypeName("报溢");
        storage.setSubOrderType("50");
        storage.setSubOrderTypeName("报溢");
        storage.setTransportCode("出库中");
        storage.setTransportName("出库中");
        storage.setStorageCode("出库中");
        storage.setStorageName("出库中");
        storage.setTransportCode1(profitLoss.getLogisticsCenterCode());
        storage.setTransportName1(profitLoss.getLogisticsCenterName());
        storage.setStorageCode1(profitLoss.getWarehouseCode());
        storage.setStorageName1(profitLoss.getWarehouseName());
        storage.setOrderCount(profitLoss.getProfitQuantity()+profitLoss.getLossQuantity());
        storage.setAmount(profitLoss.getProfitTotalCostRate());
        storage.setDiscountPrice(BigDecimal.ZERO);
        storage.setOptTime(profitLoss.getCreateTime());
        storage.setCreateTime(profitLoss.getCreateTime());
        storage.setCreateByName(profitLoss.getCreateBy());
        for (ProfitLossProduct product : listByOrderCode) {
            detail = BeanCopyUtils.copy(product, StorageDetail.class);
            detail.setUnit(product.getUnit());
            detail.setSkuDesc(product.getColor()+product.getSpecification()+product.getModel());
            detail.setProductType(0);
//            detail.setSupplierCode(product.getSupplierCode());
//            detail.setSupplierName(product.getSupplierName());
            // 厂商指导价
            productSkuInfo = productSkuInfoMap.get(product.getSkuCode());
            if (productSkuInfo != null) {
                detail.setGuidePrice(productSkuInfo.getManufacturerGuidePrice().toString());
                detail.setCategoryCode(productSkuInfo.getProductCategoryCode());
                detail.setCategoryName(productSkuInfo.getProductCategoryName());
                detail.setBrandCode(productSkuInfo.getProductBrandCode());
                detail.setBrandName(productSkuInfo.getProductBrandName());
                detail.setGuidePrice("0");
            } else {
                detail.setGuidePrice("0");
            }
            if(product.getLineNum() != null){
                detail.setStorageLocationCode(product.getLineNum().toString());
            }
            if(product.getTax() != null){
                detail.setTaxRate(product.getTax());
            }
//            detail.setUnitCount(Integer.parseInt(product.getInboundBaseContent()));
            if(product.getQuantity() != null){
                detail.setExpectCount(product.getQuantity());
                detail.setExpectMinUnitCount(product.getQuantity());
                detail.setSingleCount(product.getQuantity().intValue());
                detail.setMinUnitCount(product.getQuantity());
            }
            if(product.getTaxPrice() != null){
                detail.setExpectTaxPrice(product.getTaxPrice());
                detail.setTaxPrice(product.getTaxPrice());
            }
            // 查询批次sku对应的批次信息
            String key = String.format("%s,%s,%s", product.getSkuCode(), product.getOrderCode(), product.getLineNum());
            List<ProfitLossProductBatch> batchList = productBatchMap.get(key);
            if(CollectionUtils.isNotEmpty(batchList)){
                List<ScmpStorageBatch> infoBatch = new ArrayList<>();
                for (ProfitLossProductBatch productBatch : batchList) {
                    ScmpStorageBatch info = new ScmpStorageBatch();
                    BeanUtils.copyProperties(productBatch, info);
                    info.setPurchaseOrderCode(productBatch.getOrderCode());
                    info.setBatchNo(productBatch.getBatchCode());
                    // 商品类型  是否赠品（0商品1赠品）
                    info.setProductType("0");
                    infoBatch.add(info);
                    storage.setCreateById(productBatch.getCreateById());
                    storage.setCreateByName(productBatch.getCreateByName());
                }
                detail.setBatchList(infoBatch);
            }
            details.add(detail);
        }
        storage.setDetails(details);
        return storage;
    }

    /**
     *  调拨损溢入库
     * @param orderCode
     * @return
     */
    private Storage inbound(String orderCode) {
        // 查询入库单信息
        Inbound inbound = inboundDao.inboundCodeOrderLast(orderCode, String.valueOf(InboundTypeEnum.ALLOCATE.getCode()));
        // 查询入库单商品信息
        List<InboundProduct> inboundProducts = inboundProductDao.selectByInboundOderCode(inbound.getInboundOderCode());
        List<String> skuCodes = inboundProducts.stream().map(InboundProduct::getSkuCode).collect(Collectors.toList());
        Map<String, ProductSkuInfo> productSkuInfoMap = productInfoBySkuCode(skuCodes);
        // 查询入库单批次商品信息
        Map<String, List<InboundBatch>> inboundBatchMap = new HashMap<>();
        for(InboundProduct detail : inboundProducts) {
            String key = String.format("%s,%s,%s", detail.getSkuCode(), detail.getInboundOderCode(), detail.getLinenum());
            if (inboundBatchMap.get(key) == null) {
                inboundBatchMap.put(key, inboundBatchDao.inboundListBySku(detail.getSkuCode(), detail.getInboundOderCode(), detail.getLinenum()));
            }
        }
        if(inboundBatchMap != null){
            LOGGER.info("对接sap，入库单商品批次信息，{}", JsonUtil.toJson(productSkuInfoMap));
        }
        List<StorageDetail> details = Lists.newArrayList();
        StorageDetail detail;
        ProductSkuInfo productSkuInfo;
        Storage storage = BeanCopyUtils.copy(inbound, Storage.class);
        storage.setOrderId(inbound.getId().toString());
        storage.setOrderCode(inbound.getInboundOderCode());
        storage.setInOutFlag(inbound.getInboundTypeCode().intValue());
        storage.setSourceOrderCode(inbound.getSourceOderCode());
//        if(inbound.getInboundTypeCode().equals(InboundTypeEnum.ALLOCATE.getCode())){
            storage.setSourceOrderType("40");
            storage.setSourceOrderTypeName("调拨出库");
            storage.setSubOrderType("40");
            storage.setSubOrderTypeName("调拨出库");
//        }else {
//            storage.setSourceOrderType("50");
//            storage.setSourceOrderTypeName("报溢");
//            storage.setSubOrderType("50");
//            storage.setSubOrderTypeName("报溢");
//        }
        storage.setTransportCode("出库中");
        storage.setTransportName("出库中");
        storage.setStorageCode("出库中");
        storage.setStorageName("出库中");
        storage.setTransportCode1(inbound.getLogisticsCenterCode());
        storage.setTransportName1(inbound.getLogisticsCenterName());
        storage.setStorageCode1(inbound.getWarehouseCode());
        storage.setStorageName1(inbound.getWarehouseName());
        if(inbound.getPraInboundNum() != null){
            storage.setOrderCount(inbound.getPraInboundNum());
        }
        if(inbound.getPraTaxAmount() != null){
            storage.setAmount(inbound.getPraTaxAmount());
        }
        storage.setDiscountPrice(BigDecimal.ZERO);
        storage.setOptTime(inbound.getCreateTime());
        storage.setCreateTime(inbound.getCreateTime());

        for (InboundProduct inboundProduct : inboundProducts) {
            detail = BeanCopyUtils.copy(inboundProduct, StorageDetail.class);
            detail.setUnit(inboundProduct.getUnitName());
            detail.setSkuDesc(inboundProduct.getColorName()+inboundProduct.getInboundNorms()+inboundProduct.getModel());
            detail.setProductType(0);
            detail.setSupplierCode(inbound.getSupplierCode());
            detail.setSupplierName(inbound.getSupplierName());
            // 厂商指导价
            productSkuInfo = productSkuInfoMap.get(inboundProduct.getSkuCode());
            if (productSkuInfo != null) {
                detail.setGuidePrice(productSkuInfo.getManufacturerGuidePrice().toString());
                detail.setCategoryCode(productSkuInfo.getProductCategoryCode());
                detail.setCategoryName(productSkuInfo.getProductCategoryName());
                detail.setBrandCode(productSkuInfo.getProductBrandCode());
                detail.setBrandName(productSkuInfo.getProductBrandName());
            } else {
                detail.setGuidePrice("0");
            }
            if(inboundProduct.getLinenum() != null){
                detail.setStorageLocationCode(inboundProduct.getLinenum().toString());
            }
            if (inboundProduct.getInboundBaseContent() != null){
                detail.setUnitCount(Integer.parseInt(inboundProduct.getInboundBaseContent()));
            }
            if(inboundProduct.getTax() != null){
                detail.setTaxRate(inboundProduct.getTax());
            }
            if(inboundProduct.getPreInboundNum() != null){
                detail.setExpectCount(inboundProduct.getPreInboundNum());
            }
            if(inboundProduct.getPreInboundMainNum() != null){
                detail.setExpectMinUnitCount(inboundProduct.getPreInboundMainNum());
            }
            if(inboundProduct.getPreTaxPurchaseAmount() != null){
                detail.setExpectTaxPrice(inboundProduct.getPreTaxPurchaseAmount());
            }
            if(inboundProduct.getPraInboundNum() != null){
                detail.setSingleCount(inboundProduct.getPraInboundNum().intValue());
            }
            if(inboundProduct.getPraInboundMainNum() != null){
                detail.setMinUnitCount(inboundProduct.getPraInboundMainNum());
            }
            if(inboundProduct.getPraTaxPurchaseAmount() != null){
                detail.setTaxPrice(inboundProduct.getPraTaxPurchaseAmount());
            }


            // 查询批次sku对应的批次信息
            String key = String.format("%s,%s,%s", inboundProduct.getSkuCode(), inboundProduct.getInboundOderCode(), inboundProduct.getLinenum());
            List<InboundBatch> batchList = inboundBatchMap.get(key);
            if(CollectionUtils.isNotEmpty(batchList)){
                List<ScmpStorageBatch> infoBatch = new ArrayList<>();
                for (InboundBatch inboundBatch : batchList) {
                    ScmpStorageBatch info = new ScmpStorageBatch();
                    BeanUtils.copyProperties(inboundBatch, info);
                    info.setPurchaseOrderCode(inboundBatch.getInboundOderCode());
                    info.setBatchNo(inboundBatch.getBatchCode());
                    // 商品类型  是否赠品（0商品1赠品）
                    info.setProductType("0");
                    infoBatch.add(info);

                    storage.setCreateById(inboundBatch.getCreateById());
                    storage.setCreateByName(inboundBatch.getCreateByName());
                }
                detail.setBatchList(infoBatch);
            }

            details.add(detail);
        }
        storage.setDetails(details);
        return storage;
    }

    /**
     *  调拨损溢出库
     * @param orderCode
     * @return
     */
    private Storage outbound(String orderCode) {
        // 查询出库单信息
        Outbound outbound = outboundDao.selectOutbouondBySourceCode(orderCode);
        // 查询出库单商品信息
        List<OutboundProduct> outboundProducts = outboundProductDao.selectByOutboundOderCode(outbound.getOutboundOderCode());

        List<String> skuCodes = outboundProducts.stream().map(OutboundProduct::getSkuCode).collect(Collectors.toList());
        Map<String, ProductSkuInfo> productSkuInfoMap = productInfoBySkuCode(skuCodes);
        // 查询出库单批次商品信息
        Map<String, List<OutboundBatch>> outboundBatchMap = new HashMap<>();
        for(OutboundProduct detail : outboundProducts) {
            String key = String.format("%s,%s,%s", detail.getSkuCode(), detail.getOutboundOderCode(), detail.getLinenum());
            if (outboundBatchMap.get(key) == null) {
                outboundBatchMap.put(key, outboundBatchDao.outboundBatchBySap(detail.getSkuCode(), detail.getOutboundOderCode(), detail.getLinenum()));
            }
        }

        if(outboundBatchMap != null){
            LOGGER.info("对接sap，出库单商品批次信息，{}", JsonUtil.toJson(productSkuInfoMap));
        }
        List<StorageDetail> details = Lists.newArrayList();
        StorageDetail detail;
        ProductSkuInfo productSkuInfo;
        Storage storage = BeanCopyUtils.copy(outbound, Storage.class);
        storage.setOrderId(outbound.getId().toString());
        storage.setOrderCode(outbound.getOutboundOderCode());
        storage.setInOutFlag(outbound.getOutboundTypeCode().intValue());
        storage.setSourceOrderCode(outbound.getSourceOderCode());
//        if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ALLOCATE.getCode())){
            storage.setSourceOrderType("35");
            storage.setSourceOrderTypeName("调拨入库");
            storage.setSubOrderType("35");
            storage.setSubOrderTypeName("调拨入库");
//        }else {
//            storage.setSourceOrderType("45");
//            storage.setSourceOrderTypeName("报损");
//            storage.setSubOrderType("45");
//            storage.setSubOrderTypeName("报损");
//        }
        storage.setTransportCode("出库中");
        storage.setTransportName("出库中");
        storage.setStorageCode("出库中");
        storage.setStorageName("出库中");
        storage.setTransportCode1(outbound.getLogisticsCenterCode());
        storage.setTransportName1(outbound.getLogisticsCenterName());
        storage.setStorageCode1(outbound.getWarehouseCode());
        storage.setStorageName1(outbound.getWarehouseName());
        if(outbound.getPraOutboundNum() != null){
            storage.setOrderCount(outbound.getPraOutboundNum());
        }
        if(outbound.getPraTaxAmount() != null){
            storage.setAmount(outbound.getPraTaxAmount());
        }
        storage.setDiscountPrice(BigDecimal.ZERO);
        storage.setOptTime(outbound.getCreateTime());
        storage.setCreateTime(outbound.getCreateTime());
        for (OutboundProduct outboundProduct : outboundProducts) {
            detail = BeanCopyUtils.copy(outboundProduct, StorageDetail.class);
            if(outboundProduct.getLinenum() != null){
                detail.setStorageLocationCode(outboundProduct.getLinenum().toString());
            }
            detail.setUnit(outboundProduct.getUnitName());
            detail.setSkuDesc(outboundProduct.getColorName()+outboundProduct.getNorms()+outboundProduct.getModel());
            detail.setProductType(0);
            if (outboundProduct.getOutboundBaseContent() != null){
                detail.setUnitCount(Integer.parseInt(outboundProduct.getOutboundBaseContent()));
            }
            if(outboundProduct.getTax() != null){
                detail.setTaxRate(outboundProduct.getTax());
            }
            if(outboundProduct.getPreOutboundNum() != null){
                detail.setExpectCount(outboundProduct.getPreOutboundNum());
            }
            if(outboundProduct.getPreOutboundMainNum() != null){
                detail.setExpectMinUnitCount(outboundProduct.getPreOutboundMainNum());
            }
            if(outboundProduct.getPreTaxPurchaseAmount() != null){
                detail.setExpectTaxPrice(outboundProduct.getPreTaxPurchaseAmount());
            }
            if(outboundProduct.getPraOutboundNum() != null){
                detail.setSingleCount(outboundProduct.getPraOutboundNum().intValue());
            }
            if(outboundProduct.getPraOutboundMainNum() != null){
                detail.setMinUnitCount(outboundProduct.getPraOutboundMainNum());
            }
            if(outboundProduct.getPraTaxPurchaseAmount() != null){
                detail.setTaxPrice(outboundProduct.getPraTaxPurchaseAmount());
            }
            detail.setSupplierCode(outbound.getSupplierCode());
            detail.setSupplierName(outbound.getSupplierName());
            //厂商指导价
            productSkuInfo = productSkuInfoMap.get(outboundProduct.getSkuCode());
            if (productSkuInfo != null) {
                detail.setGuidePrice(productSkuInfo.getManufacturerGuidePrice().toString());
                detail.setBrandCode(productSkuInfo.getProductBrandCode());
                detail.setBrandName(productSkuInfo.getProductBrandName());
                detail.setCategoryCode(productSkuInfo.getProductCategoryCode());
                detail.setCategoryName(productSkuInfo.getProductCategoryName());
            } else {
                detail.setGuidePrice("0");
            }


            // 查询批次sku对应的批次信息
            String key = String.format("%s,%s,%s", outboundProduct.getSkuCode(), outboundProduct.getOutboundOderCode(), outboundProduct.getLinenum());
            List<OutboundBatch> batchList = outboundBatchMap.get(key);
            if(CollectionUtils.isNotEmpty(batchList)){
                List<ScmpStorageBatch> infoBatch = new ArrayList<>();
                for (OutboundBatch outboundBatch : batchList) {
                    ScmpStorageBatch info = new ScmpStorageBatch();
                    BeanUtils.copyProperties(outboundBatch, info);
                    info.setPurchaseOrderCode(outboundBatch.getOutboundOderCode());
                    info.setBatchNo(outboundBatch.getBatchCode());
                    // 商品类型  是否赠品（0商品1赠品）
                    info.setProductType("0");
                    infoBatch.add(info);

                    storage.setCreateById(outboundBatch.getCreateById());
                    storage.setCreateByName(outboundBatch.getCreateByName());
                }
                detail.setBatchList(infoBatch);
            }

            details.add(detail);
        }
        storage.setDetails(details);
        return storage;
    }

    /**
     *  销售退货
     * @param orderCode
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    private Order returnOrder(String orderCode) {
        // 查询退货单信息
        ReturnOrderInfo returnOrderInfo = returnOrderInfoMapper.selectByCode(orderCode);
        LOGGER.info("对接sap，退货单信息，{}", JsonUtil.toJson(returnOrderInfo));
        // 查询退供单商品信息
        List<ReturnOrderInfoItem> returnOrderInfoItem = returnOrderInfoItemMapper.selectByReturnOrderCode(orderCode);
        LOGGER.info("对接sap，退货单商品信息，{}", JsonUtil.toJson(returnOrderInfoItem));
        // 赋值sap 销售单信息
        OrderDetail orderDetail;
        List<OrderDetail> orderDetailList = Lists.newArrayList();
        ProductSkuInfo productSkuInfo;
        List<String> skuCodes = returnOrderInfoItem.stream().map(ReturnOrderInfoItem::getSkuCode).collect(Collectors.toList());
        Map<String, ProductSkuInfo> productSkuInfoMap = productInfoBySkuCode(skuCodes);
        // 查询退供单sku的批次信息
        Map<String, List<ReturnOrderInfoInspectionItem>> returnBatchMap = new HashMap<>();
        for(ReturnOrderInfoItem detail : returnOrderInfoItem) {
            String key = String.format("%s,%s,%s", detail.getSkuCode(), orderCode, detail.getProductLineNum());
            if (returnBatchMap.get(key) == null) {
                returnBatchMap.put(key, returnOrderInfoInspectionItemMapper.returnBatchList(detail.getSkuCode(), orderCode, detail.getProductLineNum().intValue()));
            }
        }
        if(returnBatchMap != null){
            LOGGER.info("对接sap，销售单商品批次信息，{}", JsonUtil.toJson(productSkuInfoMap));
        }
        Order order = BeanCopyUtils.copy(returnOrderInfo, Order.class);
        order.setOrderCode(returnOrderInfo.getReturnOrderCode());
        order.setOrderId(returnOrderInfo.getReturnOrderCode());
        //  0直送、1配送、2辅采
//        if(returnOrderInfo.getOrderTypeCode() != null && returnOrderInfo.getOrderTypeCode() == 0){
//            order.setOrderType("15");
//            order.setOrderTypeDesc(returnOrderInfo.getOrderType());
//        }
//        if(returnOrderInfo.getOrderTypeCode() != null && returnOrderInfo.getOrderTypeCode() == 1){
//            order.setOrderType("10");
//            order.setOrderTypeDesc(returnOrderInfo.getOrderType());
//        }
//        if(returnOrderInfo.getOrderTypeCode() != null && returnOrderInfo.getOrderTypeCode() == 2){
//            order.setOrderType("20");
//            order.setOrderTypeDesc(returnOrderInfo.getOrderType());
//        }
        order.setOrderType("25");
        order.setOrderTypeDesc("售后退货");
        if(returnOrderInfo.getReturnOrderType() != null){
            order.setOrderCategoryCode(returnOrderInfo.getReturnOrderType().toString());
        }
        //order.setOrderCategoryDesc(returnOrderInfo.getReturnOrderType());
        order.setPayTime(returnOrderInfo.getCreateTime());
        order.setPayStatus(returnOrderInfo.getPaymentStatus());
        order.setPayType(returnOrderInfo.getPaymentTypeCode());
        order.setPayTypeDesc(returnOrderInfo.getPaymentType());
        if(returnOrderInfo.getProductCount() != null){
            order.setOrderCount(returnOrderInfo.getProductCount().intValue());
        }
        order.setCreateTime(Calendar.getInstance().getTime());
        order.setStorageCode(returnOrderInfo.getTransportCenterCode());
        order.setStorageName(returnOrderInfo.getTransportCenterName());
        order.setCreateById(returnOrderInfo.getUpdateById());
        order.setCreateByName(returnOrderInfo.getUpdateByName());
        order.setReceiptTime(returnOrderInfo.getReceivingTime());
        order.setDeliveryTypeCode(returnOrderInfo.getDistributionModeCode());
        order.setDeliveryTypeDesc(returnOrderInfo.getDistributionMode());
        order.setReceiptUserName(returnOrderInfo.getConsignee());
        order.setReceiptMobile(returnOrderInfo.getConsigneePhone());
        order.setPostCode(returnOrderInfo.getZipCode());
        order.setReceiptAddr(returnOrderInfo.getProvinceName()+returnOrderInfo.getCityName()+returnOrderInfo.getDistrictName());
        order.setReceiptAddress(returnOrderInfo.getDetailAddress());
        if(returnOrderInfo.getProductChannelTotalAmount() != null){
            order.setPayChannelAmount(returnOrderInfo.getProductChannelTotalAmount());
        }
        if(returnOrderInfo.getDeliverAmount() != null){
            order.setFreightFee(returnOrderInfo.getDeliverAmount());
        }
        if(returnOrderInfo.getReturnOrderAmount() != null){
            order.setAmount(returnOrderInfo.getReturnOrderAmount());
        }
        order.setOrderChannelCode(returnOrderInfo.getOrderOriginal());
        order.setOrderChannelName(returnOrderInfo.getOrderOriginalName());
        if(returnOrderInfo.getOrderStatus() != null){
            order.setOrderStatus(returnOrderInfo.getOrderStatus().toString());
            Map<Integer, ReturnOrderStatus> allStatus = ReturnOrderStatus.getAllStatus();
            order.setOrderStatusDesc(allStatus.get(returnOrderInfo.getOrderStatus()).getFrontOrderStatus());
        }


        // 赋值sap 详情
        for(ReturnOrderInfoItem product : returnOrderInfoItem){
            orderDetail = BeanCopyUtils.copy(product, OrderDetail.class);
            orderDetail.setOrderId(product.getReturnOrderCode());
            orderDetail.setOrderType(0);
            orderDetail.setSkuDesc(product.getColorName()+product.getSpec()+product.getModel());
            orderDetail.setUnit(product.getUnitName());
            orderDetail.setScatteredUnit(product.getZeroDisassemblyCoefficient());
            orderDetail.setGiftFlag(0);
            orderDetail.setInputRate(product.getTax());
            orderDetail.setActivityNo(product.getActivityCode());
            orderDetail.setSupplierCode(returnOrderInfo.getSupplierCode());
            orderDetail.setSupplierName(returnOrderInfo.getSupplierName());
            // sku商品信息
            productSkuInfo = productSkuInfoMap.get(product.getSkuCode());
            if (productSkuInfo != null) {
                orderDetail.setBrandCode(productSkuInfo.getProductBrandCode());
                orderDetail.setBrandName(productSkuInfo.getProductBrandName());
                orderDetail.setCategoryCode(productSkuInfo.getProductCategoryCode());
                orderDetail.setCategoryName(productSkuInfo.getProductCategoryName());
            }
            if(product.getChannelUnitPrice() != null){
                orderDetail.setChannelPrice(product.getChannelUnitPrice());
            }
            if(product.getPrice() != null){
                orderDetail.setDistributionPrice(product.getPrice());
            }
            if(product.getNum() != null){
                orderDetail.setSingleCount(product.getNum().intValue());
            }
            if(product.getActualInboundNum() != null){
                orderDetail.setDeliveryCount(product.getActualInboundNum().intValue());
            }
            if(product.getPromotionLineNum() != null){
                orderDetail.setGiftLineNo(product.getPromotionLineNum().toString());
            }
            if(product.getNum() != null){
                orderDetail.setReturnCount(product.getNum().intValue());
            }

            // 查询批次sku对应的批次信息
            String key = String.format("%s,%s,%s", product.getSkuCode(), orderCode, product.getProductLineNum());
            List<ReturnOrderInfoInspectionItem> batchList = returnBatchMap.get(key);
            if(CollectionUtils.isNotEmpty(batchList)){
                List<ScmpPurchaseBatch> infoBatch = new ArrayList<>();
                for (ReturnOrderInfoInspectionItem returnBatch : batchList) {
                    ScmpPurchaseBatch info = new ScmpPurchaseBatch();
                    BeanUtils.copyProperties(returnBatch, info);
                    if(returnBatch.getProductDate() != null){
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        info.setProductDate(formatter.format(returnBatch.getProductDate()));
                    }
                    info.setPurchaseOrderCode(returnBatch.getReturnOrderCode());
                    info.setBatchNo(returnBatch.getBatchCode());
                    info.setActualTotalCount(returnBatch.getActualProductCount().longValue());
                    info.setProductType("0");
                    infoBatch.add(info);
                }
                orderDetail.setBatchList(infoBatch);
            }
            orderDetailList.add(orderDetail);
        }
        order.setDetails(orderDetailList);
        return order;
    }

    /**
     *  销售订单
     * @param orderCode
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    private Order saleOrder(String orderCode) {
        // 查询销售单信息
        OrderInfo orderInfo = orderInfoMapper.selectByOrderCode2(orderCode);
        LOGGER.info("对接sap，销售单信息，{}", JsonUtil.toJson(orderInfo));
        // 查询销售单商品信息
        List<QueryOrderInfoItemRespVO> orderInfoItem = orderInfoItemMapper.productList(orderCode);
        LOGGER.info("对接sap，销售单商品信息，{}", JsonUtil.toJson(orderInfoItem));
        // 赋值sap 销售单信息
        OrderDetail orderDetail;
        List<OrderDetail> orderDetailList = Lists.newArrayList();
        ProductSkuInfo productSkuInfo;
        List<String> skuCodes = orderInfoItem.stream().map(QueryOrderInfoItemRespVO::getSkuCode).collect(Collectors.toList());
        Map<String, ProductSkuInfo> productSkuInfoMap = productInfoBySkuCode(skuCodes);
        // 查询sku的批次信息
        Map<String, List<OrderInfoItemProductBatch>> orderBatchMap = new HashMap<>();
        for(QueryOrderInfoItemRespVO product : orderInfoItem) {
            String key = String.format("%s,%s,%s", product.getSkuCode(), orderInfo.getOrderCode(), product.getProductLineNum());
            if (orderBatchMap.get(key) == null) {
                orderBatchMap.put(key, orderInfoItemProductBatchMapper.orderBatchList(product.getSkuCode(), orderInfo.getOrderCode(), product.getProductLineNum().intValue()));
            }
        }
        if(orderBatchMap != null){
            LOGGER.info("对接sap，退货单商品批次信息，{}", JsonUtil.toJson(productSkuInfoMap));
        }
        Order order = BeanCopyUtils.copy(orderInfo, Order.class);
        order.setOrderCode(orderInfo.getOrderCode());
        order.setOrderId(orderInfo.getOrderCode());
        if(orderInfo.getOrderTypeCode() != null && orderInfo.getOrderTypeCode() == 0){
            order.setOrderType("15");
            order.setOrderTypeDesc(orderInfo.getOrderType());
        }
        if(orderInfo.getOrderTypeCode() != null && orderInfo.getOrderTypeCode() == 1){
            order.setOrderType("10");
            order.setOrderTypeDesc(orderInfo.getOrderType());
        }
        if(orderInfo.getOrderTypeCode() != null && orderInfo.getOrderTypeCode() == 2){
            order.setOrderType("20");
            order.setOrderTypeDesc(orderInfo.getOrderType());
        }
        order.setPayTime(orderInfo.getPaymentTime());
        order.setPayStatus(orderInfo.getPaymentStatus());
        order.setPayType(orderInfo.getPaymentTypeCode());
        order.setPayTypeDesc(orderInfo.getPaymentType());
        order.setCreateTime(Calendar.getInstance().getTime());
        order.setStorageCode(orderInfo.getTransportCenterCode());
        order.setStorageName(orderInfo.getTransportCenterName());
        order.setReceiptTime(orderInfo.getReceivingTime());
        order.setDeliveryTypeCode(orderInfo.getDistributionModeCode());
        order.setDeliveryTypeDesc(orderInfo.getDistributionMode());
     //   order.setReceiptUserId("无");
        order.setReceiptUserName(orderInfo.getConsignee());
        order.setReceiptMobile(orderInfo.getConsigneePhone());
        order.setPostCode(orderInfo.getZipCode());
        order.setReceiptAddr(orderInfo.getProvinceName()+orderInfo.getCityName()+orderInfo.getDistrictName());
        order.setReceiptAddress(orderInfo.getDetailAddress());
        order.setSourceCode(orderInfo.getOrderOriginal());
      //  order.setParentOrderId("无");
        order.setParentOrderCode(orderInfo.getMasterOrderCode());
        order.setOrderCategoryDesc(orderInfo.getOrderCategory());
        order.setDeliveryTypeDesc(orderInfo.getDistributionMode());
        order.setDeliveryTypeCode(orderInfo.getDistributionModeCode());
        order.setOrderChannelCode(orderInfo.getOrderOriginal());
        order.setOrderChannelName(orderInfo.getOrderOriginalName());
        order.setOrderStatus(orderInfo.getOrderStatus().toString());
        order.setInvoiceFlag(Integer.parseInt(orderInfo.getInvoiceTypeCode()));
        order.setBusinessForm(orderInfo.getBusinessForm());
        order.setTaxId(orderInfo.getTaxId());
        if(orderInfo.getProductNum() != null){
            order.setOrderCount(orderInfo.getProductNum().intValue());
        }
        if(orderInfo.getProductChannelTotalAmount() != null){
            order.setPayChannelAmount(orderInfo.getProductChannelTotalAmount());
        }
        if(orderInfo.getDeliverAmount() != null){
            order.setFreightFee(orderInfo.getDeliverAmount());
        }
        if(orderInfo.getOrderAmount() != null){
            order.setAmount(orderInfo.getOrderAmount());
        }
        if(orderInfo.getProductTotalAmount() != null){
            order.setPayDistributionAmount(orderInfo.getProductTotalAmount());
        }
        Map<Integer, OrderStatus> allStatus = OrderStatus.getAllStatus();
        order.setOrderStatusDesc(allStatus.get(orderInfo.getOrderStatus()).getFrontOrderStatus());
        // 赋值sap 详情
        for(QueryOrderInfoItemRespVO product : orderInfoItem){
            orderDetail = BeanCopyUtils.copy(product, OrderDetail.class);
            productSkuInfo = productSkuInfoMap.get(product.getSkuCode());
            orderDetail.setOrderId(orderInfo.getOrderCode());
            orderDetail.setSkuDesc(product.getColorName()+product.getSpec()+product.getModel());
            orderDetail.setUnit(product.getUnitName());
            orderDetail.setScatteredUnit(product.getZeroDisassemblyCoefficient());
            orderDetail.setGiftFlag(product.getGivePromotion());
            orderDetail.setGiftFlag(product.getGivePromotion());
            orderDetail.setActivityNo(product.getActivityCode());
            orderDetail.setInputRate(product.getTax());
            if (productSkuInfo != null) {
                orderDetail.setBrandCode(productSkuInfo.getProductBrandCode());
                orderDetail.setBrandName(productSkuInfo.getProductBrandName());
                orderDetail.setCategoryCode(productSkuInfo.getProductCategoryCode());
                orderDetail.setCategoryName(productSkuInfo.getProductCategoryName());
            }
            if(orderInfo.getOrderTypeCode() != null && orderInfo.getOrderTypeCode() == 0){
                orderDetail.setOrderType(15);
            }
            if(orderInfo.getOrderTypeCode() != null && orderInfo.getOrderTypeCode() == 1){
                orderDetail.setOrderType(10);
            }
            if(orderInfo.getOrderTypeCode() != null && orderInfo.getOrderTypeCode() == 2){
                orderDetail.setOrderType(20);
            }
            if(product.getChannelUnitPrice() != null){
                orderDetail.setChannelPrice(product.getChannelUnitPrice());
            }
            if(product.getPrice() != null){
                orderDetail.setDistributionPrice(product.getPrice());
            }
            if(product.getNum() != null){
                orderDetail.setSingleCount(product.getNum().intValue());
            }
            if(product.getActivityApportionment() != null){
                orderDetail.setActivityShareAmount(product.getActivityApportionment());
            }
            if(product.getPreferentialAllocation() != null){
                orderDetail.setDiscountShareAmount(product.getPreferentialAllocation());
            }
            if(product.getActualDeliverNum() != null){
                orderDetail.setDeliveryCount(product.getActualDeliverNum().intValue());
            }
            if(product.getPromotionLineNum() != null){
                orderDetail.setGiftLineNo(product.getPromotionLineNum().toString());
            }
            if(product.getReturnNum() != null){
                orderDetail.setReturnCount(product.getReturnNum().intValue());
            }


            // 查询批次sku对应的批次信息
            String key = String.format("%s,%s,%s", product.getSkuCode(), orderInfo.getOrderCode(), product.getProductLineNum());
            List<OrderInfoItemProductBatch> batchList = orderBatchMap.get(key);
            if(CollectionUtils.isNotEmpty(batchList)){
                List<ScmpPurchaseBatch> infoBatchs = new ArrayList<>();
                for (OrderInfoItemProductBatch batch : batchList) {
                    ScmpPurchaseBatch info = new ScmpPurchaseBatch();
                    BeanUtils.copyProperties(batch,info);
                    info.setPurchaseOrderCode(batch.getOrderCode());
                    if(batch.getProductDate() != null){
                    //    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //    info.setProductDate(formatter.format());
                        info.setProductDate(batch.getProductDate());
                    }
                    info.setBatchNo(batch.getBatchCode());
                    info.setProductType(String.valueOf(product.getGivePromotion()));
                    infoBatchs.add(info);
                }
                orderDetail.setBatchList(infoBatchs);
            }
            orderDetailList.add(orderDetail);
        }
        order.setDetails(orderDetailList);
        return order;
    }

    /**
     * 采购订单
     */
    private Purchase purchaseOrder(String purchaseCode) {
        // 查询采购单信息
        PurchaseOrder purchaseOrderInfo = purchaseOrderDao.purchaseOrder(purchaseCode);
        LOGGER.info("对接sap，采购单信息，{}", JsonUtil.toJson(purchaseOrderInfo));
        // 查询采购商品信息
        List<PurchaseOrderProduct> purchaseOrderProducts = purchaseOrderProductDao.orderProductInfo(purchaseCode);
        LOGGER.info("对接sap，采购单商品信息，{}", JsonUtil.toJson(purchaseOrderProducts));
        // 赋值sap 采购单信息
        PurchaseDetail purchaseDetail;
        List<PurchaseDetail> purchaseDetailList = Lists.newArrayList();
        ProductSkuInfo productSkuInfo;
        List<String> skuCodes = purchaseOrderProducts.stream().map(PurchaseOrderProduct::getSkuCode).collect(Collectors.toList());
        Map<String, ProductSkuInfo> productSkuInfoMap = productInfoBySkuCode(skuCodes);
        // 查询sku的批次信息
        Map<String, List<ScmpPurchaseBatch>> purchaseBatchMap = new HashMap<>();
        for(PurchaseOrderProduct product : purchaseOrderProducts) {
            String key = String.format("%s,%s,%s", product.getSkuCode(), purchaseOrderInfo.getPurchaseOrderCode(), product.getLinnum());
            if (purchaseBatchMap.get(key) == null) {
                purchaseBatchMap.put(key, purchaseBatchDao.purchaseBatchListBySap(product.getSkuCode(), purchaseOrderInfo.getPurchaseOrderCode(), product.getLinnum()));
            }
        }
        if(purchaseBatchMap != null){
            LOGGER.info("对接sap，采购单商品批次信息，{}", JsonUtil.toJson(productSkuInfoMap));
        }
        Purchase purchase = BeanCopyUtils.copy(purchaseOrderInfo, Purchase.class);
        purchase.setContractNo(purchaseOrderInfo.getContractCode());
        purchase.setGroupCode(purchaseOrderInfo.getPurchaseGroupCode());
        purchase.setGroupName(purchaseOrderInfo.getPurchaseGroupName());
        purchase.setOrderCode(purchaseOrderInfo.getPurchaseOrderCode());
        purchase.setOrderId(purchaseOrderInfo.getPurchaseOrderId());
        purchase.setOrderType("0");
        purchase.setOrderTypeDesc("采购");
        purchase.setPayDate(purchaseOrderInfo.getPaymentTime());
        purchase.setAmount(purchaseOrderInfo.getProductTotalAmount());
        // 0.预付款 1.货到付款 2.月结 3.实销实结
        if(purchaseOrderInfo.getPaymentMode() != null && purchaseOrderInfo.getPaymentMode() == 0){
            purchase.setPayType(purchaseOrderInfo.getPaymentMode().toString());
            purchase.setPayTypeDesc("预付款");
        }else if(purchaseOrderInfo.getPaymentMode() != null && purchaseOrderInfo.getPaymentMode() == 1){
            purchase.setPayType(purchaseOrderInfo.getPaymentMode().toString());
            purchase.setPayTypeDesc("货到付款");
        }else if(purchaseOrderInfo.getPaymentMode() != null && purchaseOrderInfo.getPaymentMode() == 2){
            purchase.setPayType(purchaseOrderInfo.getPaymentMode().toString());
            purchase.setPayTypeDesc("月结");
        }else if(purchaseOrderInfo.getPaymentMode() != null && purchaseOrderInfo.getPaymentMode() == 3){
            purchase.setPayType(purchaseOrderInfo.getPaymentMode().toString());
            purchase.setPayTypeDesc("实销实结");
        }
        purchase.setSettlementType(purchaseOrderInfo.getSettlementMethodCode());
        purchase.setSettlementTypeDesc(purchaseOrderInfo.getSettlementMethodName());
        purchase.setSkuCount(purchaseOrderInfo.getSingleCount().longValue());
        purchase.setCreateTime(Calendar.getInstance().getTime());
        purchase.setTransportCode(purchaseOrderInfo.getTransportCenterCode());
        purchase.setTransportName(purchaseOrderInfo.getTransportCenterName());
        purchase.setCreateById(purchaseOrderInfo.getUpdateById());
        purchase.setCreateByName(purchaseOrderInfo.getUpdateByName());
        // 赋值sap 详情
        for(PurchaseOrderProduct product : purchaseOrderProducts){
            purchaseDetail = BeanCopyUtils.copy(product, PurchaseDetail.class);
            purchaseDetail.setBrandCode(product.getBrandId());
            purchaseDetail.setCategoryCode(product.getCategoryId());
            //厂商指导价
            productSkuInfo = productSkuInfoMap.get(product.getSkuCode());
            if (productSkuInfo != null) {
                purchaseDetail.setGuidePrice(productSkuInfo.getManufacturerGuidePrice().toString());
                purchaseDetail.setProductProperty(productSkuInfo.getProductPropertyName());
            } else {
                purchaseDetail.setGuidePrice("0");
            }
            purchaseDetail.setInputRate(product.getTaxRate());
            purchaseDetail.setPrice(product.getProductAmount());
            String desc = product.getProductSpec() + "/" + product.getColorName() + "/" + product.getModelNumber();
            purchaseDetail.setSkuDesc(desc);
            purchaseDetail.setStorageCount(product.getStockCount());
            purchaseDetail.setSingleCount(product.getSingleCount().longValue());
            if(product.getProductType() == 0){
                purchaseDetail.setProductType(0);
            }else if(product.getProductType() == 1){
                purchaseDetail.setProductType(5);
            }else if(product.getProductType() == 2){
                purchaseDetail.setProductType(10);
            }

            String[] str = product.getBoxGauge().split("/");
            if(str != null){
                purchaseDetail.setUnit(str[1]);
                purchaseDetail.setUniteCount(str[0]);
            }
            // 查询批次sku对应的批次信息
            String key = String.format("%s,%s,%s", product.getSkuCode(), purchaseOrderInfo.getPurchaseOrderCode(), product.getLinnum());
            List<ScmpPurchaseBatch> batchList = purchaseBatchMap.get(key);
            if(CollectionUtils.isNotEmpty(batchList)){
                purchaseDetail.setBatchList(batchList);
            }
            purchaseDetailList.add(purchaseDetail);
        }
        purchase.setDetails(purchaseDetailList);
        LOGGER.info("对接sap，转换采购参数：{}", JsonUtil.toJson(purchase));
        return purchase;
    }

    /**  sap 入库单的信息转换*/
    private PurchaseStorage inboundInfo(Inbound inbound){
        LOGGER.info("对接sap，入库单信息：{}", JsonUtil.toJson(inbound));
        // 赋值sap 的入库单信息
        PurchaseStorage storage = new PurchaseStorage();
        storage.setOrderCode(inbound.getInboundOderCode());
        InnerValue innerValue = StringConvertUtil.inboundTypeConvert(inbound.getInboundTypeCode());
        storage.setOrderId(String.format("%s-%s", inbound.getInboundOderCode(), innerValue.getValue()));
        if ((null != inbound) && (inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode()))){
            storage.setInOutFlag(0);
            storage.setSourceOrderCode(inbound.getSourceOderCode());
            storage.setSourceOrderType("0");
            storage.setSourceOrderTypeName("采购");
            storage.setSubOrderType("0");
            storage.setSubOrderTypeName("采购入库");
        }
        LOGGER.info("对接sap，转换入库单参数：{}", JsonUtil.toJson(storage));
        return storage;
    }

    /** 退供订单*/
    private Purchase rejectOrder(String orderCode) {
        // 查询退供单信息
        RejectRecord rejectRecord = rejectRecordDao.selectByRejectCode(orderCode);
        LOGGER.info("对接sap，退供单信息，{}", JsonUtil.toJson(rejectRecord));
        // 查询退供单商品信息
        List<RejectRecordDetail> rejectRecordDetails = rejectRecordDetailDao.selectByRejectId(rejectRecord.getRejectRecordId());
        LOGGER.info("对接sap，退供单商品信息，{}", JsonUtil.toJson(rejectRecordDetails));
        // 赋值sap 退供单信息
        PurchaseDetail purchaseDetail;
        List<PurchaseDetail> purchaseDetailList = Lists.newArrayList();
        ProductSkuInfo productSkuInfo;
        List<String> skuCodes = rejectRecordDetails.stream().map(RejectRecordDetail::getSkuCode).collect(Collectors.toList());
        Map<String, ProductSkuInfo> productSkuInfoMap = productInfoBySkuCode(skuCodes);
        // 查询退供单sku的批次信息
        Map<String, List<ScmpPurchaseBatch>> rejectBatchMap = new HashMap<>();
        for(RejectRecordDetail detail : rejectRecordDetails) {
            String key = String.format("%s,%s,%s", detail.getSkuCode(), orderCode, detail.getLineCode());
            if (rejectBatchMap.get(key) == null) {
                rejectBatchMap.put(key, rejectRecordBatchDao.rejectBatchListBySap(detail.getSkuCode(), orderCode, detail.getLineCode()));
            }
        }
        if(rejectBatchMap != null){
            LOGGER.info("对接sap，采购单商品批次信息，{}", JsonUtil.toJson(productSkuInfoMap));
        }
        // 查询库存数量
        Map<String, Integer> rejectStockMap = new HashMap<>();
        for(RejectRecordDetail detail : rejectRecordDetails) {
            String key = String.format("%s,%s", detail.getSkuCode(), rejectRecord.getWarehouseCode());
            if (rejectStockMap.get(key) == null) {
                rejectStockMap.put(key, stockDao.stockCountByReject(detail.getSkuCode(), rejectRecord.getWarehouseCode()));
            }
        }

        Purchase purchase = BeanCopyUtils.copy(rejectRecord, Purchase.class);
        purchase.setContractNo(rejectRecord.getContractCode());
        purchase.setGroupCode(rejectRecord.getPurchaseGroupCode());
        purchase.setGroupName(rejectRecord.getPurchaseGroupName());
        purchase.setOrderCode(rejectRecord.getRejectRecordCode());
        purchase.setOrderId(rejectRecord.getRejectRecordId());
        purchase.setOrderType("5");
        purchase.setOrderTypeDesc("退供");
        purchase.setSettlementType(rejectRecord.getSettlementMethodCode());
        purchase.setSettlementTypeDesc(rejectRecord.getSettlementMethodName());
        purchase.setSkuCount(rejectRecord.getTotalCount());
        purchase.setCreateTime(Calendar.getInstance().getTime());
        purchase.setTransportCode(rejectRecord.getTransportCenterCode());
        purchase.setTransportName(rejectRecord.getTransportCenterName());
        purchase.setCreateById(rejectRecord.getUpdateById());
        purchase.setCreateByName(rejectRecord.getUpdateByName());
        BigDecimal amount = rejectRecord.getProductTaxAmount().add(rejectRecord.getReturnTaxAmount()).add(rejectRecord.getGiftTaxAmount());
        purchase.setAmount(amount);
        // 赋值退供sap 详情
        for(RejectRecordDetail detail : rejectRecordDetails) {
            purchaseDetail = BeanCopyUtils.copy(detail, PurchaseDetail.class);
            purchaseDetail.setBrandCode(detail.getBrandId());
            purchaseDetail.setCategoryCode(detail.getCategoryId());
            //厂商指导价
            productSkuInfo = productSkuInfoMap.get(detail.getSkuCode());
            if (productSkuInfo != null) {
                purchaseDetail.setGuidePrice(productSkuInfo.getManufacturerGuidePrice().toString());
                purchaseDetail.setProductProperty(productSkuInfo.getProductPropertyName());
            } else {
                purchaseDetail.setGuidePrice("0");
            }
            purchaseDetail.setInputRate(detail.getTaxRate());
            purchaseDetail.setPrice(detail.getProductAmount());
            String desc = detail.getProductSpec() + "/" + detail.getColorName() + "/" + detail.getModelNumber();
            purchaseDetail.setSkuDesc(desc);
            purchaseDetail.setSingleCount(detail.getTotalCount());
            // 查询库存数量
            String stockKey = String.format("%s,%s", detail.getSkuCode(), rejectRecord.getWarehouseCode());
            purchaseDetail.setStorageCount(rejectStockMap.get(stockKey));
            if(detail.getProductType().equals(0)){
                purchaseDetail.setProductType(0);
            }else if(detail.getProductType().equals(1)){
                purchaseDetail.setProductType(5);
            }else if(detail.getProductType().equals(2)){
                purchaseDetail.setProductType(10);
            }
            purchaseDetail.setUnit(detail.getUnitName());
            purchaseDetail.setUniteCount(String.valueOf(detail.getTotalCount()/detail.getProductCount()));
            // 查询批次sku对应的批次信息
            String key = String.format("%s,%s,%s", detail.getSkuCode(), orderCode, detail.getLineCode());
            List<ScmpPurchaseBatch> batchList = rejectBatchMap.get(key);
            if(CollectionUtils.isNotEmpty(batchList)){
                purchaseDetail.setBatchList(batchList);
            }
            purchaseDetailList.add(purchaseDetail);
        }
        purchase.setDetails(purchaseDetailList);
        LOGGER.info("对接sap，转换退供参数：{}", JsonUtil.toJson(purchase));
        return purchase;
    }

    /**  sap  出库单的信息转换*/
    private PurchaseStorage outboundInfo(Outbound outbound){
        LOGGER.info("对接sap，出库单信息：{}", JsonUtil.toJson(outbound));
        // 赋值sap 的入库单信息
        PurchaseStorage storage = new PurchaseStorage();
        storage.setOrderCode(outbound.getOutboundOderCode());
        InnerValue innerValue = StringConvertUtil.inboundTypeConvert(outbound.getOutboundTypeCode());
        storage.setOrderId(String.format("%s-%s", outbound.getOutboundOderCode(), innerValue.getValue()));
        if ((null != outbound) && (outbound.getOutboundTypeCode()).equals(OutboundTypeEnum.RETURN_SUPPLY.getCode())){
            storage.setInOutFlag(5);
            storage.setSourceOrderCode(outbound.getSourceOderCode());
            storage.setSourceOrderType("5");
            storage.setSourceOrderTypeName("退供");
            storage.setSubOrderType("5");
            storage.setSubOrderTypeName("退供出库");
        }
        LOGGER.info("对接sap，转换出库单参数：{}", JsonUtil.toJson(storage));
        return storage;
    }

    /**  sap 入库单的信息转换*/
    private Storage inboundPurchaseInfo(Inbound inbound){
        LOGGER.info("对接sap，入库单信息：{}", JsonUtil.toJson(inbound));
        // 赋值sap 的入库单信息
        Storage storage = new Storage();
        List<StorageDetail> storageDetailList = Lists.newArrayList();
        StorageDetail storageDetail;
        ProductSkuInfo productSkuInfo;

        storage.setAmount(inbound.getPraTaxAmount());
        storage.setOptTime(inbound.getInboundTime());
        storage.setOrderCode(inbound.getInboundOderCode());
        storage.setOrderCount(inbound.getPraMainUnitNum());
        InnerValue innerValue = StringConvertUtil.inboundTypeConvert(inbound.getInboundTypeCode());
        storage.setOrderId(String.format("%s-%s", inbound.getInboundOderCode(), innerValue.getValue()));
        if ((null != inbound) && (inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode()))){
            storage.setInOutFlag(0);
            storage.setSourceOrderCode(inbound.getSourceOderCode());
            storage.setSourceOrderType("0");
            storage.setSourceOrderTypeName("采购");
            storage.setTransportCode1(inbound.getLogisticsCenterCode());
            storage.setTransportName1(inbound.getLogisticsCenterName());
            storage.setStorageCode1(inbound.getWarehouseCode());
            storage.setStorageName1(inbound.getWarehouseName());
            storage.setSubOrderType("0");
            storage.setSubOrderTypeName("采购入库");
        }
        storage.setSupplierCode(inbound.getSupplierCode());
        storage.setSupplierName(inbound.getSupplierName());
        storage.setCreateTime(Calendar.getInstance().getTime());
        storage.setCreateByName(inbound.getUpdateBy());
        // 查询入库单的商品信息
        List<InboundProduct> inboundProducts = inboundProductDao.selectByInboundOderCode(inbound.getInboundOderCode());
        LOGGER.info("对接sap，入库单商品信息，{}", JsonUtil.toJson(inboundProducts));
        List<String> skuCodes = inboundProducts.stream().map(InboundProduct::getSkuCode).collect(Collectors.toList());
        Map<String, ProductSkuInfo> productSkuInfoMap = productInfoBySkuCode(skuCodes);

        // 查询sku的批次信息
        Map<String, List<InboundBatch>> inboundBatchMap = new HashMap<>();
        for(InboundProduct product : inboundProducts) {
            String key = String.format("%s,%s,%s", product.getSkuCode(), inbound.getInboundOderCode(), product.getLinenum());
            if (inboundBatchMap.get(key) == null) {
                inboundBatchMap.put(key, inboundBatchDao.inboundListBySku(product.getSkuCode(), inbound.getInboundOderCode(), product.getLinenum()));
            }
        }
        if(inboundBatchMap != null){
            LOGGER.info("对接sap，入库单商品批次信息，{}", JsonUtil.toJson(inboundBatchMap));
        }

        List<ScmpStorageBatch> infoBatch;
        ScmpStorageBatch info;
        for(InboundProduct product : inboundProducts){
            PurchaseOrderProduct purchaseOrderProduct = purchaseOrderProductDao.selectPreNumAndPraNumBySkuCodeAndSource(inbound.getSourceOderCode(), product.getSkuCode(), product.getLinenum().intValue());
            storageDetail = new StorageDetail();
            storageDetail.setExpectCount(product.getPreInboundNum());
            storageDetail.setExpectMinUnitCount(product.getPreInboundMainNum());
            storageDetail.setExpectTaxPrice(product.getPreTaxPurchaseAmount());
            //厂商指导价
            productSkuInfo = productSkuInfoMap.get(product.getSkuCode());
            if (productSkuInfo != null) {
                storageDetail.setGuidePrice(productSkuInfo.getManufacturerGuidePrice().toString());
            } else {
                storageDetail.setGuidePrice("0");
            }
            storageDetail.setMinUnitCount(product.getPraInboundMainNum());
            storageDetail.setSingleCount(product.getPraInboundNum().intValue());
            storageDetail.setSkuCode(product.getSkuCode());
            storageDetail.setSkuName(product.getSkuName());
            storageDetail.setSupplierCode(inbound.getSupplierCode());
            storageDetail.setSupplierName(inbound.getSupplierName());
            String desc = product.getNorms() + "/" + product.getColorName() + "/" + product.getModel();
            storageDetail.setSkuDesc(desc);
            storageDetail.setTaxPrice(product.getPreTaxPurchaseAmount());
            storageDetail.setTaxRate(product.getTax());
            storageDetail.setUnit(product.getUnitName());
            storageDetail.setUnitCount(Integer.valueOf(product.getInboundBaseContent()));
            Integer type = null;
            if(purchaseOrderProduct.getProductType() == 0){
                type = 0;
            }else if(purchaseOrderProduct.getProductType() == 1){
                type = 5;
            }else if(purchaseOrderProduct.getProductType() == 2){
                type = 10;
            }
            storageDetail.setProductType(type);
            storageDetail.setCategoryCode(purchaseOrderProduct.getCategoryId());
            storageDetail.setCategoryName(purchaseOrderProduct.getCategoryName());
            storageDetail.setBrandCode(purchaseOrderProduct.getBrandId());
            storageDetail.setBrandName(purchaseOrderProduct.getBrandName());
            // 查询批次sku对应的批次信息
            String key = String.format("%s,%s,%s", product.getSkuCode(), inbound.getInboundOderCode(), product.getLinenum());
            List<InboundBatch> batchList = inboundBatchMap.get(key);
            if(CollectionUtils.isNotEmpty(batchList)){
                infoBatch = new ArrayList<>();
                for (InboundBatch inboundBatch : batchList) {
                    info = BeanCopyUtils.copy(inboundBatch, ScmpStorageBatch.class);
                    info.setPurchaseOrderCode(inboundBatch.getInboundOderCode());
                    info.setBatchNo(inboundBatch.getBatchCode());
                    infoBatch.add(info);
                }
                storageDetail.setBatchList(infoBatch);
            }
            storageDetailList.add(storageDetail);
        }
        storage.setDetails(storageDetailList);
        LOGGER.info("对接sap，转换入库单参数：{}", JsonUtil.toJson(storage));
        return storage;
    }
}
