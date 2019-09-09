package com.aiqin.bms.scmp.api.product.service.impl.skuimport;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.common.StatusTypeCode;
import com.aiqin.bms.scmp.api.product.domain.ProductBrandType;
import com.aiqin.bms.scmp.api.product.domain.ProductCategory;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuInfoImport;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.price.SkuPriceDraftReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.AddSkuInfoReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.sku.PurchaseSaleStockReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.SaveSkuConfigReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.Manufacturer;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionaryInfo;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.TagInfo;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.dto.PurchaseGroupDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.SaveUseTagRecordItemReqVo;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.NumberConvertUtils;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Data
public class CheckSkuUpdate {
    AddSkuInfoReqVO resp;
    Map<String, ProductSkuInfo> productSkuMap;
    Map<String, SupplyCompany> supplyCompanyMap;
    Map<String, ProductBrandType> brandMap;
    Map<String, ProductCategory> categoryMap;
    Map<String, PriceChannel> channelMap;
    Map<String, TagInfo> skuTagMap;
    Map<String, NewProduct> spuMap;
    Map<String, String> repeatMap;
    SkuInfoImport importVo;
    List<String> error;
    Map<String, SupplierDictionaryInfo> dicMap;
    Map<String, Manufacturer> manufactureMap;
    Map<String, ProductSkuDraft> productSkuDraftMap;
    Map<String, PurchaseGroupDTO> purchaseGroupMap;

    public CheckSkuUpdate() {
    }

    public CheckSkuUpdate(Map<String, ProductSkuInfo> productSkuMap, Map<String, SupplyCompany> supplyCompanyMap, Map<String, ProductBrandType> brandMap, Map<String, ProductCategory> categoryMap, Map<String, PriceChannel> channelMap, Map<String, TagInfo> skuTagMap, Map<String, String> repeatMap, Object importVo, Map<String, NewProduct> spuMap, Map<String, SupplierDictionaryInfo> dicMap, Map<String, Manufacturer> manufactureMap) {
        this.error = Lists.newArrayList();
        this.resp = new AddSkuInfoReqVO();
        this.productSkuMap = productSkuMap;
        this.supplyCompanyMap = supplyCompanyMap;
        this.brandMap = brandMap;
        this.categoryMap = categoryMap;
        this.channelMap = channelMap;
        this.skuTagMap = skuTagMap;
        this.repeatMap = repeatMap;
        this.importVo = BeanCopyUtils.copy(importVo,SkuInfoImport.class);
        this.spuMap = spuMap;
        this.dicMap = dicMap;
        this.manufactureMap = manufactureMap;
    }

    //检查重复
    public CheckSkuUpdate checkRepeat() {
        return this;
    }
    //修改检查sku
    public CheckSkuUpdate checkSKuUpdate() {
        ProductSkuDraft draft = BeanCopyUtils.copy(importVo, ProductSkuDraft.class);
        //sku编码
        if (Objects.isNull(importVo.getSkuCode())) {
            error.add("sku编码不能为空");
        }else {
            ProductSkuInfo sku = productSkuMap.get(importVo.getSkuCode());
            //sku名称
            if (Objects.isNull(sku)) {
                error.add("无对应的sku编码");
            }else {
//                if (Objects.isNull(importVo.getSkuName())) {
//                    error.add("SKU名称不能为空");
//                }
//                    else {
//                        if (!sku.getSkuName().equals(importVo.getSkuName())) {
//                            error.add("sku编码和sku名称不对应");
//                        }
//                    }
            }
        }
        this.resp.setProductSkuDraft(draft);
        return this;
    }

