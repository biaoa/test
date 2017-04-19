package com.linle.withdrawBank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.withdrawBank.mapper.WithdrawBankMapper;
import com.linle.withdrawBank.model.WithdrawBank;
import com.linle.withdrawBank.service.WithdrawBankService;

@Service
@Transactional
public class WithdrawBankServiceImpl extends CommonServiceAdpter<WithdrawBank> implements WithdrawBankService {

	@Autowired
	private WithdrawBankMapper mapper;

	@Override
	public Page<WithdrawBank> getAllData(Page<WithdrawBank> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<WithdrawBank> getBanksByUid(Long uid) {
		return mapper.getBanksByUid(uid);
	}
}
