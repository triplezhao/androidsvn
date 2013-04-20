package com.fgh.smshelp.database;



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
public class IndexDao {
	
	private static final String TAG = "IndexDao";
	
	//对应的表名
	public static final String TB_NAME = "tb_index";
	//对应的表的字段
	public static final String ID = "_id";
	public static final String index_cmd = "index_cmd";
	public static final String index_name = "index_name";
	public static final String image = "image";
	
	
	
	
	protected static SQLiteDatabase db;
    protected static PigSmsOpenHelper dbhelper;
	private static IndexDao dao;
	
	
	private IndexDao(Context context){
    }
	
	public static synchronized IndexDao getInstance(Context ctx) {
		if (ctx == null) {
			return null;
		}
		if(dbhelper==null){
			dbhelper = new PigSmsOpenHelper(ctx);
    	}
    	if( db==null){
    		 db  = dbhelper.getWritableDatabase();
    	}
		if (dao == null) {
			dao = new IndexDao(ctx);
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
	private ContentValues bean2ContentValues(IndexBean indexBean) {
		ContentValues v = new ContentValues();
		
		if (indexBean.getId() != null) {
			v.put(ID, indexBean.getId());
		}
		if (indexBean.getIndex_cmd() != null) {
			v.put(index_cmd,indexBean.getIndex_cmd());
		}
		if (indexBean.getIndex_name() != null) {
			v.put(index_name,indexBean.getIndex_name());
		}
		if (indexBean.getImage() != null) {
			v.put(image,indexBean.getImage());
		}
		
		return v;
	}
	
	
	/**
	 * 根据游标转换成具体的bean集合
	 * @param cursor
	 * @return
	 */
	private List<IndexBean> cursor2Beans(Cursor cursor) {
	        try {
	        	 List<IndexBean> imeiList = null;
	        	 IndexBean indexBean = null;
					if (cursor != null && cursor.getCount() > 0) {
						imeiList = new ArrayList<IndexBean>();
						while (cursor.moveToNext()) {
							indexBean = new IndexBean();
							indexBean.setId(cursor.getString(0));
							indexBean.setIndex_cmd(cursor.getString(1));
							indexBean.setIndex_name(cursor.getString(2));
							indexBean.setImage(cursor.getString(3));
							imeiList.add(indexBean);
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
    public long insert(IndexBean indexBean)
    {
    	if (db != null) {
    		ContentValues cv=bean2ContentValues(indexBean);
            long uid = db.insert(TB_NAME, ID, cv);
            Log.i(TAG, "insert " + uid + "");
            return uid;
		}
		return -1L;
    }
    /**
     * 删除by imeivalue
     * @param imeivalue
     * @return
     */
    public long deleteByIndexName(String indexname)
    {
    	if (db != null) {
    		   int rows = db.delete(TB_NAME, 
    				   		build_Where_Key(new String[]{index_name}),
    				   		new String[]{indexname+""});
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
    public long deleteByBean(IndexBean indexBean)
    {
    	if (db != null) {
    		int rows = db.delete(TB_NAME, 
    				build_Where_Key(new String[]{index_name}),
    				new String[]{String.valueOf(indexBean.getIndex_name()+"")});
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
    public int update(IndexBean indexBean)
    {
        ContentValues values = bean2ContentValues(indexBean);

        int rows = db.update(TB_NAME, values,
        		build_Where_Key(new String[]{ID}),
        		new String[]{indexBean.getId()});
        Log.i(TAG, "update " + rows + "");
        
        return rows;
    }
    
    /**
     * 查询某一个chanel
     * @param chanelid
     * @return
     */
    public IndexBean queryOneByName(String indexname)
    {
        Cursor cursor = db.query(TB_NAME, null,
        		build_Where_Key(new String[]{index_name}),
        		new String[]{indexname}, null, null, null, null);
        IndexBean indexBean=null;
        if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				indexBean = new IndexBean();
				indexBean.setId(cursor.getString(0));
				indexBean.setIndex_cmd(cursor.getString(1));
				indexBean.setIndex_name(cursor.getString(2));
				indexBean.setImage(cursor.getString(3));
	        	break;
			}
		}
        return indexBean;
    }
    /**
     * 查询所有
     * @return
     */
    public List<IndexBean> queryAll()
    {
    	Cursor cursor = db.query(TB_NAME, null,
    			null,null, null, null, null, null);
    	
    	return cursor2Beans(cursor);
    }

    
}
