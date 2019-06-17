package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.purchasegroup.PurchaseGroupBuyerDao;
import com.aiqin.bms.scmp.api.supplier.dao.purchasegroup.PurchaseGroupDao;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.UrlConfig;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.dto.PurchaseGroupBuyerDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.dto.PurchaseGroupDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.PurchaseGroupReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.QueryPurchaseGroupReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.UpdatePurchaseGroupReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.UserPositionsRequest;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.*;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
import com.aiqin.bms.scmp.api.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 采购管理组service实现层
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Service
@Slf4j
public class PurchaseGroupServiceImpl  implements PurchaseGroupService {


    @Autowired
    private PurchaseGroupDao purchaseGroupDao;

    @Autowired
    private PurchaseGroupBuyerDao purchaseGroupBuyerDao;

    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Autowired
    private UrlConfig urlConfig;

    /**
     * 分页查询
     * @param vo
     * @return
     */
    @Override
    public BasePage<QueryPurchaseGroupResVo> findPurchaseGroupList(QueryPurchaseGroupReqVo vo) {
        try {
            PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                vo.setCompanyCode(authToken.getCompanyCode());
            }
            List<PurchaseGroupDTO> purchaseGroupDTOS = purchaseGroupDao.selectByPurchaseGroup(vo);
            // 获取分页参数
            BasePage<QueryPurchaseGroupResVo> basePage = PageUtil.getPageList(vo.getPageNo(),purchaseGroupDTOS);
            //转化成返回实体
            List<QueryPurchaseGroupResVo> list = BeanCopyUtils.copyList(purchaseGroupDTOS,QueryPurchaseGroupResVo.class);
            // 通过采购组编码去查询关联人员
            for (QueryPurchaseGroupResVo vo1:list){
                List<PurchaseGroupBuyerDTO> groupBuyerDTOList = purchaseGroupBuyerDao.selectByPurchaseCode(vo1.getPurchaseGroupCode());
                //转化关联人员实体并且set到返回实体
                vo1.setBuyerResVoList(BeanCopyUtils.copyList(groupBuyerDTOList, QueryPurchaseGroupBuyerResVo.class));
                List<String> stringList = new ArrayList<>();
                vo1.getBuyerResVoList().stream().forEach(purchase -> stringList.add(purchase.getBuyerName()));
                vo1.setBuyers(String.join(",",stringList));
            }
            basePage.setDataList(list);
            return basePage;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("分页查询失败");
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     * 新增采购组转化实体
     * @param purchaseGroupReqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public HttpResponse<Integer> savePurchaseGroup(PurchaseGroupReqVo purchaseGroupReqVo) {

        String companyCode = "";
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            companyCode =  authToken.getCompanyCode();
        }
        String codNa = purchaseGroupReqVo.getPurchaseGroupName();
        Integer integer=purchaseGroupDao.checkName(codNa,null,companyCode);
        if(integer>0){
            throw new GroundRuntimeException("采购组名称不能重复");
        }
        //转化实体
        PurchaseGroupDTO purchaseGroupDTO = new PurchaseGroupDTO();
        BeanCopyUtils.copy(purchaseGroupReqVo,purchaseGroupDTO);
        //生成采购组编号
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.PURCHASE_GROUP_CODE);
        purchaseGroupDTO.setPurchaseGroupCode(String.valueOf(encodingRule.getNumberingValue()));
        // 更新数据库编码尺度
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        //设置采购组主体的删除状态，启用禁用状态
        purchaseGroupDTO.setDelFlag(StatusTypeCode.UN_DEL_FLAG.getStatus());
        purchaseGroupDTO.setEnable(StatusTypeCode.DIS_ABLE.getStatus());
        //保存采购组的主体
        int k = ((PurchaseGroupService) AopContext.currentProxy()).insertSelective(purchaseGroupDTO);
        if( k>0){
            //采购人员转化成数据库访问实体
            try {
                if(CollectionUtils.isNotEmptyCollection(purchaseGroupReqVo.getGroupBuyerReqVoList())){
                    List<PurchaseGroupBuyerDTO> groupBuyerDTOList =  BeanCopyUtils.copyList(purchaseGroupReqVo.getGroupBuyerReqVoList(),PurchaseGroupBuyerDTO.class);
                    // 设置关联编码
                    groupBuyerDTOList.stream().forEach(purchase -> purchase.setPurchaseGroupCode(String.valueOf(purchaseGroupDTO.getPurchaseGroupCode())));
                    //设置启用禁用状态
                    groupBuyerDTOList.stream().forEach(purchase -> purchase.setEnable(StatusTypeCode.DIS_ABLE.getStatus()));
                    // 设置逻辑删除状态
                    groupBuyerDTOList.stream().forEach(purchase -> purchase.setDelFlag(StatusTypeCode.UN_DEL_FLAG.getStatus()));
                    // 保存采购组专员
                    int kp = ((PurchaseGroupService)AopContext.currentProxy()).saveList(groupBuyerDTOList);
                    if(kp>0){
                        return  HttpResponse.success(kp);
                    }else {
                        throw new GroundRuntimeException("保存采购管理组专员失败");
                    }
                }else{
                    return  HttpResponse.success(k);
                }
            } catch (Exception e) {
                throw new GroundRuntimeException("转化实体出错");
            }
        }else {
            throw new GroundRuntimeException("保存采购管理组主体失败");
        }
    }

    /**
     * 通过id去获取采购组详情
     * @param id
     * @return
     */
    @Override
    public PurchaseGroupResVo findPurchaseGroupDetail(Long id) {
        if(id!=null){
            PurchaseGroupResVo purchaseGroupResVo = new PurchaseGroupResVo();
            //通过id查询采购组详情
            PurchaseGroupDTO purchaseGroupDTO = purchaseGroupDao.selectByPrimaryKey(id);
            //转化成返回实体
            BeanCopyUtils.copy(purchaseGroupDTO,purchaseGroupResVo);
            //查询采购专员
            List<PurchaseGroupBuyerDTO> purchaseGroupBuyerDTOS = purchaseGroupBuyerDao.enableByPurchaseCode(purchaseGroupResVo.getPurchaseGroupCode());
            //实体转化
            try {
                purchaseGroupResVo.setGroupBuyerResVos(BeanCopyUtils.copyList(purchaseGroupBuyerDTOS, PurchaseGroupBuyerResVo.class));
                return purchaseGroupResVo;
            } catch (Exception e) {
                throw new GroundRuntimeException("实体转出错误");
            }
        }else {
            throw new GroundRuntimeException("查询合同详情失败");
        }
    }

    /**
     * 更新采购组转化实体
     * @param updatePurchaseGroupReqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public  HttpResponse<Integer> updatePurchaseGroup(UpdatePurchaseGroupReqVo updatePurchaseGroupReqVo) {
        PurchaseGroupDTO purchaseGroupDTO = new PurchaseGroupDTO();

        // 转化成数据库访问实体
        BeanCopyUtils.copy(updatePurchaseGroupReqVo,purchaseGroupDTO);

        String codNa = updatePurchaseGroupReqVo.getPurchaseGroupName();
        String companyCode = "";
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            companyCode =  authToken.getCompanyCode();
        }
        Integer integer=purchaseGroupDao.checkName(codNa,updatePurchaseGroupReqVo.getId(),companyCode);
        if(integer>0){
            throw new GroundRuntimeException("采购组名称不能重复");
        }

        int k =  ((PurchaseGroupService) AopContext.currentProxy()).updateByPrimaryKeySelective(purchaseGroupDTO);
        if (k>0){
            try {
                List<PurchaseGroupBuyerDTO > list = BeanCopyUtils.copyList(updatePurchaseGroupReqVo.getGroupBuyerReqVoList(),PurchaseGroupBuyerDTO.class);
                // 将采购管理组人员分两种情况，有id的执行修改，没有id的执行添加
                List<PurchaseGroupBuyerDTO > addList = new ArrayList<>();
                List<PurchaseGroupBuyerDTO> updateList = new ArrayList<>();
                for (PurchaseGroupBuyerDTO dto : list) {
                    if (dto.getId()==null){
                        addList.add(dto);
                    }else {
                        updateList.add(dto);
                    }
                }
                if(addList.size()>0){
                    //设置关联编码
                    addList.stream().forEach(purchase -> purchase.setPurchaseGroupCode(String.valueOf(purchaseGroupDTO.getPurchaseGroupCode())));
                    // 保存采购组管理人员
                    int kp = ((PurchaseGroupService)AopContext.currentProxy()).saveList(addList);
                }
                if (updateList.size() > 0) {
                    int kp = ((PurchaseGroupService)AopContext.currentProxy()).updateList(updateList);
                }
                return HttpResponse.success(k);
            } catch (Exception e) {
                throw new GroundRuntimeException("更新采购管理组失败");
            }

        }else {
            throw new GroundRuntimeException("更新采购管理组失败");
        }
    }

    /**
     * 保存采购组主体
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public int insertSelective(PurchaseGroupDTO record) {
      try {
          AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
          if(null != authToken){
              record.setCompanyCode(authToken.getCompanyCode());
              record.setCompanyName(authToken.getCompanyName());
          }
          return purchaseGroupDao.insertSelective(record);
      }catch (Exception e){
          throw new GroundRuntimeException("保存采购管理组主体失败");
      }
    }

    /**
     * 批量保存采购组人员
     * @param purchaseGroupBuyerDTOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @SaveList
    public int saveList(List<PurchaseGroupBuyerDTO> purchaseGroupBuyerDTOS) {
        try {
            return purchaseGroupBuyerDao.saveList(purchaseGroupBuyerDTOS);
        }catch (Exception e){
            throw new GroundRuntimeException("保存采购管理组人员失败");
        }
    }

    /**
     * 通过id去更新采购管理组主体
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int updateByPrimaryKeySelective(PurchaseGroupDTO record) {
       try{
           return purchaseGroupDao.updateByPrimaryKeySelective(record);

       }catch (Exception e){
           throw new GroundRuntimeException("更新采购管理组失败");
       }
    }

    /**
     * 批量更新采购管理组专员
     * @param purchaseGroupBuyerDTOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @UpdateList
    public int updateList(List<PurchaseGroupBuyerDTO> purchaseGroupBuyerDTOS) {
        try {
            return purchaseGroupBuyerDao.updateList(purchaseGroupBuyerDTOS);
        }catch (Exception e){
            throw new GroundRuntimeException("更新采购管理组人员失败");
        }
    }

    /**
     * 提供采购组接口
     * @return
     */
    @Override
    public List<PurchaseGroupVo> getPurchaseGroup() {
        try{
            String companyCode = "";
            String personId = "";
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                companyCode = authToken.getCompanyCode();
                personId = authToken.getPersonId();
            }
            List<PurchaseGroupVo> list = purchaseGroupDao.getPurchaseGroup(companyCode,personId);
//            for (PurchaseGroupVo purchaseGroupVo : list) {
//                //查询未禁用的关联人员
//                List<PurchaseGroupBuyerDTO> groupBuyerDTOList = purchaseGroupBuyerDao.selectByPurchaseCode(purchaseGroupVo.getPurchaseGroupCode());
//                //转化关联人员实体并且set到返回实体
//                purchaseGroupVo.setList(BeanCopyUtils.copyList(groupBuyerDTOList, PurchaseGroupBuyerVo.class));
//            }
            return list;
        }catch (Exception e){
            throw new GroundRuntimeException("转化数据出错");
        }

    }

    /**
     * 获取外购人员信息
     * @return
     */
    @Override
    public HttpResponse getPurchaseGroupBuyerList(UserPositionsRequest userPositionsRequest) {
        String companyCode = null;
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            companyCode = authToken.getCompanyCode();
        }
        BasicNameValuePair pair1 =  new BasicNameValuePair("page_no", userPositionsRequest.getPageNo().toString());
        BasicNameValuePair pair2 =  new BasicNameValuePair("page_size", userPositionsRequest.getPageSize().toString());
        BasicNameValuePair pair3 =  new BasicNameValuePair("person_name", userPositionsRequest.getPersonName());
        BasicNameValuePair pair4 =  new BasicNameValuePair("position_level_name", userPositionsRequest.getPositionLevelName());
        BasicNameValuePair pair5 =  new BasicNameValuePair("position_name", userPositionsRequest.getPositionName());
        BasicNameValuePair pair6 =  new BasicNameValuePair("company_code", companyCode);

        String url = urlConfig.CENTRAL_URL+"/person/list";
        HttpClient orderOperationClient = HttpClient.get(url).addParameters(pair1,pair2,pair3,pair4,pair5,pair6);
        HttpResponse orderDto = orderOperationClient.action().result(HttpResponse.class);
        return orderDto;
    }
}
