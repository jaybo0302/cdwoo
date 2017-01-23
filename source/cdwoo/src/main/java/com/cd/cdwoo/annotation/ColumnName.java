package com.cd.cdwoo.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 注解该列的列名
 * 如果属性名与数据库字段一致可不写该注解
 * 也可以直接使用as
 * 
 * @author chendong
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnName {
  /**
   * 数据库字段名
   */
  String value() default "";
  /**
   * 别名 用于select column as XXX
   */
  String as() default "";
}
