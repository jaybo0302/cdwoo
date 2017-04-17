/**
 * File：CustomDao.java
 * Package：com.cd.cdwoo.core.dao
 * Author：chendong.bj@fang.com
 * Date：2017年4月13日 下午1:46:43
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cd.cdwoo.user.entity.User;


/**
 * @author chendong.bj@fang.com
 */
public interface CustomDao {
  List<Map<String, Object>> getList(@Param("realName") String realName);
  User getUserById(@Param("userId") int userId);
}
