/**
 * File：IJobPlanProcess.java
 * Package：com.fang.cms.common
 * Author：
 * Date：2017年4月7日 下午1:48:31
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.common;

import java.util.Map;

/**
 * Desc : 定时任务实现接口 .
 * @author 
 */
public interface IJobPlanProcess {
  /**
   * Desc : 执行方法 .
   * @author 
   * @date 2017年4月7日 下午1:48:59
   * @param param 参数列表
   */
  void process(Map<String, Object> param);
  /**
   * Desc : 成功方法 .
   * @author 
   * @date 2017年4月7日 下午1:49:12
   */
  void success();
  /**
   * Desc : 失败方法 .
   * @author 
   * @date 2017年4月7日 下午1:49:20
   * @param e 异常
   */
  void failed(Exception e);
}
