package com.linle.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.util.Page;
import com.linle.entity.enumType.MessageStatus;
import com.linle.entity.sys.UserMessage;
import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.base.BaseAPIunit;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.mobileapi.push.been.PushUserMsg;
import com.linle.user.mapper.UserMessageMapper;
import com.linle.user.service.UserMessageService;

/**
 * @描述:
 * @作者:杨立忠 @创建时间：2015年9月8日
 **/
@Service("userMessageService")
@Transactional
public class UserMessageServiceImpl implements UserMessageService {
	protected final static Logger _logger = LoggerFactory.getLogger(UserMessageServiceImpl.class);
	@Autowired
	private UserMessageMapper userMessageMapper;
	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public boolean addUserMessage(UserMessage userMessage) {
		try {
			boolean flg = userMessageMapper.insertSelective(userMessage) > 0;
			userMessage=userMessageMapper.selectByPrimaryKey(userMessage.getId());
			if(userMessage.getUser()!=null){
				PushBean pushBean = new PushBean();//消息推送		
				pushBean.setType(PushType.TOPIC_MSG);
				String alias = "alias"+userMessage.getUser().getId();
				PushUserMsg content = new PushUserMsg();
				int noContent = userMessageMapper.remainCount(userMessage.getUser().getId(), MessageStatus.unreceive);
				content.setRemain(noContent);
				pushBean.setContent(content);
				applicationContext.publishEvent(new PushMessageEvent(pushBean, "", alias,PushFrom.LINLE_USER));
			}
			return flg;
		} catch (Exception e) {
			_logger.error("新增消息出错了", e);
			return false;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	public Page<UserMessage> findPage(Page<UserMessage> page) {
		List<UserMessage> list = userMessageMapper.getAllData(page);
		page.setResults(list);
		return page;
	}

	@Override
	public boolean updateUserMessage(UserMessage userMessage) {
		return userMessageMapper.updateByPrimaryKey(userMessage) > 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<UserMessage> findUserMessageByPageNo(Long userId,int pageNo, int size) {
		
		Map<String, Object> parame = new HashMap<String, Object>();
		parame.put("begin", BaseAPIunit.limit(size, pageNo).get("begin"));
		parame.put("end", BaseAPIunit.limit(size, pageNo).get("end"));
		
		List<UserMessage> list = userMessageMapper.findByPageNo(userId, (int)BaseAPIunit.limit(size, pageNo).get("begin"),(int)BaseAPIunit.limit(size, pageNo).get("end"));
//		List<UserMessage> list = userMessageMapper.findByMaxId(userId, maxId, size);
		/*if (list != null && list.size() > 0) {
			for (UserMessage message : list) {
				message.setMessageStatus(MessageStatus.receive);
				updateUserMessage(message);
			}
		}*/
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	public int countRemainder(Long userId, Long lastId) {
		return userMessageMapper.countRemainder(lastId, userId);
	}


	@Override
	public boolean updateOwnerId(Long ownerId, Long id) {
		return userMessageMapper.updateOwnerId(ownerId, id) > 0;
	}

	@Override
	public int remainCount(Long userId, MessageStatus messageStatus) {
		return userMessageMapper.remainCount(userId, messageStatus);
	}

	@Override
	public boolean readMessage(Long msgId) {
		UserMessage message = new UserMessage();
		message.setId(msgId);
		message.setMessageStatus(MessageStatus.receive);
		return updateUserMessage(message);
	}

}
