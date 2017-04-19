package com.linle.sms.mapper;

import com.linle.entity.SmsRecord;

import component.BaseMapper;

public interface SmsRecordMapper extends BaseMapper<SmsRecord>{
   
	public SmsRecord getSmsRecord(SmsRecord smsRecord);
}