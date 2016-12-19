/**
 * File：AwareBean.java
 * Package：com.cd.cdwoo.test.spring
 * Author：chendong
 * Date：2016年12月19日 上午10:23:24
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * @author chendong
 * 1、如果你的BeanName、ApplicationContext、BeanFactory有用，那么就自己定义一个变量将它们保存下来，如果没用，那么只需要实现setXXX()方法，用一下Spring注入进来的参数即可
 * 2、如果Bean同时还实现了InitializingBean，容器会保证BeanName、ApplicationContext和BeanFactory在调用afterPropertiesSet()方法被注入
 */
public class AwareBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware {
  
  private String beanName;
  private ApplicationContext applicationContext;
  private BeanFactory beanFactory;
  
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    System.err.println("Enter AwareBean.setApplicationContext() and applicationContext = " + applicationContext + "\n");
    this.applicationContext = applicationContext;
  }
  
  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    System.err.println("Enter AwareBean.setBeanFactory() and beanFactory = " + beanFactory);
    this.beanFactory = beanFactory;
  }
  
  @Override
  public void setBeanName(String name) {
    this.beanName = name;
    System.err.println("Enter AwareBean.setBeanName() and beanName = " + beanName);
  }
  
}
