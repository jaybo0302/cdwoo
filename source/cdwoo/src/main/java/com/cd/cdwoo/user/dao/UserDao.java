/**
 * File：UserDao.java
 * Package：com.cd.cdwoo.user.dao
 * Author：chendong
 * Date：2016年12月8日 下午4:13:23
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.user.dao;

import com.cd.cdwoo.user.entity.User;


/**
 * @author chendong
 */
public interface UserDao {
  /**
   * @param user
   */
  void addUser(User user);
  
}
