package com.cd.cdwoo.common;

import java.util.List;
import java.util.Map;

/**
 * @author cd
 * @param <T>
 *        实体类摸对象
 */
public interface IBaseService<T> {
  
  /**
   * 增加实体类
   * 
   * @param t
   *        实体类
   */
  int addObject(T t);
  
  /**
   * 批量删除实体类
   * ids以逗号","分隔开
   * 
   * @param ids
   *        ids
   */
  int deleteObject(String ids);
  
  /**
   * 没有条件查询 查询所有数据
   * 
   * @return list
   */
  List<Object> queryObjects();
  
  /**
   * 根据Id返回实体类
   * 
   * @param id
   *        id
   * @return 实体类
   */
  Object getObjectById(String id);
  
  /**
   * 查询所有的数据个数
   * 
   * @return int
   */
  Long getObjectCount();
}
