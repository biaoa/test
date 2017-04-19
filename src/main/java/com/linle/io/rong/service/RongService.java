package com.linle.io.rong.service;

import java.util.List;

import com.linle.entity.sys.Users;
import com.linle.io.rong.models.GetTokenResult;
import com.linle.io.rong.models.TxtMessage;

public interface RongService {
	
	/**
	 * 
	 * @Description 获得用户融云TOken，并将token赋值user表
	 * @param user
	 * @return boolean
	 * $
	 */
	GetTokenResult getUserRongToken(Users user);
	
	/**
	 * 
	 * @Description 融云发送消息
	 * @param sendId 发送人Id
	 * @param toUserIds 接收人列表
	 * @param message 消息
	 * @return String 返回json格式的消息
	 * $
	 */
	String sendMessage(Long sendId,List<String> toUserIds,TxtMessage message);
	
	
	String sendMessageToOne(Long sendId,Long toUsers,TxtMessage message);

}
