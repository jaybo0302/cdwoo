/**
 * File：FirstTask.java
 * Package：com.cd.cdwoo.core.task.instance
 * Author：chendong.bj@fang.com
 * Date：2017年4月11日 上午10:16:39
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.core.task.instance;

import java.util.Map;

import com.cd.cdwoo.common.IJobPlanProcess;


/**
 * @author chendong.bj@fang.com
 */
public class FirstTask implements IJobPlanProcess {
  
  @Override
  public void process(Map<String, Object> param) {
    System.err.println("first task is process");
  }
  
  @Override
  public void success() {
    System.err.println("first task is success");
  }
  
  @Override
  public void failed(Exception e) {
    System.err.println("first task is failed");
  }
  
}
