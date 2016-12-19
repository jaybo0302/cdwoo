/**
 * File：LifeCycleBean.java
 * Package：com.cd.cdwoo.test.spring
 * Author：chendong
 * Date：2016年12月19日 上午10:12:09
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * @author chendong
 * 1、InitializingBean接口、Disposable接口可以和init-method、destory-method配合使用，接口执行顺序优先于配置
 * 2、InitializingBean接口、Disposable接口底层使用类型强转.方法名()进行直接方法调用，init-method、destory-method底层使用反射，前者和Spring耦合程度更高但效率高，后者解除了和Spring之间的耦合但是效率低，使用哪个看个人喜好
 * 3、afterPropertiesSet()方法是在Bean的属性设置之后才会进行调用，某个Bean的afterPropertiesSet()方法执行完毕才会执行下一个Bean的afterPropertiesSet()方法，因此不建议在afterPropertiesSet()方法中写处理时间太长的方法
 */
public class LifeCycleBean implements InitializingBean, DisposableBean {
  
  @SuppressWarnings("unused")
  private String lifeCycleBeanName;
  
  /**
   * @param lifeCycleBeanName set lifeCycleBeanName
   */
  public void setLifeCycleBeanName(String lifeCycleBeanName) {
    System.err.println("Enter LifeCycleBean.setLifeCycleBeanName(), lifeCycleBeanName = " + lifeCycleBeanName);
    this.lifeCycleBeanName = lifeCycleBeanName;
  }

  @Override
  public void destroy() throws Exception {
    System.err.println("Enter LifeCycleBean.destory()");
  }
  
  @Override
  public void afterPropertiesSet() throws Exception {
    System.err.println("Enter LifeCycleBean.afterPropertiesSet()");
  }
  
  public void beanStart() {
    System.err.println("Enter LifeCycleBean.beanStart()");
  }
  
  public void beanEnd() {
    System.err.println("Enter LifeCycleBean.beanEnd()");
  }
}
