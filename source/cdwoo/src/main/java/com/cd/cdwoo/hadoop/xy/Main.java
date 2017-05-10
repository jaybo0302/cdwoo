package com.cd.cdwoo.hadoop.xy;
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
      if ("work".equals(args[0])) {
        runWorkJob(args);
      } else if ("home".equals(args[0])) {
        runHomeJob(args);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  public static boolean runWorkJob(String[] args) throws IOException {
    Configuration conf = HBaseConfiguration.create();
    Job job = Job.getInstance(conf, "work xy coordinate job");
    job.setJarByClass(Main.class);
    job.setMapperClass(DataMapper.class);
    job.setReducerClass(DataReducer.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(NullWritable.class);
    job.setNumReduceTasks(300);
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo09.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo10.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo11.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo12.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo13.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo14.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo15.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo16.gz");
    
    Path output = new Path("/hbasegroup/cd/appxy/work");
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
  public static boolean runHomeJob(String[] args) throws IOException {
    Configuration conf = HBaseConfiguration.create();
    Job job = Job.getInstance(conf, "home xy coordinate job");
    job.setJarByClass(Main.class);
    job.setMapperClass(DataMapper.class);
    job.setReducerClass(DataReducer.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(NullWritable.class);
    job.setNumReduceTasks(300);
    //FileInputFormat.addInputPaths(job, "/logs/app/serialized/201705*/visitor.appsf.general.baseinfo*.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo00.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo01.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo02.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo03.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo04.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo05.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo06.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo21.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo22.gz");
    FileInputFormat.addInputPaths(job, "/logs/app/serialized/" + args[1] +"/visitor.appsf.general.baseinfo23.gz");
    Path output = new Path("/hbasegroup/cd/appxy/home");
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
