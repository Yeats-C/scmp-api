package com.aiqin.bms.scmp.api.product.jobs;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-06
 * @time: 13:06
 */
public interface ProductSkuSaleAreaJob {
    /**
     * 更新数据生效的定时任务
     * @author NullPointException
     * @date 2019/6/6
     * @param
     * @return void
     */
    void updateSaleAreaStatus();
    /**
     * 更新数据
     * @author NullPointException
     * @date 2019/6/6
     * @param
     * @return void
     */
    void updateSaleAreaData() throws Exception;
}
