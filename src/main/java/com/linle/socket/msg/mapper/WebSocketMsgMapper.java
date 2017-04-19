package com.linle.socket.msg.mapper;

import java.util.List;
import java.util.Map;

import com.linle.socket.msg.model.WebSocketMsg;

import component.BaseMapper;

public interface WebSocketMsgMapper extends BaseMapper<WebSocketMsg> {

	int modifySendStatus(WebSocketMsg msg);

	int modifyReadStatus(WebSocketMsg msg);

	List<WebSocketMsg> getMsgList(Map<String, Object> map);

	int getMsgListCount(Map<String, Object> map);

	WebSocketMsg selectLastReadMsg(Long userId);

}