package com.linle.cSupport.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.CSupport;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.v1.request.SupportOperateRequest;

public interface CSupportService extends BaseService<CSupport> {
	
	public boolean supportOperate(SupportOperateRequest req,Users user);
	
	//验证是否点赞过
	public int selectByTopicIdAndUserid(SupportOperateRequest req,Users user);

	public List<CSupport> selectSupportUsers(long topicId);
}
