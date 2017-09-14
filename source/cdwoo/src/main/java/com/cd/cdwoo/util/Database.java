package com.cd.cdwoo.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
	private Connection connection;
	public String connectionString;
	public PreparedStatement prest = null;
	public CallableStatement calst = null;
	
	private static Map<String, String> ConnectionStrings = new HashMap<String, String>();
	
	static {
		
		ConnectionStrings.put("ub", "jdbc:sqlserver://10.16.69.12:1433;databaseName=ub;user=datacenter_ub_admin;password=htMsk5b4;");
		//ConnectionStrings.put("proj_north", "jdbc:mysql://192.168.7.86:3311/newhouse?user=dsjpt_r&password=JKo65c78&useUnicode=true&characterEncoding=utf-8");
		//ConnectionStrings.put("proj_south", "jdbc:mysql://192.168.7.86:3312/newhouse?user=dsjpt_r&password=Jko35M72&useUnicode=true&characterEncoding=utf-8"); 
		ConnectionStrings.put("t400", "jdbc:mysql://192.168.7.86:3315/newhouse630?user=630_r_tab&password=3029e1ac&useUnicode=true&characterEncoding=utf-8");
		ConnectionStrings.put("im", "jdbc:sqlserver://124.251.44.71:1433;databaseName=Chat_3g;user=chat_3g_admin;password=M4ry4m7y;");
		ConnectionStrings.put("media", "jdbc:sqlserver://118.192.167.12:1433;databaseName=glht_report;user=glht_report_w;password=iAt7fyxc;");
		ConnectionStrings.put("bigdata", "jdbc:mysql://10.16.64.36:3122/bigdata?user=bigdata_admin&password=62Bs6dux&useUnicode=true&characterEncoding=utf-8&useSSL=false");
	}
	
	public Database() {
		this.connectionString = ConnectionStrings.get("ub");
		this.open();
	}
	
	public Database(String dbName) {
		this.connectionString = ConnectionStrings.get(dbName);
		this.open();		
	}	
	
	public void open() {
		
		boolean reOpen = false;
		try {
			if (this.connectionString.contains("sqlserver")) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			}
			else {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			}
			connection = DriverManager.getConnection(this.connectionString);
		} catch (InstantiationException e) {
			System.out.println("open database InstantiationException" + e.getMessage());
			reOpen = true;
		} catch (IllegalAccessException e) {
			System.out.println("open database IllegalAccessException" + e.getMessage());
			reOpen = true;
		} catch (ClassNotFoundException e) {
			System.out.println("open database ClassNotFoundException" + e.getMessage());
			reOpen = true;
		} catch (SQLException e) {
			System.out.println("open database SQLException" + e.getMessage());
			reOpen = true;
		}
		if(reOpen){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			open();
		}
		
	}
	
	// {call storedprocedure(?,?)}
	public void setProcedureCall(String storedProcedure) throws SQLException {
		this.calst = this.connection.prepareCall("{call " + storedProcedure + "}");
	}	
	
	public void putString(int i, String v) throws SQLException {
		this.calst.setString(i, v);
	}
	
	public void putInt(int i, int v) throws SQLException {
		this.calst.setInt(i, v);
	}
	
	public void processUpdate() throws SQLException {
		this.calst.executeUpdate();
	}
	
	//public void processResultSet() {}
	
	public ResultSet executeResultSet(String commandText) {
		ResultSet rs = null;
		try {
			prest = this.connection.prepareStatement(commandText);
			rs = prest.executeQuery();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public int executeUpdate(String commandText) {
		int row = -1;
		try {
			prest = this.connection.prepareStatement(commandText);
			row = prest.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
	
	public void setBatch(String commandText) {
		try {
			this.prest = this.connection.prepareStatement(commandText);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addBatch(String value) {
		try {
			this.prest.setString(1, value);
			this.prest.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addBatch(List<String> values) {
		try {
			for (int i = 0; i < values.size(); i++) {
				this.prest.setString(i + 1, values.get(i));			
			}
			this.prest.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void addBatch(String... values) {
		try {
			for (int i = 0; i < values.length; i++) {
				this.prest.setString(i + 1, values[i]);			
			}
			this.prest.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void addBatch(String key, String value) {
		try {
			this.prest.setString(1, key);		
			this.prest.setString(2, value);
			this.prest.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addBatch(Map<String, String> map, String...keys) {
		try {
			for (int i = 0; i < keys.length; i++) {
				this.prest.setString(i + 1, map.get(keys[i]));	
			}
			this.prest.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void executeBatch() {
		try {
			this.connection.setAutoCommit(false);
			this.prest.executeBatch();
		    this.connection.setAutoCommit(true);
		    this.prest.clearBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setCommand(String commandText) {
		try {
			prest = this.connection.prepareStatement(commandText);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setInt(int i, int v) {
		try {
			prest.setInt(i,  v);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setLong(int i, long v) {
		try {
			prest.setLong(i,  v);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setString(int i, String v) {
		try {
			prest.setString(i,  v);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setDate(int i, Date v) {
		try {
			if(v != null){
				prest.setDate(i,  new java.sql.Date(v.getTime()));	
			}else{
				prest.setDate(i,  null);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int executeUpdate() {
		int row = -1;
		try {		
			row = prest.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
	
	public ResultSet executeResultSet() {
		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return rs;
	}
	
	public void close() {
		try {
			if(this.prest != null){
				this.prest.close();
			}
			if(this.calst != null) {
				this.calst.close();
			}
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return this.connection;
	}
}