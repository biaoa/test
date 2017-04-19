package com.linle.preferentialActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linle.common.base.BaseController;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.preferentialActivity.model.PreferentialActivity;
import com.linle.preferentialActivity.service.PreferentialActivityService;
import com.linle.preferentialActivityRecord.model.PreferentialActivityRecord;
import com.linle.preferentialActivityRecord.service.PreferentialActivityRecordService;
@Controller
@RequestMapping("/preferentialActivity")
public class PreferentialActivityController extends BaseController {
	@Autowired
	private PreferentialActivityService preferentialActivityService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private PreferentialActivityRecordService preferentialActivityRecordService;
	// 列表
	@RequiresPermissions("preferentialActivity_list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model) {
		Page<PreferentialActivity> page = new Page<PreferentialActivity>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		try {
			preferentialActivityService.getAllForPage(page);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/preferentialActivity/preferentialActivity_list";
	}

	@RequestMapping(value = "/record_list", method = RequestMethod.GET)
	public String record_list(Integer pageNo, Model model) {
		Page<PreferentialActivityRecord> page = new Page<PreferentialActivityRecord>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		try {
			preferentialActivityRecordService.getAllForPage(page);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/preferentialActivity/preferentialActivityRecord_list";
	}
	//toAdd
	@RequiresPermissions("preferentialActivity_list")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public ModelAndView toAdd(Model model, Long id) {
		ModelAndView mv = new ModelAndView();
		PreferentialActivity obj= new PreferentialActivity();
		if (id != null) {
			 obj=preferentialActivityService.selectByPrimaryKey(id);
		}
		model.addAttribute("preferentialActivity", obj);
		List<Community> communityList = communityService.selectAllCommunity();
		model.addAttribute("communityList", communityList);
		mv.setViewName("preferentialActivity/preferentialActivity_add");
		return mv;
	}
	
	@RequiresPermissions("preferentialActivity_list")
	@RequestMapping(value = "/doOperate",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse doOperate(PreferentialActivity preferentialActivity) {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
			String beginDateStr=preferentialActivity.getBeginDateStr();
			String endDateStr=preferentialActivity.getEndDateStr();
			if(!"".equals(beginDateStr)&&!"".equals(endDateStr)){
				preferentialActivity.setBeginDate(sdf.parse(beginDateStr));
				preferentialActivity.setEndDate(sdf.parse(beginDateStr));
			}else{
				new BaseResponse(1, "开始时间和结束时间不能为空！");
			}
			if(preferentialActivity.getId()==null){
				preferentialActivity.setCreateDate(new Date());
				preferentialActivityService.insertSelective(preferentialActivity);
			}else{
				preferentialActivity.setUpdateDate(new Date());
				preferentialActivityService.updateByPrimaryKeySelective(preferentialActivity);
			}
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("preferentialActivity_list")
	@RequestMapping(value = "/del/{id}",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse del(@PathVariable Long id) {
		try {
			preferentialActivityService.deleteByPrimaryKey(id);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("删除出错了", e);
			return BaseResponse.ServerException;
		}
	}
}
