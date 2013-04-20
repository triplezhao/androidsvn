package com.fgh.demos.pageturner.sqlite;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pigframe.util.PigLog;


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
public class BookStoryDao {
	
	private static final String TAG = "UserDao";
	
	//对应的表名
	public static final String TB_NAME = "tb_BookStory";
	//对应的表的字段
	public static final String ID = "_id";
	public static final String BOOKID = "bookId";
	public static final String BOOKPATH = "bookPath";
	public static final String BOOKNAME = "bookName";
	public static final String BOOKOFFSET = "bookOffset";
	public static final String TIME = "time";
	public static final String BOOKSIZE = "bookSize";
	
	
	
	
	protected static SQLiteDatabase db;
    protected static PigBookOpenHelper dbhelper;
	private static BookStoryDao dao;

	
	
	
	
	private BookStoryDao(Context context){
    }
	
	public static synchronized BookStoryDao getInstance(Context ctx) {
		if (ctx == null) {
			return null;
		}
		if(dbhelper==null){
			dbhelper = new PigBookOpenHelper(ctx);
    	}
    	if( db==null){
    		 db  = dbhelper.getWritableDatabase();
    	}
		if (dao == null) {
			dao = new BookStoryDao(ctx);
		}
		return dao;
	}
    
    public void close()
    {
    	if(db!=null){
    		db.close();
    	}
    	if(dbhelper!=null){
    		dbhelper.close();
    	}
    	dao=null;
    }
	
	
	
