package com.linle.socket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.linle.entity.sys.Users;

@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

	@Override
	public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
		
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
			Map<String, Object> attributes) throws Exception {
		 if (request instanceof ServletServerHttpRequest) {
	            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
	            HttpSession session = servletRequest.getServletRequest().getSession(false);
	            if (session != null) {
	                //使用userName区分WebSocketHandler，以便定向发送消息
	            	Subject subject = SecurityUtils.getSubject();
	            	Users userInfo = (Users) subject.getSession().getAttribute("cUser");
	                String uid = userInfo.getId().toString();
	                attributes.put(Constants.WEBSOCKET_USERID,uid);
	            }
	        }
	        return true;
	}

}
