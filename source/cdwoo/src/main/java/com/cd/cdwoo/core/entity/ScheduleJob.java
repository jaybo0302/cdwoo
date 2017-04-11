/**
 * File：ScheduleJob.java
 * Package：com.fang.cms.core.entity
 * Author：
 * Date：2017年4月7日 上午10:41:52
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.core.entity;

import java.util.Map;

/**
 * Desc : 定时任务信息 .
 * @author 
 */
public class ScheduleJob {
  /**
   * Field : 任务id .
   * Add By 
   * 2017年4月7日 上午10:42:31
   */
  private String jobId;
  /**
   * Field : 任务名称 .
   * Add By 
   * 2017年4月7日 上午10:42:38
   */
  private String jobName;
  /**
   * Field : 任务分组 .
   * Add By 
   * 2017年4月7日 上午10:43:01
   */
  private String jobGroup;
  /**
   * Field : 0禁用 1启用 2删除 .
   * Add By 
   * 2017年4月7日 上午10:43:11
   */
  private String jobStatus;
  /**
   * Field : 作业指向路径 .
   * Add By 
   * 2017年4月7日 上午11:20:55
   */
  private String jobClass;
  /**
   * Field : 作业参数 .
   * Add By 
   * 2017年4月7日 下午2:14:47
   */
  private Map<String, Object> jobParams;
  /**
   * Field : 任务运行时间表达式 .
   * Add By 
   * 2017年4月7日 上午10:43:20
   */
  private String cronExpression;
  /**
   * Field : 任务描述 .
   * Add By 
   * 2017年4月7日 上午10:43:28
   */
  private String desc;
  /**
   * getter
   * @return jobId
   */
  public String getJobId() {
    return jobId;
  }
  /**
   * setter
   * @param jobId set jobId
   */
  public void setJobId(String jobId) {
    this.jobId = jobId;
  }
  /**
   * getter
   * @return jobName
   */
  public String getJobName() {
    return jobName;
  }
  /**
   * setter
   * @param jobName set jobName
   */
  public void setJobName(String jobName) {
    this.jobName = jobName;
  }
  /**
   * getter
   * @return jobGroup
   */
  public String getJobGroup() {
    return jobGroup;
  }
  /**
   * setter
   * @param jobGroup set jobGroup
   */
  public void setJobGroup(String jobGroup) {
    this.jobGroup = jobGroup;
  }
  /**
   * getter
   * @return jobStatus
   */
  public String getJobStatus() {
    return jobStatus;
  }
  /**
   * setter
   * @param jobStatus set jobStatus
   */
  public void setJobStatus(String jobStatus) {
    this.jobStatus = jobStatus;
  }
  /**
   * getter
   * @return cronExpression
   */
  public String getCronExpression() {
    return cronExpression;
  }
  /**
   * setter
   * @param cronExpression set cronExpression
   */
  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }
  /**
   * getter
   * @return desc
   */
  public String getDesc() {
    return desc;
  }
  /**
   * setter
   * @param desc set desc
   */
  public void setDesc(String desc) {
    this.desc = desc;
  }
  /**
   * getter
   * @return jobClass
   */
  public String getJobClass() {
    return jobClass;
  }
  /**
   * setter
   * @param jobClass set jobClass
   */
  public void setJobClass(String jobClass) {
    this.jobClass = jobClass;
  }
  /**
   * getter
   * @return jobParams
   */
  public Map<String, Object> getJobParams() {
    return jobParams;
  }
  /**
   * setter
   * @param jobParams set jobParams
   */
  public void setJobParams(Map<String, Object> jobParams) {
    this.jobParams = jobParams;
  }
}
