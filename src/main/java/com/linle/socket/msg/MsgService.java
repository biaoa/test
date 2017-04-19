package com.linle.socket.msg;

import java.util.List;

import com.linle.socket.msg.model.WebSocketMsg;

public interface MsgService {

	public void add(String key,WebSocketMsg webMsg);

	public List<WebSocketMsg> getMsgs(String key);
	
	public WebSocketMsg getMsg(String key);
	
	public void del(String key,WebSocketMsg webMsg);
	

}
