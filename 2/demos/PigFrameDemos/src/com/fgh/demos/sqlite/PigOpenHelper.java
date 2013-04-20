package com.fgh.demos.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PigOpenHelper extends SQLiteOpenHelper {
	private static final String TAG = "DBOpenHelper";
	
	public static final String DB_NAME = "db_pig";
	private static final int DATABASE_VERSION = 2;

	public PigOpenHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
	}
	public PigOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/// 创建表
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("CREATE TABLE IF NOT EXISTS "+
				    UserDao.TB_NAME+"(" +
	                UserDao.ID + " integer primary key,"+
	                UserDao.USER + " varchar,"+
	                UserDao.USERID + " long,"+
	                UserDao.TOKEN + " varchar,"+
	                UserDao.TOKENSECRET + " varchar,"+
	                UserDao.ISLOGIN + " short" +
	                ")"
	                );
		Log.i(TAG, "onCreate");
	}

	/// 更新表
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + UserDao.TB_NAME);
        onCreate(db);
        Log.i(TAG, "onUpgrade");
	}


}
