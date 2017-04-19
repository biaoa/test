package com.linle.user.service;

import java.util.List;

import com.linle.common.util.Page;
import com.linle.entity.enumType.MessageStatus;
import com.linle.entity.sys.UserMessage;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年9月8日
 **/
public interface UserMessageService {
	/**
	 * 新增
	 * @param userMessage
	 * @return
	 */
	public boolean addUserMessage(UserMessage userMessage);
	/**
	 * 修改
	 * @param userMessage
	 * @return
	 */
	public boolean updateUserMessage(UserMessage userMessage);
	/**
	 * 
	 * @param page
	 * @return
	 */
	public Page<UserMessage> findPage(Page<UserMessage> page);
	/**
	 * 获得我得消息列表
	 * @param userId
	 * @param maxId
	 * @param size
	 * @return
	 */
	public List<UserMessage> findUserMessageByPageNo(Long userId,int pageNo,int size);

	/**
	 * 剩余数量
	 * 
	 * @param UserId
	 * @param lastId
	 * @return
	 */
	public int countRemainder(Long userId, Long lastId);

	/**
	 * 根据消息类型和关联Id获取消息信息
	 * @param owenrId
	 * @param businessType
	 * @return
	 */
	public boolean updateOwnerId(Long ownerId,Long id);
	
	public int remainCount(Long userId,MessageStatus messageStatus);
	public boolean readMessage(Long msgId);

}
