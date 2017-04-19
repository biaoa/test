package com.linle.entity.sys;

import com.linle.entity.enumType.RegionLevelType;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月20日
 **/
public class SysRegion extends BaseDomain{
	private String gb2260;
	private String name;
	private RegionLevelType regionLevel;
	private SysRegion parent;
	private String parentIds;
	private String diallingCode;
	public String getGb2260() {
		return gb2260;
	}
	public void setGb2260(String gb2260) {
		this.gb2260 = gb2260;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public RegionLevelType getRegionLevel() {
		return regionLevel;
	}
	public void setRegionLevel(RegionLevelType regionLevel) {
		this.regionLevel = regionLevel;
	}
	public SysRegion getParent() {
		return parent;
	}
	public void setParent(SysRegion parent) {
		this.parent = parent;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getDiallingCode() {
		return diallingCode;
	}
	public void setDiallingCode(String diallingCode) {
		this.diallingCode = diallingCode;
	}
	
}
