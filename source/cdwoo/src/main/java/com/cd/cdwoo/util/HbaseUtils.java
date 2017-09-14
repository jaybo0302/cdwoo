package com.cd.cdwoo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author wangxiao
 * 
 */
public class HbaseUtils {
	private static Configuration conf = null;
	private static volatile Connection conn = null;

	static{
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "dzc-w-hadoopnew04.light.soufun.com,dzc-w-hadoopnew03.light.soufun.com,dzc-w-hadoopnew01.light.soufun.com,dzc-w-hadoopnew05.light.soufun.com,dzc-w-hadoopnew06.light.soufun.com,dzc-w-hadoopnew02.light.soufun.com,dzc-w-hadoopnew07.light.soufun.com");
        conn = createHbaseConnection();
        ConnectionHelper.checkValid();
	}
	
	/**
	 * 获取�?个新的connection连接
	 * 注： 此方法�?�时较长�? 如果只需htable对象能够解决的，调用createTable方法即可
	 * @return
	 */
    public static Connection createHbaseConnection() {
        Connection connection = null;
        try {
            long time = System.currentTimeMillis();
            connection = ConnectionFactory.createConnection(conf);
            System.out.println("time,HbaseUtils,createHbaseConnection:" + (System.currentTimeMillis() - time));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }
	
	private static Connection getHbaseConnection() {
    if(null == conn || conn.isClosed()){
        conn = createHbaseConnection();
    }
		return conn;
	}
	
	/**
   * 创建Table连接
   * @param tableName
   * @return
   */
  public static Table getTable(String tableName) {
    return getTable(TableName.valueOf(tableName));
  }
  
  /**
   * 创建Table连接
   * @param tableName
   * @return
   */
  public static Table getTable(TableName tableName) {
    Table table = null;
    try {
      table = getHbaseConnection().getTable(tableName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return table;
  }
  
  
  /**
   * 关闭�?
   * @throws IOException 
   */
  public static void closeTable(HTable htable) throws IOException {
    if(htable != null) {
      htable.close();
    }
  }
  /**
   * 关闭连接
   * @throws IOException  
   *//*
  public void closeConn() throws IOException  {
    if(conn != null) {
      conn.close();
    }
  }*/
  
  public static class ConnectionHelper {
    private static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(1);
    private static final Long PERIOD = 5000L;// 5秒检测一次connection
    public static void checkValid() {
      EXECUTOR.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
          if (conn == null || conn.isClosed()) {
            conn = createHbaseConnection();
          }
        }
      }, PERIOD, PERIOD, TimeUnit.MILLISECONDS);
    }
  }
	
	/**
	 * 创建表String
	 * @throws IOException 
	 */
//	public HTable createTable(String tableName) throws IOException {
//		HTable hTable = new HTable(conf, Bytes.toBytes(tableName));
//		return hTable;
//	}

	/**
	 * 扫描位置信息�?
	 */

//	public Map<String, String> scanLocation(HTable hTable, Map<String, Set<String>> userIds) throws IOException {
//		System.out.println("--------------------位置信息起始�?--------------------");
//		Map<String, String> location = new HashMap<String, String>();
//		Map<String, Integer> workLongitude = new HashMap<String, Integer>();
//		Map<String, Integer> workLatitude = new HashMap<String, Integer>();
//		Map<String, Integer> homeLongitude = new HashMap<String, Integer>();
//		Map<String, Integer> homeLatitude = new HashMap<String, Integer>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		Calendar now = Calendar.getInstance();
////		now.add(Calendar.DATE, -1);
//		String end = sdf.format(now.getTime());
//		now.add(Calendar.MONTH, -12);
//		String start = sdf.format(now.getTime());
//		
//		
//		for(Entry<String, Set<String>> entry : userIds.entrySet()) {
//			String idName = entry.getKey();
//			Set<String> idValues = entry.getValue();
//			for(String idVal : idValues) {
//				String val = idVal;
//				Scan scan = new Scan();
//				scan.setCaching(100);
//				scan.setStartRow(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + start));
//				Filter stopRowFilter = new InclusiveStopFilter(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + end));
//				scan.setFilter(stopRowFilter);
//				ResultScanner rs = hTable.getScanner(scan);
//				
//				for(Result r : rs) {
//					for(Cell cell : r.rawCells()){
////						System.out.println(new String(CellUtil.cloneRow(cell))+"\t"
////			                    +new String(CellUtil.cloneFamily(cell))+"\t"
////			                    +new String(CellUtil.cloneQualifier(cell))+"\t"
////			                    +new String(CellUtil.cloneValue(cell),"UTF-8")+"\t"
////			                    +cell.getTimestamp());
//						String type = new String(CellUtil.cloneQualifier(cell));
//						String[] value = new String(CellUtil.cloneValue(cell),"UTF-8").split("#STR#")[0].split("\\+");
//						String longitude = value[0];
//						String latitude = value[1];
//						if(type.equals("work")) {
//							if(workLongitude.containsKey(longitude)) {
//								workLongitude.put(longitude, workLongitude.get(longitude) + 1);
//							} else {
//								workLongitude.put(longitude, 1);
//							}
//							if(workLatitude.containsKey(latitude)) {
//								workLatitude.put(latitude, workLatitude.get(latitude) + 1);
//							} else {
//								workLatitude.put(latitude, 1);
//							}
//						} else {
//							if(homeLongitude.containsKey(longitude)) {
//								homeLongitude.put(longitude, homeLongitude.get(longitude) + 1);
//							} else {
//								homeLongitude.put(longitude, 1);
//							}
//							if(homeLatitude.containsKey(latitude)) {
//								homeLatitude.put(latitude, homeLatitude.get(latitude) + 1);
//							} else {
//								homeLatitude.put(latitude, 1);
//							}
//						}
//			        }
//				}
//				rs.close();
//			}
//		}
//		
//		location.put("work", maxCount(workLongitude) + "+" + maxCount(workLatitude));
//		location.put("home", maxCount(homeLongitude) + "+" + maxCount(homeLatitude));
//		System.out.println("--------------------位置信息终结�?--------------------");
//		return location;		
//	}
	
	/**
	 * 获取初次和末次访问时�?
	 * @throws IOException,zoom 
	 */
