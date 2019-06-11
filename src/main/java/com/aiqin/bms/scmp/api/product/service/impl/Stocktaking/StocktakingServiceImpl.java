package com.aiqin.bms.scmp.api.product.service.impl.Stocktaking;

import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.StocktakingDao;
import com.aiqin.bms.scmp.api.product.domain.Stocktaking.StocktakingInfo;
import com.aiqin.bms.scmp.api.product.domain.Stocktaking.StocktakingProductInfo;
import com.aiqin.bms.scmp.api.product.domain.request.Stocktaking.SelectStocktakingRequest;
import com.aiqin.bms.scmp.api.product.domain.request.Stocktaking.SelectStocktakingWholeRequest;
import com.aiqin.bms.scmp.api.product.domain.request.Stocktaking.StocktakingWhole;
import com.aiqin.bms.scmp.api.product.domain.request.dictionary.ProductDistributorQuVoPage;
import com.aiqin.bms.scmp.api.product.service.InventoryService;
import com.aiqin.bms.scmp.api.product.service.Stocktaking.StocktakingService;
import com.aiqin.bms.scmp.api.util.UUIDUtils;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 盘点
 * @author hzy
 *
 */
@Slf4j
@Service
public class StocktakingServiceImpl implements StocktakingService  {


    @Autowired
    private StocktakingDao stocktakingDao;
    
    @Resource
    private InventoryService inventoryService;

    
    //添加盘点数据
	@Override
	@Transactional(rollbackFor = GroundRuntimeException.class)
	public HttpResponse addStocktakingWhole(StocktakingWhole param) {
		
		if(param !=null) {
			
			//新增盘点数据
			StocktakingInfo info = new StocktakingInfo();
			info = param.getStocktakingInfo();
			try {
				if(info !=null) {
					if(info.getStocktakingDate() !=null && !info.getStocktakingDate().equals("")) {
						//盘点ID、盘点编码
						info.setStocktakingId(UUIDUtils.getUUID());
						info.setStocktakingCode(getStocktakingCode(info.getStocktakingDate()));
						//保存
						stocktakingDao.addStocktakingInfo(info);
					}else {
						log.warn("新增盘点日期为空");
						return HttpResponse.failure(ResultCode.NOT_HAVE_PARAM);
					}
				}
			} catch (Exception e) {
				log.error("新增盘点数据异常:{}",e);
				return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
			}
			
			//新增盘点商品数据
			List<StocktakingProductInfo> list = new ArrayList();
			list = param.getProductList();
			try {
				if(list !=null && list.size()>0) {
					for(StocktakingProductInfo productInfo : list) {
						//盘点ID、盘点商品ID
						productInfo.setStocktakingId(info.getStocktakingId());
						productInfo.setStocktakingProductId(UUIDUtils.getUUID());
						productInfo.setCreateBy(info.getCreateBy());
					    //保存
						stocktakingDao.addProductInfo(productInfo);
					}
				}
				return HttpResponse.success();
				
			} catch (Exception e) {
				log.error("新增盘点商品数据异常:{}",e);
				return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
			}
			
		}else {
			log.warn("新增盘点数据为null");
			return null;
		}
	}

	//盘点单编号：
	//共15位
	//PD+yymmdd+公司编号+五位随机数
	private String getStocktakingCode(String date) {
		String code = "";
		date = date.replaceAll("-","");
		code = "PD"+date+Global.COMPANY_01+UUIDUtils.randomNumber();
		return code;
	}


	//查询库存
	@Override
	public HttpResponse searchInventories(ProductDistributorQuVoPage param) {
		
		try {
			return HttpResponse.success(inventoryService.getPtDiReVoPage(param));
			
		} catch (Exception e) {
            log.error("查询库存异常：[{}]", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
	}

	
	//查询盘点记录
	@Override
	public HttpResponse selectStocktaking(SelectStocktakingRequest param) {
		List<StocktakingInfo> list = new ArrayList();
		try {
			//分页数据
			list = stocktakingDao.selectStocktakingList(param);
			//总数据量
			Integer totalCount = null;
			totalCount = stocktakingDao.countStocktaking(param);
			
			return HttpResponse.success(new PageResData(totalCount,list));
			
		} catch (Exception e) {
			log.error("查询盘点记录失败:[{}]",e);
			return HttpResponse.failure(ResultCode.SELECT_STOCKTAKING_ERROR);
		}
	}
	
	//查询盘点详情
	@Override
	public HttpResponse selectStocktakingWhole(SelectStocktakingWholeRequest param) {
		
		if(param !=null && param.getStocktakingIdList() !=null && param.getStocktakingIdList().size()>0) {
			
		}else {
			param.setStocktakingIdList(null);
		}
		
		//返回数据
		StocktakingWhole info = new StocktakingWhole();
		
		//组装盘点数据
		StocktakingInfo stocktakingInfo = new StocktakingInfo();
		try {
			stocktakingInfo = stocktakingDao.selectStocktaking(param);
			info.setStocktakingInfo(stocktakingInfo);
		} catch (Exception e) {
			log.error("组装盘点数据失败:[{}]",e);
			return HttpResponse.failure(ResultCode.SELECT_STOCKTAKING_ERROR);
		}
		
		//组装盘点商品数据
		List<StocktakingProductInfo> productList = new ArrayList();
		try {
			if(stocktakingInfo !=null) {
				param.setStocktakingId(stocktakingInfo.getStocktakingId());
				productList = stocktakingDao.selectProductList(param);
				info.setProductList(productList);	
			}
		} catch (Exception e) {
			log.error("组装盘点数据失败:[{}]",e);
			return HttpResponse.failure(ResultCode.SELECT_STOCKTAKING_WHOLE_ERROR);
		}
		
		//组装盘点商品数据总数据条数
		Integer totalCount = null;
		try {
			if(stocktakingInfo !=null) {
				param.setStocktakingId(stocktakingInfo.getStocktakingId());
				totalCount = stocktakingDao.countProductList(param);
				info.setTotalCount(totalCount);
			}
			return HttpResponse.success(info);
			
		} catch (Exception e) {
			log.error("组装盘点商品数据总数据条数失败:[{}]",e);
			return HttpResponse.failure(ResultCode.SELECT_STOCKTAKING_WHOLE_ERROR);
		}
	}

	//更新商品信息删除标识
	@Override
	public HttpResponse updateDelFlag(@Valid String stocktakingProductId) {
		try {
			stocktakingDao.updateDelFlag(stocktakingProductId);
			return HttpResponse.success();
		} catch (Exception e) {
			log.error("更新商品信息删除标识失败:[{}]",e);
			return HttpResponse.failure(ResultCode.UPDATEDELFLAG);
		}
	}
}
