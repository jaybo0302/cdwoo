package com.cd.cdwoo.web.aop;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.cd.cdwoo.common.ServiceException;
import com.cd.cdwoo.common.ServiceResult;
import com.cd.cdwoo.optLog.entity.OptLog;
import com.cd.cdwoo.optLog.service.OptLogService;
import com.cd.cdwoo.util.CDWooLogger;
/**
 * 业务逻辑层AOP，用于对业务逻辑层方法执行统一的AOP处理，如下： <li>显示操作结果提示信息</li> <li>记录操作日志</li>
 * @author chendong
 */
public class ServiceAop {
  /**
   * 日志内部类，用于对业务逻辑层方法执行统一的AOP处理，
   * SUCCESS , FAIL
   */
  final class OptResult {
    /**
     * 成功
     * SUCCESS
     */
    static final String SUCCESS = "操作成功";
    /**
     * 失败
     * FAIL
     */
    static final String FAIL = "操作失败";
    /**
     * 构造方法私有化, 避免外部将其调用
     */
    private OptResult() {
    }
  }
  /**
   * 操作结果保留长度
   */
  private static final int OPT_DESC_RESERVE_LENGTH = 252;
  /**
   * 操作日志业务逻辑接口
   */
  @Autowired
  private OptLogService optLogService;
  /**
   * 拦截业务逻辑方法成功执行
   * 
   * @param serviceResult
   *        返回的操作信息
   */
  public void afterReturn(ServiceResult serviceResult) {
    // CDWooLogger.info("后置增强方法已经进入");
    if (serviceResult == null) {
      CDWooLogger.error("serviceResult为空");
      return;
    }
    //CDWooLogger.info("aop retrun success");
    // 记录操作日志
    OptLog optLog = createOptLog(serviceResult);
    optLogService.addOptLog(optLog);
  }
  /**
   * 拦截业务逻辑方法执行失败
   * 
   * @param ex
   *        业务逻辑方法抛出的异常
   */
  public void atferException(ServiceException ex) {
    // CDWooLogger.info("异常增强方法已经进入");
    // 记录操作日志
    OptLog optLog = createFailOptLog(ex);
    optLogService.addOptLog(optLog);
  }
  /**
   * 创建操作日志
   * 
   * @param serviceResult
   *        操作信息
   * @return 操作日志
   */
  private OptLog createOptLog(ServiceResult serviceResult) {
    String optDesc = serviceResult.getDesc();
    OptLog optLog = new OptLog();
    // 添加登录操作员的ip姓名
    optLog.setOperatorName("");
    optLog.setOperatorIp("");
    optLog.setOptTime(DateUtils.truncate(new Date(), Calendar.MILLISECOND).getTime());
    if (optDesc.length() > OPT_DESC_RESERVE_LENGTH) {
      optDesc = StringUtils.substring(optDesc, 0, OPT_DESC_RESERVE_LENGTH) + "...";
    }
    optLog.setOptLogDesc(optDesc);
    optLog.setOptResult(OptResult.SUCCESS);
    
    return optLog;
  }
  /**
   * 创建操作失败的操作日志
   * 
   * @param se
   *        业务逻辑异常
   * @return 操作失败的操作日志
   */
  private OptLog createFailOptLog(ServiceException se) {
    OptLog optLog = createOptLog(se.getServiceResult());
    String errorMessage = se.getMessage();
    if (errorMessage.length() > OPT_DESC_RESERVE_LENGTH) {
      errorMessage = StringUtils.substring(errorMessage, 0, OPT_DESC_RESERVE_LENGTH) + "...";
    }
    optLog.setOptResult(OptResult.FAIL);
    optLog.setFailReason(errorMessage);
    
    return optLog;
  }
}
