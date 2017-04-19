package com.linle.refundRecord.service;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.RefundRecord;
import com.linle.mobileapi.base.BaseResponse;

public interface RefundRecordService extends BaseService<RefundRecord> {

	Page<RefundRecord> selectRefundOrderList(Page<RefundRecord> page);
	
	//退款链接失效，重新退款
	public BaseResponse resetRefund(String orderNo);

	//确认退款
	BaseResponse confirmRefund(String orderNo);

}
