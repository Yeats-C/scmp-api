//package com.aiqin.bms.scmp.api.product.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.fuwushe.api.interfacees.IBizApiLogService;
//import org.fuwushe.api.interfacees.IBizSapApiService;
//import org.fuwushe.api.interfacees.IBizSapBusinessRelationService;
//import org.fuwushe.api.mq.ApiTypeEnum;
//import org.fuwushe.api.mq.MQMessageInfo;
//import org.fuwushe.api.util.SetSystemProperty;
//import org.fuwushe.common.exception.FServiceException;
//import org.fuwushe.common.sapjco3.SapUtil;
//import org.fuwushe.common.utils.PropertyPlaceholder;
//import org.fuwushe.common.utils.ValidateUtil;
//import org.fuwushe.dao.base.Condition;
//import org.fuwushe.dao.base.IBaseDao;
//import org.fuwushe.domain.biz.BizApiLog;
//import org.fuwushe.domain.sap.BizSapBusinessRelation;
//import org.fuwushe.enums.BizSapBusinessRelationEnum;
//import org.fuwushe.enums.Op;
//import org.fuwushe.enums.OpType;
//import org.fuwushe.service.impl.BaseServiceImpl;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.math.BigInteger;
//import java.sql.Time;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import static org.fuwushe.api.mq.ApiTypeEnum.DL_SALE_MASTERORDER_BATCH;
//
///**
// *  @author liuyujian
// *  @date 2019/3/22 10:41
// */
//@Service("bizSapApiServiceImpl")
//public class BizSapApiServiceImpl extends BaseServiceImpl<BizApiLog> implements IBizSapApiService {
//	private static final Logger logger = LoggerFactory.getLogger(BizSapApiServiceImpl.class);
//	@Resource(name="bizSapBusinessRelationServiceImpl")
//	private IBizSapBusinessRelationService sapBusinessRelationService;
//	@Resource(name="baseDaoImpl")
//	private IBaseDao baseDao;
//	@Resource(name = "bizApiLogServiceImpl")
//	private IBizApiLogService bizApiLogService;
//
//
//	@Override
//	public void createSendToSap(long logId, SapUtil sapUtil){
//		try {
//			BizApiLog apiLog = this.findById(logId);
//			if (apiLog !=null && apiLog.getStatus() ==0){
//				long bizId  = apiLog.getBizId();
//				ApiTypeEnum typeEnum =ApiTypeEnum.getEnum(apiLog.getInterfaceType());
//				Object rfcName = "";
//				Object methodCode = "";
//				String jsonData = "";
//				switch (typeEnum) {
//					//商品数据
//					case DL_PRODUCT:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullProductParam(bizId));
//						System.out.println(jsonData);
//						break;
//					//供应商资料
//					case DL_PROVIDER:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullProviderParam(bizId));
//						System.out.println(jsonData);
//						break;
//					//门店主数据
//					case FRANCHISEE_SALE_ORGANIZATION:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullFSOParam(bizId));
//						System.out.println(jsonData);
//						break;
//					//组织机构客户
//					case ORGANIZTION_CUSTOMER:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullOCParam(bizId));
//						System.out.println(jsonData);
//						break;
//					//采购订单
//					case DC_SUPPLY_ORDER:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullSupplyOrderParam(bizId));
//						System.out.println(jsonData);
//						break;
//					//调拨单
//					case DC_TRANSFER_IMP:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullDCTransfer(bizId));
//						System.out.println(jsonData);
//						break;
//					case DC_STOCK_RETURN:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullReturnOrderParam(bizId));
//						System.out.println(jsonData);
//						break;
//					//指定损溢
//					case DL_PROFLOSS:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullDCProflossParam(bizId));
//						System.out.println(jsonData);
//						break;
//					//盘点管理
//					case DL_STOCK_SHEET:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullDCProflossOrStockSheet(bizId,apiLog));
//						System.out.println(jsonData);
//						break;
//					case DL_STOCKPICK:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullStockPickOrderParam(bizId));
//						System.out.println(jsonData);
//						break;
//					case DL_STOCKPICK_RETURN:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullStockPickReturnOrderParam(bizId));
//						System.out.println(jsonData);
//						break;
//					case DL_SALE_MASTERORDER:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullSalesParam(bizId));
//						System.out.println(jsonData);
//						break;
//					case DC_FSO_DCRETURN:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullFSOSalesReturnParam(bizId));
//						System.out.println(jsonData);
//						break;
//					case DC_SODCSUBRETURN:
//						jsonData = SapUtil.buildParam(sapBusinessRelationService.fullOCSalesReturnParam(bizId));
//						System.out.println(jsonData);
//						break;
//					default:
//						break;
//				}
//
//				rfcName = PropertyPlaceholder.getProperty("sap.rfcName");
//				methodCode =PropertyPlaceholder.getProperty("sap."+typeEnum.getCode()+".methodCode");
//				//表示没有对应的接口需要传递数据
//				if (jsonData == null){
//					return;
//				}else if (rfcName == null || methodCode == null){
//					//那么需要判断配置文件是否有对应的rfcname配置和 methodCode配置
//					throw  new FServiceException("接口："+typeEnum.getName()+"("+typeEnum.getCode()+") 对应的 配置文件,没有配置项为：sap."+typeEnum.getCode()+".rfcName");
//				}
//				String returnJsonData =sapUtil.createSend(rfcName.toString(), methodCode.toString(), jsonData);
//				returnJsonData = returnJsonData.toLowerCase();
//				logger.error("返回的数据为：\n"+returnJsonData);
//				try {
//					//更新状态为已经发送状态,并保存发送的业务数据信息
//					bizApiLogService.updateRequestAfterSuccessInfo(logId,jsonData,(short)1);
//				}catch (Exception e){
//					e.printStackTrace();
//					logger.error("更新状态为已经发送状态,并保存发送的业务数据信息------失败");
//					throw  new RuntimeException(e.getMessage());
//				}
//				if (ValidateUtil.isNull(returnJsonData)){
//					//已发送但是对方执行操作返回失败！此时status 为 已发送但是未执行
//					bizApiLogService.updateResponseAfterFailureInfo(logId,returnJsonData);
//				}else{
//					JSONArray jsonArray = JSONArray.parseArray(returnJsonData);
//					for (int i = 0; i < jsonArray.size(); i++) {
//						JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//						//成功失败标记 。MSGTY:E-失败；S-成功
//						String returnFlag  = jsonObject.getString("msgty");
//						if("S".equalsIgnoreCase(returnFlag) ){
//							try {
//								//处理成功并且置为已执行状态
//								bizApiLogService.updateResponseAfterSuccessInfo(logId,returnJsonData);
//								if (typeEnum == ApiTypeEnum.FRANCHISEE_SALE_ORGANIZATION || typeEnum==ApiTypeEnum.ORGANIZTION_CUSTOMER){
//									saveOrUpdateFSOIdSAPCodeMatch(jsonObject, bizId);
//								}
//
//							}catch (Exception e){
//								e.printStackTrace();
//								logger.error("处理成功并且置为已执行状态-----失败");
//								throw  new RuntimeException(e.getMessage());
//							}
//						}else{
//							try {
//								//已发送但是对方执行操作失败！此时status 为 已发送但是未执行
//								bizApiLogService.updateResponseAfterFailureInfo(logId,returnJsonData);
//							}catch (Exception e){
//								e.printStackTrace();
//								logger.error("已发送但是对方执行操作失败！此时status 为 已发送但是未执行-----失败");
//								throw  new RuntimeException(e.getMessage());
//							}
//						}
//					}
//				}
//
//				logger.info("\n——————发送给sap的消息已处理完成！——————");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw  new RuntimeException(e.getMessage());
//		}
//	}
//
//	public void updateOrderRecordStatus(long bizId, short status, String description) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("bizId", bizId);
//		map.put("status", status);
//		map.put("description", description);
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		map.put("modifiedTime", sdf.format(new Date()));
//		System.out.println(map);
//
//		baseDao.executeByDynamicName("updateSapOrderStatus", map);
//	}
//
//	public void saveOrUpdateFSOIdSAPCodeMatch(JSONObject jsonObject, long bizId){
//		String name = ((JSONObject) JSON.parse(jsonObject.getString("kna1s"))).getString("name1");
//		ArrayList<Condition> conditions = new ArrayList<>();
//		conditions.add(new Condition("bizId", bizId, Op.EQ, OpType.AND));
//		conditions.add(new Condition("bizType", 33, Op.EQ, OpType.AND));
//		List<BizSapBusinessRelation> relations = baseDao.findList(BizSapBusinessRelation.class,conditions,null);
//		Date now = new Date();
//		BizSapBusinessRelation relation = null;
//		if (relations.size() == 0){
//			relation = new BizSapBusinessRelation();
//			relation.setBizType(BizSapBusinessRelationEnum.FSO_ID_CODE.getCode());
//			relation.setBizId(bizId);
//			relation.setBizCode(jsonObject.getString("kunnr"));
//			relation.setBizName(name);
//			relation.setUseStatus((short) 0);
//			relation.setCreateTime(now);
//			relation.setModifyTime(now);
//		}else{
//			relation = relations.get(0);
//			relation.setBizCode(jsonObject.getString("kunnr"));
//			relation.setBizName(name);
//			relation.setModifyTime(now);
//		}
//		baseDao.save(relation);
//	}
//
//	@Override
//	public void createSendToSap(MQMessageInfo info, SapUtil sapUtil){
//		try {
//			SetSystemProperty setSystemProperty = new SetSystemProperty();
//			if (!"".equals(info.getBizCode()) || null != info.getBizCode()){
//				Object rfcName = PropertyPlaceholder.getProperty("sap.rfcName");
//				Object methodCode = PropertyPlaceholder.getProperty("sap."+info.getType().getCode()+".methodCode");
//				String jsonData = "";
//				List<String> idStr = JSON.parseArray(info.getBizCode(),String.class);//全部商品id集合
//				int DLDataIndex = 0;
//				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();//组装数据包的map
//				int counter = 0;//计数器，一个数据包中有counter条数据
//				switch (info.getType()) {
//					case DL_STOCKPICK:
//
//						break;
//					//商品数据
//					case DL_PRODUCT_BATCH:
//						try {
//							//查询出product中以0开头的code
//							String queryProductCodeWithZeroSQL = "select Code from biz_dl.biz_tbl_product where `Code` LIKE '0%'";
//							List<String> zeroCodeProductList = baseDao.queryBySQL(queryProductCodeWithZeroSQL);
//							Map<String, String> zeroCodeMap = new HashMap<String, String>(400);
//							for (String code : zeroCodeProductList) {
//								zeroCodeMap.put(code + "", code);
//							}
//							int count = 0;
//							mapList = sapBusinessRelationService.batckFullProductParam(idStr);
//							for (int i = 0; i < mapList.size(); i += 1000) {
//								List<Map<String, Object>> subTempMapList = null;
//								if (i + 1000 < mapList.size()) {
//									subTempMapList = mapList.subList(i, i + 1000);
//								} else {
//									subTempMapList = mapList.subList(i, mapList.size());
//								}
//								jsonData = SapUtil.buildParam(subTempMapList);
//								System.out.println(jsonData);
//								String returnJsonData = sapUtil
//										.createSend(rfcName.toString(), methodCode.toString(), jsonData);
//								returnJsonData = returnJsonData.toLowerCase();
//								JSONArray array = JSON.parseArray(returnJsonData);
//								System.out.println("返回数据包大小：" + array.size());
//								for (int k = 0; k < array.size(); k++) {
//									System.out.println("已经处理：" + (i + k) + "条数据");
//									JSONObject map = array.getJSONObject(k);
//									if ("S".equalsIgnoreCase(map.get("msgty") + "")) {
//										System.out.println("成功:" + (++count) + "条数据");
//									} else {
//										String code = (String) map.get("matnr");
//										if (zeroCodeMap.get(code + "") != null) {
//											code = zeroCodeMap.get(code + "");
//										}
//										System.out.println(code + "商品信息同步出错！" + map.get("msage").toString());
//									}
//								}
//
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//							throw  new RuntimeException(e.getMessage());
//						}
//
//
//						break;
//					//供应商数据
//					case DL_PROVIDER_BATCH:
//						String providerSQL = "select Id from stfoa.sysadmin_tbl_provider ";
//						List<BigInteger> providerIdList = baseDao.queryBySQL(providerSQL);
//						counter = 0;
//						for (BigInteger id : providerIdList) {
//							try {
//								long dlBeginTime = System.currentTimeMillis();
//								counter++;
//								Map<String, Object> map = sapBusinessRelationService.fullProviderParam(id.longValue());
//								mapList.add(map);
//								if ((counter % 100 != 0) && (counter != providerIdList.size())) {
//									continue;
//								}
//								jsonData = SapUtil.buildParam(mapList);
//								//表示没有对应的接口需要传递数据
//								if (rfcName == null || methodCode == null) {
//									System.out.println("rfcname或methodCode错误！");
//									continue;
//								}
//								Long beginTime = System.currentTimeMillis();
//								String returnJsonData = sapUtil
//										.createSend(rfcName.toString(), methodCode.toString(), jsonData);
//								System.out.println("sap处理时间:" + (System.currentTimeMillis() - beginTime) / 1000 + "s");
//								mapList.clear();
//								returnJsonData = returnJsonData.toLowerCase();
//								System.out.println("returnJsonData:" + returnJsonData);
//								JSONArray array = JSON.parseArray(returnJsonData);
//								for (int i = 0; i < array.size(); i++) {
//									JSONObject elementMap = array.getJSONObject(i);
//									if ("S".equalsIgnoreCase(elementMap.get("msgty") + "")) {
//										System.out.println("success");
//									} else {
//										String code = (String) elementMap.get("lifnr");
//										System.out.println(code + "同步不成功！" + (String) elementMap.get("msage"));
//									}
//								}
//								logger.info("同步供应商数据" + id + "成功,目前同步供应商数据数量：" + counter);
//								logger.info("供应商数据处理时间：" + (System.currentTimeMillis() - dlBeginTime) / 1000 + "s");
//							} catch (Exception e) {
//								e.printStackTrace();
//								System.out.println("组装数据包出错！");
//								throw  new RuntimeException(e.getMessage());
//							}
//						}
//						break;
//					case FRANCHISEE_SALE_ORGANIZATION_BATCH:
//						String fsoSQL = "select id from stfoa.biz_tbl_department where DeptType = 5 ";
//						List<BigInteger> fsoIdList = baseDao.queryBySQL(fsoSQL);
//						counter = 0;
//						for (BigInteger id : fsoIdList) {
//							try {
//								counter++;
//								Map<String, Object> map = sapBusinessRelationService.fullFSOParam(id.longValue());
//								mapList.add(map);
//								jsonData = SapUtil.buildParam(mapList);
//								if (rfcName == null || methodCode == null) {
//									System.out.println("rfcname或methodCode错误！");
//									continue;
//								}
//								String returnJsonData = null;
//								if (!jsonData.isEmpty()) {
//									returnJsonData = sapUtil
//											.createSend(rfcName.toString(), methodCode.toString(), jsonData);
//								}
//								mapList.clear();
//								returnJsonData = returnJsonData.toLowerCase();
//								System.out.println("returnJsonData:" + returnJsonData);
//								JSONArray array = JSON.parseArray(returnJsonData);
//								for (int i = 0; i < array.size(); i++) {
//									JSONObject jsonmap = array.getJSONObject(i);
//									if ("S".equalsIgnoreCase(jsonmap.get("msgty") + "")) {
//										saveOrUpdateFSOIdSAPCodeMatch(jsonmap, id.longValue());
//									} else {
//										String code = (String) jsonmap.get("kunnr");
//										System.out.println(code + "分销机构信息同步失败！" + (String) jsonmap.get("msage"));
//									}
//								}
//								logger.info("同步门店客户数据" + id + "成功,目前同步门店客户数据数量：" + counter);
//							} catch (Exception e) {
//								e.printStackTrace();
//								System.out.println("组装分销机构数据包出错！");
//								throw  new RuntimeException(e.getMessage());
//							}
//						}
//						break;
//					case ORGANIZTION_CUSTOMER_BATCH:
//						String sqlOC = " select subCustomer.Id as id from biz_dl.biz_tbl_subcustomer as subCustomer ";
//						String[] fields = new String[]{"id"};
//						counter=0;
//						List<Map<String, Object>> idList = baseDao.queryBySQL(sqlOC, fields);
//						for (Map<String, Object> element : idList) {
//							String id = element.get("id").toString();
//							try {
//								counter++;
//								Map<String, Object> map = sapBusinessRelationService.fullOCParam(Long.valueOf(id));
//								if (map.isEmpty()) {
//									continue;
//								} else {
//									mapList.add(map);
//									map = null;
//								}
//								jsonData = SapUtil.buildParam(mapList);
//								System.out.println(jsonData);
//								//表示没有对应的接口需要传递数据
//								if (rfcName == null || methodCode == null) {
//									System.out.println("rfcname或methodCode错误！");
//									continue;
//								}
//								try {
//									String returnJsonData = null;
//									if (!jsonData.isEmpty()) {
//										returnJsonData = sapUtil
//												.createSend(rfcName.toString(), methodCode.toString(), jsonData);
//									}
//									mapList.clear();
//									returnJsonData = returnJsonData.toLowerCase();
//									System.out.println("returnJsonData:" + returnJsonData);
//									JSONArray array = JSON.parseArray(returnJsonData);
//									for (int i = 0; i < array.size(); i++) {
//										JSONObject jsonmap = array.getJSONObject(i);
//										if ("S".equalsIgnoreCase(jsonmap.get("msgty") + "")) {
//											saveOrUpdateFSOIdSAPCodeMatch(jsonmap, Long.valueOf(id));
//										} else {
//											String code = (String) jsonmap.get("kunnr");
//											System.out.println(code + "组织机构信息同步失败！" + jsonmap.get("msage"));
//										}
//									}
//								} catch (Exception e) {
//									e.printStackTrace();
//									throw  new RuntimeException(e.getMessage());
//								}
//								logger.info("同步组织机构客户数据" + id + "成功,目前同步组织机构客户数据数量：" + counter);
//							} catch (Exception e) {
//								e.printStackTrace();
//								throw  new RuntimeException(e.getMessage());
//							}
//						}
//						break;
//					case DC_SUPPLY_ORDER_BATCH:
//						String supplyOrderBatchSQL = "select Id, OrderId from sap.biz_sap_trans_record where Status = 0 and OrderType='DC_SUPPLY_ORDER'";
//						String[] supplyOrderfields = new String[]{"Id", "OrderId"};
//						List<Map<String, Object>> supplyOrderIdList = baseDao
//								.queryBySQL(supplyOrderBatchSQL, supplyOrderfields);
//						for (Map<String, Object> element : supplyOrderIdList) {
//							try {
//								long orderId = Long.valueOf(element.get("OrderId").toString());
//								long bizId = Long.valueOf(element.get("Id").toString());
//								if (rfcName == null || methodCode == null) {
//									System.out.println("rfcname或methodCode错误！");
//									continue;
//								}
//								System.out.println("orderId="+orderId);
//								jsonData = SapUtil.buildParam(sapBusinessRelationService.fullSupplyOrderParam(orderId));
//								String returnJsonData = sapUtil
//										.createSend(rfcName.toString(), methodCode.toString(), jsonData);
//								returnJsonData = returnJsonData.toLowerCase();
//								System.out.println("returnJsonData:" + returnJsonData);
//								JSONArray array = JSON.parseArray(returnJsonData);
//								for (int i = 0; i < array.size(); i++) {
//									JSONObject elementMap = array.getJSONObject(i);
//									if ("S".equalsIgnoreCase(elementMap.get("msgty") + "")) {
//										updateOrderRecordStatus(bizId, (short)1, "success");
//									} else {
//										updateOrderRecordStatus(bizId, (short)0, (String)elementMap.get("msage"));
//									}
//								}
//								logger.info("同步采购订单数据" + orderId + "成功,目前同步采购订单数据数量：" + counter++);
//							} catch (Exception e) {
//								e.printStackTrace();
//								System.out.println("组装数据包出错！");
//								throw  new RuntimeException(e.getMessage());
//							}
//						}
//						break;
//					case DL_SALE_MASTERORDER_BATCH:
//						String masterOrderBatchSQL = "select Id, OrderId from sap.biz_sap_trans_record where Status = 0 and OrderType='DL_SALE_MASTERORDER'";
//						String[] masterOrderfields = new String[]{"Id", "OrderId"};
//						List<Map<String, Object>> masterOrderIdList = baseDao
//								.queryBySQL(masterOrderBatchSQL, masterOrderfields);
//						for (Map<String, Object> element : masterOrderIdList) {
//							try {
//								long orderId = Long.valueOf(element.get("OrderId").toString());
//								long bizId = Long.valueOf(element.get("Id").toString());
//								if (rfcName == null || methodCode == null) {
//									System.out.println("rfcname或methodCode错误！");
//									continue;
//								}
//								jsonData = SapUtil.buildParam(sapBusinessRelationService.fullSalesParam(orderId));
//								String returnJsonData = sapUtil
//										.createSend(rfcName.toString(), methodCode.toString(), jsonData);
//								returnJsonData = returnJsonData.toLowerCase();
//								System.out.println("returnJsonData:" + returnJsonData);
//								JSONArray array = JSON.parseArray(returnJsonData);
//								for (int i = 0; i < array.size(); i++) {
//									JSONObject elementMap = array.getJSONObject(i);
//									if ("S".equalsIgnoreCase(elementMap.get("msgty") + "")) {
//										updateOrderRecordStatus(bizId, (short)1, "success");
//									} else {
//										updateOrderRecordStatus(bizId, (short)0, (String)elementMap.get("msage"));
//									}
//								}
//								logger.info("同步销售订单数据" + orderId + "成功,目前同步销售订单数据数量：" + counter++);
//							} catch (Exception e) {
//								e.printStackTrace();
//								System.out.println("组装数据包出错！");
//								throw  new RuntimeException(e.getMessage());
//							}
//						}
//						break;
//					case DC_STOCK_RETURN_BATCH:
//						String supplyReturnOrderBatchSQL = "select Id,OrderId from sap.biz_sap_trans_record where Status = 0 and OrderType='DC_STOCK_RETURN'";
//						String[] supplyReturnOrderfields = new String[]{"Id", "OrderId"};
//						List<Map<String, Object>> supplyReturnOrderIdList = baseDao
//								.queryBySQL(supplyReturnOrderBatchSQL, supplyReturnOrderfields);
//						for (Map<String, Object> element : supplyReturnOrderIdList) {
//							try {
//								long orderId = Long.valueOf(element.get("OrderId").toString());
//								long bizId = Long.valueOf(element.get("Id").toString());
//								if (rfcName == null || methodCode == null) {
//									System.out.println("rfcname或methodCode错误！");
//									continue;
//								}
//								jsonData = SapUtil.buildParam(sapBusinessRelationService.fullReturnOrderParam(orderId));
//								System.out.println(jsonData);
//								String returnJsonData = sapUtil
//										.createSend(rfcName.toString(), methodCode.toString(), jsonData);
//								returnJsonData = returnJsonData.toLowerCase();
//								System.out.println("returnJsonData:" + returnJsonData);
//								JSONArray array = JSON.parseArray(returnJsonData);
//								for (int i = 0; i < array.size(); i++) {
//									JSONObject elementMap = array.getJSONObject(i);
//									if ("S".equalsIgnoreCase(elementMap.get("msgty") + "")) {
//										updateOrderRecordStatus(bizId, (short)1, "success");
//									} else {
//										updateOrderRecordStatus(bizId, (short)0, (String)elementMap.get("msage"));
//									}
//								}
//								logger.info("同步采购退货单数据" + orderId + "成功,目前同步采购退货单数据数量：" + counter++);
//							} catch (Exception e) {
//								e.printStackTrace();
//								System.out.println("组装数据包出错！");
//								throw  new RuntimeException(e.getMessage());
//							}
//						}
//						break;
//					case DC_TRANSFER_BATCH:
//						String dcTransferOrderBatchSQL = "select Id, OrderId, OrderType from sap.biz_sap_trans_record where Status = 0 and (OrderType='DC_TRANSFER_FROM' or OrderType='DC_TRANSFER_IMP' or OrderType='DC_TRANSFER_CANCEL') order by Id asc";
//						String[] dcTransferOrderfields = new String[]{"Id", "OrderId","OrderType"};
//						List<Map<String, Object>> dcTransferOrderIdList = baseDao
//								.queryBySQL(dcTransferOrderBatchSQL, dcTransferOrderfields);
//						for (Map<String, Object> element : dcTransferOrderIdList) {
//							try {
//								long orderId = Long.valueOf(element.get("OrderId").toString());
//								long bizId = Long.valueOf(element.get("Id").toString());
//								String orderTypeStr = String.valueOf(element.get("OrderType"));
//								int orderType = 0;
//								switch (orderTypeStr){
//									case "DC_TRANSFER_FROM":
//										orderType = 1;
//										break;
//									case "DC_TRANSFER_IMP":
//										orderType = 2;
//										break;
//									case "DC_TRANSFER_CANCEL":
//										orderType = 4;
//										break;
//									default:
//										break;
//								}
//
//								if (rfcName == null || methodCode == null) {
//									System.out.println("rfcname或methodCode错误！");
//									continue;
//								}
//								jsonData = SapUtil.buildParam(sapBusinessRelationService.fullDCTransfer(orderId,orderType));
//								String returnJsonData = sapUtil
//										.createSend(rfcName.toString(), methodCode.toString(), jsonData);
//								returnJsonData = returnJsonData.toLowerCase();
//								System.out.println("returnJsonData:" + returnJsonData);
//								JSONArray array = JSON.parseArray(returnJsonData);
//								for (int i = 0; i < array.size(); i++) {
//									JSONObject elementMap = array.getJSONObject(i);
//									if ("S".equalsIgnoreCase(elementMap.get("msgty") + "")) {
//										updateOrderRecordStatus(bizId, (short)1, "success");
//									} else {
//										updateOrderRecordStatus(bizId, (short)0, (String)elementMap.get("msage"));
//									}
//								}
//								logger.info("同步库存调拨单数据" + orderId + "成功,目前同步库存调拨单数据数量：" + counter++);
//							} catch (Exception e) {
//								e.printStackTrace();
//								System.out.println("组装数据包出错！");
//								throw  new RuntimeException(e.getMessage());
//							}
//						}
//						break;
//					case DL_PROFLOSS_BATCH:
//						String proflossBatchSQL = "select Id,OrderId from sap.biz_sap_trans_record where Status = 0 and OrderType='DL_PROFLOSS'";
//						String[] proflossOrderfields = new String[]{"Id", "OrderId"};
//						List<Map<String, Object>> proflossOrderIdList = baseDao
//								.queryBySQL(proflossBatchSQL, proflossOrderfields);
//						logger.info("指定损溢查询结果："+proflossOrderIdList.get(0));
//						for (Map<String, Object> element : proflossOrderIdList) {
//							try {
//								long orderId = Long.valueOf(element.get("OrderId").toString());
//								long bizId = Long.valueOf(element.get("Id").toString());
//								if (rfcName == null || methodCode == null) {
//									System.out.println("rfcname或methodCode错误！");
//									continue;
//								}
//								logger.info("损溢单id："+orderId);
//								jsonData = SapUtil.buildParam(sapBusinessRelationService.fullDCProflossParam(orderId));
//								System.out.println("请求jso数据:" + jsonData);
//								String returnJsonData = sapUtil
//										.createSend(rfcName.toString(), methodCode.toString(), jsonData);
//								returnJsonData = returnJsonData.toLowerCase();
//								System.out.println("returnJsonData:" + returnJsonData);
//								JSONArray array = JSON.parseArray(returnJsonData);
//								for (int i = 0; i < array.size(); i++) {
//									JSONObject elementMap = array.getJSONObject(i);
//									if ("S".equalsIgnoreCase(elementMap.get("msgty") + "")) {
//										updateOrderRecordStatus(bizId, (short)1, "success");
//									} else {
//										updateOrderRecordStatus(bizId, (short)0, (String)elementMap.get("msage"));
//									}
//								}
//								logger.info("同步指定损溢数据" + orderId + "成功,目前同步指定损溢数据数量：" + counter++);
//							} catch (Exception e) {
//								e.printStackTrace();
//								System.out.println("组装数据包出错！");
//								throw  new RuntimeException(e.getMessage());
//							}
//						}
//						break;
//					case DL_STOCK_SHEET_BATCH:
//						String stockSheetBatchSQL = "select Id,OrderId from sap.biz_sap_trans_record where Status = 0 and OrderType='DL_STOCK_SHEET'";
//						String[] stockSheetNamefields = new String[]{"Id", "OrderId"};
//						List<Map<String, Object>> stockSheetIdList = baseDao
//								.queryBySQL(stockSheetBatchSQL, stockSheetNamefields);
//						for (Map<String, Object> element : stockSheetIdList) {
//							try {
//								long orderId = Long.valueOf(element.get("OrderId").toString());
//								long bizId = Long.valueOf(element.get("Id").toString());
//								if (rfcName == null || methodCode == null) {
//									System.out.println("rfcname或methodCode错误！");
//									continue;
//								}
//								jsonData = SapUtil
//										.buildParam(sapBusinessRelationService.fullDCStockSheetParam(orderId));
//								String returnJsonData = sapUtil
//										.createSend(rfcName.toString(), methodCode.toString(), jsonData);
//								returnJsonData = returnJsonData.toLowerCase();
//								System.out.println("returnJsonData:" + returnJsonData);
//								JSONArray array = JSON.parseArray(returnJsonData);
//								for (int i = 0; i < array.size(); i++) {
//									JSONObject elementMap = array.getJSONObject(i);
//									if ("S".equalsIgnoreCase(elementMap.get("msgty") + "")) {
//										updateOrderRecordStatus(bizId, (short)1, "success");
//									} else {
//										updateOrderRecordStatus(bizId, (short)0, (String)elementMap.get("msage"));
//									}
//								}
//								logger.info("同步盘点损溢数据" + orderId + "成功,目前同步盘点损溢数据数量：" + counter++);
//							} catch (Exception e) {
//								e.printStackTrace();
//								System.out.println(element.get("OrderId").toString());
//								System.out.println("组装数据包出错！");
//								throw  new RuntimeException(e.getMessage());
//							}
//						}
//						break;
//					case DC_SODCSUBRETURN_BATCH:
//						String soDcSubReturnBatchSQL = "select Id,OrderId from sap.biz_sap_trans_record where Status = 0 and OrderType='DC_SODCSUBRETURN'";
//						String[] soDcSubReturnNamefields = new String[]{"Id", "OrderId"};
//						List<Map<String, Object>> soDcSubReturnIdList = baseDao
//								.queryBySQL(soDcSubReturnBatchSQL, soDcSubReturnNamefields);
//						for (Map<String, Object> element : soDcSubReturnIdList) {
//							try {
//								long orderId = Long.valueOf(element.get("OrderId").toString());
//								long bizId = Long.valueOf(element.get("Id").toString());
//								if (rfcName == null || methodCode == null) {
//									System.out.println("rfcname或methodCode错误！");
//									continue;
//								}
//								jsonData = SapUtil
//										.buildParam(sapBusinessRelationService.fullOCSalesReturnParam(orderId));
//								String returnJsonData = sapUtil
//										.createSend(rfcName.toString(), methodCode.toString(), jsonData);
//								returnJsonData = returnJsonData.toLowerCase();
//								System.out.println("returnJsonData:" + returnJsonData);
//								JSONArray array = JSON.parseArray(returnJsonData);
//								for (int i = 0; i < array.size(); i++) {
//									JSONObject elementMap = array.getJSONObject(i);
//									if ("S".equalsIgnoreCase(elementMap.get("msgty") + "")) {
//										updateOrderRecordStatus(bizId, (short)1, "success");
//									} else {
//										updateOrderRecordStatus(bizId, (short)0, (String)elementMap.get("msage"));
//									}
//								}
//								logger.info("同步批发销售退货数据" + orderId + "成功,目前同步批发销售退货数据数量：" + counter++);
//							} catch (Exception e) {
//								e.printStackTrace();
//								System.out.println(element.get("OrderId").toString());
//								System.out.println("组装数据包出错！");
//								throw  new RuntimeException(e.getMessage());
//							}
//						}
//						break;
//					case DC_FSO_DCRETURN_BATCH:
//						System.out.println("业务类型："+info.getType());
//						String fsoReturnOrderBatchSQL = " select Id,OrderId from sap.biz_sap_trans_record where Status = 0 and OrderType='DC_FSO_DCRETURN'";
//						String[] fsoReturnOrderfields = new String[]{"Id", "OrderId"};
//						List<Map<String, Object>> fsoReturnOrderIdList = baseDao
//								.queryBySQL(fsoReturnOrderBatchSQL, fsoReturnOrderfields);
//						System.out.println("查询结果："+fsoReturnOrderIdList.size());
//						for (Map<String, Object> element : fsoReturnOrderIdList) {
//							try {
//								long orderId = Long.valueOf(element.get("OrderId").toString());
//								long bizId = Long.valueOf(element.get("Id").toString());
//								if (rfcName == null || methodCode == null) {
//									System.out.println("rfcname或methodCode错误！");
//									continue;
//								}
//								jsonData = SapUtil.buildParam(sapBusinessRelationService.fullFSOSalesReturnParam(orderId));
//								System.out.println(jsonData);
//								String returnJsonData = sapUtil
//										.createSend(rfcName.toString(), methodCode.toString(), jsonData);
//								returnJsonData = returnJsonData.toLowerCase();
//								System.out.println("returnJsonData:" + returnJsonData);
//								JSONArray array = JSON.parseArray(returnJsonData);
//								for (int i = 0; i < array.size(); i++) {
//									JSONObject elementMap = array.getJSONObject(i);
//									if ("S".equalsIgnoreCase(elementMap.get("msgty") + "")) {
//										updateOrderRecordStatus(bizId, (short)1, "success");
//									} else {
//										updateOrderRecordStatus(bizId, (short)0, (String)elementMap.get("msage"));
//									}
//								}
//								logger.info("同步门店销售退货单数据" + orderId + "成功,目前同步门店销售退货单数据数量：" + counter++);
//							} catch (Exception e) {
//								e.printStackTrace();
//								System.out.println("组装数据包出错！");
//								throw  new RuntimeException(e.getMessage());
//							}
//						}
//						break;
//					default:
//						break;
//
//				}
//				logger.info("\n——————发送给sap的消息已处理完成！——————");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw  new RuntimeException(e.getMessage());
//		}
//	}
//}
