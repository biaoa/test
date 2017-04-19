package com.linle.community.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
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
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linle.IM.res.CommunityOnlineListResponse;
import com.linle.appIndexMenu.service.AppIndexMenuService;
import com.linle.commodityAppMenu.service.CommodityAppMenuService;
import com.linle.common.base.BaseController;
import com.linle.common.util.BigDecimalUtil;
import com.linle.common.util.ExcelToolkit;
import com.linle.common.util.FileUtil;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.common.util.StringUtil;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.communityPresident.service.CommunityPresidentService;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.AppIndexMenu;
import com.linle.entity.sys.CommodityAppMenu;
import com.linle.entity.sys.CommunityPresident;
import com.linle.entity.sys.RoomNo;
import com.linle.entity.sys.SysRegion;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.response.RoomCommonResponse;
import com.linle.propertyCompany.service.PropertyCompanyService;
import com.linle.roomno.service.RoomService;
import com.linle.system.service.RegionService;
import com.linle.user.model.CommunityOnlineIM;
import com.linle.user.model.CommunityUserIM;
import com.linle.user.model.IMUserInfoResponse;
@Controller
@RequestMapping("community")
public class CommunityController  extends BaseController{
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private PropertyCompanyService propertyCompanyService;
	
	@Autowired
	private CommunityPresidentService communityPresidentService;
	
	@Autowired
	private AppIndexMenuService appIndexMenuService;
	
	@Autowired
	private CommodityAppMenuService commodityAppMenuService;
	
	FileInputStream fis = null;
	
