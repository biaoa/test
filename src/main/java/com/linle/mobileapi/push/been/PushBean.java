package com.linle.mobileapi.push.been;

import java.io.Serializable;

/**
 * @描述:推送been
 * @作者:杨立忠
 * @创建时间：2015年10月9日
 **/
public class PushBean implements Serializable {

    /**
     * 推送bean
     */
    private static final long serialVersionUID = 1L;
    private PushType type;
    private String refId;
    private Object content;
    private String jsonStr;


	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public PushType getType() {
        return type;
    }

    public void setType(PushType type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

}
