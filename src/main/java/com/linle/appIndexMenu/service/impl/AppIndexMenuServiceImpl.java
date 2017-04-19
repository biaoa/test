package com.linle.appIndexMenu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.appIndexMenu.mapper.AppIndexMenuMapper;
import com.linle.appIndexMenu.service.AppIndexMenuService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.entity.sys.AppIndexMenu;

@Service
@Transactional
public class AppIndexMenuServiceImpl extends CommonServiceAdpter<AppIndexMenu> implements AppIndexMenuService {
	
	@Autowired
	private AppIndexMenuMapper mapper;

	@Override
	public List<AppIndexMenu> getAllMenu() {
		return mapper.getAllMenu();
	}

}
