package com.linle.entity.sys;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Permission {
	@XStreamAsAttribute
	@XStreamAlias("cname")
	private String cname;
	
	@XStreamAsAttribute
	@XStreamAlias("ename")	
	private String ename;
	
	@XStreamAsAttribute
	@XStreamAlias("permissionType")
	private int permissionType;
	
	@XStreamAsAttribute
	@XStreamAlias("moduleName")
	private String moduleName;
	
	@XStreamAsAttribute
	@XStreamAlias("description")
	private String description;
	
	
	@XStreamImplicit(itemFieldName="permission")	
	private List<Permission> subpermissions;
	private Permission parentPermission;
	/**是否选中**/
	private boolean checked=false;

	public Permission() {

	}

	public Permission(String cname, String ename, int permissionType,
			String moduleName, String description) {
		this.cname = cname;
		this.ename = ename;
		this.permissionType = permissionType;
		this.moduleName = moduleName;
		this.description = description;
	}

	public Permission(String cname, String ename, int permissionType,
			String moduleName, String description, Permission parentPermission) {
		this(cname, ename, permissionType, moduleName, description);
		this.parentPermission = parentPermission;
	}

	public Permission(String cname, String ename, int permissionType,
			String moduleName, String description, Permission parentPremission,
			List<Permission> subpermissions) {
		this(cname, ename, permissionType, moduleName, description,
				parentPremission);
		this.subpermissions = subpermissions;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public int getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(int permissionType) {
		this.permissionType = permissionType;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Permission> getSubpermissions() {
		return subpermissions;
	}

	public void setSubpermissions(List<Permission> subpermissions) {
		this.subpermissions = subpermissions;
	}

	public Permission getParentPermission() {
		return parentPermission;
	}

	public void setParentPermission(Permission parentPermission) {
		this.parentPermission = parentPermission;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	
}
