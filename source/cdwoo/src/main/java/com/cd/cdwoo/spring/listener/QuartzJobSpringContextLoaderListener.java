package com.cd.cdwoo.spring.listener;


import javax.servlet.ServletContextEvent;

import org.quartz.SchedulerException;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cd.cdwoo.common.QuartzJob;

/**
 * Desc :重写spring的监听器, 项目启动时加载定时任务 .
 * @author 
 */
public class QuartzJobSpringContextLoaderListener extends ContextLoaderListener {
  @Override
  public void contextInitialized(ServletContextEvent event) {
    super.contextInitialized(event);
    // 启动定时任务
    QuartzJob quartz = new QuartzJob(WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext()));
    try {
      quartz.work();
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }
}