//	public Map<String, String> scanVisitTime(HTable hTable, Map<String, Set<String>> userIds) throws IOException {
//		Map<String, String> firstAndLast = new HashMap<String, String>();
//		String firstVisit = "2050-12-31 23:59:59";
//		String lastVisit = "1990-01-01 00:00:00";
//		boolean flag = false;
//
//		List<Get> queryRowList = new ArrayList<Get>();
//		for(Entry<String, Set<String>> entry : userIds.entrySet()) {
//			String idName = entry.getKey();
//			Set<String> idValues = entry.getValue();
//			for(String idVal : idValues) {
//				String val = idVal;
//				queryRowList.add(new Get(Bytes.toBytes(val+"#STR#" + idName)));
//			}
//		}
//		Result[] results = hTable.get(queryRowList);
//		for(Result r : results) {
//			flag = true;
//			for(Cell cell : r.rawCells()){
//	            String visitTime = new String(CellUtil.cloneValue(cell),"UTF-8");
//	            if(visitTime.compareTo(firstVisit) < 0) {
//	            	firstVisit = visitTime;
//	            } else if(visitTime.compareTo(lastVisit) > 0) {
//	            	lastVisit = visitTime;
//	            }
//	        }
//		}
//		firstVisit = firstVisit.substring(0,10);
//		lastVisit = lastVisit.substring(0,10);
//		if(!flag) {
//			firstVisit = "";
//			lastVisit = "";
//		}
//		firstAndLast.put("firstVisit", firstVisit);
//		firstAndLast.put("lastVisit", lastVisit);
//
//		return firstAndLast;
//	}
	

	
//	public List<Map<String, String>> scanHourTrend(HTable hTable, Map<String, Set<String>> userIds) throws IOException {
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		Calendar now = Calendar.getInstance();
//		// 设置结束时间为当前时�?
//		String end = sdf.format(now.getTime());
//		now.add(Calendar.MONTH, -6);
//		String start = sdf.format(now.getTime());
////		System.out.println(current + "--------------灵巧的小时分割线-----------------");
//		List<Map<String, String>> visitTrend = new ArrayList<Map<String, String>>();
//
//		Map<String, Integer> hourMap = new HashMap<String, Integer>();
//		for(Integer x=0; x<24; x++) {
//			String hour = x.toString().length()<2 ? "0" + x: x.toString();
//			hourMap.put(hour, 0);
//		}
//		for(Entry<String, Set<String>> entry : userIds.entrySet()) {
//			String idName = entry.getKey();
//			Set<String> idValues = entry.getValue();
//			for(String idVal : idValues) {
//				Scan scan = new Scan();
//				scan.setCaching(100);
//				String val = idVal;
//				scan.setStartRow(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + start));
//				FilterList filterList = new FilterList();
//				Filter stopRowFilter = new InclusiveStopFilter(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + end));
//				/*ColumnPrefixFilter hourColFilter = new ColumnPrefixFilter(Bytes.toBytes("hour#STR#")); */
//				byte[] startColumn = Bytes.toBytes("hour#STR#00");
//				byte[] endColumn = Bytes.toBytes("hour#STR#23");
//				Filter hourColFilter = new ColumnRangeFilter(startColumn, true, endColumn, true);
//				filterList.addFilter(stopRowFilter);
//				filterList.addFilter(hourColFilter);
//				scan.setFilter(filterList);
//				ResultScanner rs = hTable.getScanner(scan);
//				for(Result r : rs) {
//					for(Cell cell : r.rawCells()){
////						System.out.println(new String(CellUtil.cloneRow(cell))+"\t"
////			                    +new String(CellUtil.cloneFamily(cell))+"\t"
////			                    +new String(CellUtil.cloneQualifier(cell))+"\t"
////			                    +new String(CellUtil.cloneValue(cell),"UTF-8")+"\t"
////			                    +cell.getTimestamp());
//			            Integer pv = new Integer(new String(CellUtil.cloneValue(cell),"UTF-8"));
//			            String hour = new String(CellUtil.cloneQualifier(cell));
//			            hour = hour.split("#STR#")[1];
//			            if(hour.length()==2) {
//			            	pv = pv + hourMap.get(hour);
//				            hourMap.put(hour, pv);
//			            }			            
//			        }
//				}
//				rs.close();
//			}
//		}
////		System.out.println("hourMap:" + hourMap);
//		for(Integer x=0; x<24; x++) {
//			Map<String, String> item = new HashMap<String, String>();
//			String hour = x.toString().length()<2 ? "0" + x: x.toString();
////			System.out.println("hour to put in item:" + hour);
//			item.put("X", hour);
//			item.put("Y", hourMap.get(hour).toString());
//			visitTrend.add(item);
//		}
//		
////		System.out.println(visitTrend);
//
//		System.out.println("--------------稳重的小时终结线-----------------");
//		return visitTrend;
//	}
	
