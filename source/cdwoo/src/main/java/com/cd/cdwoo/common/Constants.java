/**
 * File：Constants.java
 * Package：com.cd.cdwoo.common
 * Author：chendong
 * Date：2016年12月9日 下午3:20:40
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.common;
import com.cd.cdwoo.util.PropertiesReaderUtils;
/**
 * @author chendong
 */
public final class Constants {
  /**
   * 存放项目properties文件的公共路径
   */
  public static final String COMM_PATH = "com/cd/cdwoo/conf/";
  /**
   * 存放项目properties文件的公共路径
   */
  public static final String CONSTANTS_PATH = COMM_PATH + "constants/constants.properties";
  /**
   * 接口响应警戒阈值.
   * 
   * @Fields API_TIME
   */
  public static final long API_TIME = Long.parseLong(PropertiesReaderUtils.getPropertiesOrDefault(
      CONSTANTS_PATH, "api_time", "2000"));
  /**
   * 无参构造
   */
  private Constants() {
  }
}
