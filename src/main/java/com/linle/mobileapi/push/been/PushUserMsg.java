package com.linle.mobileapi.push.been;

import java.io.Serializable;

/**
 * @描述:系统消息数量
 * @作者:杨立忠
 * @创建时间：2015年10月10日
 **/
public class PushUserMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int remain;

	public int getRemain() {
		return remain;
	}

	public void setRemain(int remain) {
		this.remain = remain;
	}
	
}
