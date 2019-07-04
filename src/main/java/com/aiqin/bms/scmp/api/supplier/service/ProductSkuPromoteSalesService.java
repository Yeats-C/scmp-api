package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ProductSkuPromoteSales;
import com.aiqin.bms.scmp.api.supplier.domain.request.promotesales.QueryPromoteSalesReqVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuPromoteSalesService
 * @date 2019/7/3 12:35
 * @description TODO
 */
public interface ProductSkuPromoteSalesService {

    /**
     *
     * 功能描述: 分页查询
     *
     * @param reqVo
     * @return
     * @auther knight.xie
     * @date 2019/7/3 12:36
     */
    BasePage<ProductSkuPromoteSales> getList(QueryPromoteSalesReqVo reqVo);

    /**
     *
     * 功能描述: 根据ID删除
     *
     * @param id
     * @return
     * @auther knight.xie
     * @date 2019/7/3 12:37
     */
    int deleteById(Long id);

    /**
     *
     * 功能描述: 全部删除
     *
     * @return
     * @auther knight.xie
     * @date 2019/7/3 12:37
     */
    int deleteAll();

    /**
     *
     * 功能描述: 批量保存
     *
     * @param records
     * @return
     * @auther knight.xie
     * @date 2019/7/3 12:40
     */
    int saveList(List<ProductSkuPromoteSales> records);


    /**
     *
     * 功能描述: 读取excel
     *
     * @param efile
     * @return
     * @auther knight.xie
     * @date 2019/7/3 12:49
     */
    int fileUpload(MultipartFile efile);


}
