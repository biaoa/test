package com.linle.IM.res;

import java.io.Serializable;
import java.util.List;

import com.linle.user.model.CommunityUserIM;

public class CommunityUserListResponse implements Serializable{
	private static final long serialVersionUID = -8623410149504026755L;
	private List<CommunityUserIM> userlist;

	public List<CommunityUserIM> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<CommunityUserIM> userlist) {
		this.userlist = userlist;
	}
}
