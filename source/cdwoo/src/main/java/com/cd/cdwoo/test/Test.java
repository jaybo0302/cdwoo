/**
 * File：Test.java
 * Package：com.cd.cdwoo.test
 * Author：chendong
 * Date：2016年12月14日 上午9:47:57
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author chendong
 */
public class Test {
  protected static final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 100, 2, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(1),
      new ThreadFactory() {
          private AtomicInteger id = new AtomicInteger(0);
          @Override
          public Thread newThread(Runnable r) {
              Thread thread = new Thread(r);
              thread.setName("home-service-" + id.addAndGet(1));
              return thread;
          }
      });
  protected static final ThreadPoolExecutor executor4 = new ThreadPoolExecutor(2, 100, 2, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(),
      new ThreadFactory() {
          private AtomicInteger id = new AtomicInteger(0);
          @Override
          public Thread newThread(Runnable r) {
              Thread thread = new Thread(r);
              thread.setName("home-service-" + id.addAndGet(1));
              return thread;
          }
      }, new ThreadPoolExecutor.CallerRunsPolicy());
  protected static final ThreadPoolExecutor executor2 = new ThreadPoolExecutor(2, 100, 2, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(),
      new ThreadFactory() {
          private AtomicInteger id = new AtomicInteger(0);
          @Override
          public Thread newThread(Runnable r) {
              Thread thread = new Thread(r);
              thread.setName("home-service-" + id.addAndGet(1));
              return thread;
          }
      }, new ThreadPoolExecutor.CallerRunsPolicy());
  protected static final ThreadPoolExecutor executor3 = new ThreadPoolExecutor(2, 100, 2, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(),
      new ThreadFactory() {
          private AtomicInteger id = new AtomicInteger(0);
          @Override
          public Thread newThread(Runnable r) {
              Thread thread = new Thread(r);
              thread.setName("home-service-" + id.addAndGet(1));
              return thread;
          }
      }, new ThreadPoolExecutor.CallerRunsPolicy());
public static void main(String[] args) throws InterruptedException, ExecutionException {
  Future<Long> f1 = executor.submit(new Callable<Long>() {
      @Override
      public Long call() throws Exception {
          //Thread.sleep(1000); //延时以使得第二层的f3在第一层的f2占用corePoolSize后才submit
          Future<Long> f3 = executor.submit(new Callable<Long>() {
              @Override
              public Long call() throws Exception {
                  return -1L;
              }
          });
          System.out.println("f1.f3" + f3.get());
          return -1L;
      }
  });
  //Thread.sleep(10);
  Future<Long> f2 = executor.submit(new Callable<Long>() {
      @Override
      public Long call() throws Exception {
          //Thread.sleep(1000);//延时
          Future<Long> f4 = executor.submit(new Callable<Long>() {
              @Override
              public Long call() throws Exception {
                  return -1L;
              }
          });
          System.out.println("f2.f4" + f4.get());
          return -1L;
      }
  });
  System.out.println("here");
  System.out.println("f1" + f1.get());
  System.out.println("f2" + f2.get());
}
}
