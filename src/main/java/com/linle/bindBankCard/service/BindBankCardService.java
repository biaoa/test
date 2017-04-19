package com.linle.bindBankCard.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.BindBankCard;
import com.linle.mobileapi.v1.model.BankRes;

public interface BindBankCardService extends BaseService<BindBankCard> {

	boolean deleteCardNo(Map<String, Object> map);

	List<BankRes> getCardList(Map<String, Object> map);
	
	BindBankCard getByCardNo(String cardNo);

}
