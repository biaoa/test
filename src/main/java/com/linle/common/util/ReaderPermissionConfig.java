package com.linle.common.util;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linle.entity.sys.Permission;
import com.linle.entity.sys.PermissionConfig;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;




public class ReaderPermissionConfig {
	private static final Logger logger = LoggerFactory
			.getLogger(ReaderPermissionConfig.class);

	public static PermissionConfig readerConfig() {
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(
					ReaderPermissionConfig.class
							.getResourceAsStream("/permission.xml"),
					"UTF-8");
			XStream xStream = new XStream(new DomDriver());
			xStream.processAnnotations(PermissionConfig.class);
			PermissionConfig permissionConfig = (PermissionConfig) xStream
					.fromXML(inputStreamReader);
			setParentPermission(permissionConfig.getPermissions(), null);
			return permissionConfig;
		} catch (Exception e) {
			logger.error("读取权限配置出错：" + e);
			return null;
		}
	}
	
	public static List<Permission> getPermissions(){
		List<Permission> list = readerConfig().getPermissions();
		List<Permission> permissions =new ArrayList<Permission>();
		permissions.addAll(list);
		permissions.addAll(getPermissions(list));
		return permissions;
	}
	public static String[] getPermissionENames(){
		List<Permission> permissions =  getPermissions();
		String[] enames = new String[permissions.size()];
		for(int i = 0;i<permissions.size();i++){
			enames[i] = permissions.get(i).getEname();
		}		
		return enames;
	}
	
	public static String[] getPermissionENamesAndCNames(){
		List<Permission> permissions =  getPermissions();
		String[] enames = new String[permissions.size()];
		for(int i = 0;i<permissions.size();i++){
			enames[i] = permissions.get(i).getEname()+":"+permissions.get(i).getCname();
		}		
		return enames;
	}
	
	
	private static List<Permission> getPermissions(List<Permission> list){
		List<Permission> permissions = new ArrayList<Permission>();		
		for(Permission permission:list){			
			List<Permission> subPermissions = permission.getSubpermissions();
			if(null != subPermissions && subPermissions.size()>0){
				permissions.addAll(subPermissions);
				permissions.addAll(getPermissions(subPermissions));
			}
		}
		return permissions;
	}
	
	private static void setParentPermission(List<Permission> permissions,Permission parentPermission){
		for(Permission permission:permissions){
			permission.setParentPermission(parentPermission);
			List<Permission> subPermissions = permission.getSubpermissions();
			if(null != subPermissions && subPermissions.size()>0){
				setParentPermission(subPermissions, permission);
			}
		}
	}
	public static String getAllEnamesString(){
		String[] enames = getPermissionENames();
		String enameStr="";
		for (String ename : enames) {
			enameStr = enameStr+ename+",";
		}
		return enameStr;
	}
	
	public static String getAllEnamesAndCnamesString(){
		String[] enames = getPermissionENamesAndCNames();
		String enameStr="";
		for (String ename : enames) {
			enameStr = enameStr+ename+",";
		}
		return enameStr;
	}

	public static void main(String[] args) {
		try {
			PermissionConfig config = readerConfig();
//			String[] enames = getPermissionENames();
			logger.debug(config.toString());
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
}
