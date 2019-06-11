package com.aiqin.bms.scmp.api.product.jobs;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-27
 * @time: 14:39
 */
public interface SynPrice {
    /**
     * 更新价格为生效状态
     * @author NullPointException
     * @date 2019/5/27
     * @param
     * @return void
     */
    void updatePriceStatusForEffective() throws Exception;
    /**
     * 失效
     * @author NullPointException
     * @date 2019/5/27
     * @param
     * @return void
     */
    void updatePriceStatusForUnEffective() throws Exception;
    /**
     * 更新
     * @author NullPointException
     * @date 2019/6/6
     * @param
     * @return void
     */
    void updatePriceData() throws Exception;
}
