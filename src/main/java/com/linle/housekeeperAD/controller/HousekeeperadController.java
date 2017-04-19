package com.linle.housekeeperAD.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.entity.sys.Housekeeperad;
import com.linle.housekeeperAD.service.HousekeeperadService;
import com.linle.mobileapi.base.BaseResponse;

//管家广告管理
@Controller
@RequestMapping("/housekeeperAD")
public class HousekeeperadController extends BaseController {

	@Autowired
	private HousekeeperadService housekeeperadService;

	@Autowired
	private CommunityService communityService;

	// 列表
	@RequiresPermissions("housekeeperAD_manage")
	@RequestMapping("/list")
	public String list(Integer pageNo, Model model) {
		Page<Housekeeperad> page = new Page<Housekeeperad>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		page.setParams(params);
		try {
			housekeeperadService.getAllHousekeeperAD(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return "redirect:/500";
		}
		model.addAttribute("pagelist", page);
		return "/housekeeperAD/housekeeperAD_list";
	}

	// 添加
	@RequiresPermissions(value = "housekeeperAD_manage")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, Long id) {
		Housekeeperad housekeeperad = new Housekeeperad();
		if (id != null) {
			housekeeperad = housekeeperadService.selectByPrimaryKey(id);
		}
		List<Community> communityList = communityService.selectAllCommunity();
		model.addAttribute("communityList", communityList);
		model.addAttribute("housekeeperad", housekeeperad);
		return "housekeeperAD/housekeeperAD_add";
	}

	@RequiresPermissions(value = "housekeeperAD_manage")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse addBanner(Housekeeperad housekeeperad) {
		try {
			if (housekeeperad.getHasH5()==null) {
				housekeeperad.setHasH5(1);
			}
			if (housekeeperad.getId() != null) {
				housekeeperad.setUpdateDate(new Date());
				housekeeperadService.updateByPrimaryKeySelective(housekeeperad);
				return BaseResponse.OperateSuccess;
			}
			housekeeperad.setCreateUser(getCurrentUser().getId());
			housekeeperad.setCreateDate(new Date());
			housekeeperadService.insertSelective(housekeeperad);
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
	@RequiresPermissions(value = "housekeeperAD_manage")
	@RequestMapping(value = "/setShow", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse setShow(Housekeeperad housekeeperad) {
		try {
			Housekeeperad had = housekeeperadService.selectByPrimaryKey(housekeeperad.getId());
			had.setShowFlag(housekeeperad.getShowFlag());
			housekeeperadService.updateByPrimaryKeySelective(had);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	// 预览
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Long id, Model model) {
		try {
			Housekeeperad housekeeperad = housekeeperadService.selectByPrimaryKey(id);
			model.addAttribute("housekeeperad", housekeeperad);
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
			return "error/500";
		}
		return "housekeeperAD/housekeeperAD_detail";
	}

	@RequiresPermissions(value = "housekeeperAD_manage")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse del(Long id) {
		try {
			housekeeperadService.deleteByPrimaryKey(id);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

}
