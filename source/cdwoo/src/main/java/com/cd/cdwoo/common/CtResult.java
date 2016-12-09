/**
 * File：InvokeResult.java
 * Package：com.fang.bdp.core.commons
 * Author：wangjiashuai
 * Date：2015-6-9 下午3:33:03
 * Copyright (C) 2003-2015 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.common;

/**
 * controller层操作结果
 * 
 * @author chendong
 */
public class CtResult {
  
  /**
   * data
   */
  private Object data;
  
  /**
   * 错误消息
   */
  private String errorMessage;
  
  /**
   * 是否有错误
   */
  private boolean hasErrors;
  /**
   * 操作成功，并返回成功结果
   * 
   * @param data
   *        data
   * @return result
   */
  public static CtResult success(Object data) {
    CtResult result = new CtResult();
    result.data = data;
    result.hasErrors = false;
    return result;
  }
  
  /**
   * 操作成功
   * 
   * @return result
   */
  public static CtResult success() {
    CtResult result = new CtResult();
    result.hasErrors = false;
    return result;
  }
  
  /**
   * 操作失败
   * 
   * @param message
   *        失败信息
   * @return result
   */
  public static CtResult failure(String message) {
    CtResult result = new CtResult();
    result.hasErrors = true;
    result.errorMessage = message;
    return result;
  }
  
  /**
   * 
   * @return data
   */
  public Object getData() {
    return data;
  }
  
  /**
   * 
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
  
  /**
   * 
   * @return hasErrors
   */
  public boolean isHasErrors() {
    return hasErrors;
  }
  
  /**
   * 
   * @return !hasErrors
   */
  public boolean isSuccess() {
    return !hasErrors;
  }
}
 