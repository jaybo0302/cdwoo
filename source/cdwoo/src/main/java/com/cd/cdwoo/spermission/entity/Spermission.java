/**
 * File：Spermission.java
 * Package：com.cd.cdwoo.spermission.entity
 * Author：user
 * Date：2017年2月20日 下午3:08:35
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.spermission.entity;

/**
 * 说明
 * @author user
 */
public class Spermission {
   public Integer Id;
   
   public String permisson;
   
   public String description;
   
   public Integer available;

  /**
   * @return id
   */
  public Integer getId() {
    return Id;
  }

  /**
   * @param id set id
   */
  public void setId(Integer id) {
    Id = id;
  }

  /**
   * @return permisson
   */
  public String getPermisson() {
    return permisson;
  }

  /**
   * @param permisson set permisson
   */
  public void setPermisson(String permisson) {
    this.permisson = permisson;
  }

  /**
   * @return description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description set description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return available
   */
  public int getAvailable() {
    return available;
  }

  /**
   * @param available set available
   */
  public void setAvailable(int available) {
    this.available = available;
  }
   
}
