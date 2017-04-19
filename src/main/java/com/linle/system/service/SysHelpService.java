package com.linle.system.service;

import java.util.List;

import com.linle.common.util.Page;
import com.linle.entity.sys.SysHelp;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月31日
 **/
public interface SysHelpService {
	public Boolean addSysHelp(SysHelp help);
	public Boolean updateSysHelp(SysHelp help);
	public SysHelp getById(Long id);
	public List<SysHelp> getMobileSysHelps(Long centerId);
	public List<SysHelp> getSysHelpsForPage(Page<SysHelp> page);
	public Boolean delHelpById(Long helpId);
}
