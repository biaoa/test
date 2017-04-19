package com.linle.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.linle.common.util.Page;
import com.linle.entity.sys.UserLog;
import com.linle.user.base.BaseUserController;
import com.linle.user.service.UserLogService;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月13日
 **/
@RequestMapping("/sys")
@Controller
public class UserLogController  extends BaseUserController{
	
	@Autowired
	protected UserLogService userLogService;
	/**
	 * 日志列表
	 * @param model
	 * @return
	 */
	@RequiresPermissions("system_logManage")
	@RequestMapping(value = "/loglist", method = RequestMethod.GET)
	public ModelAndView logList(Integer pageNo, Model model) {
		ModelAndView mv = new ModelAndView();
		Page<UserLog> page = new Page<UserLog>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		//查询条件分装到这个map里面去
		page.setParams(params);
		page.setResults(userLogService.findAllUserLogs(page));
		model.addAttribute("pagelist", page);
		mv.setViewName("system/log_list");
		return mv;
	}
}
