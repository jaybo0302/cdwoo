/**
 * File：PostProcessorBean.java
 * Package：com.cd.cdwoo.test.spring.beanPostProcess
 * Author：chendong
 * Date：2016年12月19日 下午1:45:12
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test.spring.beanPostProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


/**
 * @author chendong
 * 
 * 1、postProcessBeforeInitialization：在初始化Bean之前
 * 2、postProcessAfterInitialization：在初始化Bean之后
 * 这两个方法是有返回值的，不要返回null，否则getBean的时候拿不到对象。
 * 
 */
public class PostProcessorBean implements BeanPostProcessor {
  
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    System.err.println("Enter PostProcessorBean.postProcessBeforeInitialization()");
    return bean;
  }
  
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    System.err.println("Enter PostProcessorBean.postProcessAfterInitialization()");
    Map<String, String> map = new HashMap<String, String>();
    map.put("shabi", "0fly");
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    list.add(map);
    return bean;
  }
  
}
