package com.aiqin.bms.scmp.api.supplier.web.contract;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.ContractByUsernameReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.ContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.contract.vo.QueryContractReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.ContractPurchaseResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.ContractResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.contract.QueryContractResVo;
import com.aiqin.bms.scmp.api.supplier.service.ContractService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @description:
 * @author:曾兴旺
 * @date: 2018/11/30
 */
@RestController
@Api(description = "合同管理")
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    /**
     * 分页获取合同列表
     * @param vo
     * @return
     */
    @ApiOperation("查询合同List")
    @PostMapping("/getContractList")
    public HttpResponse<BasePage<QueryContractResVo>> getContractList(@RequestBody @Valid QueryContractReqVo vo) {
        return HttpResponse.success(contractService.findContractList(vo));
    }

    /**
     * 保存合同
     * @param contractReqVo
     * @return
     */
    @ApiOperation("新增合同")
    @PostMapping("/saveContract")
    public HttpResponse<ContractReqVo> saveContract(@RequestBody  @Valid ContractReqVo contractReqVo){
        try {
            int kp = contractService.saveContract(contractReqVo);
            return HttpResponse.success(kp);
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.ADD_ERROR);
        }
    }

    /**
     * 查看合同详情
     * @param id
     * @return
     */
    @ApiOperation("查看合同详情")
    @GetMapping("/getContract")
    public HttpResponse<ContractResVo> findContractDetail(@RequestParam  @ApiParam( value = "传入主键id" ,required = true) Long id){
        try {
            ContractResVo contractRespVo = contractService.findContractDetail(id);
            return HttpResponse.success(contractRespVo);
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }
//    /**
//     *  通过传过来申请修改通过的申请合同，保存合同修改
//     * @param contractReqVo
//     * @return
//     */
//    @ApiOperation("修改合同")
//    @PutMapping("/updateContract")
//    public Object updateContract(@RequestBody @Valid ContractReqVo contractReqVo){
//        try {
//            int kp = contractService.updateContract(contractReqVo);
//            return HttpResponse.success(kp);
//        }catch (Exception ex){
//            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
//        }
//    }

    /**
     * 逻辑删除合同
     * @param id
     * @return
     */
    @ApiOperation("逻辑删除合同")
    @DeleteMapping("/deleteContract")
    public HttpResponse<Integer> deleteContract(@RequestParam  @ApiParam( value = "传入主键id" ,required =true) Long id){
        try {
            return HttpResponse.success(contractService.deleteContract(id));
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.DELETE_ERROR);
        }
    }

    @ApiOperation("根据创建人查询合同")
    @PostMapping("/getContractByUsername")
    public  HttpResponse<List<ContractResVo>>  getContractByUsername(@RequestBody ContractByUsernameReqVo reqVO){
        try {
            return HttpResponse.success(contractService.getContractByUsername(reqVO));
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }

    @ApiOperation("查询当人所属公司和采购组的合同")
    @PostMapping("/getContractByPurchaseGroup")
    public  HttpResponse<List<ContractPurchaseResVo>> getContractByPurchaseGroup(){
        try {
            return HttpResponse.success(contractService.getContractByPurchaseGroup());
        }catch (Exception ex){
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
    }

}
