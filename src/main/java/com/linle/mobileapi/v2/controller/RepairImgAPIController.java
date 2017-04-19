package com.linle.mobileapi.v2.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.DateUtil;
import com.linle.common.util.OrderCode;
import com.linle.entity.sys.RepairRecord;
import com.linle.entity.sys.RepairType;
import com.linle.entity.sys.SysFolder;
import com.linle.entity.sys.Users;
import com.linle.event.SystemMsgEvent;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.RepairRequest;
import com.linle.repairRecord.service.RepairRecordService;
import com.linle.socket.msg.BaseWebSocketMsgDataVo;
import com.linle.socket.msg.model.WebSocketMsg;

@Controller
@RequestMapping("/api/2")
public class RepairImgAPIController extends BaseController {

	@Autowired
	private RepairRecordService repairService;

	/**
	 * @Description: 提交报修服务
	 */
	@ResponseBody
	@RequestMapping(value = "/submitRepair", method = RequestMethod.POST)
	public BaseResponse addRepair(RepairRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				RepairRecord record = new RepairRecord();
				if(req.getFolderId()!=null){
					SysFolder folder = new SysFolder();
					folder.setId(req.getFolderId());//app端传过来，先上传图片，再执行该提交报修服务方法
					record.setFolder(folder);
				}
				RepairType type = new RepairType();
				type.setId(req.getTypeID());
				record.setCommunityId(user.getCommunity().getId());
				record.setUser(user);
				record.setContent(req.getContent());
				record.setRepairType(type);//报修项目
				record.setRepairPattern(0);//报修形式-线上
				record.setParentTypeId(req.getParentTypeId());//报修类型
				record.setBeginDate(DateUtil.StringToDate(req.getBeginDate()));
				record.setEndDate(DateUtil.StringToDate(req.getEndDate()));
				record.setStatus(0);
				record.setSingle(OrderCode.GenerationOrderCode());
				boolean isok = repairService.insertSelective(record) > 0;
				
				BaseWebSocketMsgDataVo msgDate = new BaseWebSocketMsgDataVo();
				msgDate.setTilte("报修服务提醒");
				msgDate.setBody("用户:"+user.getAddressDetails().getOverall()+"提交了新的报修，请及时查看");
				msgDate.setUrl("/repairRecord/list");
				WebSocketMsg msg = new WebSocketMsg();
				msg.setFrom((long)1);
				msg.setTo(user.getCommunity().getUser().getId());
				msg.setObj(m.writeValueAsString(msgDate));
				msg.setSendDate(new Date());
				if(user.getCommunity().getPresident()!=null){
					//如果社长信息存在
					msgDate.setBody(user.getCommunity().getName()+"用户:"+user.getAddressDetails().getOverall()+"提交了报修提醒，请及时查看");
					msg.setObj(m.writeValueAsString(msgDate));
					List<Long> tos = new ArrayList<Long>();
					tos.add(msg.getTo());
					tos.add(user.getCommunity().getPresident().getUserId()); //获得小区的社长id
					msg.setTos(tos);
				}
				applicationContext.publishEvent(new SystemMsgEvent(msg.getTo().toString(),msg));
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

}