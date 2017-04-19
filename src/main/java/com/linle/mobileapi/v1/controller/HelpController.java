package com.linle.mobileapi.v1.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.common.base.BaseController;
import com.linle.common.util.LimitUtil;
import com.linle.common.util.SMSutil;
import com.linle.common.util.SysConfig;
import com.linle.communityConvention.model.CommunityConvention;
import com.linle.communityConvention.service.CommunityConventionService;
import com.linle.communityEmployee.model.CommunityEmployeeListResponse;
import com.linle.communityEmployee.service.CommunityEmployeeService;
import com.linle.communitySuggestions.service.CommunitySuggestionsService;
import com.linle.communitySuggestionsComment.model.CommunitySuggestionsComment;
import com.linle.communitySuggestionsComment.service.CommunitySuggestionsCommentService;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.CommunityEmployee;
import com.linle.entity.sys.CommunitySuggestions;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.CommunitySuggestionsRequest;
import com.linle.mobileapi.v1.request.GetAdviceRequest;
import com.linle.mobileapi.v1.request.SuggestionsCommentOperateRequest;
import com.linle.mobileapi.v1.request.UniversalTelephoneRequest;
import com.linle.mobileapi.v1.request.VersionRequest;
import com.linle.mobileapi.v1.response.CommunitySuggestionsResponse;
import com.linle.mobileapi.v1.response.GetAdviceResponse;
import com.linle.mobileapi.v1.response.SuggestionsCommentResponse;
import com.linle.mobileapi.v1.response.UniversalTelephoneResponse;
import com.linle.mobileapi.v1.response.VersionResponse;
import com.linle.problemType.model.ProblemType;
import com.linle.problemType.service.ProblemTypeService;
import com.linle.serviceAgreement.model.ServiceAgreement;
import com.linle.serviceAgreement.service.ServiceAgreementService;
import com.linle.sms.service.SmsService;
import com.linle.universalTelephone.service.UniversalTelephoneService;
import com.linle.versionManager.service.VersionManagerService;



@Controller
@RequestMapping("/api/1")

public class HelpController extends BaseController {

	@Autowired
	private UniversalTelephoneService universalTelephoneService;

	@Autowired
	private ProblemTypeService problemTypeService;

	@Autowired
	private CommunityEmployeeService communityEmployeeService;
	@Autowired
    private ServiceAgreementService serviceAgreementService;
	@Autowired
	private VersionManagerService versionManagerService;
//	@Autowired
//	private CacheManager cacheManager;

	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private CommunityConventionService ConventionService;
	
