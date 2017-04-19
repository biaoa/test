package com.linle.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 异步返回
 *
 * @author linle
 */
public class ResponseMsg {
    /**
     * 返回状态
     **/
    private int code;/**0成功，非0失败**/
    /**
     * 返回信息
     **/
    private String msg;
    /**
     * 返回参数
     **/
    private Map<String, Object> paramsMap = new HashMap<String, Object>();

    public ResponseMsg(int code, String msg, Map<String, Object> paramsMap) {
        this.code = code;
        this.msg = msg;
        this.paramsMap = paramsMap;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
    }


}
