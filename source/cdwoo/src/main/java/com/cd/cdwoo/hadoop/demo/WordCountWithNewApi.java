/**
 * File：WordCountWithNewApi.java
 * Package：com.cd.cdwoo.hadoop.demo
 * Author：chendong.bj@fang.com
 * Date：2017年5月2日 下午2:43:08
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.hadoop.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
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

import com.cd.cdwoo.hadoop.demo.WordCountWithNewApi.Map.Reduce;

/**
 * @author chendong.bj@fang.com
 */
public class WordCountWithNewApi  extends Configured implements Tool {
  /**
   * methods desc.
   * @author chendong.bj@fang.com
   * @date 2017年5月2日
   * @param args
   * @throws Exception 
   */
  public static void main(String[] args) throws Exception {
    int ret = ToolRunner.run(new WordCountWithNewApi(), args);
    System.exit(ret);
  }
  public static class Map extends Mapper<LongWritable, Text, Text, Text> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    public void map (LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      String[] lines = value.toString().split(" ");
        context.write(new Text(lines[0]), new Text(lines[1]));
    }
    
    public static class Reduce extends Reducer<Text, Text, Text, IntWritable> {
      public void reduce (Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //对values的值进行倒排，然后写入到hdfs
        List<Integer> list = new ArrayList<Integer>();
        for (Text value : values) {
          list.add(Integer.parseInt(value.toString()));
        }
        Collections.sort(list);
        for (Integer num : list) {
          context.write(key, new IntWritable(num));
        }
      }
    }
  }
  public int run(String[] args) throws Exception {
    Job job = new Job(getConf());
    job.setJarByClass(WordCountWithNewApi.class);
    job.setJobName("WordCount");
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    
    job.setMapperClass(Map.class);
    job.setReducerClass(Reduce.class);
    
    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
    
    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    
    boolean success = job.waitForCompletion(true);
    return success ? 0 : 1;
  }
}
