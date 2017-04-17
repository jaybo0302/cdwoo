/**
 * File：CustomServiceImpl.java
 * Package：com.cd.cdwoo.core.service
 * Author：chendong.bj@fang.com
 * Date：2017年4月13日 下午1:48:36
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cd.cdwoo.core.dao.CustomDao;
import com.cd.cdwoo.user.entity.User;


/**
 * @author chendong.bj@fang.com
 */
@Service
public class CustomServiceImpl implements CustomServive {
  /**
   * customDao
   */
  @Autowired
  private CustomDao customDao;

  @Override
  public List<Map<String, Object>> getList(String realName) {
    return customDao.getList(realName);
  }

  @Override
  public User getUserById(String userId) {
    return customDao.getUserById(Integer.parseInt(userId));
  }
}
