package com.linle.utilities.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.linle.common.util.DateUtil;
import com.linle.common.util.FileUtil;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.common.util.StringUtil;
import com.linle.common.util.SysConfig;
import com.linle.entity.sys.Utilities;
import com.linle.entity.vo.WaterVO;
import com.linle.event.PushMessageEvent;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushType;
import com.linle.mobileapi.v1.model.BaseEntity;
import com.linle.priceSetting.model.PriceSetting;
import com.linle.priceSetting.service.PriceSettingService;
import com.linle.roomno.service.RoomService;
import com.linle.sysOrder.service.SysOrderService;
import com.linle.utilities.service.UtilitiesService;
import com.linle.utilitiesChild.service.UtilitiesChildService;

//水费，电费，燃气费
@Controller
@RequestMapping("utilities")
public class UtilitiesController extends BaseController {

	@Autowired
	private UtilitiesService utilitiesService;
	
	@Autowired
	private SysOrderService orderService;
	@Autowired
	private RoomService roomService;
	@Autowired
	private PriceSettingService priceSettingService;
	
	@RequiresPermissions("water")
	@RequestMapping(value = "/list")
	public String index(Integer pageNo, Model model, String orderNo, String owner, String date, Integer status,
			String type,String roomno,String buildingType,String unitType) {
		Page<Utilities> page = new Page<Utilities>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		// 查询条件分装到这个map里面去
		params.put("orderNo", orderNo);
		params.put("owner", owner);
		params.put("status", status);
		params.put("buildingType", buildingType);
		params.put("unitType", unitType);
		params.put("roomno", StringUtil.returnNewHouseNo(roomno));
		long communityId=getCommunity().getId();
		params.put("community_id", communityId);
		params.put("type", type);
		if (date != null && !"".equals(date)) {
			params.put("year", date.split("-")[0]);
			params.put("month", date.split("-")[1]);
		} else {
			utilitiesService.getDateCondition(params);
			date=params.get("year")+"-"+params.get("month");
		}
		page.setParams(params);
		try {
			utilitiesService.findAllUtilities(page);
			Utilities utilities=utilitiesService.getStatisticQuantity(params);
			System.out.println(params);
			model.addAttribute("sumQuantity", utilities==null?0:utilities.getSumQuantity());
			model.addAttribute("waitQuantity", utilities==null?0:utilities.getWaitQuantity());
			model.addAttribute("completeQuantity", utilities==null?0:utilities.getSumQuantity().subtract(utilities.getWaitQuantity()));
		} catch (Exception e) {
			_logger.error("出错了", e);
		}
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("owner", owner);
		model.addAttribute("date", date);
		model.addAttribute("status", status);
		model.addAttribute("pagelist", page);
		model.addAttribute("type", type);
		model.addAttribute("buildingList", roomService.getBuildForAPI(communityId));//该小区所有幢
		model.addAttribute("buildingType", buildingType);//幢
		model.addAttribute("unitType", unitType);//单元
		model.addAttribute("roomno", roomno);
		model.addAttribute("status1", (status == null || 0 == status) ? "current" : "");
		model.addAttribute("status2", (status != null && status == 1) ? "current" : "");
		model.addAttribute("status3", (status != null && status == 2) ? "current" : "");
		return "/utilities/utilities_list";
	}

	//缴费订单生成
	@RequiresPermissions("water")
	@RequestMapping(value = "/utilitiesCreateOrder", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg utilitiesCreateOrder(String jsonStr,String type,String recordTime,String yearMonth,Model model) {
		try {
			ResponseMsg res=utilitiesService.utilitiesCreateOrder(jsonStr,getCommunity().getId(),type,recordTime,yearMonth);
			return res;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1, "操作失败", null);
		}

	}
	
