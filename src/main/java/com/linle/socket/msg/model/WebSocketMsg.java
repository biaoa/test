package com.linle.socket.msg.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linle.entity.sys.BaseDomain;

public class WebSocketMsg extends BaseDomain{

    private static final long serialVersionUID = 2318935461612131603L;

	private Long from; //发送者

    private Long to; //接收者(这里的to是存数据库的)

    private Date sendDate; //发送时间

    private String obj;//数据
    
    private int sendStatus; //发送状态
    
    private int readStatus; //阅读状态
    
    @JsonIgnore
    private List<Long> tos;//这里是多个接收者的逻辑
    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

	public int getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public int getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(int readStatus) {
		this.readStatus = readStatus;
	}

	public List<Long> getTos() {
		return tos;
	}

	public void setTos(List<Long> tos) {
		this.tos = tos;
	}
}