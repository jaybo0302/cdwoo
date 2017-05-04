package com.cd.cdwoo.hadoop.demo;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;


public class SequenceFileWriteDemo {
  private static String[] myValue = {"hello world", "bye word", "hello hadoop", "bye hadoop"};
  public static void main(String[] args) throws IOException {
    String uri = "file:///chendong/dir";
    Configuration conf = new Configuration();
    /*conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
    conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());*/
    FileSystem fs = FileSystem.get(URI.create(uri), conf);
    Path path = new Path(uri);
    IntWritable key = new IntWritable();
    Text value = new Text();
    SequenceFile.Writer writer = null;
    
    writer = SequenceFile.createWriter(fs, conf, path, key.getClass(), value.getClass());
    for (int i = 0; i < 500000; i++) {
      key.set(500000 - i);
      value.set(myValue[i%myValue.length]);
      writer.append(key, value);
    }
    IOUtils.closeStream(writer);
  }
  
}
