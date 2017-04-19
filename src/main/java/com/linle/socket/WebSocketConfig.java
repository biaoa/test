package com.linle.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry; 
/**
 * 
 * @author pangd
 * @Description WebScoket配置处理器
 * @date 2016年11月3日下午3:06:48
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(systemWebSocketHandler(), "/webSocketServer")
				.addInterceptors(new WebSocketHandshakeInterceptor());

		registry.addHandler(systemWebSocketHandler(), "/sockjs/webSocketServer")
				.addInterceptors(new WebSocketHandshakeInterceptor()).withSockJS();
	}
	
	@Bean
    public WebSocketHandler systemWebSocketHandler(){
        return new SystemWebSocketHandler();
    }

}
