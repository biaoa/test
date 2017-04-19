package com.linle.common.event;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationEvent;

import com.linle.entity.sys.Users;




public class CommonEventModel {

    private EventType eventType;

    private Long userId;

    private String clientIp;

    private CommonEventModel(EventType eventType) {
        Subject subject = SecurityUtils.getSubject();
        this.eventType = eventType;
        this.userId = ((Users) subject.getPrincipal()).getId();
        this.clientIp = subject.getSession().getHost();
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public static ApplicationEvent LOGIN_EVENT() {
        return new CommonEvent(new CommonEventModel(EventType.LOGIN_SUC));
    }

}
