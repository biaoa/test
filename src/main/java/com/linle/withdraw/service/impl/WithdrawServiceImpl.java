package com.linle.withdraw.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.Withdraw;
import com.linle.withdraw.mapper.WithdrawMapper;
import com.linle.withdraw.service.WithdrawService;

@Service
@Transactional
public class WithdrawServiceImpl extends CommonServiceAdpter<Withdraw> implements WithdrawService {
	
	@Autowired
	private WithdrawMapper mapper;

	@Override
	public BigDecimal sumWithdrawMoney(Long uid,String orderType) {
		Map<String, Object> map = new HashMap<>();
		map.put("uid", uid);
		map.put("orderType", orderType);
		return mapper.sumWithdrawMoney(map);
	}

	@Override
	public Page<Withdraw> findAllWithdraw(Page<Withdraw> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}
}
