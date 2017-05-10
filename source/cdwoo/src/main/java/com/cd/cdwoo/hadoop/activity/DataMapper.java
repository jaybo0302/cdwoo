package com.cd.cdwoo.hadoop.activity;



import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


/**
 * @author chendong.bj@fang.com
 */
public class DataMapper extends Mapper<LongWritable, Text, Text, Text> {

  private Text outputKey = new Text();
  private Text outputValue = new Text();
  private Set<String> cities = new HashSet<String>();
  @Override
  protected void cleanup(Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException,
      InterruptedException {
    super.cleanup(context);
  }

  @Override
  protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
      throws IOException, InterruptedException {
    //2c3bb5c52fbba118fabe6310970e058ac84bbb44,113.2894,23.1406,2017-05-04,15:26:44
//    String[] fields = value.toString().split(",");
//    if (fields[1].lastIndexOf(".") < 0 || fields[2].lastIndexOf(".") < 0) {
//      return;
//    }
//    //将imei 作为key
//    //113.2116+23.16有类似的数据，做特殊处理
//    outputKey.set(fields[0]);
//    //time作为value 以便计算活动时长。
//    outputValue.set(formatXY(fields[1]) + "," + formatXY(fields[2]) + "," + fields[3]+ "," + fields[4]);
//    context.write(outputKey, outputValue);
    
    String[] fields = value.toString().split(",", -1);
    String[] info = fields[1].split("\\^", -1);
    if (fields[4].length() != 19) {
      return;
    }
    if (fields.length == 7 && info.length == 7 && !info[4].equals("")) {
      if (cities.contains(fields[3])) {
        outputKey.set(fields[0]);
        outputValue.set(formatXY(info[4].split("\\+")[0]) + "," + formatXY(info[4].split("\\+")[1]) + "," + fields[4].replace(" ", ","));
        context.write(outputKey, outputValue);
      }
    }
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
  @Override
  protected void setup(Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
    super.setup(context);
    cities.add("北京");
    cities.add("广州");
  }
  
}
