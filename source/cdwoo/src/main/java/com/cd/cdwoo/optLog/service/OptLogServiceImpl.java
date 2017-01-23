package com.cd.cdwoo.optLog.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cd.cdwoo.optLog.dao.OptLogDao;
import com.cd.cdwoo.optLog.entity.OptLog;
/**
 * 日志业务实现类层
 * 
 * @author chendong
 */
@Service
public class OptLogServiceImpl  implements OptLogService {
  @Autowired
  private OptLogDao optLogDao;
  public void addOptLog(OptLog optLog) {
    optLogDao.addOptLog(optLog);
  }
  public OptLog queryById(String id) {
    return (OptLog) optLogDao.getOptLogById(id);
  }
}
