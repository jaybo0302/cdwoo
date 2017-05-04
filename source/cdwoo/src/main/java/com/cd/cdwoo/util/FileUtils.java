/**
 * File：FileUtils.java
 * Package：com.cd.cdwoo.util
 * Author：chendong.bj@fang.com
 * Date：2017年5月4日 上午10:46:38
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.util;

import java.io.File;
/**
 * @author chendong.bj@fang.com
 */
public final class FileUtils {
  /**
   * private cons 
   */
  private FileUtils() {}

  public static void showFileTree(String path, int pathNum) {
    File file = new File(path);
    if (!file.exists()) {
      return;
    }
    
    File[] fileList = file.listFiles();
    for (File f : fileList) {
      if (f.isDirectory()) {
        int num = pathNum;
        System.err.println(getWhiteSpace(pathNum) + f.getName());
        showFileTree(path + File.separator + f.getName(), ++num);
      } else {
        System.out.println(getWhiteSpace(pathNum) + f.getName());
      }
    }
  }
  public static String getWhiteSpace(int num) {
    String result = "";
    for (int i = 0; i < num; i++) {
      result += "-";
    }
    return result;
  }
  /**
   * methods desc.
   * @author chendong.bj@fang.com
   * @date 2017年5月4日
   * @param args
   */
  public static void main(String[] args) {
    /*File file = new File("D://upload2xshell-tmpfile/LinuxProject");
    System.out.println(file.isDirectory());
    File[] fileList = file.listFiles();
    
    for (File file1 : fileList) {
      System.out.println(file1.getName() + " " + file1.isFile());
    }*/
    showFileTree("D://upload2xshell-tmpfile", 0);
  }
}
