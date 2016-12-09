/**
 * File：AuditLogUtil.java
 * Package：com.fang.bdp.core.util
 * Author：zhaolingfei
 * Date：2016年6月28日 上午11:29:40
 * Version: V1.0
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.util;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * 项目自查辅助类.
 * @author zhaolingfei
 */
public class AuditLogUtil {
  //logger工厂 新建自定义标签 AUDIT
  private static final Logger auditLog = (Logger) LoggerFactory.getLogger("AUDIT");
  /**
   * <p>
   * 错误异常日志.
   * </p>
   * @param ex 错误异常
   */
  public static void auditLog(Throwable ex) {
    StackTraceElement[] stes = ex.getStackTrace();
    // 日志开始时间
    StringBuilder strLog = new StringBuilder("[ERROR - AuditLog] Cause By :");
    strLog.append(ex.getClass());
    strLog.append(" From : ");
    strLog.append(stes[0].getClassName());
    strLog.append(" Method : ");
    strLog.append(stes[0].getMethodName());
    strLog.append(" LineNum : ");
    strLog.append(stes[0].getLineNumber());
    auditLog.info(strLog.toString());
  }
  /**
   * <p>
   * 方法超时日志.
   * </p>
   * @param className 类名
   * @param methodName 方法名
   * @param costTime 耗费时长
   */
  public static void auditLog(String className, String methodName, long costTime) {
    StringBuilder strLog = new StringBuilder("[WARN - AutoLog] Cause By : Load time is too long From : ");
    strLog.append(className);
    strLog.append(" Method : ");
    strLog.append(methodName);
    strLog.append(" Cost : [ ");
    strLog.append(costTime);
    strLog.append(" ] ms");
    auditLog.info(strLog.toString());
  }
  
  /**
   * <p>
   * 连接池爆满.
   * </p>
   * @param className
   * @param maxCount
   */
  public static void auditLog(String className, int maxCount) {
    StringBuilder strLog = new StringBuilder("[ERROR - AutoLog] Cause By : The number of connection is maximum From : ");
    strLog.append(className);
    strLog.append(" maxCount : [ ");
    strLog.append(maxCount);
    strLog.append(" ]");
    auditLog.info(strLog.toString());
  }
  /**
   * <p>
   * 连接池报警.
   * </p>
   * @param className
   * @param maxCount
   * @param activePool
   */
  public static void auditLog(String className, int maxCount, int activePool) {
    StringBuilder strLog = new StringBuilder("[WARN - AutoLog] Cause By : The number of connections will reach the peak From : ");
    strLog.append(className);
    strLog.append(" maxCount : [ ");
    strLog.append(maxCount);
    strLog.append(" ] activePool : [ ");
    strLog.append(activePool);
    strLog.append(" ]");
    auditLog.info(strLog.toString());
  }
  /**
   * <p>
   * 敏感 操作日志.
   * </p>
   * @param className 类名
   * @param methodName 方法名
   * @param params 操作数据
   */
  public static void auditLog(String className, String methodName, String params) {
    StringBuilder strLog = new StringBuilder("[ALERT - AutoLog] Cause By : This operation is sensitive From : ");
    strLog.append(className);
    strLog.append(" Method : ");
    strLog.append(methodName);
    auditLog.info(strLog.toString());
  }
  /**
   * <p>
   * 接口响应警戒.
   * </p>
   * @param code 状态码
   * @param method 请求方法 post/get
   * @param url 请求url
   * @param time 耗时
   * @param result 返回结果
   */
  public static void auditLog(String code, String method, String url, String result) {
    StringBuilder strLog = new StringBuilder("[ERROR - AutoLog] Cause By : HttpURLConnection info From : ");
    strLog.append(url);
    strLog.append(" Method : [ ");
    strLog.append(method);
    strLog.append(" ] ResponseCode : [ ");
    strLog.append(code);
    strLog.append(" ] result : [ " + result + " ]");
    auditLog.info(strLog.toString());
  }
  /**
   * <p>
   * 接口超时响应警戒.
   * @param code 状态码
   * @param method 请求方法 post/get
   * @param url 请求地址
   * @param time 耗时
   * @param result 结果
   */
  public static void auditLog(String code, String method, String url, long time, String result) {
    StringBuilder strLog = new StringBuilder("[WARN - AutoLog] Cause By : The httpconnection is to long From : ");
    strLog.append(url);
    strLog.append(" Method : [ ");
    strLog.append(method);
    strLog.append(" ] ResponseCode : [ ");
    strLog.append(code);
    strLog.append(" ] result : [ ");
    strLog.append(result);
    strLog.append(" ] Cost : [ ");
    strLog.append(time);
    strLog.append(" ] ms");
    auditLog.info(strLog.toString());
  }
}
