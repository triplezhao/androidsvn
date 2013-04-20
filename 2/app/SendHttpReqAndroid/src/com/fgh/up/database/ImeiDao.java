package com.fgh.up.database;



import java.util.ArrayList;
import java.util.List;

import com.fgh.up.util.ImeiDateUtil;

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
public class ImeiDao {
	
	private static final String TAG = "ImeiDao";
	
	//对应的表名
	public static final String TB_NAME = "tb_imei";
	//对应的表的字段
	public static final String ID = "_id";
	public static final String imei_value = "imei_value";
	public static final String imei_chanel = "imei_chanel";
	public static final String imei_date = "imei_date";
	
	
	
	
	protected static SQLiteDatabase db;
    protected static PigUPOpenHelper dbhelper;
	private static ImeiDao dao;

	
	
	
	
	private ImeiDao(Context context){
    }
	
	public static synchronized ImeiDao getInstance(Context ctx) {
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
			dao = new ImeiDao(ctx);
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
	private ContentValues bean2ContentValues(ImeiBean imeiBean) {
		ContentValues v = new ContentValues();
		
		if (imeiBean.getId() != null) {
			v.put(ID, imeiBean.getId());
		}
		if (imeiBean.getImei_value() != null) {
			v.put(imei_value,imeiBean.getImei_value());
		}
		if (imeiBean.getImei_chanel() != null) {
			v.put(imei_chanel,imeiBean.getImei_chanel());
		}
		if (imeiBean.getImei_date() != 0) {
			v.put(imei_date,imeiBean.getImei_date());
		}
		
		return v;
	}
	
	
	/**
	 * 根据游标转换成具体的bean集合
	 * @param cursor
	 * @return
	 */
	private List<ImeiBean> cursor2Beans(Cursor cursor) {
	        try {
	        	 List<ImeiBean> imeiList = null;
	        	 ImeiBean imeiBean = null;
					if (cursor != null && cursor.getCount() > 0) {
						imeiList = new ArrayList<ImeiBean>();
						while (cursor.moveToNext()) {
							imeiBean = new ImeiBean();
							imeiBean.setId(cursor.getString(0));
							imeiBean.setImei_value(cursor.getString(1));
							imeiBean.setImei_chanel(cursor.getString(2));
							imeiBean.setImei_date(cursor.getInt(3));
							imeiList.add(imeiBean);
						}
					}
				  cursor.close();
				  return imeiList;
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
    public long insert(ImeiBean imeiBean)
    {
    	if (db != null) {
    		ContentValues cv=bean2ContentValues(imeiBean);
            long uid = db.insert(TB_NAME, ID, cv);
            Log.i(TAG, "insert " + uid + "");
            return uid;
		}
		return -1L;
    }
    /**
     * 删除某一时间之前的imei
     * @param imeivalue
     * @return
     */
    public long deleteByDate(String dateString)
    {
    	long l_data=ImeiDateUtil.dateString2long(dateString);
    	if (db != null) {
    		int rows = db.delete(TB_NAME, 
    				imei_date+" < "+l_data,
    				null);
    		
    		Log.i(TAG, "delete " + rows + "");
    		return rows;
    	}
    	return -1L;
    }
    /**
     * 删除by imeivalue
     * @param imeivalue
     * @return
     */
    public long deleteByImei(String imeivalue)
    {
    	if (db != null) {
    		   int rows = db.delete(TB_NAME, 
    				   		build_Where_Key(new String[]{imei_value}),
    				   		new String[]{imeivalue+""});
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
    public long deleteByBean(ImeiBean imeiBean)
    {
    	if (db != null) {
    		int rows = db.delete(TB_NAME, 
    				build_Where_Key(new String[]{imei_value}),
    				new String[]{String.valueOf(imeiBean.getImei_value()+"")});
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
    public int update(ImeiBean imeiBean)
    {
        ContentValues values = bean2ContentValues(imeiBean);

        int rows = db.update(TB_NAME, values,
        		build_Where_Key(new String[]{ID}),
        		new String[]{imeiBean.getId()});
        Log.i(TAG, "update " + rows + "");
        
        return rows;
    }
    
    /**
     * 随机查询某一个imei
     * @param chanelid
     * @return
     */
    public ImeiBean queryOneImeiByRandom(String chanel)
    {
    	
    	Cursor cursor = db.query(TB_NAME, null,
    			build_Where_Key(new String[]{imei_chanel}),
        		new String[]{chanel}, null, null, null, null);
    	ImeiBean imeiBean=null;
    	int count=cursor.getCount()-1;
    	if(count>0){
	    	int index=(int) (Math.random()*count);
	    	if(cursor.moveToPosition(index)){
	    		imeiBean = new ImeiBean();
				imeiBean.setId(cursor.getString(0));
				imeiBean.setImei_value(cursor.getString(1));
				imeiBean.setImei_chanel(cursor.getString(2));
				imeiBean.setImei_date(cursor.getInt(3));
	    	};
    	}
    	return imeiBean;
    }
    public Cursor getChanelImeiCuisor(String chanel)
    {
    	
    	Cursor cursor = db.query(TB_NAME, null,
    			build_Where_Key(new String[]{imei_chanel}),
    			new String[]{chanel}, null, null, null, null);
    	
    	return cursor;
    }
    /**
     * 查询某一个chanel
     * @param chanelid
     * @return
     */
    public ImeiBean queryOneImeiByImeiValue(String imeiValue)
    {
        Cursor cursor = db.query(TB_NAME, null,
        		build_Where_Key(new String[]{imei_value}),
        		new String[]{imeiValue}, null, null, null, null);
        ImeiBean imeiBean=null;
        if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				imeiBean = new ImeiBean();
				imeiBean.setId(cursor.getString(0));
				imeiBean.setImei_value(cursor.getString(1));
				imeiBean.setImei_chanel(cursor.getString(2));
				imeiBean.setImei_date(cursor.getInt(3));
	        	break;
			}
		}
        return imeiBean;
    }
    /**
     * 查询所有
     * @return
     */
    public List<ImeiBean> queryAllImeis()
    {
    	Cursor cursor = db.query(TB_NAME, null,
    			null,null, null, null, null, null);
    	
    	return cursor2Beans(cursor);
    }

    
}
