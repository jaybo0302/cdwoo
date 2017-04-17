/**
 * File：Test.java
 * Package：com.cd.cdwoo.test.demo
 * Author：chendong.bj@fang.com
 * Date：2017年4月13日 上午8:53:46
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chendong.bj@fang.com
 */
public class Test {
  
  /**
   * methods desc.
   * @author chendong.bj@fang.com
   * @date 2017年4月13日
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    List<Map<String, String>> list = getList();
    System.out.println(list.get(0).get("key1"));
    Object obj = list.get(0).get("key2");
  }
  public static <A> List<A> getList() {
    List<A> list = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    map.put("key1", "key1");
    map.put("key2", 2);
    list.add((A) map);
    return list;
  }
}
