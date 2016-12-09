package com.cd.cdwoo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解该列的查询出来的别名 as <br>
 * 该注解虽然也是用于查询使用别名as,但与<code>ColumnName.class</code>解决的问题不同 <br>
 * <code>@AsSelectForEntity</code>主要解决多表查询时候出现的重名问题 <br>
 * @ColumnName是在所有查询中都使用同一个别名 <br>
 * @AsSelectForEntity只在EntitySerivce查询的时候使用的专属别名 <br>
 * Example: <br>
 * &nbsp; News实体类中有createDate，关联表NewType中同样有createDate <br>
 * 在NewType.createDate字段加入<code>@AsSelectForEntity(as="cd",entity=News.calss)</code> <br>
 * 也就是在NewsSerivce中查询的时候会出现的别名<code>createDate as cd</code>而不会NewType自己的sql查询
 * 
 * @author chendong
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AsSelectForEntity {
  
  /**
   * 字段别名
   * 
   * @return
   */
  String as();
  
  /**
   * 实体类，当这个实体类查询的时候使用的专属别名
   * 
   * @return
   */
  Class<?> entity();
}
