package com.linle.pay.service;

import java.util.Map;

import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Refund;

public interface PayService {
	//创建支付对象
	Charge createCharge(Map<String, Object> payParams);
	
	//退款
	Refund refund(Charge charge,String description);
	
	//查询支付情况
	Charge retrieve(String chargeId);

}
