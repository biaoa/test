package com.linle.garage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.Garage;
import com.linle.entity.vo.SpaceOrder;
import com.linle.garage.mapper.GarageMapper;
import com.linle.garage.service.GarageService;

@Service("GarageService")
@Transactional
public class GarageServiceImpl extends CommonServiceAdpter<Garage> implements GarageService {
	
	
	@Autowired
	private GarageMapper mapper;
	
	@Override
	public Page<Garage> findAllGarage(Page<Garage> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<Garage> getGarageList(Long id) {
		return mapper.getGarageList(id);
	}

	@Override
	public Page<SpaceOrder> findAllGarageOrder(Page<SpaceOrder> page) {
		page.setResults(mapper.getAllGarageOrder(page));
		return page;
	}

	@Override
	public boolean del(Garage garage) {
		return mapper.del(garage)>0;
	}
}
