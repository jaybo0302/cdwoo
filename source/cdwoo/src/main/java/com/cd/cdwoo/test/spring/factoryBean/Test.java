/**
 * File：Test.java
 * Package：com.cd.cdwoo.test.spring.factoryBean
 * Author：chendong
 * Date：2016年12月19日 上午10:49:14
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test.spring.factoryBean;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author chendong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/appContext-test.xml"})
public class Test {

   @Resource
   private Animal animal;
   
   @org.junit.Test
   public void aa() {
     animal.move();
   }
}
