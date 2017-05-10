package com.cd.cdwoo.hadoop.activity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


/**
 * @author chendong.bj@fang.com
 */
public class DataReducer extends Reducer<Text, Text, Text, NullWritable> {

  @Override
  protected void cleanup(Reducer<Text, Text, Text, NullWritable>.Context context) throws IOException,
      InterruptedException {
    super.cleanup(context);
  }

  @Override
  protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, NullWritable>.Context context)
      throws IOException, InterruptedException {//key 2c3bb5c52fbba118fabe6310970e058ac84bbb44
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
              //超过8小时
              if ((t - beginTime) > 8 * 3600 * 1000) {
                //之前有活动记录
                if (endTime - beginTime > 0) {
                  acTime = (endTime - beginTime);
                  totalTime = totalTimeMap.get(recordLocation) == null ? 0 : totalTimeMap.get(recordLocation);
                  totalTime += acTime;
                  totalTimeMap.put(recordLocation, totalTime);
                  context.write(new Text(key.toString() + "," + recordLocation + "," + sdf.format(new Date(beginTime)).replace(" ",",") + "," + acTime/1000 + "," + totalTime/1000), null);
                  //将当前位置时间记录
                  recordLocation = currentLocation;
                  beginTime = t;
                  endTime = t;
                  continue;
                } else {
                  //没有活动记录，直接将当前位置时间记录
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
            //如果位置不一样，则记录数据，如果开始时间和结束时间一样，则不记录数据，活动时间为0，无效数据
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
              //没有活动记录，则要将当前的位置设置为下一条数据的位置,开始结束时间重置
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
                //计算当前时间是否超过8个小时 如果超过8小时最后一条记录无效
                if (acTime > 8*3600*1000) {
                  //如果超过8小时，查看是否之前有活动记录，有则记录，没有则全部完毕
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
                //居住活动是10小时
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
    }

  @Override
  protected void setup(Reducer<Text, Text, Text, NullWritable>.Context context) throws IOException,
      InterruptedException {
    super.setup(context);
  }
}
