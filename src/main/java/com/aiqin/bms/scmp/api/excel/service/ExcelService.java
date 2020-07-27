package com.aiqin.bms.scmp.api.excel.service;

import com.aiqin.bms.scmp.api.excel.domain.*;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseOrderDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseOrderProductDao;
import com.aiqin.bms.scmp.api.purchase.dao.RejectRecordDao;
import com.aiqin.bms.scmp.api.purchase.dao.RejectRecordDetailDao;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoMapper;
import com.aiqin.bms.scmp.api.util.excel.utils.ExcelUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ExcelService {


    @Resource
    private SaveService saveService;

    /**
     * 导入单据到线上数据库
     *
     * @param multipartFile
     */
    public HttpResponse importExcel(MultipartFile multipartFile) throws Exception {
        FileInputStream inputStream = (FileInputStream) multipartFile.getInputStream();
        //String originalFilename = multipartFile.getOriginalFilename();
        // Workbook wb = null;
        try {
//            log.info("============开始创建Workbook对象============================");
//            if (WDWUtil.isExcel2007(originalFilename)) {
//                wb = new XSSFWorkbook(inputStream);
//            } else {
//                wb = new HSSFWorkbook(inputStream);
//            }
//            log.info("============创建Workbook对象成功============================");
//            int numberOfSheets = wb.getNumberOfSheets();
            for (int i = 1; i < 9; i++) {
                if (i == 1) {
                    //采购单主表
                    List<PurchaseOrderExcel> purchaseOrderExcels = ExcelUtil.readExcel(multipartFile, PurchaseOrderExcel.class, i + 1);
                    saveService.saveDb(purchaseOrderExcels);
                } else if (i == 2) {
                    //采购单明细
                    List<PurchaseOrderProductExcel> pus = ExcelUtil.readExcel(multipartFile, PurchaseOrderProductExcel.class, i + 1);
                    saveService.savepurchaseOrderProductDb(pus);
                } else if (i == 3) {
                    //退供主表
                    List<RejectRecordExcel> rrs = ExcelUtil.readExcel(multipartFile, RejectRecordExcel.class, i + 1);
                    saveService.saveRejectRecord(rrs);
                } else if (i == 4) {
                    //退供表明细
                    List<RejectRecordDetailExcel> rrd = ExcelUtil.readExcel(multipartFile, RejectRecordDetailExcel.class, i + 1);
                    saveService.saveRejectRecordDetail(rrd);

                } else if (i == 5) {
                    //销售主表
                    List<OrderInfoExcel> of = ExcelUtil.readExcel(multipartFile, OrderInfoExcel.class, i + 1);
                    saveService.saveOrderInfo(of);

                } else if (i == 6) {
                    //销售明细
                    List<OrderInfoItemExcel> oft = ExcelUtil.readExcel(multipartFile, OrderInfoItemExcel.class, i + 1);
                    saveService.saveOrderInfoItem(oft);
                } else if (i == 7) {
                    //退货主表
                    List<ReturnOrderInfoExcel> returnOrderInfoExcels = ExcelUtil.readExcel(multipartFile, ReturnOrderInfoExcel.class, i + 1);
                    saveService.saveReturnOrderInfo(returnOrderInfoExcels);
                } else if (i == 8) {
                    //退货明细
                    List<ReturnOrderInfoItemExcel> ReturnOrderInfoItemExcels = ExcelUtil.readExcel(multipartFile, ReturnOrderInfoItemExcel.class, i + 1);
                    saveService.saveReturnOrderInfoItem(ReturnOrderInfoItemExcels);
                }

            }

        } catch (Exception e) {
            log.error("异常=={}", e.toString());
            return HttpResponse.failure(MessageId.create(Project.SCMP_API, 10008, e.getMessage()));
        } finally {
//            try {
//                if (wb != null) {
//                    wb.close();
//                }
//            } catch (IOException ignore) {
//                log.error("Workbook关闭异常=={}", ignore);
//                return HttpResponse.failure(MessageId.create(Project.SCMP_API, 10008, ignore.getMessage()));
//            }

            try {
                inputStream.close();
            } catch (IOException e) {
                log.error("FileInputStream关闭异常=={}", e);
                return HttpResponse.failure(MessageId.create(Project.SCMP_API, 10008, e.getMessage()));
            }
        }
        return HttpResponse.success();
    }


}