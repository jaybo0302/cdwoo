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
import java.util.Iterator;
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
//    List<Map<String, String>> list = getList();
//    System.out.println(list.get(0).get("key1"));
//    Object obj = list.get(0).get("key2");
//    System.out.println("2017-05-03 19:57:38".split(" ")[1].split(":")[0]);
//    System.out.println("116.3823+39.8195".split("\\+")[1].substring(0, "116.3823+39.8195".split("\\+")[1].indexOf(".") + 3));
//    System.out.println("2017-05-03 19:57:38".length());
//    System.out.println("asasdfasfasf===23.45+123.34".split("===")[0]);
//    
//    System.out.println("2d9560044283af893c78c757fe67e97736ebf307#!#116.4180+39.9784#!#2017-05-06 11:11:06".split("#!#")[1].split("\\+")[0].length());
//    String l = "113.412+22.9419";
//    String x = l.split("\\+")[0];
//    String y = l.split("\\+")[1];
//    System.out.println(x.lastIndexOf(".") +" " + y.lastIndexOf("."));
    
 // 只要实现了Iterable接口的对象都可以使用for-each循环。  
    // Iterable接口只由iterator方法构成，  
    // iterator()方法是java.lang.Iterable接口，被Collection继承。  
    /*public interface Iterable<T> { 
        Iterator<T> iterator(); 
    }*/  
    Iterable<String> iter = new Iterable<String>() {  
        public Iterator<String> iterator() {  
            List<String> l = new ArrayList<String>();  
            l.add("aa");  
            l.add("bb");  
            l.add("cc");  
            return l.iterator();  
        }  
    };  
    for(int count : new int[] {1, 2}){  
        for (String item : iter) {  
            System.out.println(item);  
        }  
        System.out.println("---------->> " + count + " END.");  
    }
  }
  public static <A> List<A> getList() {
    List<A> list = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    map.put("key1", "key1");
    map.put("key2", 2);
    list.add((A) map);
    return list;
  }
  public static String formatXY(String str) {
    int l = str.length();
    int o = str.lastIndexOf(".");
    if (str.length() - str.lastIndexOf(".") >= 3) {
      return str.substring(0, str.lastIndexOf(".") + 3);
    } else {
      for (int i=0; i<((2-(l-o))+1); i++){
        str += "0";
      }
      return str;
    }
  }
}
