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
public class ChanelDao {
	
	private static final String TAG = "ChanelDao";
	
	//对应的表名
	public static final String TB_NAME = "tb_chanel";
	//对应的表的字段
	public static final String ID = "_id";
	public static final String chanelNAME = "chanelName";
	
	
	
	
	protected static SQLiteDatabase db;
    protected static PigUPOpenHelper dbhelper;
	private static ChanelDao dao;

	
	
	
	
	private ChanelDao(Context context){
    }
	
	public static synchronized ChanelDao getInstance(Context ctx) {
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
			dao = new ChanelDao(ctx);
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
	 * @param chanelBean
	 * @return
	 */
	private ContentValues bean2ContentValues(ChanelBean chanelBean) {
		ContentValues v = new ContentValues();
		
		if (chanelBean.getId() != null) {
			v.put(ID, chanelBean.getId());
		}
		if (chanelBean.getChanelName() != null) {
			v.put(chanelNAME,chanelBean.getChanelName());
		}
		
		return v;
	}
	
	
	/**
	 * 根据游标转换成具体的bean集合
	 * @param cursor
	 * @return
	 */
	private List<ChanelBean> cursor2Beans(Cursor cursor) {
	        try {
	        	 List<ChanelBean> chanelList = null;
	        	 ChanelBean chanelBean = null;
					if (cursor != null && cursor.getCount() > 0) {
						chanelList = new ArrayList<ChanelBean>();
						while (cursor.moveToNext()) {
							chanelBean = new ChanelBean();
				        	chanelBean.setId(cursor.getString(0));
				        	chanelBean.setChanelName(cursor.getString(1));
				        	chanelList.add(chanelBean);
						}
					}
				  cursor.close();
				  return chanelList;
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
    public long insert(ChanelBean chanel)
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
    public long deleteByChanelName(String chanelName)
    {
    	if (db != null) {
    		   int rows = db.delete(TB_NAME, 
    				   		build_Where_Key(new String[]{chanelName}),
    				   		new String[]{chanelName+""});
    		    Log.i(TAG, "delete " + rows + "");
    	        return rows;
    	}
    	return -1L;
    }
    /**
     * 删除个chanelbean，参数为bean。实际条件为uid
     * @param chanelBean
     * @return
     */
    public long deleteByBean(ChanelBean chanelBean)
    {
    	if (db != null) {
    		int rows = db.delete(TB_NAME, 
    				build_Where_Key(new String[]{chanelNAME}),
    				new String[]{String.valueOf(chanelBean.getChanelName()+"")});
    		Log.i(TAG, "delete " + rows + "");
    		return rows;
    	}
    	return -1L;
    }
    
    /**
     * 更新chanel表的记录
     * @param chanelBean
     * @return
     */
    public int update(ChanelBean chanelBean)
    {
        ContentValues values = bean2ContentValues(chanelBean);

        int rows = db.update(TB_NAME, values,
        		build_Where_Key(new String[]{ID}),
        		new String[]{chanelBean.getId()});
        Log.i(TAG, "update " + rows + "");
        
        return rows;
    }
    
    /**
     * 查询某一个chanel
     * @param chanelid
     * @return
     */
    public ChanelBean queryOneChanelByChanelName(String chanelname)
    {
        Cursor cursor = db.query(TB_NAME, null,
        		build_Where_Key(new String[]{chanelNAME}),
        		new String[]{chanelname}, null, null, null, null);
        ChanelBean chanelBean=null;
        if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				chanelBean = new ChanelBean();
	        	chanelBean.setId(cursor.getString(0));
	        	chanelBean.setChanelName(cursor.getString(1));
	        	break;
			}
		}
        return chanelBean;
    }
    /**
     * 查询所有
     * @return
     */
    public List<ChanelBean> queryAllchanelStory()
    {
    	Cursor cursor = db.query(TB_NAME, null,
    			null,null, null, null, null, null);
    	
    	return cursor2Beans(cursor);
    }

}
