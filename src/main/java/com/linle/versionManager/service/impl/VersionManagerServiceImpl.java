package com.linle.versionManager.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.response.VersionResponse;
import com.linle.versionManager.mapper.VersionManagerMapper;
import com.linle.versionManager.model.VersionManager;
import com.linle.versionManager.service.VersionManagerService;
@Service
@Transactional
public class VersionManagerServiceImpl extends CommonServiceAdpter<VersionManager>  implements VersionManagerService {
	@Autowired
	private VersionManagerMapper mapper;

	@Override
	public Page<VersionManager> getAllForPage(Page<VersionManager> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}
	
	public VersionManager selectBySoftwareFlag(String softwareFlag,String deviceType){
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("softwareFlag", softwareFlag);
		map.put("deviceType", deviceType);
		return mapper.selectBySoftwareFlag(map);
	}
	
	public VersionResponse checkVersion(String softwareFlag,String deviceType,String currVersion){
		VersionResponse res=new VersionResponse();
		try {
			VersionManager versionManager=this.selectBySoftwareFlag(softwareFlag,deviceType);
			if(versionManager==null){
				res.setCode(1);
				res.setMsg("该软件标识不存在");
				return res;
			}
			int is_update=0;//0:不用更新  1：非强制性更新  2 强制性更新
			String min_version=versionManager.getMinVersion();//最低版本号
			String new_version=versionManager.getNewVersion();//最新版本号
			//第一步：当前版本号和最低版本号比较
			int num=compareVersion(currVersion,min_version);
			if(num==0){
				is_update=1;
			}else if(num<0){
				is_update=2;
			}else if(num>0){
				//第二步：当前版本号和最新版本号比较
				int num2=compareVersion(currVersion,new_version);
				if(num2==0){
					is_update=0;
				}else if(num2<0){
					is_update=1;
				}else if(num2>0){//这种情况不可能发生   
					is_update=0;
				}
			}
			String msg="";
			//0:不用更新  1：非强制性更新  2 强制性更新
			if(is_update==0){
				 msg="当前版本无需更新";
			}else if(is_update==1){
				 msg="当前版本非强制性更新";
			}else if(is_update==2){
				 msg="当前版本需强制性更新";
			}
			res.setIsUpdate(is_update);
			res.setNewVersion(new_version);
			res.setUrl(versionManager.getUrl());
			res.setContent(versionManager.getContent());
			res.setCode(0);
			res.setMsg(msg);
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
		}
		return res;
	}
	
	/**
	 * 判断前者大于后者
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int compareVersion(String s1, String s2){
        if( s1 == null && s2 == null )
            return 0;
        else if( s1 == null )
            return -1;
        else if( s2 == null )
            return 1;

        String[]
            arr1 = s1.split("[^a-zA-Z0-9]+"),
            arr2 = s2.split("[^a-zA-Z0-9]+")
        ;

        int i1, i2, i3;

        for(int ii = 0, max = Math.min(arr1.length, arr2.length); 
       		 ii <= max; ii++){
            if( ii == arr1.length )
                return ii == arr2.length ? 0 : -1;
            else if( ii == arr2.length )
                return 1;

            try{
                i1 = Integer.parseInt(arr1[ii]);
            }
            catch (Exception x){
                i1 = Integer.MAX_VALUE;
            }

            try{
                i2 = Integer.parseInt(arr2[ii]);
            }
            catch (Exception x){
                i2 = Integer.MAX_VALUE;
            }

            if( i1 != i2 ){
                return i1 - i2;
            }

            i3 = arr1[ii].compareTo(arr2[ii]);

            if( i3 != 0 )
                return i3;
        }

        return 0;
    }
	
}
