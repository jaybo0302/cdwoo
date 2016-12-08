package com.cd.cdwoo.optLog.service;

import com.cd.cdwoo.common.BaseServiceImpl;
import com.cd.cdwoo.optLog.entity.OptLog;

/**
 * 日志业务实现类层
 * 
 * @author wangyouwei
 */
public class OptLogServiceImpl extends BaseServiceImpl<OptLog> implements OptLogService {
  
  public void addOptLog(OptLog optLog) {
    super.addObject(optLog);
  }
  
  public OptLog queryById(String id) {
    return (OptLog) super.getObjectById(id);
  }
}
