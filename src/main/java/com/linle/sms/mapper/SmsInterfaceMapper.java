package com.linle.sms.mapper;

import com.linle.entity.SmsInterface;

import component.BaseMapper;

public interface SmsInterfaceMapper extends BaseMapper<SmsInterface>{
   public SmsInterface getByDateId(Integer id);
}