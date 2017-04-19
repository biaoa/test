package com.linle.appIndexMenu.mapper;

import java.util.List;

import com.linle.entity.sys.AppIndexMenu;

import component.BaseMapper;

public interface AppIndexMenuMapper extends BaseMapper<AppIndexMenu>{

	List<AppIndexMenu> getAllMenu();
}