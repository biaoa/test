//package com.linle.socket.msg;
//
//import java.util.Date;
//
//import com.linle.entity.sys.BaseDomain;
//
///**
// * 
// * @author pangd
// * @Description 消息对象
// * @date 2016年11月3日下午3:24:21
// */
//public class WebSocketMsg extends BaseDomain {
//
//	private static final long serialVersionUID = 550520903751108935L;
//	
//	private Long to;// 接收者
//
//	private Object obj;//数据
//	
//	private Long from;//发送者
//	
//	private Date sendDate; //发送时间
//
//	public Object getObj() {
//		return obj;
//	}
//
//	public void setObj(Object obj) {
//		this.obj = obj;
//	}
//
//	public Long getTo() {
//		return to;
//	}
//
//	public void setTo(Long to) {
//		this.to = to;
//	}
//
//	public Long getFrom() {
//		return from;
//	}
//
//	public void setFrom(Long from) {
//		this.from = from;
//	}
//
//	public Date getSendDate() {
//		return sendDate;
//	}
//
//	public void setSendDate(Date sendDate) {
//		this.sendDate = sendDate;
//	}
//}
