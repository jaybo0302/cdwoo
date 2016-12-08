package com.cd.cdwoo.common;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

/**
 * @author pengmaokui
 * @param <T>
 */
public interface IBaseDao<T> {
  
  /**
   * 增加实体类
   * 
   * @param t
   *        t
   */
  int addObject(T t);
  
  /**
   * 批量删除实体类
   * 
   * @param ids
   *        ids
   */
  int deleteObject(List<String> ids);
  
  /**
   * 根据Id获取对象
   * 
   * @param map
   *        map
   * @return object
   */
  Object getObjectById(Map<String, Object> map);
  
  /**
   * 根据Id获取对象
   * 
   * @param id
   *        id
   * @return object
   */
  Object getObjectById(String id);
  
  /**
   * 查询所有的数据个数
   * 
   * @return int
   */
  Long getObjectCount();
  
  /**
   * 修改数据
   * 
   * @param t
   *        t
   */
  int modifyObject(T t);
  
  /**
   * 修改数据
   * 
   * @return SqlSessionTemplate
   */
  SqlSessionTemplate getSqlSession();
  
  /**
   * 取表是否存在
   * 
   * @param dbName
   * 
   * @return 0:无 1:有
   */
  int findTableIsExist(String dbName);
  
  /**
   * 初始化创建表
   * 
   * @return 0:失败 1:成功
   */
  int exeuteInitTable();
}
