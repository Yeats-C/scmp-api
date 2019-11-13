package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.PricePromotionStatus;
import com.aiqin.bms.scmp.api.base.WorkFlowBaseUrl;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.bireport.dao.ProSuggestReplenishmentDao;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.dao.StockDao;
import com.aiqin.bms.scmp.api.product.dao.TaxCostLogDao;
import com.aiqin.bms.scmp.api.product.domain.request.price.PriceApplyPromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionDetailReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.LoadGeneratePromotionRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PriceApplyPromotionRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionDetailRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionProductRespVo;
import com.aiqin.bms.scmp.api.product.mapper.*;
import com.aiqin.bms.scmp.api.product.service.ProductApplyPromotionService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.supplier.mapper.PurchaseGroupBuyerMapper;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.IdSequenceUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: mamingze
 * @Date: 2019-11-05 11:18
 * @Description:
 */
@Service
public class ProductApplyPromotionServiceImpl extends BaseServiceImpl implements ProductApplyPromotionService {

  @Autowired
  private ProductApplyPromotionMapper productApplyPromotionMapper;

  @Autowired
  private PricePromotionDetailMapper pricePromotionDetailMapper;

    @Autowired
    private PricePromotionProductMapper pricePromotionProductMapper;

    @Autowired
    private PricePromotionMapper pricePromotionMapper;

    @Autowired
    private PurchaseGroupBuyerMapper purchaseGroupBuyerMapper;

    @Autowired
    private ProductSkuPriceInfoMapper productSkuPriceInfoMapper;

    @Autowired
    private WorkFlowBaseUrl workFlowBaseUrl;

    @Autowired
    private TaxCostLogDao taxCostLogDao;

    @Autowired
    private ProSuggestReplenishmentDao proSuggestReplenishmentDao;

    @Autowired
    private StockDao stockDao;


