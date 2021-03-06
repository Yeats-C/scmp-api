package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ProductSkuPromoteSales;
import com.aiqin.bms.scmp.api.supplier.domain.request.promotesales.QueryPromoteSalesReqVo;
import com.aiqin.bms.scmp.api.supplier.mapper.ProductSkuPromoteSalesMapper;
import com.aiqin.bms.scmp.api.supplier.service.ProductSkuPromoteSalesService;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.ExcelUtil;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuPromoteSalesServiceImpl
 * @date 2019/7/3 12:35

 */
@Service
@Slf4j
public class ProductSkuPromoteSalesServiceImpl implements ProductSkuPromoteSalesService {

    private static final String IMPORT_TITLE="sku编码,sku名称";
    @Autowired
    private ProductSkuPromoteSalesMapper mapper;

    /**
     * 功能描述: 分页查询
     *
     * @param reqVo
     * @return
     * @auther knight.xie
     * @date 2019/7/3 12:36
     */
    @Override
    public BasePage<ProductSkuPromoteSales> getList(QueryPromoteSalesReqVo reqVo) {
        PageHelper.startPage(reqVo.getPageNo(), reqVo.getPageSize());
        List<ProductSkuPromoteSales> list = mapper.getList(reqVo);
        return PageUtil.getPageList(reqVo.getPageNo(),list);
    }

    /**
     * 功能描述: 根据ID删除
     *
     * @param id
     * @return
     * @auther knight.xie
     * @date 2019/7/3 12:37
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    /**
     * 功能描述: 全部删除
     *
     * @return
     * @auther knight.xie
     * @date 2019/7/3 12:37
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAll() {
        return mapper.deleteAll();
    }

    /**
     * 功能描述: 批量保存
     *
     * @param records
     * @return
     * @auther knight.xie
     * @date 2019/7/3 12:40
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveList(List<ProductSkuPromoteSales> records) {
        List<ProductSkuPromoteSales> all = mapper.getAll();
        List<ProductSkuPromoteSales> delList = Lists.newLinkedList();
        if(CollectionUtils.isNotEmptyCollection(all)){
            Map<String, List<ProductSkuPromoteSales>> saleMaps = all.stream().collect(Collectors.groupingBy(ProductSkuPromoteSales::getSkuCode));
            records.forEach(item->{
                if(saleMaps.containsKey(item.getSkuCode())){
                    delList.add(item);
                }
            });
        }
        if(CollectionUtils.isNotEmptyCollection(delList)){
            List<String> delSkus = delList.stream().map(ProductSkuPromoteSales::getSkuCode).collect(Collectors.toList());
            mapper.deleteBySkuCodes(delSkus);
        }
        return mapper.insertBatch(records);
    }

    /**
     * 功能描述: 读取excel
     *
     * @param efile
     * @return
     * @auther knight.xie
     * @date 2019/7/3 12:49
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int fileUpload(MultipartFile efile) {
        List<Object[]> excelAll = ExcelUtil.getExcelAll(efile);
        if(CollectionUtils.isEmptyCollection(excelAll) || excelAll.size() == 1){
            throw new BizException(ResultCode.IMPORT_DATA_EMPTY);
        }
        List<ProductSkuPromoteSales> records = null;
        try {
            records = ExcelUtil.validValue(excelAll, IMPORT_TITLE, ProductSkuPromoteSales.class);
        } catch (Exception e) {
            log.error(Global.ERROR, e);
           throw new BizException(ResultCode.OBJECT_CONVERSION_FAILED);
        }
        return saveList(records);
    }
}
