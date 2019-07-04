package com.aiqin.bms.scmp.api.base;

import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;

public interface ResultCode {
    MessageId SYSTEM_ERROR = MessageId.create(Project.SCMP_API, 500, "系统异常");
    MessageId REQUIRED_PARAMETER = MessageId.create(Project.SCMP_API, 1, "参数缺失");
    MessageId NAME_REPEAT = MessageId.create(Project.SCMP_API, 2, "名称重复");
    MessageId NOT_HAVE_PARAM = MessageId.create(Project.SCMP_API, 3, "参数信息错误");
    //系统相关异常
    MessageId BEAN_COPY_ERROR = MessageId.create(Project.PURCHASE_API,599,"对象拷贝异常");
    MessageId USER_NOT_FOUND = MessageId.create(Project.PURCHASE_API,403,"获取登录用户失败！");
    MessageId SELECT_WEI_SHOP_SKU_ERROR = MessageId.create(Project.PRODUCT_API, 3, "未查询到微商城sku");
    MessageId SELECT_WEI_SHOP_TOP_SKU_ERROR = MessageId.create(Project.PRODUCT_API, 4, "未查询到微商城已上架sku");
    MessageId SELECT_WARNING_STOCK_SKU_ERROR = MessageId.create(Project.PRODUCT_API, 5, "未查询到库存预警sku");
    MessageId SELECT_PRODUCT_CATEGORY_INFO_ERROR = MessageId.create(Project.PRODUCT_API, 6, "未查询到商品分类信息");
    MessageId SELECT_PRODUCT_BY_PRODUCTID_ERROR = MessageId.create(Project.PRODUCT_API, 7, "根据商品id未查询到商品信息");
    MessageId UPDATE_PRODUCT_INFO_ERROR = MessageId.create(Project.PRODUCT_API, 8, "修改商品信息未成功");
    MessageId UPLODE_IMAGE_ERROR = MessageId.create(Project.PRODUCT_API, 9, "上传图片失败");
    MessageId NO_HAVE_INFO_ERROR = MessageId.create(Project.ZERO, 10, "未查询到对应信息");
    MessageId ADD_ERROR = MessageId.create(Project.ZERO, 13, "新增失败");
    MessageId UPDATE_ERROR = MessageId.create(Project.ZERO, 14, "修改失败");
    MessageId SEARCH_ERROR = MessageId.create(Project.ZERO, 14, "查询数据失败");
    MessageId DELETE_ERROR = MessageId.create(Project.ZERO,15,"删除失败");
    MessageId CANCEL_ERROR = MessageId.create(Project.ZERO, 16, "撤销失败");

    MessageId FILE_UPLOAD_ERROR = MessageId.create(Project.ZERO, 20, "文件上传失败");
    //合同
    MessageId SAVE_PLAN_TYPE_FAILED = MessageId.create(Project.SUPPLIER_API, 999, "保存目标返利失败");

