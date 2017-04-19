package com.linle.activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linle.common.base.BaseController;
import com.linle.entity.sys.Users;
import com.linle.shop.service.ShopService;

/**
 * 
 * @author pangd
 * @Description app活动相关
 * @date 2016年9月9日上午9:58:21
 */
@Controller
@RequestMapping("/api/1/activity")
public class ActivityController extends BaseController {
	@Autowired
	private ShopService shopService;
	
	@RequestMapping("/2016/zhongqiu")
	public String Zhongqiu(Model model, String sid, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		processSidCookie(servletRequest, servletResponse, sid);
		int islogin = 1;
		try {
			validatorSid(sid);
		} catch (Throwable e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			islogin = 0;
		}
		model.addAttribute("islogin", islogin);
		return "/activity/2016_zhongqiu";
	}

	@RequestMapping("/paymentActivity")
	public String PaymentActivity(Model model, String sid, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			validatorSid(sid);
		} catch (Throwable e) {
			e.printStackTrace();
			_logger.error("缴费活动出错:"+e);
		}
		return "/activity/paymentActivity";
	}
	
	@RequestMapping("/commodityActivity")
	public String commodityActivity(Model model, String sid, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			validatorSid(sid);
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				model.addAttribute("shoplist", shopService.getAllActivityShop(user.getCommunity().getId()));
			}
		} catch (Throwable e) {
			e.printStackTrace();
			_logger.error("商家商品活动出错:"+e);
			return "/error/tologin";
		}
		return "/activity/commodityActivity";
	}
}
