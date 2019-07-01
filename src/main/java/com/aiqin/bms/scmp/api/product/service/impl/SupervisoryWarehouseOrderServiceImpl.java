package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.SaveList;
import com.aiqin.bms.scmp.api.common.SupervisoryWarehouseOrderTypeEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.pojo.SupervisoryWarehouseOrder;
import com.aiqin.bms.scmp.api.product.domain.pojo.SupervisoryWarehouseOrderProduct;
import com.aiqin.bms.scmp.api.product.domain.request.supervisory.SaveSupervisoryWarehouseOrderReqVo;
import com.aiqin.bms.scmp.api.product.mapper.SupervisoryWarehouseOrderMapper;
import com.aiqin.bms.scmp.api.product.mapper.SupervisoryWarehouseOrderProductMapper;
import com.aiqin.bms.scmp.api.product.service.SupervisoryWarehouseOrderService;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.service.EncodingRuleService;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author knight.xie
 * @className SupervisoryWarehouseOrderServiceImpl
 * @date 2019/6/29 17:47
 * @description TODO
 * @version 1.0
 */
@Service
public class SupervisoryWarehouseOrderServiceImpl extends BaseServiceImpl implements SupervisoryWarehouseOrderService {

    @Autowired
    private SupervisoryWarehouseOrderMapper mapper;

    @Autowired
    private SupervisoryWarehouseOrderProductMapper productMapper;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private EncodingRuleService encodingRuleService;

    /**
     * 功能描述: 保存
     * @param reqVo
     * @return
     * @auther knight.xie
     * @date 2019/6/29 18:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(SaveSupervisoryWarehouseOrderReqVo reqVo) {
        SupervisoryWarehouseOrder order = new SupervisoryWarehouseOrder();
        BeanCopyUtils.copy(reqVo,order);
        if(CollectionUtils.isEmptyCollection(reqVo.getProducts())){
            throw new BizException(ResultCode.PRODUCT_NO_EXISTS);
        }
        //查询仓库下面是否存在监管仓
        WarehouseResVo warehouseResVo = warehouseService.getWarehouseTypeByLogisticsCenterCode(order.getTransportCenterCode(), Global.SUPERVISORY_WAREHOUSE_TYPE);
        if(Objects.isNull(warehouseResVo)){
            throw new BizException(ResultCode.SUPERVISORY_WAREHOUSE_NOT_EXISTS);
        }
        order.setWarehouseCode(warehouseResVo.getWarehouseCode());
        order.setWarehouseName(warehouseResVo.getWarehouseName());
        //获取订单编号
        EncodingRule encodingRule = encodingRuleService.getNumberingType(EncodingRuleType.SUPERVISORY_WAREHOUSE_ORDER_CODE);
        String orderCode = String.valueOf(encodingRule.getNumberingValue());
        order.setOrderCode(orderCode);
        encodingRuleService.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        //获取订单类型名称
        String typeName = SupervisoryWarehouseOrderTypeEnum.getName(order.getOrderType());
        if(StringUtils.isBlank(typeName)){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        order.setOrderTypeName(typeName);
        //保存订单信息
        int insert = ((SupervisoryWarehouseOrderService) AopContext.currentProxy()).insert(order);
        List<SupervisoryWarehouseOrderProduct> records = BeanCopyUtils.copyList(reqVo.getProducts(),SupervisoryWarehouseOrderProduct.class);

        records.forEach(item->{
            item.setOrderCode(orderCode);
            item.setOrderTypeName(typeName);
            item.setOrderType(order.getOrderType());
        });
        //保存商品信息
        ((SupervisoryWarehouseOrderService)AopContext.currentProxy()).insertBatchProduct(records);
        //TODO 生成出入库单据
        return insert;
    }

    /**
     * 功能描述: 插入数据库
     *
     * @param record
     * @return
     * @auther knight.xie
     * @date 2019/6/29 18:16
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Save
    public int insert(SupervisoryWarehouseOrder record) {
        record.setCompanyCode(getUser().getCompanyCode());
        record.setCompanyName(getUser().getCompanyName());
        return mapper.insertSelective(record);
    }

    /**
     * 功能描述: 批量插入商品
     *
     * @param records
     * @return
     * @auther knight.xie
     * @date 2019/6/29 18:18
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertBatchProduct(List<SupervisoryWarehouseOrderProduct> records) {
        return productMapper.insertBatch(records);
    }
}