    MessageId UPDATE_PRODUCT_ON_SALE_ERROR = MessageId.create(Project.PRODUCT_API, 10, "根据商品id将商品从微商城下架失败");
    MessageId SELECT_PRODUCT_BY_PRODUCTCODELIST_ERROR = MessageId.create(Project.PRODUCT_API, 11, "根据商品codeList查询商品信息失败");
    MessageId SELECT_PRODUCT_BY_SEARCHTEXT_ERROR = MessageId.create(Project.PRODUCT_API, 12, "根据搜索条件查询商品信息失败");
    MessageId UPDATE_PRODUCT_BY_SEARCHTEXT_ERROR = MessageId.create(Project.PRODUCT_API, 13, "根据搜索条件修改商品信息失败");
    MessageId SELECT_SHOP_SKU_ERROR = MessageId.create(Project.PRODUCT_API, 14, "未查询到sku");
    MessageId SELECT_SHOP_TOP_SKU_ERROR = MessageId.create(Project.PRODUCT_API, 15, "未查询到门店已上架sku");
    MessageId SELECT_PRODUCT_CATEGORY_COUNT_ERROR = MessageId.create(Project.PRODUCT_API, 16, "查询品类数量失败");
    MessageId SELECT_PRODUCT_PRICE_ERROR = MessageId.create(Project.PRODUCT_API, 17, "查询商品价格跟零售价失败");
    MessageId SELECT_PRODUCT_CYCLE_ERROR = MessageId.create(Project.PRODUCT_API, 18, "查询商品消耗周期失败");
    MessageId SELECT_PRODUCT_INFO_BY_PIDLIST_ERROR = MessageId.create(Project.PRODUCT_API, 19, "根据商品id集合查询商品信息失败");
    MessageId SELECT_ALL_TOP_SKU_ERROR = MessageId.create(Project.PRODUCT_API, 20, "查询所有上架sku数量失败");
    MessageId SELECT_ALL_DOWN_SKU_ERROR = MessageId.create(Project.PRODUCT_API, 21, "查询所有下架sku数量失败");
    MessageId COUNT_SOLD_OUT_SHOP_SKU_ERROR = MessageId.create(Project.PRODUCT_API, 22, "查询门店售罄sku数量失败");
    MessageId COUNT_SOLD_OUT_WEI_SHOP_SKU_ERROR = MessageId.create(Project.PRODUCT_API, 23, "查询微商城售罄sku数量失败");
    MessageId UPDATE_BRAND_INFO_BY_BRANDID_AND_DISID_ERROR = MessageId.create(Project.PRODUCT_API, 24, "品牌编辑失败");
    MessageId DELETE_BRAND_INFO_BY_BRANDID_AND_DISID_ERROR = MessageId.create(Project.PRODUCT_API, 25, "品牌删除失败");
    MessageId SELECT_LIKE_PRODUCT_LIST_ERROR = MessageId.create(Project.PRODUCT_API, 64, "根据pos扫码查询相似商品失败");
    /**
     * 品牌的返回值
     */
    MessageId ADD_PRODUCT_BRAND_ERROR = MessageId.create(Project.PRODUCT_API, 26, "品牌新增失败");
    MessageId EDIT_PRODUCT_BRAND_ERROR = MessageId.create(Project.PRODUCT_API, 27, "品牌修改失败");
    MessageId VIEW_PRODUCT_BRAND_ERROR = MessageId.create(Project.PRODUCT_API, 28, "品牌查询失败");
    MessageId LIST_PRODUCT_BRAND_ERROR = MessageId.create(Project.PRODUCT_API, 29, "品牌列表展示失败");
    MessageId ENABLE_PRODUCT_BRAND_ERROR = MessageId.create(Project.PRODUCT_API, 30, "品牌可用状态修改失败");
    /**
     * 品类返回值
     */
    MessageId ADD_PRODUCT_CATEGORY_ERROR = MessageId.create(Project.PRODUCT_API, 37, "品类新增失败");
    MessageId UPDATE_PRODUCT_CATEGORY_ERROR = MessageId.create(Project.PRODUCT_API, 38, "品类修改失败");
    MessageId DELETE_PRODUCT_CATEGORY_ERROR = MessageId.create(Project.PRODUCT_API, 39, "品类删除失败");
    MessageId GET_PRODUCT_CATEGORY_ERROR = MessageId.create(Project.PRODUCT_API, 40, "品类查询失败");
    //**字典
    MessageId DICTIONARY_CODE = MessageId.create(Project.SUPPLIER_API, 41, "字典查询失败");
    /**
     * 库房库存返回值
     */
    MessageId GET_STOCK_INFO_BY_STOCKID_ERROR = MessageId.create(Project.SUPPLIER_API, 42, "根据stockId查询单个stock信息失败");
    MessageId GET_STOCK_INFO_BY_PAGE_ERROR = MessageId.create(Project.SUPPLIER_API, 43, "库存列表查询失败");
    MessageId GET_OUTBOUND_INFO_BY_SEARCH_ERROR = MessageId.create(Project.SUPPLIER_API, 44, "查询出库信息失败");
    MessageId GET_INBOUND_INFO_BY_SEARCH_ERROR = MessageId.create(Project.SUPPLIER_API, 45, "查询入库信息失败");


