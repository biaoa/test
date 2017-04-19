package com.linle.socket.msg.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.socket.msg.BaseWebSocketMsgDataVo;
import com.linle.socket.msg.mapper.WebSocketMsgMapper;
import com.linle.socket.msg.model.WebSocketMsg;
import com.linle.socket.msg.service.WebSocketMsgService;

@Service
@Transactional
public class WebSocketMsgServiceImpl extends CommonServiceAdpter<WebSocketMsg> implements WebSocketMsgService {

	@Autowired
	private WebSocketMsgMapper mapper;

	@Override
	public boolean modifySendStatus(WebSocketMsg msg) {
		return mapper.modifySendStatus(msg) > 0;
	}

	@Override
	public boolean modifyReadStatus(WebSocketMsg msg) {
		return mapper.modifyReadStatus(msg) > 0;
	}

	@Override
	public List<BaseWebSocketMsgDataVo> getMsgList(Map<String, Object> map) {
		List<WebSocketMsg> list = mapper.getMsgList(map);
		List<BaseWebSocketMsgDataVo> msglist = new ArrayList<>();
		for (WebSocketMsg webSocketMsg : list) {
			try {
				BaseWebSocketMsgDataVo msg = m.readValue(webSocketMsg.getObj(), BaseWebSocketMsgDataVo.class);
				msg.setReadStatus(webSocketMsg.getReadStatus());
				msg.setMsgId(webSocketMsg.getId());
				msg.setCreateDate(webSocketMsg.getCreateDate());
				msglist.add(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return msglist;
	}

	@Override
	public int getMsgListCount(Map<String, Object> map) {
		return mapper.getMsgListCount(map);
	}

	@Override
	public WebSocketMsg selectLastReadMsg(Long userId) {
		return mapper.selectLastReadMsg(userId);
	}

}
