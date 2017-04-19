package com.linle.shopPreferential.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.ShopPreferential;
import com.linle.shopPreferential.mapper.ShopPreferentialMapper;
import com.linle.shopPreferential.service.ShopPreferentialService;


@Service
@Transactional
public class ShopPreferentialServiceImpl extends CommonServiceAdpter<ShopPreferential>
		implements ShopPreferentialService {

	@Autowired
	private ShopPreferentialMapper mapper;

	@Override
	public Page<ShopPreferential> findAllPreferential(Page<ShopPreferential> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<ShopPreferential> selectPreferentials(String[] ids) {
		return mapper.selectPreferentials(ids);
	}
	

}
