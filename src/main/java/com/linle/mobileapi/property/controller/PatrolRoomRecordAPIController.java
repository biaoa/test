package com.linle.mobileapi.property.controller;

import java.util.Date;
import java.util.HashMap;
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

import com.linle.common.base.BaseController;
import com.linle.common.util.LimitUtil;
import com.linle.communityEmployee.service.CommunityEmployeeService;
import com.linle.entity.sys.CommunityEmployee;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.property.request.PatrolRoomRecordListRequest;
import com.linle.mobileapi.property.request.PatrolRoomRecordOperateRequest;
import com.linle.mobileapi.property.response.PatrolRoomRecordListResponse;
import com.linle.patrolRoomRecord.model.PatrolRoomRecord;
import com.linle.patrolRoomRecord.service.PatrolRoomRecordService;

@Controller
@RequestMapping("/api/1/property")
public class PatrolRoomRecordAPIController extends BaseController {

	@Autowired
	private PatrolRoomRecordService patrolRoomRecordService;
	@Autowired
	private CommunityEmployeeService communityEmployeeService;
	//获取巡更记录列表
	@ResponseBody
	@RequestMapping(value = "/getPatrolRoomRecordList",method = RequestMethod.POST)
	public BaseResponse getPatrolRoomRecordList(PatrolRoomRecordListRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		PatrolRoomRecordListResponse res = new PatrolRoomRecordListResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				Map<String, Object> map = new HashMap<>();
				map.put("searchValue", req.getSearchValue());
				map.put("identity", user.getIdentity().getCode());
				int identity=user.getIdentity().getCode();
				if(identity==10){//普通员工--小区员工
					map.put("u_id", user.getId());
				}else if(identity==11){//部门负责人-- 小区员工
					CommunityEmployee emp=communityEmployeeService.selectByUid(user.getId());
					if(emp!=null){
						map.put("departmentId",emp.getDepartmentId());
					}
				}else if(identity==3){//小区账号
					map.put("communityId", getCommunity().getId());
				}else{
					return new BaseResponse(1,"此账号无法查询巡更记录");
				}
				
				LimitUtil.limit(map,req.getPageSize(), req.getPageNumber());
				res.setData(patrolRoomRecordService.getPatrolRoomRecordListForApi(map));
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
		
	/**
	 * 巡更记录操作
	 */
	@ResponseBody
	@RequestMapping(value = "/doOperate", method = RequestMethod.POST)
	public BaseResponse doOperate(@Valid PatrolRoomRecordOperateRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				PatrolRoomRecord record = new PatrolRoomRecord();
				record.setuId(user.getId());
				record.setCheckAddress(req.getCheckAddress());
				record.setScanAddress(req.getScanAddress());
				record.setStatus(req.getStatus());
				record.setRemark(req.getRemark());
				record.setCreateDate(new Date());
				patrolRoomRecordService.insertSelective(record);
				return BaseResponse.OperateSuccess;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.info("巡更插入出错了");
			return BaseResponse.ServerException;
		}

	}
	
	
}