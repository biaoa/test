package com.linle.socket.msg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.LimitUtil;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.socket.msg.BaseWebSocketMsgDataVo;
import com.linle.socket.msg.model.WebSocketMsg;
import com.linle.socket.msg.service.WebSocketMsgService;

@Controller
@RequestMapping("/webScoketMsg")
public class WebSocketMsgController extends BaseController {

	@Autowired
	private WebSocketMsgService webSocketMsgService;

	@RequestMapping(value = "/readMsg", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse readMsg(Long id) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (!subject.isAuthenticated()) {
				return BaseResponse.PleaseSignIn;
			}
			WebSocketMsg msg = webSocketMsgService.selectByPrimaryKey(id);
			if (msg != null && msg.getReadStatus() == 0) {
				msg.setReadStatus(1);
				webSocketMsgService.modifyReadStatus(msg);
				return BaseResponse.OperateSuccess;
			} else {
				return new BaseResponse(1, "消息不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BaseResponse.ServerException;
		}
	}

	@RequestMapping(value = "getMsgListCount", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse msgListCount(Integer status) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (!subject.isAuthenticated()) {
				return BaseResponse.PleaseSignIn;
			}
			Map<String, Object> params = new HashMap<>();
			params.put("to", getCurrentUser().getId());
			params.put("readStatus", status);
			int msgCount = webSocketMsgService.getMsgListCount(params);
			Map<String, Object> result = new HashMap<>();
			result.put("msgCount", msgCount);
			return new BaseResponse(0, "获取成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return BaseResponse.ServerException;
		}
	}

	@RequestMapping(value = "getMsgList", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse msgList(Integer status, Integer pageNo) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (!subject.isAuthenticated()) {
				return BaseResponse.PleaseSignIn;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("to", getCurrentUser().getId());
			map.put("readStatus", status);
			LimitUtil.limit(map, 10, pageNo);
			List<BaseWebSocketMsgDataVo> msgList = webSocketMsgService.getMsgList(map);
			Map<String, Object> result = new HashMap<>();
			result.put("msgList", msgList);
			return new BaseResponse(0, "", result);
		} catch (Exception e) {
			e.printStackTrace();
			return BaseResponse.ServerException;
		}
	}

	@RequestMapping("/toMsgList")
	public String toMsgList() {
		return "msg/msgList";
	}
}
