package com.cd.cdwoo.optLog.dao;
import org.springframework.stereotype.Repository;

import com.cd.cdwoo.optLog.entity.OptLog;

/**
 * OptLogDao
 * 
 * @author chendong
 */
public interface OptLogDao  {

  /**
   * @param optLog
   */
  void addOptLog(OptLog optLog);

  /**
   * @param id
   * @return
   */
  OptLog getOptLogById(String id);
  
}
