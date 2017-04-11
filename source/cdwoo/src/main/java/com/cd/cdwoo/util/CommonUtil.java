/**
 * File：CommonUtil.java
 * Package：com.fang.cms.utils
 * Author：zhaolingfei
 * Date：2016年4月6日 下午2:43:38
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cd.cdwoo.common.Constants;
import com.cd.cdwoo.core.entity.QuartzJobFactory;
import com.cd.cdwoo.core.entity.ScheduleJob;

/**
 * 通用工具类.
 * 
 * @author zhaolingfei
 */
public class CommonUtil {
  /**
   * Field : 日志 .
   * Add By feix [ zhaolingfei@fang.com ]
   * 2017年4月7日 下午5:18:50
   */
  private static final Logger LOG = (Logger) LoggerFactory.getLogger(QuartzJobFactory.class);
  /**
   * Field : 常用字符编码GBK .
   * Add By feix [ zhaolingfei@fang.com ]
   * 2017年3月9日 下午2:24:27
   */
  public static final String GBK = "GBK";
  /**
   * Field : 常用字符编码GB2312 .
   * Add By feix [ zhaolingfei@fang.com ]
   * 2017年3月9日 下午2:24:27
   */
  public static final String GB2312 = "GB2312";
  /**
   * Field : 常用字符编码UTF-8 .
   * Add By feix [ zhaolingfei@fang.com ]
   * 2017年3月9日 下午2:24:27
   */
  public static final String UTF8 = "UTF-8";
  /**
   * Field : 常用字符编码UTF-16 .
   * Add By feix [ zhaolingfei@fang.com ]
   * 2017年3月9日 下午2:24:27
   */
  public static final String UTF16 = "UTF-16";
  /**
   * Field : 常用字符编码ISO-8859-1 .
   * Add By feix [ zhaolingfei@fang.com ]
   * 2017年3月9日 下午2:24:27
   */
  public static final String ISO88591 = "ISO-8859-1";
  /**
   * Field : 常用相应头格式 json.
   * Add By feix [ zhaolingfei@fang.com ]
   * 2017年3月9日 下午2:24:27
   */
  public static final String APP_JSON = "application/json";
  /**
   * 判断对象或对象数组中每一个对象是否为空.<br>
   * 例如: 对象为null，字符序列长度为0，集合类、Map为empty.
   * 
   * @Author zhaolingfei
   * @Date 2016年4月6日
   * @param obj
   *        需要判断是否为空的对象
   * @return boolean
   */
  public static boolean isNullOrEmpty(Object obj) {
    if (obj == null) {
      return true;
    }
    if (obj instanceof CharSequence) {
      return ((CharSequence) obj).length() == 0;
    }
    if (obj instanceof Collection) {
      return ((Collection<?>) obj).isEmpty();
    }
    if (obj instanceof Map) {
      return ((Map<?, ?>) obj).isEmpty();
    }
    if (obj instanceof Object[]) {
      Object[] object = (Object[]) obj;
      if (object.length == 0) {
        return true;
      }
      boolean empty = true;
      for (int i = 0; i < object.length; i++) {
        if (!isNullOrEmpty(object[i])) {
          empty = false;
          break;
        }
      }
      return empty;
    }
    return false;
  }
  /**
   * 判断字符串是否是数字.
   * 
   * @Author zhaolingfei
   * @Date 2016年4月12日
   * @param str
   *        需要判断的字符串
   * @return boolean
   */
  public static boolean isNumber(String str) {
    return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
  }
  /**
   * 将list转换为以逗号分隔的字符串.
   * 
   * @Author zhaolingfei
   * @Date 2016年4月15日
   * @param <T>
   *        可以转换的类型
   * @param li
   *        需要转换的list 一般转换String Integer等
   * @return String
   */
  public static <T> String list2String(List<T> li) {
    StringBuilder str = new StringBuilder("");
    for (T t : li) {
      if (StringUtils.isBlank(str.toString())) {
        str.append(t);
      } else {
        str.append("," + t);
      }
    }
    return str.toString();
  }

