package com.linle.withdraw.mapper;

import java.math.BigDecimal;
import java.util.Map;

import com.linle.entity.sys.Withdraw;

import component.BaseMapper;

public interface WithdrawMapper extends BaseMapper<Withdraw>{

	BigDecimal sumWithdrawMoney(Map<String, Object> map);
}