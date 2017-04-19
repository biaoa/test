package com.linle.withdrawBank.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.withdrawBank.model.WithdrawBank;

public interface WithdrawBankService extends BaseService<WithdrawBank> {
	
	/**
	 * 
	 * @Description 列表方法
	 * @param page
	 * @return Page<WithdrawBank>
	 * $
	 */
	Page<WithdrawBank> getAllData(Page<WithdrawBank> page);
	
	/**
	 * 
	 * @Description 根据uid查询银行卡列表
	 * @param uid
	 * @return List<WithdrawBank>
	 * $
	 */
	List<WithdrawBank> getBanksByUid(Long uid);

}
