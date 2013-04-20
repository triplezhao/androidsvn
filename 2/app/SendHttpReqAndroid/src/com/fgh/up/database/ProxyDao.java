package com.fgh.up.database;



import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 * 本地用户信息操作类
 * @author ztw
 *
 */
public class ProxyDao {
	
	private static final String TAG = "ProxyDao";
	
	//对应的表名
	public static final String TB_NAME = "tb_proxy";
	//对应的表的字段
	public static final String ID = "_id";
	public static final String mProxyName = "proxyName";
	public static final String mProxyValue = "proxyValue";
	
	
	
	
	protected static SQLiteDatabase db;
    protected static PigUPOpenHelper dbhelper;
	private static ProxyDao dao;

	
	
	
	
	private ProxyDao(Context context){
    }
	
	public static synchronized ProxyDao getInstance(Context ctx) {
		if (ctx == null) {
			return null;
		}
		if(dbhelper==null){
			dbhelper = new PigUPOpenHelper(ctx);
    	}
    	if( db==null){
    		 db  = dbhelper.getWritableDatabase();
    	}
		if (dao == null) {
			dao = new ProxyDao(ctx);
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
	
	
	
	/**
	 * 更具bean对象转换成CV
	 * @param proxyBean
	 * @return
	 */
	private ContentValues bean2ContentValues(ProxyBean proxyBean) {
		ContentValues v = new ContentValues();
		
		if (proxyBean.getId() != null) {
			v.put(ID, proxyBean.getId());
		}
		if (proxyBean.getProxyName() != null) {
			v.put(mProxyName,proxyBean.getProxyName());
		}
		if (proxyBean.getProxyValue() != null) {
			v.put(mProxyValue,proxyBean.getProxyValue());
		}
		
		return v;
	}
	
	
	/**
	 * 根据游标转换成具体的bean集合
	 * @param cursor
	 * @return
	 */
	private List<ProxyBean> cursor2Beans(Cursor cursor) {
	        try {
	        	 List<ProxyBean> proxyList = null;
	        	 ProxyBean proxyBean = null;
					if (cursor != null && cursor.getCount() > 0) {
						proxyList = new ArrayList<ProxyBean>();
						while (cursor.moveToNext()) {
							proxyBean = new ProxyBean();
				        	proxyBean.setId(cursor.getString(0));
				        	proxyBean.setProxyName(cursor.getString(1));
				        	proxyBean.setProxyValue(cursor.getString(2));
				        	proxyList.add(proxyBean);
						}
					}
				  cursor.close();
				  return proxyList;
			    } catch (Exception e) {
				  cursor.close();
			    }
			   return null;
		}
	
	
	/**
	 * 构造where条件的sql语句
	 * @param keys
	 * @return
	 */
	private String build_Where_Key(Object[] keys) {
		if (keys == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (Object key : keys) {
			if (key != null) {
				sb.append(" ").append(key.toString()).append(" = ? ");
			}
			sb.append("and");
		}
		return sb.substring(0, sb.length() - 3);
	}
	
	
	
	//==================================================================
	//以上完成基础方法3个，用于后面的CRUD增删改查方法
	//==================================================================
	
	
	
	
	
	
	
	
	
	
		
    /**
     * 插入chanel历史表的记录
     * @param chanel
     * @return
     */
    public long insert(ProxyBean chanel)
    {
    	if (db != null) {
    		ContentValues cv=bean2ContentValues(chanel);
            long uid = db.insert(TB_NAME, ID, cv);
            Log.i(TAG, "insert " + uid + "");
            return uid;
		}
		return -1L;
    }
    /**
     * 删除by chanelid
     * @param chanelid
     * @return
     */
    public long deleteByChanelName(String proxyName)
    {
    	if (db != null) {
    		   int rows = db.delete(TB_NAME, 
    				   		build_Where_Key(new String[]{mProxyValue}),
    				   		new String[]{proxyName+""});
    		    Log.i(TAG, "delete " + rows + "");
    	        return rows;
    	}
    	return -1L;
    }
    /**
     * 删除个chanelbean，参数为bean。实际条件为uid
     * @param proxyBean
     * @return
     */
    public long deleteByBean(ProxyBean proxyBean)
    {
    	if (db != null) {
    		int rows = db.delete(TB_NAME, 
    				build_Where_Key(new String[]{mProxyValue}),
    				new String[]{String.valueOf(proxyBean.getProxyValue()+"")});
    		Log.i(TAG, "delete " + rows + "");
    		return rows;
    	}
    	return -1L;
    }
    
    /**
     * 更新chanel表的记录
     * @param proxyBean
     * @return
     */
    public int update(ProxyBean proxyBean)
    {
        ContentValues values = bean2ContentValues(proxyBean);

        int rows = db.update(TB_NAME, values,
        		build_Where_Key(new String[]{ID}),
        		new String[]{proxyBean.getId()});
        Log.i(TAG, "update " + rows + "");
        
        return rows;
    }
    
    /**
     * 查询某一个chanel
     * @param chanelid
     * @return
     */
    public ProxyBean queryOneChanelByProxyValue(String proxyValue)
    {
        Cursor cursor = db.query(TB_NAME, null,
        		build_Where_Key(new String[]{mProxyValue}),
        		new String[]{proxyValue}, null, null, null, null);
        ProxyBean proxyBean=null;
        if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				proxyBean = new ProxyBean();
	        	proxyBean.setId(cursor.getString(0));
	        	proxyBean.setProxyName(cursor.getString(1));
	        	proxyBean.setProxyValue(cursor.getString(2));
	        	break;
			}
		}
        return proxyBean;
    }
    /**
     * 查询所有
     * @return
     */
    public List<ProxyBean> queryAllchanelStory()
    {
    	Cursor cursor = db.query(TB_NAME, null,
    			null,null, null, null, null, null);
    	
    	return cursor2Beans(cursor);
    }

}
