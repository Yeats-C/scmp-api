package com.aiqin.bms.scmp.api.base;

import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;

public interface ResultCode {
    MessageId SYSTEM_ERROR = MessageId.create(Project.SCMP_API, 500, "系统异常");


    MessageId REQUIRED_PARAMETER = MessageId.create(Project.SCMP_API, 400, "参数缺失");
    MessageId NOT_HAVE_PARAM = MessageId.create(Project.SCMP_API, 400, "参数信息错误");
    MessageId USER_NOT_FOUND = MessageId.create(Project.SCMP_API, 401, "获取登录用户失败！");
    MessageId NAME_REPEAT = MessageId.create(Project.SCMP_API, 1, "名称重复");
    MessageId REQUEST_MODE_ERROR = MessageId.create(Project.SCMP_API, 400, "请求方式错误");

    //系统相关异常
    MessageId COMPANY_CODE_EMPTY = MessageId.create(Project.SCMP_API, 2, "公司信息为空");
    MessageId LOGISTICS_CENTER_CODE_EMPTY = MessageId.create(Project.SCMP_API, 3, "仓库信息为空");
    MessageId QUERY_ERROR = MessageId.create(Project.SCMP_API, 4, "查询错误");
    MessageId BEAN_COPY_ERROR = MessageId.create(Project.SCMP_API, 5, "对象拷贝异常");
    MessageId NO_HAVE_INFO_ERROR = MessageId.create(Project.SCMP_API, 6, "未查询到对应信息");
    MessageId ADD_ERROR = MessageId.create(Project.SCMP_API, 7, "新增失败");
    MessageId UPDATE_ERROR = MessageId.create(Project.SCMP_API, 8, "修改失败");
    MessageId SEARCH_ERROR = MessageId.create(Project.SCMP_API, 9, "查询数据失败");
    MessageId DELETE_ERROR = MessageId.create(Project.SCMP_API, 10, "删除失败");
    MessageId CANCEL_ERROR = MessageId.create(Project.SCMP_API, 11, "撤销失败");
    MessageId OBJECT_CONVERSION_FAILED = MessageId.create(Project.SCMP_API, 12, "对象转化失败");
    MessageId DATE_CONVERSION_FAILED = MessageId.create(Project.SCMP_API, 13, "日期转换异常");
    MessageId OBJECT_NOT_FOUND = MessageId.create(Project.SCMP_API, 14, "没有找到对应的对象");
    MessageId LOGIN_ERROR = MessageId.create(Project.SCMP_API, 15, "登录信息异常,请重新登录");
    MessageId SUBMIT_ERROR = MessageId.create(Project.SCMP_API, 16, "获取数据异常，提交失败");

    // 文件
    MessageId FILE_UPLOAD_ERROR = MessageId.create(Project.SCMP_API, 20, "文件上传失败");
    MessageId FILE_UPLOAD_ERROR2 = MessageId.create(Project.SCMP_API, 21, "上传文件为空");
    MessageId FILE_UPLOAD_ERROR3 = MessageId.create(Project.SCMP_API, 22, "该Sku商品下有相同文件名重复文件，请检查");
    MessageId FILE_UPLOAD_ERROR4 = MessageId.create(Project.SCMP_API, 23, "文件名称格式错误");
    MessageId FIND_NULL = MessageId.create(Project.SCMP_API, 24, "没有查到相应对象");

    //合同
    MessageId SAVE_PLAN_TYPE_FAILED = MessageId.create(Project.SCMP_API, 30, "保存目标返利失败");

    // 品牌
    MessageId ADD_PRODUCT_BRAND_ERROR = MessageId.create(Project.SCMP_API, 41, "品牌新增失败");
    MessageId EDIT_PRODUCT_BRAND_ERROR = MessageId.create(Project.SCMP_API, 42, "品牌修改失败");
    MessageId VIEW_PRODUCT_BRAND_ERROR = MessageId.create(Project.SCMP_API, 43, "品牌查询失败");
    MessageId LIST_PRODUCT_BRAND_ERROR = MessageId.create(Project.SCMP_API, 44, "品牌列表展示失败");
    MessageId ENABLE_PRODUCT_BRAND_ERROR = MessageId.create(Project.SCMP_API, 45, "品牌可用状态修改失败");

