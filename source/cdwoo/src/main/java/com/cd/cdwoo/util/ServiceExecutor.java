package com.cd.cdwoo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 利用CompletionService和线程池同时执行多个Service
 *
 */
public class ServiceExecutor {
  // 共享的线程池
  static Executor commExecutor = new ThreadPoolExecutor(
      0,
      Integer.MAX_VALUE,
      600, TimeUnit.SECONDS, // terminate idle threads after 5 sec
      new SynchronousQueue<Runnable>()  // directly hand off tasks
  );

  CompletionService<ServiceResult> completionService;
  Set<Future<ServiceResult>> pending;
  Map<String, ServiceResult> result;

  public ServiceExecutor() {
    completionService = new ExecutorCompletionService<ServiceResult>(commExecutor);
    pending = new HashSet<Future<ServiceResult>>();
    result = new HashMap<>();
  }

  /**
   * 提交一个计划执行的ServiceTask
   */
  public void submit(ServiceTask task) {
    pending.add(completionService.submit(task));
  }

  /**
   * 等待并收集每个ServiceTask的执行结果
   */
  public Map<String, ServiceResult> takeCompletedWithError() throws IOException {
    while (pending.size() > 0) {
      try {
        Future<ServiceResult> future = completionService.take();
        pending.remove(future);
        ServiceResult sr = future.get();
        result.put(sr.getTaskId(), sr);
      } catch (InterruptedException ie) {
        ie.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      } 
    }
    return result;
  }

  /**
   * 封装任务执行的结果
   */
  public static class ServiceResult {
    String taskId;
    Object obj;

    ServiceResult(String taskId, Object obj) {
      this.taskId = taskId;
      this.obj = obj;
    }

    public String getTaskId() {
      return taskId;
    }

    public Object getResult() {
      return obj;
    }

    public String toString() {
      return taskId + "-" + obj;
    }
  }

  /**
   * 封装计划执行的Service。具体的实现类需要实现doCall()方法，返回service方法的结果。
   */
  public abstract static class ServiceTask implements Callable<ServiceResult> {
    String taskId;
    public ServiceTask(String taskId) {
      this.taskId = taskId;
    }

    @Override
    public ServiceResult call() throws Exception {
      CDWooLogger.info("executing " + this.taskId + " ...");
      long timeStart = System.currentTimeMillis();
      Object obj = doCall();
      long timeEnd = System.currentTimeMillis();
      CDWooLogger.info(this.taskId + " executed, timeUsed=" + (timeEnd - timeStart));
      return new ServiceResult(this.taskId, obj);
    }

    public abstract Object doCall() throws Exception;
  }

  /**
   * 示例
   * @throws IOException 
   */
  public static void main(String[] args) throws Exception {
    ServiceExecutor se = new ServiceExecutor();

    ServiceTask task1 = new ServiceTask("task-1") {
      @Override
      public Object doCall() throws Exception {
        System.err.println("im in task-1...");
        return new Integer(1);
      }
    };

    ServiceTask task2 = new ServiceTask("task-2") {
      @Override
      public Object doCall() throws Exception {
        System.err.println("im in task-2...");
        return "hello";
      }
    };

    se.submit(task1);
    se.submit(task2);

    Map<String, ServiceResult> result = se.takeCompletedWithError();
    for (String r : result.keySet()) {
      System.err.println("result:" + r + "->" + result.get(r));
    }
  }
}
