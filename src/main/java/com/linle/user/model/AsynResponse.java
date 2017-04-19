package com.linle.user.model;
/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月26日
 **/
public class AsynResponse {
	private boolean valid = true;
	
	public AsynResponse(boolean valid){
		this.valid = valid;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}


	
}
