package com.cd.cdwoo.plugs.mybatis;
import java.sql.Connection;
import java.util.Properties;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
/**
 * Mybatis插件类
 * 
 * @author chendong
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class CustomPlugin implements Interceptor {
  /**
   * 是否显示sql
   */
  private String isShowSql = "";
  /**
   * getter
   * 
   * @return isShowSql
   */
  public String getIsShowSql() {
    return isShowSql;
  }
  /**
   * setter
   * 
   * @param isShowSql
   *        是否展示sql参数
   */
  public void setIsShowSql(String isShowSql) {
    this.isShowSql = isShowSql;
  }
  /**
   * mybatis插件类中操作(1.显示sql)
   * 
   * @param ivk
   *        Invocation类型参数
   * @throws Throwable
   *         抛出获取数据异常
   * @return ivk.proceed()
   */
  public Object intercept(Invocation ivk) throws Throwable {
    RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
    BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(
        statementHandler, "delegate");
    if (ivk.getTarget() instanceof RoutingStatementHandler) {
      if ("TRUE".equals(isShowSql.toUpperCase())) {
        BoundSql boundSql = delegate.getBoundSql();
        String sql = boundSql.getSql();
        System.out.println("sql:" + sql);
      }
    }
    return ivk.proceed();
  }
  public Object plugin(Object arg0) {
    return Plugin.wrap(arg0, this);
  }
  public void setProperties(Properties properties) {
  }
}
