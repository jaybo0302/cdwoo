/**
 * File：CustomSerive.java
 * Package：com.cd.cdwoo.core.service
 * Author：chendong.bj@fang.com
 * Date：2017年4月13日 下午1:48:13
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.core.service;

import java.util.List;
import java.util.Map;

import com.cd.cdwoo.user.entity.User;


/**
 * @author chendong.bj@fang.com
 */
public interface CustomServive {
  List<Map<String, Object>> getList(String realName);
  User getUserById(String userId);
  Object testMasClus();
}
