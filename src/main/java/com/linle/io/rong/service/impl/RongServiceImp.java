package com.linle.io.rong.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.util.JsonUtil;
import com.linle.entity.sys.Users;
import com.linle.io.rong.ApiHttpClient;
import com.linle.io.rong.models.FormatType;
import com.linle.io.rong.models.GetTokenResult;
import com.linle.io.rong.models.SdkHttpResult;
import com.linle.io.rong.models.TxtMessage;
import com.linle.io.rong.service.RongService;
import com.linle.user.service.UserInfoService;

@Service
@Transactional
public class RongServiceImp implements RongService {

	@Value("${rong_appKey}")
	private String appKey;

	@Value("${rong_appSecret}")
	private String appSecret;
	
    final Logger _logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 获得token并将token赋值user表
	 */
	@Override
	public GetTokenResult getUserRongToken(Users user) {
		GetTokenResult result = new GetTokenResult();
		try {
			String logo = "/resources/images/default_user.png";
			if (user.getLogo() != null && !"".equals(user.getLogo())) {
				logo = user.getLogo();
			}
			SdkHttpResult Rongresult = ApiHttpClient.getToken(appKey, appSecret, user.getId().toString(),
					user.getName(), logo, FormatType.json);
			if (Rongresult.getHttpCode() == 200) {
				result = JsonUtil.readValue(Rongresult.getResult(), GetTokenResult.class);
				_logger.info("获得融云token的结果是:"+result);
				user.setToken(result.getToken());
				userInfoService.updateUserToken(user);
			}
		} catch (Exception e) {
			System.out.println("修改融云token出错！");
			e.printStackTrace(); _logger.error("出错了", e);
		}
		return result;
	}

	@Override
	public String sendMessage(Long sendId, List<String> toUserIds, TxtMessage message) {
		SdkHttpResult result =null;
		try {
			result = ApiHttpClient.publishMessage(appKey, appSecret, sendId.toString(), toUserIds, message, FormatType.json);
			_logger.info("发送消息的结果是:"+result.toString());
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
	 	}
		
		return "";
	}

	@Override
	public String sendMessageToOne(Long sendId, Long toUsers, TxtMessage message) {
		SdkHttpResult result =null;
		try {
			List<String> ids = new ArrayList<>();
			ids.add(toUsers.toString());
			result = ApiHttpClient.publishMessage(appKey, appSecret, sendId.toString(), ids, message, FormatType.json);
			_logger.info("发送消息的结果是:"+result.toString());
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
	 	}
		
		return "";
	}

}
