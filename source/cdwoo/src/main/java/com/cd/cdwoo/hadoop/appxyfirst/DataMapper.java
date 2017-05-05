package com.cd.cdwoo.hadoop.appxyfirst;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;


public class DataMapper extends Mapper<LongWritable, Text, Text, Text> {
  private static final IntWritable one = new IntWritable(1);
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
    /*String line = value.toString();
    String[] fields = line.split(",");
    if (fields.length == 7) {
      context.write(new Text(fields[3]), one);
    }*/
    //baseinfo.gz - imei,ip^message^os^version^xy^username^httpcompany,action,city,time,page,passportid
    String[] fields = value.toString().split(",", -1);
    String[] info = fields[1].split("\\^", -1);
    //��2017-05-04 23:09:13 
    //Ψһ�û���ʶ����λ��꣬���ڣ�ʱ��
    if (fields.length == 7 && info.length == 7 && !info[4].equals("")) {
      if (cities.contains(fields[3])) {
        //String[] dates = fields[4].split(" ", -1);
        outputKey.set(fields[0]);
        outputValue.set(info[4] + "#!#" + fields[4]);
        context.write(outputKey, outputValue);
      }
    }
  }

  @Override
  protected void setup(Context context) throws IOException, InterruptedException {
    super.setup(context);
    cities.add("����");
    cities.add("����");
//    cities.add("�Ϻ�");
//    cities.add("����");
  }
  
}
