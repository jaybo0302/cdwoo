package com.cd.cdwoo.common;

/**
 * service层操作结果
 * 
 * @author chendong
 */
public class ServiceResult {
  
  /**
   * 描述
   * 
   */
  private String desc;
  
  /**
   * 错误信息
   * 
   */
  private String errorMessage;
  
  /**
   * 构造方法
   * 
   * @param desc
   */
  public ServiceResult(String desc) {
    this.desc = desc;
  }
  
  /**
   * @return desc
   */
  public String getDesc() {
    return desc;
  }
  
  /**
   * @param desc
   *        set desc
   */
  public void setDesc(String desc) {
    this.desc = desc;
  }
  
  /**
   * @return errorMessage
   */
  public String getErrorMessage() {
    return errorMessage;
  }
  
  /**
   * @param errorMessage
   *        set errorMessage
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
  
}
