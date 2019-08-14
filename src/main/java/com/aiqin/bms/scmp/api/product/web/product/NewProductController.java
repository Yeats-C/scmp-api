package com.aiqin.bms.scmp.api.product.web.product;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductSaveReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.NewProductUpdateReqVO;
import com.aiqin.bms.scmp.api.product.domain.request.newproduct.QueryNewProductReqVO;
import com.aiqin.bms.scmp.api.product.domain.response.newproduct.NewProductResponseVO;
import com.aiqin.bms.scmp.api.product.service.NewProductService;
import com.aiqin.bms.scmp.api.util.ExportExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@Api(description = "商品相关接口")
@RequestMapping("/newProduct")
@Slf4j
public class NewProductController {
    @Autowired
    private NewProductService newProductService;

    @PostMapping("/insert")
    @ApiOperation("商品新增")
    public HttpResponse<String> insertProduct(@RequestBody NewProductSaveReqVO newProductSaveReqVO) {
        try {
            return HttpResponse.success(newProductService.insertProduct(newProductSaveReqVO));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }

    @PutMapping("/update")
    @ApiOperation("商品修改")
    public HttpResponse<Integer> updateProduct(@RequestBody NewProductUpdateReqVO newProductUpdateReqVO) {
        try {
            return HttpResponse.success(newProductService.updateProduct(newProductUpdateReqVO));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }

    @PostMapping("/list")
    @ApiOperation("查询商品列表")
    public HttpResponse<List<NewProductResponseVO>> listSupplierDicionary(@RequestBody QueryNewProductReqVO queryNewProductReqVO) {
        try {
            return HttpResponse.success(newProductService.getList(queryNewProductReqVO));
        } catch (Exception e) {
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
        }
    }

    @GetMapping("/skuDetails")
    @ApiOperation("sku详情")
    public HttpResponse skuDetails(String productCode, String productName) {
        try {
            return HttpResponse.success(newProductService.productSku(productCode, productName));
        } catch (Exception ex) {
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, ex.getMessage()));
        }
    }
   @PostMapping("/excalList")
   @ApiOperation("导出列表")
   public HttpResponse<NewProductResponseVO>getExcalList(@RequestBody QueryNewProductReqVO queryNewProductReqVO){
       try {
           return HttpResponse.success(newProductService.getPageList(queryNewProductReqVO));
       } catch (Exception e) {
           return HttpResponse.failure(MessageId.create(Project.PRODUCT_API, -1, e.getMessage()));
       }
   }
    @PostMapping("/excel")
    @ApiOperation("导出")
    public void exportProduct(@RequestBody QueryNewProductReqVO queryNewProductReqVO, HttpServletResponse response) throws IOException {
        try {
            List<NewProductResponseVO> getList = newProductService.getPageList(queryNewProductReqVO);
            HSSFWorkbook wb = ExportExcel.exportData(getList);
            String excelName = "数据列表";
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;fileName=" + new String(excelName.getBytes("UTF-8"), "iso-8859-1"));
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception ex) {
            throw new GroundRuntimeException(ex.getMessage());
        }
    }
}
