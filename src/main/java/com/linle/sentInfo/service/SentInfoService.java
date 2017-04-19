package com.linle.sentInfo.service;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.SentInfo;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.SentRequest;

public interface SentInfoService extends BaseService<SentInfo> {

	BaseResponse createSentOrder(SentRequest req);

	Page<SentInfo> getAllsentOrder(Page<SentInfo> page);
	
	boolean updateStatus(SentInfo sent);
	
	/**
	 * 
	 * @Description 用户取消订单
	 * @param orderNo
	 * @return boolean
	 * $
	 */
	boolean cancelSent(String sentId);

}