    // 品类
    MessageId ADD_PRODUCT_CATEGORY_ERROR = MessageId.create(Project.SCMP_API, 51, "品类新增失败");
    MessageId UPDATE_PRODUCT_CATEGORY_ERROR = MessageId.create(Project.SCMP_API, 52, "品类修改失败");
    MessageId DELETE_PRODUCT_CATEGORY_ERROR = MessageId.create(Project.SCMP_API, 53, "操作失败");
    MessageId GET_PRODUCT_CATEGORY_ERROR = MessageId.create(Project.SCMP_API, 54, "品类查询失败");

    // 字典
    MessageId DICTIONARY_CODE = MessageId.create(Project.SCMP_API, 60, "字典查询失败");

    MessageId ID_EMPTY = MessageId.create(Project.SCMP_API, 65, "主键ID不能为空");
    MessageId OBJECT_EMPTY = MessageId.create(Project.SCMP_API, 66, "查询失败,未找到对应的信息");

    // 图片上传
    MessageId FILE_EMPTY = MessageId.create(Project.SCMP_API, 70, "传入图片不能为空");
    MessageId FILE_NAME_EMPTY = MessageId.create(Project.SCMP_API, 71, "传入图片名称不能为空");
    MessageId FILE_DOWN_ERROR = MessageId.create(Project.SCMP_API, 72, "图片下载失败");
    MessageId PLEASE_UPLOAD_AN_IMAGE = MessageId.create(Project.SCMP_API, 73, "请上传图片");
    MessageId DATA_REPEAT = MessageId.create(Project.SCMP_API, 74, "数据重复，请检查！");

    // sku销量/进货量返回
    MessageId SAVE_BATCH_SKU_SALE_VOLIME_ERROR = MessageId.create(Project.SCMP_API, 80, "sku销量批量新增失败");
    MessageId SAVE_BATCH_SKU_SALE_VOLIME_SKU_EMPTY = MessageId.create(Project.SCMP_API, 81, "sku销量批量新增失败,有为空的SKU数据,请检查");
    MessageId SAVE_BATCH_SKU_PURCHASE_VOLIME_ERROR = MessageId.create(Project.SCMP_API, 82, "sku进货量批量新增失败");
    MessageId SAVE_BATCH_SKU_PURCHASE_VOLIME_SKU_EMPTY = MessageId.create(Project.SCMP_API, 83, "sku进货量批量新增失败,有为空的SKU数据,请检查");

    // 库存
    MessageId STOCK_RETURN_SUPPLY_VERIFY_ERROR = MessageId.create(Project.SCMP_API, 90, "退供商品信息验证失败");
    MessageId STOCK_LOCK_ERROR = MessageId.create(Project.SCMP_API, 91, "库存锁定失败");
    MessageId STOCK_BATCH_INFO_NULL = MessageId.create(Project.SCMP_API, 92, "批次库存的数据为空");

    // 调拨单相关返回
    MessageId ALLOCATION_RETURN_ADD_ERROR = MessageId.create(Project.SCMP_API, 100, "调拨单添加失败");
    MessageId ALLOCATION_RETURN_REVOCATION_ERROR = MessageId.create(Project.SCMP_API, 101, "调拨单撤销失败");
    MessageId STOCK_CHANGE_ERROR = MessageId.create(Project.SCMP_API, 102, "修改库存失败");

    // 商品
    MessageId PRODUCT_NO_EXISTS = MessageId.create(Project.SCMP_API, 110, "商品信息不存在");
    MessageId STOCK_EMPTY = MessageId.create(Project.SCMP_API, 111, "库存信息为空");
    MessageId STOCK_ONE = MessageId.create(Project.SCMP_API, 112, "库存信息有且只能有一条");
    MessageId PURCHASE_SALE_STOCK_EMPTY = MessageId.create(Project.SCMP_API, 113, "进销存信息为空");
    MessageId PURCHASE_EMPTY = MessageId.create(Project.SCMP_API, 114, "采购信息为空");
    MessageId PURCHASE_ONE = MessageId.create(Project.SCMP_API, 115, "采购信息有且只能有一条");
    MessageId SALE_EMPTY = MessageId.create(Project.SCMP_API, 116, "销售信息为空");
    MessageId SALE_ONE = MessageId.create(Project.SCMP_API, 117, "销售息有且只能有一条");
    MessageId STORE_SALE_EMPTY = MessageId.create(Project.SCMP_API, 118, "门店销售信息为空");
    MessageId BOX_PACKING_EMPTY = MessageId.create(Project.SCMP_API, 119, "包装信息为空");

