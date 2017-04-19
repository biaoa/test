package com.linle.appBanner.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.appBanner.model.AppBannerStatisticalVo;
import com.linle.appBanner.service.AppBannerService;
import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.common.util.StringUtil;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.entity.sys.AppBanner;
import com.linle.entity.sys.Users;
import com.linle.event.AppBannerAccessEvent;
import com.linle.mobileapi.base.BaseResponse;

/**
 * 
 * @author pangd
 * @Description app banner管理
 */
@Controller
@RequestMapping("/appBanner")
public class AppBannerController extends BaseController {

	@Autowired
	private AppBannerService bannerService;

	@Autowired
	private CommunityService communityService;

	// 列表
	@RequiresPermissions("banner_manager")
	@RequestMapping("/list")
	public String list(Integer pageNo, Model model) {
		Page<AppBanner> page = new Page<AppBanner>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		page.setParams(params);
		try {
			bannerService.getAllBanner(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return "redirect:/500";
		}
		model.addAttribute("pagelist", page);
		return "/appBanner/appBanner_list";
	}

	// 添加
	@RequiresPermissions(value = "banner_manager")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, Long id) {
		AppBanner banner = new AppBanner();
		if (id != null) {
			banner = bannerService.selectByPrimaryKey(id);
		}
		List<Community> communityList = communityService.selectAllCommunity();
		model.addAttribute("communityList", communityList);
		model.addAttribute("banner", banner);
		return "appBanner/appBanner_add";
	}

	@RequiresPermissions(value = "banner_manager")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse addBanner(AppBanner banner) {
		try {
			if (banner.getIsTop() == null) {
				banner.setIsTop(1);
			}
			if (banner.getHasH5() == null) {
				banner.setHasH5(1);
			}

			if (banner.getId() != null) {
				banner.setUpdateDate(new Date());
				bannerService.updateByPrimaryKeySelective(banner);
				return BaseResponse.OperateSuccess;
			}
			banner.setUserId(getCurrentUser().getId());
			banner.setDelFlag(1); // 默认不启用
			banner.setCreateDate(new Date());

			bannerService.insertSelective(banner);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	/**
	 * 
	 * @Description 置顶/取消置顶功能
	 * @param banner
	 * @return BaseResponse $
	 */
	@RequiresPermissions(value = "banner_manager")
	@RequestMapping(value = "/setTop", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse setTop(AppBanner banner) {
		try {
			bannerService.updateByPrimaryKeySelective(banner);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	/**
	 * 
	 * @Description 设置显示/隐藏
	 * @param banner
	 * @return BaseResponse $
	 */
	@RequiresPermissions(value = "banner_manager")
	@RequestMapping(value = "/setShow", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse setShow(AppBanner banner) {
		try {
			bannerService.updateByPrimaryKeySelective(banner);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	// 预览
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Long id, String sid, Model model, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		AppBanner banner = bannerService.selectByPrimaryKey(id);
		Long uid = null, communityId = null;
		// device.isNormal()→ PC device.isMobile()→移动端 device.isTablet()→平板
		Device device = DeviceUtils.getCurrentDevice(request);
		if (!device.isNormal()) {
			if (StringUtil.isNotNull(sid)) {
				try {
					processSidCookie(servletRequest, servletResponse, sid);
//					validatorSid(sid);
					Subject subject = SecurityUtils.getSubject();
					if (subject.isAuthenticated()) {
						Users userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
						uid = userInfo.getId();
						communityId = userInfo.getCommunity().getId();
					}
				} catch (Throwable e) {
					e.printStackTrace(); _logger.error("出错了", e);
				}
			}
			// 只要不是PC端访问的appBanner都插入访问量表中;
			applicationContext.publishEvent(new AppBannerAccessEvent(id, uid, communityId));
		}
		if(banner!=null){
			if(banner.getHasH5()==2){
				return "redirect:"+banner.getBannerUrl()+"?sid="+sid;
			}
		}
		model.addAttribute("banner", banner);
		return "appBanner/appBanner_detail";
	}

	@RequiresPermissions(value = "banner_manager")
	@RequestMapping(value = "/del")
	@ResponseBody
	public BaseResponse del(Long id) {
		try {
			bannerService.deleteByPrimaryKey(id);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	// 首页轮播图统计
	@RequiresPermissions(value = "banner_manager")
	@RequestMapping(value = "/statistical")
	public String bannerStatistical(String description, String beginDate, String endDate, Integer status,
			Integer pageNo, Model model) {
		try {
			if (description != null) {
				description = new String(description.getBytes("iso8859-1"), "utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		Page<AppBannerStatisticalVo> page = new Page<>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("description", description);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		map.put("status", status);
		page.setParams(map);

		bannerService.statistical(page);

		model.addAttribute("description", description);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		model.addAttribute("pagelist", page);
		return "appBanner/appBanner_statistical";
	}
}
