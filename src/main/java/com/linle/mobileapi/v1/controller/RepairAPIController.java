package com.linle.mobileapi.v1.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linle.common.base.BaseController;
import com.linle.common.util.DateUtil;
import com.linle.common.util.OrderCode;
import com.linle.common.util.SysConfig;
import com.linle.entity.sys.RepairRecord;
import com.linle.entity.sys.RepairType;
import com.linle.entity.sys.SysFolder;
import com.linle.entity.sys.Users;
import com.linle.event.SystemMsgEvent;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.RepairListRequest;
import com.linle.mobileapi.v1.request.RepairOperateRequest;
import com.linle.mobileapi.v1.request.RepairRequest;
import com.linle.mobileapi.v1.response.RepairListResponse;
import com.linle.mobileapi.v1.response.RepairTypeResponse;
import com.linle.repairRecord.service.RepairRecordService;
import com.linle.repairType.sercie.RepairTypeService;
import com.linle.socket.msg.BaseWebSocketMsgDataVo;
import com.linle.socket.msg.model.WebSocketMsg;

@Controller
@RequestMapping("/api/1")
public class RepairAPIController extends BaseController {

	@Autowired
	private RepairTypeService typeService;


	@Autowired
	private RepairRecordService repairService;

	// 获得类型列表
	@ResponseBody
	@RequestMapping(value = "/getRepairType")
	public BaseResponse getTypeList(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		RepairTypeResponse res = new RepairTypeResponse();
		try {
			res.setData(typeService.getAllType());
			res.setCode(0);
			res.setMsg("获取成功");
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		return res;
	}

	/**
	 * 
	 * @Description: 提交报修服务
	 * @param @param
	 *            req
	 * @param @return
	 * @return BaseResponse
	 */
	@ResponseBody
	@RequestMapping(value = "/submitRepair", method = RequestMethod.POST)
	public BaseResponse addRepair(RepairRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				Long folderID = doFile(req.getList(), servletRequest,SysConfig.REPAIR_FOLDER);
				SysFolder folder = new SysFolder();
				folder.setId(folderID);
				RepairRecord record = new RepairRecord();
				RepairType type = new RepairType();
				type.setId(req.getTypeID());
				record.setCommunityId(user.getCommunity().getId());
				record.setUser(user);
				record.setContent(req.getContent());
				record.setRepairType(type);
				record.setRepairPattern(0);
				record.setBeginDate(DateUtil.StringToDate(req.getBeginDate()));
				record.setEndDate(DateUtil.StringToDate(req.getEndDate()));
				record.setStatus(0);
				record.setFolder(folder);
				record.setSingle(OrderCode.GenerationOrderCode());
				boolean isok = repairService.insertSelective(record) > 0;
				return isok ? BaseResponse.AddSuccess : BaseResponse.AddFail;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("添加报修出错了");
			return BaseResponse.ServerException;
		}

	}


	// 获得报修列表
	@ResponseBody
	@RequestMapping(value = "/getRepairList")
	public BaseResponse getRepairList(@Valid RepairListRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		RepairListResponse res = new RepairListResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Map<String, Object> map = new HashMap<>();
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				map.put("uid", user.getId());
				map.put("parentTypeId", req.getParentTypeId());
				res.setData(repairService.getRepairList(map));
				res.setCode(0);
				res.setMsg("获取成功");
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			return BaseResponse.ServerException;
		}
		return res;
	}

	// 报修单 已完成
	@ResponseBody
	@RequestMapping(value = "/repairOperate")
	public BaseResponse repairOperate(RepairOperateRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		BaseResponse res = new BaseResponse();
		try {
			if (req.getStatus() == null) {
				res.setCode(1);
				res.setMsg("此报修不能完成");
				return res;
			}
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				if(-1==req.getStatus()){
					BaseWebSocketMsgDataVo msgDate = new BaseWebSocketMsgDataVo();
					msgDate.setTilte("报修服务提醒");
					msgDate.setBody("用户:"+user.getAddressDetails().getOverall()+"提交了报修提醒，请及时查看");
					msgDate.setUrl("/repairRecord/list");
					WebSocketMsg msg = new WebSocketMsg();
					msg.setFrom((long)1);
					msg.setTo(user.getCommunity().getUser().getId());
					msg.setObj(m.writeValueAsString(msgDate));
					msg.setSendDate(new Date());
					if(user.getCommunity().getPresident()!=null){
						//如果社长信息存在(社长因为有可能有多个小区，所以信息中增加小区信息)
						msgDate.setBody(user.getCommunity().getName()+"用户:"+user.getAddressDetails().getOverall()+"提交了报修提醒，请及时查看");
						msg.setObj(m.writeValueAsString(msgDate));
						List<Long> tos = new ArrayList<Long>();
						tos.add(msg.getTo());
						tos.add(user.getCommunity().getPresident().getUserId()); //获得小区的社长id
						msg.setTos(tos);
					}
					applicationContext.publishEvent(new SystemMsgEvent(msg.getTo().toString(),msg));
					return BaseResponse.OperateSuccess;
				}
				Map<String, Object> map = new HashMap<>();
				map.put("uid", user.getId());
				map.put("rid", req.getId());
				map.put("status", req.getStatus());
				res = repairService.operateForAPI(map);
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		return res;
	}
	//提醒

	public static void main(String[] args) throws JsonProcessingException, ParseException {

	}
}