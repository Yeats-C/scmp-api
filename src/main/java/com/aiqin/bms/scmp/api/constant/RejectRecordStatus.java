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
     *
     * 退供申请单状态: 0  待提交 1 待审核 2.审核中 3.审核通过 4.审核不通过 5. 撤销
     */
    Integer REJECT_APPLY_NO_SUBMIT = 0;

    Integer REJECT_APPLY_TO_REVIEW = 1;

    Integer REJECT_APPLY_UNDER_REVIEW = 2;

    Integer REJECT_APPLY_YES = 3;

    Integer REJECT_APPLY_NO = 4;

    Integer REJECT_APPLY_REVOKE = 5;



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
