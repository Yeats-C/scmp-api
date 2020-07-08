package com.aiqin.bms.scmp.api.product.service.impl.skuimport;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.StatusTypeCode;
import com.aiqin.bms.scmp.api.product.domain.ProductBrandType;
import com.aiqin.bms.scmp.api.product.domain.ProductCategory;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuInfoImport;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuInfoImportNewReally;
import com.aiqin.bms.scmp.api.product.domain.excel.SkuInfoImportReally;
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
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class CheckSkuNewReally {

    AddSkuInfoReqVO resp;
    Map<String, ProductSkuInfo> productSkuMap;
    Map<String, SupplyCompany> supplyCompanyMap;
    Map<String, ProductBrandType> brandMap;
    Map<String, ProductCategory> categoryMap;
    Map<String, PriceChannel> channelMap;
    Map<String, TagInfo> skuTagMap;
    Map<String, NewProduct> spuMap;
    Map<String, String> repeatMap;
    SkuInfoImportReally importVo;
    List<String> error;
    Map<String, SupplierDictionaryInfo> dicMap;
    Map<String, SupplierDictionaryInfo> dicMap2;
    Map<String, Manufacturer> manufactureMap;
    Map<String, ProductSkuDraft> productSkuDraftMap;
    Map<String, PurchaseGroupDTO> purchaseGroupMap;
    private static String picFolderCodeKey = "picFolderCode_";

    private CheckSkuNewReally() {
    }

    public CheckSkuNewReally(Map<String, ProductSkuInfo> productSkuMap, Map<String, SupplyCompany> supplyCompanyMap, Map<String, ProductBrandType> brandMap, Map<String, ProductCategory> categoryMap, Map<String, PriceChannel> channelMap, Map<String, TagInfo> skuTagMap, Map<String, String> repeatMap, Object importVo, Map<String, NewProduct> spuMap, Map<String, SupplierDictionaryInfo> dicMap, Map<String, Manufacturer> manufactureMap, Map<String, SupplierDictionaryInfo> dicMap2) {
        this.error = Lists.newArrayList();
        this.resp = new AddSkuInfoReqVO();
        this.productSkuMap = productSkuMap;
        this.supplyCompanyMap = supplyCompanyMap;
        this.brandMap = brandMap;
        this.categoryMap = categoryMap;
        this.channelMap = channelMap;
        this.skuTagMap = skuTagMap;
        this.repeatMap = repeatMap;
        this.importVo = BeanCopyUtils.copy(importVo, SkuInfoImportReally.class);
        this.spuMap = spuMap;
        this.dicMap = dicMap;
        this.dicMap2 = dicMap2;
        this.manufactureMap = manufactureMap;
    }

    //检查重复
    public CheckSkuNewReally checkRepeat() {
        return this;
    }

    //新增检查sku
    public CheckSkuNewReally checkSKuNew() {
        ProductSkuDraft draft = BeanCopyUtils.copy(importVo, ProductSkuDraft.class);
        //sku名称
        if (Objects.isNull(importVo.getSkuName())) {
            error.add("SKU名称不能为空");
        } else {
            ProductSkuInfo sku = productSkuMap.get(importVo.getSkuName());
            if (Objects.nonNull(sku)) {
                error.add("sku名称已存在");
            }
        }
        this.resp.setProductSkuDraft(draft);
        return this;
    }

    //检查基础数据和spu
    public CheckSkuNewReally checkBaseDate() {
        ProductSkuDraft productSkuDraft = this.resp.getProductSkuDraft();
        //类型
        if (Objects.isNull(importVo.getGoodsGiftsDesc())) {
            error.add("类型不能为空");
        } else {
            StatusTypeCode code = StatusTypeCode.getAll().get(importVo.getGoodsGiftsDesc());
            if (Objects.isNull(code)) {
                error.add("类型值错误，请填写商品或赠品");
            } else {
                productSkuDraft.setGoodsGifts(code.getStatus());
            }
        }
        //sku简称
        if (Objects.isNull(importVo.getSkuAbbreviation())) {
//                error.add("SKU简称不能为空");
        }
        //品牌
        if (Objects.isNull(importVo.getProductBrandName())) {
            error.add("品牌不能为空");
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
            error.add("品类不能为空");
        } else {
//            String[] split = importVo.getProductCategoryName().split("-");
//            if (split.length != 4) {
//                error.add("品类应为四级用\"-\"分割");
//            } else {
//                boolean flag = true;
//                ProductCategory current = null;
//                for (int i = split.length - 1; i >= 0; i--) {
//                    ProductCategory productCategory = categoryMap.get(split[i] + "," + (i + 1));
//                    if (Objects.isNull(productCategory)) {
//                        error.add("无对应名称为" + split[i] + "的品牌信息");
//                        flag = false;
//                        break;
//                    } else {
//                        if (split.length - 1 == i) {
//                            current = productCategory;
//                        } else {
//                            if (!productCategory.getCategoryId().equals(current.getParentId())) {
//                                error.add("品牌名为" + current.getCategoryName() + "的上级名称不为" + split[i]);
//                                flag = false;
//                                break;
//                            } else {
//                                current = productCategory;
//                            }
//                        }
//                    }
//                }
//                if (flag) {
//                    productSkuDraft.setProductCategoryCode(categoryMap.get(split[split.length - 1] + "," + 4).getCategoryId());
//                    productSkuDraft.setProductCategoryName(categoryMap.get(split[split.length - 1] + "," + 4).getCategoryName());
//                }
//            }
            ProductCategory productCategory = categoryMap.get(importVo.getProductCategoryName());
            if (Objects.isNull(productCategory)) {
                error.add("无对应编码为" + importVo.getProductCategoryName() + "的品类信息");
            } else {
                productSkuDraft.setProductCategoryCode(productCategory.getCategoryId());
                productSkuDraft.setProductCategoryName(productCategory.getCategoryName());
            }
        }
        //spu
        if (Objects.isNull(importVo.getProductName())) {
            error.add("所属SPU不能为空");
        } else {
            NewProduct newProduct = spuMap.get(importVo.getProductName().trim());
            if (Objects.isNull(newProduct)) {
                spuMap.put(importVo.getProductName().trim(), new NewProduct());
            } else {
                productSkuDraft.setProductCode(newProduct.getProductCode());
            }
            NewProduct spuInfo = BeanCopyUtils.copy(importVo, NewProduct.class);
            if (spuInfo != null) {
                spuInfo.setAbbreviation(importVo.getSpuAbbreviation());
                spuInfo.setBarCode(importVo.getSpuMnemonicCode());
                this.resp.setSpuInfo(spuInfo);
            }
        }
        //商品属性
        if (Objects.isNull(importVo.getProductPropertyName())) {
            error.add("商品属性不能为空");
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
            error.add("所属部门不能为空");
        } else {
            SupplierDictionaryInfo dic = dicMap.get(importVo.getProductSortName());
            if (Objects.isNull(dic)) {
                error.add("无对应名称的所属部门");
            } else {
                productSkuDraft.setProductSortCode(dic.getSupplierDictionaryValue());
            }
        }
//        boolean flag1 = false;
//        boolean flag2 = false;
        //颜色
        if (Objects.nonNull(importVo.getColorName())) {
//                error.add("颜色不能为空");
//            flag1 = true;
        }
        //型号
        if (Objects.nonNull(importVo.getModelNumber())) {
//                error.add("型号不能为空");
//            flag2 = true;
        }
//        if (flag1&&flag2) {
//            error.add("颜色和型号只能填写一个");
//        } else if(!(flag1 || flag2)){
//            error.add("颜色和型号必须填写一个");
//        }
        //是否管理保质期
//        if (Objects.isNull(importVo.getQualityAssuranceManagementDesc())) {
////            error.add("是否管理保质期不能为空");
//        } else {
//            QualityAssuranceManagements e = QualityAssuranceManagements.getAll().get(importVo.getQualityAssuranceManagementDesc());
//            if (Objects.isNull(e)) {
//                error.add("是否管理保质期请选择管理或者不管理");
//            } else {
//                productSkuDraft.setQualityAssuranceManagement(e.getType());
//            }
//            if (e.getType().equals((byte) 0)) {
//                //管理
//                //保质期单位
//                if (Objects.isNull(importVo.getQualityNumber())) {
//                    error.add("保质期单位不能为空");
//                } else {
//                    QualityTypes type = QualityTypes.getAll().get(importVo.getQualityNumber());
//                    if (Objects.isNull(type)) {
//                        error.add("保质期单位只能是年月天");
//                    } else {
//                        productSkuDraft.setQualityNumber(type.getType().toString());
//                    }
//                }
//                //保质天数
//                if (Objects.isNull(importVo.getQualityDate())) {
//                    error.add("保质天数不能为空");
//                }else {
//                    productSkuDraft.setQualityDate(Integer.parseInt(importVo.getQualityDate())+"");
//                }
//            }
//        }
        //保质天数
        if (StringUtils.isBlank(importVo.getQualityDate())) {
            error.add("保质天数不能为空");
        } else {
            try {
                int i = Integer.parseInt(importVo.getQualityDate());
                if (i == 0) {
                    productSkuDraft.setQualityAssuranceManagement(QualityAssuranceManagements.NO.getType());
                } else if (i > 0) {
                    productSkuDraft.setQualityAssuranceManagement(QualityAssuranceManagements.YES.getType());
                    productSkuDraft.setQualityNumber(QualityTypes.DAY.getType().toString());
                    productSkuDraft.setQualityDate(i + "");
                }
            } catch (NumberFormatException e) {
                error.add("保质天数格式不正确");
            }
        }

        //供货渠道类别
        if (Objects.isNull(importVo.getSupplyCategoriesSupplyChannelsName())) {
            error.add("供货渠道类别不能为空");
        } else {
            SupplierDictionaryInfo info = dicMap2.get(importVo.getSupplyCategoriesSupplyChannelsName());
            if (Objects.isNull(info)) {
                error.add("无对应的名称的供货渠道类别");
            } else {
                productSkuDraft.setCategoriesSupplyChannelsCode(info.getSupplierDictionaryValue());
                //库存模式
                boolean b = "直送".equals(importVo.getSupplyCategoriesSupplyChannelsName());
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
            error.add("厂家指导价不能为空");
        } else {
            try {
                productSkuDraft.setManufacturerGuidePrice(new BigDecimal(importVo.getManufacturerGuidePrice()));
            } catch (NumberFormatException e) {
                error.add("厂家指导价格式不正确");
            }
        }
        //适用其实月龄
        if (StringUtils.isBlank(importVo.getApplicableMonthAge())) {
            error.add("适用起始月龄不能为空");
        } else {
            productSkuDraft.setApplicableMonthAge(importVo.getApplicableMonthAge().trim());
        }
        //是否季节商品
        if (Objects.isNull(importVo.getSeasonalGoodsDesc())) {
            error.add("是否季节商品不能为空");
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
            error.add("仓位类型不能为空");
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
            error.add("是否结构性商品不能为空");
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
        //唯一码管理
        if (Objects.isNull(importVo.getUniqueCodeDesc())) {
            error.add("唯一码管理不能为空");
        } else {
            Generals generals = Generals.getAll().get(importVo.getUniqueCodeDesc());
            if (Objects.isNull(generals)) {
                error.add("唯一码管理请填写是或者否");
            } else {
                productSkuDraft.setUniqueCode(generals.getType());
            }
        }
        //特征
        if (Objects.isNull(importVo.getFeatureName())) {
            error.add("特征不能为空");
        } else {
            StatusTypeCode typeCode = StatusTypeCode.getAll().get(importVo.getFeatureName());
            if (Objects.isNull(typeCode)) {
                error.add("特征格式不正确");
            } else {
                productSkuDraft.setFeatureCode(typeCode.getStatus().toString());
            }
        }
        //通货等级
        if (Objects.isNull(importVo.getCurrencyLevelName())) {
            error.add("通货等级不能为空");
        } else {
            StatusTypeCode typeCode = StatusTypeCode.getAll().get(importVo.getCurrencyLevelName());
            if (Objects.isNull(typeCode)) {
                error.add("通货等级格式不正确");
            } else {
                productSkuDraft.setCurrencyLevelCode(typeCode.getStatus().toString());
            }
        }
        //覆盖渠道
        if (Objects.isNull(importVo.getPriceChannelName())) {
            error.add("覆盖渠道不能为空");
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
        if (false) {
        }
        //商品描述
        if (false) {
        }
        //管理方式默认写死
        productSkuDraft.setManagementStyleCode("1");
        productSkuDraft.setManagementStyleName("只管理数量");
        this.resp.setProductSkuDraft(productSkuDraft);
        return this;
    }

    //检查进销存包装
    public CheckSkuNewReally checkInvoice() {
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
            error.add("库存规格不能为空");
        } else {
            stock.setSpec(importVo.getStockSpec());
        }
        //库存单位
        if (Objects.isNull(importVo.getStockUnitName())) {
            error.add("库存单位不能为空");
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
        //如果长宽高体积毛重净重为空,则不存库存包装信息
        boolean stockNotSave = StringUtils.isBlank(importVo.getStockBoxLength()) && StringUtils.isBlank(importVo.getStockBoxWidth())
                && StringUtils.isBlank(importVo.getStockBoxHeight()) && StringUtils.isBlank(importVo.getStockBoxVolume())
                && StringUtils.isBlank(importVo.getStockBoxGrossWeight()) && StringUtils.isBlank(importVo.getStockNetWeight());
        if (!stockNotSave) {
            ProductSkuBoxPackingDraft stockBox = new ProductSkuBoxPackingDraft();
            stockBox.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
            stockBox.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
            stockBox.setLargeUnit(stock.getUnitName());
            stockBox.setUnitCode(stock.getUnitCode());
            boolean flag = true;
            try {
                if (StringUtils.isNotBlank(importVo.getStockBoxLength())) {
                    BigDecimal bigDecimalLength = new BigDecimal(importVo.getStockBoxLength().trim());
                    Long longLength = bigDecimalLength.longValue();
                    if (new BigDecimal(longLength).compareTo(bigDecimalLength) == 0) {
                    } else {
                        //小数
                        throw new BizException("库存长格式不正确");
                    }
                    stockBox.setBoxLength(longLength);
                } else {
                    flag = false;
                }

            } catch (Exception e) {
                error.add("库存长格式不正确");
                flag = false;
            }
            try {
                if (StringUtils.isNotBlank(importVo.getStockBoxWidth())) {
                    BigDecimal bigDecimalWidth = new BigDecimal(importVo.getStockBoxWidth().trim());
                    Long longWidth = bigDecimalWidth.longValue();
                    if (new BigDecimal(longWidth).compareTo(bigDecimalWidth) == 0) {
                    } else {
                        //小数
                        throw new BizException("库存宽格式不正确");
                    }
                    stockBox.setBoxWidth(longWidth);
                } else {
                    flag = false;
                }

            } catch (Exception e) {
                error.add("库存宽格式不正确");
                flag = false;
            }
            try {
                if (StringUtils.isNotBlank(importVo.getStockBoxHeight())) {
                    BigDecimal bigDecimalHeight = new BigDecimal(importVo.getStockBoxHeight().trim());
                    Long longHeight = bigDecimalHeight.longValue();
                    if (new BigDecimal(longHeight).compareTo(bigDecimalHeight) == 0) {
                    } else {
                        //小数
                        throw new BizException("库存高格式不正确");
                    }
                    stockBox.setBoxHeight(longHeight);
                } else {
                    flag = false;
                }
            } catch (Exception e) {
                error.add("库存高格式不正确");
                flag = false;
            }
            try {
                if (StringUtils.isNotBlank(importVo.getStockBoxVolume())) {
                    BigDecimal bigDecimalVolume = new BigDecimal(importVo.getStockBoxVolume().trim());
                    Long longVolume = bigDecimalVolume.longValue();
                    if (new BigDecimal(longVolume).compareTo(bigDecimalVolume) == 0) {
                    } else {
                        //小数
                        throw new BizException("库存体积格式不正确");
                    }
                    stockBox.setBoxVolume(longVolume);
                    flag = false;
                }
            } catch (Exception e) {
                error.add("库存体积格式不正确");
            }
            if (flag) {
                // 长宽高有一个没有填就不算，体积填了不算，只有长宽高全都填对了才算体积
                stockBox.setBoxVolume(stockBox.getBoxLength() * stockBox.getBoxWidth() * stockBox.getBoxHeight());
            }
            try {
                if (StringUtils.isNotBlank(importVo.getStockBoxGrossWeight())) {
                    stockBox.setBoxGrossWeight(new BigDecimal(importVo.getStockBoxGrossWeight().trim()));
                } else {
                    error.add("库存毛重不能为空");
                }
            } catch (Exception e) {
                error.add("库存毛重格式不正确");
            }

            if (StringUtils.isNotBlank(importVo.getStockNetWeight())) {
                try {
                    stockBox.setNetWeight(new BigDecimal(importVo.getStockNetWeight()));

                } catch (Exception e) {
                    error.add("库存净重格式不正确");
                }
            }
            productSkuBoxPackingDrafts.add(stockBox);
        }
        //库存条形码 默认使用销售条形码
//        if (Objects.isNull(importVo.getStockBarCode())) {
//            error.add("库存条形码不能为空");
//        } else {
        stock.setBarCode(importVo.getSaleBarCode());
//        }
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
            error.add("采购规格不能为空");
        } else {
            purchase.setSpec(importVo.getPurchaseSpec());
        }
        //采购单位
        if (Objects.isNull(importVo.getPurchaseUnitName())) {
            error.add("采购单位不能为空");
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
        //如果长宽高体积毛重净重为空,则不存包装信息
        if (Objects.nonNull(importVo.getStockUnitName()) && Objects.nonNull(importVo.getPurchaseUnitName()) && (!importVo.getStockUnitName().equals(importVo.getPurchaseUnitName()))) {
            boolean purchaseBoxNotSave = StringUtils.isBlank(importVo.getPurchaseBoxLength()) && StringUtils.isBlank(importVo.getPurchaseBoxWidth())
                    && StringUtils.isBlank(importVo.getPurchaseBoxHeight()) && StringUtils.isBlank(importVo.getPurchaseBoxVolume())
                    && StringUtils.isBlank(importVo.getPurchaseBoxGrossWeight()) && StringUtils.isBlank(importVo.getPurchaseNetWeight());
            if (!purchaseBoxNotSave) {
                ProductSkuBoxPackingDraft purchaseBox = new ProductSkuBoxPackingDraft();
                purchaseBox.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
                purchaseBox.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
                purchaseBox.setLargeUnit(purchase.getUnitName());
                purchaseBox.setUnitCode(purchase.getUnitCode());
                boolean flag = true;
                try {
                    if (StringUtils.isNotBlank(importVo.getPurchaseBoxLength())) {
                        BigDecimal bigDecimalLength = new BigDecimal(importVo.getPurchaseBoxLength().trim());
                        Long longLength = bigDecimalLength.longValue();
                        if (new BigDecimal(longLength).compareTo(bigDecimalLength) == 0) {
                        } else {
                            //小数
                            throw new BizException("采购长格式不正确");
                        }
                        purchaseBox.setBoxLength(longLength);
                    } else {
                        flag = false;
                    }
                } catch (Exception e) {
                    error.add("采购长格式不正确");
                    flag = false;
                }
                try {
                    if (StringUtils.isNotBlank(importVo.getPurchaseBoxWidth())) {
                        BigDecimal bigDecimalWidth = new BigDecimal(importVo.getPurchaseBoxWidth().trim());
                        Long longWidth = bigDecimalWidth.longValue();
                        if (new BigDecimal(longWidth).compareTo(bigDecimalWidth) == 0) {
                        } else {
                            //小数
                            throw new BizException("采购宽格式不正确");
                        }
                        purchaseBox.setBoxWidth(longWidth);
                    } else {
                        flag = false;
                    }
                } catch (Exception e) {
                    error.add("采购宽格式不正确");
                    flag = false;
                }
                try {
                    if (StringUtils.isNotBlank(importVo.getPurchaseBoxHeight())) {
                        BigDecimal bigDecimalHeight = new BigDecimal(importVo.getPurchaseBoxHeight().trim());
                        Long longHeight = bigDecimalHeight.longValue();
                        if (new BigDecimal(longHeight).compareTo(bigDecimalHeight) == 0) {
                        } else {
                            //小数
                            throw new BizException("采购高格式不正确");
                        }
                        purchaseBox.setBoxHeight(longHeight);
                    } else {
                        flag = false;
                    }
                } catch (Exception e) {
                    error.add("采购高格式不正确");
                    flag = false;
                }
                try {
                    if (StringUtils.isNotBlank(importVo.getPurchaseBoxVolume())) {
                        BigDecimal bigDecimalVolume = new BigDecimal(importVo.getPurchaseBoxVolume().trim());
                        Long longVolume = bigDecimalVolume.longValue();
                        if (new BigDecimal(longVolume).compareTo(bigDecimalVolume) == 0) {
                        } else {
                            //小数
                            throw new BizException("采购体积格式不正确");
                        }
                        purchaseBox.setBoxVolume(longVolume);
                        flag = false;
                    }
                } catch (Exception e) {
                    error.add("采购体积格式不正确");
                }
                if (flag) {
                    purchaseBox.setBoxVolume(purchaseBox.getBoxLength() * purchaseBox.getBoxWidth() * purchaseBox.getBoxHeight());
                }
                if (StringUtils.isNotBlank(importVo.getPurchaseBoxGrossWeight())) {
                    try {
                        purchaseBox.setBoxGrossWeight(new BigDecimal(importVo.getPurchaseBoxGrossWeight().trim()));
                    } catch (Exception e) {
                        error.add("采购毛重格式不正确");
                    }
                } else {
                    error.add("采购毛重不能为空");
                }
                if (StringUtils.isNotBlank(importVo.getPurchaseNetWeight())) {
                    try {
                        purchaseBox.setNetWeight(new BigDecimal(importVo.getPurchaseNetWeight()));
                    } catch (Exception e) {
                        error.add("采购净重格式不正确");
                    }
                }
                productSkuBoxPackingDrafts.add(purchaseBox);
            }
        }
        //采购基商品含量
        if (Objects.isNull(importVo.getPurchaseBaseProductContent())) {
            error.add("采购基商品含量不能为空");
        } else {
            try {
                purchase.setBaseProductContent(Integer.parseInt(importVo.getPurchaseBaseProductContent()));
            } catch (Exception e) {
                error.add("采购基商品含量不正确");
            }
        }
        //采购拆零系数 默认是1
        purchase.setZeroRemovalCoefficient((long) 1);
//        if (Objects.isNull(importVo.getPurchaseZeroRemovalCoefficient())) {
//            error.add("采购拆零系数不能为空");
//        } else {
//            try {
//                purchase.setZeroRemovalCoefficient(Long.parseLong(importVo.getPurchaseZeroRemovalCoefficient()));
//            } catch (Exception e) {
//                error.add("采购拆零系数不正确");
//            }
//        }
        //采购条形码
        if (Objects.isNull(importVo.getPurchaseBarCode())) {
            error.add("采购条形码不能为空");
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
        //分销规格
//        if (Objects.isNull(importVo.getDistributionSpec())) {
//            error.add("分销规格不能为空");
//        } else {
        distribution.setSpec(stock.getSpec());
//        }
        //分销单位
//        if (Objects.isNull(importVo.getDistributionUnitName())) {
//            error.add("分销单位不能为空");
//        } else {
//            SupplierDictionaryInfo info = dicMap.get(importVo.getDistributionUnitName());
//            if (Objects.isNull(info)) {
//                error.add("无对应名称为" + importVo.getDistributionUnitName() + "的单位");
//            } else {
        distribution.setUnitCode(stock.getUnitCode());
        distribution.setUnitName(stock.getUnitName());
//            }
//        }
        //分销基商品含量
//        if (Objects.isNull(importVo.getDistributionBaseProductContent())) {
//            error.add("分销基商品含量不能为空");
//        } else {
//            try {
        distribution.setBaseProductContent(1);
//            } catch (Exception e) {
//                error.add("分销基商品含量不正确");
//            }
//        }
        //分销拆零系数
        if (Objects.isNull(importVo.getDistributionZeroRemovalCoefficient())) {
            error.add("分销拆零系数不能为空");
        } else {
            try {
                distribution.setZeroRemovalCoefficient(Long.parseLong(importVo.getDistributionZeroRemovalCoefficient()));
            } catch (Exception e) {
                error.add("分销拆零系数不正确");
            }
        }
        //分销条形码
//        if (Objects.isNull(importVo.getDistributionBarCode())) {
//            error.add("分销条形码不能为空");
//        } else {
        distribution.setBarCode(importVo.getSaleBarCode());
//        }
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
        sale.setIsDefault((byte) 1);
        sale.setType((byte) 3);
        //销售规格
//        if (Objects.isNull(importVo.getSaleSpec())) {
//            error.add("销售规格不能为空");
//        } else {
        sale.setSpec(stock.getSpec());
//        }
        //销售单位
//        if (Objects.isNull(importVo.getSaleUnitName())) {
//            error.add("销售单位不能为空");
//        } else {
//            SupplierDictionaryInfo info = dicMap.get(importVo.getSaleUnitName());
//            if (Objects.isNull(info)) {
//                error.add("无对应名称为" + importVo.getSaleUnitName() + "的单位");
//            } else {
        sale.setUnitCode(stock.getUnitCode());
        sale.setUnitName(stock.getUnitName());
//                if (!Optional.ofNullable(stock.getUnitCode()).orElse("库存").equals(info.getSupplierDictionaryValue())) {
//                    error.add("销售的单位必须和库存的单位一致");
//                }
//            }
//        }
        //销售条形码
        if (Objects.isNull(importVo.getSaleBarCode())) {
            error.add("销售条形码不能为空");
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
    public CheckSkuNewReally checkSettlement() {
        ProductSkuCheckoutDraft draft = new ProductSkuCheckoutDraft();
        draft.setSkuCode(this.resp.getProductSkuDraft().getSkuCode());
        draft.setSkuName(this.resp.getProductSkuDraft().getSkuName());
        //结算方式
        if (Objects.isNull(importVo.getSettlementMethodName())) {
            error.add("结算方式不能为空");
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
            error.add("进项税率不能为空");
        } else {
            try {
                draft.setInputTaxRate(new BigDecimal(importVo.getInputTaxRate()));
            } catch (Exception e) {
                error.add("进项税率格式不正确");
            }
        }
        //销项税率
        if (Objects.isNull(importVo.getOutputTaxRate())) {
            error.add("销项税率不能为空");
        } else {
            try {
                draft.setOutputTaxRate(new BigDecimal(importVo.getOutputTaxRate()));
            } catch (Exception e) {
                error.add("销项税率格式不正确");
            }
        }
        //积分系数
        if (Objects.isNull(importVo.getIntegralCoefficient())) {
//            error.add("积分系数不能为空");
        } else {
            try {
                draft.setIntegralCoefficient(new BigDecimal(importVo.getIntegralCoefficient()));
            } catch (Exception e) {
                error.add("积分系数格式不正确");
            }
        }
        //物流费奖励比例
        if (Objects.isNull(importVo.getLogisticsFeeAwardRatio())) {
//            error.add("物流费奖励比例不能为空");
        } else {
            try {
                draft.setLogisticsFeeAwardRatio(new BigDecimal(importVo.getLogisticsFeeAwardRatio()).multiply(BigDecimal.valueOf(1)));
            } catch (Exception e) {
                error.add("物流费奖励比例格式不正确");
            }
        }
        this.resp.setProductSkuCheckoutDraft(draft);
        return this;
    }

    //检查供应商
    public CheckSkuNewReally checkSupplier() {
        List<ProductSkuSupplyUnitDraft> supply = Lists.newArrayList();
        ProductSkuSupplyUnitDraft supplyUnitDraft = new ProductSkuSupplyUnitDraft();
        supplyUnitDraft.setIsDefault((byte) 1);
        supplyUnitDraft.setProductSkuCode(this.resp.getProductSkuDraft().getSkuCode());
        supplyUnitDraft.setProductSkuName(this.resp.getProductSkuDraft().getSkuName());
        //供应商
        if (Objects.isNull(importVo.getSupplyUnitName())) {
            error.add("供应商不能为空");
        } else {
            SupplyCompany supplyCompany = supplyCompanyMap.get(importVo.getSupplyUnitName().trim());
            if (Objects.isNull(supplyCompany)) {
                error.add("无对应名称的供应商");
            } else {
                String s = repeatMap.get(supplyCompany.getSupplyName() + importVo.getSkuName().trim());
                if (StringUtils.isNotBlank(s)) {
                    error.add("sku名称为:" + importVo.getSkuName() + "下的供应商名称为" + supplyCompany.getSupplyName() + "已重复");
                } else {
                    supplyUnitDraft.setSupplyUnitCode(supplyCompany.getSupplyCode());
                    supplyUnitDraft.setSupplyUnitName(supplyCompany.getSupplyName());
                    repeatMap.put(supplyCompany.getSupplyName() + importVo.getSkuName().trim(), importVo.getSkuName());
                }
            }
        }
        //联营扣点
        if (Objects.nonNull(importVo.getJointFranchiseRate())) {
            try {
                supplyUnitDraft.setJointFranchiseRate(new BigDecimal(importVo.getJointFranchiseRate().trim()));
            } catch (Exception e) {
                error.add("联营扣点格式不正确");
            }
        }
        //返点
        if (Objects.nonNull(importVo.getPoint())) {
            try {
                supplyUnitDraft.setPoint(new BigDecimal(importVo.getPoint().trim()));
            } catch (Exception e) {
                error.add("返点格式不正确");
            }
        }
        //厂商SKU编码
        if (Objects.nonNull(importVo.getFactorySkuCode())) {
            supplyUnitDraft.setFactorySkuCode(importVo.getFactorySkuCode().trim());
        }
        //厂商SKU编码
        if (Objects.nonNull(importVo.getFactorySkuCode())) {
            supplyUnitDraft.setTaxIncludedPrice(importVo.getTaxIncludedPrice());
        }
        //供应商供货渠道类别
        if (Objects.isNull(importVo.getSupplyCategoriesSupplyChannelsName())) {
            error.add("供应商供货渠道类别不能为空");
        } else {
            SupplierDictionaryInfo info = dicMap.get(importVo.getSupplyCategoriesSupplyChannelsName().trim());
            if (Objects.isNull(info)) {
                error.add("未找到对应名称的供应商供货渠道类别");
            } else {
                supplyUnitDraft.setCategoriesSupplyChannelsCode(info.getSupplierDictionaryValue());
                supplyUnitDraft.setCategoriesSupplyChannelsName(info.getSupplierContent());
                // 设置基本sku基本数据的供货渠道类别
                this.resp.getProductSkuDraft().setCategoriesSupplyChannelsCode(info.getSupplierDictionaryValue());
                this.resp.getProductSkuDraft().setCategoriesSupplyChannelsName(info.getSupplierContent());
                //库存模式
                boolean b = "直送".equals(importVo.getSupplyCategoriesSupplyChannelsName());
                if (b) {
                    this.resp.getProductSkuDraft().setInventoryModel(InventoryModels.NO.getType());
                } else {
                    this.resp.getProductSkuDraft().setInventoryModel(InventoryModels.YES.getType());
                }
            }
        }
        supply.add(supplyUnitDraft);
        this.resp.setProductSkuSupplyUnitDrafts(supply);
        return this;
    }

    //检查价格
    public CheckSkuNewReally checkPrice() {
        List<SkuPriceDraftReqVO> priceList = Lists.newArrayList();
        Map<String, SkuPriceDraftReqVO> price = PriceAndWarehouseMap.price;
        //爱亲渠道价
        if (Objects.isNull(importVo.getReadyCol67())) {
            error.add("爱亲渠道价不能为空");
        } else {
            SkuPriceDraftReqVO aiqinChannel1 = price.get("爱亲渠道价");
            SkuPriceDraftReqVO aiqinChannel = new SkuPriceDraftReqVO();

            BeanCopyUtils.copy(aiqinChannel1, aiqinChannel);
            try {
                aiqinChannel.setPriceTax(new BigDecimal(importVo.getReadyCol67()));
            } catch (Exception e) {
                error.add("爱亲渠道价格式不正确");
            }
            priceList.add(aiqinChannel);
        }
        //萌贝树渠道价
        if (Objects.isNull(importVo.getReadyCol68())) {
            error.add("萌贝树渠道价不能为空");
        } else {
            SkuPriceDraftReqVO mengbeishuChannel1 = price.get("萌贝树渠道价");
            SkuPriceDraftReqVO mengbeishuChannel = BeanCopyUtils.copy(mengbeishuChannel1, SkuPriceDraftReqVO.class);
            try {
                mengbeishuChannel.setPriceTax(new BigDecimal(importVo.getReadyCol68()));
            } catch (Exception e) {
                error.add("萌贝树渠道价格式不正确");
            }
            priceList.add(mengbeishuChannel);
        }
        //小红马渠道价
        if (Objects.isNull(importVo.getReadyCol69())) {
            error.add("小红马渠道价不能为空");
        } else {
            SkuPriceDraftReqVO xiaohongmaChannel1 = price.get("小红马渠道价");
            SkuPriceDraftReqVO xiaohongmaChannel = BeanCopyUtils.copy(xiaohongmaChannel1, SkuPriceDraftReqVO.class);
            try {
                xiaohongmaChannel.setPriceTax(new BigDecimal(importVo.getReadyCol69()));
            } catch (Exception e) {
                error.add("小红马渠道价格式不正确");
            }
            priceList.add(xiaohongmaChannel);
        }
        //爱亲分销价
        if (Objects.isNull(importVo.getReadyCol70())) {
            error.add("爱亲分销价不能为空");
        } else {
            SkuPriceDraftReqVO aiqinDistribution1 = price.get("爱亲分销价");
            SkuPriceDraftReqVO aiqinDistribution = BeanCopyUtils.copy(aiqinDistribution1, SkuPriceDraftReqVO.class);
            try {
                aiqinDistribution.setPriceTax(new BigDecimal(importVo.getReadyCol70()));
            } catch (Exception e) {
                error.add("爱亲分销价格式不正确");
            }
            priceList.add(aiqinDistribution);
        }
        //萌贝树分销价
        if (Objects.isNull(importVo.getReadyCol71())) {
            error.add("萌贝树分销价不能为空");
        } else {
            SkuPriceDraftReqVO mengbeishuDistribution1 = price.get("萌贝树分销价");
            SkuPriceDraftReqVO mengbeishuDistribution = BeanCopyUtils.copy(mengbeishuDistribution1, SkuPriceDraftReqVO.class);
            try {
                mengbeishuDistribution.setPriceTax(new BigDecimal(importVo.getReadyCol71()));
            } catch (Exception e) {
                error.add("萌贝树分销价格式不正确");
            }
            priceList.add(mengbeishuDistribution);
        }
        //小红马分销价
        if (Objects.isNull(importVo.getReadyCol72())) {
            error.add("小红马分销价不能为空");
        } else {
            SkuPriceDraftReqVO xiaohongmaDistribution1 = price.get("小红马分销价");
            SkuPriceDraftReqVO xiaohongmaDistribution = BeanCopyUtils.copy(xiaohongmaDistribution1, SkuPriceDraftReqVO.class);
            try {
                xiaohongmaDistribution.setPriceTax(new BigDecimal(importVo.getReadyCol72()));
            } catch (Exception e) {
                error.add("小红马分销价格式不正确");
            }
            priceList.add(xiaohongmaDistribution);
        }
        //爱亲售价
//        if (Objects.isNull(importVo.getReadyCol73())) {
//            error.add("爱亲售价不能为空");
//        } else {
//            SkuPriceDraftReqVO aiqinSale = price.get("爱亲售价");
//            try {
//                aiqinSale.setPriceTax(NumberConvertUtils.stringParseLong(importVo.getReadyCol73()));
//            } catch (Exception e) {
//                error.add("爱亲售价格式不正确");
//            }
//            priceList.add(aiqinSale);
//        }
        //售价
        if (Objects.isNull(importVo.getReadyCol74())) {
            error.add("售价不能为空");
        } else {
            SkuPriceDraftReqVO shoujia1 = price.get("售价");
            SkuPriceDraftReqVO shoujia = BeanCopyUtils.copy(shoujia1, SkuPriceDraftReqVO.class);
            try {
                shoujia.setPriceTax(new BigDecimal(importVo.getReadyCol74()));
            } catch (Exception e) {
                error.add("售价格式不正确");
            }
            priceList.add(shoujia);
        }
        //会员价
        if (Objects.isNull(importVo.getReadyCol75())) {
            error.add("会员价不能为空");
        } else {
            SkuPriceDraftReqVO huiyuanjia1 = price.get("会员价");
            SkuPriceDraftReqVO huiyuanjia = BeanCopyUtils.copy(huiyuanjia1, SkuPriceDraftReqVO.class);
            try {
                huiyuanjia.setPriceTax(new BigDecimal(importVo.getReadyCol75()));
            } catch (Exception e) {
                error.add("会员价格式不正确");
            }
            priceList.add(huiyuanjia);
        }
        this.resp.setProductSkuPrices(priceList);
        return this;
    }

    //检查配置
    public CheckSkuNewReally checkConfig() {
        List<SaveSkuConfigReqVo> configReqVos = Lists.newArrayList();
        Map<String, SaveSkuConfigReqVo> warehouse = PriceAndWarehouseMap.warehouse;
        //华北仓
        if (Objects.isNull(importVo.getReadyCol76())) {
            error.add("华北仓状态不能为空");
        } else {
            SkuStatusEnum statusEnum = SkuStatusEnum.getAllStatus().get(importVo.getReadyCol76());
            if (Objects.isNull(statusEnum)) {
                error.add("无法找到华北仓的状态类型");
            } else {
                SaveSkuConfigReqVo huabei1 = warehouse.get("华北仓");
                SaveSkuConfigReqVo huabei = BeanCopyUtils.copy(huabei1, SaveSkuConfigReqVo.class);
                huabei.setConfigStatus(statusEnum.getStatus());
                huabei.setConfigStatusName(statusEnum.getName());
                configReqVos.add(huabei);
            }
        }
        //华东仓
        if (Objects.isNull(importVo.getReadyCol77())) {
            error.add("华东仓状态不能为空");
        } else {
            SkuStatusEnum statusEnum = SkuStatusEnum.getAllStatus().get(importVo.getReadyCol77());
            if (Objects.isNull(statusEnum)) {
                error.add("无法找到华东仓状态的状态");
            } else {
                SaveSkuConfigReqVo huadong1 = warehouse.get("华东仓");
                SaveSkuConfigReqVo huadong = BeanCopyUtils.copy(huadong1, SaveSkuConfigReqVo.class);
                huadong.setConfigStatus(statusEnum.getStatus());
                huadong.setConfigStatusName(statusEnum.getName());
                configReqVos.add(huadong);
            }
        }
        //华南仓
        if (Objects.isNull(importVo.getReadyCol78())) {
            error.add("华南仓状态不能为空");
        } else {
            SkuStatusEnum statusEnum = SkuStatusEnum.getAllStatus().get(importVo.getReadyCol78());
            if (Objects.isNull(statusEnum)) {
                error.add("无法找到华南仓状态的状态");
            } else {
                SaveSkuConfigReqVo huanan1 = warehouse.get("华南仓");
                SaveSkuConfigReqVo huanan = BeanCopyUtils.copy(huanan1, SaveSkuConfigReqVo.class);
                huanan.setConfigStatus(statusEnum.getStatus());
                huanan.setConfigStatusName(statusEnum.getName());
                configReqVos.add(huanan);
            }
        }
        //西南仓
        if (Objects.isNull(importVo.getReadyCol79())) {
            error.add("西南仓状态不能为空");
        } else {
            SkuStatusEnum statusEnum = SkuStatusEnum.getAllStatus().get(importVo.getReadyCol79());
            if (Objects.isNull(statusEnum)) {
                error.add("无法找到西南仓状态的状态");
            } else {
                SaveSkuConfigReqVo xinan1 = warehouse.get("西南仓");
                SaveSkuConfigReqVo xinan = BeanCopyUtils.copy(xinan1, SaveSkuConfigReqVo.class);
                xinan.setConfigStatus(statusEnum.getStatus());
                xinan.setConfigStatusName(statusEnum.getName());
                configReqVos.add(xinan);
            }
        }
        //华中仓
        if (Objects.isNull(importVo.getReadyCol80())) {
            error.add("华中仓状态不能为空");
        } else {
            SkuStatusEnum statusEnum = SkuStatusEnum.getAllStatus().get(importVo.getReadyCol80());
            if (Objects.isNull(statusEnum)) {
                error.add("无法找到华中仓状态的状态");
            } else {
                SaveSkuConfigReqVo huazhong1 = warehouse.get("华中仓");
                SaveSkuConfigReqVo huazhong = BeanCopyUtils.copy(huazhong1, SaveSkuConfigReqVo.class);
                huazhong.setConfigStatus(statusEnum.getStatus());
                huazhong.setConfigStatusName(statusEnum.getName());
                configReqVos.add(huazhong);
            }
        }
        this.resp.setProductSkuConfigs(configReqVos);
        return this;
    }

    //检查厂家
    public CheckSkuNewReally checkManufacturer() {
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
                draft.setIsDefault((byte) 1);
                //厂方商品编号
                if (Objects.isNull(importVo.getFactoryProductNumber())) {
                    // error.add("厂方商品编号不能为空");
                } else {
                    draft.setFactoryProductNumber(importVo.getFactoryProductNumber());
                }
                //保修地址
                if (Objects.isNull(importVo.getAddress())) {
                    // error.add("保修地址不能为空");
                } else {
                    draft.setAddress(importVo.getAddress());
                }
                manufacturerDrafts.add(draft);
                this.resp.setProductSkuManufacturerDrafts(manufacturerDrafts);
            }
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
    public SkuInfoImportReally getSkuInfoImportReally() {
        this.importVo.setError(StringUtils.strip(this.error.toString(), "[]"));
        return this.importVo;
    }

}