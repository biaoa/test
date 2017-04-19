//package com.linle.common.base;
//
//
//import com.linle.common.util.Page;
//
//public interface IBaseService<T> {
//	
//	/**
//	 * 执行添加操作
//	 * @param obj
//	 * @return 返回添加对象
//	 */
//     public boolean addExec(T baseDomain);
//     /**
//      * 通过ID执行删除操作
//      * @param id
//      */
//     public boolean deleteById(Long id);
//
//     /**
//      * 执行修改操作
//      * @param obj
//      * @return 修改对象
//      */
//     public T updateExec(T baseDomain);
//     
//     /**
//      * 通过ID查询对应的对象
//      * @param id
//      * @return
//      */
//     public T getDataById(String id);
//     
// 	 public Page<T> findDataForPage(Page<T> page) throws Exception ;
//}