	//账单生成列表
	@RequiresPermissions("water")
	@RequestMapping(value = "/utilities_create")
	public String utilities_create(Integer pageNo, Model model,String recordTime,String yearMonth,
			String type,String roomno,String buildingType,String unitType) {
		Page<WaterVO> page = new Page<WaterVO>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		long communityId=getCommunity().getId();
		
		List<BaseEntity> buildingList=roomService.getBuildForAPI(communityId);
		List<BaseEntity> unitList=new ArrayList<>();
		if(buildingList.size()>0){
			unitList=roomService.getUnitForAPI(communityId,buildingList.get(0).getName());
			
			// 查询条件分装到这个map里面去
			if(buildingType==null||"".equals(buildingType)){
				buildingType=buildingList.get(0).getName().toString();
			}
			if(unitType==null||"".equals(unitType)){
				unitType=unitList.get(0).getName().toString();
			}
		}
	
		params.put("buildingType", buildingType);
		params.put("unitType", unitType);
		params.put("roomno", roomno);
		params.put("community_id", communityId);
		params.put("type", type);


 		//获取上次缴费的时间  年-月
		//排除当前应缴月份，可能会存在这种情况：当前月份只是生成部分房号的账单，若不排除当前要生成应缴月份，则会获取到当前即将生成应缴月份
		if(yearMonth!=null){
			params.put("month", yearMonth.split("-")[1]);
			params.put("year", yearMonth.split("-")[0]);
		}else{
			params.put("month", DateUtil.getMonth());
			params.put("year", DateUtil.getYear());
		}
 		Map<String, Object> map=utilitiesService.getDateCondition(params);
 		params.put("monthOld", map.get("month"));
		params.put("yearOld", map.get("year"));
		
		//应缴月份
		if(yearMonth!=null){
			params.put("month", yearMonth.split("-")[1]);
			params.put("year", yearMonth.split("-")[0]);
		}else{
			params.put("month", DateUtil.getMonth());
			params.put("year", DateUtil.getYear());
		}
		params.put("settingPrice", utilitiesService.getSettingPrice(communityId, type));
		page.setParams(params);
		try {
			utilitiesService.findAllUtilitiesVo(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		model.addAttribute("type", type);
		
		model.addAttribute("buildingList", buildingList);//该小区所有幢
		model.addAttribute("buildingType", buildingType);//幢
		model.addAttribute("unitList", unitList);//该小区所有单元
		model.addAttribute("unitType", unitType);//单元
		model.addAttribute("recordTime", recordTime);
		model.addAttribute("roomno", roomno);
		String typeStr="";
		int typeId=Integer.parseInt(type);
		if(typeId==1){//1 水费 2电费 3燃气费'
			typeStr="water";
		}else if(typeId==2){
			typeStr="electricity";
		}else if(typeId==3){
			typeStr="gas";
		}
		PriceSetting priceSetting=priceSettingService.selectByType(communityId, typeStr, null);
		if(priceSetting!=null){
			model.addAttribute("setprice", priceSetting.getPrice());
		}
		model.addAttribute("recordTime",recordTime);
		model.addAttribute("yearMonth",yearMonth);
		return "/utilities/utilities_create";
	}
	

	/**
	 * 查看某房号所有缴费记录
	 * @return
	 */
	@RequiresPermissions("water")
	@RequestMapping(value = "/owner_utilities_detail")
	public String owner_utilities_detail(Integer pageNo, Model model, String orderNo, String owner, String date, 
			String type,String roomno) {
		Page<Utilities> page = new Page<Utilities>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		// 查询条件分装到这个map里面去
		params.put("orderNo", orderNo);
//		params.put("owner", owner);
		params.put("roomno", roomno);
		long communityId=getCommunity().getId();
		params.put("community_id", communityId);
		params.put("type", type);
		if (date != null && !"".equals(date)) {
			params.put("year", date.split("-")[0]);
			params.put("month", date.split("-")[1]);
		} 
		page.setParams(params);
		try {
			utilitiesService.findAllOwnerUtilities(page);
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
		}
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("owner", owner);
		model.addAttribute("date", date);
		model.addAttribute("pagelist", page);
		model.addAttribute("type", type);
		model.addAttribute("roomno", roomno);
		return "/utilities/owner_utilities_detail";
	}
	
	@RequiresPermissions("water")
	@RequestMapping(value="/toUpdate/{id}",method=RequestMethod.GET)
	public String toAdd(@PathVariable long id,HttpServletRequest request,Model model){
		Utilities record=utilitiesService.selectByPrimaryKey(id);
		model.addAttribute("utilities", record);
		return "/utilities/utilities_add";
	}
	
	@Autowired
	private UtilitiesChildService utilitiesChildService;
	
	//save
	@RequiresPermissions("water")
	@RequestMapping(value = "/doOperate", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse doOperate(@Valid Utilities utilities, BindingResult errors, Model model) {
		BaseResponse res=new BaseResponse();
		try {
			if (utilities.getId() != null) {//修改
				//状态(0 全部 1未交 2已交 3线下缴费 4 已带到下期缴费 5 已缴预缴 )
				if(utilities.getStatus()==2||utilities.getStatus()==3||utilities.getStatus()==5){//2已交 3线下缴费 4 已带到下期缴费
					Utilities record=utilitiesService.selectByPrimaryKey(utilities.getId());
					if(record!=null){
						utilitiesChildService.insertUtilitiesChild(record,utilities);
						return BaseResponse.OperateSuccess;
					}else{
						return BaseResponse.OperateFail;
					}
				}
				if(utilities.getStatus()==1){//未交
					res=utilitiesService.updateFee(utilities);
				}
				return res;
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
	public ResponseMsg fileupload(@RequestParam(value = "file") MultipartFile file,
			String type,Long uid,HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			processSidCookie(servletRequest, servletResponse, getSidByUserId(uid));
			byte[] bytes = file.getBytes();
			String contentType = FileUtil.getDateName(file.getOriginalFilename());/// 获取文件后缀名
			if (FileUtil.isExcel(contentType)) {// 验证文件后缀名是否正确
//				String path = "/upload/" + FileUtil.getDateString() + type;
//				String uploadDir = request.getSession().getServletContext().getRealPath(path);
				String realPath = SysConfig.DISK_FOLDER;
				String fee_folder="";
				if(Integer.parseInt(type)==1){
					fee_folder="water";
				}else if(Integer.parseInt(type)==2){
					fee_folder="electricity";
				}else if(Integer.parseInt(type)==3){
					fee_folder="gas";
				}
				String path = "/upload/fee/"+fee_folder+"/"+ FileUtil.getDateString();
				String uploadDir = realPath+path;
				File dirPath = new File(uploadDir);
				if (!dirPath.exists()) {
					dirPath.mkdirs();
				}
				String sep = System.getProperty("file.separator");
				String pathimg = FileUtil.getFileName() + contentType;
				File uploadedFile = new File(uploadDir + sep + pathimg);
				FileCopyUtils.copy(bytes, uploadedFile);
				return utilitiesService.utilitiesInfo(uploadedFile.getPath(), type, getCommunity().getId());
			} else {
				return new ResponseMsg(1, "上传错误", null);
			}
		} catch (Exception e) {
			return new ResponseMsg(1, "上传文件出现错误", null);
		}
	}

	@RequiresPermissions("water")
	@RequestMapping(value = "/send")
	@ResponseBody
	public BaseResponse send(HttpServletRequest request, String date, String type) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			List<String> toUserIds = utilitiesService.getNeedMessageList(getCommunity().getId(), date, type);
			if (toUserIds.isEmpty()) {
				return new BaseResponse(1, "没有符合条件的用户");
			}
			String dates[] = date.split("-");
			String msg = messageSplice(type, dates);
			try {
				// rongService.sendMessage(userInfo.getId(), toUserIds, new
				// TxtMessage(message));

				// 融云发送消息
				// TxtMessage message=new TxtMessage(msg);//文本信息
				// applicationContext.publishEvent(new
				// RongMessageEvent(userInfo.getId(), toUserIds, message));

				// 推送
				PushBean pushBean = new PushBean();
				if ("1".equals(type)) {
					pushBean.setType(PushType.WATER_MSG);// 水费
				} else if ("2".equals(type)) {
					pushBean.setType(PushType.ELECTRICITY_MSG);// 电费
				} else if ("3".equals(type)) {
					pushBean.setType(PushType.GAS_MSG);// 燃气费
				}
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
	 * @Description  水费，电费，燃气费 线下缴费的逻辑
	 * @param orderNo
	 * @return BaseResponse
	 * $
	 */
	@RequiresPermissions("water")
	@RequestMapping(value="/offline",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse offline(String orderNo,Integer invoiceStatus,String actualAmount,String remark){
		try {
			long id = utilitiesService.offline(orderNo);
			Utilities oldRecord=utilitiesService.selectByPrimaryKey(id);
			Utilities record=new Utilities();
			record.setId(id);
			record.setInvoiceStatus(invoiceStatus);
			record.setRemark(remark);
			record.setActualAmount(new BigDecimal(actualAmount));
			record.setBalance(record.getActualAmount().subtract(oldRecord.getPayable()==null?BigDecimal.ZERO:oldRecord.getPayable()));//本期结余
			record.setPayDate(new Date());
			record.setUpdateDate(new Date());
			utilitiesService.updateByPrimaryKeySelective(record);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	//预缴
	@RequiresPermissions("water")
	@RequestMapping(value="/preAmount",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse preAmount(long id,String actualAmount,String remark){
		try {
			Utilities oldRecord=utilitiesService.selectByPrimaryKey(id);
			BigDecimal oldBalance=oldRecord.getBalance();
			BigDecimal actualAmountAdd=(oldBalance==null?BigDecimal.ZERO:oldBalance).add(new BigDecimal(actualAmount));
			Utilities record=new Utilities();
			record.setId(id);
			record.setRemark(remark);
			record.setActualAmount((oldRecord.getPayable()==null?BigDecimal.ZERO:oldRecord.getPayable()).add(actualAmountAdd));//实际缴费
			record.setBalance(record.getActualAmount().subtract(oldRecord.getPayable()==null?BigDecimal.ZERO:oldRecord.getPayable()));//本期结余
			utilitiesService.updateByPrimaryKeySelective(record);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("water")
	@RequestMapping(value="/updateInvoiceStatus",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse updateInvoiceStatus(long id){
		try {
			Utilities record=new Utilities();
			record.setId(id);
			record.setInvoiceStatus(1);
			utilitiesService.updateByPrimaryKeySelective(record);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("updateInvoiceStatus出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("water")
	@RequestMapping(value="/updateRemark",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse updateRemark(long id,String remark){
		try {
			Utilities record=new Utilities();
			record.setId(id);
			record.setRemark(remark);
			utilitiesService.updateByPrimaryKeySelective(record);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("updateRemark出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	
	@RequiresPermissions("water")
	@RequestMapping(value="/alonesend",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse alonesend(HttpServletRequest request,String date, String type,String orderNo){
		try {
			List<String> toUserIds = orderService.alonesendMessageUser(orderNo);
			if(toUserIds.isEmpty()){
				return new BaseResponse(1,"该房号下未找到注册用户");
			}
			String msg = messageSplice(type, date.split("-"));
			PushBean pushBean = new PushBean();
			if ("1".equals(type)) {
				pushBean.setType(PushType.WATER_MSG);// 水费
			} else if ("2".equals(type)) {
				pushBean.setType(PushType.ELECTRICITY_MSG);// 电费
			} else if ("3".equals(type)) {
				pushBean.setType(PushType.GAS_MSG);// 燃气费
			}
			pushBean.setRefId("");
			pushBean.setContent(msg);
			String[] array = new String[1];
			String[] toUserIdsArr = toUserIds.toArray(array);
			applicationContext.publishEvent(new PushMessageEvent(pushBean, "", toUserIdsArr, PushFrom.LINLE_USER));
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("单独通知出错了:"+e);
			return BaseResponse.ServerException;
		}
	}

	public String messageSplice(String type, String dates[]) {
		String messageType = "";
		if ("1".equals(type)) {
			messageType = "水费";
		} else if ("2".equals(type)) {
			messageType = "电费";
		} else if ("3".equals(type)) {
			messageType = "燃气费";
		}
		String str = "您的" + dates[0] + "年" + dates[1] + "月" + messageType + "账单已出，请您及时缴费";
		return str;
	}

}
