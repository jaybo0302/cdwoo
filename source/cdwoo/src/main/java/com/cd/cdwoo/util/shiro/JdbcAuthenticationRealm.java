/**
 * File：JdbcAuthenticationRealm.java
 * Package：com.cd.cdwoo.util.shiro
 * Author：user
 * Date：2017年2月20日 下午1:48:59
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.util.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 说明
 * @author user
 */
public class JdbcAuthenticationRealm extends AuthorizingRealm {
  
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    return null;
  }
  
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    return null;
  }
  
}
