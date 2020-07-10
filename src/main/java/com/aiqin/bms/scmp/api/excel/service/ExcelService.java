package com.aiqin.bms.scmp.api.excel.service;

import com.aiqin.bms.scmp.api.excel.domain.PurchaseOrderExcel;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseOrderDao;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil;
import com.aiqin.bms.scmp.api.util.excel.utils.WDWUtil;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.common.utils.DateUtil;
import jdk.jfr.events.ExceptionThrownEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
                    List<PurchaseOrderExcel> purchaseOrderExcels = ExcelUtil.readExcel(multipartFile, PurchaseOrderExcel.class, i + 1);
                    System.out.println(purchaseOrderExcels);
                    this.saveDb(purchaseOrderExcels);
                    purchaseOrderExcels.clear();
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
    @Transactional(rollbackFor = Exception.class)
    public void saveDb(List<PurchaseOrderExcel> purchaseOrders) throws Exception{
        List<String> code = purchaseOrders.stream().map(PurchaseOrderExcel::getPurchaseOrderCode).collect(Collectors.toList());
        if (CollectionUtils.isNotEmptyCollection(code)) {
            //查询出已经存在的采购编号数据
            List<PurchaseOrder> existOrderCodeList = this.purchaseOrderDao.selectByPurchaseOrderCode(code);
             if(CollectionUtils.isNotEmptyCollection(existOrderCodeList)){
                 //已经存在采购单号
                 List<String> existOrderCodes = existOrderCodeList.stream().map(PurchaseOrder::getPurchaseOrderCode).collect(Collectors.toList());
                 //删除已经存在的
                 Iterator<PurchaseOrderExcel> iterator = purchaseOrders.iterator();
                 while(iterator.hasNext()){
                     PurchaseOrderExcel next = iterator.next();
                     if(existOrderCodes.contains(next.getPurchaseOrderCode())){
                         iterator.remove();
                     }
                 }

             }

            if(CollectionUtils.isNotEmptyCollection(purchaseOrders)){
                String s = JsonUtil.toJson(purchaseOrders);
                List<PurchaseOrder> saves = JSONObject.parseArray(s, PurchaseOrder.class);

                saves.forEach(p -> {
                    p.setPurchaseOrderId(IdUtil.purchaseId());
                    //因为状态都一样所以写死了
                    p.setPurchaseOrderStatus(1);
                    String preArrivalDate = p.getPreArrivalDate();
                    Date date = DateUtils.getDate(p.getPreArrivalDate());
                    p.setPreArrivalTime(date);
                });
                if (CollectionUtils.isNotEmptyCollection(saves)) {
                    this.purchaseOrderDao.insertMany(saves);
                }
            }

        }


    }


}
