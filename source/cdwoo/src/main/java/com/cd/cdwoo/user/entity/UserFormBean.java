/**
 * File：UserFormBean.java
 * Package：com.cd.cdwoo.user.entity
 * Author：chendong
 * Date：2016年12月8日 下午1:57:04
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.user.entity;

import com.cd.cdwoo.common.FormBean;


/**
 * @author chendong
 */
public class UserFormBean extends FormBean {
  private String nickName;
  private String email;
  private String realName;
  private String phoneNo;
  private int age;
  private int gender;
  
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
