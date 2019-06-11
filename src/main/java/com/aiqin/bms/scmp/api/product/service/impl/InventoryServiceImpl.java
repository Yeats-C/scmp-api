package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.*;
import com.aiqin.bms.scmp.api.product.domain.request.*;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorQuVo;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorQuVoPage;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorVo;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.ProductDistributorReVo;
import com.aiqin.bms.scmp.api.product.domain.response.inbound.ProductDistributorReVoPage;
import com.aiqin.bms.scmp.api.product.service.InventoryService;
import com.google.common.collect.Lists;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * @author wuyongqiang
 * @description 库存管理业务操作实现类
 * @date 2018/11/20 15:19
 */
@Service
public class InventoryServiceImpl implements InventoryService {
    /**
     * 入库记录类型
     */
    public static final int RECORD_TYPE_INPUT = 0;

    /**
     * 出库记录类型
     */
    public static final int RECORD_TYPE_OUTPUT = 1;

    /**
     * 锁定库存解锁状态
     */
    public static final int RELEASE_STATUS_UNLOCK = 1;

    @Resource
    private InventoryDao inventoryDao;
    @Resource
    private ProductCategoryDistributionDao productCategoryDistributionDao;
    @Resource
    private ProductDistributorDao productDistributorDao;
    @Resource
    private ProductBrandDistributionDao productBrandDistributionDao;
    @Resource
    private ProductBrandTypeDao productBrandTypeDao;

    @Override
    public PageResData<Inventory> searchInventories(InventoryRequest param) {
        if (param.getStoreId() == null) {
            throw new IllegalArgumentException("所属门店不能为空");
        }
        int count = this.inventoryDao.getInventoryCount(param);
        List<Inventory> inventories = new ArrayList<>();
        List<Inventory> reInventories = this.inventoryDao.getInventories(param);
        for (Inventory reInventory : reInventories) {
            if (reInventory.getWarningStatus() == null) {
                reInventory.setWarningStatus(0);
            }
            if (reInventory.getSellableStorage() == null) {
                reInventory.setSellableStorage(0);
            }
            if (reInventory.getWarningStatus() == 0) {
                reInventory.setWarningType(3);
            } else if (reInventory.getWarningStock() != null) {
                if (reInventory.getSellableStorage() > reInventory.getWarningStock()) {
                    reInventory.setWarningType(1);
                } else {
                    reInventory.setWarningType(2);
                }
            } else {
                if (reInventory.getSellableStorage() > reInventory.getDefaultWarningStock()) {
                    reInventory.setWarningType(1);
                } else {
                    reInventory.setWarningType(2);
                }
            }
            inventories.add(reInventory);
        }

        return new PageResData<>(count, inventories);
    }

    @Override
    public PageResData<InventoryWarehouse> getInventoryWarehouse(String distributorId, String distributorName) {
        if (distributorId == null) {
            throw new IllegalArgumentException("所属门店不能为空");
        }
        InventoryWarehouse param = new InventoryWarehouse();
        param.setDistributorId(distributorId);
        param.setDistributorName(distributorName);
        param.setStorageType(1);
        param.setDisplayStock("1");
        param.setReturnStock("2");
        param.setStorageStock("3");
        List<InventoryWarehouse> list = new ArrayList<>();
        list.add(param);
        return new PageResData<>(1, list);
    }

    @Override
    public PageResData<InventoryDistribution> searchInventoryDistributions(InventoryDistributionRequest param) {
        if (param.getStoreId() == null) {
            throw new IllegalArgumentException("所属门店不能为空");
        }
        if (param.getProductSku() == null) {
            throw new IllegalArgumentException("商品SKU码不能为空");
        }
        int count = this.inventoryDao.getInventoryDistributionCount(param);
        List<InventoryDistribution> distributions = this.inventoryDao.getInventoryDistributions(param);
        return new PageResData<>(count, distributions);
    }

    @Override
    public void updateInventoryWarning(InventoryWarningRequest param) {
        if (param.getStoreId() == null || param.getStoreId().length() <= 0) {
            throw new IllegalArgumentException("所属门店不能为空");
        }
//        if (param.getSkus() == null || param.getSkus().isEmpty()) {
//            throw new IllegalArgumentException("商品SKU码不能为空");
//        }
//        if (param.getWarnable() == null && param.getThreshold() == null) {
//            throw new IllegalArgumentException("是否预警和预警阀值不能同时为空");
//        }
        this.inventoryDao.updateInventoryWarning(param);
    }

