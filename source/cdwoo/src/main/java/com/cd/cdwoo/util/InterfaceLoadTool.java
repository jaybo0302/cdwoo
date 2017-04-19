package com.cd.cdwoo.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
/**
 * 新房的压力测试工具：读取Access日志中的Url，启动N个线程模拟客户端同时向接口提交
 * 这些Url请求。
 *
 * 该工具也可以用来测试其他GET方式为主的服务接口
 */
public class InterfaceLoadTool {

  //                                    - TaskRunner1
  //                                   /
  //              Task                /
  // TaskFeeder ----------> TaskQueue --> TaskRunner2
  //                                  \
  //                                   \
  //                                    - TaskRunner3
  class LoadTask {
    String uri;
    Map<String, String> params;
    public LoadTask(String uri, Map<String, String> params) {
      this.uri = uri;
      this.params = params;
    }
  }

  // 保存任务的队列
  class TaskQueue {
    List<LoadTask> queue = Collections.synchronizedList(new LinkedList<LoadTask>());
    public void offer(LoadTask task) {
      //task.uri =  "http://cms.light.test.fang.com" + task.uri;
      task.uri =  "http://local.fang.com:8080" + task.uri;
      queue.add(task);
    }
    public LoadTask poll() {
      //queue.isEmpty();
      if (queue.size() == 0) {
        return null;
      }
      return queue.remove(0);
    }
    public int size() {
      return queue.size();
    }
    public void clear() {
      queue.clear();
    }
    public void dump(PrintWriter out) throws IOException {
      out.println("queue.size:" + queue.size());
    }
  }
  // 读取Access日志文件中的Url，向队列中填充任务
  class TaskFeeder extends Thread {
    private File logFile;
    private TaskQueue queue;
    public TaskFeeder(File logFile, TaskQueue queue) {
      this.logFile = logFile;
      this.queue = queue;
    }
    //124.251.46.84 - "null-user" [17/Apr/2017:06:01:02 +0800] "GET /knowledge/v1/knowledges/recommendation.do?channel=xf&quality=high&city=%E5%8C%97%E4%BA%AC&pageSize=4&page=1 HTTP/1.0" 200 2897 "-" "-"
    final Pattern LOG_PATTERN = Pattern.compile(" "); 
    private void feed() {
      BufferedReader reader = null;
      try {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(logFile), "GBK"));
        String line = null;
        while ((line = reader.readLine()) != null) {
          if (line.length() < 10) {
            continue;
          }
          String[] tokens = LOG_PATTERN.split(line);
          if (tokens == null) continue;
          StringBuffer urlSB = new StringBuffer(512);
          Map <String, String> params = new HashMap<>();
          if (!tokens[6].startsWith("/knowledge/v1/")) continue;
          System.out.println(tokens[6]);
          queue.offer(new LoadTask(tokens[6], params));
          while (queue.size() >= 1000) {
            try {
              sleep(100);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      } catch (IOException ioe) {
        System.err.println("feeder - failed to read request from file '" + logFile.getAbsolutePath() + "', because:" + ioe);
        System.exit(-1);
      } finally {
        if (reader != null) {
          try {
            reader.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
    @Override
    public void run() {
      feed();
    }
  }
  // 从队列中提取任务并执行的线程
  class TaskRunner extends Thread {
    TaskQueue queue;
    int trytimes = 0;
    public TaskRunner(TaskQueue queue) {
      this.queue = queue;
    }
    // 调用服务端的接口，提交Url请求
    private void doTask(LoadTask task) {
      String result = HttpUtils.get(task.uri, task.params, null, 50000, 50000, "utf-8", "utf-8");
      System.out.println(result);
    }
    public void run() {
      while (true) {
        LoadTask task = queue.poll();
        if (task == null) {
          trytimes ++;
          if (trytimes >= 10) {
            System.out.println("[INFO ] - runner:" + getName() + " - over!");
            break;
          }
          try {
            sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } else {
          trytimes = 0;
          doTask(task);
        }
      }
    }
  }
  public static void main(String[] args) {
    int runnerCount = 3;
    File logFile = null;
    for (int i=0; i<args.length; i++) {
      if ("-log".equals(args[i])) {
        logFile = new File(args[++i]);
      } else if ("-thread".equals(args[i])) {
        runnerCount = Integer.parseInt(args[++i]);
        runnerCount = (runnerCount < 3) ? 3 : runnerCount;
      }
    }
    if (logFile == null) {
      System.out.println("usage: java " + InterfaceLoadTool.class.getName() + " -log $LOG-FILE -thread $THREAD-NUMBER");
      return;
    }
    InterfaceLoadTool test = new InterfaceLoadTool();
    TaskQueue queue = test.new TaskQueue();
    TaskFeeder feeder = test.new TaskFeeder(logFile, queue);
    feeder.start();
    TaskRunner[] runners = new TaskRunner[runnerCount];
    for (int i=0; i<runners.length; i++) {
      runners[i] = test.new TaskRunner(queue);
      runners[i].start();
    }
  }
}
