package com.fgh.up.pc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection
{
//	public static final String DB_NAME = "db_up";
	public String dbname="db_up.db";
	private String connectionUrl="jdbc:sqlite:";
	public Connection con=null;
	private Statement stmt=null;
	private PreparedStatement psm=null;
//	private ResultSet rs=null;
	String path=null;
	
	
	private static DBConnection dBConnection;
	
	
	public static synchronized DBConnection getInstance() {
		if (dBConnection == null) 
		{
//			dBConnection.close();
			dBConnection = new DBConnection();
		}else{
			dBConnection.close();
			dBConnection = new DBConnection();
		}
		return dBConnection;
	}
	
	
	private DBConnection()
	{
		path=this.getClass().getClassLoader().getResource("").getPath()+"/"
				+dbname ;
		//System.out.println(path);
		connectionUrl+=path;
		connect();
	}

	public boolean connect()
	{
//		String psql="insert into position values(?,?,?,?,?,datetime('now'),?)";// id,lat,lats,lng,lngs,time,speed
		try
		{
			Class.forName("org.sqlite.JDBC");
			con=DriverManager.getConnection(connectionUrl);
			stmt=con.createStatement();
//			stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			this.psm=con.prepareStatement(psql);
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	

	public void close()
	{
//		if(rs!=null)
//			try
//			{
//				rs.close();
//			}
//			catch(Exception e)
//			{
//			}
		if(stmt!=null)
			try
			{
				stmt.close();
			}
			catch(Exception e)
			{
			}
		if(psm!=null)
			try
			{
				psm.close();
			}
			catch(Exception e)
			{
			}
		if(con!=null)
			try
			{
				con.close();
			}
			catch(Exception e)
			{
			}
	}

	public synchronized int update(String sql) throws SQLException
	{
		return stmt.executeUpdate(sql);
	}

	public synchronized int exeSql(String sql){
		
		if(stmt!=null){
			try {
				stmt.execute(sql);
				return 1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		}
		return 0;
	}
	public   ResultSet  exeQuerySql(String sql){
		
	
		
		if(stmt!=null){
			try {
				stmt.close();
				stmt=con.createStatement();
				return stmt.executeQuery(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

}
