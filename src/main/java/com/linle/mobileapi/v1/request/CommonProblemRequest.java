package com.linle.mobileapi.v1.request;

import org.hibernate.validator.constraints.NotEmpty;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author chnekai
 * @Description 常见问题
 * 
 */
public class CommonProblemRequest extends BaseRequest {

	private static final long serialVersionUID = -7623701878449765694L;
	
	@NotEmpty(message="belongTo字段不能为空")
	private int belongTo;

	public int getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(int belongTo) {
		this.belongTo = belongTo;
	}
	

}
