package com.fgh.demos.sqlite;



import java.util.ArrayList;
import java.util.List;

import com.fgh.demos.pageturner.sqlite.BookStoryBean;


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
public class UserDao {
	
	private static final String TAG = "UserDao";
	
	//对应的表名
	public static final String TB_NAME = "tb_user";
	//对应的表的字段
	public static final String ID = "_id";
	public static final String USER = "user";
	public static final String USERID = "userId";
	public static final String TOKEN = "token";
	public static final String TOKENSECRET = "tokenSecret";
	public static final String ISLOGIN = "isLogin";
	
	
	
	
	protected static SQLiteDatabase db;
    protected static PigOpenHelper dbhelper;
	private static UserDao dao;

	
	
	
	
	private UserDao(Context context){
    }
	
	public static synchronized UserDao getInstance(Context ctx) {
		if (ctx == null) {
			return null;
		}
		if(dbhelper==null){
			dbhelper = new PigOpenHelper(ctx);
    	}
    	if( db==null){
    		 db  = dbhelper.getWritableDatabase();
    	}
		if (dao == null) {
			dao = new UserDao(ctx);
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
	 * @param userBean
	 * @return
	 */
	private ContentValues bean2ContentValues(UserBean userBean) {
		ContentValues v = new ContentValues();
		
		if (userBean.getId() != null) {
			v.put(ID, userBean.getId());
		}
		if (userBean.getUserId() != 0) {
			v.put(USERID, userBean.getUserId());
		}
		if (userBean.getUser() != null) {
			v.put(USER, userBean.getUser());
		}
		if (userBean.getToken() != null) {
			v.put(TOKEN,userBean.getToken());
		}
		if (userBean.getTokenSecret() != null) {
			v.put(TOKENSECRET, userBean.getTokenSecret() );
		}
		v.put(ISLOGIN, userBean.getIsLogin());
		
		return v;
	}
	
	
	/**
	 * 根据游标转换成具体的bean集合
	 * @param cursor
	 * @return
	 */
	private List<UserBean> cursor2Beans(Cursor cursor) {
	        try {
	        	 List<UserBean> userList = null;
	        	 UserBean userBean = null;
					if (cursor != null && cursor.getCount() > 0) {
						userList = new ArrayList<UserBean>();
						while (cursor.moveToNext()) {
							userBean = new UserBean();
				        	userBean.setId(cursor.getString(0));
				        	userBean.setUser(cursor.getString(1));
				        	userBean.setUserId(cursor.getLong(2));
				        	userBean.setToken(cursor.getString(3));
				        	userBean.setTokenSecret(cursor.getString(4));
				        	userBean.setIsLogin(cursor.getShort(5));
				            userList.add(userBean);
						}
					}
				  cursor.close();
				  return userList;
			    } catch (Exception e) {
				  cursor.close();
			    }
			   return null;
		}
	private UserBean cursor2Bean(Cursor cursor) {
		try {
			UserBean userBean = null;
			if (cursor != null && cursor.getCount() > 0) {
					userBean = new UserBean();
					userBean.setId(cursor.getString(0));
					userBean.setUser(cursor.getString(1));
					userBean.setUserId(cursor.getLong(2));
					userBean.setToken(cursor.getString(3));
					userBean.setTokenSecret(cursor.getString(4));
					userBean.setIsLogin(cursor.getShort(5));
			}
			return userBean;
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
     * 插入user表的记录
     * @param user
     * @return
     */
    public long insert(UserBean user)
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
     * 删除by uid
     * @param uid
     * @return
     */
    public long deleteByUid(String uid)
    {
    	if (db != null) {
    		   int rows = db.delete(TB_NAME, 
    				   		build_Where_Key(new String[]{USERID}),
    				   		new String[]{uid});
    		    Log.i(TAG, "delete " + rows + "");
    	        return rows;
    	}
    	return -1L;
    }
    /**
     * 删除个userbean，参数为bean。实际条件为uid
     * @param userBean
     * @return
     */
    public long deleteByBean(UserBean userBean)
    {
    	if (db != null) {
    		int rows = db.delete(TB_NAME, 
    				build_Where_Key(new String[]{USERID}),
    				new String[]{String.valueOf(userBean.getUserId())});
    		Log.i(TAG, "delete " + rows + "");
    		return rows;
    	}
    	return -1L;
    }
    
    /**
     * 更新user表的记录
     * @param userBean
     * @return
     */
    public int update(UserBean userBean)
    {
        ContentValues values = bean2ContentValues(userBean);

        int rows = db.update(TB_NAME, values,
        		build_Where_Key(new String[]{ID}),
        		new String[]{userBean.getId()});
        Log.i(TAG, "update " + rows + "");
        
        return rows;
    }
    /**
     * 更新user表的记录
     * @param where_uid
     * @param islogin
     * @return
     */
    public int updateByUid(String where_uid,int islogin)
    {
    	ContentValues values = new ContentValues();
    	values.put(ISLOGIN, islogin);
    	
    	int rows = db.update(TB_NAME, values,
    			build_Where_Key(new String[]{USERID}),
    			new String[]{where_uid});
    	/// 使用下面的方法会报错，不知道什么原因，待解。。。
    	//int id = db.update(SqliteHelper.TB_NAME, values, USER + "=" + user.getUser(), null);
    	Log.i(TAG, "update " + rows + "");
    	
    	return rows;
    }
    
    /**
     * 查询某一个user
     * @param uid
     * @return
     */
    public UserBean queryOneByUserId(String uid)
    {
        Cursor cursor = db.query(TB_NAME, null,
        		build_Where_Key(new String[]{USERID}),
        		new String[]{uid}, null, null, null, null);
        
        
        UserBean userBean=null;
        
        if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				cursor2Bean(cursor);
	        	break;
			}
		}
        return userBean;
    }
    /**
     * 查询所有
     * @return
     */
    public List<UserBean> queryAll()
    {
    	Cursor cursor = db.query(TB_NAME, null,
    			null,null, null, null, null, null);
    	
    	return cursor2Beans(cursor);
    }

}
