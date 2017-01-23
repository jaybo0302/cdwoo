package com.cd.cdwoo.optLog.entity;

import com.cd.cdwoo.annotation.ColumnName;
import com.cd.cdwoo.common.FormBean;
/**
 * 日志搜索实体formbean
 * 
 * @author chendong
 */
public class OptLogFormBean extends FormBean {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * Id
   */
  private Long id;
  /**
   * 操作员登录名
   */
  private String operatorName;
  /**
   * 操作员登录IP
   */
  private String operatorIp;
  /**
   * optTime 为数据库中字段
   * 因为查询的开始时间个结束时间都是用optTime 去匹配
   */
  @ColumnName("optTime")
  private String startDate;
  /**
   * createDate optTime
   */
  @ColumnName("optTime")
  private String endDate;
  /**
   * 操作描述。
   */
  private String optLogDesc;
  /**
   * 操作结果
   */
  private String optResult;
  /**
   * 失败原因。对于操作成功的情况，该字段无意义。
   */
  private String failReason;
  /**
   * @return id
   */
  public Long getId() {
    return id;
  }
  /**
   * @param id
   *        set id
   */
  public void setId(Long id) {
    this.id = id;
  }
  /**
   * @return operatorName
   */
  public String getOperatorName() {
    return operatorName;
  }
  /**
   * @param operatorName
   *        set operatorName
   */
  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }
  /**
   * @return operatorIp
   */
  public String getOperatorIp() {
    return operatorIp;
  }
  /**
   * @param operatorIp
   *        set operatorIp
   */
  public void setOperatorIp(String operatorIp) {
    this.operatorIp = operatorIp;
  }
  /**
   * @return startDate
   */
  public String getStartDate() {
    return startDate;
  }
  /**
   * @param startDate
   *        set startDate
   */
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }
  /**
   * @return endDate
   */
  public String getEndDate() {
    return endDate;
  }
  /**
   * @param endDate
   *        set endDate
   */
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
  /**
   * @return optLogDesc
   */
  public String getOptLogDesc() {
    return optLogDesc;
  }
  /**
   * @param optLogDesc
   *        set optLogDesc
   */
  public void setOptLogDesc(String optLogDesc) {
    this.optLogDesc = optLogDesc;
  }
  /**
   * @return optResult
   */
  public String getOptResult() {
    return optResult;
  }
  /**
   * @param optResult
   *        set optResult
   */
  public void setOptResult(String optResult) {
    this.optResult = optResult;
  }
  /**
   * @return failReason
   */
  public String getFailReason() {
    return failReason;
  }
  /**
   * @param failReason
   *        set failReason
   */
  public void setFailReason(String failReason) {
    this.failReason = failReason;
  }
}
