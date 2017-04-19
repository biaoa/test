package com.linle.bindBankCard.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.BindBankCard;
import com.linle.mobileapi.v1.model.BankRes;

import component.BaseMapper;

public interface BindBankCardMapper extends BaseMapper<BindBankCard>{

	int deleteCardNo(Map<String, Object> map);

	List<BankRes> getCardList(Map<String, Object> map);

	BindBankCard getByCardNo(String cardNo);
}