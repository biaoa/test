package com.linle.parkingspace.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.StringUtil;
import com.linle.entity.sys.ParkingSpace;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.parkingspace.service.ParkingSpaceService;

/**
 * 
* @ClassName: ParkingSpaceAPIController 
* @Description: 车位信息
* @author pangd
* @date 2016年4月6日 上午9:56:01 
*
 */
@Controller
@RequestMapping("/parkingSpace")
public class ParkingSpaceController extends BaseController {
	
	@Autowired
	private ParkingSpaceService parkingSpaceService;
	
	@ResponseBody
	@RequestMapping(value="/addspace",method=RequestMethod.POST)
	public BaseResponse addspace(@Valid ParkingSpace space){
		try {
			space.setSpace(space.getSpace().replace("c", "_"));
			space.setCreateDate(new Date());
			space.setSpaceCount(StringUtil.charInStringCount(space.getSpace(), "x"));
			parkingSpaceService.insertSelective(space);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		return new BaseResponse(0,"设置成功");
	}
	
	@ResponseBody
	@RequestMapping(value="/getParkingSpace",method=RequestMethod.POST)
	public BaseResponse getParkingSpace(Long id){
		try {
			Map<String, Object> map = new HashMap<>();
			ParkingSpace space = parkingSpaceService.selectByGarageId(id);
			map.put("parkingSpace", space);
			return new BaseResponse(0,"获取成功",map);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new BaseResponse(1,"服务异常");
		}
		
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/setParkingSpace",method=RequestMethod.POST)
	public BaseResponse setParkingSpace(@Valid ParkingSpace space){
		try {
			boolean isok = parkingSpaceService.updateByPrimaryKeySelective(space)>0;
			_logger.info(getCurrentUser().getUserName()+"设置车位的结果是:"+isok);
			return new BaseResponse(0,"设置成功");	
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new BaseResponse(1,"服务异常");
		}
	}
}

