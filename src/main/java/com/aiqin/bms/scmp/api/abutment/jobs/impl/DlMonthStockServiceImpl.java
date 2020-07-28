package com.aiqin.bms.scmp.api.abutment.jobs.impl;

import com.aiqin.bms.scmp.api.abutment.domain.request.dl.MonthStockRequest;
import com.aiqin.bms.scmp.api.abutment.domain.response.DLResponse;
import com.aiqin.bms.scmp.api.abutment.jobs.DlMonthStockService;
import com.aiqin.bms.scmp.api.abutment.service.impl.ParameterAssemblyServiceImpl;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.StockMonthBatchDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockMonthBatch;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.DLHttpClientUtil;
import com.aiqin.ground.util.json.JsonUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DlMonthStockServiceImpl implements DlMonthStockService {

    private static Logger LOGGER = LoggerFactory.getLogger(DlMonthStockServiceImpl.class);

    public static final Integer DEBANG = 1;
    public static final Integer JINGDONG = 2;

    @Resource
    private StockMonthBatchDao stockMonthBatchDao;
    @Resource
    private WarehouseDao warehouseDao;
    @Value("${dl.url}")
    private String DL_URL;
    @Resource
    private DLHttpClientUtil dlHttpClientUtil;

    @Override
    //@Scheduled(cron = "0 0/20 * * * ?")
    public void monthStockDl(){

        Map<String, WarehouseDTO> warehouseMap = new HashMap<>();
        WarehouseDTO warehouse;
        List<WarehouseDTO> warehouses = warehouseDao.warehouseList();
        for(WarehouseDTO ware :  warehouses){
            if(warehouseMap.get(ware.getWarehouseCode()) == null){
                warehouseMap.put(ware.getWarehouseCode(), warehouseDao.getWarehouseByCode(ware.getWarehouseCode()));
            }
        }
        MonthStockRequest monthStockRequest;
        List<MonthStockRequest> list = Lists.newArrayList();

        // 查询德邦所有的库存信息
        List<StockMonthBatch> stockMonthBatches = stockMonthBatchDao.stockMonthByDl(DEBANG);
        if(CollectionUtils.isNotEmptyCollection(stockMonthBatches)){
            for(StockMonthBatch stockMonth : stockMonthBatches){
                monthStockRequest = new MonthStockRequest();
                monthStockRequest.setSkuCode(stockMonth.getSkuCode());
                monthStockRequest.setBatchCode(stockMonth.getBatchCode());
                monthStockRequest.setBatchCount(stockMonth.getBatchCount());
                monthStockRequest.setWmsType(DEBANG);
                if(StringUtils.isNotBlank(stockMonth.getWarehouseCode())){
                    warehouse = warehouseMap.get(stockMonth.getWarehouseCode());
                    monthStockRequest.setWarehouseCode(warehouse.getWmsWarehouseId());
                    monthStockRequest.setWarehouseName(warehouse.getWmsWarehouseName());
                    monthStockRequest.setWmsWarehouseType(warehouse.getWmsWarehouseType());
                }else {
                    LOGGER.info("同步dl月份批次未查询到德邦对应的库房：{}", stockMonth.getWarehouseCode());
                }
                list.add(monthStockRequest);
            }
        }

        // 查询京东所有的库存信息
        List<StockMonthBatch> stockMonthBatcheJds = stockMonthBatchDao.stockMonthByDl(JINGDONG);
        if(CollectionUtils.isNotEmptyCollection(stockMonthBatcheJds)){
            for(StockMonthBatch stockMonth : stockMonthBatcheJds){
                monthStockRequest = new MonthStockRequest();
                monthStockRequest.setSkuCode(stockMonth.getSkuCode());
                monthStockRequest.setBatchCode(stockMonth.getBatchCode());
                monthStockRequest.setBatchCount(stockMonth.getBatchCount());
                monthStockRequest.setWmsType(JINGDONG);
                if(StringUtils.isNotBlank(stockMonth.getWarehouseCode())){
                    warehouse = warehouseMap.get(stockMonth.getWarehouseCode());
                    monthStockRequest.setWarehouseCode(warehouse.getWmsWarehouseId());
                    monthStockRequest.setWarehouseName(warehouse.getWmsWarehouseName());
                    monthStockRequest.setWmsWarehouseType(warehouse.getWmsWarehouseType());
                }else {
                    LOGGER.info("同步dl月份批次未查询京东到对应的库房：{}", stockMonth.getWarehouseCode());
                }
                list.add(monthStockRequest);
            }
        }

        String url = DL_URL + "update/productdate";
        DLResponse dlResponse = dlHttpClientUtil.HttpHandler1(JsonUtil.toJson(list), url);
        if (dlResponse.getStatus() == 0) {
            LOGGER.info("熙耘->DL，同步月份批次信息成功");
        } else {
            LOGGER.info("熙耘->DL，同步月份批次信息失败:{}", dlResponse.getMessage());
        }
    }
}
