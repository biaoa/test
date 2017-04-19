package com.linle.patrolRoomRecord.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.common.util.StringUtil;
import com.linle.communityDepartment.service.CommunityDepartmentService;
import com.linle.patrolRoomRecord.model.PatrolRoomRecord;
import com.linle.patrolRoomRecord.service.PatrolRoomRecordService;

/**
 * 
 * @Description:巡更记录管理
 * @author chenkai
 * @date 2016年8月10日
 *
 */
@Controller
@RequestMapping("patrolRoomRecord")
public class PatrolRoomRecordController extends BaseController {

	@Autowired
	private PatrolRoomRecordService patrolRoomRecordService;
	@Autowired
	private CommunityDepartmentService communityDepartmentService;
	
	// 列表
	@RequiresPermissions("patrolRoomRecord_manage")
	@RequestMapping(value = "/list")
	public String list(Integer pageNo, Model model,String empName,String beginDate,String endDate,String departmentId) {
		Page<PatrolRoomRecord> page = new Page<PatrolRoomRecord>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		long communityId=getCommunity().getId();
		params.put("communityId", communityId);
		if(StringUtil.isNotNull(beginDate)){
			params.put("beginDate", beginDate);
		}
		if (StringUtil.isNotNull(endDate)) {
			params.put("endDate", endDate);
		}
		if (StringUtil.isNotNull(departmentId)) {
			params.put("departmentId", departmentId);
		}
		if (StringUtil.isNotNull(empName)) {
			params.put("empName", empName);
		}
		page.setParams(params);
		try {
			patrolRoomRecordService.getAllDataForPage(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		model.addAttribute("departmentList",communityDepartmentService.getAllDepartment(getCommunity().getId()));
		//搜索相关
		model.addAttribute("empName", empName);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("departmentId", departmentId);
		return "/patrolRoomRecord/patrolRoomRecord_list";
	}
	
 }
