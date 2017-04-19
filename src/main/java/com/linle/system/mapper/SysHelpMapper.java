package com.linle.system.mapper;

import java.util.List;

import com.linle.common.util.Page;
import com.linle.entity.sys.SysHelp;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月31日
 **/
public interface SysHelpMapper {
	public Integer addSysHelp(SysHelp sysHelp);
	
	public Integer updateSysHelp(SysHelp sysHelp);
	public SysHelp getById(Long id);
	/**
	 * 后台帮助列表
	 * @param page
	 * @return
	 */
	public List<SysHelp> getSysHelpsForPage(Page<SysHelp> page);
	/**
	 * mobile 帮助列表
	 * @param centerId
	 * @return
	 */
	public List<SysHelp> getSysHelps(Long centerId);

	public Integer delHelpById(SysHelp sysHelp);
	
}