    // 移库
    MessageId MOVEMENT_RECOVER = MessageId.create(Project.SCMP_API, 130, "移库撤销失败");

    // 基础信息-价格项目/渠道管理
    MessageId PRICE_TYPE_NO_EXITST = MessageId.create(Project.SCMP_API, 135, "价格类型不存在或价格类型和价格属性不匹配,请重新选择");
    MessageId ONLY_ONE_PURCHASE_PRICE = MessageId.create(Project.SCMP_API, 136, "采购价有且只能有一条数据");

    MessageId SKU_CONFIG_SUBMIT_ERROR = MessageId.create(Project.SCMP_API, 74, "申请SKU配置数据保存异常！");
    MessageId SKU_PURCHASE_PRICE_IS_EMPTY = MessageId.create(Project.SCMP_API, 74, "未找到价格项目为采购类的,请先添加价格项目");
    MessageId SKU_DIFFERENT_APPLY_TYPE =  MessageId.create(Project.SCMP_API, 75, "不同申请类型必须分开提交");
    MessageId SKU_DIFFERENT_GRPUP_TYPE =  MessageId.create(Project.SCMP_API, 76, "不同采购组必须分开提交");

    //价格
    MessageId SAVE_PRICE_FAILED = MessageId.create(Project.SCMP_API, 96, "价格数据保存失败");
    MessageId DELETE_SKU_PRICE_DRAFT_FAILED = MessageId.create(Project.SCMP_API, 96, "删除价格临时表数据失败");
    MessageId PRICE_DATA_CAN_NOT_BE_NULL = MessageId.create(Project.SCMP_API, 96, "价格数据不能为空");
    MessageId SAVE_PRICE_LOG_FAILED = MessageId.create(Project.SCMP_API, 96, "保存价格日志失败");

    //变价
    MessageId DATA_ERROR = MessageId.create(Project.SCMP_API, 98, "数据异常");
    MessageId CAN_NOT_FIND_CHANGE_PRICE_INFO = MessageId.create(Project.SCMP_API, 98, "无法获取变价数据信息！");
    MessageId UPDATE_RETURN_ORDER_INFO_FAILED = MessageId.create(Project.SCMP_API, 98, "更新退货单失败！");

    //退供
    MessageId IMPORT_REJECT_APPLY_ERROR = MessageId.create(Project.SCMP_API, 1000, "导入退供申请单异常");
    MessageId NOT_HAVE_REJECT_APPLY_RECORD = MessageId.create(Project.SCMP_API, 1001, "未查询到对应的退供申请单记录");
    MessageId NO_HAVE_ACCOUNT = MessageId.create(Project.SCMP_API, 1003, "未查询到账号信息");
    MessageId SELECT_ROLE_ERROR = MessageId.create(Project.SCMP_API, 1004, "查询角色异常");
    MessageId CONTROL_ERROR = MessageId.create(Project.SCMP_API, 1005, "调用主控系统异常");
    MessageId REJECT_APPLY_PRODUCT_NULL = MessageId.create(Project.SCMP_API, 1006, "退供申请单的商品信息为空");
    MessageId REJECT_APPLY_NULL = MessageId.create(Project.SCMP_API, 1007, "退供申请单的信息为空");
    MessageId REJECT_RECORD_NULL = MessageId.create(Project.SCMP_API, 1008, "退供单信息为空");
    MessageId REJECT_APPLY_CENTER_NULL = MessageId.create(Project.SCMP_API, 1009, "退供申请单的分仓信息为空");
    MessageId REJECT_PRODUCT_NULL = MessageId.create(Project.SCMP_API, 1010, "退供单的商品信息为空");

    //订单
    MessageId ORDER_SAVE_FAILURE = MessageId.create(Project.SCMP_API, 99, "订单保存失败");
    MessageId CAN_NOT_FIND_ORDER = MessageId.create(Project.SCMP_API, 99, "订单数据查询失败");
    MessageId UPDATE_ORDER_STATUS_FAILED = MessageId.create(Project.SCMP_API, 99, "订单状态更新失败");
    MessageId LOG_SAVE_ERROR = MessageId.create(Project.SCMP_API, 99, "订单日志保存失败");
    MessageId SAVE_LOCK_BATCH_FAILED = MessageId.create(Project.SCMP_API, 99, "保存批次锁库失败");
    MessageId LOCK_BATCH_STOCK_FAILED = MessageId.create(Project.SCMP_API, 99, "锁定批次库存失败");
    MessageId ORDER_INFO_IS_HAVE = MessageId.create(Project.SCMP_API, 99, "单据已存在");
    MessageId ORDER_INFO_NOT_HAVE = MessageId.create(Project.SCMP_API, 99, "单据不存在");

