package com.aiqin.bms.scmp.api.supplier.domain;

import lombok.Getter;

import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className FilePathEnum
 * @date 2019/10/15 14:50
 */
@Getter
public enum  FilePathEnum {
    SUPPLIER_FILE("supplierFile","supplier-file/","供应商文件"),
    SUPPLIER_PICTURE("supplierPicture","supplier-picture/","供应商图片"),
    BRAND_PICTURE("brandPicture","brand-picture/","品牌图片"),
    CATEGORY_PICTURE("categoryPicture","category-picture/","品类图片"),
    INSPECTION_REPORT("inspectionReport","inspection-report/","质检报告图片"),
    VARIABLE_PRICE("variablePrice","variable-price/","变价资质图片"),
    CONTRACT_FILE("contractFile","contract-file/","合同文件"),
    PRODUCT_FILE("productFile","product-file/","商品文件"),
    PRODUCT_PICTURE("productPicture","product-picture/","商品图片"),
    PURCHASE_FILE("purchaseFile","purchase-file/","采购文件"),
    RETURN_FILE("returnFile","return-file/","退供文件"),
    RETURN_PICTURE("returnPicture","return-picture/","退供图片"),
    GOODS_COMPANY("goodsCompany","goods-company/","供应商审批图片"),

    ;
    private String code;
    private String filePath;
    private String desc;
    FilePathEnum (String code,String filePath,String desc) {
        this.code = code;
        this.filePath = filePath;
        this.desc = desc;
    }
    public static final String getFilePathEnumByCode(String code){
        for (FilePathEnum value : FilePathEnum.values()) {
            if(Objects.equals(value.code,code)){
                return value.filePath;
            }
        }
        return null;
    }
}