//	public List<Map<String, String>> scanDayTrend(HTable hTable, Map<String, Set<String>> userIds, String start, String end, List<String> dayList) throws IOException {
//		List<Map<String, String>> visitTrend = new ArrayList<Map<String, String>>();
//		System.out.println("--------------黑色的天天分割线-----------------");
//		for(String i : dayList) {
//			Scan scan = new Scan();
//			scan.setCaching(100);
//			Integer pvCount = 0;
//			for(Entry<String, Set<String>> entry : userIds.entrySet()) {
//				String idName = entry.getKey();
//				Set<String> idValues = entry.getValue();
//				for(String idVal : idValues) {
//					String val = idVal;
//					FilterList filterList=new FilterList();
//					QualifierFilter colFilter = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("day#STR#" + i)));
//					scan.setStartRow(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + start));
//					Filter stopRowFilter = new InclusiveStopFilter(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + end));
//					filterList.addFilter(colFilter);
//					filterList.addFilter(stopRowFilter);
//					scan.setFilter(filterList);
//					ResultScanner rs = hTable.getScanner(scan);
//					
//					for(Result r : rs) {
//						for(Cell cell : r.rawCells()){
////							System.out.println(new String(CellUtil.cloneRow(cell))+"\t"
////				                    +new String(CellUtil.cloneFamily(cell))+"\t"
////				                    +new String(CellUtil.cloneQualifier(cell))+"\t"
////				                    +new String(CellUtil.cloneValue(cell),"UTF-8")+"\t"
////				                    +cell.getTimestamp());
//				            Integer pv = new Integer(new String(CellUtil.cloneValue(cell),"UTF-8"));
//				            pvCount = pvCount + pv;
//				        }
//					}
//					rs.close();
//				}
//			}
//			Map<String, String> item = new HashMap<String, String>();
//			item.put("X", i);
//			item.put("Y", pvCount.toString());
//			visitTrend.add(item);
//		}
//		System.out.println("--------------黑色的天天终结线-----------------");
//		return visitTrend;
//		
//	}
	
