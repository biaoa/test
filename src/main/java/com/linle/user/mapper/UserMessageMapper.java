package com.linle.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.linle.entity.enumType.MessageStatus;
import com.linle.entity.sys.UserMessage;

import component.BaseMapper;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年9月8日
 **/
public interface UserMessageMapper extends BaseMapper<UserMessage>{
	public List<UserMessage> findByMaxId(@Param("userId") Long userId,
			@Param("maxId") int maxId, @Param("size") int size);

	public int countRemainder(@Param("maxId") Long maxId,
			@Param("userId") Long userId);
	
	/**
	 * 未读的
	 * @param userId
	 * @param messageStatus
	 * @return
	 */
	public int remainCount(@Param("userId") Long userId,@Param("messageStatus") MessageStatus messageStatus);

	public int updateOwnerId(@Param("ownerId") Long ownerId,@Param("id") Long id);
	/**
	 * 根据页码分页
	 * @param userId
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<UserMessage> findByPageNo(@Param("userId")Long userId, @Param("begin") int begin, @Param("end") int end);

}
