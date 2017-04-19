package com.linle.mobileapi.v1.model;

public class ExpressQueryResult {
	private String status;

	private String msg;
	
	private ExpressQueryRES result;
	
	public  ExpressQueryResult(){
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ExpressQueryRES getResult() {
		return result;
	}

	public void setResult(ExpressQueryRES result) {
		this.result = result;
	}
}