	/**
	 * 更具bean对象转换成CV
	 * @param bookStoryBean
	 * @return
	 */
	private ContentValues bean2ContentValues(BookStoryBean bookStoryBean) {
		ContentValues v = new ContentValues();
		
		if (bookStoryBean.getId() != null) {
			v.put(ID, bookStoryBean.getId());
		}
		if (bookStoryBean.getBookId() != null) {
			v.put(BOOKID, bookStoryBean.getBookId());
		}
		if (bookStoryBean.getBookPath() != null) {
			v.put(BOOKPATH, bookStoryBean.getBookPath());
		}
		if (bookStoryBean.getBookName() != null) {
			v.put(BOOKNAME,bookStoryBean.getBookName());
		}
		
		v.put(BOOKOFFSET, bookStoryBean.getBookOffset() );
		PigLog.i(TAG, bookStoryBean.getBookOffset() +"");
		v.put(BOOKSIZE, bookStoryBean.getBookSize() );
		v.put(TIME,System.currentTimeMillis());
		return v;
	}
	
	
	/**
	 * 根据游标转换成具体的bean集合
	 * @param cursor
	 * @return
	 */
	private List<BookStoryBean> cursor2Beans(Cursor cursor) {
	        try {
	        	 List<BookStoryBean> bookStoryList = null;
	        	 BookStoryBean bookStoryBean = null;
					if (cursor != null && cursor.getCount() > 0) {
						bookStoryList = new ArrayList<BookStoryBean>();
						while (cursor.moveToNext()) {
							bookStoryBean = new BookStoryBean();
				        	bookStoryBean.setId(cursor.getString(0));
				        	bookStoryBean.setBookId(cursor.getString(1));
				        	bookStoryBean.setBookPath(cursor.getString(2));
				        	bookStoryBean.setBookName(cursor.getString(3));
				        	bookStoryBean.setBookOffset(cursor.getInt(4));
				        	bookStoryBean.setTime(cursor.getLong(5));
				        	bookStoryBean.setBookSize(cursor.getLong(6));
				        	bookStoryList.add(bookStoryBean);
						}
					}
				  cursor.close();
				  return bookStoryList;
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
     * 插入book历史表的记录
     * @param user
     * @return
     */
    public long insert(BookStoryBean user)
    {
    	if (db != null) {
    		ContentValues cv=bean2ContentValues(user);
            long uid = db.insert(TB_NAME, ID, cv);
            Log.i(TAG, "insert " + uid + "");
            return uid;
		}
		return -1L;
    }
    /**
     * 删除by bookid
     * @param bookid
     * @return
     */
    public long deleteByBookPath(String bookPath)
    {
    	if (db != null) {
    		   int rows = db.delete(TB_NAME, 
    				   		build_Where_Key(new String[]{BOOKPATH}),
    				   		new String[]{bookPath+""});
    		    Log.i(TAG, "delete " + rows + "");
    	        return rows;
    	}
    	return -1L;
    }
    /**
     * 删除个userbean，参数为bean。实际条件为uid
     * @param bookStoryBean
     * @return
     */
    public long deleteByBean(BookStoryBean bookStoryBean)
    {
    	if (db != null) {
    		int rows = db.delete(TB_NAME, 
    				build_Where_Key(new String[]{BOOKID}),
    				new String[]{String.valueOf(bookStoryBean.getBookId()+"")});
    		Log.i(TAG, "delete " + rows + "");
    		return rows;
    	}
    	return -1L;
    }
    
    /**
     * 更新user表的记录
     * @param bookStoryBean
     * @return
     */
    public int update(BookStoryBean bookStoryBean)
    {
        ContentValues values = bean2ContentValues(bookStoryBean);

        int rows = db.update(TB_NAME, values,
        		build_Where_Key(new String[]{ID}),
        		new String[]{bookStoryBean.getId()});
        Log.i(TAG, "update " + rows + "");
        
        return rows;
    }
    /**
     * 更新user表的记录
     * @param bookid
     * @param offset
     * @return 
     */
    public int updateByBookPath(String bookPath,int offset)
    {
    	ContentValues values = new ContentValues();
    	values.put(BOOKOFFSET, offset);
    	values.put(TIME, System.currentTimeMillis());
    	
    	int rows = db.update(TB_NAME, values,
    			build_Where_Key(new String[]{BOOKPATH}),
    			new String[]{bookPath+""});
    	/// 使用下面的方法会报错，不知道什么原因，待解。。。
    	//int id = db.update(SqliteHelper.TB_NAME, values, USER + "=" + user.getUser(), null);
    	Log.i(TAG, "update " + rows + "");
    	
    	return rows;
    }
    
    /**
     * 查询某一个book
     * @param bookid
     * @return
     */
    public BookStoryBean queryOneBookByBookPath(String bookpath)
    {
        Cursor cursor = db.query(TB_NAME, null,
        		build_Where_Key(new String[]{BOOKPATH}),
        		new String[]{bookpath}, null, null, null, null);
        BookStoryBean bookStoryBean=null;
        if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				bookStoryBean = new BookStoryBean();
	        	bookStoryBean.setId(cursor.getString(0));
	        	bookStoryBean.setBookId(cursor.getString(1));
	        	bookStoryBean.setBookPath(cursor.getString(2));
	        	bookStoryBean.setBookName(cursor.getString(3));
	        	bookStoryBean.setBookOffset(cursor.getInt(4));
	        	bookStoryBean.setTime(cursor.getLong(5));
	        	bookStoryBean.setBookSize(cursor.getLong(6));
	        	break;
			}
		}
        return bookStoryBean;
    }
    /**
     * 查询所有
     * @return
     */
    public List<BookStoryBean> queryAllBookStory()
    {
    	Cursor cursor = db.query(TB_NAME, null,
    			null,null, null, null, TIME+" DESC", null);
    	
    	return cursor2Beans(cursor);
    }
    /**
     * 查询最近一个book
     * @param bookid
     * @return
     */
    public BookStoryBean queryLastOneBook()
    {
        Cursor cursor = db.query(TB_NAME, null,
        		null,null, null, null, TIME+" DESC", null);
        
        BookStoryBean bookStoryBean=null;
        
        if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				bookStoryBean = new BookStoryBean();
	        	bookStoryBean.setId(cursor.getString(0));
	        	bookStoryBean.setBookId(cursor.getString(1));
	        	bookStoryBean.setBookPath(cursor.getString(2));
	        	bookStoryBean.setBookName(cursor.getString(3));
	        	bookStoryBean.setBookOffset(cursor.getInt(4));
	        	bookStoryBean.setTime(cursor.getLong(5));
	        	bookStoryBean.setBookSize(cursor.getLong(6));
	        	break;
			}
		}
        return bookStoryBean;
    }

}
