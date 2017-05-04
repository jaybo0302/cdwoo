/**
 * File：SequenceFileRead.java
 * Package：com.cd.cdwoo.hadoop.demo
 * Author：chendong.bj@fang.com
 * Date：2017年5月3日 下午3:43:19
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.hadoop.demo;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;


/**
 * @author chendong.bj@fang.com
 */
public class SequenceFileRead {
  
  /**
   * methods desc.
   * @author chendong.bj@fang.com
   * @date 2017年5月3日
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    String uri = "file:///chendong/dir";
    Configuration conf = new Configuration();
    
    FileSystem fs = FileSystem.get(URI.create(uri), conf);
    Path path = new Path(uri);
    
    SequenceFile.Reader reader = new SequenceFile.Reader(fs, path, conf);
    
    Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(), conf);
    Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), conf);
    
    long position = reader.getPosition();
    
    while (reader.next(key, value)) {
      String syncSeen = reader.syncSeen() ? "*" : "";
      System.out.printf("[%s%s]\t%s\t%s\n", position, syncSeen, key, value);
      position = reader.getPosition();
    }
    
    IOUtils.closeStream(reader);
  }
  
}
