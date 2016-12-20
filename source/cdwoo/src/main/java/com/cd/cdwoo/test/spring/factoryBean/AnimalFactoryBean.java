/**
 * File：AnimalFactoryBean.java
 * Package：com.cd.cdwoo.test.spring.factoryBean
 * Author：chendong
 * Date：2016年12月19日 上午10:44:12
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test.spring.factoryBean;

import org.springframework.beans.factory.FactoryBean;


/**
 * @author chendong
 *　1、getObject()方法是最重要的，控制Bean的实例化过程
 *　2、getObjectType()方法获取接口返回的实例的class
 *　3、isSingleton()方法获取该Bean是否为一个单例的Bean
 */
public class AnimalFactoryBean implements FactoryBean<Animal> {

  private String animal;
  @Override
  public Animal getObject() throws Exception {
    if ("mokey".equals(animal)) {
      return new Mokey();
    } else if ("tiger".equals(animal)) {
      return new Tiger();
    } else {
      return null;
    }
  }

  @Override
  public Class<?> getObjectType() {
    return Animal.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }
  
  public void setAnimal(String animal) {
    this.animal = animal;
  }
}
