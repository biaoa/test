package com.linle.knowledgeThumbRecord.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.entity.sys.Users;
import com.linle.knowledgeThumbRecord.mapper.KnowledgeThumbRecordMapper;
import com.linle.knowledgeThumbRecord.model.KnowledgeThumbRecord;
import com.linle.knowledgeThumbRecord.service.KnowledgeThumbRecordService;
import com.linle.mobileapi.base.BaseResponse;

@Service
public class KnowledgeThumbRecordServiceImpl extends CommonServiceAdpter<KnowledgeThumbRecord>
		implements KnowledgeThumbRecordService {

	@Autowired
	private KnowledgeThumbRecordMapper mapper;

	@Override
	public BaseResponse addThumb(Users user, Long knowledgeId, Integer status) {
		Map<String, Object> map = new HashMap<>();
		map.put("uid", user.getId());
		map.put("knowledgeId", knowledgeId);
		map.put("status", status);
		//第一步 判断用户是否点赞过
		KnowledgeThumbRecord record =selectByUserIdAndKnowledgeId(map);
		if (record!=null) {
			//赞过
			if(status==record.getStatus()){
				return BaseResponse.OperateFail;
			}
			record.setStatus(status);
			record.setUpdateDate(new Date());
			mapper.updateByPrimaryKeySelective(record);
			return BaseResponse.OperateSuccess;
		}else{
			//没赞过
			if(status==1){
				return BaseResponse.OperateFail;
			}
			record = new KnowledgeThumbRecord();
			record.setUid(user.getId());
			record.setCommunityId(user.getCommunity().getId());
			record.setKnowledgeId(knowledgeId);
			record.setStatus(status);
			mapper.insertSelective(record);
			return BaseResponse.OperateSuccess;
		}
	}

	@Override
	public KnowledgeThumbRecord selectByUserIdAndKnowledgeId(Map<String, Object> map) {
		return mapper.selectByUserIdAndKnowledgeId(map);
	}
}
