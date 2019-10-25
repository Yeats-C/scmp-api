package com.aiqin.bms.scmp.api.workflow.enumerate;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 * 工作流枚举
 *
 * @author: zth
 * @date: 2019-01-15
 * @time: 14:52
 */
@Getter
public enum WorkFlow {
    /**
     * 合同工作流
     */
    APPLY_CONTRACT(1,"合同申请工作流","SCMP_APPLY_CONTRACT"),
    /**
     * 供应商集团工作流
     */
    APPLY_SUPPLIER(2,"供应商集团申请工作流","SCMP_APPLY_SUPPLIER"),
    /**
     * 供应商工作流
     */
    APPLY_COMPANY(3,"供应商新增工作流","SCMP_APPLY_COMPANY"),
    APPLY_COMPANY_REVISE(3,"供应商修改工作流","SCMP_APPLY_COMPANY_REVISE"),
    /**
     * 账户申请工作流
     */
    APPLY_COMPANY_ACC(4,"账户申请工作流","SCMP_APPLY_COMPANY_ACC"),
    /**
     * 采购申请工作流
     */
    APPLY_PURCHASE(6,"采购申请工作流","SCMP_APPLY_PURCHASE"),
    /**
     * 退供申请工作流
     */
    APPLY_REFUND(7,"退供申请工作流","SCMP_APPLY_REFUND"),
    /**
     * 调拨申请工作流
     */
    APPLY_ALLOCATTION(8,"调拨申请工作流","SCMP_APPLY_ALLOCATTION"),
    /**
     * 商品申请工作流
     */
    APPLY_GOODS(9,"商品新增申请工作流","SCMP_APPLY_GOODS"),
    APPLY_GOODS_REVISE(14,"商品修改申请工作流","SCMP_APPLY_GOODS_REVISE"),
    /**
     * 商品配置申请工作流
     */
    APPLY_GOODS_CONFIG(10,"商品配置申请工作流","SCMP_APPLY_GOODS_WAREHOUSE"),
    /**
     * 商品价格申请工作流
     */
    APPLY_GOODS_PRICE(11,"商品价格申请工作流","SCMP_APPLY_GOODS_PRICE"),
    /**
     * 商品变价申请工作流
     */
    VARIABLE_PRICE(12,"变价管理流程","SCMP_APPLY_VARIABLE_PRICE"),
    /**
     * 移库审批工作流
     */
    MOVEMENT_ODER(13,"移库审批流","SCMP_APPLY_MOVE_LIBRARY"),

    /**
     * 报废审批工作流
     */
    SCRAP(15,"报废审批流","SCMP_APPLY_SKU_SCRAP"),

    /**
     * 商品销售区域申请工作流
     */
    APPLY_SALE_AREA(14,"商品销售区域申请工作流","SCMP_APPLY_SALE_AREA"),

    APPLY_GOODS_COMPANY(15,"商品供应商申请工作流","SCMP_APPLY_GOODS_COMPANY"),
    ;

    /**
     * 编号
     */
    private Integer num;
    /**
     * 标题（***工作流格式）
     */
    private String title;
    /**
     * 工作流标识，用于区分是哪个工作流
     */
    private String key;


    WorkFlow(Integer num, String title, String key) {
        this.num = num;
        this.title = title;
        this.key = key;
    }

    public static Map<Integer,WorkFlow> getAll(){
        return Arrays.stream(values()).collect(Collectors.toMap(WorkFlow::getNum, Function.identity()));
    }
}

