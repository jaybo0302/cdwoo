/**
 * File：IntegerCacheTest.java
 * Package：com.cd.cdwoo.test
 * Author：chendong
 * Date：2016年12月14日 下午1:29:00
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test.demo;

import java.lang.reflect.Field;


/**
 * @author chendong
 */
public class IntegerCacheTest {
  
  /**
   * @param args
   * @throws SecurityException 
   * @throws NoSuchFieldException 
   * @throws IllegalAccessException 
   * @throws IllegalArgumentException 
   */
  public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    // TODO Auto-generated method stub
    Class cache = Integer.class.getDeclaredClasses()[0]; //1
    Field myCache = cache.getDeclaredField("cache"); //2
    myCache.setAccessible(true);//3

    Integer[] newCache = (Integer[]) myCache.get(cache); //4
    newCache[132] = newCache[133]; //5

    int a = 2;
    int b = a + a;
    System.out.printf("%d",b);
    System.out.printf("%d + %d = %d", a, a, b); //
  }
  
}
