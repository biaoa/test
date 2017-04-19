package com.linle.appIndexMenu.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.AppIndexMenu;

public interface AppIndexMenuService extends BaseService<AppIndexMenu> {

	List<AppIndexMenu> getAllMenu();

}