    //退货
    MessageId SAVE_RETURN_ORDER_ITEM_FAILED = MessageId.create(Project.SCMP_API, 100, "退货订单附表保存失败");
    MessageId CAN_NOT_FIND_RETURN_ORDER = MessageId.create(Project.SCMP_API, 100, "退货单数据查询失败");
    MessageId SAVE_RETURN_ORDER_FAILED = MessageId.create(Project.SCMP_API, 100, "退货订单主表保存失败");
    MessageId GET_RETURN_GOODS_DETAIL_FAILED = MessageId.create(Project.SCMP_API, 100, "获取订单详情失败");
    MessageId QUERY_INSPECTION_DETAIL_ERROR = MessageId.create(Project.SCMP_API, 100, "查询验货信息失败");
    MessageId SAVE_INSPECTION_DATA_FAILED = MessageId.create(Project.SCMP_API, 100, "保存验货信息失败");
    MessageId SAVE_RETURN_RECEIPT_FAILED = MessageId.create(Project.SCMP_API, 100, "保存退货收货失败");

    //出库
    MessageId OUTBOUND_DATA_CAN_NOT_BE_NULL = MessageId.create(Project.SCMP_API, 75, "出库单信息不能为空");
    MessageId SAVE_OUT_BOUND_FAILED = MessageId.create(Project.SCMP_API, 75, "保存入库主表数据失败");
    MessageId SAVE_OUT_BOUND_PRODUCT_FAILED = MessageId.create(Project.SCMP_API, 75, "保存入库商品数据失败");
    MessageId SAVE_OUT_BOUND_BATCH_FAILED = MessageId.create(Project.SCMP_API, 75, "保存入库批次数据失败");
    MessageId INBOUND_INFO_NULL = MessageId.create(Project.SCMP_API, 76, "入库单的信息为空");
    MessageId OUTBOUND_PRODUCT_INFO_NULL = MessageId.create(Project.SCMP_API, 76, "出库单的商品信息为空");

    // 采购
    MessageId PURCHASE_APPLY_INFO_NULL = MessageId.create(Project.SCMP_API, 207, "采购申请单的通用信息为空");
    MessageId PURCHASE_APPLY_TRANSPORT_NULL = MessageId.create(Project.SCMP_API, 208, "采购申请单的分仓信息为空");
    MessageId PURCHASE_APPLY_PRODUCT_NULL = MessageId.create(Project.SCMP_API, 209, "采购申请单的商品信息为空");
    MessageId PURCHASE_ORDER_STATUS_FAIL = MessageId.create(Project.SCMP_API, 210, "变更采购单状态失败");
    MessageId PURCHASE_ORDER_NULL = MessageId.create(Project.SCMP_API, 211, "采购单信息为空");

    //审批中
    MessageId UN_SUBMIT_APPROVAL = MessageId.create(Project.SCMP_API, 201, "审批中的数据不允许修改");
    MessageId SUPPLIER_CODE_EMPTY = MessageId.create(Project.SCMP_API, 201, "供应商编码不能为空");
    MessageId INBOUND_ORDER_CODE_EMPTY = MessageId.create(Project.SCMP_API, 45, "入库单编号不能为空");
    MessageId PURCHSAE_ORDER_CODE_EMPTY = MessageId.create(Project.SCMP_API, 45, "采购单编号不能为空");
    MessageId ARRIVAL_SUBSCRIBE_STATUS_EMPTY = MessageId.create(Project.SCMP_API, 45, "预约状态不能为空");

    //发运
    MessageId TRANSPORT_SIGN_ERROR = MessageId.create(Project.SCMP_API, 1005, "发运单未发运或已签收");
    MessageId UPDATE_TRANSPORT_STATUS_FAILED = MessageId.create(Project.SCMP_API, 1005, "更新发运状态失败！");
    MessageId SAVE_TRANSPORT_LOG_FAILED = MessageId.create(Project.SCMP_API, 1005, "保存发运日志信息失败！");

