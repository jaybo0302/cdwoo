package com.cd.cdwoo.util;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

import ch.qos.logback.classic.Logger;

/**
 * logback封装类
 * 
 * @author chendong
 * 
 */
public final class CDWooLogger {
  
  private static final String FQCN = CDWooLogger.class.getName();
  
  private static final Logger logger = (Logger) LoggerFactory.getLogger(CDWooLogger.class);
  
  private CDWooLogger() {
    
  }
  
  public static boolean isTraceEnabled() {
    return logger.isTraceEnabled();
  }
  
  public static void trace(String msg) {
    logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, msg, null, null);
  }
  
  public static void trace(String format, Object arg) {
    logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, format, new Object[] {arg}, null);
  }
  
  public static void trace(String format, Object arg1, Object arg2) {
    logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, format, new Object[] {arg1, arg2}, null);
  }
  
  public static void trace(String format, Object... arguments) {
    logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, format, arguments, null);
  }
  
  public static void trace(String msg, Throwable t) {
    logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, msg, null, t);
  }
  
  public static boolean isTraceEnabled(Marker marker) {
    return logger.isTraceEnabled(marker);
  }
  
  public static void trace(Marker marker, String msg) {
    logger.log(marker, FQCN, LocationAwareLogger.TRACE_INT, msg, null, null);
  }
  
  public static void trace(Marker marker, String format, Object arg) {
    logger.log(marker, FQCN, LocationAwareLogger.TRACE_INT, format, new Object[] {arg}, null);
  }
  
  public static void trace(Marker marker, String format, Object arg1, Object arg2) {
    logger
        .log(marker, FQCN, LocationAwareLogger.TRACE_INT, format, new Object[] {arg1, arg2}, null);
  }
  
  public static void trace(Marker marker, String format, Object... argArray) {
    logger.log(marker, FQCN, LocationAwareLogger.TRACE_INT, format, argArray, null);
  }
  
  public static void trace(Marker marker, String msg, Throwable t) {
    logger.log(marker, FQCN, LocationAwareLogger.TRACE_INT, msg, null, t);
  }
  
  public static boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }
  
  public static void debug(String msg) {
    logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, msg, null, null);
  }
  
  public static void debug(String format, Object arg) {
    logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, format, new Object[] {arg}, null);
  }
  
  public static void debug(String format, Object arg1, Object arg2) {
    logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, format, new Object[] {arg1, arg2}, null);
  }
  
  public static void debug(String format, Object... arguments) {
    logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, format, arguments, null);
  }
  
  public static void debug(String msg, Throwable t) {
    logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, msg, null, t);
  }
  
  public static boolean isDebugEnabled(Marker marker) {
    return logger.isDebugEnabled(marker);
  }
  
  public static void debug(Marker marker, String msg) {
    logger.log(marker, FQCN, LocationAwareLogger.DEBUG_INT, msg, null, null);
  }
  
  public static void debug(Marker marker, String format, Object arg) {
    logger.log(marker, FQCN, LocationAwareLogger.DEBUG_INT, format, new Object[] {arg}, null);
  }
  
  public static void debug(Marker marker, String format, Object arg1, Object arg2) {
    logger
        .log(marker, FQCN, LocationAwareLogger.DEBUG_INT, format, new Object[] {arg1, arg2}, null);
  }
  
  public static void debug(Marker marker, String format, Object... arguments) {
    logger.log(marker, FQCN, LocationAwareLogger.DEBUG_INT, format, arguments, null);
  }
  
  public static void debug(Marker marker, String msg, Throwable t) {
    logger.log(marker, FQCN, LocationAwareLogger.DEBUG_INT, msg, null, t);
  }
  
  public static boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }
  
  public static void info(String msg) {
    logger.log(null, FQCN, LocationAwareLogger.INFO_INT, msg, null, null);
  }
  
  public static void info(String format, Object arg) {
    logger.log(null, FQCN, LocationAwareLogger.INFO_INT, format, new Object[] {arg}, null);
  }
  
  public static void info(String format, Object arg1, Object arg2) {
    logger.log(null, FQCN, LocationAwareLogger.INFO_INT, format, new Object[] {arg1, arg2}, null);
  }
  
  public static void info(String format, Object... arguments) {
    logger.log(null, FQCN, LocationAwareLogger.INFO_INT, format, arguments, null);
  }
  
  public void info(String msg, Throwable t) {
    logger.log(null, FQCN, LocationAwareLogger.INFO_INT, msg, null, t);
  }
  
  public static boolean isInfoEnabled(Marker marker) {
    return logger.isInfoEnabled(marker);
  }
  
  public static void info(Marker marker, String msg) {
    logger.log(marker, FQCN, LocationAwareLogger.INFO_INT, msg, null, null);
  }
  
  public static void info(Marker marker, String format, Object arg) {
    logger.log(marker, FQCN, LocationAwareLogger.INFO_INT, format, new Object[] {arg}, null);
  }
  
  public static void info(Marker marker, String format, Object arg1, Object arg2) {
    logger.log(marker, FQCN, LocationAwareLogger.INFO_INT, format, new Object[] {arg1, arg2}, null);
  }
  
  public static void info(Marker marker, String format, Object... arguments) {
    logger.log(marker, FQCN, LocationAwareLogger.INFO_INT, format, arguments, null);
  }
  
  public static void info(Marker marker, String msg, Throwable t) {
    logger.log(marker, FQCN, LocationAwareLogger.INFO_INT, msg, null, t);
  }
  
  public static boolean isWarnEnabled() {
    return logger.isWarnEnabled();
  }
  
  public static void warn(String msg) {
    logger.log(null, FQCN, LocationAwareLogger.WARN_INT, msg, null, null);
  }
  
  public static void warn(String format, Object arg) {
    logger.log(null, FQCN, LocationAwareLogger.WARN_INT, format, new Object[] {arg}, null);
  }
  
  public static void warn(String format, Object... arguments) {
    logger.log(null, FQCN, LocationAwareLogger.WARN_INT, format, arguments, null);
  }
  
  public static void warn(String format, Object arg1, Object arg2) {
    logger.log(null, FQCN, LocationAwareLogger.WARN_INT, format, new Object[] {arg1, arg2}, null);
  }
  
  public static void warn(String msg, Throwable t) {
    logger.log(null, FQCN, LocationAwareLogger.WARN_INT, msg, null, t);
  }
  
  public boolean isWarnEnabled(Marker marker) {
    return logger.isWarnEnabled(marker);
  }
  
  public static void warn(Marker marker, String msg) {
    logger.log(marker, FQCN, LocationAwareLogger.WARN_INT, msg, null, null);
  }
  
  public static void warn(Marker marker, String format, Object arg) {
    logger.log(marker, FQCN, LocationAwareLogger.WARN_INT, format, new Object[] {arg}, null);
  }
  
  public static void warn(Marker marker, String format, Object arg1, Object arg2) {
    logger.log(marker, FQCN, LocationAwareLogger.WARN_INT, format, new Object[] {arg1, arg2}, null);
  }
  
  public static void warn(Marker marker, String format, Object... arguments) {
    logger.log(marker, FQCN, LocationAwareLogger.WARN_INT, format, arguments, null);
  }
  
  public static void warn(Marker marker, String msg, Throwable t) {
    logger.log(marker, FQCN, LocationAwareLogger.WARN_INT, msg, null, t);
  }
  
  public boolean isErrorEnabled() {
    return logger.isErrorEnabled();
  }
  
  /**
   * 输出error级别信息
   * 
   * @see CDWooLogger.error(String msg, Throwable t) {
   * 
   * @param e
   *        异常
   */
  @Deprecated
  public static void error(Exception e) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, e.toString(), null, e);
  }
  
  public static void error(String msg) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, msg, null, null);
  }
  
  public static void error(String format, Object arg) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, format, new Object[] {arg}, null);
  }
  
  public static void error(String format, Object arg1, Object arg2) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, format, new Object[] {arg1, arg2}, null);
  }
  
  public static void error(String format, Object... arguments) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, format, arguments, null);
  }
  
  public static void error(String msg, Throwable t) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, msg, null, t);
  }
  
  public boolean isErrorEnabled(Marker marker) {
    return logger.isErrorEnabled(marker);
  }
  
  public static void error(Marker marker, String msg) {
    logger.log(marker, FQCN, LocationAwareLogger.ERROR_INT, msg, null, null);
  }
  
  public static void error(Marker marker, String format, Object arg) {
    logger.log(marker, FQCN, LocationAwareLogger.ERROR_INT, format, new Object[] {arg}, null);
  }
  
  public static void error(Marker marker, String format, Object arg1, Object arg2) {
    logger
        .log(marker, FQCN, LocationAwareLogger.ERROR_INT, format, new Object[] {arg1, arg2}, null);
  }
  
  public static void error(Marker marker, String format, Object... arguments) {
    logger.log(marker, FQCN, LocationAwareLogger.ERROR_INT, format, arguments, null);
  }
  
  public static void error(Marker marker, String msg, Throwable t) {
    logger.log(marker, FQCN, LocationAwareLogger.ERROR_INT, msg, null, t);
  }
  
  public static void main(String[] args) {
    CDWooLogger.info("lllllllllllllltttttttttttttttt");
  }
  
}