//	public List<Map<String, String>> scanMonthTrend(HTable hTable, Map<String, Set<String>> userIds, String start, String end, List<String> monthList) throws IOException {
//		List<Map<String, String>> visitTrend = new ArrayList<Map<String, String>>();
//		System.out.println("--------------郑重的的月度分割�?-----------------");
////		System.out.println("userIds:" + userIds);
//		for(String i : monthList) {
//			Scan scan = new Scan();
//			scan.setCaching(100);
//			String mStr = i;
//			/*mStr = mStr.charAt(0) == '0'? mStr.substring(1) : mStr;*/
//			Integer uvCount = 0;
//			Map<String, List<String>> monthWrap = new HashMap<String, List<String>>();
//			for(Entry<String, Set<String>> entry : userIds.entrySet()) {
//				String idName = entry.getKey();
//				Set<String> idValues = entry.getValue();
//				for(String idVal : idValues) {
//					String val = idVal;
//					FilterList filterList=new FilterList();
//					QualifierFilter monthFilter = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("month#STR#" + mStr)));
//					scan.setStartRow(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + start));
//					Filter stopRowFilter = new InclusiveStopFilter(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + end));
//					filterList.addFilter(monthFilter);
//					filterList.addFilter(stopRowFilter);
//					scan.setFilter(filterList);
//					ResultScanner rs = hTable.getScanner(scan);
//					
//					for(Result r : rs) {
//						for(Cell cell : r.rawCells()){
////							System.out.println(new String(CellUtil.cloneRow(cell))+"\t"
////				                    +new String(CellUtil.cloneFamily(cell))+"\t"
////				                    +new String(CellUtil.cloneQualifier(cell))+"\t"
////				                    +new String(CellUtil.cloneValue(cell),"UTF-8")+"\t"
////				                    +cell.getTimestamp());
//				            Integer uv = new Integer(new String(CellUtil.cloneValue(cell),"UTF-8"));
//				            String day = new String(CellUtil.cloneRow(cell));
//				            day = day.split("#STR#")[2];
//				            if(monthWrap.containsKey(i)) {
//				            	if(!monthWrap.get(i).contains(day)) {
//				            	  monthWrap.get(i).add(day);
//				            	}
//				            } else {
//				            	List<String> listCell = new ArrayList<String>();
//				            	listCell.add(day);
//				            	monthWrap.put(i, listCell);
//				            }
//				           /* uvCount = uvCount + uv;*/
///*				            System.out.println("pv:" + pv + "\n" +
//	            					"row start filter:" + val + "#STR#" + idName + "#STR#" + start + "\n" +
//	            					"row stop filter:" + val + "#STR#" + idName + "#STR#" + end + "\n" +
//	            					"column filter: " + "month#STR#" + mStr
//	            		          );*/
//				        }
//					}
//					rs.close();
//				}
//			}
//			Map<String, String> item = new HashMap<String, String>();
//			item.put("X", i);
//			if(monthWrap.get(i) == null) {
//				item.put("Y", "0");
//			} else {
//				Integer uvSum = monthWrap.get(i).size();
//				item.put("Y", uvSum.toString());
//			}			
//			visitTrend.add(item);
//			
//		}
////		System.out.println("--------------愤�?�的月度终结�?-----------------");
//		return visitTrend;
//		
//	}
	
//	public List<Map<String, String>> scanYearTrend(HTable hTable, Map<String, Set<String>> userIds) throws IOException {
//		System.out.println("------------------------------年趋势起始线-----------------------------");
//		List<Map<String, String>> visitTrend = new ArrayList<Map<String, String>>();
//		Map<String, List<String>> yearWrap = new HashMap<String, List<String>>();
//		String start = "19980101";
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		Calendar now = Calendar.getInstance();
//		String end = sdf.format(now.getTime());
//		
//		for(Entry<String, Set<String>> entry : userIds.entrySet()) {
//			String idName = entry.getKey();
//			Set<String> idValues = entry.getValue();
//			for(String idVal : idValues) {
//				String val = idVal;
//				Scan scan = new Scan();
//				scan.setCaching(100);
//				FilterList filterList=new FilterList();
//				ColumnPrefixFilter yearFilter = new ColumnPrefixFilter(Bytes.toBytes("year#STR#"));
//				scan.setStartRow(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + start));
//				Filter stopRowFilter = new InclusiveStopFilter(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + end));
//				filterList.addFilter(yearFilter);
//				filterList.addFilter(stopRowFilter);
//				scan.setFilter(filterList);
//				ResultScanner rs = hTable.getScanner(scan);
//				
//				for(Result r : rs) {
//					for(Cell cell : r.rawCells()){
////						System.out.println(new String(CellUtil.cloneRow(cell))+"\t"
////			                    +new String(CellUtil.cloneFamily(cell))+"\t"
////			                    +new String(CellUtil.cloneQualifier(cell))+"\t"
////			                    +new String(CellUtil.cloneValue(cell),"UTF-8")+"\t"
////			                    +cell.getTimestamp());
//						String year = new String(CellUtil.cloneQualifier(cell));
//						year = year.split("#STR#")[1];
//						String day = new String(CellUtil.cloneRow(cell));
//						day = day.split("#STR#")[2];
//			           /* Integer pv = new Integer(new String(CellUtil.cloneValue(cell),"UTF-8"));*/
//			            if(yearWrap.containsKey(year)) {
//			            	if(!yearWrap.get(year).contains(day)) {
//			            		yearWrap.get(year).add(day);
//			            	}
//			            } else {
//			            	List<String> listCell = new ArrayList<String>();
//			            	listCell.add(day);
//			            	yearWrap.put(year,listCell);
//			            }
//			        }
//				}
//				rs.close();
//			}
//		}
//		for(Entry<String, List<String>> enYear : yearWrap.entrySet()) {
//			Map<String, String> item = new HashMap<String, String>();
//			item.put("X", enYear.getKey());
//			Integer pvSum = enYear.getValue().size();
//			item.put("Y", pvSum.toString());
//			visitTrend.add(item);
//		}
//		System.out.println("------------------------------年趋势终结线-----------------------------");
//		return visitTrend;		
//	}
	
