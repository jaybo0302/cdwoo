package com.cd.cdwoo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cd.cdwoo.common.Constants;
/**
 * HTTP请求帮助类,
 * <function>
 * 通过该类发送GET,POST请求，获取网络接口所返回字符串。
 * </function>
 * 
 * @author chendong
 */
public final class HttpUtils {
  
  /**
   * HTTP请求参数验证成功
   */
  private static final String VERIFY_SUCESS = "verify_http_param_sucess";
  
  /**
   * 私有化构造函数
   */
  private HttpUtils() {
  }
  
  /**
   * 根据地址和参数返回字符串
   * 
   * @param url
   *        目的地址
   * @param params
   *        参数
   * @param httpHeaders
   *        请求头设置
   * @param connTimeout
   *        连接超时时长（毫秒）
   * @param readTimeout
   *        读取接口数据超时时长（毫秒）
   * @param inCharset
   *        输入编码
   * @param outCharset
   *        输出编码
   * @return 返回字符串
   */
  public static String post(String url, Map<String, String> params,
                            Map<String, String> httpHeaders, int connTimeout, int readTimeout,
                            String inCharset, String outCharset) {
    String result = "";
    String paramVerifyResult = httpParamVerify(url, connTimeout, readTimeout, inCharset, outCharset);
    if (!VERIFY_SUCESS.equals(paramVerifyResult)) {
      return paramVerifyResult;
    }
    StringBuilder msg = new StringBuilder(512);
    msg.append("HttpPost - URL=[").append(url);
    BufferedReader buffer = null;
    InputStream in = null;
    OutputStreamWriter out = null;
    long timeStart = System.currentTimeMillis();
    try {
      String strParam = "";
      if (params != null) {
        strParam = mapToUrlString(params, outCharset);
      }
      msg.append("], Parameter=[").append(strParam);
      URL realUrl = new URL(url);
      HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent",
          "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
      if (httpHeaders != null) {
        for (Map.Entry<String, String> entry : httpHeaders.entrySet()) {
          conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
      }
      conn.setConnectTimeout(connTimeout);
      conn.setReadTimeout(readTimeout);
      // 发送POST请求必须设置如下两行
      conn.setDoOutput(true);
      conn.setDoInput(true);
      out = new OutputStreamWriter(conn.getOutputStream(), outCharset);
      // 发送请求参数
      out.write(strParam);
      out.flush();
      in = conn.getInputStream();
      // 获取状态码
      String code = conn.getResponseCode() + "";
      buffer = new BufferedReader(new InputStreamReader(in, inCharset));
      String line = null;
      while ((line = buffer.readLine()) != null) {
        result += line;
      }
      long timeEnd = System.currentTimeMillis();
      msg.append("], Result=[").append(result);
      msg.append("], TimeUsed=[").append(timeEnd - timeStart).append("]");
      if (!code.startsWith("2") && !code.startsWith("3")) {
        AuditLogUtil.auditLog(code, "POST", url, result);
      }
      if (timeEnd - timeStart > Constants.API_TIME) {
        AuditLogUtil.auditLog(code, "POST", url, timeEnd - timeStart, result);
      }
      CDWooLogger.info(msg.toString());
    }
    catch (Exception e) {
      long timeEnd = System.currentTimeMillis();
      msg.append("], Error=[").append(e);
      msg.append("], TimeUsed=[").append(timeEnd - timeStart).append("]");
      CDWooLogger.error(msg.toString());
    }
    finally {
      try {
        if (buffer != null) {
          buffer.close();
        }
        if (in != null) {
          in.close();
        }
        if (out != null) {
          out.close();
        }
      }
      catch (Exception e) {
        CDWooLogger.error("BufferedReader error", e);
      }
    }
    return result;
  }
  
  /**
   * 根据地址和参数返回字符串
   * 
   * @param url
   *        目的地址
   * @param params
   *        参数
   * @param httpHeaders
   *        请求头设置
   * @param connTimeout
   *        连接超时时长（毫秒）
   * @param readTimeout
   *        读取接口数据超时时长（毫秒）
   * @param outCharset
   *        输出编码
   * @return 返回InputStream网络流
   */
  public static InputStream getInputStreamByPostMethod(String url, Map<String, String> params,
                                                       Map<String, String> httpHeaders,
                                                       int connTimeout, int readTimeout,
                                                       String outCharset) {
    String paramVerifyResult = httpParamVerify(url, connTimeout, readTimeout, "ignore", outCharset);
    if (!VERIFY_SUCESS.equals(paramVerifyResult)) {
      return null;
    }
    StringBuilder msg = new StringBuilder(512);
    msg.append("HttpPost - URL=[").append(url);
    String strParam = "";
    if (params != null) {
      strParam = mapToUrlString(params, outCharset);
    }
    msg.append("], Parameter=[").append(strParam);
    OutputStreamWriter out = null;
    InputStream in = null;
    long timeStart = System.currentTimeMillis();
    try {
      URL realUrl = new URL(url);
      URLConnection conn = realUrl.openConnection();
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent",
          "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
      if (httpHeaders != null) {
        for (Map.Entry<String, String> entry : httpHeaders.entrySet()) {
          conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
      }
      conn.setConnectTimeout(connTimeout);
      conn.setReadTimeout(readTimeout);
      // 发送POST请求必须设置如下两行
      conn.setDoOutput(true);
      conn.setDoInput(true);
      out = new OutputStreamWriter(conn.getOutputStream(), outCharset);
      // 发送请求参数
      out.write(strParam);
      out.flush();
      in = conn.getInputStream();
      long timeEnd = System.currentTimeMillis();
      msg.append("], TimeUsed=[").append(timeEnd - timeStart).append("]");
      CDWooLogger.info(msg.toString());
    }
    catch (Exception e) {
      long timeEnd = System.currentTimeMillis();
      msg.append("], Error=[").append(e);
      msg.append("], TimeUsed=[").append(timeEnd - timeStart).append("]");
      CDWooLogger.error(msg.toString());
    }
    finally {
      try {
        if (out != null) {
          out.close();
        }
      }
      catch (IOException ex) {
        CDWooLogger.error("OutputStreamWriter error", ex);
      }
    }
    return in;
  }
  
  /**
   * 发送GET请求返回字符串
   * 
   * @param url
   *        目的地址
   * @param params
   *        参数
   * @param httpHeaders
   *        请求头设置
   * @param connTimeout
   *        连接超时时长（毫秒）
   * @param readTimeout
   *        读取接口数据超时时长（毫秒）
   * @param inCharset
   *        输入编码
   * @param outCharset
   *        输出编码
   * @return 返回字符串
   */
  public static String get(String url, Map<String, String> params, Map<String, String> httpHeaders,
                           int connTimeout, int readTimeout, String inCharset, String outCharset) {
    String paramVerifyResult = httpParamVerify(url, connTimeout, readTimeout, inCharset, outCharset);
    if (!VERIFY_SUCESS.equals(paramVerifyResult)) {
      return paramVerifyResult;
    }
    String realUrlParam = url;
    if (params != null) {
      realUrlParam = joinUrl(url, params, outCharset);
    }
    StringBuilder msg = new StringBuilder(512);
    msg.append("HttpGet - URL=[").append(realUrlParam);
    BufferedReader buffer = null;
    InputStream in = null;
    String result = "";
    long timeStart = System.currentTimeMillis();
    try {
      URL realUrl = new URL(realUrlParam);
      HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent",
          "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
      conn.setConnectTimeout(connTimeout);
      conn.setReadTimeout(readTimeout);
      if (httpHeaders != null) {
        for (Map.Entry<String, String> entry : httpHeaders.entrySet()) {
          conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
      }
      // 建立实际的连接
      conn.connect();
      in = conn.getInputStream();
      // 获取返回状态码
      String code = conn.getResponseCode() + "";
      buffer = new BufferedReader(new InputStreamReader(in, inCharset));
      String line = null;
      while ((line = buffer.readLine()) != null) {
        result += line;
      }
      long timeEnd = System.currentTimeMillis();
      msg.append("], Result=[").append(result);
      msg.append("], TimeUsed=[").append(timeEnd - timeStart).append("]");
      if (!code.startsWith("2") && !code.startsWith("3")) {
        AuditLogUtil.auditLog(code, "POST", url, result);
      }
      if (timeEnd - timeStart > Constants.API_TIME) {
        AuditLogUtil.auditLog(code, "GET", realUrlParam, timeEnd - timeStart, result);
      }
      CDWooLogger.info(msg.toString());
    }
    catch (Exception e) {
      long timeEnd = System.currentTimeMillis();
      msg.append("], Error=[").append(e);
      msg.append("], TimeUsed=[").append(timeEnd - timeStart).append("]");
      CDWooLogger.error(msg.toString());
    }
    finally {
      try {
        if (buffer != null) {
          buffer.close();
        }
        if (in != null) {
          in.close();
        }
      }
      catch (IOException ex) {
        CDWooLogger.error("BufferedReader error", ex);
      }
    }
    return result;
  }
  
  /**
   * 发送GET请求返回字符串
   * 
   * @param url
   *        目的地址
   * @param params
   *        参数
   * @param httpHeaders
   *        请求头设置
   * @param connTimeout
   *        连接超时时长（毫秒）
   * @param readTimeout
   *        读取接口数据超时时长（毫秒）
   * @param outCharset
   *        输出编码
   * @return 返回InputStream网络流
   */
  public static InputStream getInputStreamByGetMethod(String url, Map<String, String> params,
                                                      Map<String, String> httpHeaders,
                                                      int connTimeout, int readTimeout,
                                                      String outCharset) {
    String paramVerifyResult = httpParamVerify(url, connTimeout, readTimeout, "ignore", outCharset);
    if (!VERIFY_SUCESS.equals(paramVerifyResult)) {
      return null;
    }
    String realUrlParam = url;
    if (params != null) {
      realUrlParam = joinUrl(url, params, outCharset);
    }
    StringBuilder msg = new StringBuilder(512);
    msg.append("HttpGet - URL=[").append(realUrlParam);
    
    long timeStart = System.currentTimeMillis();
    InputStream in = null;
    try {
      URL realUrl = new URL(realUrlParam);
      HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent",
          "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
      conn.setConnectTimeout(connTimeout);
      conn.setReadTimeout(readTimeout);
      if (httpHeaders != null) {
        for (Map.Entry<String, String> entry : httpHeaders.entrySet()) {
          conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
      }
      // 建立实际的连接
      conn.connect();
      in = conn.getInputStream();
      long timeEnd = System.currentTimeMillis();
      msg.append("], TimeUsed=[").append(timeEnd - timeStart).append("]");
      CDWooLogger.info(msg.toString());
    }
    catch (Exception e) {
      long timeEnd = System.currentTimeMillis();
      msg.append("], Error=[").append(e);
      msg.append("], TimeUsed=[").append(timeEnd - timeStart).append("]");
      CDWooLogger.error(msg.toString());
    }
    return in;
  }
  
  /**
   * map封装的参数转化为请求参数形式
   * 
   * @param params
   *        参数集合
   * @param charset
   *        编码方式
   * @return 请求形式参数
   */
  private static String mapToUrlString(Map<String, String> params, String charset) {
    StringBuilder ret = new StringBuilder(128);
    for (Map.Entry<String, String> entry : params.entrySet()) {
      String param = entry.getValue();
      try {
        param = URLEncoder.encode(param, charset);
      }
      catch (UnsupportedEncodingException e) {
        // ignore it
      }
      if (ret.length() > 0) {
        ret.append("&");
      }
      ret.append(entry.getKey()).append("=").append(param);
    }
    return ret.toString();
  }
  
  /**
   * 根据地址和参数返回字符串
   * 
   * @param url
   *        目的地址
   * @param connTimeout
   *        连接超时时长（毫秒）
   * @param readTimeout
   *        读取接口数据超时时长（毫秒）
   * @param inCharset
   *        输入编码
   * @param outCharset
   *        输出编码
   * @return 返回字符串
   */
  private static String httpParamVerify(String url, int connTimeout, int readTimeout,
                                        String inCharset, String outCharset) {
    if (StringUtils.isBlank(url)) {
      CDWooLogger.error("请求URL不能为空!");
      return "请求URL不能为空!";
    }
    if (connTimeout <= 0) {
      CDWooLogger.error(url + " 请求时，必须设置连接时间！");
      return "请求时，必须设置连接时间！";
    }
    if (readTimeout <= 0) {
      CDWooLogger.error(url + " 请求时，必须设置读取时间！");
      return "请求时，必须设置读取时间！";
    }
    if (StringUtils.isBlank(inCharset)) {
      CDWooLogger.error(url + " 请求时,获取输入流编码不能为空!");
      return "请求时,获取输入流编码不能为空!";
    }
    if (StringUtils.isBlank(outCharset)) {
      CDWooLogger.error(url + " 请求时间，获取输出流编码不能为空!");
      return "请求时，获取输出流编码不能为空!";
    }
    return VERIFY_SUCESS;
  }
  
  /**
   * 根据地址和参数拼接URL
   * 
   * @param url
   *        请求地址
   * @param params
   *        请求参数
   * @param charset
   *        编码方式
   * @return 拼接后的地址
   */
  private static String joinUrl(String url, Map<String, String> params, String charset) {
    StringBuilder ret = new StringBuilder(url.length() + 128);
    ret.append(url);
    int len = url.length();
    if (url.indexOf('?') > 0) {
      if (!url.endsWith("?")) {
        len--;
      }
    }
    else {
      ret.append('?');
      len++;
    }
    for (Map.Entry<String, String> entry : params.entrySet()) {
      String param = entry.getValue();
      try {
        param = URLEncoder.encode(param, charset);
      }
      catch (UnsupportedEncodingException e) {
        CDWooLogger.error("urlEncoder error : param is  {} ,charset is {} " + e.toString(), param,
            charset);
      }
      if (ret.length() > len) {
        ret.append("&");
      }
      ret.append(entry.getKey()).append("=").append(param);
    }
    return ret.toString();
  }
}
