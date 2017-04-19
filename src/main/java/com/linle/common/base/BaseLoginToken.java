package com.linle.common.base;

import org.apache.shiro.authc.UsernamePasswordToken;


public class BaseLoginToken extends UsernamePasswordToken {
    public BaseLoginToken() {
    }

    public BaseLoginToken(String userName, String password) {
        super(userName, password);
    }

    public void setPassword(String password) {
        super.setPassword(password.toCharArray());
    }

}
