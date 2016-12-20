/**
 * File：CommonBean.java
 * Package：com.cd.cdwoo.test.spring.beanPostProcess
 * Author：chendong
 * Date：2016年12月19日 下午1:42:56
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test.spring.beanPostProcess;


/**
 * @author chendong
 */
public class CommonBean {
  private String commonName;
  
  public CommonBean()
  {
      System.err.println("Enter CommonBean's constructor");
  }
  
  public void setCommonName(String commonName) {
    System.err.println("Enter CommonBean.setCommonName(), commonName = " + commonName);
    this.commonName = commonName;
  }
  
  public void initMethod() {
    System.err.println("Enter CommonBean.initMethod() and commonName = " + commonName);
  }
}
