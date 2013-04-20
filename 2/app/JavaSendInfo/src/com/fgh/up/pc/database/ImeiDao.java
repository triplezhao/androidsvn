package com.fgh.up.pc.database;



import java.awt.Cursor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;

import com.fgh.up.pc.util.ImeiDateUtil;


/**
 * 本地用户信息操作类
 * @author ztw
 *
 */
public class ImeiDao {
	
	private static final String TAG = "ImeiDao";
	
	//对应的表名
//	public static final String TB_NAME = "tb_imei";
	//对应的表的字段
	public static final String ID = "_id";
	public static final String imei_value = "imei_value";
	public static final String imei_chanel = "imei_chanel";
	public static final String imei_date = "imei_date";
	
	
	private static ImeiDao dao;
	private DBConnection dBConnection;
	
	
	private ImeiDao(){
		dBConnection=dBConnection.getInstance();
    }
	
	public static synchronized ImeiDao getInstance() {
		if (dao == null) {
			dao = new ImeiDao();
		}
		return dao;
	}
    
    public void close()
    {
//    	if(db!=null){
//    		db.close();
//    	}
//    	if(dbhelper!=null){
//    		dbhelper.close();
//    	}
//    	dao=null;
    }
	
	
	

	
	

    public long insert2table(ImeiBean imeiBean,String tb_name)
    {
    		tb_name=getTBnameByChanel(tb_name);
    	if (dBConnection != null) {
//    		ContentValues cv=bean2ContentValues(imeiBean);
//            long uid = db.insert(TB_NAME, ID, cv);
//            Log.i(TAG, "insert " + uid + "");
    		int res=dBConnection.exeSql("insert into "+tb_name
    				+" values( null, '"+imeiBean.getImei_value()
    				+"','"+imeiBean.getImei_chanel()
    				+"',"+imeiBean.getImei_date()
    				+");");
    		
    		return res;
    	}
    	return -1L;
    }
 
    

    
 
    /**
     * 新版的随机查询一条记录的方法
     * @param maxdate
     * @param mindate
     * @param chanel
     * @return
     */
    public ImeiBean queryOneImeiRandomByDate(long maxdate,long mindate,String chanel)
    {
    	
    	ResultSet  cursor=getRandomOneByChanelAndDateImei( maxdate,mindate,chanel);
    	
    	ImeiBean imeiBean=null;
    	try {
    		if(!cursor.isClosed()){
    			if(cursor.next()){
    				imeiBean = new ImeiBean();
    				imeiBean.setId(cursor.getString(1));
    				imeiBean.setImei_value(cursor.getString(2));
    				imeiBean.setImei_chanel(cursor.getString(3));
    				imeiBean.setImei_date(cursor.getInt(4));
    			}
    		}
    		
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} 
    	if(imeiBean!=null){
    		System.out.println("get from db: "+imeiBean.getImei_value());
    		
    	}
    	return imeiBean;
    }

    public ImeiBean queryOneImeiRandomByTbName(String tablename)
    {
    	 ResultSet  cursor=getRandomOneByChanelImei1(tablename);
    	
    	 ImeiBean imeiBean=null;
    	 	try {
    	 		 if(!cursor.isClosed()){
    	 			if(cursor.next()){
		 	    		imeiBean = new ImeiBean();
		 				imeiBean.setId(cursor.getString(1));
		 				imeiBean.setImei_value(cursor.getString(2));
		 				imeiBean.setImei_chanel(cursor.getString(3));
		 				imeiBean.setImei_date(cursor.getInt(4));
		 	    	}
    	 		 }
		 	    	
    	 	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    	return imeiBean;
    }
   
    private ResultSet getRandomOneByChanelImei1(String tb_name)
    {
    		tb_name=getTBnameByChanel(tb_name);
    	String minid="(SELECT MIN(_id) FROM "+tb_name+")";
    	String maxid="(SELECT MAX(_id) FROM "+tb_name+")";
    	
    	String sql="select * from "
			+tb_name
			+" where _id = ("
			+minid+"+abs(random()%("+maxid+" - "+minid+")));";
			
    	
    	ResultSet  cursor=dBConnection.exeQuerySql(sql);
    	
    	return cursor;
    	
    }
//  
    
    /**
     * @param maxdate
     * @param mindate
     * @param tb_name
     * 
     * SELECT * FROM szshuaji where _id=
    		((SELECT MIN(_id) FROM szshuaji where imei_date <= 1350403200 and imei_date >= 1336348800)+
    		abs(random()%(
    		(SELECT MAX(_id) FROM szshuaji where imei_date <= 1350403200 and imei_date >= 1336348800)
    		-
    		(SELECT MIN(_id) FROM szshuaji where imei_date <= 1350403200 and imei_date >= 1336348800))
    		));
     * @return
     */
    private ResultSet getRandomOneByChanelAndDateImei(long maxdate,long mindate,String tb_name)
    {
    		tb_name=getTBnameByChanel(tb_name);
    	String wheresql=" where " +imei_date+" > "+mindate+" and "+imei_date+" < "+maxdate;
    	String minid="(SELECT MIN(_id) FROM "+tb_name+wheresql+")";
    	String maxid="(SELECT MAX(_id) FROM "+tb_name+wheresql+")";
    	String sql="select * from "
			+tb_name
			+" where _id = ("
			+minid+" + abs(random()%("+maxid+" - "+minid+")));";
    	ResultSet  cursor=dBConnection.exeQuerySql(sql);
    	
    	return cursor;
    }

    public int createTable(String tb_name)
    {
    		tb_name=getTBnameByChanel(tb_name);
    	String createSQL=" CREATE TABLE IF NOT EXISTS "+tb_name+"(_id integer primary key,imei_value varchar,imei_chanel varchar,imei_date integer NOT NULL DEFAULT 0)";
    	
    	int  resault=dBConnection.exeSql(createSQL);
    	
    	return resault;
    }
    
    private String getTBnameByChanel(String chanel){
    	
    	String tb_name="tb_"+chanel.replaceAll("\\s*", "");
    	return tb_name;
    	
    }
}
