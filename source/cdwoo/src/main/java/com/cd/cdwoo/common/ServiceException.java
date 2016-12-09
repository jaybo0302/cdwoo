package com.cd.cdwoo.common;

import java.io.Serializable;

/**
 * service层操作结果(异常)
 * 
 * @author chendong
 */
public class ServiceException extends RuntimeException implements Serializable {
  
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 2980241194592741348L;
  
  /**
   * 业务逻辑操作信息
   */
  private ServiceResult serviceResult;
  
  /**
   * 构造方法
   * 
   * @param serviceResult
   *        业务逻辑操作信息，见{@link ServiceResult}
   */
  public ServiceException(ServiceResult serviceResult) {
    super(serviceResult.getErrorMessage());
    this.serviceResult = serviceResult;
  }
  
  /**
   * 获取操作描述getter
   * 
   * @return serviceResult.getDesc()
   */
  public String getOptDesc() {
    return serviceResult.getDesc();
  }
  
  /**
   * 获取serviceResult
   * 
   * @return serviceResult
   */
  public ServiceResult getServiceResult() {
    return serviceResult;
  }
  
}
