/**
 * File：CNCUloadCallBack.java
 * Package：com.fang.olapservice.xs3upload
 * Author：chendong
 * Date：2017年1月12日 上午10:37:04
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.xs3upload;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONObject;
import com.cd.cdwoo.util.CDWooLogger;
/**
 * @author chendong
 */
@Controller
@RequestMapping("CNCUloadCallBack")
public class CNCUloadCallBack {
  
  @RequestMapping("/upload2CNCCallback")
  public void upload2CNCCallback(HttpServletRequest req, HttpServletResponse rep) throws IOException {
    String callbackBody = req.getParameter("callbackBody");
    CDWooLogger.info(callbackBody);
    Map<String, String> returnMap = new HashMap<String, String>();
    String imageInfo = "";
    String bucket = "";
    String key = "";
    String businessLine = "";
    try {
      //callbackBody格式imageInfo=eyJtZXNzYWdlIjoiT0siLCJmb3JtYXQiOiJKUEVHIiwic2l6ZSI6IjkyNDI2Iiwid2lkdGgiOjUwMCwiaGVpZ2h0Ijo0NTUsImNvbG9yTW9kZWwiOiJzUkdCIn0=;&businessLine=bdp.core
      String params[] = callbackBody.split("&");
      for(String str:params){
        if (str.startsWith("imageInfo")){
          CDWooLogger.info(str.split("imageInfo=")[1]);
          imageInfo = str.split("imageInfo=")[1];
          continue;
        }
        if (str.startsWith("businessLine")){
          CDWooLogger.info(str.split("businessLine=")[1]);
          businessLine = str.split("businessLine=")[1];
          continue;
        }
        if (str.startsWith("key")){
          CDWooLogger.info(str.split("key=")[1]);
          key = str.split("key=")[1];
          continue;
        }
        if (str.startsWith("bucket")){
          CDWooLogger.info(str.split("bucket=")[1]);
          bucket = str.split("bucket=")[1];
          continue;
        }
      }
      CDWooLogger.info("imageInfo = " + imageInfo + "\n businessLine=" + businessLine + "\n bucket = " + bucket + "\n key = " + key );
      imageInfo = EncodeUtils.urlsafeDecodeString(imageInfo);
      CDWooLogger.info(imageInfo);
      String format = JSONObject.parseObject(imageInfo).getString("format");
      CDWooLogger.info(format);
      //判断format格式是否支持。
      boolean isSupport = false;
      String supportType = "";
      for (String str : supportType.split(",")) {
        if (format.toLowerCase().equals(str.toLowerCase())) {
          isSupport = true;
          break;
        }
      }
      if (isSupport) {
        returnMap.put("result", "100");
        returnMap.put("message", "upload success");
        returnMap.put("key", key);
        PrintWriter out = rep.getWriter();
        out.write(JSONObject.toJSONString(returnMap));
        out.flush();
        out.close();
      } else {
        //删除图片的逻辑
        returnMap.put("result", "200");
        returnMap.put("message", "upload failed");
        PrintWriter out = rep.getWriter();
        out.write(JSONObject.toJSONString(returnMap));
        out.flush();
        out.close();
      }
    }
    catch (Exception e) {
      CDWooLogger.info("" + e);
      CDWooLogger.error("" + e);
      returnMap.put("result", "200");
      returnMap.put("message", "upload failed");
      PrintWriter out = rep.getWriter();
      out.write(JSONObject.toJSONString(returnMap));
      out.flush();
      out.close();
    }
  }
}
