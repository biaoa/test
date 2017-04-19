package com.linle.socket.msg.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.socket.msg.BaseWebSocketMsgDataVo;
import com.linle.socket.msg.model.WebSocketMsg;

public interface WebSocketMsgService extends BaseService<WebSocketMsg> {

	/**
	 * 
	 * @Description 更改发送状态
	 * @param msg
	 * @return boolean $
	 */
	public boolean modifySendStatus(WebSocketMsg msg);

	/**
	 * 
	 * @Description 更改阅读状态
	 * @param msg
	 * @return boolean $
	 */
	public boolean modifyReadStatus(WebSocketMsg msg);

	/**
	 * 
	 * @Description 获得消息列表
	 * @param map
	 * @return List<BaseWebSocketMsgDataVo> $
	 */
	public List<BaseWebSocketMsgDataVo> getMsgList(Map<String, Object> map);

	/**
	 * 
	 * @Description 获得消息列表总数
	 * @param map
	 * @return int $
	 */
	public int getMsgListCount(Map<String, Object> map);
	/**
	 * 
	 * @Description  根据用户ID 查询最后一条未读消息
	 * @param userId
	 * @return WebSocketMsg
	 * $
	 */
	public WebSocketMsg selectLastReadMsg(Long userId);

}