//	public List<String> scanRole(HTable hTable, Map<String, Set<String>> userIds) throws IOException {
//		System.out.println("-------------------------角色起始�?------------------------");
//		System.out.println("userIds:" + userIds);
//		List<String> role = new ArrayList<String>();
//		Map<String,String> roleMapping = new HashMap<String,String>();
//		String filePath = this.getClass().getResource("").getPath();
//		String[] splitPath = filePath.split("WEB-INF");
//		filePath = splitPath[0];
//		roleMapping.put("newhouse#STR#customer", "新房潜客,6");
//		roleMapping.put("esf#STR#customer","二手房潜�?,3");
//		roleMapping.put("esf#STR#owner","二手房业�?,3");
//		roleMapping.put("world#STR#customer","国际潜客,6");
//		roleMapping.put("zf#STR#customer","租房潜客,1");
//		roleMapping.put("zf#STR#owner","租房业主,1");
//		roleMapping.put("home#STR#customer","家居潜客,6");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		Calendar now = Calendar.getInstance();
////		now.add(Calendar.DATE, -1);
//		String end = sdf.format(now.getTime());
//		now.add(Calendar.MONTH, -6);
//		String start = sdf.format(now.getTime());
//		
//		for(Entry<String, Set<String>> entry : userIds.entrySet()) {
//			String idName = entry.getKey();
//			Set<String> idValues = entry.getValue();
//			for(String idVal : idValues) {
//				String val = idVal;
//				Scan scan = new Scan();
//				scan.setCaching(100);
//				scan.setStartRow(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + start));
//				Filter stopRowFilter = new InclusiveStopFilter(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + end));
//				
//				scan.setFilter(stopRowFilter);
//				ResultScanner rs = hTable.getScanner(scan);
//				
//				for(Result r : rs) {
//					for(Cell cell : r.rawCells()){
////						System.out.println(new String(CellUtil.cloneRow(cell))+"\t"
////	                    +new String(CellUtil.cloneFamily(cell))+"\t"
////	                    +new String(CellUtil.cloneQualifier(cell))+"\t"
////	                    +new String(CellUtil.cloneValue(cell),"UTF-8")+"\t"
////	                    +cell.getTimestamp());
//						String columnName = new String(CellUtil.cloneQualifier(cell));
//						String tag = new String(), toJudgeTag = new String();
//						Boolean flag = false; 
//						if(columnName.contains("#needJudge#")) {							
//							String[] temp = columnName.contains("#PAIR#") ? columnName.split("#PAIR#needJudge#") : columnName.split("#STR#needJudge#");
//							tag = temp[0];
//							toJudgeTag = temp[1];
//							flag = true;
//						} else {
//							tag = columnName;
//						}
//						String dataDate = new String(CellUtil.cloneRow(cell));
//						dataDate = dataDate.split("#STR#")[2];
//						if(roleMapping.get(tag) == null) {
//							continue;
//						}
//						String searchDateRange = roleMapping.get(tag).split(",")[1];
//						if(!searchDateRange.equals("6")) {
//							Integer dateRange = new Integer(searchDateRange);
//							Calendar searchDate = Calendar.getInstance();
////							searchDate.add(Calendar.DATE, -1);
//							searchDate.add(Calendar.MONTH, -dateRange);
//							String defaultStart = sdf.format(searchDate.getTime());
//							if(defaultStart.compareTo(dataDate)>0) break;
//						}
//						if(roleMapping.get(tag) == null) {
//							continue;
//						}
//						String roleCNName = roleMapping.get(tag).split(",")[0];
//						
//						String roleName = roleCNName + "_" + tag;
//						String newVal = new String(CellUtil.cloneValue(cell),"UTF-8");
//						if(newVal.equals("needJudge")) {
//							newVal = toJudgeTag;
//						}
//						if(!role.contains(roleName)) {
//							if(!newVal.equals("none")) {
//								if(!role.toString().contains(roleName+"_")) {
//									role.add(roleName+"_"+newVal);
//								} else {
//									String regExp = roleName + "_[\\w\\+]+,"; 
//									String[] ss = role.toString().split(regExp);
//									String count = ss[0].replaceAll("[^,]", "");
//									int indexNum = count.length();
//									String oriValue = role.get(indexNum);
//									String[] tempStr = oriValue.split("_");
//									oriValue= tempStr.length ==3? tempStr[2]:"";
//									if(oriValue.length()<2 && newVal.length()<2) {
//										String finalVal = oriValue.compareTo(newVal)<0 ? oriValue : newVal;
//										role.set(indexNum, roleName+"_"+finalVal);
//									} else if(!oriValue.equals(newVal)) {
//										String combStr = oriValue + newVal;
//										if(combStr.contains("page") && combStr.contains("explore") && combStr.contains("search")) {
//											combStr = combStr.replaceAll("page|explore|search","");
//											combStr += "C";
//										}
//										if(combStr.contains("A")) {
//											role.set(indexNum, roleName+"_"+"A");
//										} else if(combStr.contains("B")) {
//											role.set(indexNum, roleName+"_"+"B");
//										} else if(combStr.contains("C")) {
//											role.set(indexNum, roleName+"_"+"C");
//										} else {
//											role.set(indexNum, roleName + "_" + combStr);
//										}
//									}										
//								}
//							} else {
//								role.add(roleName);
//							}
//						} else {
//							int indexRoleName = role.indexOf(roleName);
//							if(flag) {									
//								role.set(indexRoleName, roleName+"_"+toJudgeTag+"+");
//							} else if(!newVal.equals("none")){
//								role.set(indexRoleName, roleName+"_"+newVal);
//							}
//						}
//			        }
//				}
//				rs.close();
//			}			
//		}
//		
////		System.out.println("角色排序前："+ role);
//        for(int j=0; j<role.size(); j++) {
//    	  for(int i=0; i < role.size()-j-1; i++) {
//    		String regEx = "[^A-E]";
//            Pattern p = Pattern.compile(regEx);
//            if(role.size() < i+1+1) {
//            	break;
//            }
//            Matcher thisM = p.matcher(role.get(i));
//            Matcher nextM = p.matcher(role.get(i+1));
//            String thisDegree = thisM.replaceAll("").trim().equals("")?"F":thisM.replaceAll("").trim();
//            String nextDegree = nextM.replaceAll("").trim().equals("")?"F":nextM.replaceAll("").trim();
//    		if(thisDegree.compareTo(nextDegree) > 0) {
//    			String temp = role.get(i);
//    			role.set(i, role.get(i+1));
//    			role.set(i+1, temp);
//    		}
//    	  }
//       }
////       System.out.println("角色排序后：" + role); 
//		System.out.println("-------------------------角色终止�?------------------------");
//		return role;
//	}
	