    MessageId ID_EMPTY = MessageId.create(Project.SUPPLIER_API, 46, "主键ID不能为空");
    MessageId OBJECT_EMPTY = MessageId.create(Project.SUPPLIER_API, 47, "查询失败,未找到对应的信息");

    //图片上传
    MessageId FILE_EMPTY = MessageId.create(Project.SUPPLIER_API, 48, "传入图片不能为空");
    MessageId PLEASE_UPLOAD_AN_IMAGE = MessageId.create(Project.SUPPLIER_API, 49, "请上传图片");

    MessageId DATA_REPEAT = MessageId.create(Project.SUPPLIER_API, 98, "数据重复，请检查！");

    /**
     * 品牌的返回值
     */
    MessageId ADD_PRODUCT_DICTIONARY_ERROR = MessageId.create(Project.PRODUCT_API, 31, "商品字典新增失败");
    MessageId EDIT_PRODUCT_DICTIONARY_ERROR = MessageId.create(Project.PRODUCT_API, 32, "商品字典修改失败");
    MessageId VIEW_PRODUCT_DICTIONARY_ERROR = MessageId.create(Project.PRODUCT_API, 33, "商品字典查询失败");
    MessageId LIST_PRODUCT_DICTIONARY_ERROR = MessageId.create(Project.PRODUCT_API, 34, "商品字典列表展示失败");
    MessageId ENABLE_PRODUCT_DICTIONARY_ERROR = MessageId.create(Project.PRODUCT_API, 35, "商品字典可用状态修改失败");
    MessageId DELETE_PRODUCT_DICTIONARY_ERROR = MessageId.create(Project.PRODUCT_API, 36, "商品字典删除修改失败");

    /**
     *
     * sku销量/进货量返回
     */
    MessageId SAVE_BATCH_SKU_SALE_VOLIME_ERROR = MessageId.create(Project.PRODUCT_API,41,"sku销量批量新增失败");
    MessageId SAVE_BATCH_SKU_SALE_VOLIME_SKU_EMPTY = MessageId.create(Project.PRODUCT_API,42,"sku销量批量新增失败,有为空的SKU数据,请检查");
    MessageId SAVE_BATCH_SKU_PURCHASE_VOLIME_ERROR = MessageId.create(Project.PRODUCT_API,43,"sku进货量批量新增失败");
    MessageId SAVE_BATCH_SKU_PURCHASE_VOLIME_SKU_EMPTY = MessageId.create(Project.PRODUCT_API,44,"sku进货量批量新增失败,有为空的SKU数据,请检查");

    /**
     *
     * 库存相关返回
     */
    MessageId STOCK_RETURN_SUPPLY_SKU_EMPTY = MessageId.create(Project.PRODUCT_API,45,"退供商品信息为空");
    MessageId STOCK_RETURN_SUPPLY_COMPANY_EMPTY = MessageId.create(Project.PRODUCT_API,46,"退供公司信息为空");
    MessageId STOCK_RETURN_SUPPLY_SUPPLY_EMPTY = MessageId.create(Project.PRODUCT_API,47,"退供供货单位信息为空");
    MessageId STOCK_RETURN_SUPPLY_TRANSPORT_CENTER_EMPTY = MessageId.create(Project.PRODUCT_API,48,"退供物流中心信息为空");
    MessageId STOCK_RETURN_SUPPLY_WAREHOUSE_EMPTY = MessageId.create(Project.PRODUCT_API,49,"退供库房信息为空");
    MessageId STOCK_RETURN_SUPPLY_PURCHASE_GROUP_EMPTY = MessageId.create(Project.PRODUCT_API,50,"退供采购组信息为空");
    MessageId STOCK_RETURN_SUPPLY_VERIFY_ERROR = MessageId.create(Project.PRODUCT_API,51,"退供商品信息验证失败");
    MessageId STOCK_LOCK_ERROR = MessageId.create(Project.PRODUCT_API,53,"库存锁定失败");
    MessageId OUTBOUND_SAVE_ERROR = MessageId.create(Project.PRODUCT_API,54,"出库单保存失败");
    MessageId STOCK_UNLOCK_ERROR = MessageId.create(Project.PRODUCT_API,55,"库存解锁失败");
    MessageId STOCK_REDUCE_UNLOCK_STOCK_ERROR = MessageId.create(Project.PRODUCT_API,55,"减库存并解锁");

