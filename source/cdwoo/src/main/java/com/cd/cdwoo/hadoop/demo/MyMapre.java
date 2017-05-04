package com.cd.cdwoo.hadoop.demo;

import org.apache.hadoop.io.Text;


public class MyMapre {
  public static void strings() {
    String s = "这是一段测试文字";
    System.out.println(s.length());
    System.out.println(s.indexOf("这"));
    System.out.println(s.indexOf("是"));
    System.out.println(s.indexOf("一段"));
    System.out.println(s.indexOf("文字"));
  }
  
  public static void texts() {
    Text t = new Text("\u0041\u00df\u6771\ud801\udc00");
    System.out.println(t.getLength());
    System.out.println(t.find("\u0041"));
    System.out.println(t.find("\u00df"));
    System.out.println(t.find("\u6771"));
    System.out.println(t.find("\ud801\udc00"));
  }
  public static void main(String[] args) {
    strings();
    texts();
  }
  
}