//	public Map<String, Map<String, List<String>>> scanOwnerBehavior(HTable hTable, Map<String, Set<String>> userIds, Map<String, Map<String,List<Map<String, Set<String>>>>> bizToTagOrder, String role, Map<String, String> pageCodeMap) throws Exception {
//		System.out.println("----------------新业主行为起始线---------------------");
//		Map<String, Map<String, List<String>>> behavior = new HashMap<String, Map<String, List<String>>>();
//		String enKey = new String();
//		Map<String,List<Map<String, Set<String>>>> enValue = new HashMap<String,List<Map<String, Set<String>>>>();	
//		for(Entry<String, Map<String, List<Map<String, Set<String>>>>> enBizToTagOrder : bizToTagOrder.entrySet()) {
//			enKey = enBizToTagOrder.getKey();
//			enValue = enBizToTagOrder.getValue();
//		}
//		
//		//动作对应的中文名
//		Map<String, String> actionMap = new HashMap<String, String>();
//		//标签对组的关�?
//		
//		Map<String,String> tagToGroup = new HashMap<String,String>();
//		for(Entry<String,List<Map<String, Set<String>>>> groupNameTagsEntry : enValue.entrySet()){
//			for(Map<String,Set<String>> groupNameTagMap : groupNameTagsEntry.getValue()){
//				for(Entry<String,Set<String>> groupNameTagEntry : groupNameTagMap.entrySet() ){
//					String groupName = groupNameTagEntry.getKey();
//					for(String tag : groupNameTagEntry.getValue()){
//						tagToGroup.put(tag, groupName);
//					}
//				}
//			}
//		}
//		System.out.println("tagToGroup:" + tagToGroup);
//		
//		int searchRange = 0;
//		Pattern rolePattern = Pattern.compile("");
//		if(role.equals("esf")) {
//			searchRange = 3;
//			rolePattern = Pattern.compile(".*esf.*delegate$|.*esf.*dsearch$");
//			actionMap.put("#delegate", "我要卖房");
//			actionMap.put("#dsearch", "查看房源委托");
//		} else if(role.equals("zf")) {
//			searchRange = 1;
//			rolePattern = Pattern.compile(".*zf.*delegate$|.*zf.*reviseinfozf$|.*zf.*renthz$|.*zf.*renthzz$");
//			actionMap.put("#delegate", "帮你卖房/发布出租");
//			actionMap.put("#dsearch", "查看房源委托");
//			actionMap.put("#renthz", "发布合租");
//			actionMap.put("#renthzz", "发布整租");
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		Calendar now = Calendar.getInstance();
////		now.add(Calendar.DATE, -1);
//		String end = sdf.format(now.getTime());
//		now.add(Calendar.MONTH, -searchRange);
//		String start = sdf.format(now.getTime());
//		
//		for(Entry<String, Set<String>> entry : userIds.entrySet()) {
//			String idName = entry.getKey();
//			Set<String> idValues = entry.getValue();
//			for(String idVal : idValues) {
//				String val = idVal;
//				Scan scan = new Scan();
////				scan.setCaching(100);
//				FilterList filterList=new FilterList();
//				scan.setStartRow(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + start));
//				Filter stopRowFilter = new InclusiveStopFilter(Bytes.toBytes(val + "#STR#" + idName + "#STR#" + end));
//				filterList.addFilter(stopRowFilter);
//				scan.setFilter(filterList);
//				ResultScanner rs = hTable.getScanner(scan);
//				for(Result r : rs) {
//					for(Cell cell: r.rawCells()) {
////						System.out.println(new String(CellUtil.cloneRow(cell))+"\t"
////			                    +new String(CellUtil.cloneFamily(cell))+"\t"
////			                    +new String(CellUtil.cloneQualifier(cell))+"\t"
////			                    +new String(CellUtil.cloneValue(cell),"UTF-8")+"\t"
////			                    +cell.getTimestamp());
//						String cellName = new String(CellUtil.cloneQualifier(cell), "UTF-8");
//						String[] cellArr = cellName.split("#STR#");
//						
//						Matcher roleMatcher = rolePattern.matcher(cellName);
//						if(roleMatcher.find())  {
//							String behaviorDate = new String(CellUtil.cloneRow(cell));
//							behaviorDate = behaviorDate.split("#STR#")[2];
//							String tag = cellArr[0];
//							String city = cellArr[1];
//							String value = cellArr[2];
////							System.out.println("tag:" + tag);
//							if(tag.contains("projectid")) {
//								value = GetInfoDaoImpl.translate(new ArrayList<String>(Arrays.asList(value.split(" ")))).get(value).split("#STR#")[1];
//							}
//							String page = cellArr[3];
////							System.out.println("page code: " + tag.split("\\.")[1] + "_" +page);
//							page = pageCodeMap.get(tag.split("\\.")[1] + "_" +page);
//							tag = tagToGroup.get(tag);
//							tag = tag.replace("关注�?", "");
//							String action = cellArr[4];
//							action = actionMap.get(action);
//							long ts = cell.getTimestamp();
//							SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
//							String timestamp = sdfTime.format(ts);
////							System.out.println(behaviorDate + "\t" + timestamp + "\t" + tag + "\t" + city + "\t" + value + "\t" + page + "\t" + action);
//							List<String> infoCell = new ArrayList<String>();
//							infoCell.add(action);
//							infoCell.add(page);
//							infoCell.add("城市"+ "#STR#" +city);
//							infoCell.add(tag + "#STR#" + value);						
//							
//							Map<String, List<String>> timeCell = new HashMap<String, List<String>>();
//							timeCell.put(new String(timestamp), infoCell);
//							if(behavior.containsKey(behaviorDate)) {
//								if(behavior.get(behaviorDate).containsKey(timestamp)) {
//									behavior.get(behaviorDate).get(timestamp).add(tag + "#STR#" + value);
//								} else {
//									behavior.get(behaviorDate).putAll(timeCell);
//								}								
//							} else {
//								behavior.put(behaviorDate, timeCell);
//							}
//							
//						} else {
//							continue;
//						}
//					}				 
//				}
//				rs.close();
//			}
//		}
//		System.out.println("Owner behavior: " + behavior);
//		System.out.println("----------------新业主行为终结线---------------------");
//		return behavior;
//	}
	
	/*
	 *	扫描定位表信�? 
	 * */
