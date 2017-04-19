package com.linle.cTopicComment.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.cTopic.service.CTopicService;
import com.linle.cTopicComment.mapper.CTopicCommentMapper;
import com.linle.cTopicComment.service.CTopicCommentService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.entity.sys.CTopic;
import com.linle.entity.sys.CTopicComment;
import com.linle.entity.sys.Users;
import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.mobileapi.v1.model.CTopicCommentVO;
import com.linle.mobileapi.v1.model.TopicReplyCommentVO;
import com.linle.mobileapi.v1.request.CommentOperateRequest;
@Service
@Transactional
public class CTopicCommentServiceImpl extends CommonServiceAdpter<CTopicComment>  implements CTopicCommentService {

	@Autowired
	private CTopicCommentMapper mapper;
	@Autowired
	private CTopicService cTopicService;
	
	@Override
	public List<CTopicCommentVO> getTopicDetailForApi(Map<String, Object> map) {
		return mapper.getTopicDetailForApi(map);
	}
	
	
	@Override
	public List<TopicReplyCommentVO> getTopicReplyListForApi(Map<String, Object> map) {
		return mapper.getTopicReplyListForApi(map);
	}
	
	@Override
	public int getUnReadCount(Map<String, Object> map) {
		return mapper.getUnReadCount(map);
	}
	
	/**
	 * 评论
	 * 1.增加评论记录
	 * 2.修改话题表评论字段
	 */
	public long commentOperate(CommentOperateRequest req,Users user){
			CTopicComment comment=new CTopicComment();
		try {
			long topicId=req.getTopicId();
			long toUser = Long.parseLong(req.getToUserId());
			//1.增加评论记录
			comment.setTopicId(req.getTopicId());
			comment.setFromUserId(user.getId());
			comment.setToUserId(toUser);
			comment.setContent(req.getContent());
			comment.setIsRead(false);
			comment.setIsMain(req.getIsMain());
			comment.setCreateTime(new Date());
			comment.setIsDel(0);
			boolean flag = mapper.insertSelective(comment)>0;
			//2.修改话题表评论数字段
			CTopic topic=cTopicService.selectByPrimaryKey(topicId);
			long commentNum=topic.getCommentNum()==null?0:topic.getCommentNum();
			CTopic record=new CTopic();
			record.setTopicId(req.getTopicId());
			record.setCommentNum(commentNum+1);
			cTopicService.updateByPrimaryKeySelective(record);
			
			//3.推送
			if(!user.getId().equals(toUser)){//评论自己发布的话题，不需要评论
				String msg=user.getName()+"评论了你："+req.getContent();
				PushBean pushBean = new PushBean();
				pushBean.setRefId(topicId+"");
				pushBean.setType(PushType.TOPIC_MSG);
				pushBean.setContent(msg);
				applicationContext.publishEvent(new PushMessageEvent(pushBean, "", toUser+"",PushFrom.LINLE_USER));
//				Map<String, String> maps=new HashMap<String, String>();
//				maps.put("type", "topic");
//				maps.put("refId", topicId+"");
//				pushMessageService.pushAliasMessageByMaps(maps, msg, toUser+"");
			}
			return comment.getCommentId();
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("添加评论出错了");
		}
	}

	/**
	 * 后台评论
	 * 1.增加评论记录
	 * 2.修改话题表评论字段
	 */
	public CTopicCommentVO addTopicComment(CTopicComment comment,Users user){
		try {
			//1.增加评论记录
			comment.setFromUserId(user.getId());
			comment.setIsRead(false);
			comment.setCreateTime(new Date());
			comment.setIsDel(0);
			boolean flag = mapper.insertSelective(comment)>0;
			//2.修改话题表评论数字段
			CTopic topic=cTopicService.selectByPrimaryKey(comment.getTopicId());
			long commentNum=topic.getCommentNum()==null?0:topic.getCommentNum();
			CTopic record=new CTopic();
			record.setTopicId(comment.getTopicId());
			record.setCommentNum(commentNum+1);
			cTopicService.updateByPrimaryKeySelective(record);
			
			//3.推送
			if(!user.getId().equals(comment.getToUserId())){//评论自己发布的话题，不需要评论
				String msg=user.getName()+"评论了你："+comment.getContent();
				PushBean pushBean = new PushBean();
				pushBean.setRefId(comment.getTopicId()+"");
				pushBean.setType(PushType.TOPIC_MSG);
				pushBean.setContent(msg);
				applicationContext.publishEvent(new PushMessageEvent(pushBean, "", comment.getToUserId()+"",PushFrom.LINLE_USER));
			}
//			getTopicComment
			return mapper.getTopicComment(comment.getCommentId());
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("评论出错了");
		}
	}
	
    //修改已读
	public boolean updateReadCommentByUserid(Users user){
		try {
			CTopicComment comment=new CTopicComment();
			comment.setToUserId(user.getId());
			comment.setIsRead(false);
			mapper.updateReadCommentByUserid(comment);
			return true;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("修改状态出错了");
		}
	}
	
	//删除评论  根据commentId  is_del=1
	public boolean deleteTopicCommentById(long commentId){
		try {
			CTopicComment c=mapper.selectByPrimaryKey(commentId);
			if(c.getIsDel()!=1){
				CTopicComment comm=mapper.selectByPrimaryKey(commentId);//获得topicid
				CTopic topic=cTopicService.selectByPrimaryKey(comm.getTopicId());
				//删除评论
				CTopicComment comment=new CTopicComment();
				comment.setCommentId(commentId);
				comment.setIsDel(1);
				int count=mapper.updateByPrimaryKeySelective(comment);
				//将话题表评论数修改
				CTopic record=new CTopic();
				record.setTopicId(comm.getTopicId());
				long num=topic.getCommentNum()-count;
				record.setCommentNum(num<0?0:num);
				cTopicService.updateByPrimaryKeySelective(record);
				return true;
			}else{//该评论已经删除过
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("删除评论出错了");
		}
	}
		
	//删除评论  根据topicId  is_del=1
	public boolean updateCommentDelByTopicId(long topicId){
		try {
			CTopicComment comment=new CTopicComment();
			comment.setTopicId(topicId);
			comment.setIsDel(1);
			int count=mapper.updateCommentDelByTopicId(comment);
			return true;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("删除评论出错");
		}
	}
	
	//别人回复我的tab列表  移除is_del=2
	public boolean removeReplyCommentById(long commentId){
		try {
			CTopicComment comment=new CTopicComment();
			comment.setCommentId(commentId);
			comment.setIsDel(2);
			mapper.updateByPrimaryKeySelective(comment);
			return true;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			throw new RuntimeException("别人回复我的tab列表  移除is_del=2");
		}
	}
	
}
