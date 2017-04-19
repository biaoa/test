package com.linle.common.event;

public enum EventType {
    LOGIN("登陆"), LOGOUT("退出"), LOGIN_SUC("登陆成功");

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    EventType(String description) {
        this.description = description;
    }
}