    /**
     *  调拨单相关返回
     */
    MessageId ALLOCATION_RETURN_ADD_ERROR = MessageId.create(Project.PRODUCT_API,52,"调拨单添加失败");
    MessageId ALLOCATION_RETURN_REVOCATION_ERROR = MessageId.create(Project.PRODUCT_API,52,"调拨单撤销失败");
    MessageId STOCK_CHANGE_ERROR = MessageId.create(Project.PRODUCT_API,56,"修改库存失败");

    /**
     * 盘点
     */
    MessageId SELECT_STOCKTAKING_ERROR = MessageId.create(Project.PRODUCT_API,52,"查询盘点记录失败");
    MessageId SELECT_STOCKTAKING_WHOLE_ERROR = MessageId.create(Project.PRODUCT_API,52,"查询盘点详细失败");
    MessageId UPDATEDELFLAG = MessageId.create(Project.PRODUCT_API,52,"更新删除标识失败");

    MessageId RETURNINOUTBOUNDFAIL = MessageId.create(Project.PRODUCT_API,56,"回传失败");

    /**
     * 商品相关
     */
    MessageId PRODUCT_NO_EXISTS = MessageId.create(Project.PRODUCT_API,57,"商品信息不存在");

    /**
     * yiku
     */
    MessageId MOVEMENT_RECOVER = MessageId.create(Project.PRODUCT_API,58,"移库撤销失败");


    MessageId COMPANY_CODE_EMPTY = MessageId.create(Project.PRODUCT_API, 59, "公司信息为空");
    MessageId LOGISTICS_CENTER_CODE_EMPTY = MessageId.create(Project.PRODUCT_API, 60, "仓库信息为空");
    MessageId QUERY_ERROR = MessageId.create(Project.PRODUCT_API, 61, "查询错误");

    /**
     * 基础信息-价格项目/渠道管理
     */
    MessageId PRICE_TYPE_NO_EXITST = MessageId.create(Project.PRODUCT_API, 74, "价格类型不存在或价格类型和价格属性不匹配,请重新选择");
    MessageId ONLY_ONE_PURCHASE_PRICE = MessageId.create(Project.PRODUCT_API, 74, "采购价有且只能有一条数据");
    /**
     * SKU
     */
    MessageId STOCK_EMPTY = MessageId.create(Project.PRODUCT_API, 65, "库存信息为空");
    MessageId STOCK_ONE = MessageId.create(Project.PRODUCT_API, 66, "库存信息有且只能有一条");
    MessageId PURCHASE_SALE_STOCK_EMPTY = MessageId.create(Project.PRODUCT_API, 64, "进销存信息为空");
    MessageId PURCHASE_EMPTY = MessageId.create(Project.PRODUCT_API, 65, "采购信息为空");
    MessageId PURCHASE_ONE = MessageId.create(Project.PRODUCT_API, 66, "采购信息有且只能有一条");
    MessageId SALE_EMPTY = MessageId.create(Project.PRODUCT_API, 67, "销售信息为空");
    MessageId SALE_ONE = MessageId.create(Project.PRODUCT_API, 68, "销售息有且只能有一条");
    MessageId STORE_SALE_EMPTY = MessageId.create(Project.PRODUCT_API, 69, "门店销售信息为空");