    @Override
    public PageResData<InventoryAccount> searchInventoryAccounts(InventoryAccountRequest param) {
        if (param.getRecordType() == null) {
            throw new IllegalArgumentException("记录类型不能为空");
        }
        int count = this.inventoryDao.getInventoryAccountCount(param);
        List<InventoryAccount> accounts = this.inventoryDao.getInventoryAccounts(param);
        return new PageResData<>(count, accounts);
    }

    @Override
    public InventoryAccountDetail searchInventoryAccountDetails(String masterNumber) {
        if (masterNumber == null) {
            throw new IllegalArgumentException("出/入库单号不能为空");
        }
        List<InventoryAccount> accounts = this.inventoryDao.getInventoryAccountDetails(masterNumber);
        if (accounts == null || accounts.isEmpty()) {
            return null;
        }
        // 利用记录第1个对象填充明细对象总览信息
        InventoryAccount first = accounts.get(0);
        InventoryAccountDetail detail = new InventoryAccountDetail();
        detail.setMasterNumber(first.getMasterNumber());
        detail.setRelateNumber(first.getRelateNumber());
        detail.setBillType(first.getBillType());
        detail.setCreator(first.getCreator());
        detail.setCreateTime(first.getCreateTime());
        detail.setCreateByName(first.getCreateByName());

        // 请求明细列表对象种的总览信息
        accounts.stream().forEach((account) -> {
            account.setMasterNumber(null);
            account.setRelateNumber(null);
            account.setBillType(null);
            account.setCreator(null);
            account.setCreateByName(null);
            account.setCreateTime(null);
        });

        // 设置明细列表
        detail.setAccounts(accounts);
        return detail;
    }

