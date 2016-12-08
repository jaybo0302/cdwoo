package com.cd.cdwoo.common;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.cd.cdwoo.annotation.Table;

/**
 * 基础业务层实现类
 * 
 * @author pengmaokui
 * @param <T>
 *        实体类摸对象
 */
public class BaseServiceImpl<T> implements IBaseService<T> {
  
  /**
   * 表名
   */
  private String tableName;
  
  /**
   * class
   */
  private Class<?> clazz;
  
  /**
   * subClass
   */
  private Class<?> subClass;
  
  /**
   * 判断是否初始化clazz，tableName
   * 由于springBean初始化在GenerateSQL继承成员初始化之前，故不能把初始化放在构造函数里面
   */
  private boolean isInit = false;
  
  /**
   * 定义默认的fieldList长度，由于默认长度是10 避免不必要的List复制
   */
  private static final Integer DEFAULT_FIELDNUMBER = 20;
  
  /**
   * @作用 初始化模块.class和模块类名字
   */
  @SuppressWarnings("unchecked")
  public BaseServiceImpl() {
    super();
    try {
      clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
          .getActualTypeArguments()[0];
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * baseDao
   */
  private IBaseDao<T> baseDao;
  
  @Override
  public int addObject(T t) {
    this.preAddObject(t);
    int rows = this.baseDao.addObject(t);
    this.postAddObject(t);
    return rows;
  }
  
  @Override
  public int deleteObject(String ids) {
    this.preDeleteObject(ids);
    List<String> idList = new ArrayList<String>();
    if (!ids.isEmpty()) {
      idList = Arrays.asList(ids.split(","));
    }
    int rows = this.baseDao.deleteObject(idList);
    this.postDeleteObject(ids);
    return rows;
  }
  
  @Override
  public Object getObjectById(String id) {
    return this.baseDao.getObjectById(id);
  }
  
  @Override
  public Long getObjectCount() {
    return this.baseDao.getObjectCount();
  }
  
  /**
   * 增加方法的前置处理
   * 
   * @param t
   *        t
   */
  protected void preAddObject(T t) {
    
  }
  
  /**
   * 增加方法的后置处理
   * 
   * @param t
   *        t
   */
  protected void postAddObject(T t) {
    
  }
  
  /**
   * 批量删除方法的前置处理
   * 
   * @param ids
   *        ids
   */
  protected void preDeleteObject(String ids) {
    
  }
  
  /**
   * 批量删除方法的后置处理
   * 
   * @param ids
   *        ids
   */
  protected void postDeleteObject(String ids) {
    
  }
  
  /**
   * 修改前置处理
   * 
   * @param t
   *        t
   */
  protected void preModifyObject(T t) {
    
  }
  
  /**
   * 修改后置处理
   * 
   * @param t
   *        t
   */
  protected void postModifyObject(T t) {
    
  }
  
  /**
   * 返回多个继承FormBean类的所有成员变量
   * 
   * @param formBean
   *        formBean
   * @return list<field>
   */
  private List<Field> getAllFields(FormBean formBean) {
    List<Field> fieldList = new ArrayList<Field>(BaseServiceImpl.DEFAULT_FIELDNUMBER);
    Class<?> tempFromBean = formBean.getClass();
    while (!tempFromBean.equals(FormBean.class)) {
      Field[] fields = tempFromBean.getDeclaredFields();
      fieldList.addAll(Arrays.asList(fields));
      tempFromBean = tempFromBean.getSuperclass();
    }
    return fieldList;
  }
  
  /**
   * service初始化
   */
  private void init() {
    if (!this.isInit) {
      this.subClass = GenerateSQL.getEntityRelation().get(clazz);
      if (this.subClass != null) {
        this.tableName = this.subClass.getAnnotation(Table.class).name();
      }
      else {
        this.tableName = clazz.getAnnotation(Table.class).name();
      }
      this.isInit = true;
    }
  }
  
  /**
   * @param baseDao
   *        set baseDao
   */
  public void setBaseDao(IBaseDao<T> baseDao) {
    this.baseDao = baseDao;
  }
  
  /**
   * @return baseDao
   */
  public IBaseDao<T> getBaseDao() {
    return baseDao;
  }
  
  @Override
  public List<Object> queryObjects() {
    // TODO Auto-generated method stub
    return null;
  }
}