    MessageId BOX_PACKING_EMPTY = MessageId.create(Project.PRODUCT_API, 70, "包装信息为空");

    MessageId  OBJECT_CONVERSION_FAILED = MessageId.create(Project.PRODUCT_API, 71, "对象转化失败");
    MessageId OBJECT_NOT_FOUND = MessageId.create(Project.PRODUCT_API, 72, "没有找到对应的对象");


    MessageId LOGIN_ERROR = MessageId.create(Project.PRODUCT_API, 73, "登录信息异常,请重新登录");

    MessageId SUBMIT_ERROR = MessageId.create(Project.PRODUCT_API, 74, "获取数据异常，提交失败");

    MessageId SKU_CONFIG_SUBMIT_ERROR =  MessageId.create(Project.PRODUCT_API, 74, "申请SKU配置数据保存异常！");
    MessageId SKU_PURCHASE_PRICE_IS_EMPTY =  MessageId.create(Project.PRODUCT_API, 74, "未找到价格项目为采购类的,请先添加价格项目");

    //价格
    MessageId SAVE_PRICE_FAILED =MessageId.create(Project.PRODUCT_API, 96, "价格数据保存失败");
    MessageId DELETE_SKU_PRICE_DRAFT_FAILED =MessageId.create(Project.PRODUCT_API, 96, "删除价格临时表数据失败") ;
    MessageId PRICE_DATA_CAN_NOT_BE_NULL = MessageId.create(Project.PRODUCT_API, 96, "价格数据不能为空");
    MessageId SAVE_PRICE_LOG_FAILED = MessageId.create(Project.PRODUCT_API, 96, "保存价格日志失败");

    //变价
    MessageId DATA_ERROR =  MessageId.create(Project.PRODUCT_API, 98, "数据异常");
    MessageId DATA_NOT_COMPLETE = MessageId.create(Project.PRODUCT_API,98,"数据不完成！请检查并补充数据！");


    MessageId IMPORT_REJECT_APPLY_ERROR = MessageId.create(Project.SCMP_API, 1000, "导入退供申请单异常");
    MessageId NOT_HAVE_REJECT_APPLY_RECORD = MessageId.create(Project.SCMP_API, 1001, "未查询到对应的退供申请单记录");
    MessageId NOT_HAVE_REJECT_RECORD = MessageId.create(Project.SCMP_API, 1002, "未查询到对应的退供单记录");
    MessageId REJECT_RECORD_ERROR =  MessageId.create(Project.PRODUCT_API, 1003, "申请退供单审批数据保存异常！");

    //订单
    MessageId ORDER_SAVE_FAILURE = MessageId.create(Project.PURCHASE_API, 99, "订单保存失败");
    MessageId CAN_NOT_FIND_ORDER = MessageId.create(Project.PURCHASE_API,99,"订单数据查询失败");
    MessageId UPDATE_ORDER_STATUS_FAILED = MessageId.create(Project.PURCHASE_API,99,"订单状态更新失败");
    MessageId LOG_SAVE_ERROR = MessageId.create(Project.PURCHASE_API,99,"订单日志保存失败");
    MessageId SAVE_LOCK_BATCH_FAILED = MessageId.create(Project.PURCHASE_API,99,"保存批次锁库失败");
    MessageId CHANGE_ACTUAL_DELIVERY_NUM_FAILED = MessageId.create(Project.PURCHASE_API,99,"修改实发数量失败");
    MessageId LOCK_BATCH_STOCK_FAILED = MessageId.create(Project.PURCHASE_API,99,"锁定批次库存失败");

