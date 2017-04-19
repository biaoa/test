package com.linle.propertyFee.controller;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.linle.common.base.BaseController;
import com.linle.common.util.FileUtil;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.common.util.StringUtil;
import com.linle.common.util.SysConfig;
import com.linle.entity.sys.BroadbandFee;
import com.linle.entity.sys.PropertyFee;
import com.linle.entity.sys.Utilities;
import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.propertyFee.service.PropertyFeeService;
import com.linle.roomno.service.RoomService;
import com.linle.sysOrder.service.SysOrderService;

/**
 * 
 * @ClassName: PropertyFeeController
 * @Description: 物业费
 * @author pangd
 * @date 2016年3月24日 下午2:18:29
 *
 */
@Controller
@RequestMapping("propertyFee")
public class PropertyFeeController extends BaseController {

	@Autowired
	private PropertyFeeService propertyFeeService;
	
	@Autowired
	private SysOrderService orderService;
	@Autowired
	private RoomService roomService;
	@RequiresPermissions("property_fee")
	@RequestMapping(value = "/list")
	public String index(Integer pageNo, Model model, String orderNo, String owner, String date, String yearDate,
			Integer status,String roomno,String buildingType,String unitType) {
		Page<PropertyFee> page = new Page<PropertyFee>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		// 查询条件分装到这个map里面去
		params.put("orderNo", orderNo);
		params.put("owner", owner);
		params.put("status", status);
		params.put("roomno", StringUtil.returnNewHouseNo(roomno));
		params.put("buildingType", buildingType);
		params.put("unitType", unitType);
		long communityId=getCommunity().getId();
		params.put("community_id", communityId);
		params.put("yearDate", yearDate);
		if(yearDate==null||"".equals(yearDate)){
			if (date != null && !"".equals(date)) {
				params.put("year", date.split("-")[0]);
				params.put("month", date.split("-")[1]);
			} else {
				propertyFeeService.getDateCondition(params);
				date=params.get("year")+"-"+params.get("month");
			}
		}

		page.setParams(params);
		try {
			propertyFeeService.findAllPropertyFee(page);
			PropertyFee propertyFee=propertyFeeService.getStatisticQuantity(params);
			model.addAttribute("sumQuantity", propertyFee==null?0:propertyFee.getSumQuantity());
			model.addAttribute("waitQuantity", propertyFee==null?0:propertyFee.getWaitQuantity());
			model.addAttribute("completeQuantity", propertyFee==null?0:propertyFee.getSumQuantity().subtract(propertyFee.getWaitQuantity()));
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("owner", owner);
		model.addAttribute("date", date);
		model.addAttribute("yearDate", yearDate);
		model.addAttribute("status", status);
		model.addAttribute("pagelist", page);
		model.addAttribute("roomno", roomno);
		model.addAttribute("buildingList", roomService.getBuildForAPI(communityId));//该小区所有幢
		model.addAttribute("buildingType", buildingType);//幢
		model.addAttribute("unitType", unitType);//单元
		model.addAttribute("status1", (status == null || 0 == status) ? "current" : "");
		model.addAttribute("status2", (status != null && status == 1) ? "current" : "");
		model.addAttribute("status3", (status != null && status == 2) ? "current" : "");
		return "/propertyFee/propertyFee_list";
	}
	
	@RequiresPermissions("property_fee")
	@RequestMapping(value="/toUpdate/{id}",method=RequestMethod.GET)
	public String toAdd(@PathVariable long id,HttpServletRequest request,Model model){
		PropertyFee record=propertyFeeService.selectByPrimaryKey(id);
		model.addAttribute("propertyFee", record);
		return "/propertyFee/propertyFee_add";
	}
	//save
	@RequiresPermissions("property_fee")
	@RequestMapping(value = "/doOperate", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse doOperate(@Valid PropertyFee propertyFee, BindingResult errors, Model model) {
		try {
			if (propertyFee.getId() != null) {//修改
				propertyFee.setUpdateDate(new Date());
				return propertyFeeService.updateFee(propertyFee);
			}
			return BaseResponse.OperateFail;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}

	}	
	
	@ResponseBody
	@RequestMapping(value = "/doexcel")
	public ResponseMsg fileupload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request,
			String type) throws Exception {
		byte[] bytes = file.getBytes();
		String contentType = FileUtil.getDateName(file.getOriginalFilename());/// 获取文件后缀名
		if (FileUtil.isExcel(contentType)) {// 验证文件后缀名是否正确
			String realPath = SysConfig.DISK_FOLDER;
			String fee_folder="propertyFee";
			String path = "/upload/fee/"+fee_folder+"/"+ FileUtil.getDateString();
			String uploadDir = realPath+path;

//			String path = "/upload/" + FileUtil.getDateString() + "3";
//			String uploadDir = request.getSession().getServletContext().getRealPath(path);
			File dirPath = new File(uploadDir);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			String sep = System.getProperty("file.separator");
			String pathimg = FileUtil.getFileName() + contentType;
			File uploadedFile = new File(uploadDir + sep + pathimg);
			FileCopyUtils.copy(bytes, uploadedFile);
			return propertyFeeService.propertyFeeInfo(uploadedFile.getPath(), getCommunity().getId(), type);
		} else {
			return new ResponseMsg(1, "上传错误", null);
		}
	}

	@RequiresPermissions("property_fee")
	@RequestMapping(value = "/send")
	@ResponseBody
	public BaseResponse send(HttpServletRequest request, String date) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			List<String> toUserIds = propertyFeeService.getNeedMessageList(getCommunity().getId(), date);
			if (toUserIds.isEmpty()) {
				return new BaseResponse(1, "没有符合条件的用户");
			}
			String dates[] = date.split("-");
			String msg = "您的" + dates[0] + "年" + dates[1] + "月" + "物业费账单已出，请您及时缴费" + new Date();
			try {
				// rongService.sendMessage(userInfo.getId(), toUserIds, new
				// TxtMessage(message));

				// 融云发送消息
				// TxtMessage message=new TxtMessage(msg);//文本信息
				// applicationContext.publishEvent(new
				// RongMessageEvent(userInfo.getId(), toUserIds, message));

				// 推送
				PushBean pushBean = new PushBean();
				pushBean.setType(PushType.PROPERTYFEE_MSG);// 物业费
				pushBean.setRefId("");
				pushBean.setContent(msg);
				String[] array = new String[toUserIds.size()];
				String[] toUserIdsArr = toUserIds.toArray(array);
				applicationContext.publishEvent(new PushMessageEvent(pushBean, "", toUserIdsArr, PushFrom.LINLE_USER));

			} catch (Exception e) {
				_logger.info("消息发送失败");
				e.printStackTrace(); _logger.error("出错了", e);
			}
		}
		return new BaseResponse(1, "发送成功");
	}

	/**
	 * 
	 * @Description 物业费 线下缴费的逻辑
	 * @param orderNo
	 * @return BaseResponse $
	 */
	@RequiresPermissions("property_fee")
	@RequestMapping(value = "/offline", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse offline(String orderNo) {
		try {
			boolean iosk = propertyFeeService.offline(orderNo);
			return iosk ? BaseResponse.OperateSuccess : BaseResponse.OperateFail;
		} catch (Exception e) {
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("property_fee")
	@RequestMapping(value="/alonesend",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse alonesend(HttpServletRequest request,String date,String orderNo){
		try {
			List<String> toUserIds = orderService.alonesendMessageUser(orderNo);
			if(toUserIds.isEmpty()){
				return new BaseResponse(1,"该房号下未找到注册用户");
			}
			String dates[] = date.split("-");
			String msg = "您的" + dates[0] + "年" + dates[1] + "月" + "物业费账单已出，请您及时缴费";
			PushBean pushBean = new PushBean();
			pushBean.setType(PushType.PROPERTYFEE_MSG);// 物业费
			pushBean.setRefId("");
			pushBean.setContent(msg);
			String[] array = new String[toUserIds.size()];
			String[] toUserIdsArr = toUserIds.toArray(array);
			applicationContext.publishEvent(new PushMessageEvent(pushBean, "", toUserIdsArr, PushFrom.LINLE_USER));
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("单独通知出错了:"+e);
			return BaseResponse.ServerException;
		}
	}

}
