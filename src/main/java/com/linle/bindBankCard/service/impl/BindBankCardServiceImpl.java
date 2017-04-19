package com.linle.bindBankCard.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.bindBankCard.mapper.BindBankCardMapper;
import com.linle.bindBankCard.service.BindBankCardService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.entity.sys.BindBankCard;
import com.linle.mobileapi.v1.model.BankRes;


@Service
@Transactional
public class BindBankCardServiceImpl extends CommonServiceAdpter<BindBankCard> implements BindBankCardService {

	@Autowired
	private BindBankCardMapper mapper;
	
	@Override
	public boolean deleteCardNo(Map<String, Object> map) {
		return mapper.deleteCardNo(map)>0;
	}

	@Override
	public List<BankRes> getCardList(Map<String, Object> map) {
		return mapper.getCardList(map);
	}

	@Override
	public BindBankCard getByCardNo(String cardNo) {
		return mapper.getByCardNo(cardNo);
	}


}
