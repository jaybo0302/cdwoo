/**
 * File：OrderNumWithOn.java
 * Package：main
 * Author：chendong.bj@fang.com
 * Date：2017年7月17日 下午4:21:35
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test.demo;


/**
 * @author chendong.bj@fang.com
 */
public class OrderNumWithOn {
  
  /**
   * methods desc.
   * 将一个int 数组的数，负数在左边，正数在右边，且不能打乱原来的相对位置
   * @author chendong.bj@fang.com
   * @date 2017年7月17日
   * @param args
   */
  public static void main(String[] args) {
    int[] a = {-7,1,4,-2,-5,8,6,-3,9,-1,10};
    order(a);
  }
  
  public static void order (int[] a) {
    //遍历到的位置，从0开始遍历
    int x = 0;
    //记录连续正数的个数
    int count = 0;
    //从左边开始遍历，如果遇到正数记录count+1，x+1，如果是负数判断count是否是0，不是0换位置，如果是0跳过
    while (x < a.length) {
      if (a[x] < 0) {
        if (count ==0) {
          x++;
          continue;
        } else {
          //交换位置
          int temp = a[x];
          for (int i = x;i > (x-count);i--) {
            a[i] = a[i-1];
          }
          a[x-count] = temp;
          x = (x-count);
          count=0;
        }
      } else {
        count++;
        x++;
      }
    }
    for(int i : a){
      System.out.print(i+" ");
    }
  }
}
