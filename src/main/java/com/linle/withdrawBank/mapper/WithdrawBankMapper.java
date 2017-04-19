package com.linle.withdrawBank.mapper;

import java.util.List;

import com.linle.withdrawBank.model.WithdrawBank;

import component.BaseMapper;

public interface WithdrawBankMapper extends BaseMapper<WithdrawBank>{

	List<WithdrawBank> getBanksByUid(Long uid);
  
}