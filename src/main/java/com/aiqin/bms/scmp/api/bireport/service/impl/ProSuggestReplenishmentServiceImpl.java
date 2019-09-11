package com.aiqin.bms.scmp.api.bireport.service.impl;

import com.aiqin.bms.scmp.api.bireport.dao.ProSuggestReplenishmentDao;
import com.aiqin.bms.scmp.api.bireport.domain.request.PurchaseApplyReqVo;
import com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase.PurchaseApplyRespVo;
import com.aiqin.bms.scmp.api.bireport.service.ProSuggestReplenishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class ProSuggestReplenishmentServiceImpl implements ProSuggestReplenishmentService{

    @Autowired
    private ProSuggestReplenishmentDao proSuggestReplenishmentDao;

    /**
     * 查询14大A品建议补货
     * @return
     */
    @Override
    public List<String> selectSuggestReplenishmentByPro() {
        int proStatus = 1;
        List<String> skuCodes = proSuggestReplenishmentDao.selectSuggestReplenishmentByPro(proStatus);
        return skuCodes;
    }

    /**
     * 获取畅销建议补货skuCode
     * @return
     */
    @Override
    public List<String> selectSuggestReplenishmentBySell() {
        List<String> skuCodes = proSuggestReplenishmentDao.selectSuggestReplenishmentBySell();
        return skuCodes;
    }

    /**
     * 查询14大A品缺货
     * @return
     */
    @Override
    public List<String> selectOutStockByPro() {
        int proStatus = 1;
        int continuousDays = 0;
        List<String> skuCodes = proSuggestReplenishmentDao.selectOutStockByPro(proStatus,continuousDays);
        return skuCodes;
    }

    /**
     * 获取畅销缺货
     * @return
     */
    @Override
    public List<String> selectOutStockBySell() {
     //   int proStatus = 1;
     //   int continuousDays = 0;
        List<String> skuCodes = proSuggestReplenishmentDao.selectOutStockBySell();
        return skuCodes;
    }

    /**
     * 获取采购申请列表
     * @return
     */
    @Override
    public PurchaseApplyRespVo selectPurchaseApplySkuList(PurchaseApplyReqVo purchaseApplyReqVo){
        PurchaseApplyRespVo purchaseApplyRespVoNum = proSuggestReplenishmentDao.selectPurchaseRuleNum();
        List<PurchaseApplyRespVo> purchaseApplyRespVos = proSuggestReplenishmentDao.selectPurchaseApplySkuList(purchaseApplyReqVo);
        PurchaseApplyRespVo purRespVo = new PurchaseApplyRespVo();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar calendar = Calendar.getInstance();
        Boolean flag = true;
        for (PurchaseApplyRespVo purchaseApplyRespVo : purchaseApplyRespVos) {
            if (purchaseApplyRespVo.getAdviceOrders() != null & purchaseApplyRespVo.getArrivalCycle() != null & purchaseApplyRespVo.getOutPuts() != null & purchaseApplyRespVo.getNeedDays() != null){
                if (!(purchaseApplyRespVo.getAdviceOrders() >= purchaseApplyRespVo.getOutPuts())) {
                    calendar.add(Calendar.DATE, purchaseApplyRespVo.getArrivalCycle().intValue()+purchaseApplyRespVo.getNeedDays().intValue()+purchaseApplyRespVoNum.getNumOrderApproved().intValue()+purchaseApplyRespVoNum.getNumApprovedPayment().intValue()+purchaseApplyRespVoNum.getNumPaymentConfirm().intValue());
                    purchaseApplyRespVo.setPredictedArrival(df.format(calendar.getTime()));
                    purRespVo = purchaseApplyRespVo;
                    purRespVo.setNumOrderApproved(purchaseApplyRespVoNum.getNumOrderApproved());
                    purRespVo.setNumApprovedPayment(purchaseApplyRespVoNum.getNumApprovedPayment());
                    purRespVo.setNumPaymentConfirm(purchaseApplyRespVoNum.getNumPaymentConfirm());
                    flag = false;
                    break;
                }
            }
        }
        if (flag){
            if (purchaseApplyRespVos.size()>0 ){
                PurchaseApplyRespVo purchaseApplyRespVo = purchaseApplyRespVos.get(purchaseApplyRespVos.size() - 1);
                if (purchaseApplyRespVo.getAdviceOrders() != null & purchaseApplyRespVo.getArrivalCycle() != null & purchaseApplyRespVo.getOutPuts() != null & purchaseApplyRespVo.getNeedDays() != null){
                    calendar.add(Calendar.DATE, purchaseApplyRespVo.getArrivalCycle().intValue()+purchaseApplyRespVo.getNeedDays().intValue()+purchaseApplyRespVoNum.getNumOrderApproved().intValue()+purchaseApplyRespVoNum.getNumApprovedPayment().intValue()+purchaseApplyRespVoNum.getNumPaymentConfirm().intValue());
                    purchaseApplyRespVo.setPredictedArrival(df.format(calendar.getTime()));
                    purRespVo = purchaseApplyRespVo;
                }
            }
        }
        return purRespVo;
    }
}
