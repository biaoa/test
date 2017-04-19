package com.linle.communityNotice.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.communityNotice.mapper.CommunityNoticeMapper;
import com.linle.communityNotice.service.CommunityNoticeService;
import com.linle.entity.sys.CommunityNotice;
import com.linle.event.PushMessageEvent;
import com.linle.event.RongMessageEvent;
import com.linle.io.rong.models.TxtMessage;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.mobileapi.v1.model.CommunityNoticeVO;
import com.linle.user.service.UserInfoService;

@Service
@Transactional
public class CommunityNoticeServiceImpl extends CommonServiceAdpter<CommunityNotice> implements CommunityNoticeService {
	
	@Autowired
	private CommunityNoticeMapper mapper;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Override
	public Page<CommunityNotice> getAllCommunityNotice(Page<CommunityNotice> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}
	
	@Override
	public CommunityNotice selectByIdForAPI(Map<String, Object> map) {
		return mapper.selectByIdForAPI(map);
	}
	
	@Override
	public List<CommunityNoticeVO> getAllForAPI(Map<String, Object> map) {
		return mapper.getAllForAPI(map);
	}
	
	@Override
	public List<CommunityNoticeVO> getAllPublicForAPI(Map<String, Object> map) {
		return mapper.getAllPublicForAPI(map);
	}
	
	@Override
	public BaseResponse send(Long noticeId,Community community ) {
		CommunityNotice communityNotice = mapper.selectByPrimaryKey(noticeId);
		String msg = communityNotice.getTitle();
		List<String> toUserList = userInfoService.getUserIdByCommunityId(community.getId());
		if(toUserList.size()==0){
			return new BaseResponse(0, "该小区没有用户，不能发布通知");
		}
		communityNotice.setUpdateDate(new Date());
		communityNotice.setStatus(1);
		mapper.updateByPrimaryKeySelective(communityNotice);
		if(communityNotice.getType()==null||communityNotice.getType()==0){//物业通知
			//融云发送消息
			TxtMessage message=new TxtMessage(msg);//文本信息
			applicationContext.publishEvent(new RongMessageEvent(community.getUser().getId(), toUserList, message));
			
			//推送
			PushBean pushBean = new PushBean();
			pushBean.setRefId(communityNotice.getId()+"");
			pushBean.setType(PushType.COMMUNITYNOTICE_MSG);
			pushBean.setContent(msg);
			String[] array=new String[toUserList.size()];
			String[] toUserIdsArr=toUserList.toArray(array);
			applicationContext.publishEvent(new PushMessageEvent(pushBean, "",toUserIdsArr ,PushFrom.LINLE_USER));
		}
		
		return BaseResponse.OperateSuccess;
	}

	@Override
	public boolean del(Long id) {
		return mapper.del(id)>0;
	}

	@Override
	public List<CommunityNotice> selectCommunityNoticeByCommunityId(Map<String, Object> map) {
		return mapper.selectCommunityNoticeByCommunityId(map);
	}
}
