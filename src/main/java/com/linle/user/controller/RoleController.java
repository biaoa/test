package com.linle.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linle.common.util.Page;
import com.linle.common.util.ReaderPermissionConfig;
import com.linle.common.util.ResponseMsg;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.Permission;
import com.linle.entity.sys.RolePermissionRelation;
import com.linle.entity.sys.UserRole;
import com.linle.entity.sys.Users;
import com.linle.user.base.BaseUserController;
import com.linle.user.model.AsynResponse;
import com.linle.user.service.RolePermissionService;
import com.linle.user.service.UserRoleService;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月14日
 **/
@RequestMapping("/sys")
@Controller
public class RoleController extends BaseUserController{
	
	@Autowired
	private UserRoleService userRoleService;
	@Autowired 
	private RolePermissionService rolePermissionService;
	
	/**
	 * 角色列表
	 * @param model
	 * @return
	 */
	
	@RequiresPermissions("system_userRoleManage")
	@RequestMapping(value = "/rolelist", method = RequestMethod.GET)
	public ModelAndView userList(Integer pageNo, Model model) {
		ModelAndView mv = new ModelAndView();
		Page<UserRole> page = new Page<UserRole>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageNo!=null){
			page.setPageNo(pageNo);
		}
		//查询条件分装到这个map里面去
		params.put("available", true);
		params.put("suorceType", getCurrentUser().getIdentity());
		params.put("sourceId", 0);
		
		page.setParams(params);
		try {
			userRoleService.findRolesForPage(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		mv.setViewName("system/role_list");
		return mv;
	}
	
	/**
	 * to角色添加
	 * 
	 * @return
	 */
	@RequiresPermissions("system_userRoleManage")
	@RequestMapping(value = "/addRolePage", method = RequestMethod.GET)
	public ModelAndView addRolePage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/role_add");
		return mv;
	}
	/**
	 * 角色添加
	 * 
	 * @return
	 */
	@RequiresPermissions("system_userRoleManage")
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public @ResponseBody ResponseMsg addRole(@Valid UserRole userRole,BindingResult errors, Model model) {
		userRole.setSourceType(getCurrentUser().getIdentity());
		if (userRoleService.addRole(userRole)) {
			return new ResponseMsg(0, "新增成功", null);
		}
		return new ResponseMsg(1, "新增失败", null);
		
	}
	
	/**
	 * 删除角色
	 * @param userRole
	 * @return
	 */
	@RequiresPermissions("system_userRoleManage")
	@RequestMapping(value = "/delRole/{roleId}", method = RequestMethod.POST)
	public @ResponseBody
	ResponseMsg delRole(@PathVariable Long roleId) {
		if(roleId!=null&&userRoleService.delRoleById(roleId)>0){
			return new ResponseMsg(0, "删除成功", null);
		}
		return new ResponseMsg(1, "删除失败", null);
	}
	
	/**
	 * 权限配置
	 * @param model
	 * @return
	 */
	@RequiresPermissions("system_userRoleManage")
	@RequestMapping(value = "/permisssion", method = RequestMethod.GET)
	public ModelAndView permisssion(Long roleId, Model model) {
		ModelAndView mv = new ModelAndView();
		List<Permission> filePermissions = (List<Permission>) ReaderPermissionConfig.readerConfig().getPermissions();
		//默认权限树从配置文件读取
		List<Permission> pagePermissions = filePermissions;
		List<RolePermissionRelation> rolePermissions = rolePermissionService.loadRolePermissionRelationByRoleId(roleId);
		Users cUser = getCurrentUser();
		//根据角色构建对应权限树
		if(cUser.getIdentity()==UserType.JM){
			pagePermissions = initPagePermission(filePermissions,userRoleService.getRoleByename("center:admin").getId());
		}else if(cUser.getIdentity()==UserType.JM){
			pagePermissions = initPagePermission(filePermissions,userRoleService.getRoleByename("incubator:admin").getId());
		}else if(cUser.getIdentity()==UserType.JM){
			pagePermissions = initPagePermission(filePermissions,userRoleService.getRoleByename("resource:admin").getId());
		}
		model.addAttribute("permissionList", initPermissionChecked(pagePermissions,rolePermissions));
		model.addAttribute("roleId", roleId);
		mv.setViewName("system/permission");
		return mv;
	}
	@RequiresPermissions("system_userRoleManage")
	@RequestMapping(value = "/updateRolePermission", method = RequestMethod.POST)
	ModelAndView updateRolePermission(Long roleId, String enameStr) {
		ModelAndView mv = new ModelAndView();
		if(rolePermissionService.addRolePermissions(roleId, enameStr)){
			mv.setViewName("redirect:/sys/rolelist");		
		}
		return mv;
	}
	
	/**
	 * 异步检查是否已经存在
	 * @param ename
	 * @return
	 */
	@RequestMapping(value = "/checkRoleExsit", method = RequestMethod.POST)
	public @ResponseBody
	AsynResponse checkRoleExsit(String ename) {
		boolean flag = true;
		if(ename!=null&&!"".equals(ename)){
			if(userRoleService.checkRoleExsit(ename)){
				flag = false;
			}
		}
		return new AsynResponse(flag);
	
	}
	
	
	
	
	/**
	 * 初始化选择项
	 * @param permissionList
	 * @param rolePermissionRelation
	 * @return
	 */
	private List<Permission> initPermissionChecked(List<Permission> permissionList, List<RolePermissionRelation> rolePermissionRelation){
		for (Permission permission : permissionList) {
			for (RolePermissionRelation per : rolePermissionRelation) {
				if(permission.getEname().equals(per.getPermissionEname())){
					permission.setChecked(true);
					break;
				}
			}
			if(permission.getSubpermissions()!=null && permission.getSubpermissions().size()>0){
				permission.setSubpermissions(initPermissionChecked(permission.getSubpermissions(), rolePermissionRelation));
			}
			
		}
		return permissionList;
	}
	/**
	 * 根据角色初始化权限树
	 * @param permissionList
	 * @param rolePermissionRelation
	 * @return
	 */
	private List<Permission> initPagePermission(List<Permission> permissionList, Long roleId){
		List<Permission> permissionList1 = new ArrayList<Permission>();
		for(Permission permission:permissionList){
			boolean result = isExstes(permission.getEname(), roleId);  //如果配置了某个权限
			if(result){
				if(permission.getSubpermissions() != null && permission.getSubpermissions().size() > 0){//权限下的子权限
					List<Permission> permissionList2 = new ArrayList<Permission>();
					for(Permission sub:permission.getSubpermissions()){
						if(this.isExstes(sub.getEname(), roleId)){
							permissionList2.add(sub);
						}
					}
					permission.setSubpermissions(permissionList2);
				}
				permissionList1.add(permission);
			}
		}
		return permissionList1;
	}
	/**
	 * 查询某个角色是否有某个权限
	 * @param ename
	 * @param roleId
	 * @return
	 */
	private boolean isExstes(String ename, Long roleId) {
		List<RolePermissionRelation> rolePermissionList = rolePermissionService.loadRolePermissionRelationByRoleId(roleId);
			for (RolePermissionRelation rolePermissionRelation : rolePermissionList) {
				if(ename.equals(rolePermissionRelation.getPermissionEname())){
					return true;
				}
			}
			return false;
	}

}
