package com.linle.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.linle.entity.sys.Users;
import com.linle.event.CreateOrderFeeEvent;
import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.sysOrder.service.SysOrderService;

@Component
public class CreateOrderFeeListener implements
		ApplicationListener<CreateOrderFeeEvent> {
	protected final Logger _logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysOrderService orderService;

	@Async
	@Override
	public void onApplicationEvent(CreateOrderFeeEvent event) {
		try {
			Users user =event.getUser();
			orderService.createFeeOrderAndDetailByRoomNo(user);
			_logger.info("用户注册时，异步执行了创建缴费订单方法!");
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("创建缴费订单出错了!");
		}
	}

}
