/**
 * File：QuartzJob.java
 * Package：com.fang.cms.common
 * Author：
 * Date：2017年4月7日 上午10:45:34
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.common;

import java.util.HashMap;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.cd.cdwoo.core.entity.QuartzJobFactory;
import com.cd.cdwoo.core.entity.ScheduleJob;

/**
 * Desc : 定时任务启动器 .
 * @author 
 */
public final class QuartzJob {
  /**
   * Field : 日志 .
   * Add By 
   * 2017年4月7日 下午3:45:59
   */
  private static final Logger LOG = LoggerFactory.getLogger(QuartzJob.class);
  /**
   * Field : spring 上下文 .
   * Add By 
   * 2017年4月7日 下午3:46:26
   */
  private ApplicationContext context;
  /**
   * Constructor .
   * Add by 
   */
  public QuartzJob() {
    super();
  }
  /**
   * Constructor .
   * Add by 
   * @param context spring 上下文
   */
  public QuartzJob(ApplicationContext context) {
    this.context = context;
  }
  /**
   * Desc : 定时任务启动设置 .
   * @author 
   * @date 2017年4月10日 上午9:22:32
   * @throws SchedulerException 定时任务异常
   */
  public void work() throws SchedulerException {
    LOG.info("quartz is running ...");
    // scheduler 对象
    Scheduler schedulerFactoryBean = (Scheduler) context.getBean("schedulerFactoryBean");
    ScheduleJob job = new ScheduleJob();
    // 作业id 任务检查定时任务
    job.setJobId("taskCheck");
    // 作业名称
    job.setJobName("checkJob");
    // 作业分组
    job.setJobGroup("system");
    // 作业目标路径
    job.setJobClass("com.cd.cdwoo.core.task.instance.JobTask");
    // 作业所需参数
    job.setJobParams(new HashMap<String, Object>());
    // 作业调度规则
    job.setCronExpression("0 */1 * * * ?");
    // 作业说明
    job.setDesc("定时作业配置检查任务");
    // 作业状态
    job.setJobStatus("0");
    // 所有作业 从配置文件获取
    // Constant.ALL_QUARTZ_JOBS.add(sJob);
    // 启动定时任务
    Scheduler scheduler = schedulerFactoryBean;
    TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
    // 不存在，创建一个
    if (null == trigger) {
      JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(job.getJobName(), job.getJobGroup()).build();
      JobDataMap dataMap = jobDetail.getJobDataMap();
      dataMap.put("scheduleJob", job); // 传递 job 对象至执行的方法体
      // 表达式调度构建器
      CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
      // 按新的cronExpression表达式构建一个新的trigger
      trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).withDescription(job.getDesc()).build();
      scheduler.scheduleJob(jobDetail, trigger);
    } else {
      // Trigger已存在，那么更新相应的定时设置
      // 表达式调度构建器
      CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
      // 按新的cronExpression表达式重新构建trigger
      trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
      // 按新的trigger重新设置job执行
      scheduler.rescheduleJob(triggerKey, trigger);
    }
  }
}