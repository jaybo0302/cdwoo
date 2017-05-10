/**
 * File：Test.java
 * Package：com.cd.cdwoo.hadoop.test
 * Author：chendong.bj@fang.com
 * Date：2017年5月9日 下午1:14:54
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.hadoop.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class Test  extends Configured implements Tool {
    private static Set<String> cities = new HashSet<String>();
    private static Text outputKey = new Text();
    private static Text outputValue = new Text();
    static {
      cities.add("北京");
      cities.add("广州");
    }
    /**
     * methods desc.
     * @author chendong.bj@fang.com
     * @date 2017年5月2日
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
      int ret = ToolRunner.run(new Test(), args);
      System.exit(ret);
    }
    public static class Map extends Mapper<LongWritable, Text, Text, Text> {
      public void map (LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        ////////////////////////=====================xy map======================///////////////////
//        String[] fields = value.toString().split(",", -1);
//        String[] info = fields[1].split("\\^", -1);
//        if (fields[4].length() != 19) {
//          return;
//        }
//        if (fields.length == 7 && info.length == 7 && !info[4].equals("")) {
//          if (cities.contains(fields[3])) {
//            outputKey.set(fields[0]);
//            outputValue.set(info[4] + "," + fields[4]);
//            context.write(outputKey, outputValue);
//          }
//        }
       //////////////////////=====================xy map======================///////////////////
        
        
       ///////////////////////////================activity map==================///////////////////////
//        String[] fields = value.toString().split(",", -1);
//        String[] info = fields[1].split("\\^", -1);
//        if (fields[4].length() != 19) {
//          return;
//        }
//        if (fields.length == 7 && info.length == 7 && !info[4].equals("")) {
//          if (cities.contains(fields[3])) {
//            outputKey.set(fields[0]);
//            outputValue.set(formatXY(info[4].split("\\+")[0]) + "," + formatXY(info[4].split("\\+")[1]) + "," + fields[4].replace(" ", ","));
//            context.write(outputKey, outputValue);
//          }
//        }
        String[] fields = value.toString().split(",");
        if (fields[1].lastIndexOf(".") < 0 || fields[2].lastIndexOf(".") < 0) {
          return;
        }
        outputKey.set(fields[0]);
        outputValue.set(formatXY(fields[1]) + "," + formatXY(fields[2]) + "," + fields[3]+ "," + fields[4]);
        context.write(outputKey, outputValue);
        ///////////////////////////================activity map==================///////////////////////
        
                
        ///////////////////////////================add map==================///////////////////////
//        String[] fields = value.toString().split(",");
//        outputKey.set(fields[0] + "," + fields[1] + "," + fields[2]);
//        outputValue.set(fields[3] + "," + fields[4] + "," + fields[5]);
//        context.write(outputKey, outputValue);
        ///////////////////////////================add map==================///////////////////////
      }
    }
    public static class Reduce extends Reducer<Text, Text, Text, NullWritable> {
      public void reduce (Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //////////////////////=====================xy reduce======================///////////////////
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<Long> timeList = new ArrayList<>();
//        HashMap<Long, String> map = new HashMap<>();
//        for(Text value : values) {
//          String info = value.toString();
//          String date = info.split(",")[1];
//          long time = 0;
//          try {
//            time = sdf.parse(date).getTime();
//          }
//          catch (ParseException e) {
//            e.printStackTrace();
//          }
//          //对相同时间进行排重
//          if (!timeList.contains(time)) {
//            timeList.add(time);
//            map.put(time, info);
//          }
//        }
//        //按照时间排序
//        Collections.sort(timeList);
//        //遍历时间list并将对应的info取出
//        for (Long time : timeList) {
//          String[] info = map.get(time).split(",");
//          String xy = info[0].replace("+", ",");
//          String date = info[1].replace(" ", ",");
//          context.write(new Text(key.toString() + "," + xy + "," + date), null);
//        }
        //////////////////////=====================xy reduce======================///////////////////
        
        
              
      ///////////////////////////================activity reduce==================///////////////////////

        //key 2c3bb5c52fbba118fabe6310970e058ac84bbb44
        //value113.2894,23.1406,2017-05-04,15:26:44
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //首先将用户所有的数据按照时间排序 时间已做排重 并将用户value存入map中
        //遍历时间，如果xy一样的话数据用户在一个位置停留，直到定位不一样算作一次活动结束记录开始时间，活动时间，以及累计时长
        //这里原来使用的是<Long,Text>键值对，在put的时候会发现value值始终是最后一个，值传递和引用传递的区别
        HashMap<Long, String> valueMap = new HashMap<>();
        HashMap<String, Long> totalTimeMap = new HashMap<>();
        List<Long> list = new ArrayList<>();
        for (Text value : values) {
          try {
            long time = sdf.parse(value.toString().split(",")[2] + " " + value.toString().split(",")[3]).getTime();
            if (list.contains(time)) {
              continue;
            }
            list.add(time);
            valueMap.put(time, value.toString());
          }
          catch (ParseException e) {
            e.printStackTrace();
          }
        }
        //按照时间排序
        Collections.sort(list);
        //计算活动时长
        //context.write(new Text(key.toString() +"," + sdf.format(new Date(list.get(0))).split(" ")[1] +","+ (list.get(list.size() - 1) - list.get(0))/1000) , null);
        //遍历时间list
        long beginTime = list.get(0);
        long endTime = list.get(0);
        long totalTime = 0;
        long acTime = 0;
        String recordLocation = valueMap.get(list.get(0)).toString().split(",")[0]+"," + valueMap.get(list.get(0)).toString().split(",")[1];
        for(long t : list) {
          System.err.println("value==" + valueMap.get(t) + "         time==" + t + "           beginTime=="+sdf.format(beginTime) + "                   endTime=" + sdf.format(endTime));
          //如果没有遍历到最后一个，正常进行
          if (t < list.get(list.size()-1)) {
            String currentLocation = valueMap.get(t).split(",")[0]+","+valueMap.get(t).split(",")[1];
            if (recordLocation.equals(currentLocation)) {
              //如果仍旧是一样的位置，记录时间，继续遍历
              //000db5c91e82608f0cc6016963e6e6e2082105a7,113.2430,23.2089,2017-04-25,14:10:36
              //000db5c91e82608f0cc6016963e6e6e2082105a7,113.2424,23.2091,2017-04-26,09:15:09
              //如果出现以上情况，则要进行上一天的活动清算，即工作活动时间不能超过8个小时，居住活动时间不能超过10个小时
              //判断当前时间是工作还是居住
              int hour = Integer.parseInt(sdf.format(t).split(" ")[1].split(":")[0]);
              if (8<hour&&hour<17) {
                if ((t - beginTime) > 8 * 3600 * 1000) {
                  if (endTime - beginTime > 0) {
                    acTime = (endTime - beginTime);
                    totalTime = totalTimeMap.get(recordLocation) == null ? 0 : totalTimeMap.get(recordLocation);
                    totalTime += acTime;
                    totalTimeMap.put(recordLocation, totalTime);
                    context.write(new Text(key.toString() + "," + recordLocation + "," + sdf.format(new Date(beginTime)).replace(" ",",") + "," + acTime/1000 + "," + totalTime/1000), null);
                    recordLocation = currentLocation;
                    beginTime = t;
                    endTime = t;
                    continue;
                  } else {
                    recordLocation = currentLocation;
                    beginTime = t;
                    endTime = t;
                    continue;
                  }
                }
              } else {
                if ((t - beginTime) > 10 * 3600 * 1000) {
                  if (endTime - beginTime > 0) {
                    acTime = (endTime - beginTime);
                    totalTime = totalTimeMap.get(recordLocation) == null ? 0 : totalTimeMap.get(recordLocation);
                    totalTime += acTime;
                    totalTimeMap.put(recordLocation, totalTime);
                    context.write(new Text(key.toString() + "," + recordLocation + "," + sdf.format(new Date(beginTime)).replace(" ",",") + "," + acTime/1000 + "," + totalTime/1000), null);
                    recordLocation = currentLocation;
                    beginTime = t;
                    endTime = t;
                    continue;
                  } else {
                    recordLocation = currentLocation;
                    beginTime = t;
                    endTime = t;
                    continue;
                  }
                }
              }
              endTime = t;
              continue;
            } else {
              //如果位置不一样，则记录数据，如果开始时间和结束时间一样，则不记录数据，位置时间为0，无效数据
              if (endTime - beginTime > 0) {
                acTime = (endTime - beginTime);
                //从map中获取活动总时间，如果没有记录设置为0；
                totalTime = totalTimeMap.get(recordLocation) == null ? 0 : totalTimeMap.get(recordLocation);
                totalTime += acTime;
                totalTimeMap.put(recordLocation, totalTime);
                context.write(new Text(key.toString() + "," + recordLocation + "," + sdf.format(new Date(beginTime)).replace(" ",",") + "," + acTime/1000 + "," + totalTime/1000), null);
                //写入上一条数据之后要将所有的记录设置为当前的值
                recordLocation = currentLocation;
                beginTime = t;
                endTime = t;
                continue;
              } else {
                //如果仅为一条数据，则要将当前的位置设置为下一条数据的位置,开始结束时间重置
                recordLocation = currentLocation;
                beginTime = t;
                endTime = t;
                continue;
              }
            }
          } else {
            //如果遍历到最后一条
            //判断当前位置是否与上一条一样，如果一样，计算时间，如果不一样，将上一条记录之前的记录做统计，如果活动时间不为0，写入一条到hdfs
            String currentLocation = valueMap.get(t).split(",")[0]+","+valueMap.get(t).split(",")[1];
            int hour = Integer.parseInt(sdf.format(t).split(" ")[1].split(":")[0]);
            if (recordLocation.equals(currentLocation)) {
              if (t - beginTime > 0) {
                acTime = (t - beginTime);
                if (8<hour&&hour<17) {
                  if (acTime > 8*3600*1000) {
                    acTime = (endTime - beginTime);
                    if (acTime > 0) {
                      totalTime = totalTimeMap.get(recordLocation) == null ? 0 : totalTimeMap.get(recordLocation);
                      totalTime += acTime;
                      totalTimeMap.put(recordLocation, totalTime);
                      context.write(new Text(key.toString() + "," + recordLocation + "," + sdf.format(new Date(beginTime)).replace(" ",",") + "," + acTime/1000 + "," + totalTime/1000), null);
                    }
                  } else {
                    totalTime = totalTimeMap.get(recordLocation) == null ? 0 : totalTimeMap.get(recordLocation);
                    totalTime += acTime;
                    totalTimeMap.put(recordLocation, totalTime);
                    context.write(new Text(key.toString() + "," + recordLocation + "," + sdf.format(new Date(beginTime)).replace(" ",",") + "," + acTime/1000 + "," + totalTime/1000), null);
                  }
                } else {
                  if (acTime > 10*3600*1000) {
                    acTime = (endTime - beginTime);
                    if (acTime > 0) {
                      totalTime = totalTimeMap.get(recordLocation) == null ? 0 : totalTimeMap.get(recordLocation);
                      totalTime += acTime;
                      totalTimeMap.put(recordLocation, totalTime);
                      context.write(new Text(key.toString() + "," + recordLocation + "," + sdf.format(new Date(beginTime)).replace(" ",",") + "," + acTime/1000 + "," + totalTime/1000), null);
                    }
                  } else {
                    totalTime = totalTimeMap.get(recordLocation) == null ? 0 : totalTimeMap.get(recordLocation);
                    totalTime += acTime;
                    totalTimeMap.put(recordLocation, totalTime);
                    context.write(new Text(key.toString() + "," + recordLocation + "," + sdf.format(new Date(beginTime)).replace(" ",",") + "," + acTime/1000 + "," + totalTime/1000), null);
                  }
                }
              }
            } else {
              if (endTime - beginTime > 0) {
                acTime = (endTime - beginTime);
                totalTime = totalTimeMap.get(recordLocation) == null ? 0 : totalTimeMap.get(recordLocation);
                totalTime += acTime;
                totalTimeMap.put(recordLocation, totalTime);
                context.write(new Text(key.toString() + "," + recordLocation + "," + sdf.format(new Date(beginTime)).replace(" ",",") + "," + acTime/1000 + "," + totalTime/1000), null);
              }
            }
          }
        }
      
     ///////////////////////////================activity reduce==================///////////////////////
              
              
              
      ///////////////////////////================add reduce==================///////////////////////
//        HashMap<Long, String> map = new HashMap<>();
//        List<Long> list = new ArrayList<>();
//        for (Text value:values) {
//          //2017-05-02,11:16:23,15622
//          String[] fields = value.toString().split(",");
//          String date = fields[0] + " " + fields[1];
//          try {
//            long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
//            list.add(time);
//            map.put(time, value.toString());
//          }
//          catch (ParseException e) {
//            e.printStackTrace();
//          }
//        }
//        
//        Collections.sort(list);
//        long timeTotal = 0;
//        for(Long l : list) {
//          timeTotal += Long.parseLong(map.get(l).split(",")[2]);
//          context.write(new Text(key.toString() + "," + map.get(l) + "," + timeTotal), null);
//        }
      ///////////////////////////================add reduce==================///////////////////////
      }
    }
    public int run(String[] args) throws Exception {
      Job job = new Job(getConf());
      job.setJarByClass(Test.class);
      job.setJobName("test");
      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(Text.class);
      job.setMapperClass(Map.class);
      job.setReducerClass(Reduce.class);
      job.setInputFormatClass(TextInputFormat.class);
      job.setOutputFormatClass(TextOutputFormat.class);
      //////////////////xy/////////////////////////////////
//      FileInputFormat.setInputPaths(job, new Path("C:\\Users\\jaybo\\Desktop\\hadoop\\visitor.appsf.general.baseinfo01.gz"));
//      FileOutputFormat.setOutputPath(job, new Path("C:\\Users\\jaybo\\Desktop\\hadoop\\appxy"));
      
      
      /////////////////////activity////////////////////////////
      FileInputFormat.setInputPaths(job, new Path("C:\\Users\\jaybo\\Desktop\\hadoop\\part-r-00000"));
      FileOutputFormat.setOutputPath(job, new Path("C:\\Users\\jaybo\\Desktop\\hadoop\\activity"));
      
      /////////////////////add///////////////////////////////
//      FileInputFormat.setInputPaths(job, new Path("C:\\Users\\jaybo\\Desktop\\hadoop\\activity\\part*"));
//      FileOutputFormat.setOutputPath(job, new Path("C:\\Users\\jaybo\\Desktop\\hadoop\\final"));
      boolean success = job.waitForCompletion(true);
      return success ? 0 : 1;
    }
    
    
    public static String formatXY(String str) {
      int l = str.length();
      int o = str.lastIndexOf(".");
      if (str.length() - str.lastIndexOf(".") >= 3) {
        return str.substring(0, str.lastIndexOf(".") + 3);
      } else {
        for (int i=0; i<((3-(l-o))); i++){
          str += "0";
        }
        return str;
      }
    }
  }
