/**
 * File：AddTotalPro.java
 * Package：com.cd.cdwoo.hadoop.demo
 * Author：chendong.bj@fang.com
 * Date：2017年5月8日 下午3:11:30
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.hadoop.demo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.cd.cdwoo.hadoop.demo.AddTotalPro.Map.Reduce;

/**
 * @author chendong.bj@fang.com
 */
public class AddTotalPro extends Configured implements Tool {
  private static Text outputKey = new Text();
  private static Text outputValue = new Text();
  /**
   * methods desc.
   * @author chendong.bj@fang.com
   * @date 2017年5月8日
   * @param args
   * @throws Exception 
   */
  public static void main(String[] args) throws Exception {
    // TODO Auto-generated method stub
    int ret = ToolRunner.run(new AddTotalPro(), args);
    System.exit(ret);
  }

  public static class Map extends Mapper<LongWritable, Text, Text, Text> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    public void map (LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      //293082683545e68bad2f3200853775becfe4d59b#!#113.34+23.13#!#2017-05-02#!#11:16:23#!#15622s
      outputKey.set(value.toString().split("#!#")[0] + "#!#" + value.toString().split("#!#")[1]);
      outputValue.set(value.toString().split("#!#")[2] + "#!#" + value.toString().split("#!#")[3] + "#!#" + value.toString().split("#!#")[4]);
      context.write(outputKey, outputValue);
    }
    
    public static class Reduce extends Reducer<Text, Text, Text, NullWritable> {
      public void reduce (Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        HashMap<Long, String> map = new HashMap<>();
        List<Long> list = new ArrayList<>();
        for (Text value:values) {
          //2017-05-02#!#11:16:23#!#15622s
          String[] fields = value.toString().split("#!#");
          String date = fields[0] + " " + fields[1];
          try {
            
            long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
            list.add(time);
            map.put(time, value.toString());
          }
          catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        
        Collections.sort(list);
        long timeTotal = 0;
        for(Long l : list) {
          timeTotal += Long.parseLong(map.get(l).split("#!#")[2].replace("s", ""));
          context.write(new Text(key.toString() + "#!#" + map.get(l) + "#!#" + timeTotal +"s"), null);
        }
      }
    }
  }
  public int run(String[] args) throws Exception {
    Job job = new Job(getConf());
    job.setJarByClass(AddTotalPro.class);
    job.setJobName("add total pro");
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    
    job.setMapperClass(Map.class);
    job.setReducerClass(Reduce.class);
    
    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
    
    FileInputFormat.setInputPaths(job, "C:\\Users\\jaybo\\Desktop\\hadoop\\activity\\part-r-*");
    FileOutputFormat.setOutputPath(job, new Path("C:\\Users\\jaybo\\Desktop\\hadoop\\final"));
    
    boolean success = job.waitForCompletion(true);
    return success ? 0 : 1;
  }
  
}
