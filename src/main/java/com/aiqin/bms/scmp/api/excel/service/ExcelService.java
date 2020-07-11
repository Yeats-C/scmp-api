package com.aiqin.bms.scmp.api.excel.service;

import com.aiqin.bms.scmp.api.excel.domain.*;
import com.aiqin.bms.scmp.api.excel.utils.ListUtils;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseOrderDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseOrderProductDao;
import com.aiqin.bms.scmp.api.purchase.dao.RejectRecordDao;
import com.aiqin.bms.scmp.api.purchase.dao.RejectRecordDetailDao;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil;
import com.aiqin.bms.scmp.api.util.excel.utils.WDWUtil;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExcelService {

    private static com.alibaba.excel.metadata.Sheet initSheet;

    //自定义设置初始化sheet相关参数，可根据实际情形调整
    static {
        initSheet = new com.alibaba.excel.metadata.Sheet(1, 0);
        initSheet.setSheetName("sheet");
        initSheet.setAutoWidth(Boolean.TRUE);
    }

    @Resource
    private PurchaseOrderDao purchaseOrderDao;

    @Resource
    private PurchaseOrderProductDao purchaseOrderProductDao;

    @Resource
    private RejectRecordDao rejectRecordDao;

    @Resource
    private RejectRecordDetailDao rejectRecordDetailDao;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private OrderInfoItemMapper orderInfoItemMapper;

    /**
     * 导入单据到线上数据库
     *
     * @param multipartFile
     */
    public HttpResponse importExcel(MultipartFile multipartFile) throws Exception {
        FileInputStream inputStream = (FileInputStream) multipartFile.getInputStream();
        String originalFilename = multipartFile.getOriginalFilename();
        Workbook wb = null;
        try {
            if (WDWUtil.isExcel2007(originalFilename)) {
                wb = new XSSFWorkbook(inputStream);
            } else {
                wb = new HSSFWorkbook(inputStream);
            }

            int numberOfSheets = wb.getNumberOfSheets();
            for (int i = 1; i < numberOfSheets; i++) {
                Sheet sheetAt = wb.getSheetAt(i);
                if ("采购单主表".equals(sheetAt.getSheetName())) {
//                    List<PurchaseOrderExcel> purchaseOrderExcels = ExcelUtil.readExcel(multipartFile, PurchaseOrderExcel.class, i + 1);
//                    System.out.println(purchaseOrderExcels);
//                    this.saveDb(purchaseOrderExcels);
//                    purchaseOrderExcels.clear();
                } else if ("采购单明细表".equals(sheetAt.getSheetName())) {
//                    List<PurchaseOrderProductExcel> pus = ExcelUtil.readExcel(multipartFile, PurchaseOrderProductExcel.class, i + 1);
//                    savepurchaseOrderProductDb(pus);

                } else if ("退供单主表".equals(sheetAt.getSheetName())) {
//                    List<RejectRecordExcel> rrs = ExcelUtil.readExcel(multipartFile, RejectRecordExcel.class, i + 1);
//                    saveRejectRecord(rrs);


                } else if ("退供单明细表".equals(sheetAt.getSheetName())) {
//                    List<RejectRecordDetailExcel> rrd = ExcelUtil.readExcel(multipartFile, RejectRecordDetailExcel.class, i + 1);
//                    saveRejectRecordDetail(rrd);

                } else if ("销售单主表".equals(sheetAt.getSheetName())) {
//                    List<OrderInfoExcel> of = ExcelUtil.readExcel(multipartFile, OrderInfoExcel.class, i + 1);
//                    saveOrderInfo(of);

                } else if ("销售单明细表".equals(sheetAt.getSheetName())) {
//                    List<OrderInfoItemExcel> oft = ExcelUtil.readExcel(multipartFile, OrderInfoItemExcel.class, i + 1);
//                    saveOrderInfoItem(oft);
                }else  if("退货单主表".equals(sheetAt.getSheetName())){


                }

            }

        } catch (Exception e) {
            log.error("io异常=={}", e.toString());
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
            } catch (IOException ignore) {
                log.error(ignore.toString());
            }
        }
        return HttpResponse.success();
    }


    /**
     * 存储采购单主表信息
     *
     * @param purchaseOrders
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveDb(List<PurchaseOrderExcel> purchaseOrders) throws Exception {
        List<String> code = purchaseOrders.stream().map(PurchaseOrderExcel::getPurchaseOrderCode).collect(Collectors.toList());
        if (CollectionUtils.isNotEmptyCollection(code)) {
            //查询出已经存在的采购编号数据
            List<PurchaseOrder> existOrderCodeList = this.purchaseOrderDao.selectByPurchaseOrderCode(code);
            if (CollectionUtils.isNotEmptyCollection(existOrderCodeList)) {
                //已经存在采购单号
                List<String> existOrderCodes = existOrderCodeList.stream().map(PurchaseOrder::getPurchaseOrderCode).collect(Collectors.toList());
                //删除已经存在的
                Iterator<PurchaseOrderExcel> iterator = purchaseOrders.iterator();
                while (iterator.hasNext()) {
                    PurchaseOrderExcel next = iterator.next();
                    if (existOrderCodes.contains(next.getPurchaseOrderCode())) {
                        iterator.remove();
                    }
                }

            }

            if (CollectionUtils.isNotEmptyCollection(purchaseOrders)) {
                String s = JsonUtil.toJson(purchaseOrders);
                List<PurchaseOrder> saves = JSONObject.parseArray(s, PurchaseOrder.class);
                purchaseOrders.clear();

                saves.forEach(p -> {
                    p.setPurchaseOrderId(IdUtil.purchaseId());
                    //因为状态都一样所以写死了
                    p.setPurchaseOrderStatus(1);
                    Date date = DateUtils.getDate(p.getPreArrivalDate());
                    p.setPreArrivalTime(date);
                });
                if (CollectionUtils.isNotEmptyCollection(saves)) {
                    this.purchaseOrderDao.insertMany(saves);
                }
                saves.clear();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }


    }


    @Transactional(rollbackFor = Exception.class)
    public void savepurchaseOrderProductDb(List<PurchaseOrderProductExcel> pus) throws Exception {
        if (CollectionUtils.isNotEmptyCollection(pus)) {
            String s = JsonUtil.toJson(pus);
            List<PurchaseOrderProduct> saves = JSONObject.parseArray(s, PurchaseOrderProduct.class);
            pus.clear();
            System.gc();
            //通过采购单号获取采购id
            List<String> purchaseOrderCode = saves.stream().map(PurchaseOrderProduct::getPurchaseOrderCode).distinct().collect(Collectors.toList());
            List<PurchaseOrder> purchaseOrders = this.purchaseOrderDao.selectByPurchaseOrderCode(purchaseOrderCode);
            Map<String, String> purchaseOrderIdMap = new HashMap<>();

            if (CollectionUtils.isNotEmptyCollection(purchaseOrders)) {
                purchaseOrderIdMap = purchaseOrders.stream().collect(Collectors.toMap(PurchaseOrder::getPurchaseOrderCode, PurchaseOrder::getPurchaseOrderId));
                purchaseOrders.clear();
            }
            for (PurchaseOrderProduct save : saves) {
                save.setBoxGauge(save.getProductCount() + "/" + save.getDanwei());
                if ("普通商品".equals(save.getLiebie())) {
                    save.setProductType(0);
                } else if ("实物返".equals(save.getLiebie())) {
                    save.setProductType(2);
                } else if ("赠品".equals(save.getLiebie())) {
                    save.setProductType(1);
                }
                //设置采购单id
                String purchaseOrderId = purchaseOrderIdMap.get(save.getPurchaseOrderCode());
                save.setPurchaseOrderId(purchaseOrderId);
                save.setOrderProductId(UUID.randomUUID().toString().replaceAll("-", ""));
            }
            purchaseOrderIdMap.clear();
            this.purchaseOrderProductDao.insertAll(saves);
            saves.clear();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    /**
     * 保存退供单主表
     *
     * @param rrs
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveRejectRecord(List<RejectRecordExcel> rrs) throws Exception {
        if (CollectionUtils.isNotEmptyCollection(rrs)) {
            List<String> rejectCodeList = rrs.stream().map(RejectRecordExcel::getRejectRecordCode).collect(Collectors.toList());

            //查询出已经在退供主表存在的退供单单号信息
            List<RejectRecord> existRejectCodeList = this.rejectRecordDao.selectByRejectCodeList(rejectCodeList);
            if (CollectionUtils.isNotEmptyCollection(existRejectCodeList)) {
                //已经存在退供单信息
                List<String> existOrderCodes = existRejectCodeList.stream().map(RejectRecord::getRejectRecordCode).collect(Collectors.toList());

                Iterator<RejectRecordExcel> iterator = rrs.iterator();
                while (iterator.hasNext()) {
                    RejectRecordExcel next = iterator.next();
                    if (existOrderCodes.contains(next.getRejectRecordCode())) {
                        //删除已经存在的
                        iterator.remove();
                    }
                }

            }


            String s = JsonUtil.toJson(rrs);
            List<RejectRecord> saves = JSONObject.parseArray(s, RejectRecord.class);
            rrs.clear();
            //遍历设置reject_record_id的值
            saves.forEach(p -> {
                p.setRejectRecordId(IdUtil.rejectRecordId());
            });
            if (CollectionUtils.isNotEmptyCollection(saves)) {
                this.rejectRecordDao.insertMany(saves);
            }
            saves.clear();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


    }

    /**
     * 保存退供单明细
     *
     * @param rrd
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveRejectRecordDetail(List<RejectRecordDetailExcel> rrd) throws Exception {

        if (CollectionUtils.isNotEmptyCollection(rrd)) {
            String s = JsonUtil.toJson(rrd);
            List<RejectRecordDetail> saves = JSONObject.parseArray(s, RejectRecordDetail.class);
            rrd.clear();
            System.gc();
            //通过退供单号获取 reject_record_id退供单id
            List<String> purchaseOrderCodeList = saves.stream().map(RejectRecordDetail::getRejectRecordCode).distinct().collect(Collectors.toList());
            List<RejectRecord> purchaseOrders = this.rejectRecordDao.selectByRejectCodeList(purchaseOrderCodeList);
            Map<String, String> purchaseOrderIdMap = new HashMap<>();

            if (CollectionUtils.isNotEmptyCollection(purchaseOrders)) {
                purchaseOrderIdMap = purchaseOrders.stream().collect(Collectors.toMap(RejectRecord::getRejectRecordCode, RejectRecord::getRejectRecordId));
                purchaseOrders.clear();
            }
            for (RejectRecordDetail save : saves) {

                //设置采购单id
                String rejectRecordId = purchaseOrderIdMap.get(save.getRejectRecordCode());
                save.setRejectRecordId(rejectRecordId);
                save.setRejectRecordDetailId(UUID.randomUUID().toString().replaceAll("-", ""));

            }
            purchaseOrderIdMap.clear();
            this.rejectRecordDetailDao.insertAll(saves);
            saves.clear();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 保存销售主表信息
     *
     * @param of
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrderInfo(List<OrderInfoExcel> of) {

        if (CollectionUtils.isNotEmptyCollection(of)) {
            List<String> orderCodeList = of.stream().map(OrderInfoExcel::getOrderCode).collect(Collectors.toList());

            //查询出已经在存在的销售单信息
            List<OrderInfo> existOrderCodeeList = this.orderInfoMapper.selectByOrderCodes(orderCodeList);
            if (CollectionUtils.isNotEmptyCollection(existOrderCodeeList)) {
                //已经存在销售单信息
                List<String> existOrderCodes = existOrderCodeeList.stream().map(OrderInfo::getOrderCode).collect(Collectors.toList());

                Iterator<OrderInfoExcel> iterator = of.iterator();
                while (iterator.hasNext()) {
                    OrderInfoExcel next = iterator.next();
                    if (existOrderCodes.contains(next.getOrderCode())) {
                        //删除已经存在的
                        iterator.remove();
                    }
                }

            }

            String s = JsonUtil.toJson(of);
            List<OrderInfo> saves = JSONObject.parseArray(s, OrderInfo.class);
            of.clear();
            if (CollectionUtils.isNotEmptyCollection(saves)) {
                //数据量太多会mysql报错 分批次插入
                Map<Integer, List<OrderInfo>> itemMap = new ListUtils<OrderInfo>().batchList(saves, 3000);

                for (Integer i : itemMap.keySet()) {
                    List<OrderInfo> orderInfos1 = itemMap.get(i);
                    orderInfoMapper.insertBatch(orderInfos1);
                }
            }
            saves.clear();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * 保存销售单明细数据
     *
     * @param oft
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrderInfoItem(List<OrderInfoItemExcel> oft) {
        if (CollectionUtils.isNotEmptyCollection(oft)) {
            String s = JsonUtil.toJson(oft);
            List<OrderInfoItem> saves = JSONObject.parseArray(s, OrderInfoItem.class);
            oft.clear();
            if (CollectionUtils.isNotEmptyCollection(saves)) {
                //
                Map<Integer, List<OrderInfoItem>> itemMap = new ListUtils<OrderInfoItem>().batchList(saves, 3000);
                for (Integer i : itemMap.keySet()) {
                    //数据量太多会mysql报错 分批次插入
                    List<OrderInfoItem> orderInfosItems = itemMap.get(i);
                    orderInfoItemMapper.insertBatch(orderInfosItems);
                }
            }

            saves.clear();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}