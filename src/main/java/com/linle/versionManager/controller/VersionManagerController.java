package com.linle.versionManager.controller;

import java.util.Date;

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
import com.linle.mobileapi.base.BaseResponse;
import com.linle.versionManager.model.VersionManager;
import com.linle.versionManager.service.VersionManagerService;

/**
 * 
 * @Description: 版本号更新管理
 * @author chenkai
 *
 */
@Controller
@RequestMapping("versionManager")
public class VersionManagerController extends BaseController{

	@Autowired
	private VersionManagerService versionManagerService;
	// 列表
	@RequiresPermissions("versionManager_list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Integer pageNo, Model model) {
		Page<VersionManager> page = new Page<VersionManager>();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		}
		try {
			versionManagerService.getAllForPage(page);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		model.addAttribute("pagelist", page);
		return "/versionManager/versionManager_list";
	}

	@RequiresPermissions("versionManager_list")
	@RequestMapping(value = "/del/{id}",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse del(@PathVariable Long id) {
		try {
			VersionManager record=new VersionManager();
			record.setId(id);
			record.setIsDel(1);
			versionManagerService.updateByPrimaryKeySelective(record);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequiresPermissions("versionManager_list")
	@RequestMapping(value = "/doOperate",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse doOperate(VersionManager versionManager) {
		try {
			VersionManager record=versionManagerService.selectBySoftwareFlag(versionManager.getSoftwareFlag(),versionManager.getDeviceType());
			if(versionManager.getId()==null){
				//判断数据库是否已存在该软件标识
				if(record!=null){
					return new BaseResponse(2, "该软件已存在，不需要再新增该软件标识的软件");
				}
				versionManager.setCreateDate(new Date());
				versionManager.setIsDel(0);
				versionManagerService.insertSelective(versionManager);
			}else{
				if(record!=null&&versionManager.getId()!=record.getId()){
					return new BaseResponse(2, "该软件已存在");
				}
				versionManager.setUpdateDate(new Date());
				versionManagerService.updateByPrimaryKeySelective(versionManager);
			}
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	//toAdd
	@RequiresPermissions("versionManager_list")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public ModelAndView toAdd(Model model, Long id) {
		ModelAndView mv = new ModelAndView();
		VersionManager obj= new VersionManager();
		if (id != null) {
			 obj=versionManagerService.selectByPrimaryKey(id);
		}
		model.addAttribute("versionManager", obj);
		mv.setViewName("versionManager/versionManager_add");
		return mv;
	}
	
}
