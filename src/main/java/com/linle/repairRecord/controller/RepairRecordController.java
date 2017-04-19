package com.linle.repairRecord.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.DateUtil;
import com.linle.common.util.Page;
import com.linle.common.util.StringUtil;
import com.linle.entity.sys.RepairRecord;
import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.repairRecord.service.RepairRecordService;
import com.linle.repairType.sercie.RepairTypeService;

/**
 * 
* @ClassName: HouseResourceController 
* @Description: 报修记录管理
* @author pangd
* @date 2016年3月28日 上午9:48:13 
*
 */
@Controller
@RequestMapping("repairRecord")
public class RepairRecordController extends BaseController{
	
	@Autowired
	private RepairRecordService recordService;
	
	@Autowired
	private RepairTypeService typeService;
	//列表
	@RequiresPermissions("repair_manager")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model,String single,String typeId,String phone,String createDate,String beginDate 
			,String endDate,String status,String parentTypeId) {
		Page<RepairRecord> page = new Page<RepairRecord>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		//查询条件分装到这个map里面去
		params.put("communityId",getCommunity().getId());
		if (StringUtil.isNotNull(single)) {
			params.put("single", single);
		}
		if (StringUtil.isNotNull(typeId)) {
			params.put("typeId", typeId);
		}
		if (StringUtil.isNotNull(phone)) {
			params.put("phone", phone);
		}
		if (StringUtil.isNotNull(createDate)) {
			params.put("createDate", DateUtil.ShortStringToDate(createDate));
		}
		if (StringUtil.isNotNull(beginDate)) {
			params.put("beginDate", DateUtil.ShortStringToDate(beginDate));
		}
		if (StringUtil.isNotNull(endDate)) {
			params.put("endDate", DateUtil.ShortStringToDate(endDate));
		}
		params.put("status", status);
		if (StringUtil.isNull(parentTypeId)) {
			parentTypeId="0";
		}
		params.put("parentTypeId", parentTypeId);
		page.setParams(params);
		try {
			recordService.findAllRepaiRecord(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		if (StringUtil.isNotNull(status)) {
			model.addAttribute("status", status);
		}else{
			model.addAttribute("status", "-1");
		}
		model.addAttribute("parentTypeId", parentTypeId);
		model.addAttribute("createDate", createDate);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("single", single);
		model.addAttribute("typeId", typeId);
		model.addAttribute("phone", phone);
		model.addAttribute("repairType",typeService.getAllType());
		model.addAttribute("pagelist", page);
		return "/repairRecord/repairRecord_list";
	}
	//操作
	
	@RequiresPermissions("repair_manager")
	@ResponseBody
	@RequestMapping(value = "/operate")
	public BaseResponse operate(Long rid,int val)  {
		RepairRecord record = recordService.selectByPrimaryKey(rid);
		if(record!=null){
			Map<String,Object> map = new HashMap<>();
			map.put("id", rid);
			map.put("status", val);
			String userMsg ="您提交的报修,物业已处理,请查看";
			PushBean pushBean = new PushBean();
			pushBean.setType(PushType.REPAIR_MSG);
			pushBean.setRefId(rid+"");
			pushBean.setContent(userMsg);
			applicationContext.publishEvent(new PushMessageEvent(pushBean, "",record.getUser().getId()+"" ,PushFrom.LINLE_USER));
			return recordService.operate(map);
		}
		return new BaseResponse(1, "记录不存在,请刷新页面重试");
	}
	//删除

}
