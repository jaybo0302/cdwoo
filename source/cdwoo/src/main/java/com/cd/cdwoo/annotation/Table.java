package com.cd.cdwoo.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 数据库表名注解
 * 替换javax.persistence.Table 方便代码生成
 * 
 * @author cd
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {
  
  /**
   * 数据库表名
   * 
   * @return
   */
  String name();
}
