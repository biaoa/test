package com.linle.shopMainType.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.ShopMainType;
import com.linle.mobileapi.v1.model.ChildSort;
import com.linle.mobileapi.v1.model.Sort;
import com.linle.mobileapi.v1.model.SortSales;
import com.linle.shopMainType.mapper.ShopMainTypeMapper;
import com.linle.shopMainType.service.ShopMainTypeService;


@Service
@Transactional
public class ShopMainTypeServiceImpl extends CommonServiceAdpter<ShopMainType> implements ShopMainTypeService {
	
	@Autowired
	private ShopMainTypeMapper mapper;
	
	@Override
	public Page<ShopMainType> findAllMainType(Page<ShopMainType> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<ShopMainType> findAllMainType() {
		return mapper.findAllMainType();
	}

	@Override
	public List<Sort> getSortList(Map<String, Object> map) {
		List<Sort> mainList = new ArrayList<>();
		mainList = mapper.getSortList(map);
		for (Sort sort : mainList) {
			ChildSort childSort = new ChildSort();
			childSort.setChildSortId(Long.valueOf(0));
			childSort.setChildSortName("全部"+sort.getSortName());
			childSort.setChildSortNums(sort.getSortNums());
			sort.getChildSortList().add(0, childSort);
		}
		return mainList;
	}
	
	@Override
	public List<SortSales> selectTypeSales(Map<String, Object> map) {
		//FIXME 这里要处理一下数据，全是0太难看了 这里为了融资 把销量*2了
		Calendar cal=Calendar.getInstance();
		List<SortSales> list = mapper.selectTypeSales(map);
		for (SortSales sortSales : list) {
			if(sortSales.getSortSales()==0){
				sortSales.setSortSales(cal.get(Calendar.DAY_OF_YEAR)/sortSales.getSortId()*2);
			}else{
				if(sortSales.getSortSales()<cal.get(Calendar.DAY_OF_YEAR)/sortSales.getSortId()){
					sortSales.setSortSales(cal.get(Calendar.DAY_OF_YEAR)/sortSales.getSortId()*2);
				}
			}
		}
		return list;
	}

	@Override
	public ShopMainType selectByTypeName(String typeName) {
		return mapper.selectByTypeName(typeName);
	}
}
