package com.cd.cdwoo.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cd.cdwoo.annotation.AsSelectForEntity;
import com.cd.cdwoo.annotation.ColumnName;
import com.cd.cdwoo.annotation.NotSelectColumn;
import com.cd.cdwoo.annotation.Table;

/**
 * 存放拼接SQL需要的方法和变量
 * 
 * @author pengmaokui
 */
public final class GenerateSQL {
  
  /**
   * 
   */
  private static Map<String, Map<String, List<String>>> selectColumn = null;
  
  /**
   * 实体类之间的继承关系
   */
  private static Map<Class<?>, Class<?>> entityRelation = null;
  
  /**
   * 表明的新旧关系 key:旧表名 value：新表名
   */
  private static Map<String, String> tableNameRelation = null;
  
  /**
   * 包名称
   */
  private static String packageName;
  
  /**
   * 定义默认的fieldList长度，由于默认长度是10 避免不必要的List复制
   */
  private static final Integer DEFAULT_FIELDNUMBER = 20;
  
  /**
   * 通过反射根据实体类返回成员 <br>
   * 包括关系实体中的成员
   * 
   * @param clazz
   *        查询该类中的成员
   * @param asClazz
   *        判断是否有@AsSelectForEntity注解，并且entity是asClass
   * @return map
   */
  public static Map<String, List<String>> getSelectByEntity(Class<?> clazz, Class<?> asClazz) {
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    Class<?> subClass = GenerateSQL.entityRelation.get(clazz);
    String tableName = null;
    // tableName存放子类的tableName
    if (subClass != null) {
      tableName = ((Table) subClass.getAnnotation(Table.class)).name();
    }
    else {
      tableName = ((Table) clazz.getAnnotation(Table.class)).name();
    }
    List<String> columns = new ArrayList<String>(GenerateSQL.DEFAULT_FIELDNUMBER);
    List<Field> fieldList = new ArrayList<Field>(GenerateSQL.DEFAULT_FIELDNUMBER);
    
    Field[] fields = clazz.getDeclaredFields();
    fieldList.addAll(Arrays.asList(fields));
    
    if (subClass != null) {
      Field[] subFields = subClass.getDeclaredFields();
      fieldList.addAll(Arrays.asList(subFields));
    }
    String fieldName = null;
    for (int i = 0; i < fieldList.size(); i++) {
      Field field = fieldList.get(i);
      fieldName = field.getName();
      
      Class<?> fieldClazz = field.getType();
      // 忽略序列化的私有变量
      // 忽略掉关联对象，只保存数据库列名
      if (fieldName.equals("serialVersionUID")) {
        continue;
      }
      
      // 不是原始类型 不是数组
      if (!fieldClazz.isPrimitive()
          && !fieldClazz.isArray()
          && (fieldClazz.getPackage().toString().startsWith("package com.fang.bdp.core") || fieldClazz
              .getPackage().toString().startsWith("package " + GenerateSQL.packageName))) {
        Table tb = fieldClazz.getAnnotation(Table.class);
        if (tb != null) {
          map.putAll(GenerateSQL.getSelectByEntity(fieldClazz, asClazz));
        }
        continue;
      }
      
      NotSelectColumn nsc = field.getAnnotation(NotSelectColumn.class);
      if (nsc == null) {
        ColumnName cn = field.getAnnotation(ColumnName.class);
        if (cn != null && !cn.value().trim().isEmpty()) {
          fieldName = cn.value();
        }
        // 添加as
        AsSelectForEntity ae = field.getAnnotation(AsSelectForEntity.class);
        if (ae != null) {
          Class<?> entity = ae.entity();
          // asClazz属于entity的父类或相同
          if (asClazz.isAssignableFrom(entity)) {
            fieldName += " as " + ae.as();
          }
        }
        else if (cn != null && !cn.as().trim().isEmpty()) {
          fieldName += " as " + cn.as();
        }
        columns.add(fieldName);
      }
    }
    map.put(tableName, columns);
    return map;
  }
  
  /**
   * 返回实体类的继承关系 <br>
   * <code>map<key,value></code>对应 父类-->子类的关系。没有继承关系则key和value是同一个class
   * 
   * @param superClassList
   *        dbp实体类
   * @param classList
   *        项目实体类
   */
  public static void setRelation(List<Class<?>> superClassList, List<Class<?>> classList) {
    Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();
    Map<String, String> tableNameMap = new HashMap<String, String>();
    if (classList == null) {
      for (int i = 0; i < superClassList.size(); i++) {
        Class<?> clazz = superClassList.get(i);
        map.put(clazz, null);
        tableNameMap.put(clazz.getAnnotation(Table.class).name(), null);
      }
    }
    else {
      classList.removeAll(superClassList);
      List<Class<?>> subClassList = new ArrayList<Class<?>>();
      for (int i = 0; i < superClassList.size(); i++) {
        Class<?> superClass = superClassList.get(i);
        boolean isExistSubClass = false;
        for (int j = 0; j < classList.size(); j++) {
          Class<?> clazz = classList.get(j);
          if (superClass.isAssignableFrom(clazz)) {
            map.put(superClass, clazz);
            tableNameMap.put(superClass.getAnnotation(Table.class).name(),
                clazz.getAnnotation(Table.class).name());
            subClassList.add(clazz);
            isExistSubClass = true;
          }
        }
        if (!isExistSubClass) {
          map.put(superClass, null);
          tableNameMap.put(superClass.getAnnotation(Table.class).name(), null);
        }
      }
      
      // 删除继承的子实体类
      classList.removeAll(subClassList);
      for (int i = 0; i < classList.size(); i++) {
        Class<?> clazz = classList.get(i);
        map.put(clazz, null);
        tableNameMap.put(clazz.getAnnotation(Table.class).name(), null);
      }
    }
    GenerateSQL.setEntityRelation(map);
    GenerateSQL.setTableNameRelation(tableNameMap);
  }
  
  /**
   * @return selectColumn
   */
  public static Map<String, Map<String, List<String>>> getSelectColumn() {
    return selectColumn;
  }
  
  /**
   * @param selectColumn
   *        set selectColumn
   */
  public static void setSelectColumn(Map<String, Map<String, List<String>>> selectColumn) {
    GenerateSQL.selectColumn = selectColumn;
  }
  
  /**
   * @return entityRelation
   */
  public static Map<Class<?>, Class<?>> getEntityRelation() {
    return entityRelation;
  }
  
  /**
   * @param entityRelation
   *        set entityRelation
   */
  public static void setEntityRelation(Map<Class<?>, Class<?>> entityRelation) {
    GenerateSQL.entityRelation = entityRelation;
  }
  
  /**
   * @return packageName
   */
  public static String getPackageName() {
    return packageName;
  }
  
  /**
   * @param packageName
   *        set packageName
   */
  public static void setPackageName(String packageName) {
    GenerateSQL.packageName = packageName;
  }
  
  /**
   * @return tableNameRelation
   */
  public static Map<String, String> getTableNameRelation() {
    return tableNameRelation;
  }
  
  /**
   * @param tableNameRelation
   *        set tableNameRelation
   */
  public static void setTableNameRelation(Map<String, String> tableNameRelation) {
    GenerateSQL.tableNameRelation = tableNameRelation;
  }
  
  /**
   * private constructor
   */
  private GenerateSQL() {
  }
  
}
