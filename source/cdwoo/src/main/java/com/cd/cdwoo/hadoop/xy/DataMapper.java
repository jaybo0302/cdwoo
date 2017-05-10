package com.cd.cdwoo.hadoop.xy;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;


public class DataMapper extends Mapper<LongWritable, Text, Text, Text> {
  private Text outputKey = new Text();
  private Text outputValue = new Text();
  private Set<String> cities = new HashSet<String>();
  
  @Override
  protected void cleanup(Context context) throws IOException,
      InterruptedException {
    super.cleanup(context);
  }

  @Override
  protected void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
    String[] fields = value.toString().split(",", -1);
    String[] info = fields[1].split("\\^", -1);
    if (fields[4].length() != 19) {
      return;
    }
    if (fields.length == 7 && info.length == 7 && !info[4].equals("")) {
      if (cities.contains(fields[3])) {
        outputKey.set(fields[0]);
        outputValue.set(info[4] + "," + fields[4]);
        context.write(outputKey, outputValue);
      }
    }
  }

  @Override
  protected void setup(Context context) throws IOException, InterruptedException {
    super.setup(context);
    cities.add("北京");
    cities.add("上海");
  }
  
}
