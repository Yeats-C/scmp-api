package com.aiqin.bms.scmp.api.constant;

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
public interface RejectRecordStatus {

    /**
     * 退供单状态: 0 待审核 1 审核中  2 待供应商确认 3 待出库  4 出库开始 5 已出库 6 已发运 7 完成 8 取消 9 审核不通过
     */
    Integer REJECT_STATUS_AUDIT = 0;

    Integer REJECT_STATUS_AUDITTING = 1;

    Integer REJECT_STATUS_DEFINE = 2;

    Integer REJECT_STATUS_STOCK = 3;

    Integer REJECT_STATUS_STOCK_BEGIN = 4;

    Integer REJECT_STATUS_STOCKED = 5;

    Integer REJECT_STATUS_TRANSPORTED = 6;

    Integer REJECT_STATUS_FINISH = 7;

    Integer REJECT_STATUS_CANCEL = 8;

    Integer REJECT_STATUS_NO = 9;

}
