package com.linle.feedback.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.entity.sys.Feedback;
import com.linle.feedback.service.FeedbackService;
import com.linle.mobileapi.base.BaseResponse;

/**
 * 
 * @ClassName: FeedbackController
 * @Description: 意见反馈管理
 * @author chenkai
 * @date 2016年7月20日
 *
 */
@Controller
@RequestMapping("feedback")
public class FeedbackController extends BaseController {

	@Autowired
	private FeedbackService feedbackService;

	// 列表
	@RequiresPermissions("feedback_manage")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model) {
		Page<Feedback> page = new Page<Feedback>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		page.setParams(params);
		try {
			feedbackService.getAllDate(page);
		} catch (Exception e) {
			_logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/feedback/feedback_list";
	}

	@RequiresPermissions("feedback_manage")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse del(Feedback back) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				feedbackService.del(back);
				return BaseResponse.OperateSuccess;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("删除意见反馈出错:" + e);
			return BaseResponse.ServerException;
		}
	}
}
