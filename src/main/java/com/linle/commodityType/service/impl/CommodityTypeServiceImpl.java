package com.linle.commodityType.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.commodityType.mapper.CommodityTypeMapper;
import com.linle.commodityType.service.CommodityTypeService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.CommodityType;
import com.linle.mobileapi.base.BaseResponse;

@Service
@Transactional
public class CommodityTypeServiceImpl extends CommonServiceAdpter<CommodityType> implements CommodityTypeService {
	
	@Autowired
	private CommodityTypeMapper mapper;
	
	@Override
	public Page<CommodityType> findAllCommodityType(Page<CommodityType> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<CommodityType> getAllTypeByShopID(Long id) {
		return mapper.getAllTypeByShopID(id);
	}
	
	public BaseResponse del(long id,long shopId){
		try {	
			//1.判断该商家该分类下面有商品没
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopId", shopId);
			params.put("typeId", id);
			long count=mapper.selectChildCommodityCount(params);
			if(count>0){
				return new BaseResponse(2,"该类型下已有商品，不能删除！");
			}
			//2.修改状态
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", 1);
			mapper.update(map);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("删除分类出错");
		}
		return BaseResponse.OperateSuccess;
	}

}
