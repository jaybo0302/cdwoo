/**
 * File：Test.java
 * Package：com.cd.cdwoo.hadoop.test
 * Author：chendong.bj@fang.com
 * Date：2017年5月9日 下午1:14:54
 * Copyright (C) 2003-2017 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.hadoop.multiSrc;

import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
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

import com.cd.cdwoo.util.HbaseUtils;

public class Test  extends Configured implements Tool {
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
    public static class Map extends Mapper<LongWritable, Text, Text, NullWritable> {
      public void map (LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        
//        InputSplit split = context.getInputSplit();
//        Class<? extends InputSplit> splitClass = split.getClass();
//
//        FileSplit fileSplit = null;
//        if (splitClass.equals(FileSplit.class)) {
//            fileSplit = (FileSplit) split;
//            System.out.println(fileSplit.toString());
//        } else if (splitClass.getName().equals(
//                "org.apache.hadoop.mapreduce.lib.input.TaggedInputSplit")) {
//            // begin reflection hackery...
//            try {
//                Method getInputSplitMethod = splitClass
//                        .getDeclaredMethod("getInputSplit");
//                getInputSplitMethod.setAccessible(true);
//                fileSplit = (FileSplit) getInputSplitMethod.invoke(split);
//                System.out.println(fileSplit.toString());
//            } catch (Exception e) {
//                // wrap and re-throw error
//                throw new IOException(e);
//            }
//
//            // end reflection hackery
          context.write(new Text(value.toString()), NullWritable.get());
        }
        
//        Database db = new Database("ub");
//        String sql = "select 0";
//        if ("IMPORT_SFCARD_TuangouRecordWords".equals(value.toString())) {
//          sql = "SELECT userid,newcode from IMPORT_SFCARD_TuangouRecordWords where userid!=-1 and Createtime >= DATEADD(DAY, -30, CONVERT(varchar(12),GETDATE(),23))";
//        } else if ("IMPORT_SFCARD_FangCollection".equals(value.toString())) {
//          sql = "SELECT userid,newcode from IMPORT_SFCARD_FangCollection where userid!=-1 and userid != 0 and Createtime >= DATEADD(DAY, -30, CONVERT(varchar(12),GETDATE(),23))";
//        } else if ("BaseImport_XFMT_ActivityOrders".equals(value.toString())) {
//          sql = "SELECT PassportID userid,newcode from BaseImport_XFMT_ActivityOrders where Newcode is not null and Newcode != '' and Createtime >= DATEADD(DAY, -30, CONVERT(varchar(12),GETDATE(),23))";
//        } else if ("BaseImport_XFMT_LuckyOrders".equals(value.toString())) {
//          sql = "SELECT PassportID userid,newcode from BaseImport_XFMT_LuckyOrders where Newcode is not null and Newcode != '' and Createtime >= DATEADD(DAY, -30, CONVERT(varchar(12),GETDATE(),23))";
//        } else if ("BaseImport_XFMT_SeckillOrders".equals(value.toString())) {
//          sql = "SELECT PassportID userid,newcode from BaseImport_XFMT_SeckillOrders where Newcode is not null and Newcode != '' and Createtime >= DATEADD(DAY, -30, CONVERT(varchar(12),GETDATE(),23))";
//        } else if ("IMPORT_SFCARD_TradingHallUserInfo".equals(value.toString())) {
//          sql = "SELECT userid,newcode from IMPORT_SFCARD_TradingHallUserInfo where Newcode != '0' AND Userid != -1 and Createtime >= DATEADD(DAY, -30, CONVERT(varchar(12),GETDATE(),23))";
//        } 
//        try {
//          ResultSet rs = db.getConnection().createStatement().executeQuery(sql);
//          while(rs.next()) {
//            String userid = rs.getString(1);
//            String newcode = rs.getString(2);
//            context.write(new Text(userid+"##"+newcode), NullWritable.get());
//          }
//          rs.close();
//        }
//        catch (SQLException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//        }
//        db.close();
//      }
    }
    public static class Reduce extends Reducer<Text, Text, Text, NullWritable> {
      public void reduce (Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
//        context.write(key, null);
        HTable table = (HTable)HbaseUtils.getTable("idmapping3_ref"); 
        for (int i = 0; i < 2; i++) {
          Result r = table.get(new Get("354273054409896&&kpt:imei".getBytes()));
          for (KeyValue kv : r.list()) {
            System.out.println(kv.getKeyString());
            System.out.println(Bytes.toString(kv.getFamily()));
            System.out.println(Bytes.toString(kv.getQualifier()));
          }
          HbaseUtils.closeTable(table);
          System.out.println(Thread.currentThread().getName() + HbaseUtils.class.hashCode());
        }
      }
    }
    public int run(String[] args) throws Exception {
      Job job = new Job(getConf());
      job.setJarByClass(Test.class);
      job.setJobName("test");
      job.setMapperClass(Map.class);
      job.setReducerClass(Reduce.class);
      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(NullWritable.class);
      job.setInputFormatClass(TextInputFormat.class);
      job.setOutputFormatClass(TextOutputFormat.class);
      FileInputFormat.addInputPath(job, new Path("E:\\file-first-step\\hadoop\\multiSrc.txt"));
      FileOutputFormat.setOutputPath(job, new Path("E:\\file-first-step\\hadoop\\multiSrc1"));
      boolean success = job.waitForCompletion(true);
      return success ? 0 : 1;
    }
  }