    @Override
    public BasePage<PriceApplyPromotionRespVo> list(PriceApplyPromotionReqVo priceApplyPromotionReqVo) {
       //获取当前用户
        AuthToken authToken = getUser();
        //获取当前用户的采购组列表
         List<String> purchaseGroupCodes= purchaseGroupBuyerMapper.loadCodesByPersonId(authToken.getPersonId());
        purchaseGroupBuyerMapper.loadNamesByPersonId(authToken.getPersonId());
     //   vo.setCompanyCode(authToken.getCompanyCode());
        PageHelper.startPage(priceApplyPromotionReqVo.getPageNo(),priceApplyPromotionReqVo.getPageSize());
        priceApplyPromotionReqVo.setPurchaseGroupCodes(purchaseGroupCodes);
        List<PriceApplyPromotionRespVo> priceApplyPromotionRespVoBasePage= productApplyPromotionMapper.list(priceApplyPromotionReqVo);
        return PageUtil.getPageList(priceApplyPromotionReqVo.getPageNo(),priceApplyPromotionReqVo.getPageSize(),priceApplyPromotionRespVoBasePage.size(),priceApplyPromotionRespVoBasePage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean add(PriceApplyPromotionReqVo priceApplyPromotionReqVo) throws Exception {
        //获取当前用户
        AuthToken authToken = getUser();
        //   vo.setCompanyCode(authToken.getCompanyCode());
        priceApplyPromotionReqVo.setCreateName(authToken.getPersonName());
        priceApplyPromotionReqVo.setCreateTimestamp(new Date());
        //获取编码
        synchronized (ProductApplyPromotionServiceImpl.class){
            String code = getCode("PAP", EncodingRuleType.APPLY_PROMOTION_NO);
            priceApplyPromotionReqVo.setApplyPromotionNo(code);
        }
        Long promotionId= IdSequenceUtils.getInstance().nextId();
        priceApplyPromotionReqVo.setId(promotionId);

        productApplyPromotionMapper.insert(priceApplyPromotionReqVo);
        //进行下属规则的添加，主键关联
        addDetailAndproduct(priceApplyPromotionReqVo);
        return true;
    }



    @Override
    public Boolean updateOrDelete(PriceApplyPromotionReqVo priceApplyPromotionReqVo,Integer type) {
        //进行调整
       if(type==1){
           //把规则下面的产品
           List<PricePromotionDetailRespVo> pricePromotionDetailRespVoList=pricePromotionDetailMapper.loadByPromotionId(priceApplyPromotionReqVo.getId());
           for (PricePromotionDetailRespVo pricePromotionDetailRespVo:
                   pricePromotionDetailRespVoList) {
               pricePromotionProductMapper.deteleByBusinessId(pricePromotionDetailRespVo.getId());
           }
           //把原来促销下面的规则删除了
           pricePromotionDetailMapper.deteleByPromotionId(priceApplyPromotionReqVo.getId());
           productApplyPromotionMapper.updateById(priceApplyPromotionReqVo);
           addDetailAndproduct(priceApplyPromotionReqVo);
       }else {
           productApplyPromotionMapper.deteleById(priceApplyPromotionReqVo.getId());
       }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PriceApplyPromotionRespVo load(Long id) {
        PriceApplyPromotionRespVo priceApplyPromotionRespVo=productApplyPromotionMapper.loadById(id);
        List<PricePromotionDetailRespVo> pricePromotionDetailRespVoList=pricePromotionDetailMapper.loadByPromotionId(id);
        for (PricePromotionDetailRespVo pricePromotionDetailRespVo:
        pricePromotionDetailRespVoList) {
            List<PricePromotionProductRespVo> pricePromotionProductRespVoList=pricePromotionProductMapper.loadByBusinessId(pricePromotionDetailRespVo.getId());
            for (PricePromotionProductRespVo pricePromotionProductRespVo:
            pricePromotionProductRespVoList) {
                //对有测算标识进行计算
                if(pricePromotionProductRespVo.getIsSign()==1){
                    calculationProduct(pricePromotionProductRespVo,priceApplyPromotionRespVo.getCategoriesSupplyChannelsCode());
                }
            }

            pricePromotionDetailRespVo.setPricePromotionProductRespVoList(pricePromotionProductRespVoList);
        }
        priceApplyPromotionRespVo.setPricePromotionDetailRespVoList(pricePromotionDetailRespVoList);
        return priceApplyPromotionRespVo;
    }

    //计算商品
   public void calculationProduct(PricePromotionProductRespVo pricePromotionProductRespVo,String categoriesSupplyChannelsCode){
       List<BigDecimal> prices =   taxCostLogDao.loadPriceByProductCode(pricePromotionProductRespVo.getProductCode());
       if (CollectionUtils.isNotEmptyCollection(prices)){
           //设置库存价格
           pricePromotionProductRespVo.setStockPrice(prices.get(0));
       }
       //获取供货渠道
//       String categoriesSupplyChannelsCode= priceApplyPromotionRespVo.getCategoriesSupplyChannelsCode();
       List<BigDecimal> prices2= productSkuPriceInfoMapper.getDistributionPriceByProductCode(pricePromotionProductRespVo.getProductCode(),categoriesSupplyChannelsCode);
       if (CollectionUtils.isNotEmptyCollection(prices)){
           //设置分销价格
           pricePromotionProductRespVo.setDistributionPrice(prices2.get(0));
       }
       //设置库存
       pricePromotionProductRespVo.setStockNum(stockDao.selectSkuCodeByQueryAvailableSum(pricePromotionProductRespVo.getProductCode()));
       //设置库存成本
       pricePromotionProductRespVo.setStockPrice(BigDecimal.valueOf(pricePromotionProductRespVo.getStockNum()).multiply(pricePromotionProductRespVo.getStockPrice()));
       //设置月平均销量
       Long num= proSuggestReplenishmentDao.biAppSuggestReplenishmentAllForPromotion(pricePromotionProductRespVo.getProductCode());
       pricePromotionProductRespVo.setAverageNumLastThreeMouth(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(30)));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean generatePromotion(List<PriceApplyPromotionReqVo> priceApplyPromotionReqVoList) {
        AuthToken authToken = getUser();
       List<PricePromotionReqVo> pricePromotionProductReqVoList= Lists.newArrayList();
        //获取促销编码
        PricePromotionReqVo pricePromotionReqVo=new PricePromotionReqVo();
        synchronized (ProductApplyPromotionServiceImpl.class){
            String code = getCode("PP", EncodingRuleType.PROMOTION_NO);
            pricePromotionReqVo.setPromotionNo(code);
        }
        pricePromotionReqVo.setId(IdSequenceUtils.getInstance().nextId());
        synchronized (ProductApplyPromotionServiceImpl.class){
            String  formNo = "PPA" + IdSequenceUtils.getInstance().nextId();
            pricePromotionReqVo.setFormNo(formNo);
        }
        pricePromotionReqVo.setStatus(PricePromotionStatus.WAIT_CHECK.getNum());
        pricePromotionReqVo.setCreateName(authToken.getPersonName());
        pricePromotionReqVo.setCreateTimestamp(new Date());
        pricePromotionMapper.insert(pricePromotionReqVo);
        for (PriceApplyPromotionReqVo priceApplyPromotionReqVo:
        priceApplyPromotionReqVoList  ) {
            priceApplyPromotionReqVo.setPromotionNo(pricePromotionReqVo.getPromotionNo());
        productApplyPromotionMapper.updateById(priceApplyPromotionReqVo);
       }
        workFlow(pricePromotionReqVo.getFormNo(),pricePromotionReqVo.getApplyPromotionNo(),authToken.getPersonName()
                ,pricePromotionReqVo.getDirectSupervisorName(),pricePromotionReqVo.getPromotionName(),pricePromotionReqVo.getRemark());

        return true;
    }




    //把数据传输给审批流
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void workFlow(String formNo, String applyCode, String userName,String directSupervisorCode,String approvalName,String approvalRemark) {
        WorkFlowVO workFlowVO = new WorkFlowVO();
//        workFlowVO.setFormUrl(workFlowBaseUrl.applySkuPromotion + "?approvalType=2&code=" + applyCode + "&" + workFlowBaseUrl.authority);
        workFlowVO.setHost(workFlowBaseUrl.supplierHost);
        //流程编号
        workFlowVO.setFormNo(formNo);
        //进行编码修改
        workFlowVO.setUpdateUrl(workFlowBaseUrl.callBackBaseUrl + WorkFlow.APPLY_GOODS_CONFIG.getNum());
        //进行审批名称的传输
        workFlowVO.setTitle(approvalName);
        //添加备注
        workFlowVO.setRemark(approvalRemark);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("auditPersonId",directSupervisorCode);
        workFlowVO.setVariables(jsonObject.toString());
        //传输进行
        WorkFlowRespVO workFlowRespVO = callWorkFlowApi(workFlowVO, WorkFlow.APPLY_GOODS_CONFIG);
        //判断是否成功
        if (workFlowRespVO.getSuccess()) {
            //TODO 这里暂时没有任何操作
        } else {
            throw new BizException(MessageId.create(Project.SCMP_API,999,workFlowRespVO.getMsg()));
        }
    }

    @Override
    public LoadGeneratePromotionRespVo loadGeneratePromotion(List<PriceApplyPromotionReqVo> priceApplyPromotionReqVoList) {
        LoadGeneratePromotionRespVo loadGeneratePromotionRespVo=new LoadGeneratePromotionRespVo();
        List<PriceApplyPromotionRespVo> priceApplyPromotionRespVos=Lists.newArrayList();
        for (PriceApplyPromotionReqVo priceApplyPromotionReqVo:
        priceApplyPromotionReqVoList) {
            PriceApplyPromotionRespVo priceApplyPromotionRespVo=new PriceApplyPromotionRespVo();
            BeanUtils.copyProperties(priceApplyPromotionReqVo,priceApplyPromotionRespVo);
            priceApplyPromotionRespVos.add(priceApplyPromotionRespVo);
        }
        loadGeneratePromotionRespVo.setPriceApplyPromotionRespVos(priceApplyPromotionRespVos);
        PriceApplyPromotionRespVo priceApplyPromotionRespVo = load(priceApplyPromotionReqVoList.get(0).getId());
        //买赠
        List<PricePromotionDetailRespVo> buyPromotionDetailList=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType()==1).collect(Collectors.toList());
        loadGeneratePromotionRespVo.setBuyPromotionDetailList(buyPromotionDetailList);
         //满赠
         List<PricePromotionDetailRespVo> enoughPromotionDetailList=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType()==2).collect(Collectors.toList());;
        loadGeneratePromotionRespVo.setEnoughPromotionDetailList(enoughPromotionDetailList);
         //满减
         List<PricePromotionDetailRespVo> reducePromotionDetailList=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType()==3).collect(Collectors.toList());;
        loadGeneratePromotionRespVo.setReducePromotionDetailList(reducePromotionDetailList);
        //满折
         List<PricePromotionDetailRespVo> discountPromotionDetailList=priceApplyPromotionRespVo.getPricePromotionDetailRespVoList().stream().filter(x->x.getPromotionType()==4).collect(Collectors.toList());;
        loadGeneratePromotionRespVo.setDiscountPromotionDetailList(discountPromotionDetailList);

        return loadGeneratePromotionRespVo;
    }


