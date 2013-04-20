package com.fgh.voice.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fgh.voice.db.bean.UserBean;

public class UserDao {
	private static final String TAG = "DataHelper4User";
   
    private SQLiteDatabase db;
    private SqliteHelper4User dbHelper;
    
    public UserDao(Context context){
        dbHelper = new SqliteHelper4User(context,
        		DBConstants.DB_NAME, null, DBConstants.DB_VERSION);
        db = dbHelper.getWritableDatabase();
    }
    
    public void close()
    {
        db.close();
        dbHelper.close();
    }
    
    /// 获取users表中的User、Access Token、Access Secret的记录
    public List<UserBean> selectByUser(String user)
    {
        List<UserBean> userList = new ArrayList<UserBean>();
        Cursor cursor = db.query(SqliteHelper4User.TB_NAME, null, "USER = '" + user + "'", null, null, null, UserBean.ID + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(1)!= null)){
        	UserBean userBean =getFromCursor(cursor);
            userList.add(userBean);
            cursor.moveToNext();
        }
        cursor.close();
        
        return userList;
    }

    /// 获取users表中的User、Access Token、Access Secret的记录
    public List<UserBean> selectByIsRemember(boolean isRemember)
    {
        List<UserBean> userList = new ArrayList<UserBean>();
        short shIsRemember = (short) (isRemember?1:0);
        Cursor cursor = db.query(SqliteHelper4User.TB_NAME, null, "ISREMEMBER = " + shIsRemember, null, null, null, UserBean.ID + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(1)!= null)){
        	UserBean userBean =getFromCursor(cursor);
            userList.add(userBean);
            cursor.moveToNext();
        }
        cursor.close();
        
        return userList;
    }
    /// 获取users表中的公共用户
    public UserBean selectIsPublicUser(boolean isPublic)
    {
    	short shisPublic = (short) (isPublic?1:0);
    	Cursor cursor = db.query(SqliteHelper4User.TB_NAME, null, "isPublic = " + shisPublic, null, null, null, UserBean.ID + " DESC");
    	cursor.moveToFirst();
    	if (!cursor.isAfterLast() && (cursor.getString(1)!= null)){
    		UserBean userBean =getFromCursor(cursor);
    		return userBean;
    	}
    	cursor.close();
    	
    	return null;
    }
    public UserBean selectOneUser()
    {
//    	List<UserBean> userList = new ArrayList<UserBean>();
    	Cursor cursor = db.query(SqliteHelper4User.TB_NAME, null, null, null, null, null, UserBean.ID + " DESC");
    	cursor.moveToFirst();
    	if (!cursor.isAfterLast() && (cursor.getString(1)!= null)){
    		UserBean userBean =getFromCursor(cursor);
    		return userBean;
    	}
    	cursor.close();
    	
    	return null;
    }
    
    /// 判断users表中的是否包含某个User的记录
    public Boolean exist(String user)
    {
        Boolean b = false;
        Cursor cursor = db.query(SqliteHelper4User.TB_NAME, null, UserBean.USER + "=" + user, null, null, null,null);
        b=cursor.moveToFirst();
       
        Log.i(TAG, "exist " + b.toString());
       
        cursor.close();
        return b;
    }

    /// 更新user表的记录
    public int update(UserBean user)
    {
        ContentValues values = new ContentValues();
        values.put(UserBean.USER, user.getUser());
        values.put(UserBean.USERID, user.getUserId());
        values.put(UserBean.TOKEN, user.getToken());
        values.put(UserBean.TOKENSECRET, user.getTokenSecret());
        values.put(UserBean.ISREMEMBER, user.getIsRemember()?1:0);
        values.put(UserBean.ISAUTOLOGIN, user.getIsAutoLogin()?1:0);
        values.put(UserBean.ISPUBLIC, user.isPublic()?1:0);

        int id = db.update(SqliteHelper4User.TB_NAME, values, UserBean.ID + "=" + user.getId(), null);
        /// 使用下面的方法会报错，不知道什么原因，待解。。。
        //int id = db.update(SqliteHelper.TB_NAME, values, UserBean.USER + "=" + user.getUser(), null);
        
        Log.i(TAG, "update " + id + "");
        
        return id;
    }
    
    /// 插入user表的记录
    public long insert(UserBean user)
    {
        ContentValues values = new ContentValues();
        values.put(UserBean.USER, user.getUser());
        values.put(UserBean.USERID, user.getUserId());
        values.put(UserBean.TOKEN, user.getToken());
        values.put(UserBean.TOKENSECRET, user.getTokenSecret());
        values.put(UserBean.ISREMEMBER, user.getIsRemember()?1:0);
        values.put(UserBean.ISAUTOLOGIN, user.getIsAutoLogin()?1:0);
        values.put(UserBean.ISPUBLIC, user.isPublic()?1:0);
        long uid = db.insert(SqliteHelper4User.TB_NAME, UserBean.ID, values);
        
        Log.i(TAG, "insert " + uid + "");
        
        return uid;
    }
    
    /// 删除user表的记录
    public int delete(UserBean user){
        int id = db.delete(SqliteHelper4User.TB_NAME, UserBean.ID + "=" + user.getId(), null);
        
        Log.i(TAG, "delete " + id + "");
        
        return id;
    }
    
    private UserBean getFromCursor(Cursor cursor){
    	UserBean userBean = new UserBean();
		userBean.setId(cursor.getString(0));
		userBean.setUser(cursor.getString(1));
		userBean.setUserId(cursor.getLong(2));
		userBean.setToken(cursor.getString(3));
		userBean.setTokenSecret(cursor.getString(4));
		userBean.setIsRemember(cursor.getShort(5)==0?false:true);
		userBean.setIsAutoLogin(cursor.getShort(6)==0?false:true);
		userBean.setPublic(cursor.getShort(7)==0?false:true);
		
		return userBean;
    }
}