    //检查基础数据
    public CheckSkuUpdate checkBaseDate() {
        ProductSkuDraft productSkuDraft = this.resp.getProductSkuDraft();
        //类型
        if (Objects.isNull(importVo.getGoodsGiftsDesc())) {
//            error.add("类型不能为空");
        } else {
            StatusTypeCode code = StatusTypeCode.getAll().get(importVo.getGoodsGiftsDesc());
            if (Objects.isNull(code)) {
                error.add("类型值错误，请填写商品或赠品");
            } else  {
                productSkuDraft.setGoodsGifts(code.getStatus());
            }
        }
        //sku简称
        if (Objects.isNull(importVo.getSkuAbbreviation())) {
//                error.add("SKU简称不能为空");
        }
        //品牌
        if (Objects.isNull(importVo.getProductBrandName())) {
//            error.add("品牌不能为空");
        } else {
            ProductBrandType productBrandType = brandMap.get(importVo.getProductBrandName());
            if (Objects.isNull(productBrandType)) {
                error.add("无对应品牌信息");
            } else {
                productSkuDraft.setProductBrandCode(productBrandType.getBrandId());
            }
        }
        //品类
        if (Objects.isNull(importVo.getProductCategoryName())) {
//            error.add("品类不能为空");
        } else {
            String[] split = importVo.getProductCategoryName().split("-");
            if (split.length != 4) {
                error.add("品类应为四级用\"-\"分割");
            } else {
                boolean flag = true;
                ProductCategory current = null;
                for (int i = split.length - 1; i >= 0; i--) {
                    ProductCategory productCategory = categoryMap.get(split[i] + "," + (i + 1));
                    if (Objects.isNull(productCategory)) {
                        error.add("无对应名称为" + split[i] + "的品牌信息");
                        flag = false;
                        break;
                    } else {
                        if (split.length - 1 == i) {
                            current = productCategory;
                        } else {
                            if (!productCategory.getCategoryId().equals(current.getParentId())) {
                                error.add("品牌名为" + current.getCategoryName() + "的上级名称不为" + split[i]);
                                flag = false;
                                break;
                            } else {
                                current = productCategory;
                            }
                        }
                    }
                }
                if (flag) {
                    productSkuDraft.setProductCategoryCode(categoryMap.get(split[split.length - 1] + "," + 4).getCategoryId());
                    productSkuDraft.setProductCategoryName(categoryMap.get(split[split.length - 1] + "," + 4).getCategoryName());
                }
            }
        }

        //spu
        if (Objects.isNull(importVo.getProductName())) {
//            error.add("所属SPU不能为空");
        } else {
            NewProduct newProduct = spuMap.get(importVo.getProductName().trim());
            if (Objects.isNull(newProduct)) {
                spuMap.put(importVo.getProductName().trim(), new NewProduct());
            } else {
                productSkuDraft.setProductCode(newProduct.getProductCode());
            }
        }
        //商品属性
        if (Objects.isNull(importVo.getProductPropertyName())) {
//            error.add("商品属性不能为空");
        } else {
            SupplierDictionaryInfo dic = dicMap.get(importVo.getProductPropertyName());
            if (Objects.isNull(dic)) {
                error.add("无对应名称的商品属性");
            } else {
                productSkuDraft.setProductPropertyCode(dic.getSupplierDictionaryValue());
            }
        }
        //所属部门
        if (Objects.isNull(importVo.getProductSortName())) {
//            error.add("所属部门不能为空");
        } else {
            SupplierDictionaryInfo dic = dicMap.get(importVo.getProductSortName());
            if (Objects.isNull(dic)) {
                error.add("无对应名称的所属部门");
            } else {
                productSkuDraft.setProductSortCode(dic.getSupplierDictionaryValue());
            }
        }
        //是否管理保质期
        if (Objects.isNull(importVo.getQualityAssuranceManagementDesc())) {
//            error.add("是否管理保质期不能为空");
        } else {
            QualityAssuranceManagements e = QualityAssuranceManagements.getAll().get(importVo.getQualityAssuranceManagementDesc());
            if (Objects.isNull(e)) {
                error.add("是否管理保质期请选择管理或者不管理");
            } else {
                productSkuDraft.setQualityAssuranceManagement(e.getType());
            }
            if (e.getType().equals((byte) 0)) {
                //管理
                //保质期单位
                if (Objects.isNull(importVo.getQualityNumber())) {
                    error.add("保质期单位不能为空");
                } else {
                    QualityTypes type = QualityTypes.getAll().get(importVo.getQualityNumber());
                    if (Objects.isNull(type)) {
                        error.add("保质期单位只能是年月天");
                    } else {
                        productSkuDraft.setQualityNumber(type.getType().toString());
                    }
                }
                //保质天数
                if (Objects.isNull(importVo.getQualityDate())) {
                    error.add("保质天数不能为空");
                }else {
                    productSkuDraft.setQualityDate(Integer.parseInt(importVo.getQualityDate())+"");
                }
            }
        }

        //供货渠道类别
        if (Objects.isNull(importVo.getCategoriesSupplyChannelsName())) {
//            error.add("供货渠道类别不能为空");
        } else {
            SupplierDictionaryInfo info = dicMap.get(importVo.getCategoriesSupplyChannelsName());
            if (Objects.isNull(info)) {
                error.add("无对应的名称的供货渠道类别");
            } else {
                productSkuDraft.setCategoriesSupplyChannelsCode(info.getSupplierDictionaryValue());
                //库存模式
                boolean b = "直送".equals(importVo.getCategoriesSupplyChannelsName());
                if (b) {
                    productSkuDraft.setInventoryModel(InventoryModels.NO.getType());
                } else {
                    productSkuDraft.setInventoryModel(InventoryModels.YES.getType());
                }
            }
        }
        //助记码
        if (false) {
        }
        //厂家指导价
        if (Objects.isNull(importVo.getManufacturerGuidePrice())) {
//            error.add("厂家指导价不能为空");
        } else {
            try {
                productSkuDraft.setManufacturerGuidePrice(NumberConvertUtils.stringParseLong(importVo.getManufacturerGuidePrice()));
            } catch (NumberFormatException e) {
                error.add("厂家指导价格式不正确");
            }
        }
        //适用其实月龄
        if (false) {
        }
        //是否季节商品
        if (Objects.isNull(importVo.getSeasonalGoodsDesc())) {
//            error.add("是否季节商品不能为空");
        } else {
            Generals generals = Generals.getAll().get(importVo.getSeasonalGoodsDesc());
            if (Objects.isNull(generals)) {
                error.add("是否季节商品请填写是或者否");
            } else {
                productSkuDraft.setSeasonalGoods(generals.getType());
            }
        }
        //仓位类型
        if (Objects.isNull(importVo.getWarehouseTypeName())) {
//            error.add("仓位类型不能为空");
        } else {
            SupplierDictionaryInfo info = dicMap.get(importVo.getWarehouseTypeName());
            if (Objects.isNull(info)) {
                error.add("仓位类型请填写大货仓位或者小货仓位");
            } else {
                productSkuDraft.setWarehouseTypeCode(info.getSupplierDictionaryValue());
            }
        }
        //结构性商品
        if (Objects.isNull(importVo.getStructuralGoodsDesc())) {
//            error.add("是否结构性商品不能为空");
        } else {
            Generals generals = Generals.getAll().get(importVo.getStructuralGoodsDesc());
            if (Objects.isNull(generals)) {
                error.add("结构性商品请填写是或者否");
            } else {
                productSkuDraft.setStructuralGoods(generals.getType());
            }
        }
        //使用时长
        if (Objects.nonNull(importVo.getUseTime())) {
            try {
                productSkuDraft.setUseTime(Integer.parseInt(importVo.getUseTime()));
            } catch (Exception e) {
                error.add("使用时长格式不正确");
            }
        }
        //库存模式
        if (Objects.isNull(importVo.getInventoryModelDesc())) {
//            error.add("库存模式不能为空");
        } else {
            InventoryModels generals = InventoryModels.getAll().get(importVo.getInventoryModelDesc());
            if (Objects.isNull(generals)) {
                error.add("库存模式请填写有库存销售或者无库存销售");
            } else {
                productSkuDraft.setInventoryModel(generals.getType());
            }
        }
        //唯一码管理
        if (Objects.isNull(importVo.getUniqueCodeDesc())) {
//            error.add("唯一码管理不能为空");
        } else {
            Generals generals = Generals.getAll().get(importVo.getUniqueCodeDesc());
            if (Objects.isNull(generals)) {
                error.add("唯一码管理请填写是或者否");
            } else {
                productSkuDraft.setUniqueCode(generals.getType());
            }
        }
        //覆盖渠道
        if (Objects.isNull(importVo.getPriceChannelName())) {
//            error.add("覆盖渠道不能为空");
        } else {
            List<ProductSkuChannelDraft> draft = Lists.newArrayList();
            String[] split = importVo.getPriceChannelName().split(",");
            boolean flag = true;
            if (split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    PriceChannel priceChannel = channelMap.get(split[i]);
                    if (Objects.isNull(priceChannel)) {
                        error.add("无法找到名称为" + split[i] + "的渠道");
                        flag = false;
                    }
                }
                if (flag) {
                    //这里需要渠道完全正确才封装对象
                    for (int i = 0; i < split.length; i++) {
                        PriceChannel priceChannel = channelMap.get(split[i]);
                        ProductSkuChannelDraft channelDraft = new ProductSkuChannelDraft();
                        channelDraft.setSkuName(importVo.getSkuName());
                        channelDraft.setPriceChannelCode(priceChannel.getPriceChannelCode());
                        channelDraft.setPriceChannelName(priceChannel.getPriceChannelName());
                        draft.add(channelDraft);
                        resp.setProductSkuChannelDrafts(draft);
                    }
                }
            } else {
                //只有一条
                PriceChannel priceChannel = channelMap.get(importVo.getPriceChannelName());
                if (Objects.isNull(priceChannel)) {
                    error.add("无法找到名称为" + importVo.getPriceChannelName() + "的渠道");
                    flag = false;
                } else {
                    ProductSkuChannelDraft channelDraft = new ProductSkuChannelDraft();
                    channelDraft.setSkuName(importVo.getSkuName());
                    channelDraft.setPriceChannelCode(priceChannel.getPriceChannelCode());
                    channelDraft.setPriceChannelName(priceChannel.getPriceChannelName());
                    draft.add(channelDraft);
                    resp.setProductSkuChannelDrafts(draft);
                }
            }
        }
        //商品标签
        if (Objects.nonNull(importVo.getTagName())) {
            List<SaveUseTagRecordItemReqVo> tagInfoList = Lists.newArrayList();
            boolean flag = true;
            String[] split = importVo.getTagName().split(",");
            if (split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    TagInfo tagInfo = skuTagMap.get(split[i]);
                    if (Objects.isNull(tagInfo)) {
                        error.add("名称为" + split[i] + "的标签不存在");
                        flag = false;
                    }
                }
                if (flag) {
                    //全部验证成功才能提交
                    for (int i = 0; i < split.length; i++) {
                        TagInfo tagInfo = skuTagMap.get(split[i]);
                        SaveUseTagRecordItemReqVo reqVo = new SaveUseTagRecordItemReqVo();
                        reqVo.setTagCode(tagInfo.getTagCode());
                        reqVo.setTagName(tagInfo.getTagName());
                        tagInfoList.add(reqVo);
                    }
                    resp.setTagInfoList(tagInfoList);
                }
            } else {
                TagInfo tagInfo = skuTagMap.get(importVo.getTagName());
                if (Objects.isNull(tagInfo)) {
                    error.add("名称为" + importVo.getTagName() + "的标签不存在");
                } else {
                    SaveUseTagRecordItemReqVo reqVo = new SaveUseTagRecordItemReqVo();
                    reqVo.setTagCode(tagInfo.getTagCode());
                    reqVo.setTagName(tagInfo.getTagName());
                    tagInfoList.add(reqVo);
                    resp.setTagInfoList(tagInfoList);
                }
            }
        }
        //商品备注
        if (StringUtils.isNotBlank(importVo.getRemark())) {
            productSkuDraft.setRemark(importVo.getRemark());
        }
        if(StringUtils.isNotBlank(importVo.getProductDesc()))
            productSkuDraft.setRemark(importVo.getProductDesc());
        //管理方式默认写死
        productSkuDraft.setManagementStyleCode("1");
        productSkuDraft.setManagementStyleName("只管理数量");
        this.resp.setProductSkuDraft(productSkuDraft);
        return this;
    }

    //检查进销存包装
    public CheckSkuUpdate checkInvoice() {
        List<PurchaseSaleStockReqVo> purchaseSaleStockReqVos = Lists.newArrayList();
        List<ProductSkuBoxPackingDraft> productSkuBoxPackingDrafts = Lists.newArrayList();
        //库存
        PurchaseSaleStockReqVo stock = new PurchaseSaleStockReqVo();
        stock.setBaseProductContent(1);
        stock.setZeroRemovalCoefficient(1L);
        stock.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
        stock.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
        stock.setProductCode(this.resp.getProductSkuDraft().getProductCode());
        stock.setProductName(this.resp.getProductSkuDraft().getProductName());
        stock.setType((byte) 0);
        //库存规格
        if (Objects.isNull(importVo.getStockSpec())) {
//            error.add("库存规格不能为空");
        } else {
            stock.setSpec(importVo.getStockSpec());
        }
        //库存单位
        if (Objects.isNull(importVo.getStockUnitName())) {
//            error.add("库存单位不能为空");
        } else {
            SupplierDictionaryInfo info = dicMap.get(importVo.getStockUnitName());
            if (Objects.isNull(info)) {
                error.add("无对应名称为" + importVo.getStockUnitName() + "的单位");
            } else {
                stock.setUnitCode(info.getSupplierDictionaryValue());
                stock.setUnitName(info.getSupplierContent());
            }
        }
        //库存包装信息
        if (Objects.nonNull(importVo.getStockBoxLength())) {
            ProductSkuBoxPackingDraft stockBox = new ProductSkuBoxPackingDraft();
            stockBox.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
            stockBox.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
            stockBox.setLargeUnit(stock.getUnitName());
            stockBox.setUnitCode(stock.getUnitCode());
            boolean flag = true;
            try {
                stockBox.setBoxLength(NumberConvertUtils.stringParseLong(importVo.getStockBoxLength().trim()));
            } catch (Exception e) {
                error.add("库存长格式不正确");
                flag = false;
            }
            try {
                stockBox.setBoxWidth(NumberConvertUtils.stringParseLong(importVo.getStockBoxWidth().trim()));
            } catch (Exception e) {
                error.add("库存宽格式不正确");
                flag = false;
            }
            try {
                stockBox.setBoxHeight(NumberConvertUtils.stringParseLong(importVo.getStockBoxHeight().trim()));
            } catch (Exception e) {
                error.add("库存高格式不正确");
                flag = false;
            }
            if (flag) {
                stockBox.setBoxVolume(stockBox.getBoxLength() * stockBox.getBoxWidth() * stockBox.getBoxHeight()/10000);
            }
            try {
                stockBox.setBoxGrossWeight(NumberConvertUtils.stringParseBigDecimal(importVo.getStockBoxGrossWeight().trim()));
            } catch (Exception e) {
                error.add("库存毛重格式不正确");
            }
            try {
                stockBox.setNetWeight(NumberConvertUtils.stringParseBigDecimal(importVo.getStockNetWeight()));
            } catch (Exception e) {
                error.add("库存净重格式不正确");
            }
            productSkuBoxPackingDrafts.add(stockBox);
        }
        stock.setBarCode(importVo.getSaleBarCode());
        purchaseSaleStockReqVos.add(stock);
        //采购
        PurchaseSaleStockReqVo purchase = new PurchaseSaleStockReqVo();
        purchase.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
        purchase.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
        purchase.setProductCode(this.resp.getProductSkuDraft().getProductCode());
        purchase.setProductName(this.resp.getProductSkuDraft().getProductName());
        purchase.setType((byte) 1);
        //采购规格
        if (Objects.isNull(importVo.getPurchaseSpec())) {
//            error.add("采购规格不能为空");
        } else {
            purchase.setSpec(importVo.getPurchaseSpec());
        }
        //采购单位
        if (Objects.isNull(importVo.getPurchaseUnitName())) {
//            error.add("采购单位不能为空");
        } else {
            SupplierDictionaryInfo info = dicMap.get(importVo.getPurchaseUnitName());
            if (Objects.isNull(info)) {
                error.add("无对应名称为" + importVo.getPurchaseUnitName() + "的单位");
            } else {
                purchase.setUnitCode(info.getSupplierDictionaryValue());
                purchase.setUnitName(info.getSupplierContent());
            }
        }
        //采购包装信息
        if (Objects.nonNull(importVo.getStockUnitName())&&Objects.nonNull(importVo.getPurchaseUnitName())&&(!importVo.getStockUnitName().equals(importVo.getPurchaseUnitName()))) {
            if (Objects.nonNull(importVo.getPurchaseBoxLength())) {
                ProductSkuBoxPackingDraft purchaseBox = new ProductSkuBoxPackingDraft();
                purchaseBox.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
                purchaseBox.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
                purchaseBox.setLargeUnit(purchase.getUnitName());
                purchaseBox.setUnitCode(purchase.getUnitCode());
                boolean flag = true;
                try {
                    purchaseBox.setBoxLength(NumberConvertUtils.stringParseLong(importVo.getPurchaseBoxLength().trim()));
                } catch (Exception e) {
                    error.add("采购长格式不正确");
                    flag = false;
                }
                try {
                    purchaseBox.setBoxWidth(NumberConvertUtils.stringParseLong(importVo.getPurchaseBoxWidth().trim()));
                } catch (Exception e) {
                    error.add("采购宽格式不正确");
                    flag = false;
                }
                try {
                    purchaseBox.setBoxHeight(NumberConvertUtils.stringParseLong(importVo.getPurchaseBoxHeight().trim()));
                } catch (Exception e) {
                    error.add("采购高格式不正确");
                    flag = false;
                }
                if (flag) {
                    purchaseBox.setBoxVolume(purchaseBox.getBoxLength() * purchaseBox.getBoxWidth() * purchaseBox.getBoxHeight()/10000);
                }
                try {
                    purchaseBox.setBoxGrossWeight(NumberConvertUtils.stringParseBigDecimal(importVo.getPurchaseBoxGrossWeight().trim()));
                } catch (Exception e) {
                    error.add("采购毛重格式不正确");
                }
                try {
                    purchaseBox.setNetWeight(NumberConvertUtils.stringParseBigDecimal(importVo.getPurchaseNetWeight()));
                } catch (Exception e) {
                    error.add("采购净重格式不正确");
                }
                productSkuBoxPackingDrafts.add(purchaseBox);
            }
        }
        //采购基商品含量
        if (Objects.isNull(importVo.getPurchaseBaseProductContent())) {
//            error.add("采购基商品含量不能为空");
        } else {
            try {
                purchase.setBaseProductContent(Integer.parseInt(importVo.getPurchaseBaseProductContent()));
            } catch (Exception e) {
                error.add("采购基商品含量不正确");
            }
        }
        //采购拆零系数 默认是1
        purchase.setZeroRemovalCoefficient((long) 1);
        //采购条形码
        if (Objects.isNull(importVo.getPurchaseBarCode())) {
//            error.add("采购条形码不能为空");
        } else {
            purchase.setBarCode(importVo.getPurchaseBarCode().trim());
        }
        purchaseSaleStockReqVos.add(purchase);

        //分销
        PurchaseSaleStockReqVo distribution = new PurchaseSaleStockReqVo();
        distribution.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
        distribution.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
        distribution.setProductCode(this.resp.getProductSkuDraft().getProductCode());
        distribution.setProductName(this.resp.getProductSkuDraft().getProductName());
        distribution.setType((byte) 2);
        distribution.setSpec(stock.getSpec());
        distribution.setUnitCode(stock.getUnitCode());
        distribution.setUnitName(stock.getUnitName());
        distribution.setBaseProductContent(1);
        //分销拆零系数
        if (Objects.isNull(importVo.getDistributionZeroRemovalCoefficient())) {
//            error.add("分销拆零系数不能为空");
        } else {
            try {
                distribution.setZeroRemovalCoefficient(Long.parseLong(importVo.getDistributionZeroRemovalCoefficient()));
            } catch (Exception e) {
                error.add("分销拆零系数不正确");
            }
        }
        distribution.setBarCode(importVo.getSaleBarCode());
        //最大订购数量
        if (Objects.nonNull(importVo.getMaxOrderNum())) {
            try {
                distribution.setMaxOrderNum(Integer.parseInt(importVo.getMaxOrderNum()));
            } catch (NumberFormatException e) {
                error.add("最大订购数量格式不正确");
            }
        }
        purchaseSaleStockReqVos.add(distribution);

        //销售
        PurchaseSaleStockReqVo sale = new PurchaseSaleStockReqVo();
        sale.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
        sale.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
        sale.setProductCode(this.resp.getProductSkuDraft().getProductCode());
        sale.setProductName(this.resp.getProductSkuDraft().getProductName());
        sale.setIsDefault((byte)1);
        sale.setType((byte) 3);
        sale.setSpec(stock.getSpec());
        sale.setUnitCode(stock.getUnitCode());
        sale.setUnitName(stock.getUnitName());
        //销售条形码
        if (Objects.isNull(importVo.getSaleBarCode())) {
//            error.add("销售条形码不能为空");
        } else {
            sale.setBarCode(importVo.getSaleBarCode());
        }
        //销售描述
        if (Objects.isNull(importVo.getDescription())) {
//            error.add("销售描述不能为空");
        } else {
            sale.setDescription(importVo.getDescription().trim());
        }
        purchaseSaleStockReqVos.add(sale);
        this.resp.setPurchaseSaleStockReqVos(purchaseSaleStockReqVos);
        this.resp.setProductSkuBoxPackingDrafts(productSkuBoxPackingDrafts);
        return this;
    }

    //检查结算信息
    public CheckSkuUpdate checkSettlement() {
        ProductSkuCheckoutDraft draft = new ProductSkuCheckoutDraft();
        draft.setSkuCode(this.resp.getProductSkuDraft().getSkuCode());
        draft.setSkuName(this.resp.getProductSkuDraft().getSkuName());
        //结算方式
        if (Objects.isNull(importVo.getSettlementMethodName())) {
//            error.add("结算方式不能为空");
        } else {
            SupplierDictionaryInfo info = dicMap.get(importVo.getSettlementMethodName().trim());
            if (Objects.isNull(info)) {
                error.add("未找到该名称对应的结算方式");
            } else {
                draft.setSettlementMethodName(info.getSupplierContent());
                draft.setSettlementMethodCode(info.getSupplierDictionaryValue());
            }
        }
        //进项税率
        if (Objects.isNull(importVo.getInputTaxRate())) {
//            error.add("进项税率不能为空");
        } else {
            try {
                draft.setInputTaxRate(NumberConvertUtils.stringParseLong(importVo.getInputTaxRate()));
            } catch (Exception e) {
                error.add("进项税率格式不正确");
            }
        }
        //销项税率
        if (Objects.isNull(importVo.getOutputTaxRate())) {
//            error.add("销项税率不能为空");
        } else {
            try {
                draft.setOutputTaxRate(NumberConvertUtils.stringParseLong(importVo.getOutputTaxRate()));
            } catch (Exception e) {
                error.add("销项税率格式不正确");
            }
        }
        //积分系数
        if (Objects.isNull(importVo.getIntegralCoefficient())) {
//            error.add("积分系数不能为空");
        } else {
            try {
                draft.setIntegralCoefficient(Long.parseLong(importVo.getIntegralCoefficient()));
            } catch (Exception e) {
                error.add("积分系数格式不正确");
            }
        }
        //物流费奖励比例
        if (Objects.isNull(importVo.getLogisticsFeeAwardRatio())) {
//            error.add("物流费奖励比例不能为空");
        } else {
            try {
                draft.setLogisticsFeeAwardRatio(NumberConvertUtils.stringParseBigDecimal(importVo.getLogisticsFeeAwardRatio()).multiply(BigDecimal.valueOf(100)));
            } catch (Exception e) {
                error.add("物流费奖励比例格式不正确");
            }
        }
        this.resp.setProductSkuCheckoutDraft(draft);
        return this;
    }

    //检查厂家
    public CheckSkuUpdate checkManufacturer() {
        //生产厂家
        if (Objects.nonNull(importVo.getManufacturerName())) {
            Manufacturer manufacturer = manufactureMap.get(importVo.getManufacturerName().trim());
            if (Objects.isNull(manufacturer)) {
                error.add("未找到对应名称的生产厂家");
            } else {
                List<ProductSkuManufacturerDraft> manufacturerDrafts = Lists.newArrayList();
                ProductSkuManufacturerDraft draft = new ProductSkuManufacturerDraft();
                draft.setManufacturerName(manufacturer.getName());
                draft.setManufacturerCode(manufacturer.getManufacturerCode());
                draft.setIsDefault((byte)1);
                //厂方商品编号
                if (Objects.isNull(importVo.getFactoryProductNumber())) {
                    error.add("厂方商品编号不能为空");
                } else {
                    draft.setFactoryProductNumber(importVo.getFactoryProductNumber());
                }
                //保修地址
                if (Objects.isNull(importVo.getAddress())) {
                    error.add("保修地址不能为空");
                } else {
                    draft.setAddress(importVo.getAddress());
                }
                manufacturerDrafts.add(draft);
                this.resp.setProductSkuManufacturerDrafts(manufacturerDrafts);
            }
        }
        return this;
    }

    //检查图片
    public CheckSkuUpdate checkPic() {
        if (Objects.isNull(importVo.getPicFolderCode())) {
//                error.add("图片文件夹编号不能为空");
        }else {
            this.resp.getProductSkuDraft().setPicFolderCode(importVo.getPicFolderCode().trim());
        }
        return this;
    }

    //返回实体
    public AddSkuInfoReqVO getRespVO() {
        return this.resp;
    }

    //返回检查sku+供应商重复的校验map
    public Map<String, String> getReapMap() {
        return this.repeatMap;
    }

    //返回spu重复校验的map
    public Map<String, NewProduct> getSpuMap() {
        return this.spuMap;
    }

    //返回导入的vo
    public SkuInfoImport getSkuInfoImport() {
        this.importVo.setError(StringUtils.strip(this.error.toString(), "[]"));
        return this.importVo;
    }
}