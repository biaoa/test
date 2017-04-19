package com.linle.communitySuggestionsComment.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.communitySuggestionsComment.model.CommunitySuggestionsComment;

public interface CommunitySuggestionsCommentService extends BaseService<CommunitySuggestionsComment>{

	List<CommunitySuggestionsComment> getSuggestionsDetailForApi(Map<String, Object> map);

	List<CommunitySuggestionsComment> selectCommentListById(Map<String, Object> map);

}
