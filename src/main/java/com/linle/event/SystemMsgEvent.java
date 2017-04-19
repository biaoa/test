package com.linle.event;

import org.springframework.context.ApplicationEvent;

import com.linle.socket.msg.model.WebSocketMsg;

/**
 * 
 * @author pangd
 * @Description 后台发给物业/商家的通知
 * @date 2016年11月9日下午5:23:57
 */
public class SystemMsgEvent extends ApplicationEvent {

	private static final long serialVersionUID = -1414343984088469505L;

	private WebSocketMsg webMsg;

	public SystemMsgEvent(String key, WebSocketMsg webMsg) {
		super(key);// redis 中的key
		this.webMsg = webMsg;
	}

	public WebSocketMsg getWebMsg() {
		return webMsg;
	}

	public void setWebMsg(WebSocketMsg webMsg) {
		this.webMsg = webMsg;
	}
}
