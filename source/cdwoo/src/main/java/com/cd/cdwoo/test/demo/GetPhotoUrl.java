/**
 * File：GetPhotoUrl.java
 * Package：com.cd.cdwoo.test.demo
 * Author：chendong.bj@fang.com
 * Date：2017年6月5日 上午9:30:18
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test.demo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.IOUtils;

import com.cd.cdwoo.util.HttpUtils;


/**
 * @author chendong.bj@fang.com
 */
public class GetPhotoUrl {
  
  /**
   * methods desc.
   * @author chendong.bj@fang.com
   * @date 2017年6月5日
   * @param args
   * @throws Exception 
   */
  public static void main(String[] args) throws Exception {
    readAndWrite("C:\\Users\\jaybo\\Desktop\\photoUrl\\url_src.txt", "C:\\Users\\jaybo\\Desktop\\photoUrl\\url.txt");
  }
  public static void readAndWrite(String path, String tarPath) throws Exception {
    File tarFile = new File (tarPath);
    File file = new File(path);
    List<String> strList = IOUtils.readLines(new FileInputStream(file));
    List<String> urlList = new ArrayList<>();
    for (String str : strList) {
      if (str.contains("//c1.staticflickr.com/")){
        urlList.add("https:" + str.substring(str.indexOf("//c1.staticflickr.com/"), str.indexOf("jpg") + 3).replace("_z", "_b"));
        getPhoto("https:" + str.substring(str.indexOf("//c1.staticflickr.com/"), str.indexOf("jpg") + 3).replace("_z", "_b"));
      }
    }
    IOUtils.writeLines(urlList, "\n", new FileOutputStream(tarFile));
  }
  public static void getPhoto (String url) throws Exception {
    File file = new File ("C:\\Users\\jaybo\\Desktop\\photoUrl\\photo\\" + UUID.randomUUID().toString() + ".jpg");
    if (!file.exists()) {
      file.createNewFile();
    }
    System.out.println("begin download " + url);
    InputStream is = HttpUtils.getInputStreamByGetMethod(url, null, null, 5000,5000, "utf-8");
    FileOutputStream out = new FileOutputStream(file);
    //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
    byte[] data = readInputStream(is);  
    //写入数据  
    out.write(data);  
    //关闭输出流  
    out.close();  
    System.out.println("end download " + url);
  }
  public static byte[] readInputStream(InputStream inStream) throws Exception{  
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
    //创建一个Buffer字符串  
    byte[] buffer = new byte[1024];  
    //每次读取的字符串长度，如果为-1，代表全部读取完毕  
    int len = 0;  
    //使用一个输入流从buffer里把数据读取出来  
    while( (len=inStream.read(buffer)) != -1 ){  
        //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
        outStream.write(buffer, 0, len);  
    }
    //关闭输入流  
    inStream.close();  
    //把outStream里的数据写入内存  
    return outStream.toByteArray();  
  }
}
