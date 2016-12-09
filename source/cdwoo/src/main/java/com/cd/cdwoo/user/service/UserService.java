/**
 * File：UserService.java
 * Package：com.cd.cdwoo.user.service
 * Author：chendong
 * Date：2016年12月8日 下午1:43:42
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.user.service;

import org.springframework.stereotype.Service;

import com.cd.cdwoo.common.ServiceResult;
import com.cd.cdwoo.user.entity.User;


/**
 * @author chendong
 */

public interface UserService {
  
  /**
   * add user method
   * @param user entity
   * @return serviceResult
   */
  public ServiceResult addUser(User user);
}