    //退货
    MessageId SAVE_RETURN_ORDER_ITEM_FAILED = MessageId.create(Project.PURCHASE_API,100,"退货订单附表保存失败");
    MessageId CAN_NOT_FIND_RETURN_ORDER = MessageId.create(Project.PURCHASE_API,100,"退货订单数据查询失败");
    MessageId SAVE_RETURN_ORDER_FAILED = MessageId.create(Project.PURCHASE_API,100,"退货订单主表保存失败");
    MessageId GET_RETURN_GOODS_DETAIL_FAILED = MessageId.create(Project.PURCHASE_API,100,"获取订单详情失败");
    MessageId QUERY_INSPECTION_DETAIL_ERROR = MessageId.create(Project.PURCHASE_API,100,"查询验货信息失败");
    MessageId SAVE_INSPECTION_DATA_FAILED = MessageId.create(Project.PURCHASE_API,100,"保存验货信息失败");

    MessageId SAVE_RETURN_RECEIPT_FAILED = MessageId.create(Project.PURCHASE_API,100,"保存退货收货失败");

    //出库
    MessageId OUTBOUND_DATA_CAN_NOT_BE_NULL = MessageId.create(Project.SCMP_API,75,"出库单信息不能为空");
    MessageId SAVE_OUT_BOUND_FAILED = MessageId.create(Project.SCMP_API,75,"保存入库主表数据失败");
    MessageId SAVE_OUT_BOUND_PRODUCT_FAILED = MessageId.create(Project.SCMP_API,75,"保存入库商品数据失败");
    MessageId SAVE_OUT_BOUND_BATCH_FAILED = MessageId.create(Project.SCMP_API,75,"保存入库批次数据失败");

    // 采购
    MessageId NOT_PURCHASE_GROUP_DATA = MessageId.create(Project.SCMP_API,201,"此采购组该人员无相关数据");
    MessageId IMPORT_PURCHASE_APPLY_ERROR = MessageId.create(Project.SCMP_API, 202, "导入采购申请单异常");

    //审批中
    MessageId UN_SUBMIT_APPROVAL = MessageId.create(Project.SCMP_API,201,"审批中的数据不允许修改");
    MessageId SUPPLIER_CODE_EMPTY = MessageId.create(Project.SCMP_API,201,"供应商编码不能为空");
    MessageId INBOUND_ORDER_CODE_EMPTY = MessageId.create(Project.SUPPLIER_API, 45, "入库单编号不能为空");
    MessageId PURCHSAE_ORDER_CODE_EMPTY = MessageId.create(Project.SUPPLIER_API, 45, "采购单编号不能为空");
    MessageId ARRIVAL_SUBSCRIBE_STATUS_EMPTY = MessageId.create(Project.SUPPLIER_API, 45, "预约状态不能为空");

    //发运
    MessageId TRANSPORT_DELIVERY_ERROR = MessageId.create(Project.PURCHASE_API, 1005, "发运单已签收或不存在");
    MessageId TRANSPORT_SIGN_ERROR = MessageId.create(Project.PURCHASE_API, 1005, "发运单未发运或已签收");
    MessageId UPDATE_TRANSPORT_STATUS_FAILED = MessageId.create(Project.PURCHASE_API, 1005, "更新发运状态失败！");
    MessageId SAVE_TRANSPORT_LOG_FAILED = MessageId.create(Project.PURCHASE_API, 1005, "保存发运日志信息失败！");

    //入库
    MessageId SAVE_IN_BOUND_FAILED = MessageId.create(Project.PURCHASE_API,76,"保存入库批次数据失败");
    MessageId SAVE_IN_BOUND_PRODUCT_FAILED = MessageId.create(Project.PURCHASE_API,75,"保存入库商品数据失败");
    MessageId SAVE_IN_BOUND_BATCH_FAILED = MessageId.create(Project.PURCHASE_API,75,"保存入库批次数据失败");

    MessageId SUPERVISORY_WAREHOUSE_NOT_EXISTS = MessageId.create(Project.PURCHASE_API,75,"监管仓不存在,请先添加");

    MessageId IMPORT_DATA_ERROR = MessageId.create(Project.SCMP_API, 1000, "导入数据异常");
    MessageId IMPORT_DATA_EMPTY= MessageId.create(Project.SCMP_API, 1000, "导入数据为空");

}