    //入库
    MessageId SAVE_IN_BOUND_FAILED = MessageId.create(Project.SCMP_API, 76, "保存入库批次数据失败");
    MessageId SAVE_IN_BOUND_PRODUCT_FAILED = MessageId.create(Project.SCMP_API, 75, "保存入库商品数据失败");
    MessageId SAVE_IN_BOUND_BATCH_FAILED = MessageId.create(Project.SCMP_API, 75, "保存入库批次数据失败");
    MessageId SUPERVISORY_WAREHOUSE_NOT_EXISTS = MessageId.create(Project.SCMP_API, 75, "监管仓不存在,请先添加");

    MessageId IMPORT_DATA_ERROR = MessageId.create(Project.SCMP_API, 1000, "导入数据异常");
    MessageId IMPORT_DATA_EMPTY = MessageId.create(Project.SCMP_API, 1000, "导入数据为空");
    MessageId MAIN_PRODUCT_EMPTY = MessageId.create(Project.SCMP_API, 1000, "主商品为空,请选择主商品");
    MessageId SKU_CODE_EMPTY = MessageId.create(Project.SCMP_API, 1000, "skuCode为空,找不到需要修改的数据");
    MessageId ORDER_TYPE_EMPTY = MessageId.create(Project.SCMP_API, 1000, "订单类型为空,请传入订单订单类型");
    MessageId ORDER_TYPE_ERROR = MessageId.create(Project.SCMP_API, 1000, "订单类型传输错误,请传入正确的订单类型");

    //导入
    MessageId IMPORT_EXCEPTION = MessageId.create(Project.SCMP_API, 1000, "导入异常，请检查格式！");
    MessageId IMPORT_HEDE_ERROR = MessageId.create(Project.SCMP_API, 1000, "表头格式错误！");
    MessageId DRAFT_CONFIG_SAVE_ERROR = MessageId.create(Project.SCMP_API, 1000, "保存导入数据失败！");
    MessageId SPU_NAME_EXISTS = MessageId.create(Project.SCMP_API, 1000, "SPU名称存在，请重新输入！");
    MessageId IMPORT_DATA_SAVE_FAILED = MessageId.create(Project.SCMP_API, 1000, "保存导入数据失败！");
    MessageId APPLY_DATA_EMPTY = MessageId.create(Project.SCMP_API, 1000, "申请列表不能为空！");

    MessageId DELIVERY_RETURN_EMPTY = MessageId.create(Project.SCMP_API, 1000, "发货、退货信息不能为空！");
    MessageId DELIVERY_EMPTY = MessageId.create(Project.SCMP_API, 1000, "发货信息不能为空！");
    MessageId RETURN_EMPTY = MessageId.create(Project.SCMP_API, 1000, "退货信息不能为空！");
    MessageId EXPORT_FAILED = MessageId.create(Project.SCMP_API, 1000, "导出失败！");

    MessageId OBJECT_EMPTY_BY_FORMNO = MessageId.create(Project.SCMP_API, 1000, "根据formNo未找到对应的信息");
    MessageId OPERATION_TYPE_ERROR = MessageId.create(Project.SCMP_API, 10001, "采购组操作类型错误");
    MessageId FIND_CONTRACT_ERROR = MessageId.create(Project.SCMP_API, 10002, "无法找到合同信息");

    MessageId AT_LEAST_ONE_DATA = MessageId.create(Project.SCMP_API, 10003, "至少需要一条仓库配置信息");
    MessageId PURCHASE_GROUP_REPEAT = MessageId.create(Project.SCMP_API, 10004, "提交的sku不属于同一个采购组");
    MessageId NOT_SAME_APPLY = MessageId.create(Project.SCMP_API, 10004, "新增类型和修改类型必须分开提交");
    MessageId REPEAT_DATA = MessageId.create(Project.SCMP_API, 10005, "新增供应商存在重复,请检查后重新提交");
    MessageId SUMBIT_NOT_DATA = MessageId.create(Project.SCMP_API, 10006, "没有找到需要提交的数据,请检查后重新提交");

    MessageId REPEAT_DATA2 = MessageId.create(Project.SCMP_API, 10007, "修改仓库地址存在重复,请检查后重新提交");
    MessageId REUPDATE_ERROR = MessageId.create(Project.SCMP_API, 10007, "只有审批驳回或者撤销的数据才能重新编辑");

}
