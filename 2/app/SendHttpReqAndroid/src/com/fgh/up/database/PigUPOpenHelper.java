package com.fgh.up.database;



import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PigUPOpenHelper extends SQLiteOpenHelper {
	private static final String TAG = "DBOpenHelper";
	
	public static final String DB_NAME = "db_up";
	private static final int DATABASE_VERSION = 11;

	public PigUPOpenHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
	}
	public PigUPOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/// 创建表
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS "+
				    ChanelDao.TB_NAME+"(" +
	                ChanelDao.ID + " integer primary key,"+
	                ChanelDao.chanelNAME + " varchar"+
	                ")"
	                );
		Log.i(TAG, "onCreate");
		ContentValues v = new ContentValues();
			v.put(ChanelDao.chanelNAME, "zhuowang");
		db.insert(ChanelDao.TB_NAME, "_id", v);
		
		ContentValues v2 = new ContentValues();
			v2.put(ChanelDao.chanelNAME, "motoHD");
		db.insert(ChanelDao.TB_NAME, "_id", v2);
		
		
		db.execSQL("CREATE TABLE IF NOT EXISTS "+
			    ImeiDao.TB_NAME+"(" +
			    ImeiDao.ID + " integer primary key,"+
			    ImeiDao.imei_value + " varchar,"+
			    ImeiDao.imei_chanel + " varchar,"+
			    ImeiDao.imei_date + " integer NOT NULL DEFAULT 0"+
                ")"
                );
		db.execSQL("CREATE TABLE IF NOT EXISTS "+
				ProxyDao.TB_NAME+"(" +
				ProxyDao.ID + " integer primary key,"+
				ProxyDao.mProxyName + " varchar,"+
				ProxyDao.mProxyValue + " varchar"+
				")"
				);
		
	}

	/// 更新表
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + ChanelDao.TB_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + ImeiDao.TB_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + ProxyDao.TB_NAME);
        onCreate(db);
        Log.i(TAG, "onUpgrade");
	}


}
