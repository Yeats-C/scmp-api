package com.aiqin.bms.scmp.api.supplier.web;

import com.aiqin.bms.scmp.api.util.BrowserUtils;
import com.aiqin.bms.scmp.api.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author HuangLong
 * @date 2018/12/25
 */
@Controller
@Slf4j
public class SupplierBaseController {
//    @Autowired
//    private ApplySupplyComAcctService applySupplyComAcctService;
//
//    @Autowired
//    private ApplyContractService applyContractService;
//
//    @Autowired
//    private ApplySupplierService applySupplierService;
//
//    @Autowired
//    private ApplySupplyComServcie applySupplyComServcie;
    /**
     * 导出数据
     *
     * @param response
     * @param request
     * @param sheetName
     * @param title
     * @param list
     */
    protected void doExportXls(HttpServletResponse response,
                               HttpServletRequest request, String sheetName, String[] title, List<?> list) {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream fOut = null;
        try {
            // 根据浏览器进行转码，使其支持中文文件名
            if (BrowserUtils.isIE(request)) {
                response.setHeader("content-disposition",
                        "attachment;filename=" + java.net.URLEncoder.encode(sheetName, "UTF-8") + ".xls");
            } else {
                String newtitle = new String(sheetName.getBytes("UTF-8"), "iso-8859-1");
                response.setHeader("content-disposition", "attachment;filename=" + newtitle + ".xls");
            }
            response.setCharacterEncoding("utf-8");
            // 产生工作簿对象
            HSSFWorkbook workbook = null;
            workbook = ExcelUtil.getHSSFWorkbook(sheetName, title, list, null);
            fOut = response.getOutputStream();
            workbook.write(fOut);
        } catch (Exception e) {
            log.error("error", e);
            throw new RuntimeException("导出失败！");
        } finally {
            try {
                if(fOut != null){
                    fOut.flush();
                    fOut.close();
                }
            } catch (IOException e) {
                log.error("error", e);
            }
        }
    }
//    @PostMapping("supplier/workFlowCallBack/{type}")
//    @ApiOperation("审批回调")
//    @ResponseBody
//    public String workFlowCallBack(@RequestBody WorkFlowCallbackVO vo,
//                                         @PathVariable Integer type){
//        log.info("BaseController-workFlowCallBack-参数是：[{}],类型是：[{}]", JSONObject.toJSONString(vo),type);
//        try {
//            switch (type){
//                case 1:
////                    return applyContractService.workFlowCallback(vo);
//                case 2:
//                    //供应商集团审批
//                    return applySupplierService.workFlowCallback(vo);
//                case 3:
//                    //供应商审批
//                    return applySupplyComServcie.workFlowCallback(vo);
//                case 4:
//                    //供应商账户的审批
//                    return applySupplyComAcctService.workFlowCallback(vo);
////                case 5:
////                    //申请制造商的审批
////                    return applyManufacturerService.workFlowCallBack(vo);
//                case 6:
//                case 7:
//                case 8:
////                    //调拨单的审批
////                    log.info("调拨单的审批");
////                    return allocationService.workFlowCallback(vo);
//                case 9:
//                case 10:
//                case 11:
//                default: return "false";
//            }
//        } catch (Exception e) {
//            log.error("error", e);
//            return "false";
//        }
//    }
}
