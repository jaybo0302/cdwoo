/**
 * File：SpringBeanUtils.java
 * Package：com.fang.cms.utils
 * Author：feix [ zhaolingfei@fang.com ]
 * Date：2017年4月7日 上午10:52:13
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.util;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Desc : SpringBeanUtils .
 * @author feix [ zhaolingfei@fang.com ]
 */
@Component
public class SpringBeanUtils implements ApplicationContextAware {
  /**
   * Field : 创建日志 .
   * Add By feix [ zhaolingfei@fang.com ]
   * 2017年4月7日 上午10:54:51
   */
  private static final Logger LOG = Logger.getLogger(SpringBeanUtils.class);
  /**
   * Field : spring 上下文 .
   * Add By feix [ zhaolingfei@fang.com ]
   * 2017年4月7日 上午10:53:19
   */
  private static ApplicationContext context;
  /**
   * Desc : 获取spring管理的bean .<br>
   * eg.<br>
   * <code>ResourceService rs = (ResourceService)SpringBeanUtils.getBean("resourceService")</code>
   * @author feix [ zhaolingfei@fang.com ]
   * @date 2017年4月7日 上午10:56:17
   * @param name beanName
   * @return bean对象
   */
  public static Object getBean(String name) {
    try {
      return context.getBean(name);
    } catch (BeansException e) {
      LOG.error(" [internal] Get bean {" + name + "} from spring context error : \n {" + e.toString() + "}");
      return null;
    }
  }
  /**
   * Desc : 获取spring管理的bean .<br>
   * eg.<br>
   * <code>ResourceService rs = (ResourceService)SpringBeanUtils.getBean("resourceService")</code>
   * @author feix [ zhaolingfei@fang.com ]
   * @date 2017年4月7日 上午11:00:32
   * @param name beanName
   * @param requiredType 类
   * @param <T> 泛型
   * @return bean对象
   */
  public static <T> T getBean(String name, Class<T> requiredType) {
    try {
      return context.getBean(requiredType, name);
    } catch (BeansException e) {
      LOG.error(" [internal] Get bean {" + name + "} from spring context error : \n {" + e.toString() + "}");
      return null;
    }
  }
  /**
   * It is an overriding method .
   * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }
  /**
   * getter
   * @return context
   */
  public static ApplicationContext getContext() {
    return context;
  }
  /**
   * setter
   * @param context set context
   */
  public static void setContext(ApplicationContext context) {
    SpringBeanUtils.context = context;
  }
}
