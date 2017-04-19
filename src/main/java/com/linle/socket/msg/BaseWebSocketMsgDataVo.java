package com.linle.socket.msg;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author pangd
 * @Description PC端通知的基本类
 * @date 2016年11月7日下午3:55:06
 */
public class BaseWebSocketMsgDataVo implements Serializable {
	
	private Long msgId;
	
	private static final long serialVersionUID = 4431011194409868062L;

	private String tilte; // 如 缴费通知,投诉建议等

	private String body; // 消息的描述

	private String url; // 消息点击跳转的链接
	
	private String jsonStr; //发送的json数据
	
	private int readStatus;
	
	private Date createDate; //创建时间
	public String getTilte() {
		return tilte;
	}

	public void setTilte(String tilte) {
		this.tilte = tilte;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public int getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(int readStatus) {
		this.readStatus = readStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