//	public Map<String, String> scanRoute(HTable hTable, Map<String, Set<String>> userIds) throws IOException {
//		Map<String, String> route = new HashMap<String, String>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		Calendar now = Calendar.getInstance();
////		now.add(Calendar.DATE, -1);
//		String end = sdf.format(now.getTime());
//		now.add(Calendar.MONTH, -1);
//		String start = sdf.format(now.getTime());
//		
//		for(Entry<String, Set<String>> entry : userIds.entrySet()) {
//			String idName = entry.getKey();
//			Set<String> idValues = entry.getValue();
//			for(String idVal : idValues) {
//				Scan scan = new Scan();
////				scan.setCaching(100);
//				scan.setStartRow(Bytes.toBytes(idVal + "#STR#" + idName + "#STR#" + start));
//				Filter stopRowFilter = new InclusiveStopFilter(Bytes.toBytes(idVal + "#STR#" + idName + "#STR#" + end));
//				scan.setFilter(stopRowFilter);
//				ResultScanner rs = hTable.getScanner(scan);
//				for(Result r : rs) {
//					String time = r.getRow().toString();
//					if(time.split("#STR#").length > 2) {
//						time = time.split("#STR#")[2];
//					} else {
//						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
//						time = sdf2.format(r.getColumn(Bytes.toBytes("detail"), Bytes.toBytes("xy")).get(0).getTimestamp());
//					}
//					route.put(time, new String(r.getValue(Bytes.toBytes("detail"), Bytes.toBytes("xy")),"utf-8"));
//				}
//				rs.close();
//			}
//		}
//		return route;
//	}
	

