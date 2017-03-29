/**
 * File：HttpClientUtil.java
 * Package：com.fang.olapservice.xs3upload
 * Author：user
 * Date：2017年2月28日 上午9:16:42
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

/**
 * 说明 httpclient工具类
 * @author chendong
 */
public final class HttpClientUtil {
  /**
   * 私有构造
   * 构造函数说明
   */
  private HttpClientUtil(){
    
  }
  
  public static String httpPostStringEntity(String url, Map<String, String> headMap, String body) {
    HttpPost httpPost = null;
    CloseableHttpResponse ht = null;
    InputStream is = null;
    BufferedReader br = null;
    try {
        httpPost = new HttpPost(url);

        StringEntity s = new StringEntity(body, "utf-8");
        httpPost.setEntity(s);

        if (headMap != null && headMap.size() > 0) {
            for(Map.Entry<String, String> entry:headMap.entrySet()){
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        if (!httpPost.containsHeader("User-Agent"))
            httpPost.addHeader("User-Agent", "wcs-java-sdk-2.0.0");
        CloseableHttpClient hc = getHttpClient();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        ht = hc.execute(httpPost);

        HttpEntity het = ht.getEntity();
        is = het.getContent();
        br = new BufferedReader(new InputStreamReader(is, "utf8"));
        String readLine;
        StringBuffer sb = new StringBuffer();
        while ((readLine = br.readLine()) != null) {
            sb.append(readLine);
        }
        String result = sb.toString();
        CDWooLogger.info(result);
        return result;
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    } finally {
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(br);
        IOUtils.closeQuietly(ht);
        if (httpPost != null) {
            httpPost.releaseConnection();
        }
    }
  }
  
  /** 
   * httpPost方法说明
   * @param url 
   * @param params
   * @param headMap
   * @param file
   * @return 参数说明
   */
  public static String httpPost(String url, Map<String, String> params, Map<String, String> headMap, File file) {
    HttpPost httpPost = null;
    CloseableHttpResponse ht = null;
    InputStream is = null;
    BufferedReader br = null;
    try {
        httpPost = new HttpPost(url);
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();

        if (file != null) {
            MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();

            FileBody fileBody = new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName());
            mEntityBuilder.addPart("file", fileBody);
            mEntityBuilder.addTextBody("desc", file.getName());

            if (params != null && params.size() > 0) {
                for(Map.Entry<String, String> entry:params.entrySet()){
                    mEntityBuilder.addTextBody(entry.getKey(), entry.getValue(), ContentType.create("text/plain", Charset.forName("UTF-8")));
                }
            }
            httpPost.setEntity(mEntityBuilder.build());
        } else if (params != null && params.size() > 0) {
            for(Map.Entry<String, String> entry:params.entrySet()){
                paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            HttpEntity he = null;
            try {
                he = new UrlEncodedFormEntity(paramsList, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpPost.setEntity(he);
        }

        if (headMap != null && headMap.size() > 0) {
            for(Map.Entry<String, String> entry:headMap.entrySet()){
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        if (!httpPost.containsHeader("User-Agent"))
            httpPost.addHeader("User-Agent", "wcs-java-sdk-2.0.0");
        CloseableHttpClient hc = getHttpClient();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        ht = hc.execute(httpPost);

        HttpEntity het = ht.getEntity();
        is = het.getContent();
        br = new BufferedReader(new InputStreamReader(is, "utf8"));
        String readLine;
        StringBuffer sb = new StringBuffer();
        while ((readLine = br.readLine()) != null) {
            sb.append(readLine);
        }
        String result = sb.toString();
        CDWooLogger.info(result);
        return result;
      } catch (IOException e) {
          e.printStackTrace();
          return null;
      } finally {
          IOUtils.closeQuietly(is);
          IOUtils.closeQuietly(br);
          IOUtils.closeQuietly(ht);
          if (httpPost != null) {
              httpPost.releaseConnection();
          }
      }
  }
  
  /** 
   * getHttpClient方法说明
   * @return 参数说明
   */
  public static CloseableHttpClient getHttpClient() {
    CloseableHttpClient httpClient = null;
    try {
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                System.out.println("request fail retryRequest false");
                return false;
            }
        };
        // 5秒超时
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).setConnectTimeout(30000).setRedirectsEnabled(false)
                .build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);// 连接池最大并发连接数
        cm.setDefaultMaxPerRoute(30);// 单路由最大并发数
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(30000).build();
        cm.setDefaultSocketConfig(socketConfig);

        httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).setRetryHandler(myRetryHandler).build();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return httpClient;
  }
}