    @Override
    public List<String> loadNamesByPersonId() {
        AuthToken authToken = getUser();
        return purchaseGroupBuyerMapper.loadNamesByPersonId(authToken.getPersonId());
    }

    private void addDetailAndproduct(PriceApplyPromotionReqVo priceApplyPromotionReqVo){
        //进行下属规则的添加，主键关联
        List<PricePromotionDetailReqVo> pricePromotionDetailReqVoList= priceApplyPromotionReqVo.getPricePromotionDetailReqVoList();
        pricePromotionDetailReqVoList.stream().forEach(x->x.setPromotionId(priceApplyPromotionReqVo.getId()));
        pricePromotionDetailReqVoList.stream().forEach(x->x.setId(IdSequenceUtils.getInstance().nextId()));
        for (PricePromotionDetailReqVo pricePromotionDetailReqVo:
        pricePromotionDetailReqVoList ) {
//            if(pricePromotionDetailReqVo.getPromotionType().equals("2")) {
                pricePromotionDetailMapper.insert(pricePromotionDetailReqVo);
                //进行下属的产品的属性书写
                List<PricePromotionProductReqVo> pricePromotionProductReqVoList = pricePromotionDetailReqVo.getPricePromotionProductReqVoList();
                pricePromotionProductReqVoList.stream().forEach(x -> x.setBusinessId(pricePromotionDetailReqVo.getId()));
                pricePromotionProductReqVoList.stream().forEach(x -> x.setId(IdSequenceUtils.getInstance().nextId()));
                pricePromotionProductMapper.insertSelective(pricePromotionProductReqVoList);
//            }

        }

    }
}
