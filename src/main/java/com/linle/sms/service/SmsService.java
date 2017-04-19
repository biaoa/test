package com.linle.sms.service;

import java.util.Date;
import java.util.List;

import com.linle.entity.SmsInterface;
import com.linle.entity.SmsRecord;
import com.linle.entity.enumType.SmsValidateType;

/**  
 * @Title: SmsService.java
 * @Package com.linle.loanapproval.service
 * @author shangjing
 * @date 2015年8月26日下午2:40:56
 * @comment 短信发送接口Servie
 */
public interface SmsService {

	/**
	 * 获取不同的短信模板
	 * @param type
	 * @return
	 */
	public SmsInterface getSmsInterface(Integer type);
	/**
	 * 发送验证码短信
	 */
	public String sendValidateSms(SmsValidateType type, String phone);
	/**
	 * 发送通知短信
	 * @param smsInterface
	 * @param phone
	 * @param data
	 * @return
	 */
	public String sendInfoSms(SmsInterface smsInterface, String phone, List<String> data);
	
	/**
	 * 保存发送短信记录
	 * @param type
	 * @param phone
	 * @param code
	 * @param content
	 */
	public void saveSmsRecord(Integer type, String phone, String code, String content,Date senddate,String result);
	/**
	 * 查询验证码的最近记录
	 * @param type
	 * @param phone
	 * @return
	 */
	public SmsRecord getSmsRecord(Integer type,String phone);
	//发送验证码
	public boolean sendCode(String smsFreeSignName, String param, String phone, String templteCode,String code,int type);
	//判断验证码是否正确
	public boolean verifyCode(String smsCode,String phone,Integer type);
}
