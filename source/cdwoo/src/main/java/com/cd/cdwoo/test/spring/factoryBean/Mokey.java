/**
 * File：Mokey.java
 * Package：com.cd.cdwoo.test.spring.factoryBean
 * Author：chendong
 * Date：2016年12月19日 上午10:43:01
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test.spring.factoryBean;


/**
 * @author chendong
 */
public class Mokey implements Animal {
  
  @Override
  public void move() {
    System.err.println("mokey move");
  }
}
