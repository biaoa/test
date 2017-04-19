package com.linle.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.linle.entity.enumType.RegionLevelType;
import com.linle.entity.sys.SysRegion;
import com.linle.mobileapi.v1.model.ProvinceVO;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月20日
 **/
public interface RegionMapper {
	
	public Integer add(SysRegion region);
	public SysRegion getById(Long id);
	
	public List<SysRegion> findByLevel(@Param("level") RegionLevelType level , @Param("parents") SysRegion parents);
	
	/**
	 * @功能：加载谋个级别的所有
	 * @作者：杨立忠
	 * @param level
	 * @param parentId 当parentId不为null时候，根据父级别查询子级别，若parentId为null，查询所有level级别数据
	 * @return
	 */
	public List<SysRegion> findByLevels( RegionLevelType level);
	public List<SysRegion> getByLevels();
	
	/**
	 * @作者：杨立忠
	 * 根据父编码查找
	 * @param parentGb2260
	 * @return
	 */
	public SysRegion findLevelsByParent(String parentGb2260);
	
	public List<ProvinceVO> getAllData();
}
