/**
 * File：WordCountWithNewApi.java
 * Package：com.cd.cdwoo.hadoop.demo
 * Author：chendong.bj@fang.com
 * Date：2017年5月2日 下午2:43:08
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.hadoop.demo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;
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

import com.cd.cdwoo.hadoop.demo.WordCountWithNewApi.Map.Reduce;

/**
 * @author chendong.bj@fang.com
 */
public class WordCountWithNewApi  extends Configured implements Tool {
  private static Text outputKey = new Text();
  private static Text outputValue = new Text();
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
//      String[] lines = value.toString().split(" ");
//        context.write(new Text(lines[0]), new Text(lines[1]));
      String[] fields = value.toString().split("#!#");
      String x = fields[1].split("\\+")[0];
      String y = fields[1].split("\\+")[1];
      outputKey.set(fields[0]+ "#!#" + x.substring(0, x.lastIndexOf(".") + 3) + "+" + y.substring(0, y.lastIndexOf(".") + 3) + "#!#" + fields[2].split(" ")[0]);
      outputValue.set(fields[2].split(" ")[1]);
      context.write(outputKey, outputValue);
    }
    
    public static class Reduce extends Reducer<Text, Text, Text, NullWritable> {
      public void reduce (Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //对values的值进行倒排，然后写入到hdfs
//        List<Integer> list = new ArrayList<Integer>();
//        for (Text value : values) {
//          list.add(Integer.parseInt(value.toString()));
//        }
//        Collections.sort(list);
//        for (Integer num : list) {
//          context.write(key, new IntWritable(num));
//        }
        List<Long> list = new ArrayList<>();
        for (Text value : values) {
          try {
            list.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(key.toString().split("#!#")[2] + " " + value).getTime());
          }
          catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        Collections.sort(list);
        //计算活动时长
        context.write(new Text(key.toString() +"#!#" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(list.get(0))).split(" ")[1] +"#!#"+ (list.get(list.size() - 1) - list.get(0))/1000+"s") , null);
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
