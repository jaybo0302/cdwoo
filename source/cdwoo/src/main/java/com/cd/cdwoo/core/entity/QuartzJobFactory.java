/**
 * File：QuartzJobFactory.java
 * Package：com.fang.cms.quartz
 * Author：
 * Date：2017年4月7日 上午10:40:00
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.core.entity;

import java.lang.reflect.Method;
import java.util.Map;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Desc : 有状态定时作业工厂类 .
 * @author 
 */
@DisallowConcurrentExecution
public class QuartzJobFactory extends QuartzJobBean {
  /**
   * Field : 日志 .
   * Add By 
   * 2017年4月7日 下午2:39:21
   */
  private static final Logger LOG = (Logger) LoggerFactory.getLogger(QuartzJobFactory.class);
  /**
   * Field : 定时任务信息 .
   * Add By 
   * 2017年4月7日 下午4:04:06
   */
  private ScheduleJob scheduleJob;
  /**
   * It is an overriding method .
   * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
   */
  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    try {
      Class<?> targetClass = Class.forName(scheduleJob.getJobClass());
      Object targetObject = targetClass.newInstance();
      Method process;
      try {
        // 获取到方法对象
        process = targetClass.getMethod("process", new Class[] {Map.class});
        process.invoke(targetObject, scheduleJob.getJobParams());
      } catch (SecurityException e) {
        LOG.error(e.getMessage(), e);
      } catch (NoSuchMethodException e) {
        LOG.error(e.getMessage(), e);
      }
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new JobExecutionException(e);
    }
  }
  /**
   * Desc : 获取定时任务 .
   * @author 
   * @date 2017年4月10日 上午9:25:16
   * @param scheduleJob 定时任务实体
   */
  public void setScheduleJob(ScheduleJob scheduleJob) {
    this.scheduleJob = scheduleJob;
  }
}
