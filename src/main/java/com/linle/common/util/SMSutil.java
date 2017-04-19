package com.linle.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SMSutil {
	public static final Logger _logger = LoggerFactory.getLogger(SMSutil.class);
	private static final String APP_KEY="23323945";
	
	private static final String URL="http://gw.api.taobao.com/router/rest";
	
	private static final String SECRET="a1ecc79c4acfd02fd0be6164164ff907";
	
	
	public static String  sendMsg(String smsFreeSignName,String Param,String phone,String templateCode){
		TaobaoClient client = new DefaultTaobaoClient(URL, APP_KEY, SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(smsFreeSignName);
		req.setSmsParamString(Param);
		req.setRecNum(phone);
		req.setSmsTemplateCode(templateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp =null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		return rsp.getBody();
	}
	
}
