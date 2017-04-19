package com.linle.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.util.Page;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.sys.SysHelp;
import com.linle.system.mapper.SysHelpMapper;
import com.linle.system.service.SysHelpService;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月31日
 **/
@Service("sysHelpService")
@Transactional
public class SysHelpServiceImpl implements SysHelpService{

	@Autowired
	private SysHelpMapper mapper;
	@Override
	public Boolean addSysHelp(SysHelp help) {
		return mapper.addSysHelp(help)>0;
	}

	@Override
	public Boolean updateSysHelp(SysHelp help) {
		return mapper.updateSysHelp(help)>0;
	}

	@Override
	public SysHelp getById(Long id) {
		return mapper.getById(id);
	}

	@Override
	public List<SysHelp> getMobileSysHelps(Long centerId) {
		return mapper.getSysHelps(centerId);
	}

	@Override
	public List<SysHelp> getSysHelpsForPage(Page<SysHelp> page) {
		return mapper.getSysHelpsForPage(page);
	}

	@Override
	public Boolean delHelpById(Long helpId) {
		SysHelp help = mapper.getById(helpId);
		help.setDelFlag(UserStatusType.deleted);
		return mapper.delHelpById(help)>0;
	}

}
