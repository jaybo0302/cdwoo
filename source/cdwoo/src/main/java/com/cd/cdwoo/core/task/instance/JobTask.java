/**
 * File：JobTask.java
 * Package：com.fang.cms.core.task.instance
 * Author：
 * Date：2017年4月8日 上午10:14:16
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.core.task.instance;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.cd.cdwoo.common.Constants;
import com.cd.cdwoo.common.IJobPlanProcess;
import com.cd.cdwoo.core.entity.QuartzJobFactory;
import com.cd.cdwoo.core.entity.ScheduleJob;
import com.cd.cdwoo.util.CommonUtil;
import com.cd.cdwoo.util.SpringBeanUtils;

/**
 * Desc : ！！！！！！！！ 定时任务自我检查 请勿修改及改动本类！！！！！！！！.
 * @author 
 */
public class JobTask implements IJobPlanProcess {
  /**
   * Field : 定时作业工厂常量 .
   * Add By 
   * 2017年4月8日 下午12:24:58
   */
  public static final Scheduler SCHEDULER = SpringBeanUtils.getBean("schedulerFactoryBean", Scheduler.class);
  /**
   * It is an overriding method . <br>
   * 定时任务执行方法
   * @see com.cd.cdwoo.common.IJobPlanProcess#process(java.util.Map)
   */
  @Override
  public void process(Map<String, Object> param) {
    // 定时更新任务列表
    Constants.ALL_QUARTZ_JOBS.clear();
    Constants.ALL_QUARTZ_JOBS.addAll(CommonUtil.getQuzrtzs());
    // 修改定时任务
    for (ScheduleJob job : Constants.ALL_QUARTZ_JOBS) {
      // 任务主键
      JobKey jk = new JobKey(job.getJobName(), job.getJobGroup());
      // 触发器主键
      TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
      try {
        CronTrigger trigger = (CronTrigger) SCHEDULER.getTrigger(triggerKey);
        if (SCHEDULER.checkExists(jk) && (Integer.valueOf(job.getJobStatus()) == 1)) {
          // 任务已经存在，且任务已失效则移除任务
          SCHEDULER.deleteJob(jk);
          if (!(null == trigger)) {
            // 停止调度触发器相关job
            SCHEDULER.unscheduleJob(triggerKey);
          }
          continue;
        }
        // 不存在，创建一个
        if (null == trigger) {
          JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(job.getJobName(), job.getJobGroup()).build();
          JobDataMap dataMap = jobDetail.getJobDataMap();
          dataMap.put("scheduleJob", job); // 传递 job 对象至执行的方法体
          // 表达式调度构建器
          CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
          // 按新的cronExpression表达式构建一个新的trigger
          trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).withDescription(job.getDesc()).build();
          SCHEDULER.scheduleJob(jobDetail, trigger);
        } else {
          // Trigger已存在，那么更新相应的定时设置
          // 表达式调度构建器
          CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
          // 按新的cronExpression表达式重新构建trigger
          trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
          // 按新的trigger重新设置job执行
          SCHEDULER.rescheduleJob(triggerKey, trigger);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  /**
   * It is an overriding method . <br>
   * 定时任务成功执行方法
   * @see com.fang.cms.common.IJobPlanProcess#success()
   */
  @Override
  public void success() {
  }
  /**
   * It is an overriding method . <br>
   * 定时任务失败执行方法
   * @see com.fang.cms.common.IJobPlanProcess#failed(java.lang.Exception)
   */
  @Override
  public void failed(Exception e) {
  }
}
