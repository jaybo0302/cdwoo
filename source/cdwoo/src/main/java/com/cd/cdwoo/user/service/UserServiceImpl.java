/**
 * File：UserServiceImpl.java
 * Package：com.cd.cdwoo.user.service
 * Author：chendong
 * Date：2016年12月8日 下午1:44:28
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cd.cdwoo.common.ServiceException;
import com.cd.cdwoo.common.ServiceResult;
import com.cd.cdwoo.user.dao.UserDao;
import com.cd.cdwoo.user.entity.User;
import com.cd.cdwoo.util.CDWooLogger;


/**
 * @author chendong
 */
@Service
public class UserServiceImpl  implements UserService {

  @Autowired
  private UserDao userDao;
  @Override
  public ServiceResult addUser(User user) {
    ServiceResult serviceResult = new ServiceResult("add user："+user.getNickName());
    try {
      userDao.addUser(user);
      CDWooLogger.info("add user success");
      return serviceResult;
    }
    catch (Exception e) {
      CDWooLogger.error("add user failed the reason is : " + e);
      serviceResult.setErrorMessage("add user failed：" + e);
      throw new ServiceException(serviceResult);
    }
  }
  
}
