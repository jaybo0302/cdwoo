/**
 * File：FactoryPostProcessorBean.java
 * Package：com.cd.cdwoo.test.spring.beanPostProcess
 * Author：chendong
 * Date：2016年12月19日 下午1:54:58
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test.spring.beanPostProcess;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;


/**
 * @author chendong
 * 1、BeanFactoryPostProcessor的执行优先级高于BeanPostProcessor
 * 2、BeanFactoryPostProcessor的postProcessBeanFactory()方法只会执行一次
 * 注意到postProcessBeanFactory方法是带了参数ConfigurableListableBeanFactory的，这就和我之前说的可以使用BeanFactoryPostProcessor来改变Bean的属性相对应起来了。
 */
public class FactoryPostProcessorBean implements BeanFactoryPostProcessor {
  
  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory configurablelistablebeanfactory)
      throws BeansException {
    System.err.println("Enter FactoryPostProcessorBean.postProcessBeanFactory()");
    BeanDefinition beanDefinition = configurablelistablebeanfactory.getBeanDefinition("common0");
    MutablePropertyValues beanProperty = beanDefinition.getPropertyValues();
    System.err.println("scope before change：" + beanDefinition.getScope());
    beanDefinition.setScope("singleton");
    System.err.println("scope after change：" + beanDefinition.getScope());
    System.err.println("beanProperty：" + beanProperty);
  }
}
