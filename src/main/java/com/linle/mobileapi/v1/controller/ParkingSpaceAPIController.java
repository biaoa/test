package com.linle.mobileapi.v1.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.entity.sys.Garage;
import com.linle.entity.sys.SpaceRecord;
import com.linle.entity.sys.SysOrder;
import com.linle.entity.sys.Users;
import com.linle.garage.service.GarageService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.ParkingStopRequest;
import com.linle.parkingspace.service.ParkingSpaceService;
import com.linle.spaceRecord.service.SpaceRecordService;
import com.linle.sysOrder.service.SysOrderService;

/**
 * 
* @ClassName: ParkingSpaceAPIController 
* @Description: 车位相关
* @author pangd
* @date 2016年4月7日 下午2:24:29 
*
 */
@Controller
@RequestMapping("/api/1")
public class ParkingSpaceAPIController extends BaseController{
	
	@Autowired
	private ParkingSpaceService parkingSpaceService;
	
	@Autowired
	private GarageService garageService;
	
	@Autowired
	private SpaceRecordService spaceRecordService;
	
	@Autowired
	private SysOrderService orderService;
	
	//车位图
    @RequestMapping(value = "/parkingSpace_appointment", method = RequestMethod.GET)
    public String parkingSpaceAppointment(String sid, HttpServletRequest servletRequest, HttpServletResponse servletResponse,Model model) throws Throwable {
    	try {
    		processSidCookie(servletRequest, servletResponse, sid);
    		validatorSid(sid);
    		 Subject subject = SecurityUtils.getSubject();
    		 if (subject.isAuthenticated()) {
    			 List<Garage> garageList = garageService.getGarageList(getUserCommunity().getId());
    	    		if (!garageList.isEmpty()) {
    	    			model.addAttribute("garageList", garageList);
    	    			model.addAttribute("garage", garageList.get(0));
    				}else{
    					//这里要返回 没有车库的页面
    					return "error/nodata";
    				}
    	    		return "/app/parking";
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
    	return "/error/tologin";
    }
    
	@ResponseBody
	@RequestMapping(value="/saveSpaceOrder",method=RequestMethod.POST)
	public BaseResponse setParkingSpace(Long gid,String spaceid,String select_space,String price_list,String total,String type,String dateType,String orderType){
		try {
			return parkingSpaceService.saveOrder(gid,spaceid,select_space, price_list,total,type,dateType,orderType);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new BaseResponse(1,"服务异常");
		}
	}
    
	//车位续费
    @RequestMapping(value = "/parking_renew", method = RequestMethod.GET)
    public String parkingRenew(String sid, HttpServletRequest servletRequest, HttpServletResponse servletResponse,Model model) throws Throwable {
    	try {
    		processSidCookie(servletRequest, servletResponse, sid);
    		validatorSid(sid);
    		 Subject subject = SecurityUtils.getSubject();
    		 if (subject.isAuthenticated()) {
    			Users user = getCurrentUser();
    			List<SpaceRecord> spaceList = spaceRecordService.getSpaceList(user.getId());
    			System.out.println(spaceList.size());
    	    		if (!spaceList.isEmpty()) {
    	    			model.addAttribute("spaceList", spaceList);
    	    			model.addAttribute("spaceRecode", spaceList.get(0));
    	    			return "/app/parkingRenew";
    				}else{
    					//这里要返回 没有车库的页面
    					return "error/nodata";
    				}
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
    	return "/error/tologin";
    }
    
    
	//车位申停
    @RequestMapping(value = "/parking_stop", method = RequestMethod.GET)
    public String toParkingStop(String sid, HttpServletRequest servletRequest, HttpServletResponse servletResponse,Model model) {
    	try {
    		processSidCookie(servletRequest, servletResponse, sid);
				validatorSid(sid);
    		 Subject subject = SecurityUtils.getSubject();
    		 if (subject.isAuthenticated()) {
    			Users user = getCurrentUser();
    			//FIXME 从session中取出来 获得其他属性报错了
//    			_logger.info(m.writeValueAsString(user));
//    			_logger.info(m.writeValueAsString(user.getAddressDetails().getOverall()));
    			user = userInfoService.findUserInfoByUserName(user.getUserName());
    			List<SpaceRecord> spaceList = spaceRecordService.getSpaceListforStop(user.getId());
    	    		if (!spaceList.isEmpty()) {
    	    			model.addAttribute("spaceList", spaceList);
    	    			model.addAttribute("spaceRecode", spaceList.get(0));
    	    			model.addAttribute("overall", user.getAddressDetails().getOverall());
    	    			return "/app/parkingStop";
    				}else{
    					//这里要返回 没有车库的页面
    					return "error/nodata";
    				}
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		} catch (Throwable e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
    	return "/error/tologin";
    }
    
    @ResponseBody
    @RequestMapping(value = "/parking_stop", method = RequestMethod.POST)
    public BaseResponse parkingStop(ParkingStopRequest req, HttpServletRequest servletRequest, HttpServletResponse servletResponse,Model model) {
    	try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
			 boolean isok = spaceRecordService.parkingStop(req.getSpaceRecordId());
			 _logger.info("车位申停结果" + isok);
			}else{
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			_logger.info("车位申停报错" + e.getMessage());
			return BaseResponse.ServerException;
		}
    	return BaseResponse.OperateSuccess;
    }
    
	@ResponseBody
	@RequestMapping(value="/saveParkingStopOrder",method=RequestMethod.POST)
	public BaseResponse saveParkingStopOrder(Long grageId,String spaceid,String select_space,String spaceRecordId,String dateType,String orderNo){
		try {
			
			SysOrder order =  orderService.getOrderByOrderNo(orderNo,getCurrentUser().getId());
			//select_space
			parkingSpaceService.saveParkingStopOrder(grageId,spaceid,select_space,spaceRecordId,order.getTotalMoney().toString(),dateType);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
    
}
