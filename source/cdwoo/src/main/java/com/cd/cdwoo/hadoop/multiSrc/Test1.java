package com.cd.cdwoo.hadoop.multiSrc;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
public class Test1 {
  
  public static void main(String[] args) throws ParseException {
    // TODO Auto-generated method stub
    String s = "file:/E:/file-first-step/hadoop/multiSrc5.txt:0+189";
    System.out.println(s.split("/hadoop/")[1]);
    Calendar c = Calendar.getInstance();c.add(Calendar.DAY_OF_MONTH, -31);
    System.out.println(DateUtils.parseDate("2017-07-23", new String[]{"yyyy-MM-dd"}));
    System.out.println(DateUtils.parseDate("2017-07-24", new String[]{"yyyy-MM-dd"}).before(c.getTime()));
    System.out.println("yyyy-MM-dd dd".substring(0, 10));
  }
}
