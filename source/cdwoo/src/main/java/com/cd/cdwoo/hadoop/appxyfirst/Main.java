package com.cd.cdwoo.hadoop.appxyfirst;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class Main {
  public static void main(String[] args) {
    try {
      runJob();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  public static boolean runJob() throws IOException {
    Configuration conf = HBaseConfiguration.create();
    Job job = Job.getInstance(conf, "chendong test job");
    job.setJarByClass(Main.class);
    job.setMapperClass(DataMapper.class);
    job.setReducerClass(DataReducer.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(NullWritable.class);
    job.setNumReduceTasks(300);
    //��Ӷ�ȡ·�� 
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/201705*/visitor.appsf.general.baseinfo*.gz");
    //FileInputFormat.addInputPaths(job, "/logs/app/splitted/20161009/visitor.appzf.zf.zztype.gz");
    //������·��
    Path output = new Path("/hbasegroup/cd/appxy/");
    FileSystem fs = FileSystem.get(conf);
    fs.delete(output, true);
    FileOutputFormat.setOutputPath(job, output);
    try {
      job.waitForCompletion(true);
    }
    catch (ClassNotFoundException | InterruptedException e) {
      e.printStackTrace();
    }
    return job.isSuccessful();
  }
}
