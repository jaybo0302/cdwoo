/**
 * File：PropertiesUtil.java
 * Package：com.fang.cms.utils
 * Author：zhaolingfei
 * Date：2016年4月22日 下午2:16:00
 * Version: V1.0
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件读取工具类.<br/>
 * 配置文件必须存在于 META-INF/conf 下 目录名为 文件名不带后缀. 例如:<br/>
 * META-INF/conf/jdbc/jdbc.properties
 * @author zhaolingfei
 */
public class PropertiesUtil {
  /**
   * 配置文件实例.
   * @Fields props
   */
  private static Properties props;
  /**
   * 读取配置文件.
   * @Author：zhaolingfei
   * @Date：2016年4月22日 下午3:09:45
   * @param fileName void
   */
  private static void readProperties(String fileName) {
    try {
      props = new Properties();
      String filePath = PropertiesUtil.class.getResource("").getPath().split("/com")[0];
      // 指定编码utf8
      InputStreamReader fis = new InputStreamReader(new FileInputStream(new File(filePath + "/META-INF/conf/" + fileName + "/" + fileName + ".properties")), "UTF-8");
      props.load(fis);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * 获取指定key的属性. key 命名规则为 <br/>
   * 文件名.属性名 如 redis.ip 标识 redis.properties 中的 ip属性
   * @Author：zhaolingfei
   * @Date：2016年4月22日 下午3:05:52
   * @param key 需要获取value的属性
   * @return String
   */
  public static String getKey(String key) {
    readProperties(key.split("\\.")[0]);
    return props.getProperty(key).trim();
  }
  /**
   * 获取所有属性，返回一个map<string,string>.
   * @Author：zhaolingfei
   * @Date：2016年4月22日 下午3:05:26
   * @param fileName 获取所有属性的文件名.
   * @return Map<String,String>
   */
  public static Map<String, String> getAll(String fileName) {
    readProperties(fileName);
    Map<String, String> map = new HashMap<>();
    Enumeration<?> enu = props.propertyNames();
    while (enu.hasMoreElements()) {
      String key = (String) enu.nextElement();
      String value = props.getProperty(key);
      map.put(key.trim(), value.trim());
    }
    return map;
  }
}
