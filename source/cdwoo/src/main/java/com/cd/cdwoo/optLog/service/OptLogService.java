package com.cd.cdwoo.optLog.service;

import com.cd.cdwoo.optLog.entity.OptLog;

/**
 * 日志业务层
 * 
 * @author cd
 */
public interface OptLogService {
  
  /**
   * 增加操作日志
   * 
   * @param optLog
   *        要增加的操作日志
   */
  void addOptLog(OptLog optLog);
  
  /**
   * 查询操作日志。
   * 
   * @param id
   * @return pagedResult
   */
  OptLog queryById(String id);
  
}
