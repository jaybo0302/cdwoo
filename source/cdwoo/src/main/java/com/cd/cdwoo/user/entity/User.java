/**
 * File：User.java
 * Package：com.cd.cdwoo.user.entity
 * Author：chendong
 * Date：2016年12月8日 下午1:39:45
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.user.entity;

import java.io.Serializable;

import com.cd.cdwoo.annotation.Table;


/**
 * @author chendong
 */
@Table(name = "cdwoo_user")
public class User  implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6779420679445127872L;
  
  /**
   * 
   */
  private int id;
  /**
   * 
   */
  private String realName;
  /**
   * 
   */
  private String email;
  /**
   * 
   */
  private String phoneNo;
  /**
   * 
   */
  private String nickName;
  /**
   * 
   */
  private int age;
  /**
   * 性别 0女，1男
   */
  private int gender;
  
  /**
   * @return id
   */
  public int getId() {
    return id;
  }
  
  /**
   * @param id set id
   */
  public void setId(int id) {
    this.id = id;
  }
  
  /**
   * @return realName
   */
  public String getRealName() {
    return realName;
  }
  
  /**
   * @param realName set realName
   */
  public void setRealName(String realName) {
    this.realName = realName;
  }
  
  /**
   * @return email
   */
  public String getEmail() {
    return email;
  }
  
  /**
   * @param email set email
   */
  public void setEmail(String email) {
    this.email = email;
  }
  
  /**
   * @return phoneNo
   */
  public String getPhoneNo() {
    return phoneNo;
  }
  
  /**
   * @param phoneNo set phoneNo
   */
  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }
  
  /**
   * @return nickName
   */
  public String getNickName() {
    return nickName;
  }
  
  /**
   * @param nickName set nickName
   */
  public void setNickName(String nickName) {
    this.nickName = nickName;
  }
  
  /**
   * @return age
   */
  public int getAge() {
    return age;
  }
  
  /**
   * @param age set age
   */
  public void setAge(int age) {
    this.age = age;
  }
  
  /**
   * @return gender
   */
  public int getGender() {
    return gender;
  }
  
  /**
   * @param gender set gender
   */
  public void setGender(int gender) {
    this.gender = gender;
  }
  
}
