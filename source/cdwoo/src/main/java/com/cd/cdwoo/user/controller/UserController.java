/**
 * File：UserController.java
 * Package：com.cd.cdwoo.user.controller
 * Author：chendong
 * Date：2016年12月8日 下午1:54:38
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cd.cdwoo.common.ServiceException;
import com.cd.cdwoo.user.entity.User;
import com.cd.cdwoo.user.service.UserService;


/**
 * @author chendong
 */
@Controller
@RequestMapping("/user")
public class UserController {
  
  @Autowired
  private UserService userService;
  
  @ResponseBody
  @RequestMapping("addUser")
  public Object addUser(HttpServletRequest request, User user) {
    try {
      this.userService.addUser(user);
      return "success";
    }
    catch (ServiceException e) {
      return "failed" + e;
    }
  }
}
