/**
 * File：Suser.java
 * Package：com.cd.cdwoo.suer.entity
 * Author：user
 * Date：2017年2月20日 下午3:12:48
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.suer.entity;

/**
 * 说明
 * @author user
 */
public class Suser {
  public Integer Id;
  
  public String userName;
  
  public String passWord;
  
  public String salt;
  
  public Integer locked;

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
   * @return userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @param userName set userName
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * @return passWord
   */
  public String getPassWord() {
    return passWord;
  }

  /**
   * @param passWord set passWord
   */
  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  /**
   * @return salt
   */
  public String getSalt() {
    return salt;
  }

  /**
   * @param salt set salt
   */
  public void setSalt(String salt) {
    this.salt = salt;
  }

  /**
   * @return locked
   */
  public Integer getLocked() {
    return locked;
  }

  /**
   * @param locked set locked
   */
  public void setLocked(Integer locked) {
    this.locked = locked;
  }
  
}
