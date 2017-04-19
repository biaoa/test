package com.linle.withdraw.service;

import java.math.BigDecimal;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.Withdraw;

public interface WithdrawService extends BaseService<Withdraw> {
	
	public BigDecimal sumWithdrawMoney(Long uid,String orderType);

	public Page<Withdraw> findAllWithdraw(Page<Withdraw> page);
}