  /**
   * 验证字符串为邮箱格式.
   * 
   * @Author：zhaolingfei
   * @Date：2016年4月21日 下午5:12:21
   * @param str
   *        待验证邮箱
   * @return boolean
   */
  public static boolean isEmail(String str) {
    boolean b = false;
    Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    Matcher ma = p.matcher(str);
    b = ma.find();
    return b;
  }
  /**
   * 获取访问者ip地址.
   * 
   * @Author：zhaolingfei
   * @Date：2016年4月21日 下午5:15:54
   * @param request
   *        请求实体
   * @return String
   */
  public static String getRemoteHost(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
  }
  /**
   * request辅助工具 设置编码(UTF8)..
   * 
   * @Author：zhaolingfei
   * @Date：2016年4月25日 下午2:31:27
   * @param request
   *        请求实体.
   * @return HttpServletRequest HttpServletRequest
   * @throws IOException
   *         HttpServletRequest
   */
  public static HttpServletRequest requestHelper(HttpServletRequest request) throws IOException {
    /* 设置字符集为'UTF-8' */
    request.setCharacterEncoding("UTF-8");
    return request;
  }
  /**
   * request辅助工具 设置编码(UTF8) 及返回格式(application/json).
   * respone.setContentType("application/json")
   * 
   * @Author：zhaolingfei
   * @Date：2016年4月21日 下午5:24:22
   * @param response
   *        响应实体
   * @return HttpServletResponse
   * @throws IOException
   *         HttpServletResponse
   */
  public static HttpServletResponse responseHelper(HttpServletResponse response) throws IOException {
    /* 设置格式为text/json */
    // response.setContentType("application/json");
    response.setContentType("text/html");
    /* 设置字符集为'UTF-8' */
    response.setCharacterEncoding("UTF-8");
    return response;
  }
  /**
   * request辅助工具 设置编码.
   * 
   * @Author：zhaolingfei
   * @Date：2016年4月21日 下午5:23:33
   * @param request
   *        请求实体
   * @param character
   *        字符集编码
   * @return HttpServletRequest
   * @throws IOException
   *         HttpServletRequest
   */
  public static HttpServletRequest requestHelperWithCharacter(HttpServletRequest request, String character) throws IOException {
    /* 设置字符集为'UTF-8' */
    request.setCharacterEncoding(character);
    return request;
  }
  /**
   * request辅助工具 设置编码 及返回格式.
   * 
   * @Author：zhaolingfei
   * @Date：2016年4月21日 下午5:22:44
   * @param response
   *        响应实体
   * @param character
   *        字符集编码
   * @param cType
   *        内容返回格式
   * @return HttpServletResponse
   * @throws IOException
   *         HttpServletResponse
   */
  public static HttpServletResponse responseHelperWithCharacterAndContentType(HttpServletResponse response, String character, String cType) throws IOException {
    response.setContentType(cType);
    /* 设置字符集为'UTF-8' */
    response.setCharacterEncoding(character);
    return response;
  }
  /**
   * Desc : 手工分页 .
   * @author feix [ zhaolingfei@fang.com ]
   * @date 2017年3月27日 下午4:10:28
   * @param rows 需要分页的数据
   * @param pagesize 页长
   * @param page 当前页
   * @return 分页后的数据
   */
  public static List<Map<String, Object>> getPage(List<Map<String, Object>> rows, Integer pagesize, Integer page) {
    if (CommonUtil.isNullOrEmpty(rows)) {
      return rows;
    }
    // 总量
    int total = rows.size();
    // 起始位置
    int start = (page - 1) * pagesize;
    // 结束位置
    int end = 0;
    if ((start + pagesize) <= total) {
      // 如果起始位置+分页长度小于总和，结束为止就是开始加页长
      end = start + pagesize;
    } else if (start < total && (start + pagesize) > total) {
      end = total;
    } else if (start >= total) {
      start = total - pagesize >= 0 ? (total - pagesize) : 0;
      end = total;
    }
    return rows.subList(start, end);
  }
  /**
   * Desc : 获取配置文件的所有定时任务 只返回变动任务.
   * @author feix [ zhaolingfei@fang.com ]
   * @date 2017年4月7日 下午5:03:31
   * @return 定时任务集合
   */
  public static List<ScheduleJob> getQuzrtzs() {
    // 新建配置集合
    List<ScheduleJob> sJobList = new ArrayList<ScheduleJob>();
    /* 获取所有定时任务配置 */
    Map<String, String> pro = PropertiesUtil.getAll("quartz");
    /* 存储变动定时任务 */
    Map<String, String> temp = new HashMap<>();
    // map 的 value
    String value = "";
    if (Constants.OLD_QUARTZ_JOBS.size() != 0) {
      /*
       * 如果历史不等于 0 则说明之前有配置记录
       * 循环配置文件中的。
       * 1. pro 有 Constants.OLD_QUARTZ_JOBS 没有 新增！ 必须传递
       * 2. pro 有 Constants.OLD_QUARTZ_JOBS 有 一样否？ 是 省略，没有变动 否则 修改！ 必须传递
       * 3. pro 没有 Constants.OLD_QUARTZ_JOBS 有 删除 必须传递【将旧状态改为 1 不可用。然后删除任务】
       */
      // 决定使用什么循环
      boolean flag = Constants.OLD_QUARTZ_JOBS.size() > pro.size() ? true : false;
      if (flag) {
        for (String str : Constants.OLD_QUARTZ_JOBS.keySet()) {
          value = Constants.OLD_QUARTZ_JOBS.get(str);
          // 旧map大。
          if (StringUtils.isBlank(pro.get(str))) {
            // 如果新配置文件里没有 情况 3
            // 修改为不可用
            temp.put(str, value.substring(0, value.length() - 1) + "1");
          } else {
            // 新配置文件有 情况 2
            // 比较 不一样 修改。放入传递
            if (!value.equals(pro.get(str))) {
              temp.put(str, pro.get(str));
            }
          }
        }
      } else {
        for (String str : pro.keySet()) {
          value = pro.get(str);
          // 新map大。
          if (StringUtils.isBlank(Constants.OLD_QUARTZ_JOBS.get(str))) {
            // 旧文件里没有 情况 1
            // 新增
            temp.put(str, value);
          } else {
            // 旧文件有 情况 2
            // 比较 不一样 修改。放入传递
            if (!value.equals(Constants.OLD_QUARTZ_JOBS.get(str))) {
              temp.put(str, pro.get(str));
            }
          }
        }
      }
    } else {
      if (isNullOrEmpty(pro)) {
        // 如果没有配置信息，则返回空集合
        return sJobList;
      }
      // 否则全部是新建的
      temp.putAll(pro);
    }
    // 新建定时任务实体
    ScheduleJob sJob = new ScheduleJob();
    // 配置列表
    String param = "";
    // 各项配置意义
    String[] params = {};
    // 循环获取所有定时任务配置
    for (String jobId : temp.keySet()) {
      param = temp.get(jobId);
      if (StringUtils.isBlank(param) || (param.split(";").length != 7)) {
        LOG.error("[ jobInit ] err : quartz.properties has error for read or some task [ jobId : " + jobId + " ] has not configured correctly ");
        continue;
      }
      params = param.split(";");
      sJob = new ScheduleJob();
      // 作业id
      sJob.setJobId(jobId);
      // 作业名称
      sJob.setJobName(params[0]);
      // 作业分组
      sJob.setJobGroup(params[1]);
      // 作业目标路径
      sJob.setJobClass(params[2]);
      // 作业所需参数
      sJob.setJobParams(getParams4Quartz(params[3]));
      // 作业调度规则
      sJob.setCronExpression(params[4]);
      // 作业说明
      sJob.setDesc(params[5]);
      // 作业状态
      sJob.setJobStatus(params[6]);
      sJobList.add(sJob);
    }
    // 清空前一次记录并放置本次配置文件信息
    Constants.OLD_QUARTZ_JOBS.clear();
    Constants.OLD_QUARTZ_JOBS.putAll(pro);
    return sJobList;
  }
  /**
   * Desc : 获取参数字段拼合参数列表 .<br>
   * 例如 <code>param1:aaaa&amp;param2:bbbb</code>
   * @author feix [ zhaolingfei@fang.com ]
   * @date 2017年4月7日 下午5:35:35
   * @param param 需要合并的参数
   * @return 参数集合
   */
  public static Map<String, Object> getParams4Quartz(String param) {
    // 接受结果集
    Map<String, Object> result = new HashMap<String, Object>();
    if (StringUtils.isBlank(param) && "null".equals(param)) {
      // 没有参数或者参数不完整
      return result;
    }
    // 切割所有参数
    String[] params = param.split("&");
    for (String str : params) {
      if (str.indexOf(":") < 0) {
        // 不包含冒号
        continue;
      }
      // 放入解析参数
      result.put(str.split(":")[0], str.split(":")[1]);
    }
    return result;
  }
}
