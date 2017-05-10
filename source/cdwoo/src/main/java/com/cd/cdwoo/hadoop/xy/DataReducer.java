package com.cd.cdwoo.hadoop.xy;

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
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    List<Long> timeList = new ArrayList<>();
    Map<Long, String> map = new HashMap<>();
    for(Text value : values) {
      String info = value.toString();
      String date = info.split(",")[1];
      long time = 0;
      try {
        time = sdf.parse(date).getTime();
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
      String[] info = map.get(time).split(",");
      String date = info[1].replace(" ", ",");
      context.write(new Text(key.toString() + "," + formatXY(info[0].split("\\+")[0]) + "," + formatXY(info[0].split("\\+")[1]) + "," + date), null);
    }
  }
  @Override
  protected void setup(Context context) throws IOException,
      InterruptedException {
    super.setup(context);
  }
  public String formatXY(String str) {
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
