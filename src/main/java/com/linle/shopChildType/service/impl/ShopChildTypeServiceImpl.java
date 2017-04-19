package com.linle.shopChildType.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.ShopChildType;
import com.linle.shopChildType.mapper.ShopChildTypeMapper;
import com.linle.shopChildType.service.ShopChildTypeService;

@Service
@Transactional
public class ShopChildTypeServiceImpl extends CommonServiceAdpter<ShopChildType> implements ShopChildTypeService {
	
	@Autowired
	private ShopChildTypeMapper mapper;

	@Override
	public Page<ShopChildType> findAllChildType(Page<ShopChildType> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<ShopChildType> selectByMain(Long mid) {
		return mapper.selectByMain(mid);
	}

	@Override
	public ShopChildType selectByTypeName(String typeName) {
		return mapper.selectByTypeName(typeName);
	}
}
