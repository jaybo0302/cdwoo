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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cd.cdwoo.core.dao.CustomDao;
import com.cd.cdwoo.core.dao.cluster.ClusterDao;
import com.cd.cdwoo.core.dao.master.MasterDao;
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
  @Autowired
  private MasterDao masterDao;
  @Autowired
  private ClusterDao clusterDao;

  @Override
  public List<Map<String, Object>> getList(String realName) {
    return customDao.getList(realName);
  }

  @Override
  public User getUserById(String userId) {
    return customDao.getUserById(Integer.parseInt(userId));
  }

  @Override
  public Object testMasClus() {
    List<Map<String, Object>> resultList = masterDao.getResult();
    setSpecialDetail(resultList, new String[] {"title", "summary"});
    return resultList;
  }
  public void setSpecialDetail(List<Map<String, Object>> list, String[] keys) {
    // 拼装专题的id
    StringBuffer ids = new StringBuffer();
    for (Map<String, Object> map : list) {
      if ("special".equals(map.get("data_type"))) {
        if (StringUtils.isNotEmpty(String.valueOf(map.get("data_id")))) {
          if (!"".equals(map.get("data_id"))) {
            ids.append(String.valueOf(map.get("data_id"))).append(",");
          }
        }
      }
    }
    // 如果当前list中没有专题类型的数据，直接返回
    if (ids.toString().length() == 0) {
      return;
    }
    // 获取专题内容集合
    List<Map<String, Object>> specialList = clusterDao.getSpecialInfo(ids.toString().substring(0,
        ids.toString().length() - 1));
    for (Map<String, Object> resultMap : list) {
      if ("special".equals(resultMap.get("data_type"))) {
        for (Map<String, Object> dataMap : specialList) {
          if (String.valueOf(resultMap.get("data_id")).equals(
              String.valueOf(dataMap.get("data_id")))) {
            for (String key : keys) {
              resultMap.put(key, String.valueOf(dataMap.get(key)));
            }
            break;
          }
        }
      }
    }
  }
}
