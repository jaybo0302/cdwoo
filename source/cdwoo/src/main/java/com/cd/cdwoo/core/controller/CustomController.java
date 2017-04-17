/**
 * File：CustomController.java
 * Package：com.cd.cdwoo.core.controller
 * Author：chendong.bj@fang.com
 * Date：2017年4月13日 下午1:49:49
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.core.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cd.cdwoo.core.service.CustomServive;


/**
 * @author chendong.bj@fang.com
 */
@Controller
@RequestMapping("/custom")
public class CustomController {
  /**
   * customService
   */
  @Autowired
  private CustomServive customServive;
  @ResponseBody
  @RequestMapping("/testParam")
  public Object testParam(HttpServletRequest req) throws Exception {
    return customServive.getUserById(URLDecoder.decode(req.getParameter("realName"), "utf-8"));
  }
}
