package com.cd.cdwoo.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
/**
 * properties文件读取工具类
 * 
 * @author chendong
 */
public final class PropertiesReaderUtils {
  /**
   * 私有无参构造
   */
  private PropertiesReaderUtils() {
    
  }
  /**
   * 获取配置文件单独key值内容
   * 
   * @param address 配置文件路径
   * @param key  需要湖片区内容的key值
   * @return 所需key值得value，如果配置文件不存在，返回key值，如果没有对应的key值，返回key值
   */
  public static String getProperties(String address, String key) {
    InputStream inputStream = PropertiesReaderUtils.class.getClassLoader().getResourceAsStream(address);
    InputStreamReader iReader = null;
    Properties properties = new Properties();
    try {
      if (inputStream == null) {
        CDWooLogger.info("properties file : " + address + " can not find");
        return key;
      }
      iReader = new InputStreamReader(inputStream, "UTF-8");
      properties.load(iReader);
    }
    catch (IOException ioE) {
      CDWooLogger.info("url:" + address + "get properties:" + key + " error" + ioE);
      return key;
    }
    finally {
      IOUtils.closeQuietly(inputStream);
      IOUtils.closeQuietly(iReader);
    }
    String value = properties.getProperty(key);
    if (StringUtils.isBlank(value)) {
      return key;
    }
    return value;
  }
  /**
   * 获取配置文件单独key值内容,如果为空，返回默认值
   * 
   * @param address
   *        配置文件路径
   * @param key
   *        需要湖片区内容的key值
   * @return
   *         所需key值得value，如果配置文件不存在，返回key值，如果没有对应的key值，返回key值
   */
  public static String getPropertiesOrDefault(String address, String key, String defaultValue) {
    InputStream inputStream = PropertiesReaderUtils.class.getClassLoader().getResourceAsStream(address);
    InputStreamReader iReader = null;
    Properties properties = new Properties();
    try {
      if (inputStream == null) {
        CDWooLogger.info("properties file : " + address + " can not find");
        return defaultValue;
      }
      iReader = new InputStreamReader(inputStream, "UTF-8");
      properties.load(iReader);
    }
    catch (IOException ioE) {
      CDWooLogger.info("url:" + address + "get properties:" + key + " error" + ioE);
      return defaultValue;
    }
    finally {
      IOUtils.closeQuietly(inputStream);
      IOUtils.closeQuietly(iReader);
    }
    String value = properties.getProperty(key);
    if (StringUtils.isBlank(value)) {
      return defaultValue;
    }
    return value;
  }
  /**
   * 获取配置文件单独key值内容
   * 
   * @param address 配置文件路径
   * @param key 需要检索内容的key值
   * @param encoding 编码格式
   * @return 所需key值得value
   */
  public static String getProperties(String address, String key, String encoding) {
    InputStreamReader inputStream = null;
    Properties properties = new Properties();
    try {
      inputStream = new InputStreamReader(PropertiesReaderUtils.class.getClassLoader().getResourceAsStream(address), encoding);
      properties.load(inputStream);
    }
    catch (Exception e) {
      CDWooLogger.error("读取配置文件失败，" + e);
    }
    finally {
      try {
        inputStream.close();
      }
      catch (Exception e) {
        CDWooLogger.error("读取配置文件后关闭流失败，" + e);
      }
    }
    return properties.getProperty(key);
  }
  /**
   * 获取配置文件行值key value对
   * 
   * @param address 配置文件路径
   * @param encoding  指定编码
   * @return  配置文件中所有键值对
   */
  public static Map<String, String> getPropertiesMap(String address, String encoding) {
    // 获取输入流
    InputStream inputStream = PropertiesReaderUtils.class.getClassLoader().getResourceAsStream(address);
    // 读取字符串
    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
    // 接收键值对数组
    Map<String, String> map = new HashMap<String, String>();
    // 接收输入流行字符串
    String line = "";
    try {
      // 读取每行数据,知道行为null
      while ((line = in.readLine()) != null) {
        // 跳过注释行
        if (line.startsWith("#")) {
          continue;
        }
        // 分割字符串 留下key value
        String[] lineArray = line.split("=", 2);
        // 获取Key
        if (lineArray.length == 2 && StringUtils.isNotBlank(lineArray[0].trim())
            && StringUtils.isNotBlank(lineArray[1].trim())) {
          map.put(lineArray[0].trim(), lineArray[1].trim());
        }
        
      }
    }
    catch (IOException ioE) {
      // 捕获IO异常
      CDWooLogger.error("PropertiesReaderUtils.getProperties -出现IO异常：" + ioE);
    }
    finally {
      // 关闭输入流
      try {
        inputStream.close();
      }
      catch (IOException e) {
        CDWooLogger.error("读取配置文件后关闭流失败，" + e);
      }
    }
    // 返回结果
    return map;
  }
}
