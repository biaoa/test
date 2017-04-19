package com.linle.system.service;
/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月21日
 **/
public interface RedisManager <K, V>{
	void deleteCached(String key)throws Exception;
	void deleteCached(String namespace,String key)throws Exception;
	void updateCached(String key,Object value)throws Exception;
	void updateCached(String namespace,String key,Object value)throws Exception;
	V getCached(String key)throws Exception;
	V getCached(String namespace,String key)throws Exception;
	/**
	 * 
	 * @Description 将一个value 插入到列表 key 的表尾(最右边)。
	 * @param key
	 * @param value void
	 * $
	 */
	void rpush(String key,Object value);
	/**
	 * 
	 * @Description 根据参数 count 的值，移除列表中与参数 value 相等的元素。<br>
	 * count 的值可以是以下几种：
	 * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
	 * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
	 * count = 0 : 移除表中所有与 value 相等的值。
	 * @param key
	 * @param count
	 * @param value 
	 * @return void
	 */
	void lrem(String key,int count,Object value);
	
	/**
	 * 
	 * @Description set新增操作
	 * @param key
	 * @param value
	 * @return Long 被添加到集合中的新元素的数量，不包括被忽略的元素
	 */
	Long sAdd(String key,Object value);
	/**
	 * 
	 * @Description set删除操作
	 * @param key
	 * @param value
	 * @return Long 被成功移除的元素的数量，不包括被忽略的元素。
	 * $
	 */
	Long sRemove(String key,String value);
	/**
	 * 
	 * @Description 取出set中全部的值
	 * @param key
	 * @return V
	 * $
	 */
	V sMembers(String key);
}
