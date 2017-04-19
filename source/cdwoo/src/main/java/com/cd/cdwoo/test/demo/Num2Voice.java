package com.cd.cdwoo.test.demo;
import java.util.HashMap;
import java.util.Map;


/**
 * File：Num2Voice.java
 * Package：
 * Author：chendong.bj@fang.com
 * Date：2017年4月17日 上午9:18:37
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */

/**
 * @author chendong.bj@fang.com
 */
public class Num2Voice {
  private static final String[] NAME = {"千", "百", "十", "一"};
  private static Map<String, String> numMap = new HashMap<String, String>();
  static {
    numMap.put("9", "九");
    numMap.put("8", "八");
    numMap.put("7", "七");
    numMap.put("6", "六");
    numMap.put("5", "五");
    numMap.put("4", "四");
    numMap.put("3", "三");
    numMap.put("2", "二");
    numMap.put("1", "一");
    numMap.put("0", "零");
  }
  /**
   * methods desc.
   * @author chendong.bj@fang.com
   * @date 2017年4月17日
   * @param args
   */
  public static void main(String[] args) {
    System.out.println(util(1201));
  }
  public static String util(long num) {
    String result = "";
    if (num == 0) {
      return "零";
    }
    String numStr = add0(String.valueOf(num), 12 - String.valueOf(num).length());
    String str1 = numStr.substring(0, 4);
    String str2 = numStr.substring(4, 8);
    String str3 = numStr.substring(8, 12);
    if (Integer.parseInt(str1) >0) {
      result += get(str1) + "亿";
    }
    if (Integer.parseInt(str2) >0) {
      result += get(str2) + "万";
    }
    result += get(str3);
    result = result.replace("零零零零", "零").replace("零零零", "零").replace("零零", "零");
    if (result.startsWith("零")) {
     result = result.substring(1, result.length());
    }
    if (result.endsWith("零")) {
      result = result.substring(0, result.length() - 1);
     }
    return result;
  }
  
  public static String add0(String str, int no) {
    for (int i = 0; i < no; i++) {
      str = "0" + str;
    }
    return str;
  }
  
  public static String get(String numStr) {
    String result = "";
    for (int i = 0; i < 4; i++) {
      String num = numMap.get(String.valueOf(numStr.toCharArray()[i]));
      if ("零".equals(num)) {
        result += num;
      } else {
        result += (num + NAME[i]);
      }
    }
    result = result.substring(0, result.length() - 1);
    return cut0(result);
  }
  public static String cut0(String str) {
    while (str.endsWith("零")) {
      str = str.substring(0, str.length() - 1);
    }
    return str;
  }
}
