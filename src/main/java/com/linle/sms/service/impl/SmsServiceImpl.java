package com.linle.sms.service.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.util.SMSutil;
import com.linle.common.util.StringUtil;
import com.linle.entity.SmsInterface;
import com.linle.entity.SmsRecord;
import com.linle.entity.enumType.SmsValidateType;
import com.linle.sms.mapper.SmsInterfaceMapper;
import com.linle.sms.mapper.SmsRecordMapper;
import com.linle.sms.service.SmsService;
/**  
 * @Title: SmsServiceImpl.java
 * @Package com.linle.loanapproval.service
 * @author shangjing
 * @date 2015年8月26日下午2:41:29
 * @comment 短信发送接口ServieImpl
 */
@Service
@Transactional
public class SmsServiceImpl implements SmsService {
	public static final Logger _logger = LoggerFactory.getLogger(SmsServiceImpl.class);
	@Autowired
	private SmsInterfaceMapper interfaceMapper;
	
	@Autowired
	private SmsRecordMapper smsRecordMapper;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 获取不同的短信模板
	 */
	@Override
	public SmsInterface getSmsInterface(Integer type) {
		
		return interfaceMapper.selectByPrimaryKey(Long.valueOf(type));
//		return interfaceMapper.getByDateId(type);
		
	}
	
	/**
	 * 发送验证码短信
	 */
	@Override
	public String sendValidateSms(SmsValidateType type, String phone) {
		SmsInterface smsInterface=getSmsInterface(type.getIntValue());//获取通知短信模板
		String code = creatValidateCode();
		Map<String, String> smsData = getParsedSMSDataMap(phone, code,smsInterface);
		String repContent = SendAcion(smsInterface, smsData);
		//添加短信记录	
		saveSmsRecord(type.getIntValue(), phone, code, smsInterface.getContent(), new Date(), repContent);
		return repContent;
	}
	/**
	 * 发送通知短信
	 */
	@Override
	public String sendInfoSms(SmsInterface smsInterface, String phone, List<String> data) {
		Map<String, String> smsData = getParsedSMSDataMapFromParams(phone, data, smsInterface);
		String repContent = SendAcion(smsInterface, smsData);
		return repContent;
	}
	/**
	 * 保存发送短信记录
	 * @param type
	 * @param phone
	 * @param code
	 * @param content
	 */
	public void saveSmsRecord(Integer type, String phone, String code, String content,Date senddate,String result) {
		logger.info("类型为："+type+",发送的手机号："+phone+"当前验证码为："+code);
		SmsRecord smsRecord=new SmsRecord();
		smsRecord.setType(type);
		smsRecord.setPhone(phone);
		smsRecord.setCode(code);
		smsRecord.setContent(content.replace("?", code + ""));
		smsRecord.setResult(result);
		smsRecord.setStatus(0);
		smsRecord.setSenddate(senddate);
		smsRecordMapper.insertSelective(smsRecord);
	}
	
	/**
	 * 执行发送短信操作
	 * @param smsInterface
	 * @param smsData
	 * @return
	 */
	private String SendAcion(SmsInterface smsInterface, Map<String, String> smsData) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String repContent = null;
		try {
			HttpPost httpPost = new HttpPost(smsInterface.getAddress());
			/**
			 * 执行普通请求
			 */
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			for (Entry<String, String> entry : smsData.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			//nvps.add(new BasicNameValuePair("sendTime", new DateFormatManager("yyyy-MM-dd HH:mm:ss").format(new Date())));
			nvps.add(new BasicNameValuePair("action", "send"));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps,getDefaultCharset()));
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity httpEntity = response.getEntity();
				if (httpEntity != null) {
					repContent = EntityUtils.toString(httpEntity);
				}
				EntityUtils.consume(httpEntity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		return repContent;
	}
	
	private Map<String, String> getParsedSMSDataMapFromParams(String phone, List<String> params, SmsInterface smsInterface) {
		Map<String, String> map = new HashMap<String, String>();
		String[] keyValue = smsInterface.getParam().split("_");
		for (String string : keyValue) {
			String[] p = string.split("=");
			map.put(p[0].trim(), p[1].trim());
		}

		String content = smsInterface.getContent();
		for (String param : params) {
			content = content.replaceFirst("[?]", param);
		}

		map.put("mobile", phone);
		map.put("content", content);
		return map;
	}

	/**
	 * 短信的参数
	 * @param phone
	 * @param code
	 * @param smsInterface
	 * @return
	 */
	private Map<String, String> getParsedSMSDataMap(String phone, String code,SmsInterface smsInterface) {
		List<String> params = new ArrayList<>();
		params.add(code);
		
		return getParsedSMSDataMapFromParams(phone, params, smsInterface);
	}
	/**
	 * 短信的字符编码
	 * @return
	 */
	private static final Charset getDefaultCharset() {
		return Charset.forName("UTF-8");
	}

	/**
	 * 查询验证码的最近记录
	 */
	@Override
	public SmsRecord getSmsRecord(Integer type, String phone) {
		SmsRecord smsRecord=new SmsRecord();
		smsRecord.setType(type);
		smsRecord.setPhone(phone);
		return smsRecordMapper.getSmsRecord(smsRecord);
	}
	
	/**
	 * 生成六位验证码
	 * 
	 * @return
	 */
	private String creatValidateCode() {
		int max = 999999;
		int min = 0;
		Random random = new Random();
		int randomInt = random.nextInt(max) % (max - min + 1) + min;
		String vc = String.valueOf(randomInt);
		for (int i = 6 - vc.length(); i > 0; i--) {
			vc = "0" + vc;
		}
		return vc;
	}

	@Override
	public boolean sendCode(String smsFreeSignName, String param, String phone, String templteCode,String code,int type) {
		
		String result = SMSutil.sendMsg(smsFreeSignName,param,phone,templteCode);
		logger.info("用户："+phone+",短信发送结果:"+result+"");
		SmsRecord record = new SmsRecord();
		record.setType(type);
		record.setPhone(phone);
		record.setCode(code);
		record.setContent(param);
		record.setResult(result);
		record.setStatus(0);
		record.setSenddate(new Date());
		boolean insertRecode =  smsRecordMapper.insertSelective(record)>0;
		boolean sendStatus = result.indexOf("alibaba_aliqin_fc_sms_num_send_response")>0?true:false;
		return insertRecode&&sendStatus;
	}

	@Override
	public boolean verifyCode(String smsCode,String phone,Integer type) {
		if (!StringUtil.isNotNull(smsCode) || !StringUtil.isNotNull(phone)) {
			return false;
		}
		SmsRecord recode = new SmsRecord();
		recode.setType(type);
		recode.setPhone(phone);
		recode.setCode(smsCode);
		recode = smsRecordMapper.getSmsRecord(recode);
		if (recode==null) {
			return false;
		}
		return true;
	}

}