//	public String maxCount(Map<String, Integer> obj) {
//		String maxCountStr = new String();
//		Integer max = 0;
//		for(Entry<String, Integer> en: obj.entrySet()) {
//			if(en.getValue() > max) {
//				max = en.getValue();
//				maxCountStr = en.getKey();
//			}
//		}
//		return maxCountStr;
//	}
	
	public static List<Result> getDatasFromHbase(List<String> rowList, int step, String tableName) {
		if(rowList == null || rowList.size() <= 0) {
			return null; //?
		}
//		int nThreads = Runtime.getRuntime().availableProcessors() * 2 + 1;
		
		ExecutorService pool = Executors.newFixedThreadPool(10);
		final int loopSize = step;
//		final int loopSize = nThreads;
		ArrayList<Future<List<Result>>> results = new ArrayList<Future<List<Result>>>();

		TableName tn = TableName.valueOf(tableName);
		for(int loop=0; loop < loopSize; loop++) {
			int end = (loop + 1) * loopSize > rowList.size() ? rowList.size() : (loop + 1) * loopSize;
			if(loop * loopSize >= end) break;
	        List<String> partRowKeys = rowList.subList(loop * loopSize, end);
	        HbaseDataGetter hbaseDataGetter = new HbaseDataGetter(partRowKeys, conn, tn, loop);
	    	Future<List<Result>> result = pool.submit(hbaseDataGetter);
	        results.add(result);
		}
	    List<Result> resultList = new ArrayList<Result>();

	    /*for(Future<List<Result>> future: results) {
	    	try {
				future.get();
				System.out.println("future:" + future.hashCode());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
	    }*/

	    boolean flag = false;
	    while(!flag) {
	    	flag = true;
	    	for(Future<List<Result>> future : results) {
	    		if(!future.isDone()) {
	    			flag = false;
	    			break;
	    		}
	    	}
	    	try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }

	    try {
	    	for (Future<List<Result>> result : results) {
	    		if(result.get() != null) {
	        		List<Result> rd = result.get();
//	        		System.out.println("thread result: " + rd);
	                resultList.addAll(rd);
	        	}
	        }
	    } catch (ExecutionException | InterruptedException e) {
	        e.printStackTrace();
	    }  finally {
//	    	try {
//				conn.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
	    	pool.shutdown();
	    }

	    return resultList;
	}
	
	public static class HbaseDataGetter implements Callable<List<Result>>
	{
	    private List<String> rowKeys;
	    private org.apache.hadoop.hbase.client.Connection conn;
	    private TableName behaviorTable;
	    private int loop;
	    public HbaseDataGetter(List<String> rowList, org.apache.hadoop.hbase.client.Connection conn, TableName behaviorTable, int loop) {
	        this.rowKeys = rowList;
	        this.conn = conn;
	        this.behaviorTable = behaviorTable;
	        this.loop = loop;
	    }
	    
	    @Override
	    public List<Result> call() throws Exception {
	    	List<Result> res = getDatasFromHbase(rowKeys, conn, behaviorTable, loop);
	        List<Result> listData = new ArrayList<Result>();
	        for(Result re : res) {
	            listData.add(re);
	        }
	        return listData;
	    }
	}
	private static List<Result> getDatasFromHbase(List<String> rowList, org.apache.hadoop.hbase.client.Connection conn, TableName behaviorTable, int loop) {
	        List<Result> result = new ArrayList<Result>();
	        List<Get> listGets = new ArrayList<Get>();
	        Table objTable = null;
	        for (String rk : rowList) {
	            Get get = new Get(Bytes.toBytes(rk));
	            listGets.add(get);
	        }
	        try {
	        	objTable = ((org.apache.hadoop.hbase.client.Connection) conn).getTable(behaviorTable);
	        	Result[] res = objTable.get(listGets);
	            for(Result r : res) {
	            	if(r != null && !r.isEmpty()) {
	            		result.add(r);
	            	}
	            }
	        } catch (IOException e1){
	            e1.printStackTrace();
	        } finally {
	            try {
	                listGets.clear();
	                objTable.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            } 
	        }
	        return result;
	}
	public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
