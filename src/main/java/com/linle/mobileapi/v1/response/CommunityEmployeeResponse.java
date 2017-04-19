package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.CommunityEmployeeVO;

public class CommunityEmployeeResponse extends BaseResponse {

	private static final long serialVersionUID = 5245686552450871055L;
	
	private List<CommunityEmployeeVO> leaderList;
	
	private List<CommunityEmployeeVO> employeeList;

	public List<CommunityEmployeeVO> getLeaderList() {
		return leaderList;
	}

	public void setLeaderList(List<CommunityEmployeeVO> leaderList) {
		this.leaderList = leaderList;
	}

	public List<CommunityEmployeeVO> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<CommunityEmployeeVO> employeeList) {
		this.employeeList = employeeList;
	}
}
