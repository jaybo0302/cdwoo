/**
 * File：ClusterDao.java
 * Package：com.cd.cdwoo.core.dao.cluster
 * Author：chendong.bj@fang.com
 * Date：2017年4月20日 上午10:41:51
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.core.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * @author chendong.bj@fang.com
 */
public interface ClusterDao {

  List<Map<String, Object>> getSpecialInfo(@Param("ids")String substring);
  
}
