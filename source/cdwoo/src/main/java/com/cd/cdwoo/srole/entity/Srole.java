/**
 * File：Srole.java
 * Package：com.cd.cdwoo.srole.entity
 * Author：user
 * Date：2017年2月20日 下午3:10:36
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.srole.entity;

/**
 * 说明
 * @author user
 */
public class Srole {
  public Integer Id;
  
  public Integer role;
  
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
   * @return role
   */
  public Integer getRole() {
    return role;
  }

  /**
   * @param role set role
   */
  public void setRole(Integer role) {
    this.role = role;
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
  public Integer getAvailable() {
    return available;
  }

  /**
   * @param available set available
   */
  public void setAvailable(Integer available) {
    this.available = available;
  }
}
