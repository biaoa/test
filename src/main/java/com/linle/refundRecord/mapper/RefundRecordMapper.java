package com.linle.refundRecord.mapper;

import com.linle.entity.sys.RefundRecord;

import component.BaseMapper;

public interface RefundRecordMapper extends BaseMapper<RefundRecord> {
	
	RefundRecord selectByOrderNo(String orderNo);
	
}