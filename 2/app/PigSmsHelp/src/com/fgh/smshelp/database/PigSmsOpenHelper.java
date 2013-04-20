package com.fgh.smshelp.database;



import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PigSmsOpenHelper extends SQLiteOpenHelper {
	private static final String TAG = "DBOpenHelper";
	
	public static final String DB_NAME = "db_sms_help";
	private static final int DATABASE_VERSION = 4;

	public PigSmsOpenHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
	}
	public PigSmsOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/// 创建表
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS "+
				    IndexDao.TB_NAME+"(" +
	                IndexDao.ID + " integer primary key,"+
	                IndexDao.index_cmd + " varchar ,"+
	                IndexDao.index_name + " varchar ,"+
	                IndexDao.image + " varchar"+
	                ")"
	                );
		Log.i(TAG, "onCreate");
		ContentValues v = new ContentValues();
			v.put(IndexDao.index_cmd, "YE");
			v.put(IndexDao.index_name, "余额查询");
		db.insert(IndexDao.TB_NAME, "_id", v);
		
		ContentValues v2 = new ContentValues();
			v2.put(IndexDao.index_cmd, "CXYL");
			v2.put(IndexDao.index_name, "套餐状况");
		db.insert(IndexDao.TB_NAME, "_id", v2);
		
		ContentValues v3 = new ContentValues();
		v3.put(IndexDao.index_cmd, "CXGLL");
		v3.put(IndexDao.index_name, "剩余流量");
		db.insert(IndexDao.TB_NAME, "_id", v3);
		
		
	}

	/// 更新表
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + IndexDao.TB_NAME);
        onCreate(db);
        Log.i(TAG, "onUpgrade");
	}


}