	@RequiresPermissions("community_manage")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model,String communityName) {
		Page<Community> page = new Page<Community>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		try {
			if(communityName!=null && !"".equals(communityName)){
				communityName=new String(communityName.getBytes("iso8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		params.put("communityName", communityName);
		Users user = getCurrentUser();
		if(user.getIdentity()==UserType.XQ){
			params.put("communityId",getCommunity().getId());
		}else if(user.getIdentity()==UserType.WY){
			params.put("propertyId", getPopertyCompany().getId());
		}else if(user.getIdentity()==UserType.SZ){
			params.put("presidentId", getCommunityPresident().getId());
		}
		page.setParams(params);
		try {
			communityService.findAllCommunity(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("communityName", communityName);
		model.addAttribute("pagelist", page);
		return "/community/community_list";
	}
	
	/**
	 * 跳转到添加小区的页面
	 * 
	 * @return
	 */
	@RequiresPermissions(value={"add_community","edit_community"},logical=Logical.OR)
	@RequestMapping(value = "/addCommunity", method = RequestMethod.GET)
	public ModelAndView addCommunity(Model model,Long id) {
		Community community = new Community();
		List<SysRegion> provinceList = regionService.getProvinces();
		List<SysRegion> cityList = new ArrayList<SysRegion>();
		List<SysRegion> countyList = new ArrayList<SysRegion>();
		if(id!=null){
			community = communityService.selectByPrimaryKey(id);
			cityList = regionService.getCitys(community.getSysRegion().getParent().getParent());
			countyList = regionService.getCountys(community.getSysRegion().getParent());
		}else{
			for (SysRegion province : provinceList) {
				cityList = regionService.getCitys(province);
				for (SysRegion city : cityList) {
					countyList = regionService.getCountys(city);
					break;
				}
				break;
			}
		}
		
		ModelAndView mv = new ModelAndView();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("cityList", cityList);
		model.addAttribute("countyList", countyList);
		model.addAttribute("popertyCompanyList", propertyCompanyService.getAllPropertyCompany());
		model.addAttribute("community", community);
		model.addAttribute("type", getCurrentUser().getIdentity().equals(UserType.XQ)?"xq":"other");
		mv.setViewName("community/community_add");
		return mv;
	}
	
	/**
	 * 添加小区信息
	 * 
	 * @return
	 */
	@RequiresPermissions(value={"add_community","edit_community"},logical=Logical.OR)
	@RequestMapping(value = "/addCommunity", method = RequestMethod.POST)
	@ResponseBody
	public  ResponseMsg addCommunity(@Valid Community company ,BindingResult errors, Model model) {
		if(company.getId()==null){
			CommunityPresident communityPresident = getCommunityPresident();
			company.setPresident(communityPresident);
			company.setLevel2Proxy(communityPresidentService.getlevel2Proxy(communityPresident).getId());
			company.setLevel1Proxy(communityPresidentService.getlevel1Proxy(communityPresident).getId());
			company.setSysId(Long.valueOf(1));
			if (communityService.addCommunity(company)) {
				//更新社长session中小区列表信息
				if(getCurrentUser().getIdentity()==UserType.SZ){
					Subject subject = SecurityUtils.getSubject();
					Session session = subject.getSession();
					List<Community> communityList = communityService.getCommunityByPresident(getCommunityPresident().getId());
					session.setAttribute("communityList", communityList);
					session.setAttribute("selectedCommunity", !communityList.isEmpty()?communityList.get(0).getId():0);
				}
				return new ResponseMsg(0, "新增成功", null);
			}else{
				return new ResponseMsg(0, "新增失败", null);
			}
		}else{
			communityService.updateByPrimaryKeySelective(company);
			return new ResponseMsg(0, "修改成功", null);
		}
	}
	
	
    
    /**
	 * 跳转到导入房号页面
	 * 
	 * @return
	 */
	@RequiresPermissions("room_no")
	@RequestMapping(value = "/room")
	public ModelAndView room(Model model,Integer pageNo,String buildingType,String unitType,String roomSearch) {
		ModelAndView mv = new ModelAndView();
		Page<RoomNo> page = new Page<RoomNo>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		//查询条件分装到这个map里面去
		long communityId=getCommunity().getId();
		params.put("community_id", communityId);
		params.put("buildingType", buildingType);
		params.put("unitType", unitType);
		params.put("roomSearch", roomSearch);
		page.setParams(params);
		try {
			roomService.getAllData(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		//搜索相关
		model.addAttribute("buildingList", roomService.getBuildForAPI(communityId));//该小区所有幢
//		model.addAttribute("unitList", page);//所有单元
		model.addAttribute("buildingType", buildingType);//幢
		model.addAttribute("unitType", unitType);//单元
		model.addAttribute("roomSearch", roomSearch);//房号
		
		mv.setViewName("community/room");
		return mv;
	}
	
	 /**
	 * 小区用户列表
	 * 
	 * @return
	 */
	@RequiresPermissions("community_user")
	@RequestMapping(value = "/communityUsers")
	public String userList(Model model,Integer pageNo,String sex,String searchValue,String buildingType,
			String unitType,String roomSearch,String beginDate,String endDate) {
		try {
			Page<Users> page = new Page<Users>();
			Map<String, Object> params = new HashMap<String, Object>();
			if(pageNo!=null){
				page.setPageNo(pageNo);
			}
			if(StringUtil.isNotNull(beginDate)){
				params.put("beginDate", beginDate);
			}
			if (StringUtil.isNotNull(endDate)) {
				params.put("endDate", endDate);
			}
			//查询条件分装到这个map里面去
			long communityId=getCommunity().getId();
			params.put("community_id", communityId);
			params.put("buildingType", buildingType);
			params.put("unitType", unitType);
			params.put("roomSearch", roomSearch);
			params.put("searchValue", searchValue);
			params.put("sex", sex);
			page.setParams(params);
			page.setResults(userInfoService.getCommunityUsers(page));
			
			model.addAttribute("pagelist", page);
			//搜索相关
			model.addAttribute("buildingList", roomService.getBuildForAPI(communityId));//该小区所有幢
			model.addAttribute("buildingType", buildingType);//幢
			model.addAttribute("unitType", unitType);//单元
			model.addAttribute("roomSearch", roomSearch);//房号
			model.addAttribute("searchValue", searchValue);
			model.addAttribute("sex", sex);
			model.addAttribute("beginDate", beginDate);
			model.addAttribute("endDate", endDate);
		} catch (Exception e) {
			e.printStackTrace(); 
			_logger.error("出错了", e);
		}
		return "community/communityUser_list";
	}
		
	 //2.加载某小区某幢所有单元
	@ResponseBody
	@RequestMapping(value = "/loadUnitName", method = RequestMethod.POST)
	public BaseResponse getUnitName(String building) {
		try {
			long communityId=getCommunity().getId();
			RoomCommonResponse res = new RoomCommonResponse();
			res.setList(roomService.getUnitForAPI(communityId,building));
			res.setCode(0);
			res.setMsg("成功");
			return res;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@ResponseBody
	@RequiresPermissions("room_no")
	@RequestMapping(value = "/delRoom", method = RequestMethod.POST)
	public BaseResponse delRoom(Long id){
		try {
			roomService.deleteByPrimaryKey(id);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	// to修改页面
	@RequiresPermissions("room_no")
	@RequestMapping(value = "/toUpdateRoom", method = RequestMethod.GET)
	public ModelAndView addrepairType(Model model, Long id) {
		ModelAndView mv = new ModelAndView();
		RoomNo roomNo = new RoomNo();
		if (id != null) {
			roomNo = roomService.selectByPrimaryKey(id);
		}
		model.addAttribute("roomNo", roomNo);
		mv.setViewName("community/room_add");
		return mv;
	}
	
	// 房号操作
	@RequiresPermissions("room_no")
	@RequestMapping(value = "/saveRoom", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg saveRoom(@Valid RoomNo roomNo,  Model model) {
		try {
			long communityId=getCommunity().getId();
			String overall=roomNo.getBuilding()+"-"+roomNo.getUnit()+"-"+roomNo.getRoom();
			//去数据库查，若有同幢同单元同房号的记录，则不做导入
			int countRoom=roomService.countRomeByOverall(overall,communityId);
			if(countRoom>0){//有相同房地址
				return new ResponseMsg(2, "已经有相同房地址："+overall, null);
			}
			roomNo.setOverall(overall);
			
			if (roomNo.getId() != null) {//编辑修改
				roomService.updateByPrimaryKeySelective(roomNo);
				return new ResponseMsg(0, "操作成功", null);
			}else{//新增
				roomNo.setCommunityId(communityId);
				if (roomService.insertSelective(roomNo) > 0) {
					return new ResponseMsg(0, "操作成功", null);
				}
			}
			
			
			return new ResponseMsg(1, "操作失败", null);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1, "操作失败", null);
		}

	}
	
	//批量生成房号
	@RequiresPermissions("room_no")
	@RequestMapping(value = "/insertBatchRooms", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg insertBatchRooms(String jsonStr,Model model) {
		try {
			ResponseMsg res=roomService.insertBatchRooms(jsonStr,getCommunity().getId());
			return res;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1, "操作失败", null);
		}

	}
	
	@ResponseBody
	@RequestMapping(value = "/doexcel")
	public ResponseMsg fileupload(@RequestParam(value = "file") MultipartFile file,
			String type, String reportCode,HttpServletRequest servletRequest, HttpServletResponse servletResponse,Long uid) throws Exception {
		processSidCookie(servletRequest, servletResponse, getSidByUserId(uid));
		byte[] bytes = file.getBytes();
		String contentType =FileUtil.getDateName(file.getOriginalFilename());/// 获取文件后缀名
		if (FileUtil.isExcel(contentType)) {// 验证文件后缀名是否正确
			String path = "/upload/" + FileUtil.getDateString();
			String uploadDir = request.getSession().getServletContext().getRealPath(path);
			File dirPath = new File(uploadDir);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			String sep = System.getProperty("file.separator");
			String pathimg = FileUtil.getFileName() + contentType;
			File uploadedFile = new File(uploadDir + sep + pathimg);
			FileCopyUtils.copy(bytes, uploadedFile);
			return roomInfo(uploadedFile.getPath());
		} else {
			return new ResponseMsg(1, "上传错误", null);
		}
	}
	

	private ResponseMsg roomInfo(String path) throws JsonProcessingException {
		List<RoomNo> list = new ArrayList<>();
		int count=0;
		String eqRoomStr="";
		try {
			fis = new FileInputStream(path);
			ExcelToolkit<RoomNo> util = new ExcelToolkit<RoomNo>(RoomNo.class);
			 list = util.importExcel("", fis, 1, 0);
			for (RoomNo room : list) {
				String overall=room.getBuilding()+"-"+room.getUnit()+"-"+room.getRoom();
				//去数据库查，若有同幢同单元同房号的记录，则不做导入
				int countRoom=roomService.countRomeByOverall(overall,getCommunity().getId());
				if(countRoom>0){//有相同房地址
					eqRoomStr=eqRoomStr+overall+";";
					continue;
				}
				room.setCommunityId(getCommunity().getId());
				room.setOverall(overall);
				_logger.debug(m.writeValueAsString(room));
				boolean b = roomService.insertSelective(room)>0;
				if (!b) {
					count++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return new ResponseMsg(1, "上传文件出现错误", null);
		}
		if(!"".equals(eqRoomStr)){//有相同房地址没有上传
			_logger.debug("相同房地址不可以上传:"+m.writeValueAsString(eqRoomStr));
			return new ResponseMsg(0, "相同房地址不可以上传:"+eqRoomStr, null);
		}
		return new ResponseMsg(0, "共:"+list.size()+"失败:"+count, null);
	}
	
	//获得用户信息
	@RequestMapping("/userInfo")
	@ResponseBody
	public BaseResponse userList(Long userId) throws JsonProcessingException{
		try {
			Users userinfo = userInfoService.getById(userId);
			CommunityUserIM imvo = new CommunityUserIM();
			imvo.setName(userinfo.getUserName());
			imvo.setId(userinfo.getId());
			imvo.setPortraitUri(userinfo.getLogo()==null?"/resources/images/default_user.png":userinfo.getLogo());
			IMUserInfoResponse res = new IMUserInfoResponse();
			res.setCode(0);
			res.setMsg("获取成功");
			res.setData(imvo);
			return res;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
		//
//		Users user  =getCurrentUser();
//		CommunityUserListResponse res = new CommunityUserListResponse();
//		List<CommunityUserIM> list = new ArrayList<>();
//		if(user.getIdentity()==UserType.SZ || user.getIdentity()==UserType.XQ){
//			CommunityUserIM im = new CommunityUserIM();
//			im.setId(Long.valueOf(1));
//			im.setName("系统");
//			im.setPortraitUri("/resources/images/default_user.png");
//			list.add(im);
//		}
//		List<CommunityUserIM> clist =  userInfoService.selectCommunityUserList(getCommunity().getId());
//		clist.addAll(list);
//		res.setUserlist(clist);
//		return res;
	}
	
	@RequestMapping("/online")
	@ResponseBody
	public CommunityOnlineListResponse online() throws JsonProcessingException{
		CommunityOnlineListResponse res = new CommunityOnlineListResponse();
		CommunityOnlineIM im = new CommunityOnlineIM();
		//获得该小区下的所有用户
		im.setId(Long.valueOf(1));
		im.setStatus(true);
		List<CommunityOnlineIM> list = new ArrayList<>();
		list = userInfoService.selectOnlineUserByCommunityId(getCommunity().getId());
		list.add(im);
		res.setData(list);
		return res;
	}
	
	@RequiresPermissions("community_menu_setting")
	@RequestMapping(value="/setAppMenu/{id}",method=RequestMethod.GET)
	public String setAppMenu(@PathVariable Long id,Model model){
		Community community = communityService.selectByPrimaryKey(id);
		List<AppIndexMenu> menuList = appIndexMenuService.getAllMenu();
		List<CommodityAppMenu> appMenuList  = commodityAppMenuService.getCommodityAppMenu(id);
		for (AppIndexMenu appIndexMenu : menuList) {
			for (CommodityAppMenu commodityAppMenu : appMenuList) {
				if(commodityAppMenu.getMenuId().equals(appIndexMenu.getId())){
					appIndexMenu.setChecked(1);
					appIndexMenu.setSort(commodityAppMenu.getSort());
				}
			}
		}
		model.addAttribute("community", community);
		model.addAttribute("menuList", menuList);
		model.addAttribute("commodityAppMenu", appMenuList);
		return "community/community_app_menu";
	}
	
	@RequiresPermissions("community_menu_setting")
	@RequestMapping(value="/setAppMenu",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse setCommunityAppMenu(Long id,String menus,String sorts){
		commodityAppMenuService.delCommodityAppMenu(id);
		commodityAppMenuService.addCommodityAppMenu(menus, id, sorts, getCurrentUser().getId());
		return BaseResponse.OperateSuccess;
	}
	
	@RequestMapping(value="/setCollaborate",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse setCollaborate(Long id,int collaborateFlag){
		try {
			if(id==null || id==0 || collaborateFlag<0 || collaborateFlag>1){
				return new BaseResponse(1,"参数错误");
			}
			collaborateFlag = collaborateFlag==0?1:0;
			Community community = new Community();
			community.setId(id);
			community.setCollaborateFlag(collaborateFlag);
			communityService.updateByPrimaryKeySelective(community);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("设置小区合作出错了:"+e);
			return BaseResponse.ServerException;
		}
	}
	//设置小区提现手续费
	@RequiresPermissions("community_withdrawalFee_setting")
	@RequestMapping(value="withdrawalFeeSetting",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse setWithdrawalFee(Long id,BigDecimal withdrawalFee){
		try {
			//不能比0小并且不能比1大
			if(BigDecimalUtil.compareTo(withdrawalFee, BigDecimal.ZERO)==-1 && BigDecimalUtil.compareTo(withdrawalFee, BigDecimal.ONE)==1){
				return new BaseResponse(1, "手续费设置错误");
			}
			Community community = new Community();
			community.setId(id);
			community.setWithdrawalFee(withdrawalFee);
			boolean modifyisok = communityService.updateWithdrawalFee(community);
			return modifyisok?BaseResponse.OperateSuccess:BaseResponse.OperateFail;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("设置提现手续费出错:"+e);
			return BaseResponse.ServerException;
		}
	}
}
