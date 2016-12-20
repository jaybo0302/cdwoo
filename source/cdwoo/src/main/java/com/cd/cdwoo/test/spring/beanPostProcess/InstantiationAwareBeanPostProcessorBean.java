/**
 * File：InstantiationAwareBeanPostProcessorBean.java
 * Package：com.cd.cdwoo.test.spring.beanPostProcess
 * Author：chendong
 * Date：2016年12月19日 下午2:05:39
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test.spring.beanPostProcess;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;


/**
 * @author chendong
 * 1、Bean构造出来之前调用postProcessBeforeInstantiation()方法
 * 2、Bean构造出来之后调用postProcessAfterInstantiation()方法
 * 不过通常来讲，我们不会直接实现InstantiationAwareBeanPostProcessor接口，而是会采用继承InstantiationAwareBeanPostProcessorAdapter这个抽象类的方式来使用。
 */
public class InstantiationAwareBeanPostProcessorBean implements InstantiationAwareBeanPostProcessor {
  
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    System.err.println("Enter InstantiationAwareBeanPostProcessorBean.postProcessBeforeInitialization()");
    return bean;
  }
  
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    System.err.println("Enter InstantiationAwareBeanPostProcessorBean.postProcessAfterInitialization()");
    return bean;
  }
  
  @Override
  public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName)
      throws BeansException {
    System.err.println("Enter InstantiationAwareBeanPostProcessorBean.postProcessBeforeInitialization()1");
    return null;
  }
  
  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    System.err.println("Enter InstantiationAwareBeanPostProcessorBean.postProcessAfterInitialization()1");
    return true;
  }
  
  @Override
  public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds,
                                                  Object bean, String beanName)
      throws BeansException {
    return pvs;
  }
  
}