    /**
     * 获取商品库存分布
     *
     * @param param 查询参数
     * @return 库存分布对象
     */
    protected InventoryDistribution getInventoryDistribution(InventoryRecordRequest param) {
        if (param.getStoreId() == null) {
            throw new IllegalArgumentException("Store id must not be null");
        }
        if (param.getProductSku() == null) {
            throw new IllegalArgumentException("Product sku must not be null");
        }
        if (param.getStorageType() == null) {
            throw new IllegalArgumentException("Storage type must not be null");
        }
        List<InventoryDistribution> distributions = this.inventoryDao
                .getInventoryDistributions(new InventoryDistributionRequest(param.getStoreId(), param.getProductSku(),
                        param.getStorageType()));
        return distributions == null || distributions.isEmpty() ? null : distributions.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateInventoryRecord(List<InventoryRecordRequest> param) {
        param.forEach(this::save);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addProductDistributor(List<ProductDistributor> params) {

        if (CollectionUtils.isEmpty(params)) {
            return false;
        }
        List<ProductDistributor> insertInfos = Lists.newArrayList();

        List<String> insertCodes = Lists.newArrayList();

        Map<String, ProductDistributor> map = params.stream().collect(toMap(ProductDistributor::getSkuCode, a -> a));

        List<String> skuCodes = params.stream().filter(a -> StringUtils.isNotBlank(a.getSkuCode())).map(a -> a.getSkuCode()).collect(Collectors.toList());

        List<ProductDistributor> updateInfos = productDistributorDao.getSelectByDistributorIdAndSkuCodeIn(params.get(0).getDistributorId(), skuCodes);
        List<String> existsCodes = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(updateInfos)) {
            existsCodes = updateInfos.stream().map(a -> a.getSkuCode()).collect(Collectors.toList());

        }
        List<String> finalExistsCodes = existsCodes;
        insertCodes = skuCodes.stream().filter(a -> !finalExistsCodes.contains(a)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(updateInfos)) {
            for (ProductDistributor item : updateInfos) {
                ProductDistributor cur = map.get(item.getSkuCode());
                Date date = new Date();
                item.setUpdateTime(date);
                item.setUpdateBy(cur.getCreateBy());
                if (Objects.isNull(item.getDisplayStock())) {
                    item.setDisplayStock(0);
                }
                if (Objects.isNull(cur.getDisplayStock())) {
                    cur.setDisplayStock(0);
                }
                item.setDisplayStock(item.getDisplayStock() + cur.getDisplayStock());
            }
        }

        if (CollectionUtils.isNotEmpty(insertCodes)) {
            for (String item : insertCodes) {
                ProductDistributor cur = map.get(item);
                cur.setStorageType(1);
                cur.setReturnStock(0);
                cur.setStorageStock(0);
                cur.setLockStock(0);
                cur.setWarningStock(0);
                cur.setWarningStatus(0);
                cur.setShowStock(0);
                Date date = new Date();
                cur.setCreateTime(date);
                cur.setDistributionChannel(0);
                insertInfos.add(cur);
            }
        }

        if (CollectionUtils.isNotEmpty(updateInfos)) {
            productDistributorDao.batchUpdateStock(updateInfos);
        }

        if (CollectionUtils.isNotEmpty(insertInfos)) {
            productDistributorDao.batchInsertStock(insertInfos);
        }
        updateInfos.addAll(insertInfos);
        List<InventoryRecordRequest> kcList = new ArrayList<>();
        for (ProductDistributor updateInfo : updateInfos) {
            InventoryRecordRequest param = new InventoryRecordRequest();
            param.setStoreId(updateInfo.getDistributorId());
            param.setStoreCode(updateInfo.getDistributorCode());
            param.setProductSku(updateInfo.getSkuCode());
            param.setRecordType(0);
            param.setRecordNumber(updateInfo.getDisplayStock());
            param.setBillType(6);
            param.setRelateNumber(updateInfo.getRelationId());
            param.setStorageType(updateInfo.getStorageType());
            param.setSellableStorage(updateInfo.getDisplayStock() - updateInfo.getLockStock());
            param.setStoragePosition(1);
            param.setReleaseStatus(1);
            param.setOperator(updateInfo.getCreateBy());
            param.setMasterNumber(getCode(param.getRecordType()));
            param.setCreateByName(params.get(0).getCreateByName());
            param.setRelateNumber(params.get(0).getRelationId());
            kcList.add(param);

        }
        Boolean re = this.inventoryDao.insertInventoryRecordList(kcList);
        //查询该门店所包含已有的分类和品牌
        String did = params.get(0).getDistributorId();
        //分类
        List<ProductCategoryDistribution> productCategorys = productCategoryDistributionDao.selectCategoryDisInfoByDisId(did);
        // 品牌
        List<ProductBrandDistribution> productBrands = productBrandDistributionDao.selectBrandDisByInitAsc(did);

        List<ProductCategoryDistribution> productCategoryDistributions = new ArrayList<>();
        List<ProductBrandDistribution> productBrandDistributions = new ArrayList<>();
        for (ProductDistributor param : params) {
            Boolean flagCategory = false;
            for (ProductCategoryDistribution productCategory : productCategorys) {
                if (param.getCategoryId().equals(productCategory.getCategoryId())) {
                    flagCategory = true;
                    continue;
                }
            }
            Boolean flagCategory2 = false;
            for (ProductCategoryDistribution productCategoryDistribution : productCategoryDistributions) {
                if (productCategoryDistribution.getCategoryId().equals(param.getCategoryId())) {
                    flagCategory2 = true;
                    continue;
                }
            }
            if (!flagCategory && !flagCategory2) {
                ProductCategoryDistribution pCa = new ProductCategoryDistribution();
                pCa.setCategoryId(param.getCategoryId());
                pCa.setCategoryName(param.getCategoryName());
                pCa.setDistributorId(param.getDistributorId());
                pCa.setDistributorName(param.getDistributorName());
                pCa.setIsShow(0);
                pCa.setCreateBy(param.getCreateBy());
                pCa.setUpdateBy(param.getCreateBy());
                productCategoryDistributions.add(pCa);
            }
            Boolean flagBrandId = false;
            for (ProductBrandDistribution productBrandDistribution : productBrands) {
                if (param.getBrandId().equals(productBrandDistribution.getBrandId())) {
                    flagBrandId = true;
                    continue;
                }
            }
            Boolean flagBrandId2 = false;
            for (ProductBrandDistribution productBrandDistribution1 : productBrandDistributions) {
                if (productBrandDistribution1.getBrandId().equals(param.getBrandId())) {
                    flagBrandId2 = true;
                    continue;
                }
            }

            if (!flagBrandId && !flagBrandId2) {
                ProductBrandDistribution pbd = new ProductBrandDistribution();
//                ProductBrandType productBrandType = productBrandTypeDao.selectBrandInfoBy(param.getBrandId());
                pbd.setBrandName(param.getBrandName());
                String s = param.getBrandName().trim();
                char word = s.charAt(0);
                String bri = null;
                if ((word >= 0x4e00) && (word <= 0x9fbb)) {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
                    bri = pinyinArray[0].substring(0, 1).toUpperCase();
                } else if (Character.isDigit(word)) {
                    bri = String.valueOf(word);
                } else if (Character.isLowerCase(word)) {
                    bri = String.valueOf(word).toUpperCase();
                } else if (Character.isUpperCase(word)) {
                    bri = String.valueOf(word);
                } else {
                    throw new IllegalArgumentException("品牌名不合法");
                }
                pbd.setBrandInitials(bri);
                pbd.setBrandId(param.getBrandId());
                pbd.setBrandSort(0);
//                pbd.setBrandName(param.getBrandName());
                pbd.setBrandStatus(0);
//                pbd.setBrandLogo(productBrandType.getBrandLogo());
                pbd.setBrandTop(1);
//                pbd.setBrandTag(productBrandType.getBrandTag());
                pbd.setDistributorId(param.getDistributorId());
                pbd.setDistributorName(param.getDistributorName());
                pbd.setIsShow(0);
                pbd.setCreateBy(param.getCreateBy());
                pbd.setUpdateBy(param.getCreateBy());
                productBrandDistributions.add(pbd);
            }
        }
        if (productCategoryDistributions.size() > 0) {
            Boolean brc = productCategoryDistributionDao.insertList(productCategoryDistributions);
        }
        if (productBrandDistributions.size() > 0) {
            Boolean brd = productBrandDistributionDao.insertList(productBrandDistributions);
        }
        return true;
    }


    @Override
    public List<ProductDistributor> getByidslu(ProductDistributorVo vo) {
        return inventoryDao.getByidslu(vo);
    }

    @Override
    public List<ProductDistributorReVo> getPtDiReVo(ProductDistributorQuVo vo) {
        return inventoryDao.getPtDiReVo(vo);
    }

    @Override
    public PageResData<ProductDistributorReVoPage> getPtDiReVoPage(ProductDistributorQuVoPage vo) {
        List<ProductDistributorReVoPage> list = inventoryDao.getPtDiReVoPage(vo);
        Integer count = inventoryDao.getPtDiReVoPageCount(vo);
        return new PageResData<>(count, list);
    }


    @Override
    public List<ProductDistributorOrder> getProductDistributorOrder(ProductDistributorOrderRequest vo) {
        return inventoryDao.queryProductDistributorOrder(vo);
    }

    private void save(InventoryRecordRequest param) {
        if (param.getStoreId() == null) {
            throw new IllegalArgumentException("Store id must not be null");
        }
        if (param.getProductSku() == null) {
            throw new IllegalArgumentException("Product sku must not be null");
        }
        if (param.getRecordType() == null) {
            throw new IllegalArgumentException("Record type must not be null");
        }
        if (param.getStorageType() == null) {
            throw new IllegalArgumentException("Storage type must not be null");
        }
        if (param.getRecordNumber() == null) {
            throw new IllegalArgumentException("Record number must not be null");
        } else if (param.getRecordNumber() < 1) {
            throw new IllegalArgumentException("Record number must be greater than 0");
        }
        if (param.getRecordType() == RECORD_TYPE_INPUT && param.getStoragePosition() == null) {
            throw new IllegalArgumentException("Storage position must not be null when record type is " + RECORD_TYPE_INPUT);
        }
        if (param.getRecordType() == RECORD_TYPE_OUTPUT && param.getReleaseStatus() == null) {
            throw new IllegalArgumentException("Release status must not be null when record type is " + RECORD_TYPE_OUTPUT);
        }

        synchronized ((param.getStoreId() + param.getProductSku()).intern()) { // 集群环境下需要调整同步锁方式
            // 判断库存是否充足
            InventoryDistribution distribution = this.getInventoryDistribution(param);
            if (param.getRecordType() == RECORD_TYPE_OUTPUT) {
                if (distribution == null || distribution.getSellableStorage() < param.getRecordNumber()) {
                    throw new IllegalArgumentException("可售库存不足");
                }
                if (param.getReleaseStatus() == RELEASE_STATUS_UNLOCK && distribution.getLockStorage() < param.getRecordNumber()) {
                    throw new IllegalArgumentException("锁定库存不足");
                }
            }

            // 更新库存数量
            this.inventoryDao.updateInventoryNumber(param);

            // 保存库存操作记录
            param.setSellableStorage(distribution == null ? 0 : distribution.getSellableStorage());
            //获取出入库编号
            param.setMasterNumber(getCode(param.getRecordType()));
            this.inventoryDao.insertInventoryRecord(param);
        }
    }

    //生成出入库编号
    private String getCode(Integer recordType) {

//变更类型，0为入库，1为出库
        String type = null;
        if (recordType == 0) {
            type = "RK";
        } else if (recordType == 1) {
            type = "CK";
        } else {
            throw new IllegalArgumentException("出入库类型错误");
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String st = df.format(new Date());
        Integer s = (int) ((Math.random() + 1) * 100);
        String ran = s.toString().substring(1);
        return type + st + ran;
    }
}
