package com.linle.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.socket.msg.BaseWebSocketMsgDataVo;
import com.linle.socket.msg.MsgService;
import com.linle.socket.msg.model.WebSocketMsg;
import com.linle.socket.msg.service.WebSocketMsgService;
import com.linle.sysOrder.service.SysOrderService;
import com.linle.system.service.RedisManager;

/**
 * 
 * @author pangd
 * @Description Socket处理器
 * @date 2016年11月3日下午3:07:11
 */
@Component
public class SystemWebSocketHandler implements WebSocketHandler {

	protected final static Logger logger;

	private static final Map<WebSocketSession, String> usersMap;

	static {
		usersMap = new HashMap<>();
		logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);
	}

	@Autowired
	private RedisManager redisManager;

	@Autowired
	private MsgService msgService;

	@Autowired
	private SysOrderService orderService;
	
	@Autowired
	private WebSocketMsgService webMsgService;

	public ObjectMapper m = new ObjectMapper();

	/**
	 * 建立连接后
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		logger.debug("connect to the websocket success......");
		String userId = (String) session.getAttributes().get(Constants.WEBSOCKET_USERID);
		usersMap.put(session, userId);
		if (userId != null) {
			// 查询用户最后一条未读消息
			 WebSocketMsg msg = webMsgService.selectLastReadMsg(Long.valueOf(userId));
			if(msg!=null){
				//发送消息
				 msg = sendMessageToUser(msg);
				if (msg != null) {
					msgService.del(msg.getTo().toString(), msg);// redis 中删除
					msg.setSendStatus(1);
					webMsgService.modifySendStatus(msg);
				}
			}
		}
	}

	/**
	 * 关闭连接后
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		logger.debug("websocket connection closed......");
		usersMap.remove(session);
		logger.info("断开连接错误原因是:" + m.writeValueAsString(closeStatus));
	}

	/**
	 * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// 客户端发来的消息在这里
		if (message.getPayloadLength() == 0) {
			return;
		}

	}

	/**
	 * 消息传输错误处理
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		logger.info("websocket connection closed......");
		usersMap.remove(session);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 *
	 * @param message
	 *            WebSocketMsg
	 * @throws IOException
	 */
	public WebSocketMsg sendMessageToUsers(WebSocketMsg msg) {
		TextMessage message = new TextMessage(msg.getObj().toString());
		Iterator<Map.Entry<WebSocketSession, String>> it = usersMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<WebSocketSession, String> entry = it.next();
			if (entry.getKey().isOpen()) {
				try {
					entry.getKey().sendMessage(message);
					logger.info("群发消息成功:key=" + entry.getValue() + "value=" + msg);
				} catch (Exception e) {
					logger.error("给用户:" + msg.getTo() + "发送消息出错:" + e);
					return null;
				}
				return msg;
			}
		}
		return null;
	}

	/**
	 * 给某个用户发送消息
	 *
	 * @param userName
	 * @param message
	 * @throws IOException
	 * @throws Exception
	 */
	public WebSocketMsg sendMessageToUser(WebSocketMsg vo) {
		try {
			BaseWebSocketMsgDataVo msgDate = m.readValue(vo.getObj(), BaseWebSocketMsgDataVo.class);
			msgDate.setMsgId(vo.getId());
			vo.setObj(m.writeValueAsString(msgDate));
			TextMessage message = new TextMessage(vo.getObj().toString());
			Iterator<Map.Entry<WebSocketSession, String>> it = usersMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<WebSocketSession, String> entry = it.next();
				if (vo.getTo().toString().equals(entry.getValue())) {
					if (entry.getKey().isOpen()) {
						try {
							entry.getKey().sendMessage(message);
						} catch (Exception e) {
							logger.error("给用户:" + vo.getTo() + "发送消息出错:" + e);
							return null;
						}
						logger.info("单独发送成功:key=" + entry.getValue() + "value=" + vo.getObj());
						msgDate.setMsgId(null);
						vo.setObj(m.writeValueAsString(msgDate));
						return vo;
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	// 根据值移除map中某个key
	public static Map<WebSocketSession, String> remove(Map<WebSocketSession, String> map, String value) {
		Iterator<Map.Entry<WebSocketSession, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<WebSocketSession, String> entry = it.next();
			if (value.equals(entry.getValue())) {
				it.remove();
			}
		}
		return map;
	}

}
