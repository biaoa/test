package com.linle.socket;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.DateUtil;
import com.linle.event.SystemMsgEvent;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.socket.msg.model.WebSocketMsg;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {
	

	@RequestMapping("/send")
	@ResponseBody
	public BaseResponse auditing(HttpServletRequest request) {
		// 无关代码都省略了
//		systemWebSocketHandler().sendMessageToUsers(new TextMessage("测试消息-群发"));
//		WebSocketMsg msg = new WebSocketMsg();
//		msg.setFrom((long)1);
//		msg.setTo((long)4303);
//		msg.setObj("我是一条测试信息:4303"+DateUtil.getCurrentDateTime());
//		msg.setSendDate(new Date());
//		applicationContext.publishEvent(new SystemMsgEvent(msg.getTo().toString(),msg));
		return BaseResponse.OperateSuccess;
	}
	
	@RequestMapping("/send1")
	@ResponseBody
	public BaseResponse auditing1(HttpServletRequest request) {
		// 无关代码都省略了
//		String userName = "桂花城";
//		systemWebSocketHandler().sendMessageToUser(userName,new TextMessage("测试消息-单独"));
//		String name ="万科未来城";
//		systemWebSocketHandler().sendMessageToUser(name,new TextMessage("测试消息-单独-万科未来城"));
		
		return BaseResponse.OperateSuccess;
	}
	
	@RequestMapping("/join")
	public String join(){
		return "system/02-responsive";
	}
	
	public static void main(String[] args) {
		
	
		
	}

}
