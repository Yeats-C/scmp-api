package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.common.StatusTypeCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.request.sku.PurchaseSaleStockReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.Supplier;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author knight.xie
 * @version 1.0
 * @className GetChangeValueUtil
 * @date 2019/7/17 18:55
 */
@Slf4j
public class GetChangeValueUtil<T> {

    public static Map<String,String> skuHeadMap = Maps.newHashMap();
    public static Map<String,String> headMap = Maps.newHashMap();
    /** 进销存map **/
    public static Map<String,String> purchaseSaleStockHeadMap = Maps.newHashMap();
    static {
        skuHeadMap.put("goodsGifts","类型");
        skuHeadMap.put("skuName","SKU名称");
        skuHeadMap.put("skuAbbreviation","SKU简称");
        skuHeadMap.put("productBrandName","品牌");
        skuHeadMap.put("productCategoryName","品类");
        skuHeadMap.put("productName","所属SPU");
        skuHeadMap.put("productPropertyName","商品属性");
        skuHeadMap.put("productSortName","所属部门");
        skuHeadMap.put("procurementSectionName","采购组");
        skuHeadMap.put("colorName","颜色");
        skuHeadMap.put("modelNumber","型号");
        skuHeadMap.put("managementStyleName","管理方式");
        skuHeadMap.put("qualityAssuranceManagement","保质管理");
        skuHeadMap.put("quality_number","保质数");
        skuHeadMap.put("qualityDate","保质");
        skuHeadMap.put("categoriesSupplyChannelsName","供货渠道类别");
        skuHeadMap.put("mnemonicCode","助记码");
        skuHeadMap.put("manufacturerGuidePrice","厂商指导价");
        skuHeadMap.put("applicableMonthAge","适用起始月龄");
        skuHeadMap.put("seasonalGoods","是否季节商品");
        skuHeadMap.put("warehouseTypeName","仓位类型");
        skuHeadMap.put("structuralGoods","结构性商品");
        skuHeadMap.put("useTime","使用时长");
        skuHeadMap.put("inventoryModel","库存模式");
        skuHeadMap.put("uniqueCode","唯一码管理");
        skuHeadMap.put("remark","备注");
        skuHeadMap.put("productDesc","商品介绍");

        headMap.put("id","主键ID");
        headMap.put("supplierName","供应商集团名称");
        headMap.put("supplierAbbreviation","供应商集团简称");

        purchaseSaleStockHeadMap.put("type", "类型");
        purchaseSaleStockHeadMap.put("spec", "规格");
        purchaseSaleStockHeadMap.put("unitName", "单位");
        purchaseSaleStockHeadMap.put("baseProductContent", "单位含量");
        purchaseSaleStockHeadMap.put("zeroRemovalCoefficient", "交易倍数");
        purchaseSaleStockHeadMap.put("barCode", "条形码");
        purchaseSaleStockHeadMap.put("description", "描述");
        purchaseSaleStockHeadMap.put("maxOrderNum", "最大订购数量");
        purchaseSaleStockHeadMap.put("isDefault", "是否默认");
    }
    private Map<String,String> compareObject(Object oldBean, Object newBean) {
        Map<String,String> changeMap = Maps.newHashMap();
        T pojo1 = (T) oldBean;
        T pojo2 = (T) newBean;
        try {
            Class clazz = pojo1.getClass();
            Field[] fields = pojo1.getClass().getDeclaredFields();
            int i = 1;
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(pojo1);
                Object o2 = getMethod.invoke(pojo2);
                String str = "";
                if (o1 == null && o2 == null ) {
                    continue;
                }
                if (o1 == null && o2 != null) {
                    str =  "从\"空\"改为\"" + o2+"\"";
                } else if(o1 != null && o2 == null){
                    str =  "从\"" + o1 + "\"改为\"\"";
                } else if (!o1.toString().equals(o2.toString())) {
                    if("goodsGifts".equals(field.getName())){
                       o1 = SkuTypeEnum.getSkuTypeEnumByType((Byte) o1).getName();
                       o2 = SkuTypeEnum.getSkuTypeEnumByType((Byte) o2).getName();
                    }
                    if("qualityAssuranceManagement".equals(field.getName())){
                        o1 = QualityAssuranceManagements.getByType((Byte) o1).getName();
                        o2 = QualityAssuranceManagements.getByType((Byte) o2).getName();
                    }
                    if("qualityDate".equals(field.getName())){
                        o1 = QualityTypes.getByType((Byte) o1).getName();
                        o2 = QualityTypes.getByType((Byte) o2).getName();
                    }
                    if("seasonalGoods".equals(field.getName()) ||
                            "structuralGoods".equals(field.getName()) ||
                            "uniqueCode".equals(field.getName())){
                        o1 = Generals.getByType((Byte) o1).getName();
                        o2 = Generals.getByType((Byte) o2).getName();
                    }
                    if("inventoryModel".equals(field.getName())){
                        o1 = InventoryModels.getByType((Byte) o1).getName();
                        o2 = InventoryModels.getByType((Byte) o2).getName();
                    }
                    if("type".equals(field.getName())){
                        o1 = PurchaseSaleStockType.getSkuTypeEnumByType((Byte) o1).getName();
                        o2 = PurchaseSaleStockType.getSkuTypeEnumByType((Byte) o2).getName();
                    }
                    str =  "从\"" + o1 + "\"改为\"" + o2+"\"";
                }
                if (StringUtils.isNotBlank(str)) {

                    changeMap.put(field.getName(),str);
                }
            }
        } catch (Exception e) {
            log.error(Global.ERROR, e);
        }
        return changeMap;
    }

    public String compareResult(Object oldBean, Object newBean,Map<String,String> headMap){
        Map<String, String> compareObject = compareObject(oldBean, newBean);
        Map<String,String> resultMap = Maps.newHashMap();
        compareObject.forEach((k,v)->{
            if(headMap.containsKey(k)){
                resultMap.put(headMap.get(k),v);
            }
        });
        return resultMap.toString().replace("{","").replace("}","").replace("=",":");
    }



    public static void main(String[] args) {
        Supplier old = new Supplier();
        old.setSupplierName("张三");
        old.setSupplierAbbreviation("张三2");

        Supplier supplier = new Supplier();
        supplier.setId(2L);
        supplier.setSupplierName("张三2");
        supplier.setSupplierAbbreviation("");
        String compareResult = new GetChangeValueUtil<Supplier>().compareResult(old, supplier, headMap);
        System.out.println(compareResult);

        PurchaseSaleStockReqVo oldVo = new PurchaseSaleStockReqVo();
        oldVo.setUnitName("个");
        oldVo.setType((StatusTypeCode.STOCK.getStatus()));
        PurchaseSaleStockReqVo newVo = new PurchaseSaleStockReqVo();
        newVo.setUnitName("箱");
        newVo.setType(StatusTypeCode.PURCHASE.getStatus());
        String cResult = new GetChangeValueUtil<PurchaseSaleStockReqVo>().compareResult(oldVo, newVo, purchaseSaleStockHeadMap);
        System.out.println(cResult);
    }


}