	// 常见问题
	@RequestMapping(value = "/common_problem") // method = RequestMethod.GET
	public String commonProblem(String sid,HttpServletRequest servletRequest, HttpServletResponse servletResponse, Model model) {
		// System.out.println(servletRequest.getSession().getServletContext().getRealPath("/")
		// + "WEB-INF\\classes\\rsa_private_key.pem");
		// System.out.println(servletRequest.getSession().getServletContext());
		// System.out.println(servletRequest.getContextPath());
		// System.out.println(servletRequest.getScheme() + "://" +
		// servletRequest.getServerName() + ":"
		// + servletRequest.getServerPort() + servletRequest.getContextPath() +
		// "/");
		try {
			if(sid!=null){
				try {
	    			processSidCookie(servletRequest, servletResponse, sid);
					validatorSid(sid);
				} catch (Throwable e) {
					e.printStackTrace(); _logger.error("出错了", e);
				}
			}
			HashMap<String, Object> map = new HashMap<>();
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				if (user.getIdentity().equals(UserType.SJ)) {
					map.put("belongTo", 1);// 商家
				}else if(user.getIdentity().equals(UserType.XQ)||user.getIdentity().equals(UserType.XQBMFZR)||user.getIdentity().equals(UserType.XQPTYG)){
					map.put("belongTo", 2);//物业版社区版
				} else {
					map.put("belongTo", 0);
				}
			} else {
				map.put("belongTo", 0);// 没登陆或没传，默认是用户
			}
			List<ProblemType> problemTypeList = problemTypeService.getAllTypeAndData(map);
			model.addAttribute("problemTypeList", problemTypeList);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
			return "error/500";
		}
		return "/app/common_question";
	}

	// 用户协议
	@RequestMapping(value = "/service_agreement", method = RequestMethod.GET)
	public String serviceAgreement(String sid,HttpServletRequest servletRequest, HttpServletResponse servletResponse, Model model) {
		try {
			if(sid!=null){
				try {
	    			processSidCookie(servletRequest, servletResponse, sid);
					validatorSid(sid);
				} catch (Throwable e) {
					e.printStackTrace(); _logger.error("出错了", e);
				}
			}
			HashMap<String, Object> map = new HashMap<>();
			Subject subject = SecurityUtils.getSubject();
			if(subject.isAuthenticated()){
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				if(user.getIdentity().equals(UserType.SJ)){
					map.put("typeId", 1);
				}else if(user.getIdentity().equals(UserType.XQ)||user.getIdentity().equals(UserType.XQBMFZR)||user.getIdentity().equals(UserType.XQPTYG)){
					map.put("typeId", 2);
				}else{
					map.put("typeId", 0);
				}
			}else {
				map.put("typeId", 0);// 没登陆或没传，默认是用户
			}
			ServiceAgreement serviceAgreement  = serviceAgreementService.selectByTypeId(map);
			model.addAttribute("serviceAgreement", serviceAgreement);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return "error/500";
		}
		return "/serviceAgreement/serviceAgreement_detail";
	}
	
	//检查是否更新版本
	@ResponseBody
	@RequestMapping(value = "/checkVersion",method = RequestMethod.POST)
	public BaseResponse check_version(@Valid VersionRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse, Model model) {
		VersionResponse res=new VersionResponse();
		try {
			res=versionManagerService.checkVersion(req.getSoftwareFlag(),req.getDeviceType(), req.getCurrVersion());
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		return res;
	}
	
	@RequestMapping(value = "/to_app_shard",method = RequestMethod.GET)
	public String weixin_shard(HttpServletRequest servletRequest, Model model) {
		return "/appShard/app_shard";
	}
	
	// 测试
	@RequestMapping(value = "/sendInfoSms") // method = RequestMethod.GET
	public void sendInfoSms(HttpServletRequest servletRequest, Model model) {
		try {
			String phone="15972950646";
			String code="";
			String	templateCode = "SMS_17680100";
			String param = "";
			String	smsFreeSignName = "邻乐社区";
			int	smsType =0;
			SMSutil.sendMsg(smsFreeSignName, param, phone, templateCode);
//			String	templteCode = "SMS_5620214";
//			String	param = "{'code':'123444','product':'邻乐社区'}";
//			String	smsFreeSignName = "变更验证";
//			int	smsType = 2;
//			boolean sendResult = smsService.sendCode(smsFreeSignName, param, phone, templteCode, code, smsType);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
	}
	
	//获取可查看员工
	@ResponseBody
	@RequestMapping(value = "/getCommunityEmployeeListByUid")
	public BaseResponse getCommunityEmployeeListByUid( HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		CommunityEmployeeListResponse res = new CommunityEmployeeListResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				Map<String, Object> map = new HashMap<>();
				map.put("identity", user.getIdentity().getCode());
				if(user.getIdentity().equals(UserType.XQPTYG)){//普通员工--小区员工
					map.put("u_id", user.getId());
				}else if(user.getIdentity().equals(UserType.XQBMFZR)){//部门负责人-- 小区员工
					CommunityEmployee emp=communityEmployeeService.selectByUid(user.getId());
					if(emp!=null){
						map.put("departmentId",emp.getDepartmentId());
					}
				}else if(user.getIdentity().equals(UserType.XQ)){//小区账号
					map.put("communityId", getCommunity().getId());
				}else{
					return new BaseResponse(1,"此账号无法查询");
				}
				
				res.setData(communityEmployeeService.getCommunityEmployeeListPrvlg(map));
				res.setCode(0);
				res.setMsg("获取成功");
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			return BaseResponse.ServerException;
		}
		return res;
	}
	
	@RequestMapping(value = "/getUniversalTelephone", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse getUniversalTelephone(UniversalTelephoneRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				UniversalTelephoneResponse res = new UniversalTelephoneResponse();
				res.setCode(0);
				res.setMsg("获取成功");
				res.setData(universalTelephoneService.getAllUniversalTelephoneForAPI());
				return res;
			} else {
				return BaseResponse.PleaseSignIn;
			}
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			_logger.error("获得万能电话出错" + e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequestMapping(value="/getCommunityConvention")
	public String communityConvention(Model model, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse){
		Long id =1L;
		CommunityConvention convention = ConventionService.selectByPrimaryKey(id);
		model.addAttribute("convention", convention);
		return "communityConvention/communityConvention";
	}
}
