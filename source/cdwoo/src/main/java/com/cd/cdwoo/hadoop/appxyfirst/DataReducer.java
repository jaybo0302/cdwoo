package com.cd.cdwoo.hadoop.appxyfirst;

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

import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class DataReducer extends Reducer<Text, Text, Text, NullWritable> {

  @Override
  protected void cleanup(Context context) throws IOException,
      InterruptedException {
    super.cleanup(context);
  }

  @Override
  protected void reduce(Text key, Iterable<Text> values, Context context)
      throws IOException, InterruptedException {
    /*int sum = 0;
    for (IntWritable value : values) {
      sum += value.get();
    }
    context.write(key, new IntWritable(sum));*/
    //将values中的时间取出，然后按照时间排序放入list中 再遍历list写入hdfs
    //key是imei  
    //values内容是xy#!#时间   106.6041+29.6022#!#2017-05-02 09:45:34
    List<Long> timeList = new ArrayList<>();
    Map<Long, String> map = new HashMap<>();
    for(Text value : values) {
      String info = value.toString();
      String date = info.split("#!#")[1];
      long time = 0;
      try {
        time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
      }
      catch (ParseException e) {
        e.printStackTrace();
      }
      if (!timeList.contains(time)) {
        timeList.add(time);
        map.put(time, info);
      }
    }
    Collections.sort(timeList);
    for (Long time : timeList) {
      context.write(new Text(key.toString() + "#!#" + map.get(time)), null);
    }
  }

  @Override
  protected void setup(Context context) throws IOException,
      InterruptedException {
    super.setup(context);
  }
  
}
